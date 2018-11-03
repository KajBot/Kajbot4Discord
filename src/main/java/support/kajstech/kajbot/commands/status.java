package support.kajstech.kajbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.OnlineStatus;
import support.kajstech.kajbot.Main;
import support.kajstech.kajbot.utils.ConfigManager;

public class status extends Command {

    public status() {
        this.name = "status";
        this.guildOnly = false;
        this.requiredRole = ConfigManager.getProperty("botcontroller");
    }

    @Override
    protected void execute(CommandEvent e) {

        String[] args = e.getArgs().split("\\W+");
        if (args[0].length() < 1) return;


        if (args[0].equalsIgnoreCase("dnd")) args[0] = "DO_NOT_DISTURB";
        if (!(args[0].equalsIgnoreCase("OFFLINE") || args[0].equalsIgnoreCase("INVISIBLE") || args[0].equalsIgnoreCase("ONLINE") || args[0].equalsIgnoreCase("DO_NOT_DISTURB") || args[0].equalsIgnoreCase("IDLE")))
            return;


        Main.jda.getPresence().setStatus(OnlineStatus.valueOf(args[0].toUpperCase()));
        e.getChannel().sendMessage("Online status set to: ``" + args[0].toUpperCase() + "``").queue();

    }
}
