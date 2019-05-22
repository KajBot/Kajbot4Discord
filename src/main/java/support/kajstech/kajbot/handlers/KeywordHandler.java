package support.kajstech.kajbot.handlers;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class KeywordHandler {

    public static final Map<String, String> kws = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private static Properties keywords = new Properties();
    private static File kwPath = new File(System.getProperty("user.dir") + "/keywords.properties");

    static {
        if (!kwPath.exists()) {
            saveKeywords();
        }

        try {
            keywords.load(new BufferedReader(new InputStreamReader(new FileInputStream(kwPath), StandardCharsets.UTF_8)));
            keywords.stringPropertyNames().forEach((k) -> kws.put(k, keywords.getProperty(k)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                saveKeywords();
            }
        }, 60000);
    }

    public static void saveKeywords() {
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
    }

    public static void removeKeyword(String key) {
        kws.remove(key);
        keywords.remove(key);
    }
}
