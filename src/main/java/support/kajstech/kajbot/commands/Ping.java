package support.kajstech.kajbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.Permission;
import support.kajstech.kajbot.handlers.ConfigHandler;

import java.time.temporal.ChronoUnit;

public class Ping extends Command {

    public Ping() {
        this.name = "ping";
        this.guildOnly = false;
        this.requiredRole = ConfigHandler.getProperty("Bot controller role");
        this.botPermissions = new Permission[]{Permission.ADMINISTRATOR};
    }

    @Override
    protected void execute(CommandEvent e) {
        e.reply("Ping: ...", m -> {
            long ping = e.getMessage().getCreationTime().until(m.getCreationTime(), ChronoUnit.MILLIS);
            m.editMessage("Ping: " + ping + "ms | Websocket: " + e.getJDA().getPing() + "ms").queue();
        });

    }
}
