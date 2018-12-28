package support.kajstech.kajbot.listeners;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.handlers.ConfigHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageLogger extends ListenerAdapter {

    private static File logPath = new File(System.getProperty("user.dir") + "\\chat.log");

    public void onMessageReceived(MessageReceivedEvent event) {
        if(!ConfigHandler.getProperty("Message logging").equalsIgnoreCase("true")) return;
        if(event.getMessage().isWebhookMessage()) return;
        if(!event.getChannelType().isGuild()) return;

        try {
            if(!Files.exists(logPath.toPath())){
                Files.createFile(logPath.toPath());
            }
            log(" - (" + event.getGuild().getName() + " - " + event.getChannel().getName() + ") " + event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator() + ": " + event.getMessage().getContentRaw() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void log(String message){
        try {
            if(!Files.exists(logPath.toPath())){
                Files.createFile(logPath.toPath());
            }
            Files.write(logPath.toPath(), (LocalDateTime.now().format(DateTimeFormatter.ofPattern(Language.getMessage("MessageLogger.TIME_FORMAT"))) + message).getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
