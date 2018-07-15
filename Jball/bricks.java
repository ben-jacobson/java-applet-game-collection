import java.applet.*;
import java.awt.*;

public class bricks extends Applet
{
final static int blockWidth = 25;
final static int blockHeight = 15;

boolean[] powerSet;

Image blue, green, teal, red, purple, yellow; // the 6 brick images

levels levelSet;
int[][] level;

	bricks(Image b, Image g, Image t, Image r, Image p, Image y, score sc, ball ba, paddle pad)
	{	
		blue = b;
		green = g;
		teal = t;
		red = r;
		purple = p;
		yellow = y;
		
		powerSet = new boolean[5];
		
		level = new int[levelSet.levelWidth][levelSet.levelHeight];
		
		levelSet = new levels();
		nextLevel(sc, ba, pad);
	}
	public void nextLevel(score sc, ball b, paddle p)
	{
	int xpos = 0, ypos = 0;
	
		sc.nextLevel();
		setBoolsFalse(b, p);
		
			while (xpos < levelSet.levelWidth && ypos < levelSet.levelHeight)
			{
					switch(sc.level)
					{
						case 1:						
							level[xpos][ypos] = levelSet.levelOne[ypos][xpos]; // has to flip x and y's
						break;
						case 2:						
							level[xpos][ypos] = levelSet.levelTwo[ypos][xpos];
						break;
						case 3:						
							level[xpos][ypos] = levelSet.levelThree[ypos][xpos];
						break;
						case 4:						
							level[xpos][ypos] = levelSet.levelFour[ypos][xpos];
						break;	
						case 5:						
							level[xpos][ypos] = levelSet.levelFive[ypos][xpos];
						break;																													
					}
					
				xpos++;				
				
					if (xpos == levelSet.levelWidth)
					{
						xpos = 0;
						ypos++;
					}
			}
	}
	public void earthQuake(powerups power)
	{
	int taken = 0;
	int xpos = 0, ypos = 0;
	
		while (taken < 10)
		{
			xpos = (int)(Math.random() * levelSet.levelWidth);
			ypos = (int)(Math.random() * levelSet.levelHeight);
						
				if (level[xpos][ypos] > 0)
				{
					level[xpos][ypos] = 0;
					
						if (((int)(Math.random() * 10) + 1) == 10)
						{
							power.addPower((xpos * 25) + 10 + 3, (ypos * 15) + 10 + 30);
						}					
					taken++;
				}
				else
				{
					taken++;
				}				
		}
	}
	public void handle(ball b, score sc, powerups p, paddle pad)
	{
	int xpos = 0, ypos = 0;
	int x = 0, y = 0;
	int blanks = 0; 
	int place = 0, bplace = 0;
	
			while (xpos < levelSet.levelWidth && ypos < levelSet.levelHeight)
			{
				x = (xpos * 25) + 10 + 3;
				y = (ypos * 15) + 10 + 30;
				
				// bounce off y
					if (b.x + (b.diameter / 2) < x + blockWidth + 5 && b.x + (b.diameter / 2) > x - 5 &&			
					    b.y + (b.diameter / 2) < y + blockHeight + 5 && b.y + (b.diameter / 2) > y - 5 &&			
					    level[xpos][ypos] > 0)
					{	
							level[xpos][ypos] = 0;
							sc.addToScore(10);
							
								if (powerSet[3] == false) // if u dont have the gold ball
								{ // do the bounce thang										
										b.yspeed = -b.yspeed;
										b.y += b.yspeed;											
								}
							
								if (((int)(Math.random() * 10) + 1) == 10)
								{
									p.addPower(x, y);
								}
					}
													
				xpos++;
				
					if (xpos == levelSet.levelWidth)
					{
						xpos = 0;
						ypos++;
					}
			}
		
		xpos = 0;
		ypos = 0;
		
			while (xpos < levelSet.levelWidth && ypos < levelSet.levelHeight)
			{
				x = (xpos * 25) + 10 + 3;
				y = (ypos * 15) + 10 + 30;
							
					while (bplace < 10)
					{							
							if (pad.bulletX[bplace] < x + blockWidth + 2 && pad.bulletX[bplace] > x - 2 &&			
							    pad.bulletY[bplace] < y + blockHeight + 1 && level[xpos][ypos] > 0)
							{	
									pad.bulletX[bplace] = 0;
									pad.bulletY[bplace] = 0;
									pad.bulletPresent[bplace] = false;
									level[xpos][ypos] = 0;
									sc.addToScore(10);
																
										if (((int)(Math.random() * 10) + 1) == 10)
										{
											p.addPower(x, y);
										}
							}
							
						bplace++;								
					}
					
				bplace = 0;
				xpos++;
				
					if (xpos == levelSet.levelWidth)
					{
						xpos = 0;
						ypos++;
					}			
			}
				
		xpos = 0;
		ypos = 0;				
		
			while(blanks < (levelSet.levelWidth * levelSet.levelHeight))
			{
					if (level[xpos][ypos] == 0)
					{
						blanks++;
					}				
					else
					{
						break;
					}
				xpos++;
				
					if (xpos == levelSet.levelWidth)
					{
						xpos = 0;
						ypos++;
					}
			}
			if (blanks > (levelSet.levelWidth * levelSet.levelHeight) - 1)
			{
				nextLevel(sc, b, pad);
				b.stopBall();
			}
			while (place < p.maxPowers)
			{
					if (p.powerUps[place] > 0)
					{
						if (p.ypos[place] + p.diameter > pad.ty + 1 && p.ypos[place] < pad.by &&
						    p.xpos[place] + p.diameter < pad.bx + 2 && p.xpos[place] > pad.tx - 2)
						{
								switch(p.powerUps[place])
								{
									case 1: // gun
										powerSet[0] = true;									
										pad.gotTheGun = true;
										sc.addToScore(50);
									break;
									case 2: // earthquake
										powerSet[1] = true;
										earthQuake(p);
										powerSet[1] = false;
										sc.addToScore(50);										
									break;
									case 3: // speed
										powerSet[2] = true;	
										b.speedBall = true;
										sc.addToScore(50);																		
									break;
									case 4: // gold
										powerSet[3] = true;	
										b.goldBall = true;	
										sc.addToScore(50);																	
									break;
									case 5: // extra life
										powerSet[4] = true;
										sc.lifeUp();
										powerSet[4] = false;
										sc.addToScore(50);																		
									break;
								}
							p.powerUps[place] = 0;
							p.xpos[place] = 0;
							p.ypos[place] = 0;
						}			
					}
				place++;
			}
			if (b.setBoolsFalse == true)
			{
				setBoolsFalse(b, pad);
			}
	}
	public void draw(Graphics g)
	{
	int xpos = 0, ypos = 0;
	
		while (xpos < levelSet.levelWidth && ypos < levelSet.levelHeight)
		{
				switch(level[xpos][ypos])
				{
					case 1:
						g.drawImage(blue,(xpos * 25) + 10 + 3,(ypos * 15) + 10 + 30,this);
					break;
					case 2:
						g.drawImage(green,(xpos * 25) + 10 + 3,(ypos * 15) + 10 + 30,this);					
					break;
					case 3:
						g.drawImage(teal,(xpos * 25) + 10 + 3,(ypos * 15) + 10 + 30,this);					
					break;
					case 4:
						g.drawImage(red,(xpos * 25) + 10 + 3,(ypos * 15) + 10 + 30,this);					
					break;
					case 5:
						g.drawImage(purple,(xpos * 25) + 10 + 3,(ypos * 15) + 10 + 30,this);					
					break;
					case 6:
						g.drawImage(yellow,(xpos * 25) + 10 + 3,(ypos * 15) + 10 + 30,this);					
					break;					
				}	
					
			xpos++;
			
				if (xpos == levelSet.levelWidth)
				{
					xpos = 0;
					ypos++;
				}
		}	
	}
	public void setBoolsFalse(ball b, paddle p)
	{
	int place = 0;
	
			while (place < 5)
			{
				powerSet[place] = false;
				place++;
			}
		b.goldBall = false;
		b.speedBall = false;
		p.gotTheGun = false;
		b.setBoolsFalse = false;
	}
}