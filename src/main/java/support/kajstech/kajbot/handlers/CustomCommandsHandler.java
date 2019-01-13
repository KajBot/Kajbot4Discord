package support.kajstech.kajbot.handlers;

import support.kajstech.kajbot.command.CommandManager;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class CustomCommandsHandler {

    private static Properties commands = new Properties();
    private static File cmdPath = new File(System.getProperty("user.dir") + "\\commands.properties");

    public static void init() {
        if (!cmdPath.exists()) {
            saveCommands();
        }

        try {
            commands.load(new BufferedReader(new InputStreamReader(new FileInputStream(cmdPath), StandardCharsets.UTF_8)));
            CustomCommandsHandler.getCommands().forEach((k, v) -> CommandManager.addCommand(k.toString(), v.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveCommands() {
        try {
            commands.store(new OutputStreamWriter(new FileOutputStream(cmdPath), StandardCharsets.UTF_8), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties getCommands() {
        return commands;
    }

    public static void addCommand(String key, String value) {
        commands.setProperty(key, value);
        CommandManager.addCommand(key, value);
        saveCommands();
    }

    public static void removeCommand(String key) {
        CommandManager.commands.remove(key);
        commands.remove(key);
        saveCommands();
    }
}
