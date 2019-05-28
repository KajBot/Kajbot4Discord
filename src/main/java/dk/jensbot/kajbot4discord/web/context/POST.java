package dk.jensbot.kajbot4discord.web.context;

import dk.jensbot.kajbot4discord.Bot;
import dk.jensbot.kajbot4discord.command.CommandManager;
import dk.jensbot.kajbot4discord.handlers.KeywordHandler;
import dk.jensbot.kajbot4discord.utils.Config;
import dk.jensbot.kajbot4discord.web.Context;
import dk.jensbot.kajbot4discord.web.Servlet;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class POST extends Servlet {
    public POST() {
        this.name = "post";
        this.path = "/api";
    }

    @Override
    protected void post(Context context) throws ServletException, IOException {
        OutputStream os = context.response().getOutputStream();

        InputStreamReader isr = new InputStreamReader(context.request().getInputStream(), StandardCharsets.UTF_8);
        Scanner s = new Scanner(isr).useDelimiter("\\A");
        String requestBody = s.hasNext() ? s.next() : "";
        isr.close();


        JSONObject body = new JSONObject(requestBody);

        if (body.isEmpty() || context.request().getHeader("token").isEmpty() || !context.request().getHeader("token").equals(Config.cfg.get("API-token"))) {
            context.response().setStatus(HttpServletResponse.SC_BAD_REQUEST);
            os.close();
        }

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
                Config.cfg.set("Bot-game", body.getJSONObject("set_status").getString("game"));
                Bot.jda.getPresence().setGame(Game.playing(body.getJSONObject("set_status").getString("game")));
            }

            if (!body.getJSONObject("set_status").isNull("online")) {
                String status = body.getJSONObject("set_status").getString("online");
                if (status.equalsIgnoreCase("dnd")) status = OnlineStatus.DO_NOT_DISTURB.toString();
                if (!(status.equalsIgnoreCase(OnlineStatus.INVISIBLE.toString()) || status.equalsIgnoreCase(OnlineStatus.ONLINE.toString()) || status.equalsIgnoreCase(OnlineStatus.DO_NOT_DISTURB.toString()) || status.equalsIgnoreCase(OnlineStatus.IDLE.toString())))
                    return;
                Bot.jda.getPresence().setStatus(OnlineStatus.valueOf(status.toUpperCase()));
            }

            if (!body.getJSONObject("set_status").isNull("activity")) {
                String status = body.getJSONObject("set_status").getString("activity");
                if (status.equalsIgnoreCase("playing")) status = Game.GameType.DEFAULT.toString();
                if (!(status.equalsIgnoreCase(Game.GameType.DEFAULT.toString()) || status.equalsIgnoreCase(Game.GameType.LISTENING.toString()) || status.equalsIgnoreCase(Game.GameType.WATCHING.toString()) || status.equalsIgnoreCase(Game.GameType.STREAMING.toString())))
                    return;
                Bot.jda.getPresence().setGame(Game.of(Game.GameType.valueOf(status.toUpperCase()), Bot.jda.getPresence().getGame().getName()));
            }
        }


        context.response().setStatus(HttpServletResponse.SC_OK);
        os.close();

    }
}
