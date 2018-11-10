package support.kajstech.kajbot.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

public class KeywordManager {

    public static Map<String, String> kws = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private static Properties keywords = new Properties();
    private static File kwFile = new File("keywords.xml");

    public static void init() {
        if (!kwFile.exists()) {
            try {
                keywords.storeToXML(new FileOutputStream(kwFile), null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            keywords.loadFromXML(new FileInputStream(kwFile));
            for (final String property : keywords.stringPropertyNames()) {
                kws.put(property, keywords.getProperty(property));
            }
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
        try {
            keywords.storeToXML(new FileOutputStream(kwFile), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getKeyword(String key) {
        key = keywords.getProperty(key);
        return key;
    }

    public static void removeKeyword(String key) {
        kws.remove(key);
        keywords.remove(key);
        try {
            keywords.storeToXML(new FileOutputStream(kwFile), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
