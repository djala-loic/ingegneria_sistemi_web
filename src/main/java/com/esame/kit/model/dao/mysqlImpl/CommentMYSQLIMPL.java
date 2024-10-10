package com.esame.kit.model.dao.mysqlImpl;

import com.esame.kit.model.dao.CommentDAO;
import com.esame.kit.model.dao.exception.DuplicatedObjectException;
import com.esame.kit.model.dao.exception.NonExistObjectException;
import com.esame.kit.model.mo.Comment;
import com.esame.kit.model.mo.Template;
import com.esame.kit.model.mo.User;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommentMYSQLIMPL implements CommentDAO {

    private final String COUNTER_ID = "commentCounterId";
    private final
    Connection connection;
    public CommentMYSQLIMPL(Connection connection){
        this.connection=connection;
    }

    @Override
    public Comment create(Template template, User user, String body)  {
        PreparedStatement ps ;
        String sql;

        Comment comment;
        try{

            sql = "update counter set counter_value=counter_value+1 where counter_id='" + COUNTER_ID + "'";
            ps = this.connection.prepareStatement(sql);
            ps.executeUpdate();

            sql = "SELECT counter_value FROM counter where counter_id='" + COUNTER_ID + "'";
            ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            rs.next();

            comment = new Comment(Long.parseLong(rs.getString("counter_value")), template, user, body);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            sql = " INSERT INTO comments (commentID,templateID,userID,body) VALUES (?,?,?,?)";

            ps = this.connection.prepareStatement(sql);
            int i = 1;

            ps.setString(i++, String.valueOf(comment.getCommentID()));
            ps.setString(i++, String.valueOf(comment.getTemplate().getTemplateID()));
            ps.setString(i++, String.valueOf(comment.getUser().getUserId()));
            ps.setString(i,comment.getBody());

            ps.executeUpdate();

            ps.close();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return comment;
    }

    @Override
    public void delete(Comment comment, String mode) throws NonExistObjectException {
        PreparedStatement ps;
        String msgError = "tentativo di cancellazione un template non esistente";
        boolean deleteMode=  mode!=null && mode.equals("forever");
        try{
            String sql = "SELECT * FROM comments"
                    + " WHERE "
                    + " commentID = ?";

            if (deleteMode){
                sql+=" AND deleteState = true ";
            }else{
                sql+=" AND deleteState = false ";
            }

            ps = this.connection.prepareStatement(sql);
            ps.setString(1, String.valueOf(comment.getCommentID()));

            ResultSet rs = ps.executeQuery();
            boolean exist  = rs.next();
            if(exist) {
                if (!deleteMode) {
                    sql = "UPDATE comments "
                            + " SET "
                            + " deleteState = true "
                            + " WHERE "
                            + " commentID = ?";
                }else {
                    sql = "DELETE FROM comments WHERE commentID = ?";

                    String sqlLikes = "DELETE FROM likes WHERE classType='Comment' AND valueID = ?";

                    ps = this.connection.prepareStatement(sqlLikes);
                    ps.setString(1, String.valueOf(comment.getCommentID()));
                    ps.executeUpdate();
                }
                ps = this.connection.prepareStatement(sql);
                ps.setString(1, String.valueOf(comment.getCommentID()));
                ps.executeUpdate();


                ps.close();
            }else throw  new NonExistObjectException(msgError);
        }catch (SQLException  e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public void update(Comment comment) throws NonExistObjectException {
        PreparedStatement ps;
        String msgError = "tentativo di update un comment non esistente";
        try{
            String sql = "SELECT * FROM comments"
                    + " WHERE "
                    + " commentID = ?";

            ps = this.connection.prepareStatement(sql);
            ps.setString(1, String.valueOf(comment.getCommentID()));
            ResultSet rs = ps.executeQuery();

            boolean exist = rs.next();
            if(!exist) throw  new NonExistObjectException(msgError);

            // update il mio post
            sql = "UPDATE comments "
                    + " SET "
                    + " userID = ? ,"
                    + " templateID = ? ,"
                    + " body = ?"
                    + " WHERE "
                    + " commentID = ? ";

            ps= this.connection.prepareStatement(sql);
            int i= 1;
            ps.setString(i++, String.valueOf(comment.getUser().getUserId()));
            ps.setString(i++, String.valueOf(comment.getTemplate().getTemplateID()));
            ps.setString(i++,comment.getBody());
            ps.setString(i, String.valueOf(comment.getCommentID()));

            ps.executeUpdate();
            ps.close();
        }catch (SQLException e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public void restore(Comment comment) throws NonExistObjectException {
        PreparedStatement ps;
        try{
            String sql = "SELECT * FROM comments"
                    + " WHERE "
                    + " deleteState = true AND"
                    + " commentID = ?";
            ps = this.connection.prepareStatement(sql);
            ps.setString(1, String.valueOf(comment.getCommentID()));

            ResultSet rs = ps.executeQuery();
            boolean exist  = rs.next();
            if(exist) {
                sql = "UPDATE comments "
                        + " SET "
                        + " deleteState = false "
                        + " WHERE "
                        + " commentID = ?";

                ps = this.connection.prepareStatement(sql);
                ps.setString(1, String.valueOf(comment.getCommentID()));
                ps.executeUpdate();


                ps.close();
            }else throw  new NonExistObjectException("tentativo di delezione un template non exixtente");
        }catch (SQLException e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public Comment getCommentMode(String mode, Long userID, Long commentID, Long templateID) {
        Comment comment =null;
        PreparedStatement ps ;
        try {
            String sql = "SELECT * FROM comments"
                    + " WHERE ";

            if(mode!=null){
            if (mode.equals("templateID")){
                sql+=" templateID = ? ";
            }else if (mode.equals("userID")){
                sql+=" userID = ? ";
            }else {
                sql+=" commentID = ? ";
            }
            }else{
                sql+=" commentID = ? ";
            }

            ps = this.connection.prepareStatement(sql);
            if(mode!=null){
            if (mode.equals("templateID")){
                ps.setString(1, String.valueOf(templateID));
            }else if (mode.equals("userID")){
                ps.setString(1, String.valueOf(userID));
            }else{
                ps.setString(1, String.valueOf(commentID));
            }}else{
                ps.setString(1, String.valueOf(commentID));
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) comment = readCommentResult(rs);
            rs.close();
            ps.close();
        }catch (SQLException e){
            throw  new RuntimeException(e);
        }
        return comment;
    }

    @Override
    public List<Comment> getComments(User user, Template template, String mode, String by) {
        ArrayList<Comment> comments = new ArrayList<>();
        PreparedStatement ps ;

        try {
            String sql = "SELECT * FROM comments";

            if(mode.equals("offLine")){
                sql+=" WHERE deleteState = true ";
            }

            if(mode.equals("onLine")){
                sql+=" WHERE deleteState = false ";
            }

            if(mode==null && by.equals("user") && user!= null){
                sql+="WHERE userID = "+user.getUserId()+" ";
            }

            if(mode==null && by.equals("template") && template!= null){
                sql+="WHERE templateID = "+template.getTemplateID()+" ";
            }

            if(mode!=null && by.equals("user") && user!= null){
                sql+=" AND userID = "+user.getUserId()+" ";
            }

            if(mode!=null && by.equals("template") && template!= null){
                sql+=" AND templateID = "+template.getTemplateID()+" ";
            }

            sql+= "ORDER BY created_at DESC";
            ps = this.connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                comments.add(readCommentResult(rs));
            }

            rs.close();
            ps.close();
        }catch (SQLException e){
            throw  new RuntimeException(e);
        }
        return comments;
    }

    private Comment readCommentResult(ResultSet rs){
        Comment comment = new Comment();
        try{
            comment.setUser(new User());
            comment.setTemplate(new Template());
            comment.getUser().setUserId(Long.parseLong(rs.getString("userID")));
            comment.getTemplate().setTemplateID( Long.parseLong(rs.getString("templateID")));
            comment.setCommentID(Long.parseLong(rs.getString("commentID")));
            comment.setBody(rs.getString("body"));
            comment.setCreatedAt(rs.getString("created_at"));
            comment.setDeleteState(Boolean.valueOf(rs.getString("deleteState")));
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return comment;
    }

}
