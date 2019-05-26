package support.kajstech.kajbot.command;

import net.dv8tion.jda.core.entities.ChannelType;
import support.kajstech.kajbot.utils.Config;
import support.kajstech.kajbot.utils.Language;

public abstract class Command {

    protected String name = "null";
    protected boolean ownerCommand = false;
    protected boolean adminCommand = false;
    protected String requiredRole = null;
    protected boolean guildOnly = true;

    final void run(CommandEvent event) {
        if (ownerCommand && !event.isOwner()) {
            return;
        }

        if (!event.isOwner()) {
            if (guildOnly && (event.getEvent().isFromType(ChannelType.PRIVATE) || event.getEvent().isFromType(ChannelType.GROUP)) || (requiredRole != null || adminCommand) && event.getEvent().isFromType(ChannelType.PRIVATE) || event.getEvent().isFromType(ChannelType.GROUP)) {
                event.reply(Language.lang.get("CommandSystem.DIRECT_ERROR"));
                return;
            }

            if (adminCommand && event.getMember().getRoles().stream().noneMatch(r -> r.getName().equalsIgnoreCase(Config.cfg.get("Admin-role")))) {
                event.reply((Language.lang.get("CommandSystem.MISSING_ROLE")).replace("%ROLE%", Config.cfg.get("Admin-role")));
                return;
            }

            if (requiredRole != null) {
                if (event.getMember().getRoles().stream().noneMatch(r -> r.getName().equalsIgnoreCase(requiredRole))) {
                    event.reply((Language.lang.get("CommandSystem.MISSING_ROLE")).replace("%ROLE%", requiredRole));
                    return;
                }
            }
        }

        // run
        try {
            execute(event);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }

    }

    protected abstract void execute(CommandEvent e);
}