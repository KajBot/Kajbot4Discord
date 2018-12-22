package support.kajstech.kajbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.handlers.ConfigHandler;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ascii extends Command {

    private final static String asciiArtUrl = "http://artii.herokuapp.com/";

    public ascii() {
        this.name = "ascii";
        this.guildOnly = false;
        this.requiredRole = ConfigHandler.getProperty("Bot controller role");
    }

    private static int randomNum(int start, int end) {

        if (end < start) {
            int temp = end;
            end = start;
            start = temp;
        }
        return (int) Math.floor(Math.random() * (end - start + 1) + start);
    }

    private static String getAsciiArt(String ascii, String font) {
        try {
            String url = asciiArtUrl + "make" + "?text=" + ascii.replaceAll(" ", "+") +
                    (font == null || font.isEmpty() ? "" : "&font=" + font);
            return new Scanner(new URL(url).openStream(), "UTF-8").useDelimiter("\\A").next();
        } catch (IOException e) {
            return Language.messages.getProperty("ASCII.ERROR_RETRIEVING_TEXT");
        }
    }

    private static List<String> getAsciiFonts() {
        String url = asciiArtUrl + "fonts_list";
        List<String> fontList = null;
        try {
            String list = new Scanner(new URL(url).openStream(), "UTF-8").useDelimiter("\\A").next();

            fontList = Arrays.stream(list.split("\n")).collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return fontList;
    }

    @Override
    protected void execute(CommandEvent e) {
        if (e.getArgs().length() < 1) return;
        String[] args = e.getArgs().split(" ");

        StringBuilder input = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            input.append(i == args.length - 1 ? args[i] : args[i] + " ");

            List<String> fonts = getAsciiFonts();
            String font = fonts.get(randomNum(0, fonts.size() - 1));

            try {
                String ascii = getAsciiArt(input.toString(), font);

                if (ascii.length() > 1900) {
                    e.getChannel().sendMessage("```fix\n\n " + Language.messages.getProperty("ASCII.TOO_BIG") + "```").queue();
                    return;
                }

                e.getChannel().sendMessage("**Font:** " + font + "\n```fix\n\n" + ascii + "```").queue();
            } catch (IllegalArgumentException iae) {
                e.getChannel().sendMessage("```fix\n\n" + Language.messages.getProperty("ASCII.INVALID_CHARACTERS") + "```").queue();
            }
        }
    }

}
