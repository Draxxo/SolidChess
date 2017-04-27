import java.awt.*;

public class Tours
{
	private Image     tour;
	private VisuChess visu;
	
	public Tours(VisuChess visu) 
	{
		tour = Toolkit.getDefaultToolkit().getImage("Pieces/Tour.png");
		this.visu = visu;
	}
	
	public boolean deplacerTours(int posX, int posY, int nvX, int nvY)
	{
		if(!pieceEntre(posX, posY, nvX, nvY))
		{
			for(int i=0;i<4;i++)
			{
				for(int j=0;j<4;j++)
				{
					if(nvX==posX && (nvY-j == posY || nvY+j == posY)) return true;
					if(nvY==posY && (nvX-i == posX || nvX+i == posX)) return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean pieceEntre(int posX, int posY, int nvX, int nvY)
	{
		int i=posX;
		int j=posY;
		
		while(nvX<posX?i>=0:i<=nvX)
		{
			j=posY;
			
			while(nvY<posY?j>=0:j<=nvY)
			{
				if( (j==nvY && ( (i>posX && i<nvX) || (i>nvX && i<posX) )) || (i==nvX && ( (j>posY && j<nvY) || (j>nvY && j<posY) ))  )
				{
					if(!visu.getTab(i,j).equals("0"))
						return true;
				}
				j = (nvY<posY?j-1:j+1);
			}
			i = (nvX<posX?i-1:i+1);
		}
	
		return false;
	}
	
	public Image getImg()
	{
		return tour;
	}
}