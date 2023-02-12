/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca2.skye;


import java.util.ArrayList;
import java.util.Arrays;
import java.security.*;

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**

 */
/**
 * 
 *
 * @author leong
 */
public class RentalSystem {

    private ArrayList<Comic> comicArr = new ArrayList<Comic>();

    private ArrayList<Rentee> renteeArr = new ArrayList<Rentee>();


    public RentalSystem() {
        try {
            importComics();
            importRentee();

        } catch (Exception e) {
            System.out.println(e);
        }

    }


    public ArrayList<Comic> getComicArr() {
        return comicArr;
    }

    public ArrayList<Rentee> getRenteeArr() {
        return renteeArr;
    }


    //Add comics to rentee, same as CA1

    //Code testing purposes
    public void createComic() throws IOException, NoSuchAlgorithmException {
        comicArr.add(new Comic("978-0785199618", "Spider-Man: Miles Morales", 112, 14.39, "Comic", "EN"));
        comicArr.add(new Comic("978-0785190219", "Ms. Marvel: No Normal", 120, 15.99, "Comic", "EN"));
        comicArr.add(new Comic("978-0785198956", "Secret Wars", 312, 34.99, "Comic", "EN"));
        comicArr.add(new Comic("978-0785156598", "Infinity Gauntlet", 256, 24.99, "Comic", "EN"));
        comicArr.add(new Manga("978-4091400017", "Doremon", 191, 12.88, "Manga", true));
        comicArr.add(new Manga("978-4091400017", "Doremon", 191, 12.88, "Manga", false));
    }
    //Code testing purposes
    public void createRentee() throws IOException, NoSuchAlgorithmException {
        renteeArr.add(new Rentee("M220622", "Ariq Sulaiman", new ArrayList<Comic>(Arrays.asList(comicArr.get(0), comicArr.get(1)))));
        renteeArr.add(new Rentee("M220623", "Sayang Sulaiman", new ArrayList<Comic>(Arrays.asList(comicArr.get(0), comicArr.get(2)))));
        renteeArr.add(new Rentee("M220624", "Ben Dover", new ArrayList<Comic>(Arrays.asList(comicArr.get(3), comicArr.get(3)))));

    }
    //Code testing purposes

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



    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, ClassNotFoundException {
        //Code testing
        RentalSystem rental = new RentalSystem();
        rental.createComic();
        rental.createRentee();


        IO.export(rental.getComicArr(), rental.getRenteeArr());
        rental.importComics();
        rental.importRentee();

    }
}
