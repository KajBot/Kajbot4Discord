package support.kajstech.kajbot.command.commands;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.command.Command;
import support.kajstech.kajbot.command.CommandEvent;
import support.kajstech.kajbot.utils.Config;
import support.kajstech.kajbot.utils.LogHelper;

import java.util.List;

public class Purge extends Command {
    public Purge() {
        this.name = "purge";
        this.guildOnly = true;
        this.requiredRole = Config.get("Bot admin role");
    }

    @Override
    public void execute(CommandEvent e) {
        try {
            int AMOUNT = Integer.parseInt(e.getArgsSplit().get(0));
            if (AMOUNT > 100) {
                int _100s = AMOUNT / 100;
                int _1s = AMOUNT - _100s * 100;
                int _AMOUNT = AMOUNT;
                for (int i = 0; i < _100s + 1; ++i) {
                    if (_AMOUNT >= 100) {
                        _AMOUNT = _AMOUNT - 100;
                        MessageHistory history = new MessageHistory(e.getChannel());
                        List<Message> msgs;
                        msgs = history.retrievePast(100).complete();
                        e.getChannel().deleteMessages(msgs).queue();
                    } else if (_1s > 0) {
                        MessageHistory history = new MessageHistory(e.getChannel());
                        List<Message> msgs;
                        msgs = history.retrievePast(_1s).complete();
                        e.getChannel().deleteMessages(msgs).queue();
                    }

                }
                e.reply((Language.getMessage("Purge.SUCCESS")).replace("%AMOUNT%", e.getArgsSplit().get(0)));
            } else {
                MessageHistory history = new MessageHistory(e.getChannel());
                List<Message> msgs;
                msgs = history.retrievePast(AMOUNT).complete();
                e.getChannel().deleteMessages(msgs).queue();
                e.reply((Language.getMessage("Purge.SUCCESS")).replace("%AMOUNT%", e.getArgsSplit().get(0)));
            }
        } catch (Exception ex) {
            LogHelper.error(this.getClass(), ex, e.getMessage().getContentRaw());
            e.reply(Language.getMessage("Purge.ERROR"));
        }
    }

}
