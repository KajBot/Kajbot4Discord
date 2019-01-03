package support.kajstech.kajbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.handlers.ConfigHandler;
import support.kajstech.kajbot.handlers.CustomCommandsHandler;

import java.awt.*;


public class CustomCommands extends Command {

    public CustomCommands() {
        this.name = "command";
        this.guildOnly = false;
        this.requiredRole = ConfigHandler.getProperty("Bot admin role");
        this.botPermissions = new Permission[]{Permission.ADMINISTRATOR};
    }

    @Override
    protected void execute(CommandEvent e) {

        String[] args = e.getArgs().split("\\s+");
        switch (args[0]) {
            case "list":
                try {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setColor(new Color(0xA6C055));
                    CustomCommandsHandler.getCommands().forEach((k, v) -> {
                        eb.addField(String.valueOf(k), String.valueOf(v), false);
                    });
                    e.reply(eb.build());
                } catch (Exception ignored) {
                    return;
                }

                break;
            case "del":
            case "remove":
                try {
                    if (CustomCommandsHandler.cmds.containsKey(args[1])) {
                        CustomCommandsHandler.removeCommand(args[1].replace(ConfigHandler.getProperty("Command prefix"), ""));
                        e.reply((Language.getMessage("Command.UNREGISTERED")).replace("%CMD%", args[1].toUpperCase()));
                    }
                } catch (Exception ignored) {
                    return;
                }
                break;
            case "add":
                try {
                    String cmdName = args[1].replace(ConfigHandler.getProperty("Command prefix"), "");
                    String[] cmdContext = e.getArgs().substring(cmdName.length() + "add ".length() + 1).split("\\s+");
                    CustomCommandsHandler.addCommand(cmdName, String.join(" ", cmdContext));
                    e.reply((Language.getMessage("Command.REGISTERED")).replace("%CMD%", cmdName.toUpperCase()));
                } catch (Exception ignored) {
                    return;
                }

                break;
        }

    }


}
