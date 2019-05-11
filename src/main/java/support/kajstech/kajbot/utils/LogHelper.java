package support.kajstech.kajbot.utils;


import net.dv8tion.jda.api.EmbedBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.kajstech.kajbot.Bot;
import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.handlers.ConfigHandler;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class LogHelper {

    private static Logger logger = LoggerFactory.getLogger("[Kajbot]");

    /**
     * helper class d() to log debug level information.
     */
    public static void debug(Class cls, String message) {
        logToFile(cls + ": " + message);
        logger.debug(cls + ": " + message);
    }


    /**
     * helper class i() to log info level information.
     */
    public static void info(String message) {
        logToFile(message);
        logger.info(message);
    }

    /**
     * helper class w() to log warning level information.
     */
    public static void warning(Class cls, String message) {
        logToFile(cls + ": " + message);
        logger.warn(cls + ": " + message);
    }


    /**
     * helper class e() to log error information.
     */
    public static void error(Class cls, Exception ex, String message) {
        logToFile(cls + ": " + ex);
        logger.error(cls + ": " + ex);
        try {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("ERROR");
            eb.setDescription("```" + message + "```");
            eb.setColor(new Color(0xFF0000));
            eb.addField("Class:", "```" + cls.getName() + "```", false);
            eb.addField("Exception: " + ex.getClass().getName(), "```" + ex.getLocalizedMessage() + "```", false);
            eb.setTimestamp(ZonedDateTime.now());

            Bot.jda.getUserById(ConfigHandler.getProperty("Bot owner ID")).openPrivateChannel().queue((channel) -> channel.sendMessage(eb.build()).queue());
        } catch (Exception e) {
            error(LogHelper.class, e.getLocalizedMessage());
        }
    }

    public static void error(Class cls, String message) {
        logToFile(cls + ": " + message);
        logger.error(cls + ": " + message);
    }


    /**
     * helper class t() to log trace information.
     */
    public static void trace(Class cls, String message) {
        logToFile(cls + ": " + message);
        logger.trace(cls + ": " + message);
    }


    private static void logToFile(String message) {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(System.getProperty("user.dir") + "/kajbot.log"), true), StandardCharsets.UTF_8));
            writer.newLine();
            writer.write(ZonedDateTime.now().format(DateTimeFormatter.ofPattern(Language.getMessage("Logging.TIME_FORMAT"))) + " - " + message);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}