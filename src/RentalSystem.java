/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jprg.ca1;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import java.awt.Font;
/**
 *
 * @author leong
 */
public class RentalSystem {
    private Comic[] comicArr = new Comic[4];
    
    public RentalSystem(){}
    
    public void createComic(){
        comicArr[0] = new Comic("978-0785199618","Spider-Man: Miles Morales",112,14.39);
        comicArr[1] = new Comic("978-0785190219","Ms. Marvel: No Normal",120,15.99);
        comicArr[2] = new Comic("978-0785198956","Secret Wars",312,34.99);
        comicArr[3] = new Comic("978-0785156598","Infinity Gauntlet",256,24.99);
    }
    
    public void displayComic(){
        int len;
        String text = String.format("%-15s| %-30s| %-7s| %-10s| %s\n%s\n","ISBN-13","Title","Pages","Price/Day","Deposit","-".repeat(80));
        
        for (Comic i : this.comicArr){
            text += String.format("%-15s| %-30s| %-7d| %-10.2f| %.2f\n",i.getISBN(),i.getTitle(),i.getPageNum(),(i.getCost()/20.0),(i.getCost()*1.10));
        }
        UIManager.put("OptionPane.messageFont", new Font("Courier New", Font.BOLD, 15));
        UIManager.put("OptionPane.buttonFont", new Font("Courier New", Font.BOLD, 15));
        JOptionPane.showMessageDialog(
                null,
                text,
                "All Comics",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    public static void main(String [] args){
//        RentalSystem rental = new RentalSystem();
//        rental.createComic();
//        rental.displayComic();
    }
}