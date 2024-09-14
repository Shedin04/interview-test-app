package com.interview.config;

import com.interview.exception.AutomationException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import static com.interview.constants.StringConstants.DOT;
import static com.interview.constants.StringConstants.SLASH;

@Slf4j
@UtilityClass
public final class ConfigurationCollector {

    private static final Properties locallySetProperties = new Properties();
    private static final String LOCAL_PROPERTIES_FILE_PATH = SLASH + "local.properties";
    private static final String CONFIGURATION_YAML_FILE_PATH = SLASH + "configuration.yaml";
    private static final String PROPERTIES_GROUP = "properties.group";
    private static final String DEFAULT_YAML_PROPERTIES_GROUP = "remote-swagger";
    private static final String THREAD_COUNT = "threadCount";
    private static final String DEFAULT_THREAD_COUNT = "3";

    public static void loadAllProperties() {
        loadLocalProperties();
        loadSystemProperties();
        loadEnvironmentVariables();
        loadModuleConfigurationYaml();
        log.info("All properties were loaded");
    }

    private static void loadLocalProperties() {
        try (InputStream inputStream = ConfigurationCollector.class.getResourceAsStream(LOCAL_PROPERTIES_FILE_PATH)) {
            if (inputStream != null) {
                locallySetProperties.load(new InputStreamReader(inputStream));
            }
        } catch (IOException e) {
            log.info("Skipping loading local properties");
        }
    }

    private static void loadSystemProperties() {
        locallySetProperties.putAll(System.getProperties());
    }

    private static void loadEnvironmentVariables() {
        System.getenv().forEach(locallySetProperties::setProperty);
    }

    private static void loadModuleConfigurationYaml() {
        final InputStream inputStream = ConfigurationCollector.class.getResourceAsStream(CONFIGURATION_YAML_FILE_PATH);
        if (inputStream != null) {
            loadYamlConfigurationFromStream(inputStream);
        } else {
            log.warn("configuration.yaml is absent for the module");
        }
    }

    private static void loadYamlConfigurationFromStream(InputStream inputStream) {
        Map<String, Object> yamlMap = new Yaml().load(inputStream);
        Map<String, Object> specifiedGroupProperties = (Map<String, Object>) yamlMap.get(getPropertiesGroup());
        if (specifiedGroupProperties == null) {
            throw new AutomationException("Invalid properties group was provided: '%s'!", getPropertiesGroup());
        }
        recursivelyAddYamlProperties(locallySetProperties, specifiedGroupProperties, StringUtils.EMPTY);
    }

    public static String getPropertiesGroup() {
        String propertiesGroup = getProperty(PROPERTIES_GROUP);
        if (propertiesGroup == null) {
            locallySetProperties.setProperty(PROPERTIES_GROUP, DEFAULT_YAML_PROPERTIES_GROUP);
            return DEFAULT_YAML_PROPERTIES_GROUP;
        }
        return propertiesGroup;
    }

    private static void recursivelyAddYamlProperties(Properties properties, Map<String, Object> propertiesMap, String prefix) {
        propertiesMap.forEach((key, value) -> {
            String propertyName = prefix + key;
            if (value instanceof Map) {
                recursivelyAddYamlProperties(properties, (Map<String, Object>) value, propertyName + DOT);
            } else {
                if (!isPropertyPresent(properties, propertyName))
                    properties.setProperty(propertyName, value.toString());
            }
        });
    }

    public static String getProperty(String key) {
        String value = locallySetProperties.getProperty(key);
        if (value == null) {
            log.warn("Property '{}' does not exist.", key);
        }
        return value;
    }

    private static boolean isPropertyPresent(Properties properties, String propertyName) {
        return properties.containsKey(propertyName);
    }

    public static String getThreadCount() {
        return Optional.ofNullable(getProperty(THREAD_COUNT)).orElse(DEFAULT_THREAD_COUNT);
    }
}
