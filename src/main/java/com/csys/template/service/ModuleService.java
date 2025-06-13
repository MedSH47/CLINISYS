package com.csys.template.service;

import com.csys.template.domain.Equipe;
import com.csys.template.domain.Module;
import com.csys.template.domain.QEquipe;
import com.csys.template.dtoRequest.ModuleRequestDTO;
import com.csys.template.dtoResponse.ModuleResponseDTO;
import com.csys.template.factory.ModuleFactory;
import com.csys.template.repository.EquipeRepository;
import com.csys.template.repository.ModuleRepository;
import com.csys.template.util.WhereClauseBuilder;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ModuleService {
  private final Logger log = LoggerFactory.getLogger(ModuleService.class);
  private final ModuleRepository moduleRepository;
  private final EquipeRepository equipeRepository;

  public ModuleService(ModuleRepository moduleRepository, EquipeRepository equipeRepository) {
    this.moduleRepository = moduleRepository;
    this.equipeRepository = equipeRepository;
  }

  public ModuleResponseDTO save(ModuleRequestDTO moduleRequestDTO) {
    log.debug("Request to save Module: {}", moduleRequestDTO);
    Module module = ModuleFactory.toEntity(moduleRequestDTO);
    if(module.getEquipe()!=null){
        equipeRepository.findById(module.getEquipe().getId())
                    .orElseThrow(() -> new IllegalArgumentException("equipe.NotFound"));
    }
    module = moduleRepository.save(module);
    return ModuleFactory.toResponseDTO(module);
  }

  public ModuleResponseDTO update(Integer moduleId, ModuleRequestDTO moduleRequestDTO) {
    log.debug("Request to update Module: {}", moduleId);
    Module existing = moduleRepository.findById(moduleId)
        .orElseThrow(() -> new IllegalArgumentException("module.NotFound"));

    Equipe newEquipe = equipeRepository.findById(moduleRequestDTO.getIdEquipe())
        .orElseThrow(() -> new IllegalArgumentException("equipe.NotFound"));

    existing.setDesignation(moduleRequestDTO.getDesignation());
    existing.setEquipe(newEquipe);

    Module saved = moduleRepository.save(existing);
    return ModuleFactory.toResponseDTO(saved);
  }

  @Transactional(readOnly = true)
  public ModuleResponseDTO findOne(Integer id) {
    log.debug("Request to get Module: {}", id);
    Module module = moduleRepository.findById(id).orElse(null);
    return ModuleFactory.toResponseDTO(module);
  }

  @Transactional(readOnly = true)
  public Collection<ModuleResponseDTO> findAll(List<Integer> equipeIds) {
    QEquipe qEquipe = QEquipe.equipe;
    WhereClauseBuilder builder = new WhereClauseBuilder()
        .optionalAnd(equipeIds, () -> qEquipe.id.in(equipeIds));
    log.debug("Request to get All Modules");
    List<Module> result = (List<Module>) moduleRepository.findAll(builder);
    return ModuleFactory.toResponseDTOs(result);
  }

  public void delete(Integer id) {
    log.debug("Request to delete Module: {}", id);
    moduleRepository.deleteById(id);
  }
}