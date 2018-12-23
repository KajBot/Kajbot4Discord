package support.kajstech.kajbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.entities.Game;
import support.kajstech.kajbot.Bot;
import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.handlers.ConfigHandler;

public class game extends Command {

    public game() {
        this.name = "game";
        this.guildOnly = false;
        this.requiredRole = ConfigHandler.getProperty("Bot controller role");
    }

    @Override
    protected void execute(CommandEvent e) {
        if (e.getArgs().length() < 1) return;

        Bot.jda.getPresence().setGame(Game.playing(e.getArgs()));
        e.getChannel().sendMessage((Language.getMessage("Game.SET")).replace("%GAME%", e.getArgs())).queue();

    }
}
