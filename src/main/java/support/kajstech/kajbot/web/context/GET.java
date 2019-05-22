package support.kajstech.kajbot.web.context;

import org.json.JSONObject;
import support.kajstech.kajbot.Bot;
import support.kajstech.kajbot.command.CustomCommandsHandler;
import support.kajstech.kajbot.handlers.KeywordHandler;
import support.kajstech.kajbot.utils.Config;
import support.kajstech.kajbot.web.Context;
import support.kajstech.kajbot.web.Servlet;

import javax.servlet.ServletException;
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

    @Override
    protected void get(Context context) throws ServletException, IOException {
        OutputStreamWriter osw = new OutputStreamWriter(context.response().getOutputStream(), StandardCharsets.UTF_8);

        JSONObject json = new JSONObject();
        try {
            Map<String, String> args = qToM(context.request().getQueryString());
            if (args.containsKey("token") && args.get("token").contentEquals(Config.cfg.get("API token"))) {
                json.put("game", Bot.jda.getPresence().getGame().getName());
                json.put("status", Bot.jda.getPresence().getStatus());
                json.put("commands", CustomCommandsHandler.getCustomCommands());
                json.put("keywords", KeywordHandler.getKeywords());

                context.response().setContentType("application/json");
            } else {
                json.put("401", "Unauthorized");
                context.response().setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }

        } catch (Exception ignored) {
            json.put("401", "Unauthorized");
            context.response().setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        osw.write(json.toString());
        osw.close();

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
}
