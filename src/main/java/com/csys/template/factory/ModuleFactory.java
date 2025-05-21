package com.csys.template.factory;

import com.csys.template.domain.Module;
import com.csys.template.domain.Ticket;
import com.csys.template.dto.ModuleDTO;
import com.csys.template.dto.TicketDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ModuleFactory {
  public static ModuleDTO moduleToModuleDTO(Module module) {
    ModuleDTO moduleDTO=new ModuleDTO();
    moduleDTO.setId(module.getId());
    moduleDTO.setDesignation(module.getDesignation());
    Collection<TicketDTO> ticketCollectionDtos = new ArrayList<>();
    module.getTicketCollection().forEach(x -> {
      TicketDTO ticketDto = new TicketDTO();
      ticketDto = TicketFactory.ticketToTicketDTO(x);
      ticketCollectionDtos.add(ticketDto);
    } );
    if(moduleDTO.getTicketCollection() !=null) {
      moduleDTO.getTicketCollection().clear();
      moduleDTO.getTicketCollection().addAll(ticketCollectionDtos);
    }
    else {
      moduleDTO.setTicketCollection(ticketCollectionDtos);
    }
    moduleDTO.setIdEquipe(module.getIdEquipe());
    return moduleDTO;
  }

  public static Module moduleDTOToModule(ModuleDTO moduleDTO) {
    Module module=new Module();
    module.setId(moduleDTO.getId());
    module.setDesignation(moduleDTO.getDesignation());
    Collection<Ticket> ticketCollections = new ArrayList<>();
    moduleDTO.getTicketCollection().forEach(x -> {
      Ticket ticket = new Ticket();
      ticket = TicketFactory.ticketDTOToTicket(x);
      ticketCollections.add(ticket);
    } );
    if(module.getTicketCollection() !=null) {
      module.getTicketCollection().clear();
      module.getTicketCollection().addAll(ticketCollections);
    }
    else {
      module.setTicketCollection(ticketCollections);
    }
    module.setIdEquipe(moduleDTO.getIdEquipe());
    return module;
  }

  public static Collection<ModuleDTO> moduleToModuleDTOs(Collection<Module> modules) {
    List<ModuleDTO> modulesDTO=new ArrayList<>();
    modules.forEach(x -> {
      modulesDTO.add(moduleToModuleDTO(x));
    } );
    return modulesDTO;
  }

  public static ModuleDTO lazymoduleToModuleDTO(Module module) {
    ModuleDTO moduleDTO=new ModuleDTO();
    moduleDTO.setId(module.getId());
    moduleDTO.setDesignation(module.getDesignation());
    moduleDTO.setIdEquipe(module.getIdEquipe());
    return moduleDTO;
  }

  public static Collection<ModuleDTO> lazymoduleToModuleDTOs(Collection<Module> modules) {
    List<ModuleDTO> modulesDTO=new ArrayList<>();
    modules.forEach(x -> {
      modulesDTO.add(lazymoduleToModuleDTO(x));
    } );
    return modulesDTO;
  }
}

