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
public class Item {
    private int numero=0;
    private char  lettre;
    private static  ArrayList<Item> collection=null;
    private ArrayList<Regle> listeRegle=null;
    private ArrayList<InfoGoTo> info=  new ArrayList();
    
    //constructeur 
    public Item(int num, char c, ArrayList<Regle> list){
        super();
        this.numero=num;
        this.lettre=c;
        this.listeRegle=list;
    }
    public Item(){
        super();
    }
    public Item( ArrayList<Regle> list){
        super();
        this.listeRegle=list;
    }
    public Item(Item item){
        super();
        this.numero=item.numero;
        this.lettre=item.lettre;
        this.listeRegle=item.listeRegle;
    }


    public  int getNumero(){
        return this.numero;
    }
    public char getLettre(){
        return this.lettre;
    }
    public boolean compareAvec(Item item){
        boolean bool=false; int i;
        if (this.getListeRegle().size()==item.getListeRegle().size()){
            for (i=0;i<this.getListeRegle().size();i++){
                if(!this.getListeRegle().get(i).compareAvecTous(item.getListeRegle())){
                    bool=false;
                    break;
                }else{
                    bool=true;
                }
            }
        }
        return bool;
        
    }
    public ArrayList<InfoGoTo> getInfoGoto(){
        return this.info;
    }
    public void afficherInfo(){
        int i;
        for (i=0;i<this.info.size();i++){
            System.out.println("+++++++");
            System.out.println("(I" +  this.info.get(i).getNum() + "," +this.info.get(i).getChar()+")" );
        }
    }
    public int  compareAvecTous(ArrayList<Item> item){
        boolean bool=false; int i;
        for (i=0;i<item.size();i++){
            if (this.compareAvec(item.get(i))){
                bool=true;
                break;
            }
        }
        if (!bool)
            i=-1;
        return i;
    }
    public ArrayList<Regle> getListeRegle(){

        return (ArrayList<Regle>) this.listeRegle.clone();
    }
    public void setNumero(int num ){
        this.numero=num;
    }
    public void setLettre(char car){
        this.lettre=car;
    }
    public void setListe(ArrayList<Regle> list){
        this.listeRegle=list;
    }
    public static ArrayList<Item> getcollection(){
        if (collection==null)
            collection = new ArrayList();
        return collection;
    }
      public static void resetCollection(){
        collection=null;
    }
    public String  listeNonTerminaux(){
            String chaine="";
            int i,j; 
            int resultat; 
            for (i=0;i<this.listeRegle.size();i++){
             for (j=0;i<this.listeRegle.get(i).getDroite().length;j++){
              resultat=this.listeRegle.get(i).getDroite()[j].indexOf(".");
               chaine = chaine  + this.listeRegle.get(i).getDroite()[j].charAt(resultat+1);
            }    
        }
            return chaine;
    }
    public void afficher(){
        int i;
        for (i=0;i<this.listeRegle.size();i++){
            //System.out.println("regle numero carlos " + i);
            listeRegle.get(i).afficher();
        }
    }
    public void setAddInfo(InfoGoTo info){
        this.info.add(info);
    }
    public boolean  fonctionGoto() {
        Item ajouter; 
        ArrayList<Item> resultat;
        resultat = new ArrayList();
        ArrayList<Regle> local,ensRegle,trouver;
        int i,res;
        String dejaAnaliser;
        local = new ArrayList();
        trouver = new ArrayList();
        char symCour;
        Regle tab[];
        tab=new Regle[this.getListeRegle().size()];
        for(int j=0;j<tab.length;j++){
            tab[j]=this.getListeRegle().get(j);
            Regle re=new Regle(tab[j].toString());
            local.add(re);
        }
       // local.addAll(this.getListeRegle());
       
        while(!local.isEmpty()){
            ensRegle = new ArrayList();
            symCour=local.get(0).recupererSymbol();
            dejaAnaliser="";
            String chaine;
            for (i=0;i<local.size();i++){
                chaine=local.get(i).toString();
                if((local.get(i).recupererSymbol()==symCour) && (chaine.indexOf(".")!=(chaine.length()-1))) {
                 
                   //Item.getcollection().get(0).afficher();
                    local.get(i).pointerRegle();
                      //Item.getcollection().get(0).afficher();
                    ensRegle.add(local.get(i));
                    trouver.add(local.get(i));
                    res=dejaAnaliser.indexOf(""+ensRegle.get(ensRegle.size()-1).recupererSymbol());
                    if (res ==-1 && !Grammaire.getInstance().testTenminaux(ensRegle.get(ensRegle.size()-1).recupererSymbol())){
                        dejaAnaliser=dejaAnaliser + ensRegle.get(ensRegle.size()-1).recupererSymbol();
                        ensRegle.addAll(ensRegle.get(ensRegle.size()-1).fermeture(""));
                    }
                } else if (chaine.indexOf(".")==(chaine.length()-1)){
                    trouver.add(local.get(i));
                }
            }
            local.removeAll(trouver);
            trouver = new ArrayList();
            ajouter = new Item(Item.getcollection().indexOf(this),symCour,ensRegle);
            
           if((ajouter.compareAvecTous(Item.getcollection())==-1) && (!ensRegle.isEmpty())){
                Item.getcollection().add(ajouter);
                InfoGoTo info = new InfoGoTo(symCour, Item.collection.size()-1);
                this.setAddInfo(info);
            }else if(ajouter.compareAvecTous(Item.getcollection())!=-1){
                InfoGoTo info = new InfoGoTo(symCour,ajouter.compareAvecTous(Item.getcollection()) );
                this.setAddInfo(info);
            }
               // System.out.println("----------------------------------------");
               //Item.getcollection().get(0).afficher();
                
                resultat.add(new Item(0,symCour,ensRegle));
            //}
            
        }
        return true;
    }
     
     
    
}
