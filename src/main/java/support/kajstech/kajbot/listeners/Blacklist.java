package support.kajstech.kajbot.listeners;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.command.commands.Permit;
import support.kajstech.kajbot.utils.Config;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Blacklist extends ListenerAdapter {

    private static final Pattern URL_REGEX = Pattern.compile("((?:(http|https|Http|Https|rtsp|Rtsp)://(?:(?:[a-zA-Z0-9$\\-_.+!*'(),;?&amp;=]|(?:%[a-fA-F0-9]{2})){1,64}(?::(?:[a-zA-Z0-9$\\-_.+!*'(),;?&amp;=]|(?:%[a-fA-F0-9]{2})){1,25})?@)?)?((?:(?:[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}\\.)+(?:(?:aero|arpa|asia|a[cdefgilmnoqrstuwxz])|(?:biz|b[abdefghijmnorstvwyz])|(?:cat|com|coop|c[acdfghiklmnoruvxyz])|d[ejkmoz]|(?:edu|e[cegrstu])|f[ijkmor]|(?:gov|g[abdefghilmnpqrstuwy])|h[kmnrtu]|(?:info|int|i[delmnoqrst])|(?:jobs|j[emop])|k[eghimnrwyz]|l[abcikrstuvy]|(?:mil|mobi|museum|m[acdghklmnopqrstuvwxyz])|(?:name|net|n[acefgilopruz])|(?:org|om)|(?:pro|p[aefghklmnrstwy])|qa|r[eouw]|s[abcdeghijklmnortuvyz]|(?:tel|travel|t[cdfghjklmnoprtvwz])|u[agkmsyz]|v[aceginu]|w[fs]|y[etu]|z[amw]))|(?:(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[0-9])))(?::\\d{1,5})?)(/(?:(?:[a-zA-Z0-9;/?:@&amp;=#~\\-.+!*'(),_])|(?:%[a-fA-F0-9]{2}))*)?(?:\\b|$)");

    private static void log(MessageReceivedEvent event, String link) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(new Color(0xA6C055));
        eb.setTimestamp(ZonedDateTime.now());
        eb.setTitle(event.getAuthor().getAsTag(), "https://discordapp.com/users/" + event.getAuthor().getId());
        eb.setDescription(link);
        event.getGuild().getTextChannelById(Config.get("Modlog channel ID")).sendMessage(eb.build()).queue();
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(System.getProperty("user.dir") + "//kajbot.log"), true), StandardCharsets.UTF_8));
            writer.newLine();
            writer.write(ZonedDateTime.now().format(DateTimeFormatter.ofPattern(Language.getMessage("Logging.TIME_FORMAT"))) + " - (" + event.getGuild().getName() + " - #" + event.getChannel().getName() + ") " + event.getAuthor().getAsTag() + ": " + link);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void log(MessageUpdateEvent event, String link) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(new Color(0xA6C055));
        eb.setTimestamp(ZonedDateTime.now());
        eb.setTitle(event.getAuthor().getAsTag(), "https://discordapp.com/users/" + event.getAuthor().getId());
        eb.setDescription(link);
        event.getGuild().getTextChannelById(Config.get("Modlog channel ID")).sendMessage(eb.build()).queue();
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(System.getProperty("user.dir") + "//kajbot.log"), true), StandardCharsets.UTF_8));
            writer.newLine();
            writer.write(ZonedDateTime.now().format(DateTimeFormatter.ofPattern(Language.getMessage("Logging.TIME_FORMAT"))) + " - (" + event.getGuild().getName() + " - #" + event.getChannel().getName() + ") " + event.getAuthor().getAsTag() + ": " + link);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        if (!Config.get("Link blacklist").equalsIgnoreCase("true") || Permit.permitted.contains(event.getMessage().getMember()) || event.getMessage().isWebhookMessage() || !event.getChannelType().isGuild() || event.getAuthor().isBot())
            return;

        for (String c : Config.get("Blacklist bypass roles").split(", ")) {
            if (event.getMember().getRoles().stream().anyMatch(r -> r.getName().equals(c))) {
                return;
            }
        }


        Matcher m = URL_REGEX.matcher(event.getMessage().getContentRaw());
        if (m.find()) {
            log(event, m.group(0));
            event.getChannel().sendMessage((Language.getMessage("BlacklistListener.BLACKLIST_ENABLED")).replace("%USER%", event.getMember().getAsMention())).queue();
            event.getMessage().delete().queue();
        }
    }

    public void onMessageUpdate(MessageUpdateEvent event) {
        if (!Config.get("Link blacklist").equalsIgnoreCase("true") || Permit.permitted.contains(event.getMessage().getMember()) || event.getMessage().isWebhookMessage() || !event.getChannelType().isGuild() || event.getAuthor().isBot())
            return;

        for (String c : Config.get("Blacklist bypass roles").split(", ")) {
            if (event.getMember().getRoles().stream().anyMatch(r -> r.getName().equals(c))) {
                return;
            }
        }


        Matcher m = URL_REGEX.matcher(event.getMessage().getContentRaw());
        if (m.find()) {
            log(event, m.group(0));
            event.getChannel().sendMessage((Language.getMessage("BlacklistListener.BLACKLIST_ENABLED")).replace("%USER%", event.getMember().getAsMention())).queue();
            event.getMessage().delete().queue();
        }
    }

}
