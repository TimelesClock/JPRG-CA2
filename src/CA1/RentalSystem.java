/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CA1;

import javax.swing.JOptionPane;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.security.*;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.text.SimpleDateFormat;

/**
 *
 * @author leong
 */
public class RentalSystem {

    private ArrayList<Comic> comicArr = new ArrayList<Comic>();
    private ArrayList<Rentee> renteeArr = new ArrayList<Rentee>();
    private ArrayList<Administrator> adminArr = new ArrayList<Administrator>();

    public RentalSystem() {
    }

    public void createComic() throws IOException {
        comicArr.add(new Comic("978-0785199618", "Spider-Man: Miles Morales", 112, 14.39));
        comicArr.add(new Comic("978-0785190219", "Ms. Marvel: No Normal", 120, 15.99));
        comicArr.add(new Comic("978-0785198956", "Secret Wars", 312, 34.99));
        comicArr.add(new Comic("978-0785156598", "Infinity Gauntlet", 256, 24.99));
        for (int i = 0; i < comicArr.size(); i++) {
            exportComic(comicArr.get(i));
        }
    }

    public void createRentee() throws IOException, NoSuchAlgorithmException {
        renteeArr.add(new Rentee("M220622", "Ariq Sulaiman", new ArrayList<Comic>(Arrays.asList(comicArr.get(0), comicArr.get(1))), "123", "2022.12.26 23:34:47"));
        renteeArr.add(new Rentee("M220623", "Sayang Sulaiman", new ArrayList<Comic>(Arrays.asList(comicArr.get(0), comicArr.get(2))), "123", "2022.12.26 23:34:47"));
        renteeArr.add(new Rentee("M220624", "Ben Dover", new ArrayList<Comic>(Arrays.asList(comicArr.get(3), comicArr.get(3))), "123", "2022.12.26 23:34:47"));
        for (int i = 0; i < renteeArr.size(); i++) {
            Rentee temp = renteeArr.get(i);
            exportRentee(temp.getMemberID(), temp.getName(), temp.getComics(), temp.getPassword(), temp.getLogin());
        }
    }

    public void createAdmin() throws IOException, NoSuchAlgorithmException {
        adminArr.add(new Administrator("A220620", "root", "root", "2022.12.26 23:34:47"));
        for (int i = 0; i < adminArr.size(); i++) {
            Administrator temp = adminArr.get(i);
            exportAdmin(temp.getMemberID(), temp.getName(), temp.getPassword(), temp.getLogin());
        }
    }

    public void displayComic() {
        String text = String.format("%-15s| %-30s| %-7s| %-10s| %s\n%s\n", "ISBN-13", "Title", "Pages", "Price/Day", "Deposit", "-".repeat(80));

        for (int index = 0; index < comicArr.size(); index++) {
            Comic i = comicArr.get(index);
            text += String.format("%-15s| %-30s| %-7d| %-10.2f| %.2f\n", i.getISBN(), i.getTitle(), i.getPageNum(), (i.getCost() / 20.0), (i.getCost() * 1.10));
        }

        JOptionPane.showMessageDialog(
                null,
                text,
                "All Comics",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public void findComic() {
        String userInput = (JOptionPane.showInputDialog(
                null,
                "Enter ISBN-13 to search:",
                "Input",
                JOptionPane.QUESTION_MESSAGE));
        Comic flag = null;
        for (Comic i : comicArr) {
            if (i.getISBN().equals(userInput)) {
                flag = i;
                break;
            }
        }
        if (flag != null) {
            double price = flag.getCost();
            String text = String.format("""
                                        %s
                                        
                                        Stock purchased at $%.2f.
                                        Cost $%.2f per day to rent.
                                        Require deposit of $%.2f.
                          """, flag.getTitle(), price, price / 20, price * 1.1);
            JOptionPane.showMessageDialog(
                    null,
                    text,
                    "Message",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    String.format("Cannot find the Comic \"%s\"!!", userInput),
                    "Info",
                    JOptionPane.ERROR_MESSAGE
            );
        }

    }

    public void findMember() {
        String userInput = JOptionPane.showInputDialog(
                null,
                "Enter MemberID to search:",
                "Input",
                JOptionPane.QUESTION_MESSAGE);
        boolean flag = false;
        Rentee member = null;
        for (Rentee renteeArr1 : renteeArr) {
            if ((renteeArr1).getMemberID().equals(userInput)) {
                member = renteeArr1;
                flag = true;
                break;
            }
        }

        if (!flag) {
            JOptionPane.showMessageDialog(
                    null,
                    String.format("Cannot find the Member \"%s\"!!", userInput),
                    "Info",
                    JOptionPane.ERROR_MESSAGE
            );
        } else {
            String out = String.format("%-7s| Name\n%s\n%-9s%s\n\n%s\n", "Member", "-".repeat(30), member.getMemberID(), member.getName(), "Comics Loaned:");
            int no = 1;
            double total = 0;
            for (Comic i : member.getComics()) {
                out += String.format("%d. %s\n", no, i.getTitle());
                total += i.getCost() / 20.0;
                no++;
            }
            out += String.format("\n\n\nTotal rental Per Day: $%.2f", total);
            JOptionPane.showMessageDialog(
                    null,
                    out,
                    "Message",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void displayMember() throws IOException {
        List<String> results = new ArrayList<String>();
        String text = String.format("%-15s| %-20s| %-20s| %s\n%s\n", "Member ID", "Name", "Permission Level", "Last Login", "-".repeat(80));
        File[] files = new File(new File("").getAbsolutePath() + "\\users\\").listFiles();
        for (File file : files) {
            if (file.isFile()) {
                results.add(file.getName());
            }
        }
        for (String name : results) {

            FileInputStream in = new FileInputStream(new File("").getAbsolutePath() + "\\users\\" + name);
            Properties prop = new Properties();

            prop.load(in);
            in.close();
            System.out.println(prop.getProperty("login"));
            text += String.format("%-15s| %-20s| %-20s| %s\n", prop.getProperty("memberID"), prop.getProperty("name"), prop.getProperty("permLevel"), prop.getProperty("login"));

            
        }
        JOptionPane.showMessageDialog(
                    null,
                    text,
                    "All Comics",
                    JOptionPane.INFORMATION_MESSAGE
            );
    }

    public void getEarning() {
        int renteeNum = renteeArr.size();
        double total = 0.0;

        for (Rentee i : renteeArr) {
            for (Comic o : i.getComics()) {
                total += o.getCost() / 20.0;
            }
        }

        double average = total / renteeNum;

        String out = String.format("""
                     Earning Per Day:
                     ---------------
                     There are %d Rentees in total.
                     
                     Average earning day based on numbers of rentees is $%.2f.
                     
                     Total earning per day is $%.2f.
                     """, renteeNum, average, total);

        JOptionPane.showMessageDialog(
                null,
                out,
                "Message",
                JOptionPane.INFORMATION_MESSAGE
        );

    }

    public void exportComic(Comic comic) throws IOException {
        Properties prop = new Properties();

        prop.put("ISBN", comic.getISBN());
        prop.put("Title", comic.getTitle() + "");
        prop.put("PageNum", comic.getPageNum() + "");
        prop.put("Cost", comic.getCost() + "");

        File dir = new File(new File("").getAbsolutePath() + "\\comics");
        if (!dir.exists()) {
            dir.mkdir();
        }

        String path = new File("").getAbsolutePath() + "\\comics\\" + comic.getISBN() + ".properties";

        FileOutputStream out = new FileOutputStream(path, false);
        prop.store(out, "Comic Data");
        out.close();
    }

    public void exportRentee(String memberID, String name, ArrayList<Comic> comics, String password, String login) throws IOException {
        Properties prop = new Properties();
        String comic = "";

        for (int i = 0; i <= (comics.size() - 2); i += 1) {

            comic += comics.get(i).getTitle() + ",";
        }

        comic += comics.get(comics.size() - 1).getTitle(); //No leading "," yay

        prop.put("login", login);
        prop.put("password", password);
        prop.put("comic", comic);
        prop.put("permLevel", "1");
        prop.put("name", name);
        prop.put("memberID", memberID);

        File dir = new File(new File("").getAbsolutePath() + "\\users");
        if (!dir.exists()) {
            dir.mkdir();
        }

        String path = new File("").getAbsolutePath() + "\\users\\" + memberID + ".properties";

        FileOutputStream out = new FileOutputStream(path, false);
        prop.store(out, "Member Data");
        out.close();
    }

    public void exportAdmin(String memberID, String name, String password, String login) throws IOException {
        Properties prop = new Properties();

        prop.put("login", login);
        prop.put("password", password);
        prop.put("permLevel", "2");
        prop.put("name", name);
        prop.put("memberID", memberID);

        File dir = new File(new File("").getAbsolutePath() + "\\users");
        if (!dir.exists()) {
            dir.mkdir();
        }

        String path = new File("").getAbsolutePath() + "\\users\\" + memberID + ".properties";

        FileOutputStream out = new FileOutputStream(path, false);
        prop.store(out, "Admin Data");
        out.close();
    }

    public void importComics() throws IOException {
        List<String> results = new ArrayList<String>();

        File[] files = new File(new File("").getAbsolutePath() + "\\comics\\").listFiles();
        for (File file : files) {
            if (file.isFile()) {
                results.add(file.getName());
            }
        }

        for (String name : results) {

            FileInputStream in = new FileInputStream(new File("").getAbsolutePath() + "\\comics\\" + name);
            Properties prop = new Properties();

            prop.load(in);
            in.close();
            comicArr.add(new Comic(prop.getProperty("ISBN"), prop.getProperty("Title"), Integer.parseInt(prop.getProperty("PageNum")), Double.parseDouble(prop.getProperty("Cost"))));
        }
    }

    public void importProperties() throws IOException, NoSuchAlgorithmException {
        List<String> results = new ArrayList<String>();

        File[] files = new File(new File("").getAbsolutePath() + "\\users\\").listFiles();
        for (File file : files) {
            if (file.isFile()) {
                results.add(file.getName());
            }
        }
        for (String name : results) {

            FileInputStream in = new FileInputStream(new File("").getAbsolutePath() + "\\users\\" + name);
            Properties prop = new Properties();

            prop.load(in);
            in.close();
            if ("2".equals(prop.getProperty("permLevel"))) {
                adminArr.add(new Administrator(prop.getProperty("memberID"), prop.getProperty("name"), prop.getProperty("password"), prop.getProperty("login")));
            } else {
                ArrayList<Comic> comicList = new ArrayList<Comic>();
                for (String comicName : prop.getProperty("comic").split(",")) {
                    boolean flag = false;
                    for (Comic i : comicArr) {
                        if (comicName.equals(i.getTitle())) {
                            comicList.add(i);
                            flag = true;
                            break;
                        }
                    }
                    if (flag == false) {
                        System.out.println("Comic, " + comicName + " is either not added or missing in the system and will not be imported into account " + prop.getProperty("memberID"));
                    }
                }
                renteeArr.add(new Rentee(prop.getProperty("memberID"), prop.getProperty("name"), comicList, prop.getProperty("password"), prop.getProperty("login")));
            }
        }
    }

    public String hash(String pw) throws NoSuchAlgorithmException {
        //Sha256 hash cause storing passwords in plaintext is pain (for the company)
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(pw.getBytes());
        String stringHash = new String(messageDigest.digest()); //So that i dont get a byte[]
        //Note to self, use equals when comparing hash to verify pw don tuse ==
        return stringHash;
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        RentalSystem rental = new RentalSystem();
        rental.createComic();
        rental.importComics();
        rental.importProperties();
//        rental.export("123","test","1");
    }
}
