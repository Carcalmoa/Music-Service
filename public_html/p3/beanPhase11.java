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

public class beanPhase11{

    String password = new String();
    ArrayList <String> countries= new ArrayList<String>();


    // SETTERS
    public void setPassword(String passwd){
        password=passwd;
    }

    // GETTERS
    public String getPassword(){
        return password;
    }
    public ArrayList<String> getCountries() throws SAXException, ParserConfigurationException, IOException, MalformedURLException, XPathExpressionException{
        countries=DataModel.getQ1Countries();
        //SORT THE COUNTRIES IN INVERSE ALPHABETIC ORDER
        Collections.sort(countries);
        Collections.reverse(countries);
        return countries;
    }
  
}