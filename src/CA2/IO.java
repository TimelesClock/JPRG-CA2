/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CA2;

import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.File;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class: DIT/FT/1B/02 Name: Leong Yu Zhi Andy Admission Number: P2205865
 *
 * @author leong
 */
/**
 *
 * @author leong
 */
public class IO implements Serializable {
    //Deserialize objects
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
    //Export all 3 main arrays, rentee array comic array and admin array
    public static void export(ArrayList<Comic> comicArr, ArrayList<Rentee> renteeArr, ArrayList<Administrator> adminArr) throws IOException, NoSuchAlgorithmException {
        //Object Serialization
        IO.serialize(comicArr, "Comic.dat");

        IO.serialize(renteeArr, "Rentee.dat");
        IO.serialize(adminArr, "Admin.dat");
        
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
            }else{
                comic += "#";
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

        //return (ArrayList<Rentee>) IO.deserialize("Rentee.dat");
        
        //Above code is if reading data from deserialization
        
        //Whats the point of serializing if we are gonna read from txt
        File file = new File("rentees.txt");
        if (!file.exists()) {
            file.createNewFile();
            return new ArrayList<Rentee>();
        }

        ArrayList<Rentee> renteeList = new ArrayList<Rentee>();
        Path filePath = Paths.get("rentees.txt");
        String data = Files.readString(filePath);
        if (data.equals("")) {

            return new ArrayList<Rentee>();
        }
        String[] members = data.split("\n");

        //Due to requirements, get txt as string, read using .split method
        for (String member : members) {
            String[] memberData = member.split(";");

            ArrayList<Comic> comicList = new ArrayList<Comic>();

            if (!memberData[2].equals("")) {
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
            }

            renteeList.add(new Rentee(memberData[0], memberData[1], comicList));
        }
        return renteeList;
    }

    public static ArrayList<Comic> importComics() throws IOException, NoSuchAlgorithmException, ClassNotFoundException {
        //return (ArrayList<Comic>) IO.deserialize("Comic.dat");
        
        //Above code is if reading data from deserialization
        
        //Whats the point of serializing if we ar gonna read from txt
        File file = new File("comics.txt");
        if (!file.exists()) {
            file.createNewFile();
            return new ArrayList<Comic>();
        }
        Path filePath = Paths.get("comics.txt");
        String data = Files.readString(filePath);
        if (data.equals("")) {

            return new ArrayList<Comic>();
        }
        //Due to requirements, get txt as string, read using .split method
        String[] comicData = data.split("\n");

        ArrayList<Comic> comicArr = new ArrayList<Comic>();

        for (String index : comicData) {
            String[] i = index.split(";");
            if (i[4].equals("Manga")) {
                comicArr.add(new Manga(i[0], i[1], Integer.parseInt(i[2]), Double.parseDouble(i[3]), i[4], i[5].equals("EN")));
            } else {
                comicArr.add(new Comic(i[0], i[1], Integer.parseInt(i[2]), Double.parseDouble(i[3]), i[4], i[5]));
            }

        }
        return comicArr;
    }

    public static ArrayList<Administrator> importAdmin() throws IOException, NoSuchAlgorithmException, ClassNotFoundException {
        //Handle if file doesnt exist
        File file = new File("Admin.dat");
        if (!file.exists()) {

            ArrayList<Administrator> temp = new ArrayList<Administrator>();
            temp.add(new Administrator("A110620", "rootLow", hash("root")));
            temp.add(new Administrator("root", "root", hash("root")));
            IO.serialize(temp, "Admin.dat");
            return (ArrayList<Administrator>) deserialize("Admin.dat");
        } else {
            //Object deserialization
            return (ArrayList<Administrator>) deserialize("Admin.dat");
        }

    }
    //Cant remember why a 2nd version of this is here but im too scared to break anything at this point lmao
    public static String hash(String pw) throws NoSuchAlgorithmException {
        //Sha256 hash cause storing passwords in plaintext is pain (for the company)
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(pw.getBytes());
        String stringHash = new String(messageDigest.digest()); //So that i dont get a byte[]
        //Note to self, use equals when comparing hash to verify pw don tuse ==
        return stringHash;
    }
}
