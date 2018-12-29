package support.kajstech.kajbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.handlers.ConfigHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Permit extends Command {

    public static List<Member> permitted = new ArrayList<>();

    public Permit() {
        this.name = "permit";
        this.guildOnly = false;
        this.requiredRole = ConfigHandler.getProperty("Bot controller role");
        this.botPermissions = new Permission[]{Permission.ADMINISTRATOR};
    }

    @Override
    protected void execute(CommandEvent e) {
        if (e.getArgs().length() < 1) return;
        if (!ConfigHandler.getProperty("Link blacklist").equalsIgnoreCase("true")) return;

        List<Member> memberMention = e.getMessage().getMentionedMembers();

        if (!e.getMessage().getMentionedMembers().isEmpty()) {
            for (Member member : memberMention) {
                permitted.add(member);
                e.reply((Language.getMessage("Permit.PERMITTED")).replace("%USER%", member.getAsMention()));
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        permitted.remove(member);
                    }
                }, 60000);
            }
        }

    }
}
