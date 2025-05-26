package com.csys.template.factory;

import com.csys.template.domain.DocumentJointes;
import com.csys.template.dto.DocumentJointesDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DocumentJointesFactory {
  public static DocumentJointesDTO documentjointesToDocumentJointesDTO(DocumentJointes documentjointes) {
    DocumentJointesDTO documentjointesDTO=new DocumentJointesDTO();
    documentjointesDTO.setId(documentjointes.getId());
    documentjointesDTO.setExtension(documentjointes.getExtension());
    documentjointesDTO.setDocument(documentjointes.getDocument());
    documentjointesDTO.setDateDocument(documentjointes.getDateDocument());
    documentjointesDTO.setNomDocument(documentjointes.getNomDocument());
    documentjointesDTO.setIdTicket(documentjointes.getIdTicket());
    documentjointesDTO.setTicketfichierSet(documentjointes.getTicketfichierSet());
    return documentjointesDTO;
  }

  public static DocumentJointes documentjointesDTOToDocumentJointes(DocumentJointesDTO documentjointesDTO) {
    DocumentJointes documentjointes=new DocumentJointes();
    documentjointes.setId(documentjointesDTO.getId());
    documentjointes.setExtension(documentjointesDTO.getExtension());
    documentjointes.setDocument(documentjointesDTO.getDocument());
    documentjointes.setDateDocument(documentjointesDTO.getDateDocument());
    documentjointes.setNomDocument(documentjointesDTO.getNomDocument());
    documentjointes.setIdTicket(documentjointesDTO.getIdTicket());
    documentjointes.setTicketfichierSet(documentjointesDTO.getTicketfichierSet());
    return documentjointes;
  }

  public static Collection<DocumentJointesDTO> documentjointesToDocumentJointesDTOs(Collection<DocumentJointes> documentjointess) {
    List<DocumentJointesDTO> documentjointessDTO=new ArrayList<>();
    documentjointess.forEach(x -> {
      documentjointessDTO.add(documentjointesToDocumentJointesDTO(x));
    } );
    return documentjointessDTO;
  }
}

