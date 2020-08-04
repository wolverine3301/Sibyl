package log;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import javax.swing.JTextArea;
import javax.swing.JTextPane;

import bayes.NaiveBayes2;

import java.io.IOException;
import java.io.PrintStream;

import dataframe.DataFrame;
import logan.sybilGUI.TextAreaOutputStream;
import logging.HTMLFormatter;
import logging.HTMLFormatter2;
import logging.StreamFormatter;
import logging.TextAreaHandler;
import logging.TextPaneHandler;
import ranker.Recollection;
import scorer.ConfusionMatrix;
import scorer.CrossValidation;
import scorer.Score;

public class Loggers {
	public static FileHandler logFile;
	public static TextAreaHandler textarea;
	public static StreamHandler stream;
    public static ConsoleHandler ch = new ConsoleHandler();
    
	public static HTMLFormatter formatterHTML = new HTMLFormatter();
	public static HTMLFormatter2 formatterHTML2 = new HTMLFormatter2();
	public static SimpleFormatter simple = new SimpleFormatter();
	
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
    public static void logToTextArea(Logger logger,Level lvl,JTextArea area) {
    	logger.setLevel(lvl);
    	textarea = new TextAreaHandler();
    	textarea.setTextArea(area);
    	textarea.setLevel(lvl);
    	logger.addHandler(textarea);
    	
    }
    public static void logToTextPane(Logger logger,Level lvl,JTextPane area) {
    	TextPaneHandler panHandler = new TextPaneHandler();
    	panHandler.setTextPane(area);
    	panHandler.setFormatter(formatterHTML2);
    	panHandler.setLevel(lvl);
    	logger.setLevel(lvl);
    	logger.addHandler(panHandler);
    	
    }
    public static void logToStream(Logger logger,Level lvl,PrintStream ps ) {
    	logger.setLevel(lvl);
    	StreamFormatter form = new StreamFormatter();
    	stream= new StreamHandler(ps,form);
    	stream.setLevel(lvl);
    	logger.addHandler(stream);
    	
    }
}
