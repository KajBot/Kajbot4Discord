package dk.jensbot.kajbot4discord.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class Language {

    public static Properties lang = new Properties();

    static {
        try {
            lang.load(new BufferedReader(new InputStreamReader(new URL("https://raw.githubusercontent.com/KajBot/Kajbot4Discord-Lang/master/" + Config.cfg.get("Language") + ".properties").openStream(), StandardCharsets.UTF_8)));
        } catch (IOException ignored) {
            try {
                lang.load(ClassLoader.getSystemResourceAsStream("en_US.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}