package p3;

import java.io.*;
import java.util.*;

public class song{
    private String title;
    private int duration;
    private String genre;
    private ArrayList<String> genre_list;
    private String composer;
    private String sid;
    private String language;

    public song(String t, String l, ArrayList<String> g_list, String  g, String c) {
        title=t;
        language=l;
        genre= g;
        genre_list=g_list;
        composer=c;
    }

    // GETTERS
    public String getTitle() {
        return title;
    }
    public int getDuration(){
        return duration;
    }
    public String getGenre(){
        return genre;
    }
    public ArrayList<String> getGenre_list() {
        return genre_list;
    }
    public String getSid(){
        return sid;
    }
    public String getLanguage(){
        return language;
    }
    public String getComposer(){
        return composer;
    }

}

