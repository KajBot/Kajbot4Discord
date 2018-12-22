package support.kajstech.kajbot.handlers;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class ConfigHandler {
    private static Properties config = new Properties();

    private static File cfgPath = new File(System.getProperty("user.dir") + "\\config.properties");

    public static void loadCfg() {
        try {
            config.load(new BufferedReader(new InputStreamReader(new FileInputStream(cfgPath), StandardCharsets.UTF_8)));
        } catch (IOException e) {
            try {
                InputStream in = ClassLoader.getSystemResourceAsStream(cfgPath.getName());
                byte[] buffer = new byte[in.available()];
                in.read(buffer);
                new FileOutputStream(cfgPath).write(buffer);
                config.load(new BufferedReader(new InputStreamReader(new FileInputStream(cfgPath), StandardCharsets.UTF_8)));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void storeCfg() {
        try {
            config.store(new FileOutputStream(cfgPath), null);
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
