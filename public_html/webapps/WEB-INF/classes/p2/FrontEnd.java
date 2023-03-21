package p2;

import java.util.*;
import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

public class FrontEnd {

//SCREEN FOR EMPTY PASSWORD
    public void doGetEmptyPassword_auto(PrintWriter out) {
        out.println("<?xml version='1.0' encoding='utf-8' ?>");
        out.println("<wrongRequest>no passwd</wrongRequest>");
    }

    public void doGetEmptyPassword_browser(PrintWriter out){
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<meta charset='utf-8'>");
        out.println("<link rel='stylesheet' href='musicService.css'>");
        out.println("<body>");
        out.println("<h1>Music Information Service</h1><br>");
        out.println("<h2>No password introduced</h2>");
        out.println("<hr>");
        out.println("&copy; Carlos Calvo Moa (2022-2023)");
        out.println("</body>");
        out.println("</html>");
    }


//SCREEN FOR BAD PASSWORD
    public void doGetBadPassword_auto(PrintWriter out) {
        out.println("<?xml version='1.0' encoding='utf-8' ?>");
        out.println("<wrongRequest>bad passwd</wrongRequest>");
    }

    public void doGetBadPassword_browser(PrintWriter out){
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<title>Music Information Service</title>");
        out.println("<meta charset='utf-8'>");
        out.println("<link rel='stylesheet' href='musicService.css'>");
        out.println("<body>");
        out.println("<h1>Music Information Service</h1><br>");
        out.println("<h2>Incorrect Password</h2>");

        out.println("<hr>");
        out.println("&copy; Carlos Calvo Moa (2022-2023)");
        out.println("</body>");
        out.println("</html>");
    }


//SCREEN FOR MISSING PARAM PCOUNTRY
    public void doGetNoCountry_auto(PrintWriter out) {
        out.println("<?xml version='1.0' encoding='utf-8' ?>");
        out.println("<wrongRequest>no param:pcountry</wrongRequest>");
    }

    public void doGetNoCountry_browser(PrintWriter out){
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<title>Music Information Service</title>");
        out.println("<meta charset='utf-8'>");
        out.println("<link rel='stylesheet' href='musicService.css'>");
        out.println("<body>");
        out.println("<h1>Music Information Service</h1><br>");
        out.println("<h2>Parameter pcountry missing</h2>");

        out.println("<hr>");
        out.println("&copy; Carlos Calvo Moa (2022-2023)");
        out.println("</body>");
        out.println("</html>");
    }

//SCREEN FOR MISSING PARAM PCOUNTRY
public void doGetNoAid_auto(PrintWriter out) {
    out.println("<?xml version='1.0' encoding='utf-8' ?>");
    out.println("<wrongRequest>no param:paid</wrongRequest>");
}

public void doGetNoAid_browser(PrintWriter out){
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<title>Music Information Service</title>");
    out.println("<meta charset='utf-8'>");
    out.println("<link rel='stylesheet' href='musicService.css'>");
    out.println("<body>");
    out.println("<h1>Music Information Service</h1><br>");
    out.println("<h2>Parameter paid missing</h2>");

    out.println("<hr>");
    out.println("&copy; Carlos Calvo Moa (2022-2023)");
    out.println("</body>");
    out.println("</html>");
}


//PHASE 01 ?p="+password+"&pphase=01
    public void doGetPhase01_auto(PrintWriter out) {
        out.println("<?xml version='1.0' encoding='utf-8' ?>");
        out.println("<service>");
        out.println("   <status>OK</status>");
        out.println("</service>");
    }

    public void doGetPhase01_browser(PrintWriter out, String password){
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<title>Music Information Service</title>");
        out.println("<meta charset='utf-8'>");
        out.println("<link rel='stylesheet' href='musicService.css'>");
        out.println("<body>");
        out.println("<h1>Music Information Service</h1><br>");
        out.println("<h2>Welcome to this server</h2>");
        out.println("<h3>Please, select a query:</h3>");
        out.println("<ul>");
        out.println("<li><a href='?p="+password+"&pphase=02'> Show error files</a></li>");
        out.println("<li><a href='?p="+password+"&pphase=11'> Query 1: Pop songs of an Album of a Country</a></li>");
        out.println("</ul><br>");

        out.println("<hr>");
        out.println("&copy; Carlos Calvo Moa (2022-2023)");
        out.println("</body>");
        out.println("</html>");
    }


//PHASE 02  ?p="+password+"&pphase=02
    public void doGetPhase02_auto(PrintWriter out, ArrayList<String>errorsMUML, ArrayList<String>fatalErrorsMUML) {
        out.println("<?xml version='1.0' encoding='utf-8' ?>");
        out.println("<wrongDocs>");
        out.println("   <errors>");
        for (int i = 0; i < errorsMUML.size(); i++) {
            out.println("<error>");
            out.println("<file>");
            out.print(errorsMUML.get(i));
            out.println("</file>");
            out.println("</error>");
        }
        out.println("   </errors>");
        out.println("   <fatalerrors>");
        for (int i = 0; i < fatalErrorsMUML.size(); i++) {
            out.println("<fatalerror>");
            out.println("<file>");
            out.print(fatalErrorsMUML.get(i));
            out.println("</file>");
            out.println("</fatalerror>");
        }
        out.println("</fatalerrors>");
        out.println("</wrongDocs>");
    }

    public void doGetPhase02_browser(PrintWriter out, String password, ArrayList<String>errorsLink,ArrayList<String>fatalErrorsLink){
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<title>Music Information Service</title>");
        out.println("<meta charset='utf-8'>");
        out.println("<link rel='stylesheet' href='musicService.css'>");
        out.println("<body>");
        out.println("<h1>Music Information Service</h1><br>");
        out.println("<h3>Files with errors: "+errorsLink.size()+"</h3>");
        out.println("<ul>");
        for (int i = 0; i < errorsLink.size(); i++) {
            out.println("<li>"+errorsLink.get(i)+"</li>");
        }
        out.println("</ul>");
        out.println("<h3>Files with fatal errors: "+fatalErrorsLink.size()+"</h3>");
        out.println("<ul>");
        for (int i = 0; i < fatalErrorsLink.size(); i++) {
            out.println("<li>"+fatalErrorsLink.get(i)+"</li>");
        }
        out.println("</ul>");
        out.println("<br><button type='button' id='back_button' onclick=\"location.href='?p="+password+"&pphase=01'\">Back</button>");
        out.println("<hr>");
        out.println("&copy; Carlos Calvo Moa (2022-2023)");
        out.println("</body>");
        out.println("</html>");

    }


//PHASE 11 ?p="+password+"&pphase=11
    public void doGetPhase11_auto(PrintWriter out,ArrayList<String> countries) {
        out.println("<?xml version='1.0' encoding='utf-8' ?>");
        out.println("<countries>");
        for (int i = 0; i < countries.size(); i++) {
            out.print("<country>");
            out.print(countries.get(i));
            out.print("</country>");
        }
        out.println("</countries>");
    }

    public void doGetPhase11_browser(PrintWriter out, String password, ArrayList<String> countries){
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<title>Music Information Service</title>");
        out.println("<meta charset='utf-8'>");
        out.println("<link rel='stylesheet' href='musicService.css'>");
        out.println("<body>");
        out.println("<h1>Music Information Service</h1><br>");
        out.println("<h2>Query 1: Phase 1</h2>");
        out.println("<h3>Please, select a Country:</h3>");
        out.println("<ol>");
        for (int i = 0; i < countries.size(); i++) {
            out.println("<li><a href='?p="+password+"&pphase=12&pcountry="+countries.get(i)+"'>"+countries.get(i)+"</a></li>");
        }
        out.println("</ol>");
        out.println("<br><button id='home_button' onclick=\"location.href='?p="+password+"&pphase=01'\">Home</button>");
        out.println("<hr>");
        out.println("&copy; Carlos Calvo Moa (2022-2023)");
        out.println("</body>");
        out.println("</html>");
    }


//PHASE 12  ?p="+password+"&pphase=12&pcountry="+country+"
    public void doGetPhase12_auto(PrintWriter out,ArrayList<album> list_albums) {
        out.println("<?xml version='1.0' encoding='utf-8' ?>");
        out.println("<albums>");
        for (int i = 0; i < list_albums.size(); i++) {
            out.println("<album year='"+list_albums.get(i).getYear()+"' performer='"+list_albums.get(i).getPerformer()+"' review='"+list_albums.get(i).getReview()+"'> "+list_albums.get(i).getName()+"</album>");
        }
        out.println("</albums>");
    }

    public void doGetPhase12_browser(PrintWriter out, String password, ArrayList<album> list_albums, String country){
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<title>Music Information Service</title>");
        out.println("<meta charset='utf-8'>");
        out.println("<link rel='stylesheet' href='musicService.css'>");
        out.println("<body>");
        out.println("<h1>Music Information Service</h1><br>");
        out.println("<h2>Query 1: Phase 2 (Country: "+country+")</h2>");
        out.println("<h3>Please, select an Album:</h3>");
        out.println("<ol>");
        for (int i = 0; i < list_albums.size(); i++) {
            out.println("<li><a href='?p="+password+"&pphase=13&pcountry="+country+"&paid="+list_albums.get(i).getAid()+"'><b>Album: </b>'"+list_albums.get(i).getName()+"'</a><b> --- Year: </b>'"+list_albums.get(i).getYear()+"'<b> --- Performer: </b>'"+list_albums.get(i).getPerformer()+"'<b> --- Review: </b>'"+list_albums.get(i).getReview()+"'</li>");
        }
        out.println("</ol>");
        out.println("<br><button id='home_button' onclick=\"location.href='?p="+password+"&pphase=01'\">Home</button>");
        out.println("<button type='button' id='back_button' onclick=\"location.href='?p="+password+"&pphase=11';\">Back</button>");
        out.println("<hr>");
        out.println("&copy; Carlos Calvo Moa (2022-2023)");
        out.println("</body>");
        out.println("</html>");
    }


//PHASE 13 ?p="+password+"&pphase=13&pcountry="+country+"&paid="aid"
    public void doGetPhase13_auto(PrintWriter out,ArrayList<song> list_songs) {
        out.println("<?xml version='1.0' encoding='utf-8' ?>");
        out.println("<songs>");
        for (int i = 0; i < list_songs.size(); i++) {
            ArrayList <String> genreList =list_songs.get(i).getGenres();
            String genre = genreList.toString().replace("[","").replace("]","");
            out.println("<song lang='"+list_songs.get(i).getLanguage()+"' genres='"+genre+"' composer='"+list_songs.get(i).getComposer()+"'>"+list_songs.get(i).getTitle()+"</song>");
        }
        out.println("</songs>");
    }

    public void doGetPhase13_browser(PrintWriter out, String password, ArrayList<song> list_songs, String country, String aid){
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<title>Music Information Service</title>");
        out.println("<meta charset='utf-8'>");
        out.println("<link rel='stylesheet' href='musicService.css'>");
        out.println("<body>");
        out.println("<h1>Music Information Service</h1><br>");
        out.println("<h2>Query 1: Phase 3 (Country: "+country+" , Album: "+aid+")</h2>");
        out.println("<h3>This is the query result:</h3>");
        out.println("<ol>");
        for (int i = 0; i < list_songs.size(); i++) {
            ArrayList <String> genreList =list_songs.get(i).getGenres();
            String genre = genreList.toString().replace("[","").replace("]","");
            out.println("<li><b>Title: </b>'"+list_songs.get(i).getTitle()+"'<b> --- Language: </b>'"+list_songs.get(i).getLanguage()+"'<b> --- Genres: </b>'"+genre+"'<b> --- Composer: </b>'"+list_songs.get(i).getComposer()+"'</li>");
        }
        out.println("</ol>");
        out.println("<br><button id='home_button' onclick=\"location.href='?p="+password+"&pphase=01'\">Home</button>");
        out.println("<button type='button' id='back_button' onclick=\"location.href='?p="+password+"&pphase=12&pcountry="+country+"'\">Back</button>");
        out.println("<hr>");
        out.println("&copy; Carlos Calvo Moa (2022-2023)");        
        out.println("</body>");
        out.println("</html>");
    }

}