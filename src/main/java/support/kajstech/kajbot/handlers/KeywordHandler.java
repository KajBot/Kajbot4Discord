package support.kajstech.kajbot.handlers;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

public class KeywordHandler {

    public static final Map<String, String> kws = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private static Properties keywords = new Properties();
    private static File kwPath = new File(System.getProperty("user.dir") + "\\keywords.properties");

    public static void init() {
        if (!kwPath.exists()) {
            saveKeywords();
        }

        try {
            keywords.load(new BufferedReader(new InputStreamReader(new FileInputStream(kwPath), StandardCharsets.UTF_8)));
            for (final String property : keywords.stringPropertyNames()) {
                kws.put(property, keywords.getProperty(property));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveKeywords() {
        try {
            keywords.store(new OutputStreamWriter(new FileOutputStream(kwPath), StandardCharsets.UTF_8), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties getKeywords() {
        return keywords;
    }

    public static void addKeyword(String key, String value) {
        keywords.setProperty(key, value);
        kws.put(key, value);
        saveKeywords();
    }

    public static void removeKeyword(String key) {
        kws.remove(key);
        keywords.remove(key);
        saveKeywords();
    }
}
