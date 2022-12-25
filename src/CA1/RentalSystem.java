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

/**
 *
 * @author leong
 */
public class RentalSystem {

    private ArrayList<Comic> comicArr;
    private ArrayList<Users> userArr;

    public RentalSystem() {
    }

    public void createComic() {
        comicArr.add(new Comic("978-0785199618", "Spider-Man: Miles Morales", 112, 14.39));
        comicArr.add(new Comic("978-0785190219", "Ms. Marvel: No Normal", 120, 15.99));
        comicArr.add(new Comic("978-0785198956", "Secret Wars", 312, 34.99));
        comicArr.add(new Comic("978-0785156598", "Infinity Gauntlet", 256, 24.99));
    }

    public void createRentee() {
        userArr.add(new Rentee("M220622", "Ariq Sulaiman", new ArrayList<Comic>(Arrays.asList(comicArr.get(0), comicArr.get(1)))));
        userArr.add(new Rentee("M220623", "Sayang Sulaiman", new ArrayList<Comic>(Arrays.asList(comicArr.get(0), comicArr.get(2)))));
        userArr.add(new Rentee("M220624", "Ben Dover", new ArrayList<Comic>(Arrays.asList(comicArr.get(3), comicArr.get(3)))));

    }

    public void displayComic() {
        String text = String.format("%-15s| %-30s| %-7s| %-10s| %s\n%s\n", "ISBN-13", "Title", "Pages", "Price/Day", "Deposit", "-".repeat(80));

        for (Comic i : this.comicArr) {
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
        for (Rentee renteeArr1 : userArr) {
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

    public void getEarning() {
        int renteeNum = userArr.size();
        double total = 0.0;

        for (Rentee i : userArr) {
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

    public void exportRentee(String memberID, String name, ArrayList<Comic> comics, String password) throws IOException {
        Properties prop = new Properties();
        String comic = "";

        for (int i = 0; i < comics.size() - 1; i++) {
            comic += comics.get(i).getTitle() + ",";
        }
        comic += comics.get(comics.size()).getTitle(); //No leading "," yay

        prop.put("password", password);
        prop.put("comic", comic);
        prop.put("permLevel", "1");
        prop.put("name", name);
        prop.put("memberID", memberID);

        String path = new File("").getAbsolutePath() + "\\users\\" + memberID + ".properties";

        FileOutputStream out = new FileOutputStream(path, true);
        prop.store(out, "Member Data");
    }

    public void exportAdmin(String memberID, String name, String password) throws IOException {
        Properties prop = new Properties();

        prop.put("password", password);
        prop.put("permLevel", "2");
        prop.put("name", name);
        prop.put("memberID", memberID);

        String path = new File("").getAbsolutePath() + "\\users\\" + memberID + ".properties";

        FileOutputStream out = new FileOutputStream(path, true);
        prop.store(out, "Admin Data");
    }

    public void importProperties() throws IOException{

        List<String> results = new ArrayList<String>();

        File[] files = new File(new File("").getAbsolutePath() + "\\users\\").listFiles();
        for (File file : files) {
            if (file.isFile()) {
                results.add(file.getName());
            }
        }
        
        for (String name : results){
            FileInputStream in = new FileInputStream(new File("").getAbsolutePath() + "\\users\\" + name +".properties");
            
            Properties prop = new Properties();
            
            prop.load(in);
            
            if("2".equals(prop.getProperty("permLevel"))){
                
            }
        }
    }

    public String hash(String pw) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(pw.getBytes());
        String stringHash = new String(messageDigest.digest()); //So that i dont get a byte[]
        //Note to self, use equals when comparing hash to verify pw don tuse ==
        return stringHash;
    }

    public static void main(String[] args) throws IOException {
        RentalSystem rental = new RentalSystem();
        rental.createComic();
//        rental.export("123","test","1");
    }
}
