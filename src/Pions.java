import java.awt.*;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Pions
{
	private Image pion;
	
	public Pions() 
	{
		//pion = Toolkit.getDefaultToolkit().getImage("Pieces/Pion.png");
		try {
			pion = ImageIO.read(getClass().getResource("Pion.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean deplacerPions(int posX, int posY, int nvX, int nvY)
	{
		if( (nvX+1==posX) && (nvY-1==posY || nvY+1==posY) ) return true;
		return false;
	}
	
	public Image getImg()
	{
		return pion;
	}
}