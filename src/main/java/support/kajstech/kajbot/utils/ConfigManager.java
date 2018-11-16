package support.kajstech.kajbot.utils;

import java.io.*;
import java.util.Properties;

public class ConfigManager {
    private static Properties config = new Properties();
    private static File cfgFile = new File("config.xml");

    public static void init() {
        try {
            config.loadFromXML(new FileInputStream(cfgFile));
        } catch (IOException e) {
            try {
                InputStream in = ClassLoader.getSystemResourceAsStream("config.xml");
                byte[] buffer = new byte[in.available()];
                OutputStream out = new FileOutputStream(cfgFile);
                in.read(buffer);
                out.write(buffer);
                config.load(new FileInputStream(cfgFile));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
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
}
