package dk.jensbot.kajbot4discord.keyword;

import dk.jensbot.simplecfg.ConfigFactory;
import dk.jensbot.simplecfg.Format;
import dk.jensbot.simplecfg.SimpleCfg;

import java.util.Properties;

public class KeywordHandler {

    public static SimpleCfg kws = new ConfigFactory("data/keywords").format(Format.PROPERTIES).create();

    public static Properties getKeywords() {
        return kws.getProps();
    }

    public static void addKeyword(String key, String value) {
        kws.set(key, value);
    }

    public static void removeKeyword(String key) {
        kws.del(key);
    }
}
