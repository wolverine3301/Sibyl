package log;

import java.util.logging.Logger;

import dataframe.DataFrame;

public class Loggers {
    
    public static Logger df_Logger = Logger.getLogger(DataFrame.class.getName());
    //public static Logger model_Logger ... maybe KNN Logger, misc loggers. 
    
    public static void changeLoggerSettings(LoggerSettings settings) {
        
    }
    
    public static void changeLoggerSettings(Logger logger, LoggerSettings settings ) { 
        
    }
}
