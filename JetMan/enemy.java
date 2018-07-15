import java.applet.*;
import java.awt.*;

public class enemy extends Applet
{
Image enemy;
int x, y;
int iX, iY;
int hp; // how many shots to be taken down
int iHp;
boolean active;
	
	public void draw(Graphics g)
	{
		g.drawImage(enemy, x, y, this);
	}
	public void reset()
	{
		x = iX;
		y = iY;
		hp = iHp;
	}
}