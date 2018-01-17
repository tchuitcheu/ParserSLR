/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MOTEUR;

import java.util.ArrayList;

/**
 *
 * @author WILLY_CARLOS
 */
public class EnsFristSuiv {
    
    public  ArrayList<String> ensPremiers=null;
    public  ArrayList<String> ensSuivant=null;
    public  ArrayList<String> observateur=null;
    public String nT;
public ArrayList<String> getEns(){

return ensPremiers;
}
public EnsFristSuiv(){
ensPremiers=new ArrayList();
ensSuivant=new ArrayList();
observateur=new ArrayList();
}
public void setEns(ArrayList<String> en){
ensPremiers=en;
}
public String getNt(){

return nT;
}
public void setNt(String nt ){
nT=nt;
}
    
}
