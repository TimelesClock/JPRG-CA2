/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CA1;

import javax.swing.JOptionPane;

/**
 *
 * @author leong
 */
public class RentalSystem {

    private Comic[] comicArr = new Comic[4];
    private Rentee[] renteeArr = new Rentee[4];

    public RentalSystem() {
    }

    public void createComic() {
        comicArr[0] = new Comic("978-0785199618", "Spider-Man: Miles Morales", 112, 14.39);
        comicArr[1] = new Comic("978-0785190219", "Ms. Marvel: No Normal", 120, 15.99);
        comicArr[2] = new Comic("978-0785198956", "Secret Wars", 312, 34.99);
        comicArr[3] = new Comic("978-0785156598", "Infinity Gauntlet", 256, 24.99);
    }
    
    public void createRentee(){
        renteeArr[0] = new Rentee("M220622","Ariq Sulaiman",new Comic[] {comicArr[0],comicArr[1]});
        renteeArr[0] = new Rentee("M220623","Sayang Sulaiman",new Comic[] {comicArr[0],comicArr[2]});
        renteeArr[0] = new Rentee("M220624","Ben Dover",new Comic[] {comicArr[3],comicArr[2]});
        
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
                          """,flag.getTitle(), price, price / 20, price * 1.1);
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

    public static void main(String[] args) {
        RentalSystem rental = new RentalSystem();
        rental.createComic();
        rental.displayComic();
    }
}
