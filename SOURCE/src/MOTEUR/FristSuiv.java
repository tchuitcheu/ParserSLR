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
public class FristSuiv {
    
 private  ArrayList<Regle> ensRegle=null;
 public static  ArrayList<EnsFristSuiv> ens= new ArrayList();
 public static String vide="!";
 public static String caractereSpecial="$";
   private String terminaux;
    private String nonTerminaux;
   private String axiome;


   public static void resetEns(){
   ens=new ArrayList();
   }
    public FristSuiv(Grammaire gram){
    int i=0;
    ensRegle = new ArrayList();
    terminaux=gram.getTerminaux();
    nonTerminaux=gram.getNonTerminaux();

    axiome=gram.getAxiome()+"";
    axiome=nonTerminaux.charAt(1)+"";

    for(i=0;i<gram.getRegle().size();i++){
     ensRegle.add(new Regle(gram.getRegle().get(i).toString()));

     String tab[] =ensRegle.get(i).getDroite();
     for(int j=0;j<tab.length;j++){
     tab[j]=tab[j]+vide;
     }
     ensRegle.get(i).setDroite(tab);
     }
        for(int j=0;j<nonTerminaux.length();j++){
            EnsFristSuiv efs=new EnsFristSuiv();
            efs.nT=nonTerminaux.charAt(j)+"";
            ens.add(efs);
        }
 }


    public void setAxiome(String axiome) {
        this.axiome = axiome;
    }

    public String getAxiome() {
        return axiome;
    }
   
    public static void setEns(ArrayList<EnsFristSuiv> ens) {
        FristSuiv.ens = ens;
    }

    public void setEnsRegle(ArrayList<Regle> ensRegle) {
        this.ensRegle = ensRegle;
    }

    public void setNonTerminaux(String nonTerminaux) {
        this.nonTerminaux = nonTerminaux;
    }

    public void setTerminaux(String terminaux) {
        this.terminaux = terminaux;
    }

    public static void setVide(String vide) {
        FristSuiv.vide = vide;
    }

    public ArrayList<Regle> getEnsRegle() {
        return ensRegle;
    }

    public String getNonTerminaux() {
        return nonTerminaux;
    }

    public String getTerminaux() {
        return terminaux;
    }

    public static String getVide() {
        return vide;
    }


    public ArrayList<Regle> getRegle(){
         return this.ensRegle;
      }
    public static ArrayList<EnsFristSuiv> getEns(){
         return ens;
      }
    public void afficher(){
     int i=0;
     for(i=0;i<ensRegle.size();i++){
        ensRegle.get(i).afficher();
     }
    }
    public void calculFirst(){
        for(int i=0;i<ensRegle.size();i++){
                for(int j=0;j<ensRegle.get(i).getDroite().length;j++){
                    first(ensRegle.get(i),j,0);
                }
            }

    }
    public void first(Regle rg,int elt,int position){
        String dt=rg.getDroite()[elt];
        boolean bovide=false;
        if(testNonTenminaux(dt.charAt(position))){
             if((ens.get(getPosition(dt.charAt(position)+"")).ensPremiers.contains(vide))&& (ens.get(getPosition(rg.getGauche()+"")).ensPremiers.contains(vide))){
                     bovide=true;
                }
        for(int i=0;i<ens.get(getPosition(dt.charAt(position)+"")).ensPremiers.size();i++){

            if(!ens.get(getPosition(rg.getGauche()+"")).ensPremiers.contains(ens.get(getPosition(dt.charAt(position)+"")).ensPremiers.get(i))){
                ens.get(getPosition(rg.getGauche()+"")).ensPremiers.add(ens.get(getPosition(dt.charAt(position)+"")).ensPremiers.get(i));
               }
            if((ens.get(getPosition(dt.charAt(position)+"")).ensPremiers.get(i).contains(vide))){
                if(!bovide){
                    ens.get(getPosition(rg.getGauche()+"")).ensPremiers.remove(vide);
                }
                first(rg,elt,position+1);
            }
            }
        }else
        {
            if( !ens.get(getPosition(rg.getGauche()+"")).ensPremiers.contains(dt.charAt(position)+""))
            ens.get(getPosition(rg.getGauche()+"")).ensPremiers.add(dt.charAt(position)+"");
        }
    }
    public void premier(String nT,int p,boolean ver){
        ArrayList<String> tab=null;
        tab=new ArrayList();
        for(int i=0;i<ensRegle.size();i++){
            String reg=ensRegle.get(i).toString();
            if(reg.startsWith(nT)){
                 for(int j=0;j<ensRegle.get(i).getDroite().length;j++){
                 char d=ensRegle.get(i).getDroite()[j].charAt(p);
                 if(testNonTenminaux(d)){
                        if(!nT.equals(d+""))
                            premier(d+"",0,true);
                        int pos=getPosition(d+"");
                        EnsFristSuiv efs1=null;
                        if(pos!=-1){
                            efs1=ens.get(pos);
                            ArrayList<String> t1=efs1.ensPremiers;
                            if(t1.contains(vide)){
                                premier(nT,p+1,true);
                                efs1=ens.get(ens.size()-1);
                                ArrayList<String> t2=efs1.ensPremiers;
                                t1.remove(vide);
                                tab.addAll(t2);
                            }
                            tab.addAll(t1);
                        }
                   }else
                        {
                        if(!tab.contains(d+""))
                        tab.add(d+"");
                    }
            }
         }
     }
    //int index=ens.indexOf(nT);
    EnsFristSuiv efs=new EnsFristSuiv();
    ArrayList<String> tab1=null;
    tab1=new ArrayList();
    for(int j=0;j<tab.size();j++){
    if(!tab1.contains(tab.get(j)))
        tab1.add(tab.get(j));
    }
    tab=tab1;
    efs.nT=nT;
    efs.ensPremiers=tab;
    int pos=getPosition(nT);
    if(pos!=-1){
   for(int j=0;j<tab.size();j++){
    if(!ens.get(pos).ensPremiers.contains(tab.get(j)))
        ens.get(pos).ensPremiers.add(tab.get(j));
    }
    }
    else
    ens.add(efs);
    if(efs.ensPremiers.contains(vide) &&ver){
    premier(nT, p,false);
    }
    }
    public static int getPosition(String elt){
    int position=-1;
         for(int i=0;i<ens.size();i++){
         if(ens.get(i).nT.equals(elt)){
         position=i;
         break;
         }
         }
    return position;
    }
    public void afficherTableauChaine(ArrayList<String> ch){
        System.out.print("");
        for(int i=0; i< ch.size();i++)
            System.out.print(ch.get(i));
        System.out.println("");
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

      public void suivant(){
         // System.out.println();
        //*
        
        for(int k=0;k<ensRegle.size();k++){
        String tab[]=ensRegle.get(k).getDroite();
        for(int i=0;i<tab.length;i++){
            for(int j=0;j<tab[i].length();j++){
                char caractereCourant=tab[i].charAt(j);
                if(testNonTenminaux(caractereCourant)){
                  //  char caractereSuivant=tab[i].charAt(j+1);
                  //  if(testNonTenminaux(caractereSuivant)){
                        ArrayList<String> tab1=ajouterFrist(ensRegle.get(k).getGauche(), tab[i],j+1);
                        for(int ij=0;ij<tab1.size();ij++){
                            if(!ens.get(getPosition(caractereCourant+"")).ensSuivant.contains(tab1.get(ij))){
                                ens.get(getPosition(caractereCourant+"")).ensSuivant.add(tab1.get(ij));
                                miseAjourObservateurs(caractereCourant+"");
                            }
                }
                  //  }

                }
            }

        }
        }
        
        if(!ens.get(getPosition(axiome)).ensSuivant.contains(caractereSpecial)){
        ens.get(getPosition(axiome)).ensSuivant.add(caractereSpecial);
        miseAjourObservateurs(axiome);
        }

        // */

      }
      public ArrayList<String> ajouterFrist(char gauche,String droite,int postion){
            ArrayList<String> tab,tab1=null;
            tab=new ArrayList();
            char car=droite.charAt(postion);
            if(testNonTenminaux(car)){

                tab1=ens.get(getPosition(car+"")).ensPremiers;
                for(int i=0;i<tab1.size();i++){
                    if(!tab.contains(tab1.get(i))){
                        tab.add(tab1.get(i));
                        miseAjourObservateurs(droite.charAt(postion-1)+"");
                    }
                }
                if(tab1.contains(vide)){
                    tab.remove(vide);
                    tab.addAll(ajouterFrist(gauche,droite, postion+1));
                }
            }else
            {
                if(!(car+"").equals(vide)){
                if(!tab.contains(car)){
                tab.add(car+"");
                miseAjourObservateurs(droite.charAt(postion-1)+"");
                }
                }else{
                    if(!ens.get(getPosition(gauche+"")).observateur.contains(droite.charAt(postion-1)+""))
                    ens.get(getPosition(gauche+"")).observateur.add(droite.charAt(postion-1)+"");
                }
            }
            return tab;
      }
      public void miseAjourObservateurs(String e){
      ArrayList<String> tab=ens.get(getPosition(e)).observateur;
      boolean ajour=false;
      for(int i=0;i<tab.size();i++){
            for(int j=0;j<ens.get(getPosition(e)).ensSuivant.size();j++){
                String elt=ens.get(getPosition(e)).ensSuivant.get(j);
                if(!ens.get(getPosition(tab.get(i))).ensSuivant.contains(elt)){
                    ens.get(getPosition(tab.get(i))).ensSuivant.add(elt);
                    ajour=true;

            }
            }
           if(ajour)
           miseAjourObservateurs(tab.get(i));
      }

      }
    
}
