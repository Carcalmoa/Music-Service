package p3;

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

    public album(String n, int y, String p, String r, String a) {
        name = n;
        year=y;
        performer=p;
        review=r;
        aid= a;
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