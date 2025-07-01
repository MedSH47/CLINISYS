'use strict';

const usernamePage   = document.querySelector('#username-page');
const chatPage       = document.querySelector('#chat-page');
const usernameForm   = document.querySelector('#usernameForm');
const messageForm    = document.querySelector('#messageForm');
const messageInput   = document.querySelector('#message');
const messageArea    = document.querySelector('#messageArea');
const connectingElem = document.querySelector('.connecting');

let stompClient = null;
let userId      = null;

const colors = [
  '#2196F3','#32c787','#00BCD4','#ff5652',
  '#ffc107','#ff85af','#FF9800','#39bbb0'
];

function connect(event) {
  event.preventDefault();
  userId = parseInt(document.querySelector('#userId').value, 10);

  if (!Number.isInteger(userId)) {
    alert('Please enter a valid integer User ID.');
    return;
  }

  usernamePage.classList.add('hidden');
  chatPage.classList.remove('hidden');

  const socket = new SockJS('/template-core/ws');
  stompClient = Stomp.over(socket);
  stompClient.connect({}, onConnected, onError);
}

function onConnected() {
  stompClient.subscribe('/topic/public', onMessageReceived);

  // Announce new user (optional)
  stompClient.send(
    "/app/chat.addUser",
    {},
    JSON.stringify({ sender: userId, type: 'JOIN' })
  );

  connectingElem.classList.add('hidden');
}

function onError(error) {
  connectingElem.textContent = 'Connection failed. Try refreshing.';
  connectingElem.style.color = 'red';
}

function sendMessage(event) {
  event.preventDefault();
  const content = messageInput.value.trim();

  if (content && stompClient) {
    stompClient.send(
      "/app/chat.sendMessage",
      {},
      JSON.stringify({
        sender:  userId,
        type:    'CHAT',
        content: content
      })
    );
    messageInput.value = '';
  }
}

function onMessageReceived(payload) {
  const msg = JSON.parse(payload.body);
  const li  = document.createElement('li');

  if (msg.type === 'JOIN' || msg.type === 'LEAVE') {
    li.classList.add('event-message');
    li.textContent = 
      (msg.type === 'JOIN' ? 'User ' : 'User ') +
      msg.sender + 
      (msg.type === 'JOIN' ? ' joined' : ' left');
  } else {
    li.classList.add('chat-message');

    // avatar
    const avatar = document.createElement('i');
    const nameStr = msg.sender.toString();
    avatar.textContent = nameStr[0];
    avatar.style['background-color'] = getAvatarColor(nameStr);
    li.appendChild(avatar);

    // ID label
    const idSpan = document.createElement('span');
    idSpan.textContent = msg.sender;
    li.appendChild(idSpan);

    // message text
    const textP = document.createElement('p');
    textP.textContent = msg.content;
    li.appendChild(textP);
  }

  messageArea.appendChild(li);
  messageArea.scrollTop = messageArea.scrollHeight;
}

function getAvatarColor(str) {
  let hash = 0;
  for (let ch of str) { hash = 31*hash + ch.charCodeAt(0); }
  return colors[Math.abs(hash % colors.length)];
}

usernameForm.addEventListener('submit', connect, true);
messageForm.addEventListener('submit', sendMessage, true);
