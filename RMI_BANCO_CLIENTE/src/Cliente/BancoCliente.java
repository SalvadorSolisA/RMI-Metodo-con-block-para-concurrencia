/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import Interfaz.BancoInterfaz;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Ruth Mireya Ramírez Zúñiga
 * @author Salvador Solis Atenco
 */
public class BancoCliente implements Runnable{

    static String nombre = "Retiro";
    static String respuesta;
    static Registry registry; 
    static BancoInterfaz servicio;
    
    public static void main(String[] args) {
        
        BancoCliente transaccion = new BancoCliente();
        Thread hilo1 = new Thread(transaccion);
        Thread hilo2 = new Thread(transaccion);
        hilo1.setName("Juan");
        hilo2.setName("Rosa");
        hilo1.start();
        hilo2.start();
    }

    @Override
    public void run() {
        for (int x = 0; x < 10; x++) {
            int cantidad = 10 + x;
            System.out.println(Thread.currentThread().getName() + " quiere retirar $" + cantidad);
            try {
                registry = LocateRegistry.getRegistry();
                servicio = (BancoInterfaz) registry.lookup(nombre);
                
                String mensaje = servicio.Retirar(cantidad);
                System.err.println("ESTADO DEL RETIRO: " + Thread.currentThread().getName() + ", "+ mensaje);
                
                System.out.println(Thread.currentThread().getName() + " se va a dormir");
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(BancoCliente.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(BancoCliente.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotBoundException ex) {
                Logger.getLogger(BancoCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(Thread.currentThread().getName() + " se ha despertado");
        }
    }
}


