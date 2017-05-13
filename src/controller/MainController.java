/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import classes.Employee;
import classes.AddEmployee;
import static java.lang.System.setSecurityManager;
import java.rmi.*;
import java.rmi.registry.*;

/**
 *
 * @author SD
 */
public class MainController {
    
    private static final String name = "sample";
    private static final String ip = "localhost";
    
    public static boolean addEmployee(Employee employee) {
        System.setProperty("java.security.policy", "file:./client.policy");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            Registry registry = LocateRegistry.getRegistry(ip, 1099);
            System.out.println("aw");
            AddEmployee comp = (AddEmployee) registry.lookup(name);
            
            comp.addEmployee(employee);
            return true;
        } catch (Exception e) {
            System.out.println("Server error");
            e.printStackTrace();
            return false;
        }
    }
    
    public static String login(String id, String password){
        System.setProperty("java.security.policy", "file:./client.policy");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            Registry registry = LocateRegistry.getRegistry(ip, 1099);
            AddEmployee comp = (AddEmployee) registry.lookup(name);
            
            return comp.login(id, password);
        } catch (Exception e) {
            System.out.println("Server error");
            e.printStackTrace();
            return "Server Error";
        }
    }
    
}
