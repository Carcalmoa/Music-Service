package p3;

import java.io.*;
import java.util.*;
import java.net.*;
import jakarta.servlet.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.*;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;


public class beanPhase13{

    String password = new String();
    String country = new String();
    String aid = new String();
    ArrayList<song> songs = new ArrayList<song>();    

    // SETTERS
    public void setPassword(String passwd){
        password=passwd;
    }
    public void setCountry(String country){
        this.country=country;
    }
    public void setAid(String aid){
        this.aid=aid;
    }

    // GETTERS
    public String getPassword(){
        return password;
    }
    public String getCountry(){
        return country;
    }
    public String getAid(){
        return aid;
    }
    public ArrayList<song> getSongs() throws SAXException, ParserConfigurationException, IOException, MalformedURLException, XPathExpressionException{
        songs=DataModel.getQ1Songs(country,aid); ///ARRAYLIST WITH ALL THE POP SONGS OF THE SELECTED ALBUM  
        return songs;
    }   

}