package be.vn.util;

import org.apache.log4j.Logger;

/**
 * Log Util
 *
 * @author TuanDV <tuandv@hospital.vn>
 */
public class LogUtil {
    /**
     * Log4j instance
     */
    private static Logger logger = null;

    /**
     * Create instance
     */
    private static void init() {
        if (logger == null) {
            logger = Logger.getLogger(LogUtil.class);
        }
    }

    /**
     * Output DEBUG log.
     *
     * @param msg Log output message
     */
    public static void trace(String msg) {
        init();
        logger.trace(msg);
    }

    /**
     * Output DEBUG log.
     *
     * @param msg Log output message
     * @param ex  Exception class
     */
    public static void trace(String msg, Throwable ex) {
        init();
        logger.trace(msg, ex);
    }

    /**
     * Output DEBUG log.
     *
     * @param msg Log output message
     */
    public static void debug(String msg) {
        init();
        logger.debug(msg);
    }

    /**
     * Output DEBUG log.
     *
     * @param msg Log output message
     * @param ex  Exception class
     */
    public static void debug(String msg, Throwable ex) {
        init();
        logger.debug(msg, ex);
    }

    /**
     * Output the INFO log.
     *
     * @param msg Log output message
     */
    public static void info(String msg) {
        init();
        logger.info(msg);
    }

    /**
     * Output the INFO log.
     *
     * @param msg Log output message
     * @param ex  Exception class
     */
    public static void info(String msg, Throwable ex) {
        init();
        logger.info(msg, ex);
    }

    /**
     * Output WARN log.
     *
     * @param msg Log output message
     */
    public static void warn(String msg) {
        init();
        logger.warn(msg);
    }

    /**
     * Output WARN log.
     *
     * @param msg Log output message
     * @param ex  Exception class
     */
    public static void warn(String msg, Throwable ex) {
        init();
        logger.warn(msg, ex);
    }

    /**
     * Output WARN log.
     *
     * @param msg Log output message
     */
    public static void error(String msg) {
        init();
        logger.error(msg);
    }

    /**
     * Output WARN log.
     *
     * @param ex Exception class
     */
    public static void error(Throwable ex) {
        init();
        logger.error("Type: " + ex.toString() + ", detail: ", ex);
    }

    /**
     * Output WARN log.
     *
     * @param msg Log output message
     * @param ex  Exception class
     */
    public static void error(String msg, Throwable ex) {
        init();
        logger.error(msg, ex);
    }

    /**
     * Output of FATAL log.
     *
     * @param msg Log output message
     */
    public static void fatal(String msg) {
        init();
        logger.fatal(msg);
    }

    /**
     * Output of FATAL log.
     *
     * @param msg Log output message
     * @param ex  Exception class
     */
    public static void fatal(String msg, Throwable ex) {
        init();
        logger.fatal(msg, ex);
    }
}
