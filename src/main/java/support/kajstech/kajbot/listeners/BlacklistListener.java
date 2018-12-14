package support.kajstech.kajbot.listeners;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.commands.permit;
import support.kajstech.kajbot.utils.ConfigManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BlacklistListener extends ListenerAdapter {

    private static final Pattern URL_REGEX = Pattern.compile("((?:(http|https|Http|Https|rtsp|Rtsp)://(?:(?:[a-zA-Z0-9$\\-_.+!*'(),;?&amp;=]|(?:%[a-fA-F0-9]{2})){1,64}(?::(?:[a-zA-Z0-9$\\-_.+!*'(),;?&amp;=]|(?:%[a-fA-F0-9]{2})){1,25})?@)?)?((?:(?:[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}\\.)+(?:(?:aero|arpa|asia|a[cdefgilmnoqrstuwxz])|(?:biz|b[abdefghijmnorstvwyz])|(?:cat|com|coop|c[acdfghiklmnoruvxyz])|d[ejkmoz]|(?:edu|e[cegrstu])|f[ijkmor]|(?:gov|g[abdefghilmnpqrstuwy])|h[kmnrtu]|(?:info|int|i[delmnoqrst])|(?:jobs|j[emop])|k[eghimnrwyz]|l[abcikrstuvy]|(?:mil|mobi|museum|m[acdghklmnopqrstuvwxyz])|(?:name|net|n[acefgilopruz])|(?:org|om)|(?:pro|p[aefghklmnrstwy])|qa|r[eouw]|s[abcdeghijklmnortuvyz]|(?:tel|travel|t[cdfghjklmnoprtvwz])|u[agkmsyz]|v[aceginu]|w[fs]|y[etu]|z[amw]))|(?:(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[0-9])))(?::\\d{1,5})?)(/(?:(?:[a-zA-Z0-9;/?:@&amp;=#~\\-.+!*'(),_])|(?:%[a-fA-F0-9]{2}))*)?(?:\\b|$)");

    public void onMessageReceived(MessageReceivedEvent event) {
        if (permit.permitted.contains(event.getMember()) || !ConfigManager.getProperty("Link blacklist").equalsIgnoreCase("true") || event.getAuthor() == event.getJDA().getSelfUser() || event.getMessage().getAuthor().isBot() || event.getMember().getRoles().stream().anyMatch(r -> r.getName().equals(ConfigManager.getProperty("Bot controller role"))))
            return;

        Matcher m = URL_REGEX.matcher(event.getMessage().getContentRaw());
        if (m.find()) {
            event.getChannel().sendMessage((Language.messages.getProperty("BlacklistListener.BLACKLIST_ENABLED")).replace("%USER%", event.getMember().getAsMention())).queue();
            event.getMessage().delete().queue();
        }
    }
}
