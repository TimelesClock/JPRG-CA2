/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jprg.ca1;

/**
 *
 * @author leong
 */
public class Rentee {
    private int memberID;
    private String name;
    private Comic[] comics;

    public Rentee(int memberID, String name, Comic[] comics) {
        this.memberID = memberID;
        this.name = name;
        this.comics = comics;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Comic[] getComics() {
        return comics;
    }

    public void setComics(Comic[] comics) {
        this.comics = comics;
    }
    
    
}