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
    commentaireDTO.setDateCommentaire(commentaire.getDateCommentaire());
    commentaireDTO.setIdTicket(commentaire.getIdTicket());
    return commentaireDTO;
  }

  public static Commentaire commentaireDTOToCommentaire(CommentaireDTO commentaireDTO) {
    Commentaire commentaire=new Commentaire();
    commentaire.setId(commentaireDTO.getId());
    commentaire.setCommentaire(commentaireDTO.getCommentaire());
    commentaire.setDateCommentaire(commentaireDTO.getDateCommentaire());
    commentaire.setIdTicket(commentaireDTO.getIdTicket());
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

