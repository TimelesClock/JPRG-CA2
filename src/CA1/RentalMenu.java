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
            int  permLevel = 0;
            UIManager.put("OptionPane.messageFont", new Font("Monospaced", Font.BOLD, 12));
            UIManager.put("OptionPane.buttonFont", new Font("Monospaced", Font.BOLD, 12));
            RentalSystem rental = new RentalSystem();

            rental.importComics();
            rental.importProperties();
            //Keep these 2 lines of code if running for the first time else comment it out
//            rental.createComic();
//            rental.createRentee();

            //----------------------------------------------------------------------------- Login stuff
            List<String> memberID = new ArrayList<String>();
            String filepath = new File("").getAbsolutePath() + "\\users\\";
            File[] files = new File(filepath).listFiles();
            for (File file : files) {
                if (file.isFile() && !("counter.properties").equals(file.getName())) {
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
                    if (passwordIn == null) {
                        System.exit(0);
                    }

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
                final int temp = permLevel;
                dict.put("5", () -> {
                    try {

                        addUser(temp,rental);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                });
            };

            String menu = """
                      Enter your option:
                      
                      1. Display all Users
                      2. Display all Comics
                      3. Search Comic by ISBN-13
                      4. Search Rentee by MemberID
                      5. Add/Remove Users
                      6. Add/Remove Comics
                      7. Print Earning Statistic      
                      8. Change Password
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
            export(rental);

            login = false;

        } while (!login);
    }

    private static void export(RentalSystem rental) {
        ArrayList<Administrator> admin = rental.getAdminArr();
        admin.forEach((i) -> {
            try {
                System.out.println(i);
                rental.exportAdmin(i);
            } catch (IOException e) {
                System.out.println(e);
            }
        });
        ArrayList<Rentee> rentee = rental.getRenteeArr();
        rentee.forEach((i) -> {
            try {
                rental.exportRentee(i);
            } catch (IOException e) {
                System.out.println(e);
            }
        });
        ArrayList<Comic> comic = rental.getComicArr();
        comic.forEach((i) -> {
            try {
                rental.exportComic(i);
            } catch (IOException e) {
                System.out.println(e);
            }
        });
    }
    
    public static void addUser(int permLevel,RentalSystem rental) throws NoSuchAlgorithmException, IOException {
        String menu;
        String in = "";
        if (permLevel == 3) {
            while (in != null && !in.equals("3")) {
                menu = """
                   Enter your option.
                       
                   1. Add new User
                   2. Remove existing User
                   3. Exit
                   """;

                in = (JOptionPane.showInputDialog(
                        null,
                        menu,
                        "Input",
                        JOptionPane.QUESTION_MESSAGE));

                if ("1".equals(in)) {
                    String name = (JOptionPane.showInputDialog(
                            null,
                            "Enter the User's name",
                            "Input",
                            JOptionPane.QUESTION_MESSAGE));
                    if (name == null) {
                        JOptionPane.showMessageDialog(
                                null,
                                "User's name cannot be null",
                                "Input",
                                JOptionPane.ERROR_MESSAGE);
                        continue;
                    }

                    String perm = (JOptionPane.showInputDialog(
                            null,
                            "Enter the User's permission level (1 for Rentee, 2 for Admin)",
                            "Input",
                            JOptionPane.QUESTION_MESSAGE));

                    if (!isNum(perm)) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Please enter only integers 1 or 2",
                                "Input",
                                JOptionPane.ERROR_MESSAGE);
                        continue;
                    }

                    int perms = Integer.parseInt(perm);

                    if (permLevel < 3 && perms == 2) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Sorry, you have to use the root account to add an admin account.",
                                "Input",
                                JOptionPane.ERROR_MESSAGE);
                        continue;

                    } else if (perms != 1 && perms != 2) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Please enter only integers 1 or 2",
                                "Input",
                                JOptionPane.ERROR_MESSAGE);
                        continue;
                    }
                    String password = rental.hash(JOptionPane.showInputDialog(
                            null,
                            "Enter the password for the user",
                            "Input",
                            JOptionPane.QUESTION_MESSAGE));

                    FileInputStream counter = new FileInputStream(new File("").getAbsolutePath() + "\\users\\counter.properties");
                    Properties prop = new Properties();

                    prop.load(counter);
                    counter.close();
                    String memberID;
                    if (perms == 1) {
                        memberID = "M"+(Integer.parseInt(prop.getProperty("member")) + 1) + "";
                        rental.addToRentee(memberID, name, new ArrayList<Comic>(), password, "null");

                    } else {
                        memberID = "A"+(Integer.parseInt(prop.getProperty("admin")) + 1) + "";

                        rental.addToAdmin(memberID, name, password, "null");


                    }

                }
            }

        }
    }
    public static boolean isNum(String strNum) {
        if (strNum == null) {
            return false;
        }

        try {
            double i = Double.parseDouble(strNum);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
