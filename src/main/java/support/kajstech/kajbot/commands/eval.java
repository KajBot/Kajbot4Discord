package support.kajstech.kajbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.JDA;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class eval extends Command {

    private final ScriptEngine engine;

    public eval() {
        this.name = "eval";
        this.guildOnly = false;
        this.ownerCommand = true;

        engine = new ScriptEngineManager().getEngineByName("nashorn");
        try {
            engine.eval("var imports = new JavaImporter(java.io, java.lang, java.util, Packages.net.dv8tion.jda.core, "
                    + "Packages.net.dv8tion.jda.core.entities, Packages.net.dv8tion.jda.core.managers, Packages.support.kajstech.kajbot.utils.KajbotLogger, Packages.support.kajstech.kajbot.utils.ConfigManager, Packages.support.kajstech.kajbot.utils.CustomCommandsManager);");
        } catch (ScriptException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    protected void execute(CommandEvent event) {

        String allArgs = event.getArgs();

        engine.put("e", event);
        engine.put("event", event);
        engine.put("api", event.getJDA());
        engine.put("jda", event.getJDA());
        engine.put("channel", event.getChannel());
        engine.put("author", event.getAuthor());
        engine.put("member", event.getMember());
        engine.put("message", event.getMessage());
        engine.put("guild", event.getGuild());
        engine.put("input", allArgs);
        engine.put("selfUser", event.getJDA().getSelfUser());
        engine.put("selfMember", event.getGuild() == null ? null : event.getGuild().getSelfMember());
        engine.put("mentionedUsers", event.getMessage().getMentionedUsers());
        engine.put("mentionedRoles", event.getMessage().getMentionedRoles());
        engine.put("mentionedChannels", event.getMessage().getMentionedChannels());


        Object out;
        try {
            out = engine.eval("(function() { with (imports) {\n" + allArgs + "\n} })();");
        } catch (Exception ex) {
            event.getChannel().sendMessage("**Exception**: ```\n" + ex.getLocalizedMessage() + "```").queue();
            System.out.println(allArgs);
            return;
        }

        String outputS;
        if (out == null) {
            outputS = "`Task executed without errors.`";
        } else {
            outputS = "Output: ```\n" + out.toString().replace("`", "\\`") + "\n```";
        }

        if (event.getJDA().getStatus() != JDA.Status.SHUTDOWN) {
            event.getChannel().sendMessage(outputS).queue();
        } else {
            System.exit(0);
        }
    }

}
