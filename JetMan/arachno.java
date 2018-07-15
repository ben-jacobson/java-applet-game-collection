import java.applet.*;
import java.awt.*;

public class arachno extends enemy
{
Image arachnoL, arachnoR;

waypoint patrol;
int place;

	arachno(Image arachnoLe, Image arachnoRi)
	{	
		active = false;	 
		
		hp = 3;
		iHp = hp;
		arachnoL = arachnoLe;
		arachnoR = arachnoRi;
		enemy = arachnoL;
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
	{
	int shotPlace = 0;
	
		y = patrol.y[place];
					
				if (x > patrol.x[place])	
				{
					x--;
					enemy = arachnoL;							
				}
				if (x < patrol.x[place])	
				{
					x++;
					enemy = arachnoR;							
				}					
				if (x == patrol.x[place]) // dont ever change the motion speed
				{
					switch(place)
					{
						case 0:
							place = 1;
						break;
						case 1:
							place = 0;
						break;
					}
				}
				if (x < pl.x + pl.width - 4 && x + 25 > pl.x + 4 &&
				    y + 13 < pl.y + pl.height && y + 25 > pl.y)
				{
					pl.die(sc);
				}
				while (shotPlace < 20)
				{
						if (pl.shootMoving[shotPlace] == true)
						{
							if (pl.shootX[shotPlace] + pl.shootSpeed[shotPlace] < x + 25 && 
							    pl.shootX[shotPlace] + pl.shootSpeed[shotPlace] > x &&
							    pl.shootY[shotPlace] < y + 25 && pl.shootY[shotPlace] > y + 13)
							{
								hp--;
								pl.shootMoving[shotPlace] = false;
									
									if (hp < 1)
									{
										sc.score += 10;
										reset();
									}
								break;
							}
						}
					shotPlace++;
				}											
	}
}