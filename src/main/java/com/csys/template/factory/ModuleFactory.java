package com.csys.template.factory;

import com.csys.template.domain.Module;
import com.csys.template.dto.ModuleDto;

import java.util.ArrayList;
import java.util.List;

public class ModuleFactory {

    public static ModuleDto moduleToModuleDto(Module module) {
        if (module == null) {
            return null;
        }
        ModuleDto dto = new ModuleDto();
        dto.setName(module.getName());
        return dto;
    }

    public static Module moduleDtoToModule(ModuleDto dto) {
        Module module = new Module();
        module.setName(dto.getName());
        return module;
    }

    public static List<ModuleDto> moduleToModuleDtos(List<Module> modules) {
        List<ModuleDto> list = new ArrayList<>();
        for (Module module : modules) {
            list.add(moduleToModuleDto(module));
        }
        return list;
    }
}
