import java.awt.*;

public class Rois
{
	private Image roi;
	
	public Rois() 
	{
		roi = Toolkit.getDefaultToolkit().getImage("Pieces/Roi.png");
	}
	
	public boolean deplacerRois(int posX, int posY, int nvX, int nvY)
	{
		if( (nvX-1==posX || nvX+1==posX) && (nvY+1==posY || nvY-1==posY) ) return true;
		if(nvX==posX && (nvY-1 == posY || nvY+1 == posY)) return true;
		if(nvY==posY && (nvX-1 == posX || nvX+1 == posX)) return true;	
		
		return false;
	}
	
	public Image getImg()
	{
		return roi;
	}
}