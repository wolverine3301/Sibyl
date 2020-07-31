package log;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import bayes.NaiveBayes2;

import java.io.IOException;
import dataframe.DataFrame;
import logging.HTMLFormatter;
import ranker.Recollection;
import scorer.ConfusionMatrix;
import scorer.CrossValidation;
import scorer.Score;

public class Loggers {
	public static FileHandler logFile;
    //public static ConsoleHandler ch = new ConsoleHandler();
	public static HTMLFormatter formatterHTML = new HTMLFormatter();
	
    public static Logger df_Logger = Logger.getLogger(DataFrame.class.getName());
    public static Logger score_Logger = Logger.getLogger(Score.class.getName());
    public static Logger cv_Logger = Logger.getLogger(CrossValidation.class.getName());
    public static Logger cm_Logger = Logger.getLogger(ConfusionMatrix.class.getName());
    public static Logger nb_Logger = Logger.getLogger(NaiveBayes2.class.getName());
    public static Logger recollection_Logger = Logger.getLogger(Recollection.class.getName());
    //public static Logger model_Logger ... maybe KNN Logger, misc loggers. 
    
    public static void changeLoggerSettings(LoggerSettings settings) {
        
    }
    
    public static void changeLoggerSettings(Logger logger, LoggerSettings settings ) { 
        
    }
    public static void logHTML(Logger logger,Level lvl)  {
    	try {
			logFile = new FileHandler(logger.getName()+"-log.%u.%g.html");
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	logFile.setFormatter(formatterHTML);
    	logFile.setLevel(lvl);
    	logger.setLevel(lvl);
    	logger.addHandler(logFile);
    }

}
