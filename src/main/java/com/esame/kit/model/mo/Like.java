package com.esame.kit.model.mo;

import java.util.List;

/**
 * Relazioni :
 *     Like - User --> N : 1
 *     Like - Template --> N : 1
 *     Like - Comment --> N : 1
 */
public class Like {
    private  Long userID;
    private  String classType;
    private  Long valueID;

    public Like() {}

    public Like(long  userID, String classType,long valueID){
        this.userID = userID;
        this.classType = classType;
        this.valueID= valueID;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public long getValueID() {
        return valueID;
    }

    public void setValueID(long valueID) {
        this.valueID = valueID;
    }

    public static  boolean isLikeForUser(Long userID, Long valueID, List<Like> likes,String classType){
        for (Like like: likes) {
            if(like.getUserID() == userID
                    && like.getClassType().equals(classType)
                    && like.getValueID() == valueID){
                return true;
            }
        }
        return  false;
    }
}
