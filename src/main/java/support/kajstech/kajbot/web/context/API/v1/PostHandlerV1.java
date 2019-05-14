package support.kajstech.kajbot.web.context.API.v1;

import com.sun.net.httpserver.HttpExchange;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import org.json.JSONObject;
import support.kajstech.kajbot.Bot;
import support.kajstech.kajbot.command.CommandManager;
import support.kajstech.kajbot.utils.Config;
import support.kajstech.kajbot.handlers.KeywordHandler;
import support.kajstech.kajbot.utils.LogHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class PostHandlerV1 {

    public static void context(HttpExchange http) throws IOException {
        LogHelper.info("Post request from: " + http.getRemoteAddress().getAddress().getHostAddress() + ":" + http.getRemoteAddress().getPort());

        InputStreamReader isr = new InputStreamReader(http.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);

        int b;
        StringBuilder buf = new StringBuilder(512);
        while ((b = br.read()) != -1) {
            buf.append((char) b);
        }
        br.close();
        isr.close();

        System.out.println(buf.toString());
        JSONObject body = new JSONObject(buf.toString());

        if (!http.getRequestHeaders().containsKey("token") || !http.getRequestHeaders().get("token").get(0).equals(Config.get("API token")) || body.length() < 1)
            return;

        if (!body.isNull("add_command")) {
            body.getJSONObject("add_command").toMap().forEach((k, v) -> CommandManager.addCustomCommand(k, (String) v));
        }

        if (!body.isNull("remove_command")) {
            body.getJSONObject("remove_command").names().forEach((k) -> CommandManager.removeCustomCommand((String) k));
        }

        if (!body.isNull("add_keyword")) {
            body.getJSONObject("add_keyword").toMap().forEach((k, v) -> KeywordHandler.addKeyword(k, (String) v));
        }

        if (!body.isNull("remove_keyword")) {
            body.getJSONObject("remove_keyword").names().forEach((k) -> KeywordHandler.removeKeyword((String) k));
        }

        if (!body.isNull("set_status")) {
            if (!body.getJSONObject("set_status").isNull("game")) {
                Config.set("Bot game", body.getJSONObject("set_status").getString("game"));
                Bot.jda.getPresence().setGame(Game.playing(body.getJSONObject("set_status").getString("game")));
            }

            if (!body.getJSONObject("set_status").isNull("online")) {
                String status = body.getJSONObject("set_status").getString("online");
                if (status.equalsIgnoreCase("dnd")) status = OnlineStatus.DO_NOT_DISTURB.toString();
                if (!(status.equalsIgnoreCase(OnlineStatus.INVISIBLE.toString()) || status.equalsIgnoreCase(OnlineStatus.ONLINE.toString()) || status.equalsIgnoreCase(OnlineStatus.DO_NOT_DISTURB.toString()) || status.equalsIgnoreCase(OnlineStatus.IDLE.toString())))
                    return;
                Bot.jda.getPresence().setStatus(OnlineStatus.valueOf(status.toUpperCase()));
            }
        }


        String response = "SUCCESS";
        http.sendResponseHeaders(200, response.length());
        OutputStream os = http.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();

    }
}
