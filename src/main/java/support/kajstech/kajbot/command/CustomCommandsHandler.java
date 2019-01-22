package support.kajstech.kajbot.command;

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
            commands.forEach((k, v) -> CommandManager.addCustomCommand((String) k, (String) v));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static void saveCommands() {
        try {
            commands.store(new OutputStreamWriter(new FileOutputStream(cmdPath), StandardCharsets.UTF_8), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties getCommands() {
        return commands;
    }
}
