package dk.jensbot.kajbot4discord.command;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

public class CustomCommandsHandler {

    private static Properties commands = new Properties();
    private static File cmdPath = new File(System.getProperty("user.dir") + "/commands.properties");

    static {
        if (!cmdPath.exists()) {
            saveCustomCommands();
        }

        try {
            commands.load(new BufferedReader(new InputStreamReader(new FileInputStream(cmdPath), StandardCharsets.UTF_8)));
            commands.forEach((k, v) -> CommandManager.addCustomCommand((String) k, (String) v));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                saveCustomCommands();
            }
        }, 60000);
    }

    public static void saveCustomCommands() {
        try {
            commands.store(new OutputStreamWriter(new FileOutputStream(cmdPath), StandardCharsets.UTF_8), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties getCustomCommands() {
        return commands;
    }
}
