package io.github.qrman.potato;

import io.vertx.core.json.JsonObject;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

    private Properties properties = new Properties();

    public void read() {
        String propertiesFileName = "test.properties";
        try (InputStream is = PropertiesReader.class.getResourceAsStream(propertiesFileName)) {
            properties.load(is);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public JsonObject getAsJson() {
        JsonObject propertiesAsJson = new JsonObject();
        properties.forEach((Object key, Object value) -> {
            propertiesAsJson.put((String) key, (String) value);
        });
        return propertiesAsJson;
    }

    public Properties getAsProperties() {
        return properties;
    }
}
