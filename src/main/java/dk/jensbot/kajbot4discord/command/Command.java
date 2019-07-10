package dk.jensbot.kajbot4discord.command;

import dk.jensbot.kajbot4discord.utils.Config;
import net.dv8tion.jda.core.entities.ChannelType;
import dk.jensbot.kajbot4discord.utils.Language;

public abstract class Command {

    protected String name = "null";
    protected boolean ownerCommand = false;
    protected boolean adminCommand = false;
    protected boolean boosterCommand = false;
    protected String requiredRole = null;
    protected boolean guildOnly = false;

    final void run(CommandEvent event) {
        if (ownerCommand && !event.isOwner()) {
            return;
        }

        if (!event.isOwner()) {
            if (guildOnly && (event.getEvent().isFromType(ChannelType.PRIVATE) || event.getEvent().isFromType(ChannelType.GROUP)) || (requiredRole != null || adminCommand || boosterCommand) && event.getEvent().isFromType(ChannelType.PRIVATE) || event.getEvent().isFromType(ChannelType.GROUP)) {
                event.reply(Language.lang.get("CommandSystem.DIRECT_ERROR"));
                return;
            }

            if (adminCommand && event.getMember().getRoles().stream().noneMatch(r -> r.getName().equalsIgnoreCase(Config.cfg.get("Bot.adminRole")))) {
                event.reply((Language.lang.get("CommandSystem.MISSING_ROLE")).replace("%ROLE%", Config.cfg.get("Bot.adminRole")));
                return;
            }

            if (requiredRole != null) {
                if (event.getMember().getRoles().stream().noneMatch(r -> r.getName().equalsIgnoreCase(requiredRole)) && event.getMember().getRoles().stream().noneMatch(r -> r.getName().equalsIgnoreCase(Config.cfg.get("Bot.adminRole")))) {
                    event.reply((Language.lang.get("CommandSystem.MISSING_ROLE")).replace("%ROLE%", requiredRole));
                    return;
                }
            }

            if(boosterCommand && event.getMember().getRoles().stream().noneMatch(r -> r.getName().equalsIgnoreCase("Nitro Booster")) && event.getMember().getRoles().stream().noneMatch(r -> r.getName().equalsIgnoreCase(Config.cfg.get("Bot.adminRole")))){
                event.reply(Language.lang.get("CommandSystem.NOT_BOOSTER"));
                return;
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