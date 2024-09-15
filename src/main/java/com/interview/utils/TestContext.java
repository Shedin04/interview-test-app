package com.interview.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
@UtilityClass
public class TestContext {

    private static final ThreadLocal<HashMap<String, Object>> data = ThreadLocal.withInitial(HashMap::new);

    public static void saveSharedParameter(String key, Object value) {
        if (key == null) {
            log.warn("Key for saving shared parameter is null");
            return;
        }
        data.get().put(key, value);
    }

    public static Object getSharedParameter(String key) {
        Object value = data.get().get(key);
        if (value == null) {
            log.warn("There is no parameter with '{}' key", key);
        }
        return value;
    }

    public static void unloadTestData() {
        data.remove();
        log.info("TestContext data was removed");
    }
}
