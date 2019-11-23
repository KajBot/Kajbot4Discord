package dk.jensbot.kajbot4discord.command;

import dk.jensbot.simplecfg.ConfigFactory;
import dk.jensbot.simplecfg.Format;
import dk.jensbot.simplecfg.SimpleCfg;

import java.util.Properties;

public class CustomCommandHandler {

    public static SimpleCfg cmds = new ConfigFactory("data/commands").format(Format.PROPERTIES).create();

    public static Properties getCommands() {
        return cmds.getProps();
    }

    public static void addCommand(String key, String value) {
        cmds.set(key, value);
    }

    public static void removeCommand(String key) {
        cmds.del(key);
    }
}
