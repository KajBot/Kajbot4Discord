package support.kajstech.kajbot.handlers;

import java.io.*;
import java.util.Properties;

public class ConfigHandler {
    private static Properties config = new Properties();

    private static File cfgFile = new File("config.xml");

    public static void loadCfg() {

        try {
            config.loadFromXML(new FileInputStream(cfgFile));
        } catch (IOException e) {
            try {
                InputStream in = ClassLoader.getSystemResourceAsStream(cfgFile.getName());
                byte[] buffer = new byte[in.available()];
                in.read(buffer);
                new FileOutputStream(cfgFile).write(buffer);

                config.loadFromXML(new FileInputStream(cfgFile));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void storeCfg() {
        try {
            config.storeToXML(new FileOutputStream(cfgFile), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setProperty(String key, String value) {
        config.setProperty(key, value);
        storeCfg();
    }

    public static String getProperty(String key) {
        return config.getProperty(key);
    }

    public static boolean containsProperty(String key) {
        try {
            return !getProperty(key).isEmpty();
        } catch (Exception ignored) {
            return false;
        }
    }
}
