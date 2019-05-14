package support.kajstech.kajbot.web.context.API.v1;

import com.sun.net.httpserver.HttpExchange;
import org.json.JSONObject;
import support.kajstech.kajbot.Bot;
import support.kajstech.kajbot.command.CustomCommandsHandler;
import support.kajstech.kajbot.utils.Config;
import support.kajstech.kajbot.handlers.KeywordHandler;
import support.kajstech.kajbot.utils.LogHelper;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class APIServerV1 {
    public static void context(HttpExchange http) throws IOException {
        LogHelper.info("Connection from: " + http.getRemoteAddress().getAddress().getHostAddress() + ":" + http.getRemoteAddress().getPort());

        OutputStreamWriter osw = new OutputStreamWriter(http.getResponseBody(), StandardCharsets.UTF_8);

        JSONObject json = new JSONObject();

        try {
            Map<String, String> args = qToM(http.getRequestURI().getQuery());
            if (args.containsKey("token") && args.get("token").contentEquals(Config.get("API token"))) {
                json.put("game", Bot.jda.getPresence().getGame().getName());
                json.put("status", Bot.jda.getPresence().getStatus());
                json.put("commands", CustomCommandsHandler.getCustomCommands());
                json.put("keywords", KeywordHandler.getKeywords());

                http.getResponseHeaders().add("Content-Type", "application/json");
                http.sendResponseHeaders(200, 0);
            } else {
                json.put("401", "Unauthorized");
                http.sendResponseHeaders(401, 0);
            }

        } catch (Exception ignored) {
            json.put("401", "Unauthorized");
            http.sendResponseHeaders(401, 0);
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
