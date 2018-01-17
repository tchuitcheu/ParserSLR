package MOTEUR;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;


 	public class SorelusPanneau extends JPanel{
 			public   Image image =null;
                        String urle=null;
 			public SorelusPanneau(String url){
 			image =Toolkit.getDefaultToolkit().getImage(url);
 			urle=url;
                        }
 		   public void paintComponent(Graphics g){
 			 
 			  super.paintComponent(g);
 			  Dimension di =this.getSize();
 			  setPreferredSize(di);
                          //System.out.println(image);
 			  //SVISIOView.getInstance(null);
 			 g.drawImage(image,0,0,di.width,di.height,this);
 			  
 		   }
 	
 	
 	}