package p4;

import java.io.*;
import java.util.*;

public class song{
    private String title;
    private String duration;
    private String genre;
    private ArrayList<String> genre_list;
    private String composer;
    private String sid;
    private String language;

    public song(String songSid, String songLanguage, String songTitle, String songDuration, ArrayList<String> genreList, String songGenre, String songComposer) {
        sid= songSid;
        language=songLanguage;
        title=songTitle;
        duration= songDuration;
        genre_list=genreList;
        genre= songGenre;
        composer=songComposer;
    }

    // GETTERS
    public String getTitle() {
        return title;
    }
    public String getDuration(){
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

