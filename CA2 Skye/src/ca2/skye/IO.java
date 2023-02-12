/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca2.skye;

import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
/**
 * Class: DIT/FT/1B/02 

 */
/**
 *
 * @author leong
 */
public class IO implements Serializable {

    public static Object deserialize(String fileName) throws IOException,
            ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object obj = ois.readObject();
        ois.close();
        return obj;
    }

    // serialize the given object and save it to file
    public static void serialize(Object obj, String fileName)
            throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(obj);

        fos.close();
    }

    public static void export(ArrayList<Comic> comicArr, ArrayList<Rentee> renteeArr) throws IOException, NoSuchAlgorithmException {
        IO.serialize(comicArr, "Comic.dat");
        
        IO.serialize(renteeArr, "Rentee.dat");

        String renteeData = "";
        //Handle export of rentee data
        for (Rentee rentee : renteeArr) {
            ArrayList<Comic> comics = rentee.getComics();

            String comic = "";
            if (!comics.isEmpty()) {
                for (int i = 0; i <= (comics.size() - 2); i += 1) {

                    comic += comics.get(i).getISBN() + "#";
                }
                comic += comics.get(comics.size() - 1).getISBN(); //No leading "#"
            }

            renteeData += rentee.getMemberID() + ";" + rentee.getName() + ";" + comic + "\n";
        }
        //Handle export of comic data
        String comicData = "";
        for (Comic comic : comicArr) {
            comicData += comic.getISBN() + ";" + comic.getTitle() + ";" + comic.getPageNum() + ';' + comic.getCost() + ";" + comic.getType() + ";" + comic.getLanguage() + "\n";
        }

        try {
            //Filewriter, write,flush,close
            FileWriter filewriter = new FileWriter("rentees.txt");
            filewriter.write(renteeData);
            filewriter.flush();
            filewriter.close();
            FileWriter filewriter2 = new FileWriter("comics.txt");
            filewriter2.write(comicData);
            filewriter2.flush();
            filewriter2.close();
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public static ArrayList<Rentee> importRentee(ArrayList<Comic> comicArr) throws IOException, NoSuchAlgorithmException, ClassNotFoundException {


        ArrayList<Rentee> renteeList = new ArrayList<Rentee>();
        Path filePath = Paths.get("rentees.txt");
        String data = Files.readString(filePath);

        String[] members = data.split("\n");
        //Due to requirements, get txt as string, read using .split method
        for (String member : members) {
            String[] memberData = member.split(";");
            ArrayList<Comic> comicList = new ArrayList<Comic>();
            for (String comicISBN : memberData[2].split("#")) {
                if ("".equals(comicISBN)) {
                    continue;
                }
                boolean flag = false;
                for (Comic i : comicArr) {
                    if (comicISBN.equals(i.getISBN())) {
                        comicList.add(i);
                        flag = true;
                        break;
                    }
                }
                if (flag == false) {
                    System.out.println("Comic, " + comicISBN + " is either not added or missing in the system and will not be imported into account " + memberData[0]);
                }
            }
            renteeList.add(new Rentee(memberData[0], memberData[1], comicList));
        }
        return renteeList;
    }

    public static ArrayList<Comic> importComics() throws IOException, NoSuchAlgorithmException, ClassNotFoundException {

        Path filePath = Paths.get("comics.txt");
        String data = Files.readString(filePath);
        //Due to requirements, get txt as string, read using .split method
        String[] comicData = data.split("\n");
        ArrayList<Comic> comicArr = new ArrayList<Comic>();
        
        for (String index : comicData){
            String[] i = index.split(";");
            if (i[4].equals("Manga")){
                comicArr.add(new Manga(i[0],i[1],Integer.parseInt(i[2]),Double.parseDouble(i[3]),i[4],i[5].equals("EN")));
            }else{
                comicArr.add(new Comic(i[0],i[1],Integer.parseInt(i[2]),Double.parseDouble(i[3]),i[4],i[5]));
            }
            
        }
        return comicArr;
    }

}
