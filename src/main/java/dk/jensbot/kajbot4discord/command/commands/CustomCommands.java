package dk.jensbot.kajbot4discord.command.commands;

import dk.jensbot.kajbot4discord.Main;
import dk.jensbot.kajbot4discord.command.Command;
import dk.jensbot.kajbot4discord.command.CommandEvent;
import dk.jensbot.kajbot4discord.command.CommandManager;
import dk.jensbot.kajbot4discord.command.CustomCommandsHandler;
import dk.jensbot.kajbot4discord.utils.Config;
import dk.jensbot.kajbot4discord.utils.Language;
import dk.jensbot.kajbot4discord.utils.LogHelper;
import net.dv8tion.jda.core.EmbedBuilder;

import java.awt.*;
import java.time.ZonedDateTime;


public class CustomCommands extends Command {

    public CustomCommands() {
        this.name = "command";
        this.adminCommand = true;
    }

    @Override
    public void execute(CommandEvent e) {
        if (e.getArgs().length() < 1) return;

        switch (e.getArgsSplit().get(0)) {
            default:
            case "list":
                try {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setColor(new Color(0xA6C055));
                    eb.setTimestamp(ZonedDateTime.now());
                    CustomCommandsHandler.getCustomCommands().forEach((k, v) -> eb.addField(String.valueOf(k), String.valueOf(v), true));
                    e.reply(eb.build());
                } catch (Exception ex) {
                    LogHelper.error(this.getClass(), ex, e.getMessage().getContentRaw());
                }

                break;
            case "del":
            case "remove":
                try {
                    for (Class<? extends Command> command : Main.internalCommands) {
                        if (command.getSimpleName().equalsIgnoreCase(e.getArgsSplit().get(1))) return;
                    }
                    CommandManager.removeCustomCommand(e.getArgsSplit().get(1).replace(Config.cfg.get("Command-prefix"), ""));
                    e.reply((Language.lang.get("Command.UNREGISTERED")).replace("%CMD%", e.getArgsSplit().get(1).toUpperCase()));
                } catch (Exception ex) {
                    LogHelper.error(this.getClass(), ex, e.getMessage().getContentRaw());
                }
                break;
            case "add":
                try {
                    String cmdName = e.getArgsSplit().get(1).replace(Config.cfg.get("Command-prefix"), "");
                    for (Class<? extends Command> command : Main.internalCommands) {
                        if (command.getSimpleName().equalsIgnoreCase(cmdName)) return;
                    }
                    String[] cmdContext = e.getArgs().substring(cmdName.length() + "add ".length() + 1).split("\\s+");
                    CommandManager.addCustomCommand(cmdName, String.join(" ", cmdContext));
                    e.reply((Language.lang.get("Command.REGISTERED")).replace("%CMD%", cmdName.toUpperCase()));
                } catch (Exception ex) {
                    LogHelper.error(this.getClass(), ex, e.getMessage().getContentRaw());
                }
                break;
        }

    }


}
