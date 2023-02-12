/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CA2;
import java.security.NoSuchAlgorithmException;
import java.io.Serializable;
/**
 * Class: DIT/FT/1B/02 
 * Name: Leong Yu Zhi Andy 
 * Admission Number: P2205865
 * @author leong
 */
public class Administrator extends Users implements Serializable{
    private String pw;
    //More constructors,getter and setters! Why am i not surprised
    public Administrator(String memberID, String name,String pw) throws NoSuchAlgorithmException{
        super(memberID,name);
        this.pw = pw;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
    
}
