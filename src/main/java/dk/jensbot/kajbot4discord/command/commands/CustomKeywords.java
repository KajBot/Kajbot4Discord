package dk.jensbot.kajbot4discord.command.commands;


import dk.jensbot.kajbot4discord.command.Command;
import dk.jensbot.kajbot4discord.command.CommandEvent;
import dk.jensbot.kajbot4discord.keyword.KeywordHandler;
import dk.jensbot.kajbot4discord.utils.Language;
import dk.jensbot.kajbot4discord.utils.LogHelper;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.time.ZonedDateTime;


public class CustomKeywords extends Command {

    public CustomKeywords() {
        this.name = "keyword";
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
                    KeywordHandler.getKeywords().forEach((k, v) -> eb.addField(String.valueOf(k), String.valueOf(v), true));
                    e.reply(eb.build());
                } catch (Exception ex) {
                    LogHelper.error(this.getClass(), ex, e.getMessage().getContentRaw());
                }

                break;

            case "del":
            case "remove":
                try {
                    if (KeywordHandler.kws.hasKey(e.getArgsSplit().get(1))) {
                        KeywordHandler.removeKeyword(e.getArgsSplit().get(1));
                        e.reply((Language.lang.getProperty("Keyword.UNREGISTERED")).replace("%KW%", e.getArgsSplit().get(1).toUpperCase()));
                    }
                } catch (Exception ex) {
                    LogHelper.error(this.getClass(), ex, e.getMessage().getContentRaw());
                }
                break;
            case "add":
                try {
                    String kwName = e.getArgsSplit().get(1);
                    String[] kwContext = e.getArgs().substring(kwName.length() + "add ".length() + 1).split("\\s+");
                    KeywordHandler.addKeyword(kwName, String.join(" ", kwContext));
                    e.reply((Language.lang.getProperty("Keyword.REGISTERED")).replace("%KW%", kwName.toUpperCase()));
                } catch (Exception ex) {
                    LogHelper.error(this.getClass(), ex, e.getMessage().getContentRaw());
                }
                break;
        }

    }


}
