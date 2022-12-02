/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package CA1;
/**
 *
 * @author leong
 */
public class Comic {

    private String ISBN;
    private String title;
    private int pageNum;
    private double cost;
    
    public Comic(String newISBN, String newTitle, int newPageNum, double newCost){
        ISBN = newISBN;
        title = newTitle;
        pageNum = newPageNum;
        cost = newCost;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    
    
    
    
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}