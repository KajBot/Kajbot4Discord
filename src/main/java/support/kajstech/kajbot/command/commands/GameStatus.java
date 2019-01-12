package support.kajstech.kajbot.command.commands;

import net.dv8tion.jda.core.entities.Game;
import support.kajstech.kajbot.Bot;
import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.command.Command;
import support.kajstech.kajbot.command.CommandEvent;
import support.kajstech.kajbot.handlers.ConfigHandler;

public class GameStatus extends Command {
    public GameStatus() {
        this.name = "game";
    }

    @Override
    public void execute(CommandEvent e) {
        if (e.getArgsSplit().get(0).length() < 1) return;

        Bot.jda.getPresence().setGame(Game.playing(e.getArgs()));
        ConfigHandler.setProperty("Bot game", e.getArgs());
        e.getEvent().getChannel().sendMessage((Language.getMessage("Game.SET")).replace("%GAME%", e.getArgs())).queue();
    }
}
