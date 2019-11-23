package dk.jensbot.kajbot4discord.utils;

import dk.jensbot.simplecfg.ConfigFactory;
import dk.jensbot.simplecfg.Format;
import dk.jensbot.simplecfg.SimpleCfg;

import java.io.File;

public class Config {
    private static File fallbackPath = new File("config.properties");

    public static SimpleCfg cfg = new ConfigFactory("data/config").format(Format.PROPERTIES).fallback(fallbackPath).create();
}
