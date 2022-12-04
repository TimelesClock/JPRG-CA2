/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CA1;

import java.util.*;
import javax.swing.*;
import java.awt.Font;

/**
 *
 * @author leong
 */
public class RentalMenu {

    public static void main(String[] args) {
        String check[] = {"1", "2", "3", "4", "5"};

        UIManager.put("OptionPane.messageFont", new Font("Monospaced", Font.BOLD, 15));
        UIManager.put("OptionPane.buttonFont", new Font("Monospaced", Font.BOLD, 15));
        RentalSystem rental = new RentalSystem();
        rental.createComic();
        rental.createRentee();
        String UserInput;
        HashMap<String, Runnable> dict = new HashMap<String, Runnable>();

        dict.put("1", () -> rental.displayComic());
        dict.put("2", () -> rental.findComic());
        dict.put("3",()-> rental.findMember());

        String menu = """
                      Enter your option:
                      
                      1. Display all Comics
                      2. Search Comic by ISBN-13
                      3. Search Rentee by MemberID
                      4. Print Earning Statistic
                      5. Exit
                      """;
        UserInput = (JOptionPane.showInputDialog(
                null,
                menu,
                "Input",
                JOptionPane.QUESTION_MESSAGE));
        while (!("5".equals(UserInput))) {
            if (Arrays.asList(check).contains(UserInput)) {
                dict.get(UserInput).run();
            }else{
                JOptionPane.showMessageDialog(
                        null,
                        "Invalid option! Please enter in the range from 1 to 5.",
                        "Input",
                        JOptionPane.ERROR_MESSAGE);
            }
            UserInput = (JOptionPane.showInputDialog(
                        null,
                        menu,
                        "Input",
                        JOptionPane.QUESTION_MESSAGE));
        }
        JOptionPane.showMessageDialog(
                null,
                "Thank you for using Comic Rental.\nWe look forward to serve you in the near future.",
                "Input",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
