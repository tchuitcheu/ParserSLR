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
public class Regle {
    public static boolean firt=true;
    //les attribut   gauche = non terminal  et droite regle de production 
    private char gauche;
    private String[]  droite;  
    //constructeur  
    public Regle(){
        super();
    }
    public static void resetFrist(){
    firt=true;
    }
    public Regle(String chaine){
        super();
        String[] resultat;
        resultat=chaine.split("-->");
        this.gauche=resultat[0].charAt(0);
        this.droite=resultat[1].split("union");
    }
    public Regle(Regle R){
       super();
       this.gauche=R.gauche;
       this.droite=R.droite;
    }
    public char getGauche(){
        return this.gauche;
    }
    public String[] getDroite(){
        return this.droite;
    }
    public void setGauche(char c){
        this.gauche=c;
    }
    public void setDroite(String[] d){
        this.droite=d;
    }
    public boolean getPositionPoint(){
       boolean bool=false; int resultat;
          resultat =  this.droite[0].indexOf(".");
          if(resultat==this.droite[0].length()-1)
             bool=true;
          return bool;
    }
    public char recupererSymbol(){
       char carac; int resultat;
         carac =':';
          resultat =  this.droite[0].indexOf(".");
          if(resultat!=this.droite[0].length()-1){
              carac=this.droite[0].charAt(resultat +1);
          }
          return carac;
    }
    public String recupererTousSymbol(){
        String chaine=""; int i;
        for (i=0;i<this.droite.length;i++){
            chaine=chaine + (new Regle(this.gauche + "-->" + this.droite[i])).recupererSymbol();
        }
        return chaine;
    }
    public boolean compareAvec(Regle reg){
        return this.toString().equals(reg.toString());
    }
    public boolean compareAvecTous(ArrayList<Regle> reg){
        boolean bool=false; int i;
        for (i=0;i<reg.size();i++){
            if (this.compareAvec(reg.get(i))){
                bool=true;
                break;
            }
        }
        return bool;
    }
    public void pointerRegle(){
       int i,j; int resultat; char[] carac;
       for (i=0;i<this.droite.length;i++){
          resultat =  this.droite[i].indexOf(".");
          if (resultat==-1){
              if(this.droite[i].length()==1 && this.droite[i].charAt(0)=='!' ){
                  this.droite[i]=".";
                  continue;
              }
              this.droite[i] = ".".concat(this.droite[i]);  
          }else if(resultat!=this.droite[i].length()-1){
              carac = this.droite[i].toCharArray();
              this.droite[i]="";
              carac[resultat]=carac[resultat+1];
              carac[resultat+1]='.';
              for (j=0;j<carac.length;j++)
              this.droite[i] =this.droite[i].concat(""+ carac[j]);
          }
       }
    }
    public boolean testVide(){
         return this.droite[0].charAt(0)=='!';
    }
    public void afficher(){
        int i;
        System.out.print(this.getGauche() + "--> ");
        for (i=0;i<this.getDroite().length;i++){
            System.out.print(this.getDroite()[i]);
            if (i!=this.getDroite().length-1)
                System.out.print("|");
        }
        System.out.println("");
    }
    @Override
    public String toString(){
    String ret="";
    ret=this.getGauche()+"";
    ret=ret+"-->";
    int i;
      for (i=0;i<this.getDroite().length;i++){
            ret=ret+this.getDroite()[i];
            if (i!=this.getDroite().length-1)
                ret=ret+"union";
        }  
     return ret;
    }
     public ArrayList<Regle> fermeture(String DejaAnaliser){
         
        ArrayList<Regle> result;
        char Symcour; 
        int res,i,j,nbreElement;
        result = new ArrayList();
        Regle Reg ;
        if (result.contains(this)){
           result.add(this); 
        }
        Symcour=this.recupererSymbol(); 
        res=DejaAnaliser.indexOf(""+  Symcour);
        if(res ==-1 && Grammaire.getInstance().testNonTenminaux(Symcour)){
         DejaAnaliser=DejaAnaliser.concat("" + Symcour);
           for (i=0;i<Grammaire.getInstance().getRegle().size();i++){
               if(Grammaire.getInstance().getRegle().get(i).getGauche()==Symcour){
                   for (j=0;j<Grammaire.getInstance().getRegle().get(i).getDroite().length;j++){
                        Reg = new Regle(Grammaire.getInstance().getRegle().get(i).getGauche() + "-->"+ Grammaire.getInstance().getRegle().get(i).getDroite()[j] );
                        Reg.pointerRegle();
                        if (!result.contains(Reg))
                        result.add(Reg);
                   }
                   break;
               }   
           }
           nbreElement = result.size();
           for (i=0;i<nbreElement;i++){
                     Reg = new Regle(result.get(i).getGauche()+ "-->" + result.get(i).getDroite()[0] ); 
                     result.addAll(Reg.fermeture(DejaAnaliser));
            }   
            
        }  
        
        if(this.getGauche()==Grammaire.getInstance().getAxiome() && firt){
            result.add(0, this);
            firt=false;
        }
        return result; 
    }
    public Regle supprimerPoint(){
        String chaine=this.toString();int i;
        chaine=(new Regle(chaine)).getDroite()[0];
        for (i=0;i<chaine.length();i++){
            if(chaine.charAt(i)=='.'){
               if(i==0)
                   chaine = chaine.substring(1, chaine.length());
               else if (i==chaine.length()-1)
                   chaine= chaine.substring(0, chaine.length()-1);
               else
                   chaine=chaine.substring(0, i) + chaine.substring(i+1, chaine.length()); 
               break;
            }
        }
        if (chaine.isEmpty()) chaine=FristSuiv.vide;
        return (new Regle(this.gauche + "-->" + chaine));
    }
     public int getNumRegle(){
          int res=-1,i,j;
          for (i=0;i<Grammaire.getInstance().getRegle().size();i++){
              for (j=0;j<Grammaire.getInstance().getRegle().get(i).getDroite().length;j++){
                  res++;
                  if(this.getDroite()[0].equals(Grammaire.getInstance().getRegle().get(i).getDroite()[j]) && this.getGauche()==Grammaire.getInstance().getRegle().get(i).getGauche()){
                      return res;
                      
                  }   
              }
          }
          return res;
      }

      public static Regle getRegleNum(int pos){
          Regle rg=null;
          int res=-1,i,j;
          for (i=0;i<Grammaire.getInstance().getRegle().size();i++){
              for (j=0;j<Grammaire.getInstance().getRegle().get(i).getDroite().length;j++){
                  res++;
                  if(res==pos){
                  String dr=Grammaire.getInstance().getRegle().get(i).getDroite()[j];
                  String gc=Grammaire.getInstance().getRegle().get(i).getGauche()+"";
                  gc+="-->"+dr;
                  rg=new Regle(gc);
                  break;
                  }
                   if(rg!=null)
                       break;
                  }

          }
          return rg;
      }
}