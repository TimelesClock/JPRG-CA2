/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CA2;

import java.io.Serializable;

/**
 * Class: DIT/FT/1B/02 Name: Leong Yu Zhi Andy Admission Number: P2205865
 *
 * @author leong
 */
public class Manga extends Comic implements Serializable {
    //Normal getter setter code here, moving on
    private boolean translated;

    public Manga(String newISBN, String newTitle, int newPageNum, double newCost, String newLanguage, boolean isTranslated) {
        super(newISBN, newTitle, newPageNum, newCost, "Manga", isTranslated ? "EN" : "JP");
        translated = isTranslated;
    }

    public boolean isTranslated() {
        return translated;
    }

    public void setTranslated(boolean translated) {
        this.translated = translated;
    }

}
