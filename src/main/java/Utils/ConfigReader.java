package Utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private Properties properties;
    private static ConfigReader configReader;
    private static final Logger log = LogManager.getLogger(ConfigReader.class);

    private ConfigReader() {
        properties = new Properties();
        loadProperties();
    }

    public static ConfigReader getInstance() {
        if (configReader == null) {
            configReader = new ConfigReader();
        }
        return configReader;
    }

    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                log.error("Sorry, unable to find config.properties");
                return;
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getBaseUri() {
        return getProperty("base.uri");
    }

    public String getBasePath() {
        return getProperty("base.path");
    }

    public int getTimeout() {
        return Integer.parseInt(getProperty("timeout"));
    }

    public String getApiKey() {
        return getProperty("api.key");
    }
}