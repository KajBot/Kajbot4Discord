package support.kajstech.kajbot.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.kajstech.kajbot.Language;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogHelper {

    private static File logPath = new File(System.getProperty("user.dir") + "\\kajbot.log");
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

    private static void logToFile(String message){
        try {
            if(!Files.exists(logPath.toPath())){
                Files.createFile(logPath.toPath());
            }
            Files.write(logPath.toPath(), (LocalDateTime.now().format(DateTimeFormatter.ofPattern(Language.getMessage("MessageLogger.TIME_FORMAT"))) + message).getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}