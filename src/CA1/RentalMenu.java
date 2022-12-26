/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CA1;

import java.util.*;
import javax.swing.*;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.NoSuchAlgorithmException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.text.SimpleDateFormat;

/**
 *
 * @author leong
 */
public class RentalMenu {

    private static boolean login = false; //only 1 static variable, cher dont kill me pls

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        do {

//        In a perfect world, sutff would be stored on a sql database using JDBC
            String check[] = {"1", "2", "3", "4", "5"};
            int permLevel = 0;
            UIManager.put("OptionPane.messageFont", new Font("Monospaced", Font.BOLD, 12));
            UIManager.put("OptionPane.buttonFont", new Font("Monospaced", Font.BOLD, 12));
            RentalSystem rental = new RentalSystem();

            //Keep these 2 lines of code if running for the first time else comment it out
//            rental.createComic();
//            rental.createRentee();
            //----------------------------------------------------------------------------- Login stuff
            List<String> memberID = new ArrayList<String>();

            File[] files = new File(new File("").getAbsolutePath() + "\\users\\").listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    memberID.add(file.getName().replaceFirst("[.][^.]+$", ""));
                }
            }
            while (!login) {
                String memberIn = JOptionPane.showInputDialog(
                        null,
                        "Welcome to Comic Rental, Please enter your member ID.",
                        "Input",
                        JOptionPane.QUESTION_MESSAGE);
                if (memberIn == null) {
                    System.exit(0);
                }

                if (memberID.contains(memberIn)) {
                    FileInputStream in = new FileInputStream(new File("").getAbsolutePath() + "\\users\\" + memberIn + ".properties");
                    Properties prop = new Properties();

                    prop.load(in);
                    in.close();
                    String passwordIn = JOptionPane.showInputDialog(
                            null,
                            "Please enter your password",
                            "Input",
                            JOptionPane.QUESTION_MESSAGE);

                    if (rental.hash(passwordIn).equals(prop.getProperty("password"))) {

                        permLevel = Integer.parseInt(prop.getProperty("permLevel"));

                        String time = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new java.util.Date());

                        FileOutputStream out = new FileOutputStream(new File("").getAbsolutePath() + "\\users\\" + memberIn + ".properties");

                        prop.setProperty("login", time);

                        prop.store(out, "Member Data");

                        out.close();

                        login = true;
                    } else {
                        JOptionPane.showMessageDialog(
                                null,
                                "Password is incorrect!",
                                "Input",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Invalid member ID!",
                            "Input",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

            //-----------------------------------------------------------------------------
            rental.importComics();
            rental.importProperties();

            String UserInput;
            HashMap<String, Runnable> dict = new HashMap<String, Runnable>();
            if (permLevel < 2) {
                dict.put("1", () -> rental.displayComic());
                dict.put("2", () -> rental.findComic());
                dict.put("3", () -> rental.findMember());
                dict.put("4", () -> rental.getEarning());

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

                while (!("5".equals(UserInput) && !(UserInput == null))) {
                    if (Arrays.asList(check).contains(UserInput)) {
                        dict.get(UserInput).run();
                    } else if (UserInput == null) {
                        break;
                    } else {
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
                login = false;
            } else {
                dict.put("1", () -> rental.displayComic());
                dict.put("2", () -> {
                    try {
                        rental.displayMember();
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                }
                );
                dict.put("3", () -> rental.findComic());
                dict.put("4", () -> rental.findMember());
                
                
                
                String menu = """
                      Enter your option:
                      
                      1. Display all Users
                      2. Display all Comics
                      3. Search Comic by ISBN-13
                      4. Search Rentee by MemberID
                      5. Add new user (Rentee/Admin)
                      6. Add new Comic
                      7. Print Earning Statistic
                      8. Import Existing data       
                      9. Exit
                      """;
                UserInput = (JOptionPane.showInputDialog(
                        null,
                        menu,
                        "Input",
                        JOptionPane.QUESTION_MESSAGE));

                while (!("9".equals(UserInput) && !(UserInput == null))) {
                    if (Arrays.asList(check).contains(UserInput)) {
                        dict.get(UserInput).run();
                    } else if (UserInput == null) {
                        break;
                    } else {
                        JOptionPane.showMessageDialog(
                                null,
                                "Invalid option! Please enter in the range from 1 to 9.",
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
                login = false;
            }
        } while (!login);
    }
}
