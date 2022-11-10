package de.throsenheim.unlimited.stockeasilyapi.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ConfigServiceImpl implements ConfigService {

    private final Environment environment;

    @Autowired
    public ConfigServiceImpl(Environment environment) {
        this.environment = environment;
    }

    public static String getConfig(String key, Environment environment) {
        String result = null;
        String keyValue = environment.getProperty(key);

        if (keyValue != null && !keyValue.isEmpty()) {
            result = keyValue;
        }
        return result;
    }

    @Override
    public String getDbConfig(String key, Environment environment) {
        return getConfig("database." + key, environment);
    }

    public String getDbConfig(String key) {
        return getDbConfig(key, environment);
    }

}
