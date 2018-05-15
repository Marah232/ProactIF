package com.mycompany.tp.ihm;

import javax.servlet.http.HttpServletRequest;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
public interface  Action {
    public abstract void excecute(HttpServletRequest request);
    
    
}
