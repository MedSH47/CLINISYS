package com.csys.template.service;

import com.csys.template.domain.Poste;
import com.csys.template.dto.PosteDTO;
import com.csys.template.factory.PosteFactory;
import com.csys.template.repository.PosteRepository;
import com.google.common.base.Preconditions;
import java.lang.Integer;
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
    this.posteRepository=posteRepository;
  }

  public PosteDTO save(PosteDTO posteDTO) {
    log.debug("Request to save Poste: {}",posteDTO);
    Poste poste = PosteFactory.posteDTOToPoste(posteDTO);
    poste = posteRepository.save(poste);
    PosteDTO resultDTO = PosteFactory.posteToPosteDTO(poste);
    return resultDTO;
  }

  public PosteDTO update(PosteDTO posteDTO) {
    log.debug("Request to update Poste: {}",posteDTO);
    Poste inBase= posteRepository.findById(posteDTO.getId()).orElse(null);
    Preconditions.checkArgument(inBase != null, "poste.NotFound");
    Poste poste = PosteFactory.posteDTOToPoste(posteDTO);
    poste = posteRepository.save(poste);
    PosteDTO resultDTO = PosteFactory.posteToPosteDTO(poste);
    return resultDTO;
  }

  @Transactional(
      readOnly = true
  )
  public PosteDTO findOne(Integer id) {
    log.debug("Request to get Poste: {}",id);
    Poste poste= posteRepository.findById(id).orElse(null);
    PosteDTO dto = PosteFactory.posteToPosteDTO(poste);
    return dto;
  }

 
  @Transactional(
      readOnly = true
  )
  public Poste findPoste(Integer id) {
    log.debug("Request to get Poste: {}",id);
    Poste poste= posteRepository.findById(id).orElse(null);
    return poste;
  }

  
  @Transactional(
      readOnly = true
  )
  public List<PosteDTO> findAll() {
    log.debug("Request to get All Postes");
    List<Poste> result= posteRepository.findAll();
    return PosteFactory.posteToPosteDTOs(result);
  }

 
  public void delete(Integer id) {
    log.debug("Request to delete Poste: {}",id);
    posteRepository.deleteById(id);
  }
}

