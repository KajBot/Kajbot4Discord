package support.kajstech.kajbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.entities.Message;
import support.kajstech.kajbot.utils.ConfigManager;

public class ping extends Command {

    public ping() {
        this.name = "ping";
        this.guildOnly = false;
        this.requiredRole = ConfigManager.getProperty("Bot controller role");
    }

    @Override
    protected void execute(CommandEvent e) {

        long time = System.currentTimeMillis();
        String respond = "Pong:";
        e.getChannel().sendMessage(respond).queue((Message m) ->
                m.editMessageFormat(respond + " `%d` ms.\n Heartbeat: `%d` ms.", System.currentTimeMillis() - time, e.getJDA().getPing()).queue());

    }
}
