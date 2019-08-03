package com.example.android.tasheeh;

import java.io.Serializable;

public class Ayah implements Serializable {

    private String surahName;
    private String ayahText;
    private int ayahNo;
    private String numberAsString="";

    public Ayah(String surahName,String ayahText,int ayahNo){
        this.ayahNo = ayahNo;
        this.ayahText = ayahText;
        this.surahName = surahName;
    }

    public String getSurahName() {
        return surahName;
    }

    public String getAyahText() {
        return ayahText;
    }

    public String getAyahNo(){
        numberAsString = Integer.toString(ayahNo);
        return numberAsString;
    }
}

