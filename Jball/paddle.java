import java.applet.*;
import java.awt.*;

public class paddle extends Applet
{
final static int width = 50;
final static int height = 10;

boolean gotTheGun;

Image paddle, gun; 

int tx, ty;
int bx, by;
int x, y;
int[] bulletX;
int[] bulletY;
boolean[] bulletPresent;
int nextb;

	paddle(Image pd, Image g, int xpos, int ypos)
	{
		paddle = pd;
		gun = g;
		x = xpos;
		y = ypos;
		tx = xpos;
		ty = ypos;
		bx = xpos + width;
		by = ypos + height;
		gotTheGun = false;
		bulletX = new int[10];
		bulletY = new int[10];
		bulletPresent = new boolean[10];
		nextb = 0;
	}
	public void draw(Graphics g)
	{
	int place = 0;
		
			if (gotTheGun == true)
			{
				g.drawImage(gun, x, y, this);
				
					while (place < 10)
					{
						if (bulletPresent[place] == true)
						{
							g.setColor(Color.red);
							g.drawRect(bulletX[place], bulletY[place], 0, 20);						
						}
						place++;
					}
			}
			else
			{
				g.drawImage(paddle, x, y, this);			
			}
	}
	public void relocate(gui bounds, int pos)
	{
		if (pos < bounds.bx - width && pos > bounds.tx)
		{
			x = pos;
			tx = x;
			ty = y;
			bx = x + width;
			by = y + height;			
		}
	}
	public void handle(gui bounds)
	{
	int place = 0;
	int success = 0;
	
		if (gotTheGun == true)
		{
			while (place < 10)
			{
					if (bulletPresent[place] == true)
					{
						bulletY[place] -= 8;	
					}
					if (bulletY[place] < bounds.ty + 10 && bulletPresent[place] == true)
					{
							bulletPresent[place] = false;		
							bulletX[place] = 0;
							bulletY[place] = 0;
							nextb--;
					}
					if (bulletPresent[place] == false && nextb > 10 - 1)
					{
					 	if (nextb > 0)
						{
							nextb--;
						}
					}				
				place++;
			}
		}
		else
		{
			nextb = 0;
		}
		
		place = 0;
		
		while (place < 10)
		{
				if (bulletPresent[place] == false)
				{
					success++;
				}
			place++;
		}
		if (success == 10)
		{
			nextb = 0;
		}	
	}
	public void shoot()
	{	
		if (gotTheGun == true && nextb < 10 - 1)
		{					
			nextb++;
			bulletPresent[nextb] = true;
			bulletX[nextb] = tx + (width / 2);
			bulletY[nextb] = ty - 20 - 1;				
		}
	}
}