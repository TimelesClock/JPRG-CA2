/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca2.skye;
import java.util.ArrayList;
import java.io.Serializable;
/**

 * @author leong
 */
public class Rentee implements Serializable{
    private ArrayList<Comic> comics;
    private String memberID;
    private String name;


    public Rentee( String memberID, String name,ArrayList<Comic> comics) {
        this.comics = comics;
        this.memberID = memberID;
        this.name = name;
    }

    public ArrayList<Comic> getComics() {
        return comics;
    }

    public void setComics(ArrayList<Comic> comics) {
        this.comics = comics;
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
    
    
    
    
}