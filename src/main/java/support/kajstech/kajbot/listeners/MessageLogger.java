package support.kajstech.kajbot.listeners;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.handlers.ConfigHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageLogger extends ListenerAdapter {

    private static File logPath = new File(System.getProperty("user.dir") + "\\chat.log");

    public void onMessageReceived(MessageReceivedEvent event) {
        if (!ConfigHandler.getProperty("Message logging").equalsIgnoreCase("true") || event.getMessage().isWebhookMessage() || !event.getChannelType().isGuild())
            return;
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logPath, true), StandardCharsets.UTF_8));
            writer.newLine();
            writer.write(LocalDateTime.now().format(DateTimeFormatter.ofPattern(Language.getMessage("Logging.TIME_FORMAT"))) + " - (" + event.getGuild().getName() + " - #" + event.getChannel().getName() + ") " + event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator() + ": " + event.getMessage().getContentRaw());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
