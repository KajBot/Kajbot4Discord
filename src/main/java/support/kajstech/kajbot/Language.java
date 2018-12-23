package support.kajstech.kajbot;


import support.kajstech.kajbot.handlers.ConfigHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Properties;

public class Language {

    private static Properties messages = new Properties();
    private static File langPath = new File(System.getProperty("user.dir") + "\\" + ConfigHandler.getProperty("Language") + ".properties");

    static void init() {
        try {
            messages.load(new BufferedReader(new InputStreamReader(new FileInputStream(langPath), StandardCharsets.UTF_8)));
        } catch (IOException e) {
            try {
                messages.load(new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream("en_US.properties"), StandardCharsets.UTF_8)));
                Files.copy(ClassLoader.getSystemResourceAsStream("en_US.properties"), langPath.getAbsoluteFile().toPath());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static String getMessage(String key) {
        return messages.getProperty(key);
    }
}
