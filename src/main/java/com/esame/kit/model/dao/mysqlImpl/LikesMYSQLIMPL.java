package com.esame.kit.model.dao.mysqlImpl;

import com.esame.kit.model.dao.LikeDAO;
import com.esame.kit.model.dao.exception.DuplicatedObjectException;
import com.esame.kit.model.dao.exception.NonExistObjectException;
import com.esame.kit.model.mo.Like;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LikesMYSQLIMPL implements LikeDAO {
    Connection connection;

    public LikesMYSQLIMPL(Connection connection){
        this.connection = connection;
    }

    @Override
    public void create(Long userID, String classType, Long valueID) throws RuntimeException {
        PreparedStatement ps;
        try {
            String sql = "SELECT * FROM Likes"
                    + " WHERE "
                    + " userID = ? AND"
                    + " classType  = ? AND "
                    + " valueID = ?";
            ps = this.connection.prepareStatement(sql);
            int i = 1;
            ps.setString(i++, String.valueOf(userID));
            ps.setString(i++,classType);
            ps.setString(i, String.valueOf(valueID));
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                throw  new DuplicatedObjectException("Likes relation gia existente");
            }else{
                sql = " insert into Likes (userID, classType, valueID) VALUES (?,?,?)";
                ps = this.connection.prepareStatement(sql);
                i = 1;
                ps.setString(i++, String.valueOf(userID));
                ps.setString(i++,classType);
                ps.setString(i, String.valueOf(valueID));
                ps.executeUpdate();
            }
            ps.close();
            rs.close();
        }catch (SQLException | DuplicatedObjectException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Like like) throws NonExistObjectException {
        PreparedStatement ps;
        try {
            String sql = "SELECT * FROM Likes"
                    + " WHERE "
                    + " userID = ? AND"
                    + " classType  = ? AND "
                    + " valueID = ?";
            ps = this.connection.prepareStatement(sql);
            int i = 1;
            ps.setString(i++, String.valueOf(like.getUserID()));
            ps.setString(i++, like.getClassType());
            ps.setString(i, String.valueOf(like.getValueID()));
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                sql = " DELETE FROM Likes WHERE userID = ? AND classType  = ? AND valueID = ? ";
                ps = this.connection.prepareStatement(sql);
                i = 1;
                ps.setString(i++, String.valueOf(like.getUserID()));
                ps.setString(i++, like.getClassType());
                ps.setString(i, String.valueOf(like.getValueID()));
                ps.executeUpdate();
            }else{
                throw  new NonExistObjectException("Likes relation non esistente");
            }
            ps.close();
            rs.close();
        }catch (SQLException  e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Like> getAllLikesByClassTypeAndID(String classType, Long valueID) {
        List <Like> likes = new ArrayList<>();
        PreparedStatement ps;
        try {
            String sql = "SELECT * FROM Likes"
                    + " WHERE "
                    + " classType  = ? AND "
                    + " valueID = ?";
            ps = this.connection.prepareStatement(sql);
            int i = 1;
            ps.setString(i++, classType);
            ps.setString(i, String.valueOf(valueID));
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                likes.add(readLikeResult(rs));
            }
            ps.close();
            rs.close();
        }catch (SQLException  e){
            throw new RuntimeException(e);
        }
        return likes;
    }

    @Override
    public Like getLikes(Long userID, String classType, Long valueID) {
        Like like = null;
        PreparedStatement ps;
        try {
            String sql = "SELECT * FROM Likes"
                    + " WHERE "
                    + "userID = ? AND"
                    + " classType  = ? AND "
                    + " valueID = ?";
            ps = this.connection.prepareStatement(sql);
            int i = 1;
            ps.setString(i++, String.valueOf(userID));
            ps.setString(i++, classType);
            ps.setString(i, String.valueOf(valueID));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) like= readLikeResult(rs);
            ps.close();
            rs.close();
        }catch (SQLException  e){
            throw new RuntimeException(e);
        }
        return like;
    }

    @Override
    public List<Like> getAllLikesByUser(Long  userID) {
        List <Like> likes = new ArrayList<>();
        PreparedStatement ps;
        try {
            String sql = "SELECT * FROM Likes"
                    + " WHERE "
                    + " userID = ?";
            ps = this.connection.prepareStatement(sql);
            ps.setString(1, String.valueOf(userID));
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                likes.add(readLikeResult(rs));
            }
            ps.close();
            rs.close();
        }catch (SQLException  e){
            throw new RuntimeException(e);
        }
        return likes;
    }

    public static Like readLikeResult(ResultSet rs){
        Like like = new Like();
        try {
            like.setUserID(Long.parseLong(rs.getString("userID")));
            like.setClassType(rs.getString("classType"));
            like.setValueID(Long.parseLong(rs.getString("valueID")));
        }catch (SQLException e){
            throw  new RuntimeException(e);
        }
        return like;
    }
}
