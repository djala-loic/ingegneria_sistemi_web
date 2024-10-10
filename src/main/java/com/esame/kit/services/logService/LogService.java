package com.esame.kit.services.logService;

import java.io.IOException;
import java.util.logging.*;
import  com.esame.kit.services.config.Configuration;

public class LogService {

    private  static Logger applicationLogger;

    private LogService (){}

    public static Logger getApplicationLogger(){
        SimpleFormatter formatterTxt;
        Handler fileHandler;

        try{
            if( applicationLogger == null){
                applicationLogger = Logger.getLogger(Configuration.GLOBAL_LOGGER_NAME);//I create the logger
                fileHandler = new FileHandler(Configuration.GLOBAL_LOGGER_FILE); //I create file witch constant  name present in configuration file
                formatterTxt= new SimpleFormatter(); // I create the txt formatter
                fileHandler.setFormatter(formatterTxt); // Set my fileHandler witch formatterTxt
                applicationLogger.addHandler(fileHandler);
                applicationLogger.setLevel(Configuration.GLOBAL_LOGGER_LEVEL);
                applicationLogger.setUseParentHandlers(false);
                applicationLogger.log(Level.CONFIG,"Logger {0} created",applicationLogger.getName());
            }
        }catch (IOException e){
            applicationLogger.log(Level.SEVERE,"Error occurred in Logger creation");
            throw new  RuntimeException(e);
        }
        return applicationLogger;
    }
}
