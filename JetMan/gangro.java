import java.applet.*;
import java.awt.*;

public class gangro extends enemy
{
Image gangroL, gangroR;
Image plasmaBlob;
int direction;

int shootX, shootY;
int shootSpeed;
boolean shot;

	gangro(Image gLeft, Image gRight, Image plasma)
	{
		active = false;	
		x = -1;
		y = -1;
		
		direction = 0;
		gangroL = gLeft;
		gangroR = gRight;
		plasmaBlob = plasma;
		enemy = gangroL;
		
		shootX = 0;
		shootY = 0;
		shootSpeed = 0;
		shot = false;
	}
	public void setPosition(int xpos, int ypos)
	{
		active = true;		
		x = xpos;
		y = ypos;
	}
	public void drawPlasma(Graphics g)
	{
		if (shot == true)
		{
			g.drawImage(plasmaBlob, shootX, shootY, this);
		}
	}
	public void handle(player pl, level lv, score sc)
	{	
		switch(direction)
		{
			case 0:
				enemy = gangroL;
			break;
			case 1:
				enemy = gangroR;
			break;
		}
		
		if (x + (25 / 2) < pl.x + (25 / 2))
		{
			direction = 1;
		}
		if (x + (25 / 2) > pl.x + (25 / 2))
		{
			direction = 0;
		}
		if (shot == false)
		{
			shot = true;
			
				if (direction == 1) // right
				{
					shootX = x + 20;
					shootY = y + 12;
					shootSpeed = 3;
				}
				if (direction == 0) // left
				{
					shootX = x - 5;
					shootY = y + 12;
					shootSpeed = -3;
				}
		}
		if (shot == true)
		{
			if (shootX > 0 && shootX < 500 && lv.levelArray[(int)(shootY / 25)][(int)((shootX + shootSpeed) / 25)] == 0)
			{
				shootX += shootSpeed;
			}
			else
			{
				shot = false;
				shootX = 0;
				shootY = 0;
			}
		}
		if (shootX < pl.x + pl.width - 4 && shootX + 10 > pl.x + 4 &&
		    shootY < pl.y + pl.height && shootY + 10 > pl.y)
		{
			pl.die(sc);			
		}			
	}
}