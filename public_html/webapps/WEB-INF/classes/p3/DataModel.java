package p3;

import java.io.*;
import java.util.*;
import java.net.*;
import java.lang.String;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.print.attribute.HashAttributeSet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.*;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;


public class DataModel extends HttpServlet{

    static ArrayList<String> errorsLink = new ArrayList<String>(); //LIST OF MUMLS WITH ERRORS FOR THE BROWSER MODE SRCEEN
    static ArrayList<String> fatalErrorsLink = new ArrayList<String>();  //LIST OF MUMLS WITH FATAL ERRORS FOR THE BROWSER MODE SRCEEN

    static Map <Integer,Document > correctMumls = new HashMap<Integer, Document>();

    //GETTERS FOR HAVING THE LISTS IN SINT175P2
    public static ArrayList<String> getErrorsLink(){
        return errorsLink;
    }
    public static ArrayList<String> getFatalErrorsLink(){
        return fatalErrorsLink;
    }

    static DocumentBuilderFactory dbf= null;
	static DocumentBuilder db = null;
  

    public static void errorChecker() throws SAXException, ParserConfigurationException, IOException, MalformedURLException, XPathExpressionException{ //METHOD FOR GETTING ALL THE MUMLS AND ANALIZE IF THEY ARE CORRECT OR IF THEY HAVE ERRORS OR FATALERRORS
 
        dbf = DocumentBuilderFactory.newInstance();
        db = dbf.newDocumentBuilder();

        String xpathTarget_muml ="//MuML";
        Document doc;

        //STRING WHERE I WILL SAVE muml20XX.xml, IT IS INITIALIZED TO THE FIRST MUML AVAILABLE
        String mumlString="muml2001.xml";
        
        //LIST WHERE I WILL SAVE ALL THE CORRERCT MUMLSTRINGS FOR ANALYZE THEM
        ArrayList<String>mumls_to_analyze_list=new ArrayList<String>();
        mumls_to_analyze_list.add(mumlString);
        

        //UNTIL WE DO NOT HAVE MORE MUMLSTRINGS TO ANALYZE
        while(mumls_to_analyze_list.size()!=0){ 
            mumlString=mumls_to_analyze_list.get(0);    //WE INITIALIZE THE VALUE OF THE mumlString TO THE FIRST VALUE IN THE LIST OF MUMLS TO ANALYZE, THAT IS THE ONE THAT WE WILL ANALYZE
            String linkString= "http://alberto.gil.webs.uvigo.es/SINT/22-23/"+mumlString+"";
            URL link= new URL(linkString); //CREATE THE LINK FROM THE LINKSTRING IN ORDER TO PARSE

            //CHECK IF FATAL ERROR, EXCEPTION WHEN TRYING TO PARSE
            try{
                doc = db.parse(link.openStream());
                //IF WE DID NOT ENTER IN THE CATCH IS BECAUSE WE DO NOT HAVE A FATALERROR SO WE WILL SAVE THE MUMLS
                //CHECK IF IT IS AN ERROR, YEAR MUST BE IN THE RANGE [1980-2021]
                NodeList nl = doc.getElementsByTagName("Year");
                Element elem = (Element)nl.item(0);
                String mumlYearString=elem.getTextContent(); 
                int mumlYear=Integer.parseInt(mumlYearString);
                if ((mumlYear<1980)||(mumlYear>2021)){
                    //ADD THE MUML TO THE LIST OF MUMLS EWITH ERRORS
                    errorsLink.add(linkString); 
                    mumls_to_analyze_list.remove(0);
                    continue;      
                }
                //CORRECT MUML
                else{ 
                    correctMumls.put(mumlYear, doc);
                    XPathFactory xpathFactory = XPathFactory.newInstance();
                    XPath xpath =xpathFactory.newXPath();
                    NodeList nl_muml = (NodeList)xpath.evaluate(xpathTarget_muml, doc, XPathConstants.NODESET);
                    for(int i=0; i<nl_muml.getLength();i++){    //CHECK THE LIST WITH ALL THE MUMLS OF THE LINK
                        Element elem_muml =(Element)nl_muml.item(i);
                        mumlString = elem_muml.getTextContent().trim(); //trim() REMOVES THE BLANKS AT THE EXTREMES
                        mumls_to_analyze_list.add(mumlString);  //SAVE IT IN THE LIST FOR ANALYZE  
                    }
                }
            }catch(Exception e) {
                //ADD THE MUML TO THE LIST OF MUMLS WITH FATAL ERRORS
                fatalErrorsLink.add(linkString);
            }
            mumls_to_analyze_list.remove(0); //REMOVE THE FIRST MUML IN THE MUMLS TO ANALYZE LIST BEACUSE WE HAVE ALREADY ANALYZED IT
        }
    }
 
    //METHOD TO CREATE AND FILL THE COUNTRIES LIST WITH ALL THE COUNTRIES THAT APPEAR IN THE CORRECT MUMLS
    public static ArrayList<String> getQ1Countries() throws SAXException, ParserConfigurationException, IOException, MalformedURLException{    
        
        ArrayList<String> countries = new ArrayList<String>(); //CREATE THE ARRAYLIST WHERE WE WILL SAVE THE FOUND COUNTRIES

        // GO THROUGH THE MAP TO GET DOCUMENT BY DOCUMENT(CORRECT MUML) AND FILL THE ARRAYLIST "countries"
        for (int key:correctMumls.keySet()){
            Document doc = correctMumls.get(key);    
        
            NodeList nl = doc.getElementsByTagName("Country");
            Element elem;
            String name;

            for (int i = 0; i < nl.getLength(); i++) {
                elem = (Element)nl.item(i);
                name = elem.getTextContent();
                countries.add(name);          
            }
            //REMOVE THE REPEATED COUNTRIES
            HashSet<String> hs = new HashSet<String>();
            hs.addAll(countries);
            countries.clear(); //REMOVES THE CONTENT OF THE ARRAYLIST
            countries.addAll(hs);
        }
        return countries;
    }

//METHOD TO CREATE AND FILL THE LIST OF ALBUMS WITH THE ALBUMS THAT APPEAR IN THE CORRECT MUMLS THAT SHARE A SPECIFIC COUNTRY VALUE RECIVED AS ARGUMENT
    public static ArrayList<album> getQ1Albums (String country) throws SAXException, ParserConfigurationException, IOException, MalformedURLException, XPathExpressionException{  

        ArrayList<album> listAlbums= new ArrayList<album>(); //CREATE THE ARRAYLIST WHERE WE WILL SAVE THE FOUND ALBUMS

        // GO THROUGH THE MAP TO GET DOCUMENT BY DOCUMENT(CORRECT MUML) TO FILL THE ARRAYLIST "listAlbums"
        for (int key:correctMumls.keySet()){
            Document doc = correctMumls.get(key);      

            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath =xpathFactory.newXPath();

            //ALBUM YEAR
            String xpathTarget_year ="/Music/Year";
            NodeList nl_albumYear = (NodeList)xpath.evaluate(xpathTarget_year, doc, XPathConstants.NODESET);
            Element elem_albumYear =(Element)nl_albumYear.item(0);
            String albumYearString = elem_albumYear.getTextContent().trim();
            int albumYear=Integer.parseInt(albumYearString);

            //ALBUM NAME, PERFORMER (SINGER OR GROUP), AID AND REVIEW
            String xpathTarget_albums ="/Music/Album[Country='"+country+"']";
            NodeList nl_album = (NodeList)xpath.evaluate(xpathTarget_albums, doc, XPathConstants.NODESET);
            for( int i=0; i<nl_album.getLength();i++){
                Element elem =(Element)nl_album.item(i);
                
                //NAME
                NodeList nl_albumName = elem.getElementsByTagName("Name");
                Element elem_albumName= (Element)nl_albumName.item(0);
                String albumName = elem_albumName.getTextContent().trim();
                
                //PERFORMER (SINGER OR GROUP)
                NodeList nl_albumSinger = elem.getElementsByTagName("Singer");
                Element elem_albumSinger = (Element)nl_albumSinger.item(0);
                String albumPerformer= new String();
                if (elem_albumSinger==null){ //IF THERE IS NO SINGER IT IS A GROUP
                    NodeList nl_albumGroup = elem.getElementsByTagName("Group");
                    Element elem_albumGroup= (Element)nl_albumGroup.item(0);
                    albumPerformer = elem_albumGroup.getTextContent().trim();
                }else{
                    albumPerformer = elem_albumSinger.getTextContent().trim();
                }

                //REVIEW
                String xpathTarget_review= "text()[normalize-space()]";
                String review =(String)xpath.evaluate(xpathTarget_review, elem, XPathConstants.STRING);
                String albumReview = review.trim();

                //AID (NEEDED FOR THE NEXT SCREEN)
                String albumAid = elem.getAttribute("aid").trim(); 

                //CREATE THE ALBUM AND ADD IT TO THE LIST OF ALBUMS
                album album= new album(albumName, albumYear, albumPerformer, albumReview, albumAid);
                listAlbums.add(album);
            }
        }
        //SORT THE LIST OF ALBUMS
        Collections.sort(listAlbums, new Comparator<album>(){
            public int compare(album a1, album a2){
                if (a1.getYear() < a2.getYear()) 
                    return -1;
                else if (a1.getYear()==a2.getYear())
                    return a1.getName().compareTo(a2.getName());
                else 
                    return 1;
            }    
        });
        return listAlbums;
    }


//METHOD TO CREATE AND FILL THE LIST OF SONGS WITH THE SONGS THAT APPEAR IN THE CORRECT MUMLS THAT SHARE A SPECIFIC COUNTRY AND ALBUM AID, BOTH PASSED BY ARGUMENTS
    public static ArrayList<song> getQ1Songs (String country, String album) throws SAXException, ParserConfigurationException, IOException, MalformedURLException, XPathExpressionException{

        ArrayList<song> listSongs= new ArrayList<song>(); //CREATE THE ARRAYLIST WHERE WE WILL SAVE THE FOUND SONGS

        // GO THROUGH THE MAP TO GET DOCUMENT BY DOCUMENT(CORRECT MUML) TO FILL THE ARRAYLIST "listSongs"       
        for (int key:correctMumls.keySet()){ 
            Document doc = correctMumls.get(key);  

            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath =xpathFactory.newXPath();
            
            String xpathTarget_songs ="/Music/Album[Country='"+country+"' and @aid='"+album+"']/Song";
            NodeList nl_song = (NodeList)xpath.evaluate(xpathTarget_songs, doc, XPathConstants.NODESET);

            for(int i=0; i<nl_song.getLength();i++){
                Element elem =(Element)nl_song.item(i);

                //TITTLE
                NodeList nl_songTitle = elem.getElementsByTagName("Title");
                Element elem_songTitle= (Element)nl_songTitle.item(0);
                String songTitle = elem_songTitle.getTextContent().trim();

                //LANGUAGE
                String songLanguage = elem.getAttribute("lang").trim(); 

                //COMPOSER
                NodeList nl_songComposer = elem.getElementsByTagName("Composer");
                Element elem_songComposer = (Element)nl_songComposer.item(0);
                String songComposer= elem_songComposer.getTextContent().trim();

                //GENRE
                NodeList nl_songGenre = elem.getElementsByTagName("Genre");
                ArrayList <String> genre_list = new ArrayList <String>();
                String songGenres= new String();
                for( int x=0; x<nl_songGenre.getLength();x++){
                    Element elem_songGenre = (Element)nl_songGenre.item(x);
                    String genre= elem_songGenre.getTextContent().trim();
                    genre_list.add(genre);            
                }

                if (genre_list.contains("Pop")){ //WE ONLY SAVE POP SONGS
                    //CREATE THE SONG AND ADD IT TO THE LIST OF SONGS
                    String songGenre = genre_list.toString().replace("[","").replace("]","");
                    song song= new song(songTitle, songLanguage, genre_list, songGenre, songComposer );
                    listSongs.add(song);
                }              
            }
        }
        //SORT THE LIST OF SONGS
        Collections.sort(listSongs, new Comparator<song>(){
            public int compare(song s1, song s2){
                if (s1.getGenre_list().size() < s2.getGenre_list().size()) 
                    return -1;
                else if (s1.getGenre_list().size()==s2.getGenre_list().size())
                    return s1.getTitle().compareTo(s2.getTitle());
                else 
                    return 1;
            }          
        });
        return listSongs;
    }
}
    


