// Snippet for assumed ModuleFactory structure
package com.csys.template.factory;

import com.csys.template.domain.Module;
import com.csys.template.dto.ModuleDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class ModuleFactory {
    public static ModuleDTO moduleToModuleDTO(Module module) {
        if (module == null) return null;
        ModuleDTO moduleDTO = new ModuleDTO();
        moduleDTO.setId(module.getId());
        moduleDTO.setDesignation(module.getDesignation());
        // map other fields...
        return moduleDTO;
    }

    public static Module moduleDTOToModule(ModuleDTO moduleDTO) {
        if (moduleDTO == null) return null;
        Module module = new Module();
        module.setId(moduleDTO.getId());
        module.setDesignation(moduleDTO.getDesignation());
        // map other fields...
        return module;
    }
    
    public static Collection<ModuleDTO> moduleToModuleDTOs(Collection<Module> modules) {
        if (modules == null) {
            return new ArrayList<>();
        }
        return modules.stream()
                      .map(ModuleFactory::moduleToModuleDTO)
                      .collect(Collectors.toList());
    }
    
    public static Collection<Module> moduleDTOToModules(Collection<ModuleDTO> moduleDTOs) {
        if (moduleDTOs == null) {
            return new ArrayList<>();
        }
        return moduleDTOs.stream()
                         .map(ModuleFactory::moduleDTOToModule)
                         .collect(Collectors.toList());
    }
}