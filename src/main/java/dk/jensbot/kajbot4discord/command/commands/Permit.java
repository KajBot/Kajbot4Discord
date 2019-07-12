package dk.jensbot.kajbot4discord.command.commands;

import dk.jensbot.kajbot4discord.command.Command;
import dk.jensbot.kajbot4discord.command.CommandEvent;
import dk.jensbot.kajbot4discord.utils.Config;
import dk.jensbot.kajbot4discord.utils.Language;
import net.dv8tion.jda.core.entities.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Permit extends Command {

    public static final List<Member> permitted = new ArrayList<>();

    public Permit() {
        this.name = "permit";
        this.adminCommand = true;
    }

    @Override
    public void execute(CommandEvent e) {
        if (e.getArgs().length() < 1) return;
        if (!Config.cfg.get("Chat.linkBlacklist").equalsIgnoreCase("true")) return;

        List<Member> memberMention = e.getMessage().getMentionedMembers();

        if (!e.getMessage().getMentionedMembers().isEmpty()) {
            for (Member member : memberMention) {
                permitted.add(member);
                e.reply((Language.lang.get("Permit.PERMITTED")).replace("%USER%", member.getAsMention()));
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
