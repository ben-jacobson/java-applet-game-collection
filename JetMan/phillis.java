import java.applet.*;
import java.awt.*;

public class phillis extends enemy
{
Image phillisL, phillisR;

waypoint patrol;
int place;

	phillis(Image phillisLe, Image phillisRi)
	{
		active = false;		
				
		hp = 2;
		iHp = hp;
		phillisL = phillisLe;
		phillisR = phillisRi;
		enemy = phillisL;
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
	public void handle(player pl, score sc)
	{ // re-written version to support different speeds
	int shotPlace = 0;
	
		y = patrol.y[place];
			
			if (place == 0) // heading towards left
			{
				enemy = phillisL;
				
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
				enemy = phillisR;
				
					if (x < patrol.x[1])
					{
						x += 2;
					}	
					else
					{
						place = 0;
					}		
			}
			if (x - 5 < pl.x + pl.width - 4 && x + 20 > pl.x + 4 &&
			    y < pl.y + pl.height && y + 25 > pl.y)
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
									sc.score += 20;								
									reset();
								}
							break;
						}
					}
				shotPlace++;
			}										
	}
}