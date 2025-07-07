// Fichier à modifier : src/main/java/com/csys/template/web/rest/ressource/ChatFileResource.java

package com.csys.template.web.rest.ressource;

import com.csys.template.domain.ChatMessage;
import com.csys.template.service.ChatMessageService;
import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class ChatFileResource {

    private final ChatMessageService chatMessageService;

    public ChatFileResource(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @PostMapping("/chat/messages/file")
    public ResponseEntity<Void> uploadAndSendFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("senderId") Integer senderId,
            @RequestParam("receiverId") Integer receiverId) throws IOException {
        
        // ✅ SÉCURITÉ : On vérifie que le fichier n'est pas vide avant de continuer.
        if (file == null || file.isEmpty()) {
            // Renvoie une erreur 400 (Bad Request) claire au lieu d'un 500.
            return ResponseEntity.badRequest().build();
        }
        
        chatMessageService.saveAndSendFileMessage(file, senderId, receiverId);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/chat/messages/{id}/file")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Integer id) {
        ChatMessage fileMessage = chatMessageService.getFileMessage(id);
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileMessage.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileMessage.getFileName() + "\"")
                .body(fileMessage.getFileContent());
    }
}