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
public class Grammaire {
    private String terminaux;
    private String nonTerminaux;
    private char axiome;
    private  ArrayList<Regle> ensRegle=null;
    private static Grammaire instance = null;
    
    public  static Grammaire getInstance(){
        if (instance==null)
            instance =  new Grammaire();
         return instance;
    }
    public  static  void ResetInstance(){
        instance=null;
    }
    public Grammaire() {
        super();
        this.ensRegle = new ArrayList();
    }
    public String getTerminaux(){
        return this.terminaux;
    } 
     public String getNonTerminaux(){
        return this.nonTerminaux;
    } 
     public char getAxiome(){
       return this.axiome;  
     }
     public void setAxiome(char s){
         this.axiome=s;
     }
     public void setTerminaux(String s){
         this.terminaux = s;
     }
      public void setNonTerminaux(String s){
         this.nonTerminaux = s;
     }
      public void setAddRegle(Regle R){
          this.ensRegle.add(R);
      }
      public boolean testNonTenminaux(char car){
          boolean b=false; int i;
          i = this.nonTerminaux.indexOf("" + car);
          if (i!=-1)
              b=true;
          return b;
      }
      public boolean testTenminaux(char car){
          boolean b=false; int i;
          i = this.terminaux.indexOf("" + car);
          if (i!=-1)
              b=true;
          return b;
      }
      public ArrayList<Regle> getRegle(){
         return this.ensRegle; 
      }
 
    
}
