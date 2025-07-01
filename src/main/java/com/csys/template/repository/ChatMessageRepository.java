package com.csys.template.repository;

import com.csys.template.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {

    List<ChatMessage> findByChatRoomIdOrderByTimestampAsc(Integer chatRoomId);

    /**
     * --- NOUVELLE MÉTHODE ESSENTIELLE ---
     * Cette requête personnalisée récupère les messages non lus pour un utilisateur.
     * La logique est la suivante :
     * 1. On sélectionne les messages (m).
     * 2. Dans les salles de discussion (cr) où l'utilisateur (p) est un participant.
     * 3. Le message ne doit pas avoir été envoyé par l'utilisateur lui-même.
     * 4. L'ID de l'utilisateur ne doit PAS être dans la liste des utilisateurs qui ont lu le message (readByUsers).
     * @param userId L'ID de l'utilisateur pour lequel on cherche les messages non lus.
     * @return Une liste de messages non lus.
     */
    @Query("SELECT m FROM ChatMessage m JOIN m.chatRoom cr JOIN cr.participants p WHERE p.id = :userId AND m.sender.id != :userId AND m NOT IN (SELECT m FROM ChatMessage m JOIN m.readByUsers u WHERE u.id = :userId) ORDER BY m.timestamp DESC")
    List<ChatMessage> findUnreadMessagesForUser(@Param("userId") Integer userId);
}
