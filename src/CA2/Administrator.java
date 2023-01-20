/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CA2;
import java.security.NoSuchAlgorithmException;
/**
 * Class: DIT/FT/1B/02 
 * Name: Leong Yu Zhi Andy 
 * Admission Number: P2205865
 * @author leong
 */
public class Administrator extends Users{
    public Administrator(String memberID, String name,String pw,String login) throws NoSuchAlgorithmException{
        super(memberID,name,"2",pw,login);
    }
}
