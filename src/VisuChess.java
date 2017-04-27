import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

@SuppressWarnings("serial")
public class VisuChess extends Panel implements MouseListener
{
	private Color      couleur;
	private IHMChess   ihm;
	private String[][] tabStock;
	private int[]      tabCord  = new int[2];
	
	private boolean click = false;
	private boolean premierClick = true;
	private String  piece = "";
	private String  choixAct = "Niveau 01";
	
	private int cordX = 0;
	private int cordY = 0;
	
	private Pions        pion   = new Pions();
	private Fous         fou    = new Fous(this);
	private Tours        tour   = new Tours(this);
	private CavaliersRoi cheval = new CavaliersRoi();
	private Rois         roi    = new Rois();
	private Dames        dame   = new Dames(this);
	
	public VisuChess(IHMChess ihm)
	{	
		couleur = Color.WHITE;
		
		tabStock = new String[4][4];
		tabStock[0][0] = null;
		
		this.ihm = ihm;
		
		this.init("Niveau 01");
		
		addMouseMotionListener(new GereSourisBouge());
		addMouseListener(this);
	}
	
	public VisuChess() {}
	
	public void init(String nv)
	{	
		choixAct = nv;
		nv = nv.replaceAll( " ", "_" );
		nv = ihm.getDifficulterAct() + "/" + nv + ".niv";
		
		try
		{
			File file = new File(getClass().getResource(nv).getFile());
			Scanner sc = new Scanner(new FileReader( file ) );
			
			while ( sc.hasNext() )
			{
				for(int i=0;i<4;i++)
				{
					for(int j=0;j<4;j++)
						tabStock[i][j] = "" + sc.next();
				}
			}		
			sc.close();			         
		}
		catch (Exception e)
		{
			 e.printStackTrace();
		}
		
		this.actuelCouleur();
	}
	
	public void pieceChoisi(int x, int y) 
	{
		int posX = 0;
 		int posY = 0;
		int n = 4;
		
		for(int i=0;i<n;i++)
		{
			posX = 0;
		
			for(int j=0;j<n;j++)
			{
				if(posX<=x && posY<=y && (posX + getWidth()/n)>=x && (posY + getHeight()/n)>=y)
				{
					tabCord[0] = x;
					tabCord[1] = y;
					
					piece = tabStock[i][j];
					tabStock[i][j] = "0";
				}		
				posX += getWidth()/n;
			}
			posY += getHeight()/n;
		}
	}
	
	public void deplacerPiece(int[] tbCod, int x, int y)
	{
		String stock = "";
		
		int posX = 0;
 		int posY = 0;
		int n = 4;
		
		int[] tbIJ = new int[2];
		
		for(int i=0;i<n;i++)
		{
			posX = 0;
		
			for(int j=0;j<n;j++)
			{
				if(posX<=tbCod[0] && posY<=tbCod[1] && (posX + getWidth()/n)>=tbCod[0] && (posY + getHeight()/n)>=tbCod[1])
				{
					stock = piece;
					tbIJ[0] = i;
					tbIJ[1] = j;
				}		
				posX += getWidth()/n;
			}
			posY += getHeight()/n;
		}
		posY = 0;
		for(int i=0;i<n;i++)
		{
			posX = 0;
		
			for(int j=0;j<n;j++)
			{
				if(posX<=x && posY<=y && (posX + getWidth()/n)>=x && (posY + getHeight()/n)>=y)
				{
					if(this.possibleDeplace(piece, tbIJ, i, j) && !tabStock[i][j].equals("R"))
					{
						tabStock[i][j] = stock;
					}
					else
						tabStock[tbIJ[0]][tbIJ[1]] = stock;
				}
				posX += getWidth()/n;
			}
			posY += getHeight()/n;
		}
	}
	
	public boolean possibleDeplace(String piece, int[] tbCod, int x, int y)
	{
		     if(piece.equals("P")) return   pion.deplacerPions       (tbCod[0], tbCod[1], x, y);
		else if(piece.equals("F")) return    fou.deplacerFous        (tbCod[0], tbCod[1], x, y);
		else if(piece.equals("T")) return   tour.deplacerTours       (tbCod[0], tbCod[1], x, y);
		else if(piece.equals("C")) return cheval.deplacerCavaliersRoi(tbCod[0], tbCod[1], x, y);
		else if(piece.equals("R")) return    roi.deplacerRois        (tbCod[0], tbCod[1], x, y);
		else if(piece.equals("D")) return   dame.deplacerDames       (tbCod[0], tbCod[1], x, y);
		return false;
	}
	
	public void paint(Graphics g) 
	{	
		this.actuelCouleur();
		
		int posX = 0;
 		int posY = 0;
		int n = 4;
		
		for(int i=0;i<n;i++)
		{
			posX = 0;
		
			for(int j=0;j<n;j++)
			{
				g.setColor(Color.WHITE);
				
				if(i%2==0 && j%2!=0) g.setColor(couleur);
				if(i%2!=0 && j%2==0) g.setColor(couleur);
				
				g.fillRect(posX, posY, posX + getWidth()/n, posY + getHeight()/n);
				
				if(tabStock[0][0] != null)
				{
					int posXimg = posX + 15;
					int posYimg = posY + 10;
					int maxXimg =  getWidth()/n - 35;
					int maxYimg = getHeight()/n - 20;
					
					     if(tabStock[i][j].equals("P")) g.drawImage(  pion.getImg(), posXimg, posYimg, maxXimg, maxYimg, null);
					else if(tabStock[i][j].equals("F")) g.drawImage(   fou.getImg(), posXimg, posYimg, maxXimg, maxYimg, null);
					else if(tabStock[i][j].equals("T")) g.drawImage(  tour.getImg(), posXimg, posYimg, maxXimg, maxYimg, null);
					else if(tabStock[i][j].equals("C")) g.drawImage(cheval.getImg(), posXimg, posYimg, maxXimg, maxYimg, null);
					else if(tabStock[i][j].equals("R")) g.drawImage(   roi.getImg(), posXimg, posYimg, maxXimg, maxYimg, null);
					else if(tabStock[i][j].equals("D")) g.drawImage(  dame.getImg(), posXimg, posYimg, maxXimg, maxYimg, null);
				
					if(click)
					{
						if(piece.equals("P")) g.drawImage(   pion.getImg(), cordX, cordY, maxXimg, maxYimg, null);
						if(piece.equals("F")) g.drawImage(    fou.getImg(), cordX, cordY, maxXimg, maxYimg, null);
						if(piece.equals("T")) g.drawImage(   tour.getImg(), cordX, cordY, maxXimg, maxYimg, null);
						if(piece.equals("C")) g.drawImage( cheval.getImg(), cordX, cordY, maxXimg, maxYimg, null);
						if(piece.equals("R")) g.drawImage(    roi.getImg(), cordX, cordY, maxXimg, maxYimg, null);
						if(piece.equals("D")) g.drawImage(   dame.getImg(), cordX, cordY, maxXimg, maxYimg, null);
					}
				}
				
				posX += getWidth()/n;
			}
			posY += getHeight()/n;
		}
		
		if(this.estGagne() && !click)
		{
			ihm.setLabel("Vous avez gagne ce niveau, felicitation !");
			
			int nNv = Integer.parseInt(choixAct.charAt(choixAct.length()-2) + "" + choixAct.charAt(choixAct.length()-1));
			if(nNv>=ihm.getInNv())
				ihm.incrementNv();
		}
	}
	
	public boolean getClick()
	{
		return click;
	}
	
	public boolean estGagne()
	{
		int nbPiece = 0;
		for(int i=0;i<4;i++)
		{
			for(int j=0;j<4;j++)
			{
				if(!tabStock[i][j].equals("0")) nbPiece++;
			}
		}
		if(nbPiece==1) return true;
		return false;
	}
	
	public void actuelCouleur()
	{
		String dif = ihm.getDifficulterAct();
		
		if(dif.equals("Debutant")     ) couleur = new Color(174, 224, 167);
		if(dif.equals("Intermediaire")) couleur = new Color(198, 198, 255);
		if(dif.equals("Avance")       ) couleur = new Color(215, 174, 255);
		if(dif.equals("Expert")       ) couleur = new Color(232, 142, 130);
	}
	
	public Color getCouleur(String dif)
	{
		if(dif.equals("Debutant")     ) return new Color(174, 224, 167);
		if(dif.equals("Intermediaire")) return new Color(198, 198, 255);
		if(dif.equals("Avance")       ) return new Color(215, 174, 255);
		if(dif.equals("Expert")       ) return new Color(232, 142, 130);
		
		return null;
	}
	
	public String getTab(int i, int j)
	{
		return tabStock[i][j];	
	}
	
	public String getTabStockId(int x, int y)
	{
		int posX = 0;
 		int posY = 0;
		int n = 4;
		
		for(int i=0;i<n;i++)
		{
			posX = 0;
		
			for(int j=0;j<n;j++)
			{
				if(posX<=x && posY<=y && (posX + getWidth()/n)>=x && (posY + getHeight()/n)>=y)
				{
					return tabStock[i][j];
				}
				posX += getWidth()/n;
			}
			posY += getHeight()/n;
		}
	
		return null;
	}
	
	public void setChangeNv()
	{
		premierClick = true;
	}
	
	public void setCord(int x, int y)
	{
		cordX = x;
		cordY = y;
	}
	
	public void mouseClicked(MouseEvent e)  
	{ 
		if(!this.getTabStockId(e.getX(), e.getY()).equals("0"))
		{
			if(premierClick && !this.estGagne())
			{
				click = true;
				this.pieceChoisi(e.getX(), e.getY());
				premierClick = false;	
			}
			else
			{
				this.deplacerPiece(tabCord, e.getX(), e.getY());
				premierClick = true;
				repaint();
				click = false;
			}
		}
	}
	
	class GereSourisBouge extends MouseMotionAdapter 
	{

		public void mouseMoved(MouseEvent e) 
		{         
			if(click)
			{
				setCord(e.getX()-(getWidth()/4)/2, e.getY()-(getHeight()/4)/2);
				repaint();
			}
		}
        
	}
	
	public void mouseEntered(MouseEvent e)  {}
	public void mousePressed(MouseEvent e)  {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseExited(MouseEvent e)   {}
	
	
	//DoubleBuffering : évite le scintillement de l'image
    public void update(Graphics g)
    {
        Graphics offgc;
        Image offscreen = null;
        Dimension d = getSize();

        // create the offscreen buffer and associated Graphics
        offscreen = createImage(d.width, d.height);
        offgc = offscreen.getGraphics();
        // clear the exposed area
        offgc.setColor(getBackground());
        offgc.fillRect(0, 0, d.width, d.height);
        offgc.setColor(getForeground());
        // do normal redraw
        paint(offgc);
        // transfer offscreen to window
        g.drawImage(offscreen, 0, 0, this);
    }
}