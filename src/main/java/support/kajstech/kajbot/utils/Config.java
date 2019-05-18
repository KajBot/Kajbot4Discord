package support.kajstech.kajbot.utils;

import support.kajstech.simplecfg.ConfigFactory;
import support.kajstech.simplecfg.Format;
import support.kajstech.simplecfg.SimpleCfg;

import java.io.File;

public class Config {

    private static File cfgPath = new File(System.getProperty("user.dir") + "/config");
    private static File fallbackPath = new File("config.properties");

    public static SimpleCfg cfg = new ConfigFactory(cfgPath).format(Format.PROPERTIES).fallback(fallbackPath).build();

}
