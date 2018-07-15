import java.applet.*;
import java.awt.*;

public class powerups extends Applet
{
final static int maxPowers = 15;

int currentPower;

Image cannon, earth, speed, gold, life;

int[] powerUps;
int[] xpos; 
int[] ypos;


int diameter;

	powerups(Image c, Image e, Image s, Image g, Image l)
	{
		powerUps = new int[maxPowers];	
		xpos = new int[maxPowers];
		ypos = new int[maxPowers];	

		currentPower = 0;
			
		cannon = c;
		earth = e;
		speed = s;
		gold = g;
		life = l;
					
		diameter = 20;
	}
	public void addPower(int x, int y)
	{
		if (currentPower < maxPowers - 1)
		{
			currentPower++;
			powerUps[currentPower] = (int)((Math.random() * 5) + 1);
			xpos[currentPower] = x + (diameter / 2);
			ypos[currentPower] = y + (diameter / 2);
		}
		if (currentPower > 15 - 1)
		{
			currentPower = 0;
		}
	}	
	public void handle(gui bounds)
	{
	int whence = 0;
	int place = 0;
	
		if (currentPower < maxPowers)
		{
			while (place < maxPowers)
			{
				ypos[place] += 3;
				
					if (ypos[place] + diameter > bounds.by - 1) // didnt catch it, it fell to the ground
					{
						powerUps[place] = 0;
						xpos[place] = 0;
						ypos[place] = 0;						
						whence = place;
						
							if (whence > maxPowers - 1)
							{
									while (whence + 1 < maxPowers) // move it backwards
									{
										powerUps[whence] = powerUps[whence + 1];
										xpos[whence] = xpos[whence + 1];
										ypos[whence] = ypos[whence + 1];
										whence++;
									}
								currentPower--;
							}
					}
				place++;
			}
		}
	}
	public void draw(Graphics g)
	{
	int place = 0;
		
		while (place < maxPowers)
		{			
				switch(powerUps[place])
				{
					case 1:
						g.drawImage(cannon, xpos[place], ypos[place], this);
					break;
					case 2:
						g.drawImage(earth, xpos[place], ypos[place], this);					
					break;					
					case 3:
						g.drawImage(speed, xpos[place], ypos[place], this);					
					break;					
					case 4:
						g.drawImage(gold, xpos[place], ypos[place], this);					
					break;					
					case 5:
						g.drawImage(life, xpos[place], ypos[place], this);					
					break;						
				}
			place++;
		}
	}	
}