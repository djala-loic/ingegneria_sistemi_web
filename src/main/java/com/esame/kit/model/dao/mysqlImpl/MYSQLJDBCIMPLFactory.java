package com.esame.kit.model.dao.mysqlImpl;

import com.esame.kit.model.dao.*;
import com.esame.kit.services.config.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class MYSQLJDBCIMPLFactory extends DAOFactory {
    private Map factoryParameter;
    private Connection connection;

    public MYSQLJDBCIMPLFactory(Map factoryParameter){
        this.factoryParameter=factoryParameter;
    }


    @Override
    public void beginTransaction() {
        try {
            Class.forName(Configuration.DATABASE_DRIVER);
            this.connection = DriverManager.getConnection(Configuration.DATABASE_URL);
            if (this.connection!=null)
                this.connection.setAutoCommit(false);

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void commitTransaction() {
        try {
            if (this.connection!=null)
                this.connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void rollbackTransaction() {
        try {
            if (this.connection!=null)
                this.connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void closeTransaction() {
        try {
            if (this.connection!=null)
                this.connection.close();
        } catch (SQLException e) {
            throw  new RuntimeException(e);
        }
    }

    @Override
    public UserDAO getUserDAO() {
        return new UserDAOMYSQLIMPL(this.connection);
    }

    @Override
    public TemplateDAO getTemplateDAO() {
        return  new TemplateDAOMYSQLIMPL(this.connection);
    }

    @Override
    public CommentDAO getCommentDAO() {
        return  new CommentMYSQLIMPL(this.connection);
    }

    @Override
    public LikeDAO getLikeDAO() {
        return new LikesMYSQLIMPL(this.connection);
    }

    @Override
    public NoteDAO getNoteDAO() {
        return new NoteDAOMSQLIMPL(this.connection);
    }

    @Override
    public PrenotazioneDAO getPrenotazioneDAO() {
        return new PrenotazioneDAOMYSQLIMPL(this.connection);
    }

}
