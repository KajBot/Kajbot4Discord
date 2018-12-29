package support.kajstech.kajbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.Permission;

public class Invite extends Command {

    public Invite() {
        this.name = "invite";
        this.guildOnly = true;
        this.botPermissions = new Permission[]{Permission.ADMINISTRATOR};
    }

    @Override
    protected void execute(CommandEvent e) {
        try {
            e.replyInDm(e.getGuild().getTextChannelById(e.getMessage().getChannel().getIdLong()).createInvite().setUnique(true).setMaxUses(1).setMaxAge(1800).complete().getURL());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
