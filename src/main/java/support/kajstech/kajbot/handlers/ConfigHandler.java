package support.kajstech.kajbot.handlers;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Properties;

public class ConfigHandler {
    private static Properties config = new Properties();

    private static File cfgPath = new File(System.getProperty("user.dir") + "\\config.properties");

    public static void loadCfg() {
        try {
            config.load(new BufferedReader(new InputStreamReader(new FileInputStream(cfgPath), StandardCharsets.UTF_8)));
        } catch (IOException e) {
            try {
                Files.copy(ClassLoader.getSystemResourceAsStream(cfgPath.getName()), cfgPath.getAbsoluteFile().toPath());
                config.load(new BufferedReader(new InputStreamReader(new FileInputStream(cfgPath), StandardCharsets.UTF_8)));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void saveCfg() {
        try {
            config.store(new OutputStreamWriter(new FileOutputStream(cfgPath), StandardCharsets.UTF_8), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setProperty(String key, String value) {
        config.setProperty(key, value);
        saveCfg();
    }

    public static void removeProperty(String key) {
        config.remove(key);
        saveCfg();
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
