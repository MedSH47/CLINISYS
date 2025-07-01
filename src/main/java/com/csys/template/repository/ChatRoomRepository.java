package com.csys.template.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csys.template.domain.ChatRoom;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom,Integer> {

    // Trouver les conversations privées entre deux utilisateurs spécifiques
    // Cette requête est plus complexe car ManyToMany nécessite de vérifier la taille de l'ensemble et les IDs
    @Query("SELECT cr FROM ChatRoom cr JOIN cr.participants p WHERE cr.type = 'PRIVATE' AND p.id IN ?1 GROUP BY cr HAVING COUNT(DISTINCT p.id) = ?2")
    Optional<ChatRoom> findPrivateChatRoomByParticipantIds(List<Integer> participantIds, long expectedParticipantCount);


    // Trouver toutes les conversations (privées et de groupe) d'un utilisateur
    @Query("SELECT cr FROM ChatRoom cr JOIN cr.participants p WHERE p.id = :userId")
    List<ChatRoom> findByParticipants_Id(Integer userId);
}