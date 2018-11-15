package support.kajstech.kajbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import support.kajstech.kajbot.utils.ConfigManager;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class info extends Command {

    public info() {
        this.name = "info";
        this.guildOnly = true;
        this.requiredRole = ConfigManager.getProperty("Bot controller role");
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
    protected void execute(CommandEvent event) {
        if(event.getArgs().length() > 0){
            List<User> userMention = event.getMessage().getMentionedUsers();
            for (User user : userMention) {
                embedUser(user, event.getGuild().getMember(user), event);
            }
        }else{
            embedUser(event.getAuthor(), event.getMember(), event);
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
        game = stat == null ? "N/A" : member.getGame() == null ? "N/A" : member.getGame().getName();

        /* Time */
        join = member == null ? "N/A" : DateTimeFormatter.ofPattern("d/M/u HH:mm:ss").format(member.getJoinDate());
        register = DateTimeFormatter.ofPattern("d/M/u HH:mm:ss").format(user.getCreationTime());

        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(nickname, null, icon).setThumbnail(icon);

        embed.addField(":spy: Identity", "ID: `" + id + "`\n" +
                "Username: `" + name + "#" + dis + "`", true);

        embed.addField(":first_quarter_moon: Status", "Game: `" + game + "`\nStatus: `" + status + "`\n", true);

        embed.addField(":stopwatch: Time", "Joined server: `" + join + "`\n" +
                "Account created: `" + register + "`\n", true);

        e.getChannel().sendMessage(embed.build()).queue();
    }

}
