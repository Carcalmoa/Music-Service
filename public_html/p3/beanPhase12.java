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


public class beanPhase12{

    ArrayList<album> albums = new ArrayList<album>();    
   
    String password = new String();
    String country = new String();
    String aid = new String();

    // SETTERS
    public void setPassword(String passwd){
        password=passwd;
    }
    public void setCountry(String country){
        this.country=country;
    }


    // GETTERS
    public String getPassword(){
        return password;
    }
    public String getCountry(){
        return country;
    }
    public ArrayList<album> getAlbums()  throws SAXException, ParserConfigurationException, IOException, MalformedURLException, XPathExpressionException{
        albums=DataModel.getQ1Albums(country);
        return albums;
    }


}