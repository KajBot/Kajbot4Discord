package support.kajstech.kajbot.command.commands;

import support.kajstech.kajbot.command.Command;
import support.kajstech.kajbot.command.CommandEvent;
import support.kajstech.kajbot.utils.Config;

import java.time.temporal.ChronoUnit;

public class Ping extends Command {
    public Ping() {
        this.name = "ping";
        this.adminCommand = true;
    }


    @Override
    public void execute(CommandEvent e) {
        e.reply("Ping: ...", m -> {
            long ping = e.getMessage().getCreationTime().until(m.getCreationTime(), ChronoUnit.MILLIS);
            m.editMessage("Ping: " + ping + "ms | Websocket: " + e.getJDA().getPing() + "ms").queue();
        });
    }

}
