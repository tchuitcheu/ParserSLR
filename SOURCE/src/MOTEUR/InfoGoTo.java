/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MOTEUR;

/**
 *
 * @author WILLY_CARLOS
 */
public class InfoGoTo {
    private char carac;
    private int numero;
    
    //constructeur 
    InfoGoTo(char c, int num){
        carac=c;
        numero=num;
    }
    public char getChar(){
        return carac;
    }
    public int getNum(){
        return numero;
    }
    
}
