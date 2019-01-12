package support.kajstech.kajbot.command.commands;

import support.kajstech.kajbot.command.Command;
import support.kajstech.kajbot.command.CommandEvent;

import java.time.temporal.ChronoUnit;

public class Ping extends Command {
    public Ping() {
        this.name = "ping";
        this.guildOnly = true;
    }


    @Override
    public void execute(CommandEvent e) {
        e.getEvent().getChannel().sendMessage("Ping ...").queue(m -> {
            long ping = e.getEvent().getMessage().getCreationTime().until(m.getCreationTime(), ChronoUnit.MILLIS);
            m.editMessage("Ping: " + ping + "ms | Websocket: " + e.getEvent().getJDA().getPing() + "ms").queue();
        });
    }

}
