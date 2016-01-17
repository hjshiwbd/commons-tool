package org.shinomin.commons.web.listener;

import org.apache.commons.lang3.StringUtils;
import org.shinomin.commons.web.service.ContextInitService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class CommonContextInitListener implements ApplicationListener<ContextRefreshedEvent> {
    private String systemConfigServiceCode;
    private String propertiesLoadPath;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext app = event.getApplicationContext();

        if (isRun(app)) {
            ContextInitService service = new ContextInitService(event.getApplicationContext());
            if (!StringUtils.isEmpty(propertiesLoadPath)) {
                service.loadAllProperies(propertiesLoadPath);
            }
            if (!StringUtils.isEmpty(systemConfigServiceCode)) {
                service.loadSystemConfig(systemConfigServiceCode);
            }

        }
    }

    private boolean isRun(ApplicationContext app) {
        return "Root WebApplicationContext".equals(app.getDisplayName())
                || "org.springframework.context.support.GenericApplicationContext".equals(app.getClass().getName());
    }

    public void setPropertiesLoadPath(String propertiesLoadPath) {
        this.propertiesLoadPath = propertiesLoadPath;
    }

    public void setSystemConfigServiceCode(String systemConfigServiceCode) {
        this.systemConfigServiceCode = systemConfigServiceCode;
    }

}
