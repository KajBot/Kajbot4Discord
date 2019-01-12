package support.kajstech.kajbot.command.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.command.ICommand;
import support.kajstech.kajbot.utils.LogHelper;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Ascii implements ICommand {

    private static final String asciiArtUrl = "http://artii.herokuapp.com/";

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
            e.printStackTrace();
            LogHelper.error(Ascii.class, e.toString());
        }

        return fontList;
    }


    @Override
    public void handle(List<String> argsSplit, String args, MessageReceivedEvent event) {
        if (args.length() < 1) return;
        StringBuilder input = new StringBuilder();
        for (int i = 0; i < argsSplit.toArray().length; i++) {
            input.append(i == argsSplit.toArray().length - 1 ? argsSplit.get(i) : argsSplit.get(i) + " ");

            List<String> fonts = getAsciiFonts();
            String font = fonts.get(randomNum(0, fonts.size() - 1));

            try {
                String ascii = getAsciiArt(input.toString(), font);

                if (ascii.length() > 1900) {
                    event.getChannel().sendMessage("```fix\n\n " + Language.getMessage("ASCII.TOO_BIG") + "```").queue();
                    return;
                }

                event.getChannel().sendMessage("**Font:** " + font + "\n```fix\n\n" + ascii + "```").queue();
            } catch (IllegalArgumentException iae) {
                event.getChannel().sendMessage("```fix\n\n" + Language.getMessage("ASCII.INVALID_CHARACTERS") + "```").queue();
            }
        }
    }

    @Override
    public String getName() {
        return "ascii";
    }
}
