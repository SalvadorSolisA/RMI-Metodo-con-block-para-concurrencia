/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Ruth Mireya Ramírez Zúñiga
 * @author Salvador Solis Atenco
 */

public interface BancoInterfaz extends Remote{
    public String Retirar (int cantidad) throws RemoteException;
}
