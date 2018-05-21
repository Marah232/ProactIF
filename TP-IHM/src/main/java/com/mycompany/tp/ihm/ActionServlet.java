/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tp.ihm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dao.JpaUtil;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modele.Client;
import modele.Employe;
import service.*;
import modele.Personne;

/**
 *
 * @author User
 */
@WebServlet(name = "ActionServlet", urlPatterns = {"/ActionServlet"})
public class ActionServlet extends HttpServlet {
    
     @Override
    public void init() throws ServletException {
        
        JpaUtil.init();
        super.init(); //To change body of generated methods, choose Tools | Templates.
     
    }

    @Override
    public void destroy() {
        JpaUtil.destroy();
        super.destroy(); //To change body of generated methods, choose Tools | Templates.
        
    }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //init
        HttpSession session = request.getSession(true);
        String todo = request.getParameter("todo");
        //response.setContentType("json");

        if (todo.equals("login")) {
            
            LoginAction LA=new LoginAction();
            LA.excecute(request);
            if( request.getAttribute("user")!=null){
                session.setAttribute("user", request.getAttribute("user"));
            }
            Serializer S=new Serializer();
            S.serializerPersonne(request,response);
           
        }
        else if (todo.equals("register")){
            RegisterAction RA= new RegisterAction();
            RA.excecute(request);
            Serializer S=new Serializer();
            S.serializerInscription(request,response);
                
        }
        else{
            Personne sessionUser=(Personne) session.getAttribute("user");
            if(sessionUser==null){
                response.sendRedirect("/login.html");
            }
            else{
                
                switch(todo) {
                case "cloturer" :{
                    if (sessionUser instanceof Client)
                        response.sendRedirect("/login.html");
                    request.setAttribute("Employe",((Employe)sessionUser));
                    CloturerInterventionAction CIA=new CloturerInterventionAction();
                    
                    CIA.excecute(request);
                     Serializer S=new Serializer();
                     
                      S.serializerCloturerIntervention(request,response);
                      break;
                     
                }
                case "new" :{
                     request.setAttribute("sessionUser", sessionUser);
                    InterventionAction ia = new InterventionAction();
                    ia.excecute(request);
                    Serializer S = new Serializer();
                    S.serializerListString(request, response);
                   break;
                }
                case "histoClient" :{
                    request.setAttribute("sessionUser", sessionUser);
                    Serializer S=new Serializer();
                    //S.serializerInterventions(request,response);
                    break;
                }
                case "today" :{
                    if (sessionUser instanceof Client)
                        response.sendRedirect("/login.html");

         
                    
                    EmployeAction EA=new EmployeAction();
                    EA.excecute(request);
                    Serializer S=new Serializer();
                    S.serializerInterventions(request,response);
                    break;
                }
                case "today_Nom" :{
                    if (sessionUser instanceof Client)
                        response.sendRedirect("/login.html");

      
                    
                    
                    request.setAttribute("NomEmploye",((Employe)sessionUser).getNom() );
                    request.setAttribute("PrenomEmploye",((Employe)sessionUser).getPrenom() );
                    Serializer S=new Serializer();
                    S.serializerNomEmploye(request,response);
                    break;
                }
                case "client" :{
                    if (sessionUser instanceof Employe)
                        response.sendRedirect("/login.html");

                    request.setAttribute("NomEmploye",sessionUser.getNom());
                    request.setAttribute("PrenomEmploye",sessionUser.getPrenom());
                    Serializer S=new Serializer();
                    
                    S.serializerNomEmploye(request,response);
                    break;

                }
                case "employe" :{
                    if (sessionUser instanceof Client)
                       
                        response.sendRedirect("/login.html");
                    request.setAttribute("Intervention",((Employe)sessionUser).getInterventionEnCours());
                    request.setAttribute("Nom",((Employe)sessionUser).getNom());
                    request.setAttribute("Prenom",((Employe)sessionUser).getPrenom());
                    Serializer S=new Serializer();
                    S.serializerInterventionEnCoursEmploye(request,response);
                    break;
                    
                }
                
                    
            }   
            }
        }
      

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
