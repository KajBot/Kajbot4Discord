package support.kajstech.kajbot.command.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import support.kajstech.kajbot.command.Command;
import support.kajstech.kajbot.command.CommandEvent;
import support.kajstech.kajbot.handlers.ConfigHandler;

import java.awt.*;
import java.time.ZonedDateTime;

public class Ping extends Command {
    public Ping() {
        this.name = "ping";
        this.guildOnly = false;
        this.requiredRole = ConfigHandler.getProperty("Bot admin role");
    }


    @Override
    public void execute(CommandEvent e) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(new Color(0xA6C055));
        eb.setTimestamp(ZonedDateTime.now());
        eb.setTitle("Ping:");
        eb.addField("Rest", e.getJDA().getRestPing().complete() + "ms", false);
        eb.addField("Websocket", e.getJDA().getGatewayPing() + "ms", false);
        e.reply(eb.build());
    }

}
