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
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //System.out.println("bonjour monde carlos ");
         ArrayList<Regle> result ;
        int i;
        //result = new ArrayList();
     Grammaire gra = Grammaire.getInstance();   
     Regle   Reg = new Regle("Z-->.E");
     gra.setAddRegle(Reg);
     //Reg.pointerRegle();
     Reg.afficher();
     Regle   Reg1 = new Regle("E-->E+TunionT" );
     gra.setAddRegle(Reg1);
     //Reg1.pointerRegle();
     Reg1.afficher();
     Regle   Reg2 = new Regle("T-->T*FunionF" );
     gra.setAddRegle(Reg2);
     Reg2.afficher();
     Regle   Reg3 = new Regle("F-->(E)unioni" );
     gra.setAddRegle(Reg3);
     Reg3.afficher();
     gra.setAxiome('Z');
     gra.setNonTerminaux("ZETF");
     gra.setTerminaux("(+i)*$");
     result=Reg.fermeture("");
     Item it0 = new Item(result);
     Item.getcollection().add(it0 );
     i=0;
     while (i<Item.getcollection().size()){
           Item.getcollection().get(i).fonctionGoto();
           i++;
     }
     for (int j=0;j<Item.getcollection().get(i).getListeRegle().size();j++){
            Item.getcollection().get(i).getListeRegle().get(j).afficher();
        }
      
      //cette instruction copie la grammaire dans une nouvelle structure.
      FristSuiv se=new FristSuiv(gra);


      //cette boucle permet de calculer les premiers
      for(int jt=0;jt<gra.getTerminaux().length();jt++){
      se.calculFirst();
      }
      //ceci permet de les afficher
   
      
      //cette boucle permet de calculer les suivants
      for(int s=0;s<10;s++){
      se.suivant();
      }
 
      TableAnalyse.getInstance().construireTableAnalyse();
     

    }  
}
