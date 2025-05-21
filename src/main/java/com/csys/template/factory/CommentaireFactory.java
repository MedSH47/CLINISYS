package com.csys.template.factory;

import com.csys.template.domain.Commentaire;
import com.csys.template.dto.CommentaireDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CommentaireFactory {
  public static CommentaireDTO commentaireToCommentaireDTO(Commentaire commentaire) {
    CommentaireDTO commentaireDTO=new CommentaireDTO();
    commentaireDTO.setId(commentaire.getId());
    commentaireDTO.setCommentaire(commentaire.getCommentaire());
    commentaireDTO.setIdTicket(commentaire.getIdTicket());
    commentaireDTO.setIdAvancement(commentaire.getIdAvancement());
    return commentaireDTO;
  }

  public static Commentaire commentaireDTOToCommentaire(CommentaireDTO commentaireDTO) {
    Commentaire commentaire=new Commentaire();
    commentaire.setId(commentaireDTO.getId());
    commentaire.setCommentaire(commentaireDTO.getCommentaire());
    commentaire.setIdTicket(commentaireDTO.getIdTicket());
    commentaire.setIdAvancement(commentaireDTO.getIdAvancement());
    return commentaire;
  }

  public static Collection<CommentaireDTO> commentaireToCommentaireDTOs(Collection<Commentaire> commentaires) {
    List<CommentaireDTO> commentairesDTO=new ArrayList<>();
    commentaires.forEach(x -> {
      commentairesDTO.add(commentaireToCommentaireDTO(x));
    } );
    return commentairesDTO;
  }
}

