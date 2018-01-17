package MOTEUR;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;

/**
 *
 * @author WILLY_CARLOS
 */
public class TableAnalyse {
    public ArrayList<String>[][] Action = new ArrayList[Item.getcollection().size()][Grammaire.getInstance().getTerminaux().length()];
    public Integer[][]  successeur = new Integer[Item.getcollection().size()][Grammaire.getInstance().getNonTerminaux().length()-1];
    private static TableAnalyse instance=null;
    public ArrayList<String>[][] getAction() {
        return Action;
    }

    public void setAction(ArrayList<String>[][] Action) {
        this.Action = Action;
    }

    public Integer[][] getSuccesseur() {
        return successeur;
    }

    public void setSuccesseur(Integer[][] successeur) {
        this.successeur = successeur;
    }
    
    public static TableAnalyse getInstance(){
        if(instance==null)
            instance = new TableAnalyse();
        return instance;
    }
     public static void ResetInstance(){
       instance=null;
    }
    public void construireTableAnalyse(){
        InfoGoTo info;  ArrayList<String> chaine; FristSuiv se=new FristSuiv(Grammaire.getInstance());
        int i,j,k; char SymCour; Regle Reg = new Regle();
        for (i=0;i<Item.getcollection().size();i++){
            for (j=0;j<Grammaire.getInstance().getTerminaux().length();j++)
                Action[i][j] = new ArrayList();

         }
        for (i=0;i<Item.getcollection().size();i++){
            for (j=0;j<Item.getcollection().get(i).getListeRegle().size();j++){
                SymCour=Item.getcollection().get(i).getListeRegle().get(j).recupererSymbol();
                Reg=Item.getcollection().get(i).getListeRegle().get(j).supprimerPoint();
                if(SymCour==':' && Item.getcollection().get(i).getListeRegle().get(j).getGauche()!=Grammaire.getInstance().getAxiome()){
                    chaine=FristSuiv.ens.get(FristSuiv.getPosition(Item.getcollection().get(i).getListeRegle().get(j).getGauche() +"")).ensSuivant;
                    for (k=0;k<chaine.size();k++){
                        if(!Action[i][Grammaire.getInstance().getTerminaux().indexOf("" + chaine.get(k))].contains("r"+Reg.getNumRegle())){
                        Action[i][Grammaire.getInstance().getTerminaux().indexOf("" + chaine.get(k))].add("r"+Reg.getNumRegle());
                    }
                    }
                }else if (SymCour==':' && Item.getcollection().get(i).getListeRegle().get(j).getGauche()==Grammaire.getInstance().getAxiome()){
                   //if(!Action[i][Grammaire.getInstance().getTerminaux().indexOf("$")].contains("acc"))
                    Action[i][Grammaire.getInstance().getTerminaux().indexOf("$")].add("acc");
                    System.out.println("------------------------------------------------");
                }
            }
            for (j=0;j<Item.getcollection().get(i).getInfoGoto().size();j++){
                info=Item.getcollection().get(i).getInfoGoto().get(j);
                if(Grammaire.getInstance().testTenminaux(info.getChar())){
                    if(!Action[i][Grammaire.getInstance().getTerminaux().indexOf("" + info.getChar())].contains("d" + info.getNum()))
                    Action[i][Grammaire.getInstance().getTerminaux().indexOf("" + info.getChar())].add("d" + info.getNum() );
                }else{
                    successeur[i][Grammaire.getInstance().getNonTerminaux().indexOf("" + info.getChar())-1]=info.getNum();
                }
            }   
        }
    }
    public void afficherTableAnaLyse(){
        int i,j,k;
        System.out.print("Etat \t \t \t Action  \t \t\t \tSuccesseur \n");
        for (i=0;i<Grammaire.getInstance().getTerminaux().length();i++)
            System.out.print("\t" + Grammaire.getInstance().getTerminaux().charAt(i));
        for (i=1;i<Grammaire.getInstance().getNonTerminaux().length();i++)
            System.out.print("\t" + Grammaire.getInstance().getNonTerminaux().charAt(i));
        System.out.println("");
        for (i=0;i<Item.getcollection().size();i++){
            System.out.print(i);
            for (j=0;j<Grammaire.getInstance().getTerminaux().length();j++)
              System.out.print("\t" + Action[i][j]);
            for (j=0;j<Grammaire.getInstance().getNonTerminaux().length()-1;j++)
                System.out.print("\t" + successeur[i][j]);
            System.out.println("");
        }
        
    }
}

