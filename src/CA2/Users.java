/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CA2;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.Serializable;

/**
 * Class: DIT/FT/1B/02 
 * Name: Leong Yu Zhi Andy 
 * Admission Number: P2205865
 * @author leong
 */
public class Users implements Serializable{
    private String memberID;
    private String name;

    //Just normal Getter Setters
    public Users(String newMemberID, String newName) throws NoSuchAlgorithmException{
        this.memberID = newMemberID;
        this.name = newName;
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
    //Cant remember why a 3rd version of this is here but im too scared to break anything at this point lmao
    public String hash(String pw) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(pw.getBytes());
        String stringHash = new String(messageDigest.digest()); //So that i dont get a byte[]
        //Note to self, use equals when comparing hash to verify pw don tuse ==
        return stringHash;
    }
    
}
