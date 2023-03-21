package p2;

import java.io.*;
import java.util.*;
import java.net.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.*;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

public class Sint175P2 extends HttpServlet{

    FrontEnd frontEnd = new FrontEnd();
    DataModel datamodel= new DataModel();


    public void init(){ //METHOD THAT CALLS THE errorChecker() TO GO THROUGH ALL MUMLs AND CHECK THEM
        try{
            datamodel.errorChecker();
        }
        catch(Exception e){
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{ //METHOD TO 
    
        String correct_password = "Practice02"; //MY PASSWORD

        String passwd = req.getParameter("p"); //GETS THE VALUE OF THE PASSWORD FROM THE LINK
        String phase = req.getParameter("pphase");  //GETS THE VALUE OF THE SCRREN PHASE FROM THE LINK
        String country = req.getParameter("pcountry");  //GETS THE VALUE OF THE ALBUM COUNTRY FROM THE LINK
        String aid = req.getParameter("paid"); //GETS THE VALUE OF ALBUM THE AID FROM THE LINK

        ArrayList<String> errorsMUML = new ArrayList<String>();
        ArrayList<String> errorsLink = new ArrayList<String>();
        ArrayList<String> fatalErrorsMUML = new ArrayList<String>();
        ArrayList<String> fatalErrorsLink = new ArrayList<String>();

        ArrayList<String> countries = new ArrayList<String>();
        ArrayList<album> list_albums = new ArrayList<album>();
        ArrayList<song> list_songs = new ArrayList<song>();
        
        try{
            PrintWriter out = res.getWriter();

            //EMPTY PASSWORD
            if (passwd==null){
                //out.println("if null password");
                if (req.getParameter("auto")!=null && req.getParameter("auto").equals("true")==true){ //auto=true AUTO MODE SCREEN
                    frontEnd.doGetEmptyPassword_auto(out);
                }else{                                        // BROWSER MODE SCREEN
                    frontEnd.doGetEmptyPassword_browser(out);
                }      
            }

            //INCORRECT PASSWORD
            else if (!passwd.equals(correct_password)){
                if (req.getParameter("auto")!=null && req.getParameter("auto").equals("true")){
                    frontEnd.doGetBadPassword_auto(out);
                }else{
                    frontEnd.doGetBadPassword_browser(out);
                }      
            }

            //PHASE NULL
            else if ((phase==null)&(passwd.equals(correct_password))){
                if (req.getParameter("auto")!=null && req.getParameter("auto").equals("true")){
                    frontEnd.doGetPhase01_auto(out);
                }else{
                    frontEnd.doGetPhase01_browser(out, passwd);
                }    
            }

            else{
                switch (phase) {
                    case "01":
                        if (req.getParameter("auto")!=null && req.getParameter("auto").equals("true")){
                            frontEnd.doGetPhase01_auto(out);
                        }else{
                            frontEnd.doGetPhase01_browser(out, passwd);
                        }              
                        break;
                    case "02": 
                        errorsMUML=datamodel.getErrorsMUML();   //ARRAYLIST WITH MUMLS WITH ERRORS FOR THE AUTO MODE SCREEN
                        errorsLink=datamodel.getErrorsLink();   //ARRAYLIST WITH LINKS OF THE MUMLS WITH ERRORS FOR THE BROWSER MODE SCREEN
                        fatalErrorsMUML=datamodel.getFatalErrorsMUML(); //ARRAYLIST WITH MUMLS WITH FATAL ERRORS FOR THE AUTO MODE SCREEN
                        fatalErrorsLink=datamodel.getFatalErrorsLink(); //ARRAYLIST WITH LINKS OF THE MUMLS WITH ERRORS FOR THE BROWSER MODE SCREEN
                        if (req.getParameter("auto")!=null && req.getParameter("auto").equals("true")){
                            frontEnd.doGetPhase02_auto(out, errorsMUML,fatalErrorsMUML);
                        }else{
                            frontEnd.doGetPhase02_browser(out, passwd, errorsLink, fatalErrorsLink);
                        }              
                        break;    
                    case "11":
                        countries=datamodel.getQ1Countries(); //ARRAYLIST WITH THE COUNTRIES THAT APPEAR IN THE CORRECT MUMLS
                        //ORDER THE COUNTRIES IN INVERSE ALPHABETIC ORDER
                        Collections.sort(countries);
                        Collections.reverse(countries);
    
                        if (req.getParameter("auto")!=null && req.getParameter("auto").equals("true")){
                            frontEnd.doGetPhase11_auto(out, countries);
                        }else{
                            frontEnd.doGetPhase11_browser(out, passwd, countries);
                        }              
                        break;
                    case "12":
                        if(country==null){  
                            if (req.getParameter("auto")!=null && req.getParameter("auto").equals("true")){
                                frontEnd.doGetNoCountry_auto(out);
                            }else{
                                frontEnd.doGetNoCountry_browser(out);
                            }   
                        }else{
                            list_albums=datamodel.getQ1Albums(country); //ARRAYLIST WITH ALL THE ALBUMS OF THE SELECTED COUNTRY
                            if (req.getParameter("auto")!=null && req.getParameter("auto").equals("true")){
                                frontEnd.doGetPhase12_auto(out, list_albums);
                            }else{
                                frontEnd.doGetPhase12_browser(out, passwd, list_albums, country);
                            }   
                        }           
                        break;
                    
                    case "13":
                        if(country==null){
                            if (req.getParameter("auto")!=null && req.getParameter("auto").equals("true")){
                                frontEnd.doGetNoCountry_auto(out);
                            }else{                    
                                frontEnd.doGetNoCountry_browser(out);
                            }
                            break; 
                        }if(aid==null){
                            if (req.getParameter("auto")!=null && req.getParameter("auto").equals("true")){
                                frontEnd.doGetNoAid_auto(out);
                            }else{
                                frontEnd.doGetNoAid_browser(out);
                            } 
                            break;
                        }else{
                            list_songs=datamodel.getQ1Songs(country,aid); ///ARRAYLIST WITH ALL THE POP SONGS OF THE SELECTED ALBUM                
                            if (req.getParameter("auto")!=null && req.getParameter("auto").equals("true")){
                                frontEnd.doGetPhase13_auto(out, list_songs);
                            }else{
                                frontEnd.doGetPhase13_browser(out, passwd, list_songs, country, aid);
                            }              
                            break;
                        }
                }
            }
                    
        }catch(Exception e){
            
        }
    }
}
