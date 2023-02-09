/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CA2;

import java.io.Serializable;

/**
 *
 * @author leong
 */
public class Manga extends Comic implements Serializable{
    private boolean translated;
    public Manga(String newISBN, String newTitle, int newPageNum, double newCost, String newType, String newLanguage,boolean translated) {
        super(newISBN, newTitle, newPageNum, newCost, newType, newLanguage);
        translated = translated;
    }

    public boolean isTranslated() {
        return translated;
    }

    public void setTranslated(boolean translated) {
        this.translated = translated;
    }
    
    
}
