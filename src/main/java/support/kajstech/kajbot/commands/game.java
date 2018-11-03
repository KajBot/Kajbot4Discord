package support.kajstech.kajbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.entities.Game;
import support.kajstech.kajbot.Main;
import support.kajstech.kajbot.utils.ConfigManager;

public class game extends Command {

    public game() {
        this.name = "game";
        this.guildOnly = false;
        this.requiredRole = ConfigManager.getProperty("botcontroller");
    }

    @Override
    protected void execute(CommandEvent e) {

        if (e.getArgs().length() < 1) return;

        Main.jda.getPresence().setGame(Game.playing(e.getArgs()));
        e.getChannel().sendMessage("Game status set to: ``" + e.getArgs() + "``").queue();

    }
}
