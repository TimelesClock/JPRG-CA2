/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CA1;
import java.security.NoSuchAlgorithmException;
/**
 *
 * @author leong
 */
public class Administrator extends Users{
    public Administrator(String memberID, String name,String pw) throws NoSuchAlgorithmException{
        super(memberID,name,"2",pw);
    }
}
