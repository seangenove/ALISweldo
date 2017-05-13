package controller;

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;
import java.util.Collection;
import java.util.*;
import java.io.*;

// create a class that will give implementation to the interface above (make sure that the class will extend UnicastRemoteObject and implement the interface above)
public class Server extends UnicastRemoteObject implements AddEmployee {
    // required throwing of RemoteException due to the super class constructor

    static HashMap<String, Employee> hm;

    // HashMap <String, String> status = new HashMap<String, String>();
    public Server() throws RemoteException {

    }

    public static void loadHashMap() {
        try {
            File toRead = new File("employees");
            FileInputStream fis = new FileInputStream(toRead);
            ObjectInputStream ois = new ObjectInputStream(fis);

            HashMap<String, Employee> mapInFile = (HashMap<String, Employee>) ois.readObject();

            ois.close();
            fis.close();
            System.out.println("asdasdas");
        } catch (Exception e) {
            hm = new HashMap<String, Employee>();
        }
    }

    public static void saveHashMap(HashMap<String, Employee> map) {
        try {
            File fileOne = new File("employees");
            FileOutputStream fos = new FileOutputStream(fileOne);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(map);
            oos.flush();
            oos.close();
            fos.close();

        } catch (Exception e) {
            System.out.println("error saving");
            // HashMap <String, Employee> hm = new HashMap<String, Employee>();
        }
    }

    public boolean addEmployee(Employee employee) throws RemoteException {
        try {
            if (employee.getFullName().equals(" ")
                    || employee.getGender().equals(" ")
                    || hm.containsKey(employee.getFullName())) {
                return false;
            }
            hm.put(employee.getFullName(), employee);
            // status.put(p.getFullName(), Character.toString(p.getGender()));
            System.out.println(hm.get("Genove, Sean").getFullName());
            saveHashMap(hm);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) {
        try {
            loadHashMap();
            System.out.println("aw");
            // create instance of class with implementation
            AddEmployee stub = new Server();
            // create a registry class and start/create a running registry
            Registry reg = LocateRegistry.createRegistry(1099);
            // register the instance of class to the registry using a unique name
            reg.rebind("sample", stub);
            System.out.println("Sample RMI bound");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
