package com.csys.template.service;

import com.csys.template.domain.Poste;
import com.csys.template.domain.QPoste;
import com.csys.template.dtoRequest.PosteRequestDTO;
import com.csys.template.dtoResponse.PosteResponseDTO;
import com.csys.template.factory.PosteFactory;
import com.csys.template.repository.PosteRepository;
import com.csys.template.util.WhereClauseBuilder;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PosteService {
  private final Logger log = LoggerFactory.getLogger(PosteService.class);
  private final PosteRepository posteRepository;

  public PosteService(PosteRepository posteRepository) {
    this.posteRepository = posteRepository;
  }

  public PosteResponseDTO save(PosteRequestDTO posteRequestDTO) {
    log.debug("Request to save Poste: {}", posteRequestDTO);
    Poste poste = PosteFactory.toEntity(posteRequestDTO);
    poste = posteRepository.save(poste);
    return PosteFactory.toResponseDTO(poste);
  }

  public PosteResponseDTO update(Integer posteId, PosteRequestDTO posteRequestDTO) {
    log.debug("Request to update Poste: {}", posteId);
    Poste existing = posteRepository.findById(posteId)
        .orElseThrow(() -> new IllegalArgumentException("poste.NotFound"));
    
    existing.setDesignation(posteRequestDTO.getDesignation());
    existing.setActif(posteRequestDTO.isActif());

    Poste saved = posteRepository.save(existing);
    return PosteFactory.toResponseDTO(saved);
  }

  @Transactional(readOnly = true)
  public PosteResponseDTO findOne(Integer id) {
    log.debug("Request to get Poste: {}", id);
    Poste poste = posteRepository.findById(id).orElse(null);
    return PosteFactory.toResponseDTO(poste);
  }
  
  @Transactional(readOnly = true)
  public List<PosteResponseDTO> findAll(Boolean[] actifs) {
    log.debug("Request to get All Postes");
    QPoste qPoste = QPoste.poste;
    WhereClauseBuilder builder = new WhereClauseBuilder()
        .optionalAnd(actifs, () -> qPoste.actif.in(actifs));
    List<Poste> result = (List<Poste>) posteRepository.findAll(builder);
    return PosteFactory.toResponseDTOs(result);
  }

  public void delete(Integer id) {
    log.debug("Request to delete Poste: {}", id);
    posteRepository.deleteById(id);
  }
}