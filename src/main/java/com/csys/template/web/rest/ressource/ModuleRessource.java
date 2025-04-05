package com.csys.template.web.rest.ressource;

import com.csys.template.domain.Module;
import com.csys.template.dto.ModuleDto;
import com.csys.template.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/Module")
public class ModuleRessource {

    @Autowired
    private ModuleService moduleService;
    private static final String ENTITY_NAME = "Module";

    @PostMapping
    public ResponseEntity<Module> addModule(@RequestBody Module entity, BindingResult bindingResult) throws URISyntaxException, MethodArgumentNotValidException {
        if (entity.getId() != null) {
            bindingResult.addError(new FieldError(ENTITY_NAME, "Id", "Post not allowed Module with Id"));
            throw new MethodArgumentNotValidException(null, bindingResult);
        }
        Module module = moduleService.addModule(entity);
        return ResponseEntity.created(new URI("/api/Module/" + module.getId())).body(module);
    }

    @GetMapping
    public List<Module> getAllModules() {
        return moduleService.getAllModules();
    }

    @PutMapping
    public ResponseEntity<Module> updateModule(@RequestBody Module entity) throws URISyntaxException {
        Module module = moduleService.updateModule(entity);
        return ResponseEntity.ok(module);
    }

    @GetMapping("/{id}")
    public ModuleDto findOne(@PathVariable Integer id) {
        return moduleService.findOne(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable Integer id) {
        moduleService.deleteModule(id);
        return ResponseEntity.ok().build();
    }
}
