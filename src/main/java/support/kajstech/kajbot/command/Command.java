package support.kajstech.kajbot.command;

import net.dv8tion.jda.core.entities.ChannelType;

public abstract class Command {

    protected String name = "null";
    protected boolean ownerCommand = false;
    protected String requiredRole = null;
    protected boolean guildOnly = true;

    protected final void run(CommandEvent event) {

        if (ownerCommand && !event.isOwner()) {
            return;
        }

        if (requiredRole != null)
            if (!event.getEvent().isFromType(ChannelType.TEXT) || event.getEvent().getMember().getRoles().stream().noneMatch(r -> r.getName().equalsIgnoreCase(requiredRole))) {
                event.getEvent().getChannel().sendMessage("You must have a role called `" + requiredRole + "` to use that!").queue();

                return;
            }


        if (guildOnly && (event.getEvent().isFromType(ChannelType.PRIVATE) || event.getEvent().isFromType(ChannelType.GROUP))) {
            event.getEvent().getChannel().sendMessage("This command cannot be used in Direct messages").queue();
            return;
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