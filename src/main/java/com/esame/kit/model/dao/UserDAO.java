package com.esame.kit.model.dao;

import com.esame.kit.model.dao.exception.DuplicatedObjectException;
import com.esame.kit.model.dao.exception.NonExistObjectException;
import com.esame.kit.model.mo.User;

import java.util.List;

public interface UserDAO {
    /**
    * @param mode String
     * mode puo valere all, true, false // indica il modo con cui vanno raccolti gli elementi nel db a seconda
    * il deleteState
    **/
    public  abstract List<User> getAllUsers(String mode, String role, String search);

    /**
     * @param  mode String
     *  mode puo valere id, email, username // indica il modo con cui si cerca lo detto user in db a seconda id, email, username
     **/
    public abstract User findUser(String mode, Long userID, String username, String email);
    public  abstract  User findLoggedUser();

    User create(Long userId, String username, String password, String firstName, String secondName, String languagesCodePreferences, String email, String role) throws DuplicatedObjectException;

    public abstract void  update(User user) throws NonExistObjectException;

    /**
     * @param  mode String
     *  mode puo valere null, forever // indica il modo con cui si delete lo detto user in db a seconda lo deleteState
     **/
    public abstract void delete(User user,String mode) throws NonExistObjectException;

    public  abstract  void restoreUser(User user) throws NonExistObjectException;

    /**
     * @param  role String
     *  mode puo valere admin o creatore o lettore // indica il modo con cui si delete lo detto  user in db a seconda lo deleteState
     **/
    public  abstract  void changeRole(User user, String role) throws NonExistObjectException;
}