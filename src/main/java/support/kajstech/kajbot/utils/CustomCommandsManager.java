package support.kajstech.kajbot.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

public class CustomCommandsManager {

    public static Map<String, String> cmds = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private static Properties commands = new Properties();

    public static void init() {
        try {
            commands.loadFromXML(new FileInputStream("commands.xml"));
            for (final String property : commands.stringPropertyNames()) {
                cmds.put(property, commands.getProperty(property));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties getCommands() {
        return commands;
    }

    public static void addCommand(String key, String value) {
        commands.setProperty(key, value);
        cmds.put(key, value);
        try {
            commands.storeToXML(new FileOutputStream("commands.xml"), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getCommand(String key) {
        key = commands.getProperty(key);
        return key;
    }

    public static void removeCommand(String key) {
        cmds.remove(key);
        commands.remove(key);
        try {
            commands.storeToXML(new FileOutputStream("commands.xml"), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
