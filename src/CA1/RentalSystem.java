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
                    exportRentee(renteeArr.get(index));
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
                    exportRentee(renteeArr.get(index));
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
            exportRentee(temp);
        }
    }

    public void createAdmin() throws IOException, NoSuchAlgorithmException {
        adminArr.add(new Administrator("A110620", "root", "root", "2022.12.26 23:34:47"));
        for (int i = 0; i < adminArr.size(); i++) {
            Administrator temp = adminArr.get(i);
            exportAdmin(temp);
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

    public void exportRentee(Rentee obj) throws IOException {
        Properties prop = new Properties();

        prop.put("login", obj.getLogin());
        prop.put("password", obj.getPassword());

        ArrayList<Comic> comics = obj.getComics();

        String comic = "";
        if (comics.size() != 0) {
            for (int i = 0; i <= (comics.size() - 2); i += 1) {

                comic += comics.get(i).getTitle() + ",";
            }
            comic += comics.get(comics.size() - 1).getTitle(); //No leading "," yay
        }

        prop.put("comic", comic);

        prop.put("permLevel", "1");
        prop.put("name", obj.getName());
        prop.put("memberID", obj.getMemberID());

        File dir = new File(new File("").getAbsolutePath() + "\\users");
        if (!dir.exists()) {
            dir.mkdir();
        }

        String path = new File("").getAbsolutePath() + "\\users\\" + obj.getMemberID() + ".properties";
        String comment;
        FileOutputStream out = new FileOutputStream(path, false);
        prop.store(out, "Member Data");
        out.close();
    }

    public void exportAdmin(Administrator obj) throws IOException {
        Properties prop = new Properties();

        prop.put("login", obj.getLogin());
        prop.put("password", obj.getPassword());
        prop.put("permLevel", obj.getPermissionLevel());
        prop.put("name", obj.getName());
        prop.put("memberID", obj.getMemberID());

        File dir = new File(new File("").getAbsolutePath() + "\\users");
        if (!dir.exists()) {
            dir.mkdir();
        }

        String path = new File("").getAbsolutePath() + "\\users\\" + obj.getMemberID() + ".properties";

        FileOutputStream out = new FileOutputStream(path, false);
        prop.store(out, "Admin Data");
        out.close();
    }

    public void importComics() throws IOException, NoSuchAlgorithmException {
        //Reset existing comicArr
        comicArr = new ArrayList<Comic>();

        File dir = new File(new File("").getAbsolutePath() + "\\comics");
        if (!dir.exists()) {
            dir.mkdir();
        }

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
        //Reset existing admin arr and rentee arr
        adminArr = new ArrayList<Administrator>();
        renteeArr = new ArrayList<Rentee>();

        File dir = new File(new File("").getAbsolutePath() + "\\users");
        if (!dir.exists()) {
            dir.mkdir();
        }

        File rootacc = new File(new File("").getAbsolutePath() + "\\users\\root.properties");

        if (!rootacc.exists()) {

            Properties rootProp = new Properties();
            FileOutputStream createRoot = new FileOutputStream(rootacc, false);

            rootProp.put("login", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new java.util.Date()));
            rootProp.put("password", hash("root"));
            rootProp.put("permLevel", "3");
            rootProp.put("name", "Admin");
            rootProp.put("memberID", "root");

            rootProp.store(createRoot, "Root Admin");
            createRoot.close();
        }

        File counter = new File(new File("").getAbsolutePath() + "\\users\\counter.properties");

        if (!counter.exists()) {

            updateCounter();
        }

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
            if ("counter.properties".equals(name)) {
                continue;
            }

            if ("2".equals(prop.getProperty("permLevel"))) {

                adminArr.add(new Administrator(prop.getProperty("memberID"), prop.getProperty("name"), prop.getProperty("password"), prop.getProperty("login")));
            } else if (Integer.parseInt(prop.getProperty("permLevel")) < 2) {;
                ArrayList<Comic> comicList = new ArrayList<Comic>();

                for (String comicName : prop.getProperty("comic").split(",")) {
                    if ("".equals(comicName)) {
                        continue;
                    }
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

    public void addToRentee(String memberID, String name, ArrayList<Comic> comic, String password, String login) throws IOException, NoSuchAlgorithmException {
        Rentee newRentee = new Rentee(memberID, name, comic, password, login);
        renteeArr.add(newRentee);
        exportRentee(newRentee);
        updateCounter();
    }

    public void addToAdmin(String memberID, String name, String password, String login) throws IOException, NoSuchAlgorithmException {
        Administrator newAdmin = new Administrator(memberID, name, password, login);
        adminArr.add(newAdmin);
        exportAdmin(newAdmin);
        updateCounter();
    }

    public void addToComic(String title, String ISBN, String pageNum, String cost) throws IOException {
        Comic comic = new Comic(ISBN, title, Integer.parseInt(pageNum), Double.parseDouble(cost));
        comicArr.add(comic);
        exportComic(comic);
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

    public static void updateCounter() throws IOException {
        File counter = new File(new File("").getAbsolutePath() + "\\users\\counter.properties");
        Properties counterProp = new Properties();
        FileOutputStream createCounter = new FileOutputStream(counter, false);

        int admin = 0;
        int member = 0;
        int num;
        File[] files = new File(new File("").getAbsolutePath() + "\\users\\").listFiles();
        for (File file : files) {
            if (file.isFile()) {
                if (isNum(file.getName().replaceFirst("[.][^.]+$", "").substring(1))) {
                    num = Integer.parseInt(file.getName().replaceFirst("[.][^.]+$", "").substring(1));
                    if ("A".equals(file.getName().replaceFirst("[.][^.]+$", "").substring(0, 1)) && num > admin) {
                        admin = num;
                    } else if ("M".equals(file.getName().replaceFirst("[.][^.]+$", "").substring(0, 1)) && num > member) {
                        member = num;
                    }
                }
            }
        }

        //If there are no admin/member files then admin counter = 110620,member counter = 220622
        if (admin == 0) {
            admin = 110620;
        }
        if (member == 0) {
            member = 220622;
        }

        counterProp.put("admin", admin + "");
        counterProp.put("member", member + "");

        counterProp.store(createCounter, "ID counter");
        createCounter.close();
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
//        RentalSystem rental = new RentalSystem();
//        rental.createComic();
//        rental.importComics();
//        rental.importProperties();
//        rental.export("123","test","1");
    }
}
