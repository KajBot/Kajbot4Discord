package support.kajstech.kajbot.command.commands;

import net.dv8tion.jda.core.entities.Game;
import support.kajstech.kajbot.Bot;
import support.kajstech.kajbot.command.Command;
import support.kajstech.kajbot.command.CommandEvent;
import support.kajstech.kajbot.utils.Config;
import support.kajstech.kajbot.utils.Language;

public class Activity extends Command {
    public Activity() {
        this.name = "activity";
        this.guildOnly = false;
        this.requiredRole = Config.cfg.get("Bot admin role");
    }

    @Override
    public void execute(CommandEvent e) {
        if (e.getArgsSplit().get(0).length() < 1) return;


        if (e.getArgsSplit().get(0).equalsIgnoreCase("playing"))
            e.getArgsSplit().set(0, Game.GameType.DEFAULT.toString());
        if (!(e.getArgsSplit().get(0).equalsIgnoreCase(Game.GameType.DEFAULT.toString()) || e.getArgsSplit().get(0).equalsIgnoreCase(Game.GameType.LISTENING.toString()) || e.getArgsSplit().get(0).equalsIgnoreCase(Game.GameType.WATCHING.toString()) || e.getArgsSplit().get(0).equalsIgnoreCase(Game.GameType.STREAMING.toString())))
            return;

        Bot.jda.getPresence().setGame(Game.of(Game.GameType.valueOf(e.getArgsSplit().get(0).toUpperCase()), Bot.jda.getPresence().getGame().getName()));
        e.reply((Language.lang.get("Status.SET")).replace("%STATUS%", e.getArgsSplit().get(0).toUpperCase()));
    }
}
