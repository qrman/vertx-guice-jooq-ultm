package io.github.qrman.potato;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import java.util.Properties;

public class PropertiesModule extends AbstractModule {
    
    private Properties properties;

    public PropertiesModule(Properties properties) {
        this.properties = properties;
    }

    @Override
    protected void configure() {
        Names.bindProperties(binder(), properties);
    }
}
