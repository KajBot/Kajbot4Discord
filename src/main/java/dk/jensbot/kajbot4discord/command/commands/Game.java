package dk.jensbot.kajbot4discord.command.commands;

import dk.jensbot.kajbot4discord.command.Command;
import dk.jensbot.kajbot4discord.command.CommandEvent;
import dk.jensbot.kajbot4discord.utils.Config;
import dk.jensbot.kajbot4discord.utils.Language;
import net.dv8tion.jda.api.entities.Activity;

public class Game extends Command {
    public Game() {
        this.name = "game";
        this.adminCommand = true;
    }

    @Override
    public void execute(CommandEvent e) {
        if (e.getArgsSplit().get(0).length() < 1) return;

        e.getJDA().getPresence().setActivity(Activity.playing(e.getArgs()));
        Config.cfg.set("Bot.game", e.getArgs());
        e.reply((Language.lang.get("Status.SET")).replace("%STATUS%", e.getArgs()));
    }
}
