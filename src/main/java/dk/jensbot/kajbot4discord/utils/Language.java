package dk.jensbot.kajbot4discord.utils;

import dk.jensbot.simplecfg.ConfigFactory;
import dk.jensbot.simplecfg.Format;
import dk.jensbot.simplecfg.SimpleCfg;

import java.io.File;

public class Language {

    private static File langPath = new File(Config.cfg.get("Language"));
    private static File fallbackPath = new File("en_US.properties");

    public static SimpleCfg lang = new ConfigFactory(langPath).format(Format.PROPERTIES).fallback(fallbackPath).build();
}