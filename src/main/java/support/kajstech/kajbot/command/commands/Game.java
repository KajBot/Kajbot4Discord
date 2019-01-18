package support.kajstech.kajbot.command.commands;

import net.dv8tion.jda.api.entities.Activity;
import support.kajstech.kajbot.Bot;
import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.command.Command;
import support.kajstech.kajbot.command.CommandEvent;
import support.kajstech.kajbot.handlers.ConfigHandler;

public class Game extends Command {
    public Game() {
        this.name = "game";
        this.guildOnly = false;
        this.requiredRole = ConfigHandler.getProperty("Bot admin role");
    }

    @Override
    public void execute(CommandEvent e) {
        if (e.getArgsSplit().get(0).length() < 1) return;

        Bot.jda.getPresence().setActivity(Activity.playing(e.getArgs()));
        ConfigHandler.setProperty("Bot game", e.getArgs());
        e.reply((Language.getMessage("Game.SET")).replace("%GAME%", e.getArgs()));
    }
}
