package support.kajstech.kajbot.utils;

import support.kajstech.simplecfg.ConfigFactory;
import support.kajstech.simplecfg.Format;
import support.kajstech.simplecfg.SimpleCfg;

import java.io.File;

public class Language {

    private static File langPath = new File(System.getProperty("user.dir") + "/" + Config.cfg.get("Language") + ".properties");
    private static File fallbackPath = new File("en_US.properties");

    public static SimpleCfg lang = new ConfigFactory(langPath).format(Format.PROPERTIES).fallback(fallbackPath).build();
}