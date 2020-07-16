package logging;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

import org.apache.logging.log4j.LogManager;

import log.LoggerSettings;

public class LogControl {
	
    public static Logger a_Logger = Logger.getLogger(A.class.getName());
    
    public static Logger b_Logger = Logger.getLogger(B.class.getName());
    
    public static Logger c_Logger = Logger.getLogger(C.class.getName());
    //public static Logger model_Logger ... maybe KNN Logger, misc loggers. 

    public static void changeLoggerSettings(LoggerSettings settings) {
        
    }
    
    public static void changeLoggerSettings(Logger logger, LoggerSettings settings ) { 
        
    }
    public static ConsoleHandler ch = new ConsoleHandler();
}
