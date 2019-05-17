package support.kajstech.kajbot.command.commands;

import net.dv8tion.jda.core.entities.Member;
import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.command.Command;
import support.kajstech.kajbot.command.CommandEvent;
import support.kajstech.kajbot.utils.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Permit extends Command {

    public static final List<Member> permitted = new ArrayList<>();

    public Permit() {
        this.name = "permit";
        this.guildOnly = true;
        this.requiredRole = Config.cfg.get("Bot admin role");
    }

    @Override
    public void execute(CommandEvent e) {
        if (e.getArgs().length() < 1) return;
        if (!Config.cfg.get("Link blacklist").equalsIgnoreCase("true")) return;

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
