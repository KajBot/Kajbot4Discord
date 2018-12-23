package support.kajstech.kajbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.handlers.ConfigHandler;
import support.kajstech.kajbot.handlers.KeywordHandler;


public class keyword extends Command {

    public keyword() {
        this.name = "keyword";
        this.guildOnly = false;
        this.requiredRole = ConfigHandler.getProperty("Bot controller role");
    }

    @Override
    protected void execute(CommandEvent e) {

        String[] args = e.getArgs().split(" ");
        switch (args[0]) {
            case "del":
            case "remove":
                try {
                    if (KeywordHandler.kws.containsKey(args[1])) {
                        KeywordHandler.removeKeyword(args[1]);
                        e.getChannel().sendMessage((Language.getMessage("Keyword.UNREGISTERED")).replace("%KW%", args[1].toUpperCase())).queue();
                    }
                } catch (Exception ignored) {
                    return;
                }
                break;
            case "add":
                try {
                    String kwName = args[1];
                    String[] kwContext = e.getArgs().substring(kwName.length() + "add ".length() + 1).split(" ");
                    KeywordHandler.addKeyword(kwName, String.join(" ", kwContext));
                    e.getChannel().sendMessage((Language.getMessage("Keyword.REGISTERED")).replace("%KW%", kwName.toUpperCase())).queue();
                } catch (Exception ignored) {
                    return;
                }
                break;
        }

    }


}
