package io.github.qrman.potato;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import java.util.Properties;

public class GuiceModuleForTests extends AbstractModule {
    
    private Properties properties;

    public GuiceModuleForTests(Properties properties) {
        this.properties = properties;
    }

    @Override
    protected void configure() {
        Names.bindProperties(binder(), properties);
    }
}
