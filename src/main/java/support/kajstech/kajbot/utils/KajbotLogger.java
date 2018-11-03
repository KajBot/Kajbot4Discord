package support.kajstech.kajbot.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KajbotLogger {

    private static Logger log = LoggerFactory.getLogger("[Kajbot]");

    /**
     * helper class d() to log debug level information.
     */
    public static void debug(Class cls, String message) {
        log.debug(cls + ": " + message);
    }


    /**
     * helper class i() to log info level information.
     */
    public static void info(String message) {
        log.info(message);
    }


    /**
     * helper class w() to log warning level information.
     */
    public static void warning(Class cls, String message) {
        log.warn(cls + ": " + message);
    }


    /**
     * helper class e() to log error information.
     */
    public static void error(Class cls, String message) {
        log.error(cls + ": " + message);
    }


    /**
     * helper class t() to log trace information.
     */
    public static void trace(Class cls, String message) {
        log.trace(cls + ": " + message);
    }
}