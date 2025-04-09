package com.csys.template.web.rest;

import com.csys.template.dto.ModuleDTO;
import com.csys.template.service.ModuleService;
import java.lang.Integer;
import java.lang.String;
import java.lang.Void;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing Module.
 */
@RestController
@RequestMapping("/api")
public class ModuleResource {
  private static final String ENTITY_NAME = "module";

  private final ModuleService moduleService;

  private final Logger log = LoggerFactory.getLogger(ModuleService.class);

  public ModuleResource(ModuleService moduleService) {
    this.moduleService=moduleService;
  }

  /**
   * POST  /modules : Create a new module.
   *
   * @param moduleDTO
   * @param bindingResult
   * @return the ResponseEntity with status 201 (Created) and with body the new module, or with status 400 (Bad Request) if the module has already an ID
   * @throws URISyntaxException if the Location URI syntax is incorrect
   * @throws org.springframework.web.bind.MethodArgumentNotValidException
   */
  @PostMapping("/modules")
  public ResponseEntity<ModuleDTO> createModule(@Valid @RequestBody ModuleDTO moduleDTO, BindingResult bindingResult) throws URISyntaxException, MethodArgumentNotValidException {
    log.debug("REST request to save Module : {}", moduleDTO);
    if ( moduleDTO.getId() != null) {
      bindingResult.addError( new FieldError("ModuleDTO","id","POST method does not accepte "+ENTITY_NAME+" with code"));
      throw new MethodArgumentNotValidException(null, bindingResult);
    }
    if (bindingResult.hasErrors()) {
      throw new MethodArgumentNotValidException(null, bindingResult);
    }
    ModuleDTO result = moduleService.save(moduleDTO);
    return ResponseEntity.created( new URI("/api/modules/"+ result.getId())).body(result);
  }

  /**
   * PUT  /modules : Updates an existing module.
   *
   * @param id
   * @param moduleDTO the module to update
   * @return the ResponseEntity with status 200 (OK) and with body the updated module,
   * or with status 400 (Bad Request) if the module is not valid,
   * or with status 500 (Internal Server Error) if the module couldn't be updated
   * @throws org.springframework.web.bind.MethodArgumentNotValidException
   */
  @PutMapping("/modules/{id}")
  public ResponseEntity<ModuleDTO> updateModule(@PathVariable Integer id, @Valid @RequestBody ModuleDTO moduleDTO) throws MethodArgumentNotValidException {
    log.debug("Request to update Module: {}",id);
    moduleDTO.setId(id);
    ModuleDTO result =moduleService.update(moduleDTO);
    return ResponseEntity.ok().body(result);
  }

  /**
   * GET /modules/{id} : get the "id" module.
   *
   * @param id the id of the module to retrieve
   * @return the ResponseEntity with status 200 (OK) and with body of module, or with status 404 (Not Found)
   */
  @GetMapping("/modules/{id}")
  public ResponseEntity<ModuleDTO> getModule(@PathVariable Integer id) {
    log.debug("Request to get Module: {}",id);
    ModuleDTO dto = moduleService.findOne(id);
    RestPreconditions.checkFound(dto, "module.NotFound");
    return ResponseEntity.ok().body(dto);
  }

  /**
   * GET /modules : get all the modules.
   *
   * @return the ResponseEntity with status 200 (OK) and the list of modules in body
   */
  @GetMapping("/modules")
  public Collection<ModuleDTO> getAllModules() {
    log.debug("Request to get all  Modules : {}");
    return moduleService.findAll();
  }

  /**
   * DELETE  /modules/{id} : delete the "id" module.
   *
   * @param id the id of the module to delete
   * @return the ResponseEntity with status 200 (OK)
   */
  @DeleteMapping("/modules/{id}")
  public ResponseEntity<Void> deleteModule(@PathVariable Integer id) {
    log.debug("Request to delete Module: {}",id);
    moduleService.delete(id);
    return ResponseEntity.ok().build();
  }
}

