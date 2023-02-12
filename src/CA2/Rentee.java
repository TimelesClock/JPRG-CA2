/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CA2;
import java.util.ArrayList;
import java.security.NoSuchAlgorithmException;
import java.io.Serializable;
/**
 * Class: DIT/FT/1B/02 
 * Name: Leong Yu Zhi Andy 
 * Admission Number: P2205865
 * @author leong
 */
public class Rentee extends Users implements Serializable{
    private ArrayList<Comic> comics;
    //Normal constructor getter setter code right here, move on
    public Rentee(String memberID, String name, ArrayList<Comic> comics) throws NoSuchAlgorithmException {
        super(memberID,name);
        this.comics = comics;
    }

    public ArrayList<Comic> getComics() {
        return comics;
    }

    public void setComics(ArrayList<Comic> comics) {
        this.comics = comics;
    }
    
    
}