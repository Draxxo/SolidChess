import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class IHMChess extends Frame implements ActionListener
{
	private ListeFichier listeFic;
	private VisuChess    visuChess;
	
	private Button    b;
	private Label     l;
	
	public IHMChess()
	{
		setTitle("Solitaire Chess");
		setSize(512,400);
		
		//les boutons pour choisir la difficulter et 
		//afficher les niveaux possible en conséquence
		this.initButton();
		
		
		//voir les niveaux disponible
		listeFic = new ListeFichier(this, "Debutant");
		add( listeFic, BorderLayout.WEST );
		
		
		//afficher le niveau à jouer
		visuChess = new VisuChess(this);
		add(visuChess);
		
		//
		l = new Label("Vous devez faire qu'il ne reste qu'une piece pour gagner");
		add(l, BorderLayout.SOUTH);
		
		//gérer la fermeture fenetre
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				System.exit(0);
			}
		} );

		setVisible(true); 
	}
	
	public void initButton()
	{
		Panel nord = new Panel(new GridLayout(1,4));
		for(int n=0;n<4;n++)
		{
			if(n==0) b = new Button("Debutant");
			if(n==1) b = new Button("Intermediaire");
			if(n==2) b = new Button("Avance");
			if(n==3) b = new Button("Expert");
			
			nord.add(b);
			b.addActionListener(this);
		}
		add(nord, BorderLayout.NORTH);
	}
	
	public void actionPerformed(ActionEvent e) 
	{	
		if(!this.getClick())
		{
			Button bout = (Button)e.getSource();
			// bout.setBackground(visuChess.getCouleur(bout.getLabel()));
			
			listeFic.modifierList(bout.getLabel());
		
			add(listeFic, BorderLayout.WEST);
		}
	}
	
	public boolean getClick()
	{
		return visuChess.getClick();
	}
	
	public void setLabel(String text)
	{
		l.setText(text);
	}
	
	public void setVisu(String nv)
	{
		visuChess.init(nv);
		visuChess.repaint();
	}

	public void setChangeNv()
	{
		visuChess.setChangeNv();
		l.setText("Vous devez faire qu'il ne reste qu'une piece pour gagner");
	}
	
	public void incrementNv()
	{
		listeFic.incrementNv();
	}
	
	public int getInNv()
	{
		return listeFic.getInNv();
	}
	
	public String getDifficulterAct()
	{
		return listeFic.getDifficulterAct();
	}
	
	public static void main(String [] arg) {
		new IHMChess();
	}
}