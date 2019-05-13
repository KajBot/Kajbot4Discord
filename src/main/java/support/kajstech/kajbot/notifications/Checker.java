package support.kajstech.kajbot.notifications;

import net.dv8tion.jda.core.hooks.ListenerAdapter;
import support.kajstech.kajbot.utils.LogHelper;

import java.io.IOException;

public class Checker extends ListenerAdapter {

    public static void run() throws InterruptedException {

        //SITES
        try {
            Twitch.check();
            YouTube.check();
        } catch (IOException e) {
            LogHelper.error(Checker.class, e.toString());
        }

        Thread.sleep(60000);
    }
}
