package support.kajstech.kajbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Invite;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import support.kajstech.kajbot.handlers.ConfigHandler;

import java.util.Arrays;

public class invite extends Command {

    public invite() {
        this.name = "invite";
        this.guildOnly = true;
        this.botPermissions = new Permission[]{Permission.ADMINISTRATOR};
    }

    @Override
    protected void execute(CommandEvent e) {
        try {
            e.replyInDm(e.getGuild().getTextChannelById(e.getMessage().getChannel().getIdLong()).createInvite().setUnique(true).setMaxUses(1).setMaxAge(1800).complete().getURL());
        }catch(Exception ignored){
        }
    }
}
