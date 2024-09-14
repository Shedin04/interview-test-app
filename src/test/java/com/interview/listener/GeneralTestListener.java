package com.interview.listener;

import com.interview.config.ConfigurationCollector;
import lombok.extern.slf4j.Slf4j;
import org.testng.ITestContext;
import org.testng.ITestListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class GeneralTestListener implements ITestListener {

    @Override
    public void onFinish(ITestContext context) {
        final Properties properties = new Properties();
        properties.putAll(getProperties());
        final File file = new File("allure-results/environment.properties");
        if (!file.exists()) {
            try (FileOutputStream out = new FileOutputStream(file)) {
                properties.store(out, "Environment Properties for Allure report");
            } catch (IOException e) {
                log.warn("There was an issue while attaching env properties: {}", e.getMessage());
            }
        }
    }

    private Map<String, String> getProperties() {
        return Map.of(
                "threadCount", ConfigurationCollector.getThreadCount(),
                "properties-group", ConfigurationCollector.getPropertiesGroup());
    }
}
