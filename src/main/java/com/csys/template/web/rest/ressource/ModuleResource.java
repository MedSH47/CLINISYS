package com.csys.template.web.rest.ressource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.csys.template.dtoRequest.ModuleRequestDTO;
import com.csys.template.dtoResponse.ModuleResponseDTO;
import com.csys.template.service.ModuleService;
import com.csys.template.util.RestPreconditions;

@RestController
@RequestMapping("/api")
public class ModuleResource {
    private final Logger log = LoggerFactory.getLogger(ModuleResource.class);
    private final ModuleService moduleService;

    public ModuleResource(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @PostMapping("/modules")
    public ResponseEntity<ModuleResponseDTO> createModule(@Valid @RequestBody ModuleRequestDTO moduleRequestDTO) throws URISyntaxException {
        log.debug("REST request to save Module : {}", moduleRequestDTO);
        ModuleResponseDTO result = moduleService.save(moduleRequestDTO);
        return ResponseEntity.created(new URI("/api/modules/" + result.getId())).body(result);
    }

    @PutMapping("/modules/{id}")
    public ResponseEntity<ModuleResponseDTO> updateModule(@PathVariable Integer id, @Valid @RequestBody ModuleRequestDTO moduleRequestDTO) {
        log.debug("Request to update Module: {}", id);
        ModuleResponseDTO result = moduleService.update(id, moduleRequestDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/modules/{id}")
    public ResponseEntity<ModuleResponseDTO> getModule(@PathVariable Integer id) {
        log.debug("Request to get Module: {}", id);
        ModuleResponseDTO dto = moduleService.findOne(id);
        RestPreconditions.checkFound(dto, "module.NotFound");
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/modules")
    public Collection<ModuleResponseDTO> getAllModules(@RequestParam(required = false)List<Integer> equipes,
                                                       @RequestParam(required = false) Boolean[] actifs) {
        log.debug("Request to get all Modules");
        return moduleService.findAll(equipes, actifs);
    }

    @DeleteMapping("/modules/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable Integer id) {
        log.debug("Request to delete Module: {}", id);
        moduleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}