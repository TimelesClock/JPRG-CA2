/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CA1;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class: DIT/FT/1B/02 
 * Name: Leong Yu Zhi Andy 
 * Admission Number: P2205865
 * @author leong
 */
public class Users {
    private String memberID;
    private String name;
    private String permissionLevel;
    private String password;
    private String lastLogin;
    
    public Users(String newMemberID, String newName,String newPermissionLevel,String pw,String login) throws NoSuchAlgorithmException{
        this.memberID = newMemberID;
        this.name = newName;
        this.permissionLevel = newPermissionLevel;
        this.password = pw;
        this.lastLogin = login;
    }
    
    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(String permissionLevel) {
        this.permissionLevel = permissionLevel;
    }
    
    public String getPassword(){
        return password;
    }
    
    public String getLogin(){
        return lastLogin;
    }
    
    public String hash(String pw) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(pw.getBytes());
        String stringHash = new String(messageDigest.digest()); //So that i dont get a byte[]
        //Note to self, use equals when comparing hash to verify pw don tuse ==
        return stringHash;
    }
    
}
