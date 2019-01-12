package support.kajstech.kajbot.command.commands;

import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import support.kajstech.kajbot.Bot;
import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.command.ICommand;

import java.util.List;

public class Status implements ICommand {
    @Override
    public void handle(List<String> argsSplit, String args, MessageReceivedEvent event) {
        if (argsSplit.get(0).length() < 1) return;


        if (argsSplit.get(0).equalsIgnoreCase("dnd")) argsSplit.set(0, OnlineStatus.DO_NOT_DISTURB.toString());
        if (!(argsSplit.get(0).equalsIgnoreCase(OnlineStatus.INVISIBLE.toString()) || argsSplit.get(0).equalsIgnoreCase(OnlineStatus.ONLINE.toString()) || argsSplit.get(0).equalsIgnoreCase(OnlineStatus.DO_NOT_DISTURB.toString()) || argsSplit.get(0).equalsIgnoreCase(OnlineStatus.IDLE.toString())))
            return;


        Bot.jda.getPresence().setStatus(OnlineStatus.valueOf(argsSplit.get(0).toUpperCase()));
        event.getChannel().sendMessage((Language.getMessage("Status.SET")).replace("%STATUS%", argsSplit.get(0).toUpperCase())).queue();
    }

    @Override
    public String getName() {
        return "status";
    }
}
