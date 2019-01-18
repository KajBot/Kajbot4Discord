package support.kajstech.kajbot.command.commands;


import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.command.Command;
import support.kajstech.kajbot.command.CommandEvent;
import support.kajstech.kajbot.handlers.ConfigHandler;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class Info extends Command {

    public Info() {
        this.name = "info";
        this.guildOnly = true;
        this.requiredRole = ConfigHandler.getProperty("Bot admin role");
    }

    private static String VariableToString(String regex, String input) {
        String[] splitting = new String[]{input};
        if (regex != null) splitting = input.split(regex);
        StringBuilder splittedBuilder = new StringBuilder();
        for (String s : splitting) {
            splittedBuilder.append(s.substring(0, 1).toUpperCase(Locale.ENGLISH)).append(s.substring(1).toLowerCase(Locale.ENGLISH)).append(" ");
        }
        String splitted = splittedBuilder.toString();
        return splitted.substring(0, splitted.length() - 1);
    }

    @Override
    public void execute(CommandEvent e) {
        if (e.getArgs().length() > 0) {
            List<User> userMention = e.getMessage().getMentionedUsers();
            for (User user : userMention) {
                embedUser(user, e.getGuild().getMember(user), e);
            }
        } else {
            embedUser(e.getEvent().getAuthor(), e.getEvent().getMember(), e);
        }
    }

    private void embedUser(User user, Member member, CommandEvent e) {
        String name, id, dis, nickname, icon, status, game, join, register;

        icon = user.getEffectiveAvatarUrl();

        /* Identity */
        name = user.getName();
        id = user.getId();
        dis = user.getDiscriminator();
        nickname = member == null || member.getNickname() == null ? name : member.getEffectiveName();

        /* Status */
        OnlineStatus stat = member == null ? null : member.getOnlineStatus();
        status = stat == null ? "N/A" : VariableToString("_", stat.getKey());
        game = stat == null ? "N/A" : member.getActivities().size() < 1 ? "N/A" : member.getActivities().get(0).getName();

        /* Time */
        join = member == null ? "N/A" : DateTimeFormatter.ofPattern("d/M/u HH:mm:ss").format(member.getTimeJoined());
        register = DateTimeFormatter.ofPattern("d/M/u HH:mm:ss").format(user.getTimeCreated());

        /* Final */
        EmbedBuilder embed = new EmbedBuilder().setAuthor(nickname, null, icon).setThumbnail(icon);

        //embed.addField(":spy: Identity", "ID: `" + id + "`\n" + "Username: `" + name + "#" + dis + "`", true);
        embed.addField(":spy: " + Language.getMessage("Info.IDENTITY"), "ID: `" + id + "`\n" + Language.getMessage("Info.USERNAME") + "`" + name + "#" + dis + "`", true);
        embed.addField(":first_quarter_moon: Status", Language.getMessage("Info.GAME") + "`" + game + "`\n" + Language.getMessage("Info.STATUS") + "`" + status + "`\n", true);
        embed.addField(":stopwatch: " + Language.getMessage("Info.TIME"), Language.getMessage("Info.JOINED_SERVER") + "`" + join + "`\n" + Language.getMessage("Info.ACCOUNT_CREATED") + "`" + register + "`\n", true);
        embed.setTimestamp(ZonedDateTime.now());

        e.reply(embed.build());
    }

}
