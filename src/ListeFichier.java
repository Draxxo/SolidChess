import java.awt.*;
import java.awt.event.*;
import java.io.*;

@SuppressWarnings("serial")
public class ListeFichier extends Panel implements ItemListener, FilenameFilter 
{
	private int nvDebloq = 57; 

	private List   l;
    private String choixAct;
	private IHMChess ihm;
	
    public ListeFichier(IHMChess ihm, String choix) 
	{ 
		setLayout(new GridLayout(1,1));
		
		this.ihm = ihm;
		
		l = new List();
		
		this.initList(choix);
	} 

	public boolean accept(File dir, String name) 
	{ 
		return (name.endsWith(".niv")); 
	} 
    
	public int getInNv()
	{
		return nvDebloq;
	}
	
	public void incrementNv()
	{
		nvDebloq++;
		l.removeAll();
		this.initList(choixAct);
	}
	
	public void initList(String choix)
	{
		this.choixAct = choix;
		
		File 		   f 	  = new File(getClass().getResource(choix + "/").getFile()); 
		FilenameFilter filter = this; 
		String[]       noms   = f.list(filter); 
		
		for (int i = 0; noms != null && i < noms.length; i++)
		{
			int n = Integer.parseInt(noms[i].charAt(noms[i].length()-6) + "" + noms[i].charAt(noms[i].length()-5));
			
			if(nvDebloq >= n)
			{
				noms[i] = noms[i].replaceAll( "_", " " );
				l.add(noms[i].substring(0,noms[i].length()-4));
			}
		}
		
		l.addItemListener(this);
		add(l);
	}
	
	public void modifierList(String choix)
	{	
		l.removeAll();
		
		this.initList(choix);
	}
	
	public String getDifficulterAct()
	{
		return this.choixAct;
	}
	
	public void itemStateChanged(ItemEvent e) 
	{
		if(!ihm.getClick())
		{
			this.ihm.setChangeNv();
			this.ihm.setVisu(l.getSelectedItem());
		}
	}
}