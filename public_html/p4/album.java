package p4;

import java.io.*;
import java.util.*;

public class album{
    private String name;
    private int year;
    private String country;
    private String performer;
    private String ISBN;
    private String company;
    private ArrayList<song> list_songs;
    private String review;
    private String format;
    private String aid;

    
    public album(int albumYear, String albumAid, String albumFormat, String albumName, String albumCountry, String albumSinger, String albumISBN, String albumCompany, String albumReview, ArrayList<song> songList) {
        year=albumYear;
        aid=albumAid;
        format=albumFormat;
        name= albumName;
        country= albumCountry;
        performer= albumSinger;
        ISBN= albumISBN;
        company= albumCompany;
        review= albumReview;
        list_songs= songList;
    }

    //GETTERS
    public String getName() {
        return name;
    }
    public int getYear() {
        return year;
    }
    public String getCountry(){
        return country;
    }
    public String getPerformer(){
        return performer;
    }
    public String getISBN(){
        return ISBN;
    }
    public String getCompany(){
        return company;
    }
    public ArrayList<song> getList_songs(){
        return list_songs;
    }
    public String getReview(){
        return review;
    }
    public String getFormat(){
        return format;
    }
    public String getAid(){
        return aid;
    }
}