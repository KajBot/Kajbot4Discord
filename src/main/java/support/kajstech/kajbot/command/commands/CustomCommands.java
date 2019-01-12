package support.kajstech.kajbot.command.commands;

import net.dv8tion.jda.core.EmbedBuilder;
import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.command.Command;
import support.kajstech.kajbot.command.CommandEvent;
import support.kajstech.kajbot.handlers.ConfigHandler;
import support.kajstech.kajbot.handlers.CustomCommandsHandler;

import java.awt.*;


public class CustomCommands extends Command {

    public CustomCommands() {
        this.name = "command";
        this.guildOnly = false;
        this.requiredRole = ConfigHandler.getProperty("Bot admin role");
    }

    @Override
    public void execute(CommandEvent e) {

        switch (e.getArgsSplit().get(0)) {
            case "list":
                try {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setColor(new Color(0xA6C055));
                    CustomCommandsHandler.getCommands().forEach((k, v) -> eb.addField(String.valueOf(k), String.valueOf(v), true));
                    e.getEvent().getChannel().sendMessage(eb.build()).queue();
                } catch (Exception ignored) {
                    return;
                }

                break;
            case "del":
            case "remove":
                try {
                    if (CustomCommandsHandler.cmds.containsKey(e.getArgsSplit().get(1))) {
                        CustomCommandsHandler.removeCommand(e.getArgsSplit().get(1).replace(ConfigHandler.getProperty("Command prefix"), ""));
                        e.getEvent().getChannel().sendMessage((Language.getMessage("Command.UNREGISTERED")).replace("%CMD%", e.getArgsSplit().get(1).toUpperCase())).queue();
                    }
                } catch (Exception ignored) {
                    return;
                }
                break;
            case "add":
                try {
                    String cmdName = e.getArgsSplit().get(1).replace(ConfigHandler.getProperty("Command prefix"), "");
                    String[] cmdContext = e.getArgs().substring(cmdName.length() + "add ".length() + 1).split("\\s+");
                    CustomCommandsHandler.addCommand(cmdName, String.join(" ", cmdContext));
                    e.getEvent().getChannel().sendMessage((Language.getMessage("Command.REGISTERED")).replace("%CMD%", cmdName.toUpperCase())).queue();
                } catch (Exception ignored) {
                    return;
                }

                break;
        }

    }


}
