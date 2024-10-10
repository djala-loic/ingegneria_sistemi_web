package com.esame.kit.services.config;

import com.esame.kit.model.dao.DAOFactory;

import java.util.Calendar;
import java.util.logging.Level;

public class Configuration {

    /*TIMEZONE SERVER configuration*/
    public  static  final  String SERVER_TIMEZONE= Calendar.getInstance().getTimeZone().getID();

    /* Database Configuration */
    public  static  final  String DAO_IMPL= DAOFactory.MYSQLJDBCIMPL;
    public  static  final  String DATABASE_DRIVER="com.mysql.cj.jdbc.Driver";
    public  static  final  String DATABASE_URL="jdbc:mysql://localhost/helpUs?user=root&password=root1969&useSSL=false&serverTimezone="+SERVER_TIMEZONE;


    /* Session Configuration*/
    public  static  final  String COOKIE_IMPL=DAOFactory.COOKIEIMPL;

    /*Logger Configuration */
    public  static  final  String GLOBAL_LOGGER_NAME="helpUs";
    public  static  final  String GLOBAL_LOGGER_FILE="/Users/anders/Desktop/work/JSP/progettoSistemiWeb/logs/helper_log.%g.%u.txt";
    public  static  final  Level GLOBAL_LOGGER_LEVEL= Level.ALL;

}
