package support.kajstech.kajbot.command.commands;

import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.command.Command;
import support.kajstech.kajbot.command.CommandEvent;
import support.kajstech.kajbot.utils.Config;
import support.kajstech.kajbot.utils.LogHelper;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Ascii extends Command {
    private static final String asciiArtUrl = "http://artii.herokuapp.com/";

    public Ascii() {
        this.name = "ascii";
        this.guildOnly = false;
        this.requiredRole = Config.cfg.get("Bot admin role");
    }

    private int randomNum(int start, int end) {

        if (end < start) {
            int temp = end;
            end = start;
            start = temp;
        }
        return (int) Math.floor(Math.random() * (end - start + 1) + start);
    }

    private String getAsciiArt(String ascii, String font) {
        try {
            String url = asciiArtUrl + "make" + "?text=" + ascii.replaceAll(" ", "+") + (font == null || font.isEmpty() ? "" : "&font=" + font);
            return new Scanner(new URL(url).openStream(), String.valueOf(StandardCharsets.UTF_8)).useDelimiter("\\A").next();
        } catch (IOException e) {
            LogHelper.error(this.getClass(), e, null);
            return Language.getMessage("ASCII.ERROR_RETRIEVING_TEXT");
        }
    }

    private List<String> getAsciiFonts() {
        String url = asciiArtUrl + "fonts_list";
        List<String> fontList = null;
        try {
            String list = new Scanner(new URL(url).openStream(), String.valueOf(StandardCharsets.UTF_8)).useDelimiter("\\A").next();
            fontList = Arrays.stream(list.split("\n")).collect(Collectors.toList());
        } catch (IOException e) {
            LogHelper.error(this.getClass(), e, null);
        }

        return fontList;
    }


    @Override
    public void execute(CommandEvent e) {
        if (e.getArgs().length() < 1) return;
        StringBuilder input = new StringBuilder();
        for (int i = 0; i < e.getArgsSplit().toArray().length; i++) {
            input.append(i == e.getArgsSplit().toArray().length - 1 ? e.getArgsSplit().get(i) : e.getArgsSplit().get(i) + " ");

            List<String> fonts = getAsciiFonts();
            String font = fonts.get(randomNum(0, fonts.size() - 1));

            try {
                String ascii = getAsciiArt(input.toString(), font);

                if (ascii.length() > 1900) {
                    e.reply("```fix\n\n " + Language.getMessage("ASCII.TOO_BIG") + "```");
                    return;
                }

                e.reply("**Font:** " + font + "\n```fix\n\n" + ascii + "```");
            } catch (IllegalArgumentException iae) {
                LogHelper.error(this.getClass(), iae, e.getMessage().getContentRaw());
                e.reply("```fix\n\n" + Language.getMessage("ASCII.INVALID_CHARACTERS") + "```");
            }
        }
    }
}
