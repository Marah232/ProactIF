/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tp.ihm;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import modele.Personne;
import service.ServiceConciergerie;

/**
 *
 * @author User
 */
public class LoginAction implements Action {

    @Override
    public void excecute(HttpServletRequest request) {
        ServiceConciergerie service = new ServiceConciergerie();
        String mail = request.getParameter("login");
        String mdp = request.getParameter("password");
        Personne p = service.authentifierPersonne(mail, mdp);
        request.setAttribute("user", p);

    }

}
