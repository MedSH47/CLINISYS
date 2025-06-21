package com.csys.template.AI_ml;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AiSearchResource {

    private final AiSearchService aiSearchService;

    public AiSearchResource(AiSearchService aiSearchService) {
        this.aiSearchService = aiSearchService;
    }

    @PostMapping("/ai-search")
    public ResponseEntity<AiSearchResponseDTO> naturalLanguageSearch(@RequestBody Map<String, String> payload) {
        String query = payload.get("query");
        if (query == null || query.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        AiSearchResponseDTO response = aiSearchService.performSearch(query);
        return ResponseEntity.ok(response);
    }
}