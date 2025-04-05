package com.csys.template.service;

import com.csys.template.domain.Module;
import com.csys.template.dto.ModuleDto;
import com.csys.template.factory.ModuleFactory;
import com.csys.template.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleService {

    @Autowired
    private ModuleRepository moduleRepository;

    public Module addModule(Module entity) {
        return moduleRepository.save(entity);
    }

    public List<Module> getAllModules() {
        return moduleRepository.findAll();
    }

    public Module updateModule(Module entity) {
        return moduleRepository.save(entity);
    }

    public ModuleDto findOne(Integer id) {
        Module module = moduleRepository.findOneById(id);
        return ModuleFactory.moduleToModuleDto(module);
    }

    public void deleteModule(Integer id) {
        moduleRepository.deleteById(id);
    }
}
