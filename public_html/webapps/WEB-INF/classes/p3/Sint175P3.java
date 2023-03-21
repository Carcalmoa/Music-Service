package p3;

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

public class Sint175P3 extends HttpServlet{

    // BEANS CLASS OBJECTS
    beanMissingParameter beanMissingParameter = new beanMissingParameter();
    beanPhase01 beanPhase01 = new beanPhase01();
    beanPhase02 beanPhase02 = new beanPhase02();
    beanPhase11 beanPhase11 = new beanPhase11();
    beanPhase12 beanPhase12 = new beanPhase12();
    beanPhase13 beanPhase13 = new beanPhase13();


    public void init(){ // METHOD THAT CALLS THE errorChecker() TO GO THROUGH ALL MUMLs AND CHECK THEM
        try{
            DataModel.errorChecker();
        }
        catch(Exception e){
        }
    }

    // METHOD TO OPEN THE SCREENS FOLLOWING THE INTRODUCED PARAMETERS IN THE LINK
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{ 
    
        String correct_password = "Practice03"; // MY PASSWORD
        String passwd = req.getParameter("p"); // GETS THE VALUE OF THE PASSWORD FROM THE LINK
        String phase = req.getParameter("pphase");  // GETS THE VALUE OF THE SCRREN PHASE FROM THE LINK
        String country = req.getParameter("pcountry");  // GETS THE VALUE OF THE ALBUM COUNTRY FROM THE LINK
        String aid = req.getParameter("paid"); // GETS THE VALUE OF ALBUM THE AID FROM THE LINK

        ServletContext sc = getServletContext();
        
        try{
            PrintWriter out = res.getWriter();

            //EMPTY PASSWORD
            if (passwd==null){   
                RequestDispatcher rd = sc.getRequestDispatcher("/emptyPassword.jsp");
                rd.forward(req,res);
            }

            //INCORRECT PASSWORD
            else if (!passwd.equals(correct_password)){
                RequestDispatcher rd = sc.getRequestDispatcher("/badPassword.jsp");
                rd.forward(req,res);
            }

            //PHASE NULL
            else if ((phase==null)&(passwd.equals(correct_password))){
                beanPhase01.setPassword(passwd); // SET THE VARIABLE "PASSWORD" OF beanPhase01 WITH THE VALUE OF p IN THE LINK
                req.setAttribute("bean01", beanPhase01); // THE OBJECT beanPhase01 WILL BE CALLED FROM phase01.jsp WITH THE NAME "bean01"
                RequestDispatcher rd = sc.getRequestDispatcher("/phase01.jsp");
                rd.forward(req,res);
            }

            else{
                switch (phase) {
                    case "01": 
                        beanPhase01.setPassword(passwd); // SET THE VARIABLE "PASSWORD" OF beanPhase01 WITH THE VALUE OF p IN THE LINK
                        req.setAttribute("bean01", beanPhase01); // THE OBJECT beanPhase01 WILL BE CALLED FROM phase01.jsp WITH THE NAME "bean01"
                        RequestDispatcher rd01 = sc.getRequestDispatcher("/phase01.jsp");
                        rd01.forward(req,res);
                        break;
                        
                    case "02":  
                        beanPhase02.setPassword(passwd); // SET THE VARIABLE "PASSWORD" OF beanPhase02 WITH THE VALUE OF p IN THE LINK
                        req.setAttribute("bean02", beanPhase02); // THE OBJECT beanPhase02 WILL BE CALLED FROM phase01.jsp WITH THE NAME "bean02"
                        RequestDispatcher rd02 = sc.getRequestDispatcher("/phase02.jsp");
                        rd02.forward(req,res);
                        break;   
                        
                    case "11":
                        beanPhase11.setPassword(passwd); // SET THE VARIABLE "PASSWORD" OF beanPhase11 WITH THE VALUE OF p IN THE LINK
                        req.setAttribute("bean11", beanPhase11); // THE OBJECT beanPhase11 WILL BE CALLED FROM phase01.jsp WITH THE NAME "bean11"
                        RequestDispatcher rd11 = sc.getRequestDispatcher("/phase11.jsp");
                        rd11.forward(req,res);
                        break;
                
                    case "12":
                        if(country==null){  
                            beanMissingParameter.setParameter("country"); // SET THE VARIABLE "PARAMETER" OF beanMissingParameter WITH THE MISSING PARAMETER: "COUNTRY"
                            req.setAttribute("beanMissing", beanMissingParameter); // THE OBJECT beanMissingParameter WILL BE CALLED FROM missingParameter.jsp WITH THE NAME "beanMissing"
                            RequestDispatcher rd = sc.getRequestDispatcher("/missingParameter.jsp");
                            rd.forward(req,res);
                        }else{
                            beanPhase12.setPassword(passwd); // SET THE VARIABLE "PASSWORD" OF beanPhase12 WITH THE VALUE OF p IN THE LINK
                            beanPhase12.setCountry(country); // SET THE VARIABLE "COUNTRY" OF beanPhase12 WITH THE VALUE OF pcountry IN THE LINK
                            req.setAttribute("bean12", beanPhase12); // THE OBJECT beanPhase12 WILL BE CALLED FROM phase01.jsp WITH THE NAME "bean12"
                            RequestDispatcher rd12 = sc.getRequestDispatcher("/phase12.jsp");
                            rd12.forward(req,res);
                        }           
                        break;
                    
                    case "13":
                        if(country==null){
                            beanMissingParameter.setParameter("country"); // SET THE VARIABLE "PARAMETER" OF beanMissingParameter WITH THE MISSING PARAMETER: "COUNTRY"
                            req.setAttribute("beanMissing", beanMissingParameter); // THE OBJECT beanMissingParameter WILL BE CALLED FROM missingParameter.jsp WITH THE NAME "beanMissing"
                            RequestDispatcher rd = sc.getRequestDispatcher("/missingParameter.jsp");
                            rd.forward(req,res);
                            break;
    
                        }if(aid==null){
                            beanMissingParameter.setParameter("aid"); // SET THE VARIABLE "PARAMETER" OF beanMissingParameter WITH THE MISSING PARAMETER: "AID"
                            req.setAttribute("beanMissing", beanMissingParameter); // THE OBJECT beanMissingParameter WILL BE CALLED FROM missingParameter.jsp WITH THE NAME "beanMissing"
                            RequestDispatcher rd = sc.getRequestDispatcher("/missingParameter.jsp");
                            rd.forward(req,res);
                            break;
    
                        }else{
                            beanPhase13.setPassword(passwd); // SET THE VARIABLE "PASSWORD" OF beanPhase13 WITH THE VALUE OF p IN THE LINK
                            beanPhase13.setCountry(country); // SET THE VARIABLE "COUNTRY" OF beanPhase13 WITH THE VALUE OF pcountry IN THE LINK
                            beanPhase13.setAid(aid); // SET THE VARIABLE "AID" OF beanPhase13 WITH THE VALUE OF paid IN THE LINK
                            req.setAttribute("bean13", beanPhase13); // THE OBJECT beanPhase13 WILL BE CALLED FROM phase01.jsp WITH THE NAME "bean13"
                            RequestDispatcher rd13 = sc.getRequestDispatcher("/phase13.jsp");
                            rd13.forward(req,res);
                            break;
                        }
                } //switch
            } //else 
        }catch(Exception e){
            
        }

    }
}
