package support.kajstech.kajbot.command.commands;

import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import support.kajstech.kajbot.Bot;
import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.command.ICommand;
import support.kajstech.kajbot.handlers.ConfigHandler;

import java.util.List;

public class GameStatus implements ICommand {
    @Override
    public void handle(List<String> argsSplit, String args, MessageReceivedEvent event) {
        if (argsSplit.get(0).length() < 1) return;

        Bot.jda.getPresence().setGame(Game.playing(args));
        ConfigHandler.setProperty("Bot game", args);
        event.getChannel().sendMessage((Language.getMessage("Game.SET")).replace("%GAME%", args)).queue();
    }

    @Override
    public String getName() {
        return "game";
    }
}
