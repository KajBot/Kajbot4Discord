package support.kajstech.kajbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.Permission;
import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.handlers.ConfigHandler;
import support.kajstech.kajbot.handlers.KeywordHandler;


public class CustomKeywords extends Command {

    public CustomKeywords() {
        this.name = "keyword";
        this.guildOnly = false;
        this.requiredRole = ConfigHandler.getProperty("Bot controller role");
        this.botPermissions = new Permission[]{Permission.ADMINISTRATOR};
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
                        e.reply((Language.getMessage("Keyword.UNREGISTERED")).replace("%KW%", args[1].toUpperCase()));
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
                    e.reply((Language.getMessage("Keyword.REGISTERED")).replace("%KW%", kwName.toUpperCase()));
                } catch (Exception ignored) {
                    return;
                }
                break;
        }

    }


}
