package support.kajstech.kajbot.listeners;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.handlers.ConfigHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class MessageLogger extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {
        if (!ConfigHandler.getProperty("Message logging").equalsIgnoreCase("true") || event.getMessage().isWebhookMessage() || !event.getChannelType().isGuild() || event.getAuthor().isBot() || event.getMessage().isWebhookMessage())
            return;


        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(System.getProperty("user.dir") + "\\chat.log"), true), StandardCharsets.UTF_8));
            writer.newLine();
            writer.write(ZonedDateTime.now().format(DateTimeFormatter.ofPattern(Language.getMessage("Logging.TIME_FORMAT"))) + " - (" + event.getGuild().getName() + " - #" + event.getChannel().getName() + ") " + event.getAuthor().getAsTag() + ": " + event.getMessage().getContentRaw());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
