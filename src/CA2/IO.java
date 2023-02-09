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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    public static void export(ArrayList<Comic> comicArr, ArrayList<Rentee> renteeArr, ArrayList<Administrator> adminArr) throws IOException, NoSuchAlgorithmException {
        IO.serialize(comicArr, "Comic.dat");
        IO.serialize(renteeArr, "Rentee.dat");
        IO.serialize(adminArr, "Admin.dat");
//        String data = "root;" + hash("root") + ";root;" + new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new java.util.Date()) + ";3\n";
        String renteeData = "";
        for (Rentee rentee : renteeArr) {
            ArrayList<Comic> comics = rentee.getComics();

            String comic = "";
            if (!comics.isEmpty()) {
                for (int i = 0; i <= (comics.size() - 2); i += 1) {

                    comic += comics.get(i).getISBN() + "#";
                }
                comic += comics.get(comics.size() - 1).getISBN(); //No leading "#"
            }

            renteeData += rentee.getMemberID() + ";" + rentee.getName() + ";" + comic + ";1;" + rentee.getPassword() + ";" + rentee.getLogin() + "\n";
        }
        String comicData = "";
        for (Comic comic : comicArr) {
            comicData += comic.getISBN() + ";" + comic.getTitle() + ";" + comic.getPageNum() + ';' + comic.getCost() + ";" + comic.getType() + ";" + comic.getLanguage() + "\n";
        }

        try {
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
        //Whats the point of serializing if we ar gonna read from txt
        ArrayList<Rentee> renteeList = new ArrayList<Rentee>();
        Path filePath = Paths.get("rentees.txt");
        String data = Files.readString(filePath);

        String[] members = data.split("\n");

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
            renteeList.add(new Rentee(memberData[0], memberData[1], comicList, memberData[3], memberData[4]));
        }
        return renteeList;
    }

    public static ArrayList<Comic> importComics() throws IOException, NoSuchAlgorithmException, ClassNotFoundException {
        //return (ArrayList<Comic>) IO.deserialize("Comic.dat");
        //Whats the point of serializing if we ar gonna read from txt
        Path filePath = Paths.get("comics.txt");
        String data = Files.readString(filePath);
        
        String[] comicData = data.split("\n");
        ArrayList<Comic> comicArr = new ArrayList<Comic>();
        
        for (String index : comicData){
            String[] i = index.split(";");
            comicArr.add(new Comic(i[0],i[1],Integer.parseInt(i[2]),Double.parseDouble(i[3]),i[4],i[5]));
        }
        return comicArr;
    }

    public static ArrayList<Administrator> importAdmin() throws IOException, NoSuchAlgorithmException, ClassNotFoundException {
        return (ArrayList<Administrator>) deserialize("Admin.dat");
    }

    public static String hash(String pw) throws NoSuchAlgorithmException {
        //Sha256 hash cause storing passwords in plaintext is pain (for the company)
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(pw.getBytes());
        String stringHash = new String(messageDigest.digest()); //So that i dont get a byte[]
        //Note to self, use equals when comparing hash to verify pw don tuse ==
        return stringHash;
    }
}
