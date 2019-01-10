package support.kajstech.kajbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.Permission;
import support.kajstech.kajbot.utils.LogHelper;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Eval extends Command {

    private final ScriptEngine engine;

    public Eval() {
        this.name = "eval";
        this.guildOnly = false;
        this.ownerCommand = true;
        this.botPermissions = new Permission[]{Permission.ADMINISTRATOR};

        engine = new ScriptEngineManager().getEngineByName("nashorn");
        try {
            engine.eval("var imports = new JavaImporter(java.io, java.lang, java.util, Packages.net.dv8tion.jda.core, Packages.net.dv8tion.jda.core.entities, Packages.net.dv8tion.jda.core.managers, Packages.support.kajstech.kajbot.utils.LogHelper);");
        } catch (ScriptException ex) {
            ex.printStackTrace();
            LogHelper.error(Eval.class, ex.toString());
        }
    }


    @Override
    protected void execute(CommandEvent event) {

        engine.put("e", event);
        engine.put("event", event);
        engine.put("api", event.getJDA());
        engine.put("jda", event.getJDA());
        engine.put("channel", event.getChannel());
        engine.put("author", event.getAuthor());
        engine.put("member", event.getMember());
        engine.put("message", event.getMessage());
        engine.put("guild", event.getGuild());
        engine.put("input", event.getArgs());
        engine.put("selfUser", event.getJDA().getSelfUser());
        engine.put("selfMember", event.getGuild() == null ? null : event.getGuild().getSelfMember());
        engine.put("mentionedUsers", event.getMessage().getMentionedUsers());
        engine.put("mentionedRoles", event.getMessage().getMentionedRoles());
        engine.put("mentionedChannels", event.getMessage().getMentionedChannels());


        Object out;
        try {
            out = engine.eval("(function() { with (imports) {\n" + event.getArgs() + "\n} })();");
        } catch (Exception ex) {
            event.getChannel().sendMessage("**Exception**: ```\n" + ex.getLocalizedMessage() + "```").queue();
            System.out.println(event.getArgs());
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
            Runtime.getRuntime().exit(0);
        }
    }

}
