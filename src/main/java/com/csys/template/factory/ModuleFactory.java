package com.csys.template.factory;

import com.csys.template.domain.Module;
import com.csys.template.dto.ModuleDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ModuleFactory {
  public static ModuleDTO moduleToModuleDTO(Module module) {
    ModuleDTO moduleDTO=new ModuleDTO();
    moduleDTO.setId(module.getId());
    moduleDTO.setDesignation(module.getDesignation());
    moduleDTO.setCreationDate(module.getCreationDate());
    moduleDTO.setCreationUser(module.getCreationUser());
    moduleDTO.setCode(module.getCode());
    moduleDTO.setTicketList(module.getTicketList());
    return moduleDTO;
  }

  @SuppressWarnings("unchecked")
  public static Module moduleDTOToModule(ModuleDTO moduleDTO) {
    Module module=new Module();
    module.setId(moduleDTO.getId());
    module.setDesignation(moduleDTO.getDesignation());
    module.setCreationDate(moduleDTO.getCreationDate());
    module.setCreationUser(moduleDTO.getCreationUser());
    module.setCode(moduleDTO.getCode());
    module.setTicketList(moduleDTO.getTicketList());
    return module;
  }

  public static Collection<ModuleDTO> moduleToModuleDTOs(Collection<Module> modules) {
    List<ModuleDTO> modulesDTO=new ArrayList<>();
    modules.forEach(x -> {
      modulesDTO.add(moduleToModuleDTO(x));
    } );
    return modulesDTO;
  }
}

