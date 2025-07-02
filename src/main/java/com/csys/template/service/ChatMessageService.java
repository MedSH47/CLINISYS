package com.csys.template.service;

import com.csys.template.domain.ChatMessage;
import com.csys.template.domain.QChatMessage;
import com.csys.template.repository.ChatMessageRepository;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChatMessageService {

    private final ChatMessageRepository repo;

    @Autowired
    public ChatMessageService(ChatMessageRepository repo) {
        this.repo = repo;
    }

    /**
     * Returns all ChatMessages between user1 and user2, sorted by their timestamp.
     */
    public List<ChatMessage> findByParticipants(Integer user1, Integer user2) {
        QChatMessage qm = QChatMessage.chatMessage;
        BooleanExpression p1 = qm.sender.eq(user1).and(qm.receiver.eq(user2));
        BooleanExpression p2 = qm.sender.eq(user2).and(qm.receiver.eq(user1));
        Predicate conversation = p1.or(p2);
        
        // --- FIX ---
        // Always sort by the reliable timestamp field.
        Sort sortByTimestampAsc = Sort.by(Sort.Direction.ASC, "timestamp");
        
        return (List<ChatMessage>) repo.findAll(conversation, sortByTimestampAsc);
    }

    /**
     * Finds the latest message from each conversation for a given user.
     */
    public List<ChatMessage> findMyChatMessages(Integer userId) {
        QChatMessage qm = QChatMessage.chatMessage;
        Predicate userIsParticipant = qm.sender.eq(userId).or(qm.receiver.eq(userId));

        // --- THE CRITICAL FIX ---
        // We must sort by timestamp descending to ensure the most recent messages come first.
        // This was the cause of the loading screen issue.
        Sort sortByTimestampDesc = Sort.by(Sort.Direction.DESC, "timestamp");
        List<ChatMessage> allMessages = (List<ChatMessage>) repo.findAll(userIsParticipant, sortByTimestampDesc);

        // This logic is now guaranteed to work correctly because the list is sorted properly.
        Map<Integer, ChatMessage> latestMessagesByPartner = allMessages.stream()
                .collect(Collectors.toMap(
                        message -> message.getSender().equals(userId) ? message.getReceiver() : message.getSender(),
                        Function.identity(),
                        (existing, replacement) -> existing
                ));

        return new ArrayList<>(latestMessagesByPartner.values())
                .stream()
                // Final sort to make sure the newest conversations are at the top of the list.
                .sorted(Comparator.comparing(ChatMessage::getTimestamp).reversed())
                .collect(Collectors.toList());
    }
}