package com.interview.base;

import com.interview.config.ConfigurationCollector;
import com.interview.listener.GeneralTestListener;
import com.interview.utils.TestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

@Listeners({GeneralTestListener.class})
public class BaseTest {

    @BeforeSuite(alwaysRun = true)
    public void loadAllProperties() {
        ConfigurationCollector.loadAllProperties();
    }

    @AfterClass(alwaysRun = true)
    public void unloadTestData() {
        TestContext.unloadTestData();
    }
}
