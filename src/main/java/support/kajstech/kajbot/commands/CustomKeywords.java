package support.kajstech.kajbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.handlers.ConfigHandler;
import support.kajstech.kajbot.handlers.CustomCommandsHandler;
import support.kajstech.kajbot.handlers.KeywordHandler;

import java.awt.*;


public class CustomKeywords extends Command {

    public CustomKeywords() {
        this.name = "keyword";
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
                    KeywordHandler.getKeywords().forEach((k, v) -> {
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
                    if (KeywordHandler.kws.containsKey(args[1])) {
                        KeywordHandler.removeKeyword(args[1]);
                        e.reply((Language.getMessage("Keyword.UNREGISTERED")).replace("%KW%", args[1].toUpperCase()));
                    }
                } catch (Exception ignored) {
                    return;
                }
                break;
            case "add":
                try {
                    String kwName = args[1];
                    String[] kwContext = e.getArgs().substring(kwName.length() + "add ".length() + 1).split("\\s+");
                    KeywordHandler.addKeyword(kwName, String.join(" ", kwContext));
                    e.reply((Language.getMessage("Keyword.REGISTERED")).replace("%KW%", kwName.toUpperCase()));
                } catch (Exception ignored) {
                    return;
                }
                break;
        }

    }


}
