package support.kajstech.kajbot.command.commands;

import support.kajstech.kajbot.Bot;
import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.command.Command;
import support.kajstech.kajbot.command.CommandEvent;
import support.kajstech.kajbot.utils.Config;

public class Activity extends Command {
    public Activity() {
        this.name = "game";
        this.guildOnly = false;
        this.requiredRole = Config.get("Bot admin role");
    }

    @Override
    public void execute(CommandEvent e) {
        if (e.getArgsSplit().get(0).length() < 1) return;

        Bot.jda.getPresence().setGame(net.dv8tion.jda.core.entities.Game.playing(e.getArgs()));
        Config.set("Bot game", e.getArgs());
        e.reply((Language.getMessage("Game.SET")).replace("%GAME%", e.getArgs()));
    }
}
