package support.kajstech.kajbot.command.commands;

import net.dv8tion.jda.core.JDA;
import support.kajstech.kajbot.command.Command;
import support.kajstech.kajbot.command.CommandEvent;
import support.kajstech.kajbot.utils.LogHelper;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Eval extends Command {

    private final ScriptEngine engine;

    public Eval() {
        this.name = "eval";

        engine = new ScriptEngineManager().getEngineByName("nashorn");
        try {
            engine.eval("var imports = new JavaImporter(java.io, java.lang, java.util, Packages.net.dv8tion.jda.core, Packages.net.dv8tion.jda.core.entities, Packages.net.dv8tion.jda.core.managers, Packages.support.kajstech.kajbot.utils.LogHelper);");
        } catch (ScriptException ex) {
            ex.printStackTrace();
            LogHelper.error(Eval.class, ex.toString());
        }
    }


    @Override
    public void execute(CommandEvent e) {

        engine.put("e", e.getEvent());
        engine.put("event", e.getEvent());
        engine.put("api", e.getEvent().getJDA());
        engine.put("jda", e.getEvent().getJDA());
        engine.put("channel", e.getEvent().getChannel());
        engine.put("author", e.getEvent().getAuthor());
        engine.put("member", e.getEvent().getMember());
        engine.put("message", e.getEvent().getMessage());
        engine.put("guild", e.getEvent().getGuild());
        engine.put("input", e.getArgs());
        engine.put("selfUser", e.getEvent().getJDA().getSelfUser());
        engine.put("selfMember", e.getEvent().getGuild() == null ? null : e.getEvent().getGuild().getSelfMember());
        engine.put("mentionedUsers", e.getEvent().getMessage().getMentionedUsers());
        engine.put("mentionedRoles", e.getEvent().getMessage().getMentionedRoles());
        engine.put("mentionedChannels", e.getEvent().getMessage().getMentionedChannels());


        Object out;
        try {
            out = engine.eval("(function() { with (imports) {\n" + e.getArgs() + "\n} })();");
        } catch (Exception ex) {
            e.getEvent().getChannel().sendMessage("**Exception**: ```\n" + ex.getLocalizedMessage() + "```").queue();
            System.out.println(e.getArgs());
            return;
        }

        String outputS;
        if (out == null) {
            outputS = "`Task executed without errors.`";
        } else {
            outputS = "Output: ```\n" + out.toString().replace("`", "\\`") + "\n```";
        }

        if (e.getEvent().getJDA().getStatus() != JDA.Status.SHUTDOWN) {
            e.getEvent().getChannel().sendMessage(outputS).queue();
        } else {
            Runtime.getRuntime().exit(0);
        }
    }

}
