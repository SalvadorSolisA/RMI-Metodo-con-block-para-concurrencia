/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import Interfaz.BancoInterfaz;
import Modelo.CuentaBancaria;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Ruth Mireya Ramírez Zúñiga
 * @author Salvador Solis Atenco
 */

public class BancoServidor extends UnicastRemoteObject implements BancoInterfaz {
    
    private CuentaBancaria cuenta;
    private static Lock bloquea = new ReentrantLock();
    public BancoServidor()  throws RemoteException {
    }

    @Override
    public String Retirar(int cantidad) throws RemoteException {
        return realizarRetiro(cantidad);
    }
    
    public static void main(String[] args) {
        try {
            String nombre = "Retiro";
            BancoInterfaz servicio;
            Registry registroRMI;
            
            registroRMI = LocateRegistry.createRegistry(1099);
            
            servicio = new BancoServidor();
            registroRMI.rebind (nombre, servicio);
            System.out.println("BancoServidor funcionando");
        } catch (Exception e) {
            System.err.println("BancoServidor excepción:");
            e.printStackTrace();
        }
    }
    
    public String realizarRetiro(int cantidad) {
        bloquea.lock();
        String mensaje = "";
        try{
            crearCuenta();
            if (cuenta.getBalance() >= cantidad) {
                if(cantidad == 13)//simula una caida del sistema
                    throw new Exception("SE CAYO EL SISTEMA");
                cuenta.retirar(cantidad);
                mensaje = " tu retiro de " + cantidad + " ha tenido éxito";
            } else {
                mensaje = "Lo sentimos, no hay dinero suficiente para retirar";
            }
        }
        catch(Exception ex){
            mensaje = ex.getMessage();
        }
        finally{
            bloquea.unlock();
        }
        
        return mensaje;
    }
    
    private CuentaBancaria crearCuenta(){
        if(cuenta != null){
            return cuenta;
        }else{
            cuenta = new CuentaBancaria();
            return cuenta;
        }
        
    }
}




/*VERSION 1 SERVIDOR CON SINCRONIZACION
public class BancoServidor extends UnicastRemoteObject implements BancoInterfaz {
    
    private CuentaBancaria cuenta;

    public BancoServidor()  throws RemoteException {
    }

    @Override
    public String Retirar(int cantidad) throws RemoteException {
        return realizarRetiro(cantidad);
    }
    
    public static void main(String[] args) {
        try {
            String nombre = "Retiro";
            BancoInterfaz servicio;
            Registry registroRMI;
            
            registroRMI = LocateRegistry.createRegistry(1099);
            
            servicio = new BancoServidor();
            registroRMI.rebind (nombre, servicio);
            System.out.println("BancoServidor funcionando");
        } catch (Exception e) {
            System.err.println("BancoServidor excepción:");
            e.printStackTrace();
        }
    }
    
    public synchronized String realizarRetiro(int cantidad) {
        crearCuenta();
        if (cuenta.getBalance() >= cantidad) {
            cuenta.retirar(cantidad);
            return " tu retiro de " + cantidad + " ha tenido éxito";
        } else {
            return "Lo sentimos, no hay dinero suficiente para retirar";
        }
    }
    
    private CuentaBancaria crearCuenta(){
        if(cuenta != null){
            return cuenta;
        }else{
            cuenta = new CuentaBancaria();
            return cuenta;
        }
        
    }
}
*/