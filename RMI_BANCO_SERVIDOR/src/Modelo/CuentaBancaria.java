/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 * @author Ruth Mireya Ramírez Zúñiga
 * @author Salvador Solis Atenco
 */
public class CuentaBancaria {
    private int balance = 100;
    
    public int getBalance() {
        return balance;
    }
    public void retirar (int cantidad) {
        balance = balance - cantidad;
    }
}
