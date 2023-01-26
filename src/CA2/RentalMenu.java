/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CA2;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class: DIT/FT/1B/02 Name: Leong Yu Zhi Andy Admission Number: P2205865
 *
 * @author leong
 */
public class RentalMenu {

    private static boolean login = false;

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException,ClassNotFoundException {
        String memberIn = "";
        do {

//        In a perfect world, sutff would be stored on a sql database using JDBC
            String check[] = {"1", "2", "3", "4", "5"};
            String checkAdmin[] = {"1", "2", "3", "4", "5", "6", "7", "8"};
            int permLevel = 0;
            UIManager.put("OptionPane.messageFont", new Font("Monospaced", Font.BOLD, 12));
            UIManager.put("OptionPane.buttonFont", new Font("Monospaced", Font.BOLD, 12));
            RentalSystem rental = new RentalSystem();

            rental.importProperties();

            //Keep these 3 lines of code if running for the first time else comment it out
//            rental.createComic();
//            rental.createRentee();
//            rental.createAdmin();
            //----------------------------------------------------------------------------- Login stuff
            List<String> memberID = new ArrayList<String>();
            

            while (!login) {
                memberIn = JOptionPane.showInputDialog(
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

                String menu = "Enter your option:\n\n1. Display all Comics\n2. Search Comic by ISBN-13\n3. Search Rentee by MemberID\n4. Print Earning Statistic\n5. Exit";
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
                export(rental);
                login = false;

            } else if (permLevel >= 2) {

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
                final String tempId = memberIn;
                dict.put("5", () -> {
                    try {

                        addUser(temp, rental, tempId);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                });

                dict.put("6", () -> {
                    try {
                        addComic(rental);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                });

                dict.put("7", () -> rental.getEarning());
                String menu = "Enter your option:\n\n1. Display all Comics\n2. Display all Users\n3. Search Comic by ISBN-13\n4. Search Rentee by MemberID\n5. Add/Remove Users\n6. Add/Remove Comics\n7. Print Earning Statistic\n8. Exit";
                UserInput = (JOptionPane.showInputDialog(
                        null,
                        menu,
                        "Input",
                        JOptionPane.QUESTION_MESSAGE));

                while (!("8".equals(UserInput) && !(UserInput == null))) {
                    if (Arrays.asList(checkAdmin).contains(UserInput)) {
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
            };

        } while (!login);
    }

    private static void export(RentalSystem rental) throws IOException,NoSuchAlgorithmException {

        rental.export();

    }

    public static void addUser(int permLevel, RentalSystem rental, String memberIn) throws NoSuchAlgorithmException, IOException,ClassNotFoundException {
        String menu;
        String in = "";
        if (permLevel == 3) {
            while (in != null && !in.equals("3")) {
                menu = "Enter your option.\n\n1. Add new User\n2. Remove existing User\n3. Exit";

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
                    String password = (JOptionPane.showInputDialog(
                            null,
                            "Enter the password for the user",
                            "Input",
                            JOptionPane.QUESTION_MESSAGE));

                    if (password.equals("")) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Password cannot be empty!",
                                "Input",
                                JOptionPane.ERROR_MESSAGE);
                        continue;
                    }
                    password = rental.hash(password);
                    FileInputStream counter = new FileInputStream(new File("").getAbsolutePath() + "\\users\\counter.properties");
                    Properties prop = new Properties();

                    prop.load(counter);
                    counter.close();
                    String memberID;
                    if (perms == 1) {
                        memberID = "M" + (Integer.parseInt(prop.getProperty("member")) + 1) + "";
                        rental.addToRentee(memberID, name, new ArrayList<Comic>(), password, "null");

                    } else {
                        memberID = "A" + (Integer.parseInt(prop.getProperty("admin")) + 1) + "";

                        rental.addToAdmin(memberID, name,perm, password, "null");

                    }

                } else if ("2".equals(in)) {
                    List<String> memberID = new ArrayList<String>();
                    String filepath = new File("").getAbsolutePath() + "\\users\\";
                    File[] files = new File(filepath).listFiles();
                    for (File file : files) {
                        if (file.isFile() && !(("counter.properties").equals(file.getName()) || ("root.properties").equals(file.getName()))) {
                            memberID.add(file.getName().replaceFirst("[.][^.]+$", ""));
                        }
                    }

                    String id = (JOptionPane.showInputDialog(
                            null,
                            "Enter the Member ID of the user to remove.",
                            "Input",
                            JOptionPane.QUESTION_MESSAGE));

                    if (id.equals("root")) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Root user cannot be removed!",
                                "Input",
                                JOptionPane.ERROR_MESSAGE);
                        continue;
                    } else if (id.equals(memberIn)) {
                        JOptionPane.showMessageDialog(
                                null,
                                "You cannot remove yourself!",
                                "Input",
                                JOptionPane.ERROR_MESSAGE);
                        continue;
                    } else if (!memberID.contains(id)) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Member with the Member ID : " + id + " cannot be found!",
                                "Input",
                                JOptionPane.ERROR_MESSAGE);
                        continue;
                    } else if (permLevel == 2 && id.substring(0, 1).equals("A")) {
                        JOptionPane.showMessageDialog(
                                null,
                                "You need to be using a root account to delete admin accounts!",
                                "Input",
                                JOptionPane.ERROR_MESSAGE);
                        continue;
                    }
                    String pw = (JOptionPane.showInputDialog(
                            null,
                            "Please enter your password to confirm the removal of user " + id,
                            "Input",
                            JOptionPane.QUESTION_MESSAGE));

                    Properties prop = new Properties();
                    String path = new File("").getAbsolutePath() + "\\users\\" + memberIn + ".properties";
                    FileInputStream load = new FileInputStream(path);
                    prop.load(load);
                    load.close();
                    System.out.println(prop.getProperty("password") + "  " + rental.hash(pw));
                    if (!rental.hash(pw).equals(prop.getProperty("password"))) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Wrong password entered!",
                                "Input",
                                JOptionPane.ERROR_MESSAGE);
                        continue;
                    }
                    File toDelete = new File(new File("").getAbsolutePath() + "\\users\\" + id + ".properties");
                    if (toDelete.delete()) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Member Successfully deleted!",
                                "Input",
                                JOptionPane.INFORMATION_MESSAGE);
                        rental.importProperties();
                    } else {
                        JOptionPane.showMessageDialog(
                                null,
                                "Member could not be deleted!",
                                "Input",
                                JOptionPane.ERROR_MESSAGE);
                    }

                } else if (!in.equals("3") && in != null) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Invalid option! Please enter in the range from 1 to 3.",
                            "Input",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        }
    }

    public static void addComic(RentalSystem rental) throws IOException, NoSuchAlgorithmException,ClassNotFoundException {
        String menu = "";
        String in = "";

        while (!"5".equals(in) && in != null) {
            menu = "Enter your option.\n\n1. Add new Comic\n2. Remove existing Comic\n3. Add comic to Rentee\n4. Remove comic from Rentee\n5. Exit";
            in = (JOptionPane.showInputDialog(
                    null,
                    menu,
                    "Input",
                    JOptionPane.QUESTION_MESSAGE));

            List<String> comicNames = new ArrayList<String>();
            List<String> ISBNlist = new ArrayList<String>();
            File[] files = new File(new File("").getAbsolutePath() + "\\comics\\").listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    Properties prop = new Properties();
                    FileInputStream IN = new FileInputStream(new File("").getAbsolutePath() + "\\comics\\" + file.getName());
                    prop.load(IN);
                    IN.close();

                    comicNames.add(prop.getProperty("Title"));
                    ISBNlist.add(prop.getProperty("ISBN"));
                }
            }

            if ("1".equals(in)) {
                String title = (JOptionPane.showInputDialog(
                        null,
                        "Enter the title of the comic.",
                        "Input",
                        JOptionPane.QUESTION_MESSAGE));

                if (comicNames.contains(title)) {
                    JOptionPane.showMessageDialog(
                            null,
                            "A comic with this title already exist!",
                            "Input",
                            JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                String ISBN = (JOptionPane.showInputDialog(
                        null,
                        "Enter the ISBN-13 of the comic.",
                        "Input",
                        JOptionPane.QUESTION_MESSAGE));

                if (!validateISBN(ISBN)) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Invalid ISBN format!",
                            "Input",
                            JOptionPane.ERROR_MESSAGE);
                    //only validates format, doesnt check using checksum
                    continue;
                } else if (ISBNlist.contains(ISBN)) {
                    JOptionPane.showMessageDialog(
                            null,
                            "A comic with this ISBN already exist!",
                            "Input",
                            JOptionPane.ERROR_MESSAGE);
                    //only validates format, doesnt check using checksum
                    continue;
                }

                String PageNum = (JOptionPane.showInputDialog(
                        null,
                        "Enter the page numbers of the comic",
                        "Input",
                        JOptionPane.QUESTION_MESSAGE));

                if (!isInt(PageNum)) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Invalid page number entered! Please enter only integers.",
                            "Input",
                            JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                String cost = (JOptionPane.showInputDialog(
                        null,
                        "Enter the cost of the comic.",
                        "Input",
                        JOptionPane.QUESTION_MESSAGE));

                if (!isNum(cost)) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Invalid cost entered! Please enter a valid number.",
                            "Input",
                            JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                rental.addToComic(title, ISBN, PageNum, cost);
            } else if ("2".equals(in)) {
                String ISBN = (JOptionPane.showInputDialog(
                        null,
                        "Enter the ISBN-13 of the comic to remove",
                        "Input",
                        JOptionPane.QUESTION_MESSAGE));

                File toDelete = new File(new File("").getAbsolutePath() + "\\comics\\" + ISBN + ".properties");
                if (toDelete.delete()) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Comic Successfully deleted!",
                            "Input",
                            JOptionPane.INFORMATION_MESSAGE);
                    rental.importProperties();
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Comic could not be found or deleted! DOuble check the ISBN?",
                            "Input",
                            JOptionPane.ERROR_MESSAGE);
                }

            } else if ("3".equals(in)) {
                List<String> memberID = new ArrayList<String>();
                String filepath = new File("").getAbsolutePath() + "\\users\\";
                File[] files2 = new File(filepath).listFiles();
                for (File file : files2) {
                    if (file.isFile() && !(("counter.properties").equals(file.getName()) || ("root.properties").equals(file.getName()) || file.getName().substring(0, 1).equals("A"))) {
                        memberID.add(file.getName().replaceFirst("[.][^.]+$", ""));
                    }
                }

                String renteeID = (JOptionPane.showInputDialog(
                        null,
                        "Enter the Rentee's member ID to add comics to.",
                        "Input",
                        JOptionPane.QUESTION_MESSAGE));

                if (!memberID.contains(renteeID)) {
                    JOptionPane.showMessageDialog(
                            null,
                            "The member ID entered could not be found!",
                            "Input",
                            JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                String isbn = (JOptionPane.showInputDialog(
                        null,
                        "Enter the ISBN of the comic to add to the Rentee.",
                        "Input",
                        JOptionPane.QUESTION_MESSAGE));

                if (!ISBNlist.contains(isbn)) {
                    JOptionPane.showMessageDialog(
                            null,
                            "The entered ISBN could not be found!Is the comic added to the system?",
                            "Input",
                            JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                rental.addComicToRentee(renteeID, isbn);

            } else if ("4".equals(in)) {
                List<String> memberID = new ArrayList<String>();
                String filepath = new File("").getAbsolutePath() + "\\users\\";
                File[] files2 = new File(filepath).listFiles();
                for (File file : files2) {
                    if (file.isFile() && !(("counter.properties").equals(file.getName()) || ("root.properties").equals(file.getName()) || file.getName().substring(0, 1).equals("A"))) {
                        memberID.add(file.getName().replaceFirst("[.][^.]+$", ""));
                    }
                }

                String renteeID = (JOptionPane.showInputDialog(
                        null,
                        "Enter the Rentee's member ID to remove comics from.",
                        "Input",
                        JOptionPane.QUESTION_MESSAGE));

                if (!memberID.contains(renteeID)) {
                    JOptionPane.showMessageDialog(
                            null,
                            "The member ID entered could not be found!",
                            "Input",
                            JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                String isbn = (JOptionPane.showInputDialog(
                        null,
                        "Enter the ISBN of the comic to remove from the Rentee.",
                        "Input",
                        JOptionPane.QUESTION_MESSAGE));

                if (!ISBNlist.contains(isbn)) {
                    JOptionPane.showMessageDialog(
                            null,
                            "The entered ISBN could not be found! Is the comic added to the system?",
                            "Input",
                            JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                rental.rmvComicFromRentee(renteeID, isbn);
            } else if (in != null && !in.equals("5")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Invalid option! Please enter in the range from 1 to 3.",
                        "Input",
                        JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    public static boolean validateISBN(String isbn) {
        Pattern regex = Pattern.compile("^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$");

        Matcher match = regex.matcher(isbn);

        return match.find();
    }

    public static boolean isNum(String strNum) {
        //lazy to do regex
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

    public static boolean isInt(String s) {
        //Im too tired to do another regex
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        // if doesnt return false
        return true;
    }
}
