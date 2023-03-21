package p2;

import java.io.*;
import java.util.*;

public class song {
    private String title;
    private int duration;
    private ArrayList <String> genre;
    private String composer;
    private String sid;
    private String language;

    public song(String t, String l, ArrayList <String>  g, String c) {
        title=t;
        language=l;
        genre= g;
        composer=c;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration(){
        return duration;
    }

    public ArrayList<String> getGenres(){
        return genre;
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