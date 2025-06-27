    package com.csys.template.service;

    import com.csys.template.domain.Commentaire;
    import com.csys.template.dtoRequest.CommentaireRequestDTO;
    import com.csys.template.dtoResponse.CommentaireResponseDTO;
    import com.csys.template.factory.CommentaireFactory;
    import com.csys.template.repository.CommentaireRepository;
    import java.util.List;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    @Service
    @Transactional
    public class CommentaireService {
        private final Logger log = LoggerFactory.getLogger(CommentaireService.class);
        private final CommentaireRepository commentaireRepository;

        public CommentaireService(CommentaireRepository commentaireRepository) {
            this.commentaireRepository = commentaireRepository;
        }

        public CommentaireResponseDTO save(CommentaireRequestDTO commentaireRequestDTO) {
            log.debug("Request to save Commentaire : {}", commentaireRequestDTO);
            Commentaire commentaire = CommentaireFactory.toEntity(commentaireRequestDTO);
            commentaire = commentaireRepository.save(commentaire);
            return CommentaireFactory.toResponseDTO(commentaire);
        }

        public CommentaireResponseDTO update(Integer commentaireId, CommentaireRequestDTO commentaireRequestDTO) {
            log.debug("Request to update Commentaire : {}", commentaireId);
            Commentaire existingCommentaire = commentaireRepository.findById(commentaireId)
                    .orElseThrow(() -> new IllegalArgumentException("commentaire.NotFound"));
            
            existingCommentaire.setCommentaire(commentaireRequestDTO.getCommentaire());
            
            Commentaire saved = commentaireRepository.save(existingCommentaire);
            return CommentaireFactory.toResponseDTO(saved);
        }

        @Transactional(readOnly = true)
        public CommentaireResponseDTO findOne(Integer id) {
            log.debug("Request to get Commentaire : {}", id);
            Commentaire commentaire = commentaireRepository.findById(id).orElse(null);
            return CommentaireFactory.toResponseDTO(commentaire);
        }

        @Transactional(readOnly = true)
        public List<CommentaireResponseDTO> findAll() {
            log.debug("Request to get All Commentaires");
            List<Commentaire> result = commentaireRepository.findAll();
            return CommentaireFactory.toResponseDTOs(result);
        }

        public void delete(Integer id) {
            log.debug("Request to delete Commentaire: {}", id);
            commentaireRepository.deleteById(id);
        }
    }