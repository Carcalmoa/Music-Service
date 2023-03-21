package p4;

import java.io.*;
import java.util.*;
import java.net.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import javax.xml.xpath.*;
import javax.xml.parsers.*;
import javax.print.attribute.HashAttributeSet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import java.lang.String;


public class DataModel extends HttpServlet{

    static DocumentBuilderFactory dbf= null;
	static DocumentBuilder db = null;
    static ArrayList <String> genreList=new ArrayList<String>(); // LIST WHERE I WILL SAVE THE GENRES OF A SONG
    static ArrayList <song> songList= new ArrayList<song>(); // LIST WHERE I WILL SAVE THE SONGS OF AN ALBUM
    static ArrayList<String> text= new ArrayList<String>(); // LIST WHERE I WILL FIND ALL THE LINES BETWEEN <ALBUM> </ALBUM>

    static ArrayList<String> errorsLink = new ArrayList<String>(); //LIST OF MUMLS WITH ERRORS FOR THE BROWSER MODE SRCEEN
    public static ArrayList<String> getErrorsLink(){
        return errorsLink;
    }

    static ArrayList<String> fatalErrorsLink = new ArrayList<String>(); //LIST OF MUMLS WITH FATAL ERRORS FOR THE BROWSER MODE SRCEEN
    public static ArrayList<String> getFatalErrorsLink(){
        return fatalErrorsLink;
    }

    static ArrayList<String> errorsMUML = new ArrayList<String>(); // LIST OF MUMLS WITH ERRORS FOR THE AUTO MODE SRCEEN
    public static ArrayList<String> getErrorsMUML(){
        return errorsMUML;
    }

    static ArrayList<String> fatalErrorsMUML = new ArrayList<String>(); //LIST OF MUMLS WITH FATAL ERRORS FOR THE AUTO MODE 
    public static ArrayList<String> getFatalErrorsMUML(){
        return fatalErrorsMUML;
    }

    static ArrayList<String> mumls_to_analyze_list=new ArrayList<String>(); // LIST OF MUMLS THAT HAVE TO BE ANALYZED

    // GETQ1COUNTRIES
    static ArrayList <String> countriesList = new ArrayList <String>(); // LIST WITH ALL THE COUNTRIES FIND IN THE CORRECT MUMLS
    public static ArrayList<String> getQ1Countries(){
        // REMOVE THE DUPLICATE COUNTRIES
        HashSet<String> hs = new HashSet<String>();
        hs.addAll(countriesList);
        countriesList.clear();
        countriesList.addAll(hs);
        // SORT THE LIST IN REVERSE ALPHABETIC ORDER
        Collections.sort(countriesList);
        Collections.reverse(countriesList);
        return countriesList;
    }

    // GETQ1ALBUMS
    static ArrayList<album> albumList= new ArrayList<album>(); // LIST WITH ALL THE ALBUMS 
    public static ArrayList<album> getQ1Albums(String country){
        ArrayList<album> albumListOfCountry= new ArrayList<album>(); // LIST WITH ALL THE ALBUMS OF A SPECIFIC COUNTRY
        for (int x = 0; x < albumList.size(); x++) {
            album album=albumList.get(x);
            if(album.getCountry().equals(country)){ // CHECK IF THE ALBUM BELONGS TO THE COUNTRY RECEIVED BY ARGUMENT
                albumListOfCountry.add(album);
            }
        }
        //SORT THE LIST WITH THE ALBUMS OF A SPECIFIC COUNTRY
        Collections.sort(albumListOfCountry, new Comparator<album>(){
            public int compare(album a1, album a2){
                if (a1.getYear() < a2.getYear()) 
                    return -1;
                else if (a1.getYear()==a2.getYear())
                    return a1.getName().compareTo(a2.getName());
                else 
                    return 1;
            }    
        });
        return albumListOfCountry;
    }

    // GETQ1SONGS
    public static ArrayList<song> getQ1Songs(String country, String aid){
        ArrayList<song> songListOfAlbum= new ArrayList<song>(); // LIST WITH ALL THE SONGS OF A SPECIFIC ALBUM
        for (int i = 0; i < albumList.size(); i++) { // GO THROUGH ALL ALBUMS
            album album=albumList.get(i);
            if(((album.getCountry().equals(country))&&(album.getAid().equals(aid)))){ 
                songListOfAlbum=album.getList_songs(); // GET THE LIST OF SONGS OF THE ALBUM WITH THE CORRESPONDING COUNTRY AND AID
            }
        }
        //SORT THE LIST OF SONGS
        Collections.sort(songListOfAlbum, new Comparator<song>(){
            public int compare(song s1, song s2){
                if (s1.getGenre_list().size() < s2.getGenre_list().size()) 
                    return -1;
                else if (s1.getGenre_list().size()==s2.getGenre_list().size())
                    return s1.getTitle().compareTo(s2.getTitle());
                else 
                    return 1;
            }          
        });
        return songListOfAlbum;
    }

    //METHOD FOR GETTING ALL THE MUMLS AND ANALIZE IF THEY ARE CORRECT OR IF THEY HAVE ERRORS OR FATALERRORS
    public static void errorChecker() throws SAXException, ParserConfigurationException, IOException, MalformedURLException, XPathExpressionException{         
        dbf = DocumentBuilderFactory.newInstance();
        db = dbf.newDocumentBuilder();

        String xpathTarget_muml ="//MuML";
        Document doc;
        
        //LIST WHERE I WILL SAVE ALL THE CORRERCT MUMLSTRINGS FOR ANALYZE THEM
        mumls_to_analyze_list.add("muml2001.xml");
        

        //UNTIL WE DO NOT HAVE MORE MUMLSTRINGS TO ANALYZE
        while(mumls_to_analyze_list.size()!=0){ 
            String mumlString=mumls_to_analyze_list.get(0);    //WE INITIALIZE THE VALUE OF THE mumlString TO THE FIRST VALUE IN THE LIST OF MUMLS TO ANALYZE, THAT IS THE ONE THAT WE WILL ANALYZE
            String linkString= "http://alberto.gil.webs.uvigo.es/SINT/22-23/"+mumlString+"";
            URL link= new URL(linkString); 
            //CHECK IF FATAL ERROR, EXCEPTION WHEN TRYING TO PARSE
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            DefaultHandler handler = new DefaultHandler() {
                //INITILIZE THE BOOLEANS USED FOR PARSE 
                boolean musicTag= false;
                boolean yearTag= false;
                boolean albumTag= false;
                boolean albumNameTag= false;
                boolean albumCountryTag= false;
                boolean albumPerformerTag= false;
                boolean albumISBNTag= false;
                boolean albumCompanyTag= false;
                boolean albumReviewTag= false;
                boolean extraTextTag= false;
                boolean songTag= false;
                boolean songTitleTag= false;
                boolean songDurationTag= false;
                boolean songGenreTag= false;
                boolean songComposerTag= false;
                boolean songMuMLTag= false;
                boolean correctMuML=false; // USED TO AVOID SAVING INFO OF A INCORRECT MUML

                // INITIALIZE THE VARIABLES WHERE WE WILL SAVE THE INFO FOR ALBUM AND SONG
                String yearString;
                String albumAid;
                String albumFormat;
                String albumName;
                String albumCountry;
                String albumSinger;
                String albumISBN;
                String albumCompany;
                String albumReview;
                String review1;
                String review2;
                String songSid;
                String songLanguage;
                String songTitle;
                String songDuration;
                String songGenre;
                String songComposer;
                String songMuML;
                int year;
                // WE ENTER IN THIS FUNCTION WHEN WE FIND A TAG
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if(qName!=null){
                        switch(qName){
                            case "Music":
                                musicTag=true;
                                break;
                            case "Year":
                                yearTag=true;
                                break;
                            case "Album":
                                albumAid = attributes.getValue("aid");
                                albumFormat = attributes.getValue("format");
                                albumTag=true;
                                albumReviewTag=true; // THE REVIEW IS INSIDE <ALBUM></ALBUM>
                                songList=new ArrayList<song>(); // EACH ALBUM HAS ITS OWN songList SO WE MUST CREATE A NEW ONE EACH TIME WE FIND AN ALBUM TAG
                                break;
                            case "Name":
                                albumNameTag=true;
                                break;
                            case "Country":
                                albumCountryTag=true;
                                break;
                            case "Singer":  // THE PERFORMER COULD APPEAR IN THE SINGER TAG OR IN THE GROUP TAG
                            case "Group":
                                albumPerformerTag=true;
                                break;
                            case "ISBN":
                                albumISBNTag=true;
                                break;
                            case "Company":
                                albumCompanyTag=true;
                                break;
                            case "Song":
                                songSid = attributes.getValue("id");
                                songLanguage = attributes.getValue("lang");
                                songTag=true;
                                genreList=new ArrayList<String>(); // EACH SONG HAS ITS OWN genreList SO WE MUST CREATE A NEW ONE EACH TIME WE FIND A SONG TAG
                                break;
                            case "Title":
                                songTitleTag=true;
                                break;
                            case "Duration":
                                songDurationTag=true;
                                break;
                            case "Genre":
                                songGenreTag=true;
                                break;
                            case "Composer":
                                songComposerTag=true;
                                break;
                            case "MuML":
                                songMuMLTag=true;
                                break; 
                            default:
                                extraTextTag=true;    // WE USE IT WHEN WE FIND TEXT BUT IT IS NOT THE REVIEW                         
                        }

                    }
                    
                } //ENDS startElement

                public void characters(char ch[], int start, int length) throws SAXException {
                    if (yearTag) {
                        yearString= new String(ch, start, length);
                        year=Integer.parseInt(yearString);
                        if(((1980<year)&&(year<2021))){ // CHECK IF THE MUML IS CORRECT, THAT IS IF THE YEAR IS INSIDE THE INTERVAL 1980-2021
                            correctMuML=true;  
                        }
                        yearTag = false;
                    }
                    else if (albumNameTag) {
                        albumName= new String(ch, start, length);
                        albumNameTag = false;
                    }
                    else if (albumCountryTag) {
                        albumCountry= new String(ch, start, length);
                        albumCountryTag = false;
                    }
                    else if (albumPerformerTag) {
                        albumSinger= new String(ch, start, length);
                        albumPerformerTag = false;
                    }
                    else if (albumISBNTag) {
                        albumISBN= new String(ch, start, length);
                        albumISBNTag = false;
                    }
                    else if (albumCompanyTag) {
                        albumCompany= new String(ch, start, length);
                        albumCompanyTag = false;
                    }
                    else if (albumReviewTag) {  // REVIEW1
                        review1= new String(ch, start, length);
                        review1=review1.trim();
                        // THE REVIEW WILL ONLY APPEARS ONCE SO, IF REVIEW1 IS NOT EMPTY WE WILL SAVE IT IN THE VARIABLE THAT WE USE IN THE ALBUM CONSTRUCTOR
                        if(review1.length()>0){ 
                            albumReview=review1;
                        }
                        albumReviewTag = false;
                    }
                    else if(extraTextTag){  // NEED IT TO AVOID SAVING TEXT THAT IS NOT THE REVIEW
                        extraTextTag=false;
                    }
                    else if (songTitleTag) {
                        songTitle= new String(ch, start, length);
                        songTitleTag = false;
                    }
                    else if (songDurationTag) {
                        songDuration= new String(ch, start, length);
                        songDurationTag = false;
                    }
                    else if (songGenreTag) {
                        songGenre= new String(ch, start, length);
                        genreList.add(songGenre);
                        songGenreTag = false;
                    }
                    else if (songComposerTag) {
                        songComposer= new String(ch, start, length);
                        songComposerTag = false;
                    }
                    else if (songMuMLTag) {
                        songMuML= new String(ch, start, length);
                        songMuMLTag=false;
                    }   
                    else{   //REVIEW2
                        review2= new String(ch, start, length); 
                        review2=review2.trim();
                        // THE REVIEW WILL ONLY APPEARS ONCE SO, IF REVIEW2 IS NOT EMPTY WE WILL SAVE IT IN THE VARIABLE THAT WE USE IN THE ALBUM CONSTRUCTOR
                        if(review2.length()>0){ 
                            albumReview=review2;
                        }
                    }
                } //ENDS characters

                // WE ENTER IN THIS FUNCTION WHEN WE FIND A CLOSE TAG
                public void endElement(String uri, String localName, String qName) throws SAXException {    
                    if(correctMuML){ // IF THE MUML IS CORRECT WE WILL SAVE ITS INFO
                        switch(qName){
                            case "Country":
                                countriesList.add(albumCountry);
                                break;
                            case "Song":
                                if(genreList.contains("Pop")){ // WE ONLY WANT THE POP SONGS
                                    songGenre = genreList.toString().replace("[","").replace("]","");
                                    song song= new song(songSid, songLanguage, songTitle, songDuration, genreList, songGenre, songComposer);
                                    songList.add(song);
                                }              
                                break;

                            case "Album": // THE </album> APPEARS AFTER THE </song> SO, WHEN WE FIND </album> WE HAVE IN songList ALL THE SONGS OF THAT ALBUM
                                album album= new album(year, albumAid, albumFormat, albumName, albumCountry, albumSinger, albumISBN, albumCompany, albumReview, songList);
                                albumList.add(album);                            
                                break;                
                            
                            case "MuML":
                                mumls_to_analyze_list.add(songMuML); //SAVE THE MuML FOUND FOR ANALYZE THEM
                                break;
                        }
                    }else{  //THE MUML IS NOT CORRECT, YEAR OUTSIDE THE RIGHT INTERVAL
                        if(qName.equalsIgnoreCase("Music")){ // TO SAVE THE MUML IN THE ERROR ARRAYLISTS WHEN WE REACH THE END OF THE XML
                            errorsMUML.add(mumlString);
                            errorsLink.add(linkString);
                        }
                    }
                }   // ENDS endElement
            };  // ENDS HANDLER
            try{
                saxParser.parse(linkString,handler); 
            }catch(Exception e) {
                //ADD THE MUML TO THE LIST OF MUMLS WITH FATAL ERRORS
                fatalErrorsMUML.add(mumlString); 
                fatalErrorsLink.add(linkString);
            }
            mumls_to_analyze_list.remove(0); //REMOVE THE FIRST MUML IN THE "MUMLS TO ANALYZE LIST" BEACUSE WE HAVE ALREADY ANALYZED IT
        }
    }

    
}
