package com.csys.template.log.listener;

import com.csys.template.log.service.LogService;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class EntityLogger implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        EntityLogger.context = applicationContext;
    }

    private LogService getLogService() {
        return context.getBean(LogService.class);
    }

    @PostPersist
    public void postPersist(Object entity) {
        getLogService().logAudit("CREATE", entity);
    }

    @PostUpdate
    public void postUpdate(Object entity) {
        getLogService().logAudit("UPDATE", entity);
    }

    @PostRemove
    public void postRemove(Object entity) {
        getLogService().logAudit("DELETE", entity);
    }
    
}