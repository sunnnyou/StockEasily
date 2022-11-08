package de.throsenheim.unlimited.stockeasilyapi.service.config;

import org.springframework.core.env.Environment;

public interface IConfigService {

    String getDbConfig(String key);

    String getDbConfig(String key, Environment environment);

}
