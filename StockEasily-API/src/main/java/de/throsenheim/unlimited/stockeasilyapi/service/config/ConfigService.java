package de.throsenheim.unlimited.stockeasilyapi.service.config;

import org.springframework.core.env.Environment;

public interface ConfigService {

    String getDbConfig(String key);

    String getDbConfig(String key, Environment environment);

}
