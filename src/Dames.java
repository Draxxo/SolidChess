import java.awt.*;

public class Dames
{
	private Image     dame;
	private VisuChess visu;
	
	public Dames(VisuChess visu) 
	{
		dame = Toolkit.getDefaultToolkit().getImage("Pieces/Dame.png");
		this.visu = visu;
	}
	
	public boolean deplacerDames(int posX, int posY, int nvX, int nvY)
	{
		if(!pieceEntre(posX, posY, nvX, nvY))
		{
			for(int i=0;i<4;i++)
			{
				if( (nvX-i == posX || nvX+i == posX) && (nvY+i == posY || nvY-i == posY) ) return true;
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
		if(nvX==posX || nvY==posY)
		{
			if(testTour(posX, posY, nvX, nvY) ) return true;
		}
		else
			if(testFou(posX, posY, nvX, nvY) ) return true;
		
		return false;
	}
	
	public boolean testTour(int posX, int posY, int nvX, int nvY)
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
	
	public boolean testFou(int posX, int posY, int nvX, int nvY)
	{
		int i=posX;
	
		while(nvX<posX?i>=0:i<=nvX)
		{
			if( (nvX-i == posX || nvX+i == posX) && (nvY+i == posY || nvY-i == posY) && ( (nvX-1 != posX || nvX+1 != posX) && (nvY+1 != posY || nvY-1 != posY) ) )
			{
				for(int j=0;j<4;j++)
				{
					if( (posX!=j && posY!=j) || (nvX!=i && nvY!=i) )
					{
						//diagonale vers le bas : OK
						if( nvX-j>posX && nvX-j!=nvX && nvX-j!=posX && nvX-j<4 && nvX-j>=0)
						{
							if( nvY+j<3 && nvY+j>0 )
								if(!visu.getTab(nvX-j, nvY+j).equals("0")) return true;
							
							if( nvY-j<3 && nvY-j>0 )
								if(!visu.getTab(nvX-j, nvY-j).equals("0")) return true;
							
						}
						else if( nvX+j<posX && nvX+j!=nvX && nvX+j!=posX && nvX+j<4 && nvX+j>=0) 
						{
							//diagonale vers le haut : OK
							if( nvY-j<3 && nvY-j>0 )
								if(!visu.getTab(nvX+j, nvY-j).equals("0")) return true;
							
							if( nvY+j<3 && nvY+j>0 )
								if(!visu.getTab(nvX+j, nvY+j).equals("0")) return true;
						}
					}
				}
			}
			i = (nvX<posX?i-1:i+1);
		}

		return false;
	}
	public Image getImg()
	{
		return dame;
	}
}