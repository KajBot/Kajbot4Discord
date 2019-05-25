package support.kajstech.kajbot.command.commands;

import support.kajstech.kajbot.Main;
import support.kajstech.kajbot.command.Command;
import support.kajstech.kajbot.command.CommandEvent;
import support.kajstech.kajbot.utils.Config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class Update extends Command {
    public Update() {
        this.name = "update";
        this.guildOnly = false;
        this.requiredRole = Config.cfg.get("Bot admin role");
    }


    @Override
    public void execute(CommandEvent e) {
        e.reply("Downloading update..");
        try {
            //DOWNLOAD
            InputStream in = new URL("https://jenkins.jensz12.com/job/KajBot-Discord-Dev/8/deployedArtifacts/download/artifact.1/").openStream();
            Files.copy(in, Paths.get(new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getName()), StandardCopyOption.REPLACE_EXISTING);
            e.reply("Download done.");

            //RESTART

            final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
            final File currentJar = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());

            if (!currentJar.getName().endsWith(".jar")) return;

            final ArrayList<String> command = new ArrayList<>();
            command.add(javaBin);
            command.add("-jar");
            command.add(currentJar.getPath());

            e.reply("Restarting..");
            final ProcessBuilder builder = new ProcessBuilder(command);
            builder.inheritIO().redirectInput(ProcessBuilder.Redirect.INHERIT).start();
            System.exit(0);

        } catch (IOException | URISyntaxException ex) {
            ex.printStackTrace();
        }
    }

}
