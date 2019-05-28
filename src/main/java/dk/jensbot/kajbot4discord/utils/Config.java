package dk.jensbot.kajbot4discord.utils;

import dk.jensbot.simplecfg.ConfigFactory;
import dk.jensbot.simplecfg.Format;
import dk.jensbot.simplecfg.SimpleCfg;

import java.io.File;

public class Config {

    private static File cfgPath = new File(System.getProperty("user.dir") + "/config.properties");
    private static File fallbackPath = new File("config.properties");

    public static SimpleCfg cfg = new ConfigFactory(cfgPath).format(Format.PROPERTIES).fallback(fallbackPath).build();
}
