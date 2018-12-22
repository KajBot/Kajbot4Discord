package support.kajstech.kajbot.web.context.API;

import com.sun.net.httpserver.HttpExchange;
import org.json.JSONObject;
import support.kajstech.kajbot.Bot;
import support.kajstech.kajbot.handlers.ConfigHandler;
import support.kajstech.kajbot.handlers.CustomCommandsHandler;
import support.kajstech.kajbot.handlers.KeywordHandler;
import support.kajstech.kajbot.utils.LogHelper;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class APIv1 {
    public static void context(HttpExchange httpExchange) throws IOException {
        OutputStreamWriter osw = new OutputStreamWriter(httpExchange.getResponseBody(), StandardCharsets.UTF_8);

        JSONObject json = new JSONObject();

        try {
            Map<String, String> args = qToM(httpExchange.getRequestURI().getQuery());
            if (args.containsKey("token") && args.get("token").contentEquals(ConfigHandler.getProperty("API token"))) {
                json.put("game", Bot.jda.getPresence().getGame().getName());
                json.put("status", Bot.jda.getPresence().getStatus());
                json.put("commands", CustomCommandsHandler.getCommands());
                json.put("keywords", KeywordHandler.getKeywords());

                httpExchange.sendResponseHeaders(200, 0);
            } else {
                json.put("401", "Unauthorized");
                httpExchange.sendResponseHeaders(401, 0);
            }

        } catch (Exception ignored) {
            json.put("401", "Unauthorized");
            httpExchange.sendResponseHeaders(401, 0);
        }

        osw.write(json.toString());
        osw.close();


        LogHelper.info(LogHelper.server, "Connection established: " + httpExchange.getRemoteAddress().getAddress().getHostAddress() + ":" + httpExchange.getRemoteAddress().getPort());
    }

    private static Map<String, String> qToM(String query) {
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length > 1) {
                result.put(pair[0], pair[1]);
            } else {
                result.put(pair[0], "");
            }
        }
        return result;
    }
}
