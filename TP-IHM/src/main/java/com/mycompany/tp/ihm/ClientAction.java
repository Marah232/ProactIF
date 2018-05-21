/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tp.ihm;

import javax.servlet.http.HttpServletRequest;
import modele.Client;
import service.ServiceConciergerie;

/**
 *
 * @author edelahaye
 */
public class ClientAction implements Action {

    @Override
    public void excecute(HttpServletRequest request) {
        request.setAttribute("interventions", new ServiceConciergerie().obtenirInterventionsParClient((Client) request.getAttribute("sessionUser")));
    }
}
