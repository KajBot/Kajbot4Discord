package support.kajstech.kajbot;


import support.kajstech.kajbot.handlers.ConfigHandler;

import java.io.*;
import java.util.Properties;

public class Language {

    public static Properties messages = new Properties();
    private static File file = new File(ConfigHandler.getProperty("Language") + ".properties");

    static void init() {

        try {
            messages.load(new FileInputStream(file));
        } catch (IOException e) {
            try {
                InputStream in = Language.class.getResourceAsStream("en_US.properties");
                byte[] buffer = new byte[in.available()];
                OutputStream out = new FileOutputStream(file);
                in.read(buffer);
                out.write(buffer);
                messages.load(new FileInputStream(file));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
