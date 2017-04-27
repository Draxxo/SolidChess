import java.awt.*;

public class CavaliersRoi
{
	private Image cavalierRoi;
	
	public CavaliersRoi() 
	{
		cavalierRoi = Toolkit.getDefaultToolkit().getImage("Pieces/CavalierRoi.png");
	}
	
	public boolean deplacerCavaliersRoi(int posX, int posY, int nvX, int nvY)
	{
		if( (nvX-1==posX || nvX+1==posX) && (nvY-2==posY || nvY+2==posY) ) return true;
		if( (nvY-1==posY || nvY+1==posY) && (nvX-2==posX || nvX+2==posX) ) return true;
		return false;
	}
	
	public Image getImg()
	{
		return cavalierRoi;
	}
}