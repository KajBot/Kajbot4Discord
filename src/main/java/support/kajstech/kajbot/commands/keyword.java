package support.kajstech.kajbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import support.kajstech.kajbot.Main;
import support.kajstech.kajbot.utils.ConfigManager;

public class keyword extends Command {

    public keyword() {
        this.name = "keyword";
        this.guildOnly = true;
        this.requiredRole = ConfigManager.getProperty("botcontroller");
    }

    @Override
    protected void execute(CommandEvent e) {
        String command = e.getMessage().getContentRaw().substring(Main.commandClient.getPrefix().length());
        switch (e.getArgs()) {
            case "remove":
                System.out.println("remove");
                break;
            case "add":
                System.out.println("add");
                break;
        }

    }


}
