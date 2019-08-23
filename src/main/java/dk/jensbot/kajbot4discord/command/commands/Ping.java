package dk.jensbot.kajbot4discord.command.commands;

import dk.jensbot.kajbot4discord.command.Command;
import dk.jensbot.kajbot4discord.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

public class Ping extends Command {
    public Ping() {
        this.name = "ping";
        this.adminCommand = true;
    }


    @Override
    public void execute(CommandEvent e) {
        e.reply("Gateway: " + e.getJDA().getGatewayPing() + "\nRest: " + e.getJDA().getRestPing());
        EmbedBuilder eb = new EmbedBuilder();
        eb.addField("Gateway", e.getJDA().getGatewayPing() + "ms", false);
        eb.addField("Rest", e.getJDA().getRestPing() + "ms", false);
        e.reply(eb.build());
    }

}