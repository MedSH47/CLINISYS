'use strict';

// Page element selectors
const loginPage = document.querySelector('#login-page');
const chatPage = document.querySelector('#chat-page');
const loginForm = document.querySelector('#loginForm');
const messageForm = document.querySelector('#messageForm');
const messageInput = document.querySelector('#message');
const messageArea = document.querySelector('#messageArea');
const connectingElement = document.querySelector('.connecting');
const partnerLabel = document.querySelector('#partnerLabel');

// STOMP and user state variables
let stompClient = null;
let currentUser = null; // Will store { id, login }
let partnerId = null;
let jwtToken = null; // To store the JWT token

// Avatar colors
const colors = ['#2196F3', '#32c787', '#00BCD4', '#ff5652', '#ffc107', '#ff85af', '#FF9800', '#39bbb0'];

// Event Listener for the Login Form
loginForm.addEventListener('submit', handleLogin, true);

/**
 * Handles the login form submission.
 * It authenticates the user, stores the token, and connects to the WebSocket.
 */
async function handleLogin(event) {
    event.preventDefault();
    const username = document.querySelector('#username').value.trim();
    const password = document.querySelector('#password').value.trim();
    partnerId = parseInt(document.querySelector('#partnerId').value.trim(), 10);

    if (!username || !password || !partnerId) {
        alert("Username, password, and Partner ID are required.");
        return;
    }

    try {
        // Step 1: Authenticate and get JWT token
        const response = await fetch('/template-core/api/authenticate', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ login: username, motDePasse: password })
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || 'Authentication failed!');
        }

        const data = await response.json();
        jwtToken = data.token;
        console.log("Successfully authenticated. Token received.");

        // Step 2: Get user details (like ID) using the login
        const userDetailsResponse = await fetch(`/template-core/api/utilisateurs/find-by-login/${username}`);
        if (!userDetailsResponse.ok) {
            throw new Error('Could not fetch user details after login.');
        }
        currentUser = await userDetailsResponse.json();
        console.log("Current user details:", currentUser);

        // Step 3: UI transition and WebSocket connection
        loginPage.classList.add('hidden');
        chatPage.classList.remove('hidden');
        partnerLabel.textContent = partnerId;

        connectToWebSocket();

    } catch (error) {
        console.error("Login Error:", error);
        alert(error.message);
    }
}

/**
 * Establishes a WebSocket connection using the stored JWT token.
 */
function connectToWebSocket() {
    if (jwtToken && currentUser) {
        const socket = new SockJS('/template-core/ws');
        stompClient = Stomp.over(socket);

        const headers = {
            'Authorization': `Bearer ${jwtToken}`
        };

        stompClient.connect(headers, onConnected, onError);
    }
}

function onConnected() {
    console.log('STOMP client connected');
    connectingElement.classList.add('hidden');

    // Subscribe to the personal queue to receive private messages
    stompClient.subscribe('/user/queue/private', onMessageReceived);

    // Announce user join to the server
    stompClient.send("/app/chat.addUser", {},
        JSON.stringify({ sender: currentUser.id, type: 'JOIN' })
    );

    // Fetch and display chat history
    fetchChatHistory();
}

function onError(error) {
    console.error('STOMP connection error:', error);
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

/**
 * Fetches the chat history between the current user and their partner.
 */
function fetchChatHistory() {
    fetch(`/template-core/api/chat/history?user1=${currentUser.id}&user2=${partnerId}`)
        .then(response => response.json())
        .then(messages => {
            messageArea.innerHTML = ''; // Clear previous messages
            messages.forEach(renderMessage);
            scrollToBottom();
        })
        .catch(error => console.error('Failed to fetch chat history:', error));
}


messageForm.addEventListener('submit', sendMessage, true);

function sendMessage(event) {
    event.preventDefault();
    const messageContent = messageInput.value.trim();

    if (messageContent && stompClient) {
        const chatMessage = {
            sender: currentUser.id,
            receiver: partnerId,
            content: messageContent,
            type: 'CHAT'
        };

        stompClient.send("/app/chat.sendPrivate", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
}

function onMessageReceived(payload) {
    const message = JSON.parse(payload.body);
    renderMessage(message);
}

function renderMessage(message) {
    const messageElement = document.createElement('li');

    if (message.type === 'JOIN' || message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        messageElement.textContent = `User ${message.sender} ${message.type === 'JOIN' ? 'joined' : 'left'}`;
    } else {
        messageElement.classList.add('chat-message');

        const avatarElement = document.createElement('i');
        const senderInitial = message.sender.toString()[0]; // Use sender ID for initial
        avatarElement.textContent = senderInitial;
        avatarElement.style['background-color'] = getAvatarColor(message.sender.toString());
        messageElement.appendChild(avatarElement);

        const usernameElement = document.createElement('span');
        const usernameText = document.createTextNode(message.sender === currentUser.id ? 'You' : `User ${message.sender}`);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);

        const textElement = document.createElement('p');
        textElement.textContent = message.content;
        messageElement.appendChild(textElement);
    }

    messageArea.appendChild(messageElement);
    scrollToBottom();
}

function getAvatarColor(messageSender) {
    const hash = messageSender.split('').reduce((acc, char) => 31 * acc + char.charCodeAt(0), 0);
    return colors[Math.abs(hash % colors.length)];
}

function scrollToBottom() {
    messageArea.scrollTop = messageArea.scrollHeight;
}