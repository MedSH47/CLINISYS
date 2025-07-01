package com.csys.template.dtoRequest;

import java.util.List;

public class ChatRoomRequest {
    private String name;
    private List<Integer> participantIds; // IDs des utilisateurs participants
    public ChatRoomRequest(String name, List<Integer> participantIds) {
        this.name = name;
        this.participantIds = participantIds;
    }
    public ChatRoomRequest() {
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Integer> getParticipantIds() {
        return participantIds;
    }
    public void setParticipantIds(List<Integer> participantIds) {
        this.participantIds = participantIds;
    }


}
