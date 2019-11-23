package dk.jensbot.kajbot4discord.web.context;

import dk.jensbot.kajbot4discord.Bot;
import dk.jensbot.kajbot4discord.command.CustomCommandHandler;
import dk.jensbot.kajbot4discord.keyword.KeywordHandler;
import dk.jensbot.kajbot4discord.utils.Config;
import dk.jensbot.kajbot4discord.web.Context;
import dk.jensbot.kajbot4discord.web.Servlet;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class GET extends Servlet {
    public GET() {
        this.name = "get";
        this.path = "/api";
    }

    private static Map<String, String> qToM(String query) {
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] pair = param.split("=");
            if (pair.length > 1) {
                result.put(pair[0], pair[1]);
            } else {
                result.put(pair[0], "");
            }
        }
        return result;


    }

    @Override
    protected void get(Context context) throws IOException {
        OutputStreamWriter osw = new OutputStreamWriter(context.response().getOutputStream(), StandardCharsets.UTF_8);

        if (context.request().getHeader("token") == null || !context.request().getHeader("token").contentEquals(Config.cfg.get("API.token"))) {
            context.response().setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            osw.close();
            return;
        }

        JSONObject json = new JSONObject();
        json.put("game", Bot.jda.getPresence().getActivity() == null ? "N/A" : Bot.jda.getPresence().getActivity().getName());
        json.put("status", Bot.jda.getPresence().getStatus());
        json.put("commands", CustomCommandHandler.getCommands());
        json.put("keywords", KeywordHandler.getKeywords());

        context.response().setContentType("application/json; charset=UTF-8");
        context.response().setStatus(HttpServletResponse.SC_OK);
        osw.write(json.toString());
        osw.close();

    }
}
