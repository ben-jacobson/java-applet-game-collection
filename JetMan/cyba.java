import java.applet.*;
import java.awt.*;

public class cyba extends enemy
{
Image cybaL, cybaR;

int shootX, shootY;
int shootSpeed;
boolean shot;

int direction;

waypoint patrol;
int place;

	cyba(Image cybaLe, Image cybaRi)
	{	
		active = false;	 
		
		hp = 5;
		iHp = hp;
		cybaL = cybaLe;
		cybaR = cybaRi;
		enemy = cybaL;
		
		shootX = 0;
		shootY = 0;
		shootSpeed = 0;
		shot = false;
		
		direction = 0;
	}
	public void setWaypoints(int x1, int y1, int x2, int y2)
	{
		active = true;		
		place = 0;
		x = x1;
		y = y1;	
		iX = x;
		iY = y;
		patrol = new waypoint(x1, y1, x2, y2);		
	}
	public void drawShot(Graphics g)
	{
		if (shot == true)
		{
			g.setColor(Color.red);
			g.drawLine(shootX, shootY, shootX + 10, shootY);
		}
	}	
	public void handle(player pl, level lv, score sc)
	{ // re-written version to support different speeds
	int shotPlace = 0;
	
		y = patrol.y[place];
					
			if (shot == false)
			{
				shot = true;
				
					if (pl.y + (25 / 2) < y + 25 && pl.y + (25 / 2) > y)
					{						
							if (direction == 1) // right
							{
								shootX = x + 11;
								shootY = y + 10;
								shootSpeed = 5;
							}
							if (direction == 0) // left
							{
								shootX = x - 1;
								shootY = y + 10;
								shootSpeed = -5;
							}
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
			if (place == 0) // heading towards left
			{
				enemy = cybaL;
				direction = 0;
				
					if (x > patrol.x[0])
					{
						x -= 2;
					}
					else
					{
						place = 1;
					}
			}
			if (place == 1) // heading towards right
			{
				enemy = cybaR;
				direction = 1;
				
					if (x < patrol.x[1])
					{
						x += 2;
					}	
					else
					{
						place = 0;
					}		
			}
			if (x + 4 < pl.x + pl.width - 4 && x + 25 - 4 > pl.x + 4 &&
			    y < pl.y + pl.height && y + 25 > pl.y)
			{
				pl.die(sc);			
			}
			if (shootX < pl.x + pl.width - 4 && shootX + 10 > pl.x + 4 &&
			    shootY < pl.y + pl.height && shootY > pl.y)
			{
				pl.die(sc);	
			}
			while (shotPlace < 20)
			{
					if (pl.shootMoving[shotPlace] == true)
					{
						if (pl.shootX[shotPlace] + pl.shootSpeed[shotPlace] < x + 25 && 
						    pl.shootX[shotPlace] + pl.shootSpeed[shotPlace] > x &&
						    pl.shootY[shotPlace] < y + 25 && pl.shootY[shotPlace] > y)
						{
							hp--;
							pl.shootMoving[shotPlace] = false;							
								
								if (hp < 1)
								{
									sc.score += 100;								
									reset();
								}
							break;
						}
					}
				shotPlace++;
			}													
	}
}