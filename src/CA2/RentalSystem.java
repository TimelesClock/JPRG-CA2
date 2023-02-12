/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CA2;


import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.security.*;

import java.io.IOException;



import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Class: DIT/FT/1B/02 
 * Name: Leong Yu Zhi Andy 
 * Admission Number: P2205865
 * @author leong
 */
/**
 * 
 *
 * @author leong
 */
public class RentalSystem {

    private ArrayList<Comic> comicArr = new ArrayList<Comic>();
    private ArrayList<Manga> mangaArr = new ArrayList<Manga>();
    private ArrayList<Rentee> renteeArr = new ArrayList<Rentee>();
    private ArrayList<Administrator> adminArr = new ArrayList<Administrator>();

    public RentalSystem() {
        try {
            //When RentalSystem is called, import comic,rentee,admin
            importComics();
            importRentee();
            importAdmin();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public ArrayList<Manga> getMangaArr() {
        return mangaArr;
    }

    public void setMangaArr(ArrayList<Manga> mangaArr) {
        this.mangaArr = mangaArr;
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
    //Add comics to rentee using comic name, same as CA1
    public String addComicToRentee(String name, String comicName) throws IOException, NoSuchAlgorithmException {
        int index = -1;
        int index2 = -1;
        for (int i = 0; i < renteeArr.size(); i++) {
            if (renteeArr.get(i).getName().equals(name)) {
                index = i;
                break;

            }
        }
        if (index != -1) {
            List<String> comic = new ArrayList<String>();

            for (int k = 0; k < renteeArr.get(index).getComics().size(); k++) {
                comic.add(renteeArr.get(index).getComics().get(k).getTitle());
            }
            if (comic.contains(comicName)) {
                return "The rentee already has this comic!";
            } else {
                for (int o = 0; o < comicArr.size(); o++) {
                    if (comicArr.get(o).getTitle().equals(comicName)) {
                        index2 = o;
                    }
                }

                if (index2 != -1) {
                    Rentee rent = renteeArr.get(index);
                    ArrayList<Comic> temp = rent.getComics();
                    temp.add(comicArr.get(index2));
                    renteeArr.set(index, new Rentee(rent.getMemberID(), rent.getName(), temp));
                    IO.export(comicArr, renteeArr, adminArr);
                    return "Comic has been successfully added to the Rentee";
                }

            }
        } else {
            return "The member ID entered could not be found!";
        }
        return "";

    }

    //Code testing purposes
    public void createComic() throws IOException, NoSuchAlgorithmException {
        comicArr.add(new Comic("978-0785199618", "Spider-Man: Miles Morales", 112, 14.39, "Comic", "EN"));
        comicArr.add(new Comic("978-0785190219", "Ms. Marvel: No Normal", 120, 15.99, "Comic", "EN"));
        comicArr.add(new Comic("978-0785198956", "Secret Wars", 312, 34.99, "Comic", "EN"));
        comicArr.add(new Comic("978-0785156598", "Infinity Gauntlet", 256, 24.99, "Comic", "EN"));
        comicArr.add(new Manga("978-4091400017", "Doremon", 191, 12.88, "Manga", true));
        comicArr.add(new Manga("978-4091400018", "DoremonJP", 191, 12.88, "Manga", false));
    }
    //Code testing purposes
    public void createRentee() throws IOException, NoSuchAlgorithmException {
        renteeArr.add(new Rentee("M220622", "Ariq Sulaiman", new ArrayList<Comic>(Arrays.asList(comicArr.get(0), comicArr.get(1)))));
        renteeArr.add(new Rentee("M220623", "Sayang Sulaiman", new ArrayList<Comic>(Arrays.asList(comicArr.get(0), comicArr.get(2)))));
        renteeArr.add(new Rentee("M220624", "Ben Dover", new ArrayList<Comic>(Arrays.asList(comicArr.get(3), comicArr.get(3)))));

    }
    //Code testing purposes
    public void createAdmin() throws IOException, NoSuchAlgorithmException {
        adminArr.add(new Administrator("A110620", "rootLow", hash("root")));
        adminArr.add(new Administrator("root", "root", hash("root")));

    }
    //Get earning stats
    public String getEarning() {
        int renteeNum = renteeArr.size();
        double total = 0.0;

        for (Rentee i : renteeArr) {
            for (Comic o : i.getComics()) {
                total += o.getCost() / 20.0;
            }
        }

        double average = total / renteeNum;

        String out = String.format("Earning Per Day:\n---------------\nThere are %d Rentees in total.\n\nAverage earning day based on numbers of rentees is $%.2f.\n\nTotal earning per day is $%.2f.", renteeNum, average, total);

        return out;

    }
    //Add new rentee
    public void addToRentee(String memberID, String name, ArrayList<Comic> comic) throws IOException, NoSuchAlgorithmException {
        Rentee newRentee = new Rentee(memberID, name, comic);
        renteeArr.add(newRentee);
        IO.export(comicArr, renteeArr, adminArr);

    }
    //Not used in ca2
    public void addToAdmin(String memberID, String name, String password) throws IOException, NoSuchAlgorithmException {
        Administrator newAdmin = new Administrator(memberID, name, password);
        adminArr.add(newAdmin);
        IO.export(comicArr, renteeArr, adminArr);

    }
    
    public void removeFromRentee(String memberName) throws IOException,NoSuchAlgorithmException{
        //Remove rentee from renteelist
        int index = 0;
        for (Rentee rentee : renteeArr){
            if (rentee.getName().equals(memberName)){
                break;
            }else{
                index++;
            }
        }
        
        renteeArr.remove(index);
        IO.export(comicArr, renteeArr, adminArr);
    }
    
    public void removeFromComic(String comicName) throws IOException,NoSuchAlgorithmException,ClassNotFoundException{
        //Remove comic from comic list
        int index = 0;
        for (Comic comic : comicArr){
            if (comic.getTitle().equals(comicName)){
                break;
            }else{
                index++;
            }
        }
        
        comicArr.remove(index);
        IO.export(comicArr, renteeArr, adminArr);
        //Importing rentee to remove the non existent comics
        importRentee();
    }

    public void addToComic(String title, String ISBN, String pageNum, String cost, String type, String language) throws IOException, NoSuchAlgorithmException {
        //Adds either a manga object or comic object based on GUI
        if (type.equals("Manga")) {
            Manga manga = new Manga(ISBN, title, Integer.parseInt(pageNum), Double.parseDouble(cost), type, language.equals("English"));
            comicArr.add(manga);
        } else {
            Comic comic = new Comic(ISBN, title, Integer.parseInt(pageNum), Double.parseDouble(cost), type, language.equals("English") ? "EN" : "JP");
            comicArr.add(comic);
        }

        IO.export(comicArr, renteeArr, adminArr);
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

    public static boolean validateISBN(String isbn) {
        Pattern regex = Pattern.compile("^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$");

        Matcher match = regex.matcher(isbn);

        return match.find();
    }

    public void importComics() throws IOException, NoSuchAlgorithmException, ClassNotFoundException {
        this.comicArr = IO.importComics();
    }

    public void importRentee() throws IOException, NoSuchAlgorithmException, ClassNotFoundException {
        this.renteeArr = IO.importRentee(comicArr);
    }

    public void importAdmin() throws IOException, NoSuchAlgorithmException, ClassNotFoundException {
        this.adminArr = IO.importAdmin();
    }

    public boolean login(String ID, String pwd) {
        //Return true if login credentials match
        try {
            boolean flag = false;
            for (Administrator admin : adminArr) {
                if (admin.getMemberID().equals(ID)) {
                    if (hash(pwd).equals(admin.getPw())) {
                        flag = true;
                    } else {
                        break;
                    }
                }
            }
            return flag;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, ClassNotFoundException {
        //Code testing
        RentalSystem rental = new RentalSystem();
        rental.createComic();
        rental.createRentee();
        rental.createAdmin();
//        rental.importProperties();
        IO.export(rental.getComicArr(), rental.getRenteeArr(), rental.getAdminArr());
        rental.importComics();
        rental.importRentee();
        rental.importAdmin();
//        rental.export("123","test","1");

    }
}
