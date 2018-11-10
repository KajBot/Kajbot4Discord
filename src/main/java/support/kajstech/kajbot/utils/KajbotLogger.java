package support.kajstech.kajbot.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KajbotLogger {

    public static Logger bot = LoggerFactory.getLogger("[Discord]");
    public static Logger server = LoggerFactory.getLogger("[Server]");

    /**
     * helper class d() to log debug level information.
     */
    public static void debug(Logger logger, Class cls, String message) {
        logger.debug(cls + ": " + message);
    }


    /**
     * helper class i() to log info level information.
     */
    public static void info(Logger logger, String message) {
        logger.info(message);
    }


    /**
     * helper class w() to log warning level information.
     */
    public static void warning(Logger logger, Class cls, String message) {
        logger.warn(cls + ": " + message);
    }


    /**
     * helper class e() to log error information.
     */
    public static void error(Logger logger, Class cls, String message) {
        logger.error(cls + ": " + message);
    }


    /**
     * helper class t() to log trace information.
     */
    public static void trace(Logger logger, Class cls, String message) {
        logger.trace(cls + ": " + message);
    }
}