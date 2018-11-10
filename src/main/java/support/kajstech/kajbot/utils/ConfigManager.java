package support.kajstech.kajbot.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private static Properties config = new Properties();
    private static File cfgFile = new File("config.xml");

    public static void init() {
        if (!cfgFile.exists()) {
            try {
                config.storeToXML(new FileOutputStream(cfgFile), null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            KajbotLogger.info("Config file missing, generating a new");
        }

        try {
            config.loadFromXML(new FileInputStream(cfgFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void shutdown() {
        try {
            config.storeToXML(new FileOutputStream(cfgFile), null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Properties getConfig() {
        return config;
    }

    public static void setProperty(String key, String value) {
        config.setProperty(key, value);
        try {
            config.storeToXML(new FileOutputStream(cfgFile), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        try {
            config.loadFromXML(new FileInputStream(cfgFile));
            key = config.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return key;
    }

    public static void removeProperty(String key) {
        config.remove(key);
        try {
            config.storeToXML(new FileOutputStream(cfgFile), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
