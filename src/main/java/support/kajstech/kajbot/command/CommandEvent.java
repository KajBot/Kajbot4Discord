package support.kajstech.kajbot.command;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import support.kajstech.kajbot.handlers.ConfigHandler;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;

public class CommandEvent {

    private final MessageReceivedEvent event;
    private String args;
    private List<String> argsSplit;

    public CommandEvent(List<String> argsSplit, String args, MessageReceivedEvent event) {
        this.event = event;
        this.args = args;
        this.argsSplit = argsSplit;
    }

    public String getArgs() {
        return args;
    }

    public List<String> getArgsSplit() {
        return argsSplit;
    }

    public MessageReceivedEvent getEvent() {
        return event;
    }

    public TextChannel getChannel() {
        return event.getTextChannel();
    }

    public Message getMessage() {
        return event.getMessage();
    }

    public JDA getJDA() {
        return event.getJDA();
    }

    public Guild getGuild() {
        return event.getGuild();
    }

    public User getSelfUser() {
        return event.getJDA().getSelfUser();
    }

    boolean isOwner() {
        return event.getAuthor().getId().equals(ConfigHandler.getProperty("Bot owner ID"));
    }

    public void reply(String message) {
        event.getChannel().sendMessage(message).queue();
    }

    public void reply(String message, Consumer<Message> success) {
        event.getChannel().sendMessage(message).queue(success);
    }

    public void reply(MessageEmbed embed) {
        event.getChannel().sendMessage(embed).queue();
    }

    public void reply(MessageEmbed embed, Consumer<Message> success) {
        event.getChannel().sendMessage(embed).queue(success);
    }

    public void reply(File file) {
        event.getChannel().sendFile(file).queue();
    }

    public void reply(File file, Consumer<Message> success) {
        event.getChannel().sendFile(file).queue(success);
    }

}
