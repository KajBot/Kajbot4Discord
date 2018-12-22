package support.kajstech.kajbot;


import support.kajstech.kajbot.handlers.ConfigHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class Language {

    public static Properties messages = new Properties();
    private static File langPath = new File(System.getProperty("user.dir") + "\\" + ConfigHandler.getProperty("Language") + ".properties");

    static void init() {
        try {
            messages.load(new BufferedReader(new InputStreamReader(new FileInputStream(langPath), StandardCharsets.UTF_8)));
        } catch (IOException e) {
            try {
                InputStream in = Language.class.getResourceAsStream("en_US.properties");
                byte[] buffer = new byte[in.available()];
                in.read(buffer);
                new FileOutputStream(langPath).write(buffer);
                messages.load(new BufferedReader(new InputStreamReader(new FileInputStream(langPath), StandardCharsets.UTF_8)));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
