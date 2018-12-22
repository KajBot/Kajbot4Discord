package support.kajstech.kajbot.handlers;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

public class CustomCommandsHandler {

    public static Map<String, String> cmds = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private static Properties commands = new Properties();
    private static File cmdPath = new File(System.getProperty("user.dir") + "\\commands.properties");

    public static void init() {
        if (!cmdPath.exists()) {
            try {
                commands.store(new FileOutputStream(cmdPath), null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            commands.load(new BufferedReader(new InputStreamReader(new FileInputStream(cmdPath), StandardCharsets.UTF_8)));
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
            commands.store(new FileOutputStream(cmdPath), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeCommand(String key) {
        cmds.remove(key);
        commands.remove(key);
        try {
            commands.store(new FileOutputStream(cmdPath), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
