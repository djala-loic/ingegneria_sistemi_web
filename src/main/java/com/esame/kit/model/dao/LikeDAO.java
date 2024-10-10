package com.esame.kit.model.dao;

import com.esame.kit.model.dao.exception.DuplicatedObjectException;
import com.esame.kit.model.dao.exception.NonExistObjectException;
import com.esame.kit.model.mo.Like;

import java.util.List;

public interface LikeDAO {
    public abstract  void create (Long userID,String classType,Long valueID) throws DuplicatedObjectException;
    public  abstract  void delete(Like like) throws NonExistObjectException;

    public abstract List<Like> getAllLikesByClassTypeAndID(String classType, Long valueID);


    Like getLikes(Long userID, String classType, Long valueID);

    public abstract List<Like> getAllLikesByUser(Long  userID);
}
