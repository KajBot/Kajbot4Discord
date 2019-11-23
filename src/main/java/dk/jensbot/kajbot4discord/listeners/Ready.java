package dk.jensbot.kajbot4discord.listeners;

import dk.jensbot.kajbot4discord.Bot;
import dk.jensbot.kajbot4discord.Main;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Ready extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event) {
        Bot.jda = event.getJDA();
    }
}
