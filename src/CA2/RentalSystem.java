/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CA2;

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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileWriter;

/**
 * Class: DIT/FT/1B/02 Name: Leong Yu Zhi Andy Admission Number: P2205865
 *
 * @author leong
 */
public class RentalSystem {

    private ArrayList<Comic> comicArr = new ArrayList<Comic>();
    private ArrayList<Rentee> renteeArr = new ArrayList<Rentee>();
    private ArrayList<Administrator> adminArr = new ArrayList<Administrator>();

    public RentalSystem() {
    }

    public ArrayList<Comic> getComicArr() {
        return comicArr;
    }

    public ArrayList<Rentee> getRenteeArr() {
        return renteeArr;
    }

    public ArrayList<Administrator> getAdminArr() {
        return adminArr;
    }

    public void addComicToRentee(String ID, String ISBN) throws IOException, NoSuchAlgorithmException {
        int index = -1;
        int index2 = -1;
        for (int i = 0; i < renteeArr.size(); i++) {
            if (renteeArr.get(i).getMemberID().equals(ID)) {
                index = i;
                break;

            }
        }
        if (index != -1) {
            List<String> comic = new ArrayList<String>();

            for (int k = 0; k < renteeArr.get(index).getComics().size(); k++) {
                comic.add(renteeArr.get(index).getComics().get(k).getISBN());
            }

            if (comic.contains(ISBN)) {
                JOptionPane.showMessageDialog(
                        null,
                        "The rentee already has this comic!",
                        "Input",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                for (int o = 0; o < comicArr.size(); o++) {
                    if (comicArr.get(o).getISBN().equals(ISBN)) {
                        index2 = o;
                    }
                }

                if (index2 != -1) {
                    Rentee rent = renteeArr.get(index);
                    ArrayList<Comic> temp = rent.getComics();
                    temp.add(comicArr.get(index2));
                    renteeArr.set(index, new Rentee(rent.getMemberID(), rent.getName(), temp, rent.getPassword(), rent.getLogin()));
                    export();
                    JOptionPane.showMessageDialog(
                            null,
                            "Comic has been successfully added to the Rentee",
                            "Input",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            }
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "The member ID entered could not be found!",
                    "Input",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    public void rmvComicFromRentee(String ID, String ISBN) throws IOException, NoSuchAlgorithmException {
        int index = -1;
        int index2 = -1;
        for (int i = 0; i < renteeArr.size(); i++) {
            if (renteeArr.get(i).getMemberID().equals(ID)) {
                index = i;
                break;

            }
        }
        if (index != -1) {
            List<String> comic = new ArrayList<String>();

            for (int k = 0; k < renteeArr.get(index).getComics().size(); k++) {
                comic.add(renteeArr.get(index).getComics().get(k).getISBN());
            }

            if (!comic.contains(ISBN)) {
                JOptionPane.showMessageDialog(
                        null,
                        "The rentee does not have this comic!",
                        "Input",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                for (int o = 0; o < comicArr.size(); o++) {
                    if (comicArr.get(o).getISBN().equals(ISBN)) {
                        index2 = o;
                    }
                }

                if (index2 != -1) {
                    Rentee rent = renteeArr.get(index);
                    ArrayList<Comic> temp = rent.getComics();
                    temp.remove(temp.indexOf(comicArr.get(index2)));
                    renteeArr.set(index, new Rentee(rent.getMemberID(), rent.getName(), temp, rent.getPassword(), rent.getLogin()));
                    export();
                    JOptionPane.showMessageDialog(
                            null,
                            "Comic has been successfully removed from the Rentee",
                            "Input",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            }
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "The member ID entered could not be found!",
                    "Input",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    public void createComic() throws IOException,NoSuchAlgorithmException {
        comicArr.add(new Comic("978-0785199618", "Spider-Man: Miles Morales", 112, 14.39));
        comicArr.add(new Comic("978-0785190219", "Ms. Marvel: No Normal", 120, 15.99));
        comicArr.add(new Comic("978-0785198956", "Secret Wars", 312, 34.99));
        comicArr.add(new Comic("978-0785156598", "Infinity Gauntlet", 256, 24.99));

    }

    public void createRentee() throws IOException, NoSuchAlgorithmException {
        renteeArr.add(new Rentee("M220622", "Ariq Sulaiman", new ArrayList<Comic>(Arrays.asList(comicArr.get(0), comicArr.get(1))), hash("123"), "2022.12.26 23:34:47"));
        renteeArr.add(new Rentee("M220623", "Sayang Sulaiman", new ArrayList<Comic>(Arrays.asList(comicArr.get(0), comicArr.get(2))), hash("123"), "2022.12.26 23:34:47"));
        renteeArr.add(new Rentee("M220624", "Ben Dover", new ArrayList<Comic>(Arrays.asList(comicArr.get(3), comicArr.get(3))), hash("123"), "2022.12.26 23:34:47"));


    }

    public void createAdmin() throws IOException, NoSuchAlgorithmException {
        adminArr.add(new Administrator("A110620", "rootLow","2", hash("root"), "2022.12.26 23:34:47"));
        adminArr.add(new Administrator("root","root","3",hash("root"),"2022.12.26 23:34:47"));


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
            String text = String.format("%s\n\nStock purchased at $%.2f.\nCost $%.2f per day to rent.\nRequire deposit of $%.2f.", flag.getTitle(), price, price / 20, price * 1.1);
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
            if (name.equals("counter.properties")) {
                continue;
            }
            FileInputStream in = new FileInputStream(new File("").getAbsolutePath() + "\\users\\" + name);
            Properties prop = new Properties();

            prop.load(in);
            in.close();
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

        String out = String.format("Earning Per Day:\n---------------\nThere are %d Rentees in total.\n\nAverage earning day based on numbers of rentees is $%.2f.\n\nTotal earning per day is $%.2f.", renteeNum, average, total);

        JOptionPane.showMessageDialog(
                null,
                out,
                "Message",
                JOptionPane.INFORMATION_MESSAGE
        );

    }



    public void export() throws IOException, NoSuchAlgorithmException {
        IO.serialize(comicArr,"Comic.dat");
        IO.serialize(renteeArr, "Rentee.dat");
        IO.serialize(adminArr,"Admin.dat");
        String data = "root;" + hash("root") + ";root;" + new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new java.util.Date()) + ";3\n";
        for (Rentee rentee : renteeArr) {
            ArrayList<Comic> comics = rentee.getComics();

            String comic = "";
            if (!comics.isEmpty()) {
                for (int i = 0; i <= (comics.size() - 2); i += 1) {

                    comic += comics.get(i).getISBN() + "#";
                }
                comic += comics.get(comics.size() - 1).getISBN(); //No leading "#"
            }

            data += rentee.getMemberID() + ";" + rentee.getPassword() + ";" + rentee.getName() + ";" + rentee.getLogin() + "~1" + ";" + comic + "\n";
        }

        for (Administrator admin : adminArr) {
            data += admin.getMemberID() + ";" + admin.getPassword() + ";" + admin.getName() + ";" + admin.getLogin() + ";" + admin.getPermissionLevel() + "\n";
        }

        try {
            FileWriter filewriter = new FileWriter("users.txt");
            filewriter.write(data);
            filewriter.flush();
            filewriter.close();
        } catch (IOException e) {
            System.out.println(e);
        }

    }


    public void importProperties() throws IOException, NoSuchAlgorithmException,ClassNotFoundException {
        comicArr = (ArrayList<Comic>) IO.deserialize("Comic.dat");
        renteeArr = (ArrayList<Rentee>) IO.deserialize("Rentee.dat");
        adminArr = (ArrayList<Administrator>) IO.deserialize("Admin.dat");
        
    }

    public void addToRentee(String memberID, String name, ArrayList<Comic> comic, String password, String login) throws IOException, NoSuchAlgorithmException {
        Rentee newRentee = new Rentee(memberID, name, comic, password, login);
        renteeArr.add(newRentee);
        export();

    }

    public void addToAdmin(String memberID, String name,String perm, String password, String login) throws IOException, NoSuchAlgorithmException {
        Administrator newAdmin = new Administrator(memberID, name,perm, password, login);
        adminArr.add(newAdmin);
        export();

    }

    public void addToComic(String title, String ISBN, String pageNum, String cost) throws IOException,NoSuchAlgorithmException {
        Comic comic = new Comic(ISBN, title, Integer.parseInt(pageNum), Double.parseDouble(cost));
        comicArr.add(comic);
        export();
    }

    public String hash(String pw) throws NoSuchAlgorithmException {
        //Sha256 hash cause storing passwords in plaintext is pain (for the company)
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(pw.getBytes());
        String stringHash = new String(messageDigest.digest()); //So that i dont get a byte[]
        //Note to self, use equals when comparing hash to verify pw don tuse ==
        return stringHash;
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

//    public static void updateCounter() throws IOException {
//        File counter = new File(new File("").getAbsolutePath() + "\\users\\counter.properties");
//        Properties counterProp = new Properties();
//        FileOutputStream createCounter = new FileOutputStream(counter, false);
//
//        int admin = 0;
//        int member = 0;
//        int num;
//        File[] files = new File(new File("").getAbsolutePath() + "\\users\\").listFiles();
//        for (File file : files) {
//            if (file.isFile()) {
//                if (isNum(file.getName().replaceFirst("[.][^.]+$", "").substring(1))) {
//                    num = Integer.parseInt(file.getName().replaceFirst("[.][^.]+$", "").substring(1));
//                    if ("A".equals(file.getName().replaceFirst("[.][^.]+$", "").substring(0, 1)) && num > admin) {
//                        admin = num;
//                    } else if ("M".equals(file.getName().replaceFirst("[.][^.]+$", "").substring(0, 1)) && num > member) {
//                        member = num;
//                    }
//                }
//            }
//        }
//
//        //If there are no admin/member files then admin counter = 110620,member counter = 220622
//        if (admin == 0) {
//            admin = 110620;
//        }
//        if (member == 0) {
//            member = 220622;
//        }
//
//        counterProp.put("admin", admin + "");
//        counterProp.put("member", member + "");
//
//        counterProp.store(createCounter, "ID counter");
//        createCounter.close();
//    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException,ClassNotFoundException {
        RentalSystem rental = new RentalSystem();
        rental.createComic();
        rental.createRentee();
        rental.createAdmin();
//        rental.importProperties();
        rental.export();
        rental.importProperties();
//        rental.export("123","test","1");

    }
}
