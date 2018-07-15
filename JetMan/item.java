import java.applet.*;
import java.awt.*;

/*
	regular = 0
	unique = 1
	fuel = 2
	laser = 3
*/

public class item extends Applet
{
Image toDraw;

boolean active;

int x, y;
int type;

	item()
	{
		x = 0;
		y = 0;
		type = -1;
		active = false;
	}
	item(Image graphic)
	{
		x = 0;
		y = 0;
		type = -1;
		toDraw = graphic;
		active = false;
	}
	public void setPosition(int objtype, int xpos, int ypos)
	{
		active = true;
		type = objtype;
		x = xpos + (int)(15 / 2);
		y = ypos + (int)(15 / 2);
	}
	public void handle(player pl, score sc)
	{
		if (type == 0 || type == 1)
		{	// unique or regular part
			if (x - pl.width + 4 < (pl.x + pl.width - 4) && x + pl.width - 4 > (pl.x + 4) && 
			    y - pl.height < pl.y + pl.height && y + pl.height > pl.y)
			{ // these stay the same for now but will be adjusted later for sprite sizes
				active = false;
				sc.score += 10;
				pl.items++;
			}
		}
		if (type == 2)
		{	// fuel
			if (x - pl.width + 4 < (pl.x + pl.width - 4) && x + pl.width - 4 > (pl.x + 4) && 
			    y < pl.y + pl.height && y > pl.y)
			{
				if (sc.fuel < 100)
				{
					active = false;
					sc.addtoFuel(25);
				}
			}		
		}
		if (type == 3)
		{	// ammo
			if (x - pl.width + 4 < (pl.x + pl.width - 4) && x + pl.width - 4 > (pl.x + 4) && 
			    y < pl.y + pl.height && y > pl.y)
			{
				active = false;
				sc.ammo += 5;
			}			
		}
	}
	public void draw(Graphics g)
	{
		g.drawImage(toDraw, x, y, this);
	}
}