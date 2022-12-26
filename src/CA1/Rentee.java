/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CA1;
import java.util.ArrayList;
import java.security.NoSuchAlgorithmException;
/**
 *
 * @author leong
 */
public class Rentee extends Users{
    private ArrayList<Comic> comics;

    public Rentee(String memberID, String name, ArrayList<Comic> comics,String pw,String login) throws NoSuchAlgorithmException {
        super(memberID,name,"1",pw,login);
        this.comics = comics;
    }

    public ArrayList<Comic> getComics() {
        return comics;
    }

    public void setComics(ArrayList<Comic> comics) {
        this.comics = comics;
    }
    
    
}