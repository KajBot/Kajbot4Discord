package dk.jensbot.kajbot4discord.command.commands;

import dk.jensbot.kajbot4discord.command.Command;
import dk.jensbot.kajbot4discord.command.CommandEvent;
import dk.jensbot.kajbot4discord.utils.LogHelper;
import net.dv8tion.jda.core.JDA;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Eval extends Command {

    private final ScriptEngine engine;

    public Eval() {
        this.name = "eval";
        this.ownerCommand = true;

        engine = new ScriptEngineManager().getEngineByName("nashorn");
        try {
            engine.eval("var imports = new JavaImporter(" +
                    "java.io," +
                    "java.lang," +
                    "java.util," +
                    "Packages.net.dv8tion.jda.api," +
                    "Packages.net.dv8tion.jda.api.entities," +
                    "Packages.net.dv8tion.jda.api.entities.impl," +
                    "Packages.net.dv8tion.jda.api.managers," +
                    "Packages.net.dv8tion.jda.api.managers.impl," +
                    "Packages.net.dv8tion.jda.api.utils);");
        } catch (ScriptException ex) {
            ex.printStackTrace();
            LogHelper.error(this.getClass(), ex.toString());
        }
    }


    @Override
    public void execute(CommandEvent e) {

        engine.put("e", e.getEvent());
        engine.put("event", e.getEvent());
        engine.put("api", e.getJDA());
        engine.put("jda", e.getJDA());
        engine.put("channel", e.getChannel());
        engine.put("author", e.getAuthor());
        engine.put("member", e.getMember());
        engine.put("message", e.getMessage());
        engine.put("guild", e.getGuild());
        engine.put("input", e.getArgs());
        engine.put("args", e.getArgs());
        engine.put("selfUser", e.getSelfUser());
        engine.put("selfMember", e.getGuild() == null ? null : e.getGuild().getSelfMember());
        engine.put("mentionedUsers", e.getMessage().getMentionedUsers());
        engine.put("mentionedRoles", e.getMessage().getMentionedRoles());
        engine.put("mentionedChannels", e.getMessage().getMentionedChannels());


        Object out;
        try {
            out = engine.eval("(function() { with (imports) {\n" + e.getArgs() + "\n} })();");
        } catch (Exception ex) {
            e.reply("**Exception**: ```\n" + ex.getLocalizedMessage() + "```");
            System.out.println(e.getArgs());
            return;
        }

        String outputS;
        if (out == null) {
            outputS = "`Task executed without errors.`";
        } else {
            outputS = "Output: ```\n" + out.toString().replace("`", "\\`") + "\n```";
        }

        if (e.getJDA().getStatus() != JDA.Status.SHUTDOWN) {
            e.reply(outputS);
        } else {
            Runtime.getRuntime().exit(0);
        }
    }

}
