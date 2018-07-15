import java.applet.*;
import java.awt.*;


public class player extends Applet
{
static final int sStillLeft = 1;     // standing Still facing left
static final int sStillRight = 2;    // standing Still facing Right
static final int sFlyingCenterL = 3; // flying upwards facing Left
static final int sFlyingCenterR = 4; // flying upwards facing Right
static final int sFlyingLeft = 5;    // flying to the Left
static final int sFlyingRight = 6;   // flying to the Right
static final int sDeadLeft = 7;      // dead facing Left
static final int sDeadRight = 8;     // dead facing Right
static final int sWalkingLeft = 9;   // walking Left
static final int sWalkingRight = 10;  // walking Right
static final int sFallingLeft = 11;
static final int sFallingRight = 12;

Image stillLeft, stillRight, flyingCenterL, flyingCenterR, flyingLeft, flyingRight, deadLeft, deadRight, 
	 walkingLeft, walkingRight;
	 
Image teleport;

static final int width = 16;
static final int height = 25;
int x, y;
int bx, by;

int ammo;
int[] shootX;
int[] shootY;
int[] shootSpeed;
boolean[] shootMoving;

int gameState;
int initX, initY;

int teleportX, teleportY;
boolean teleporterActive;

int items;
boolean gotThere;

	player(Image istillLeft, Image istillRight, Image iflyingCenterL, Image iflyingCenterR,
	       Image iflyingLeft, Image iflyingRight, Image ideadLeft, Image ideadRight, Image iwalkingLeft, 
	       Image iwalkingRight, Image teleporter)
	{	
		teleport = teleporter;
		
		stillLeft = istillLeft; 
		stillRight = istillRight; 
		flyingCenterL = iflyingCenterL; 
		flyingCenterR = iflyingCenterR; 
		flyingLeft = iflyingLeft; 
		flyingRight = iflyingRight; 
		deadLeft = ideadLeft; 
		deadRight = ideadRight;	
		walkingLeft = iwalkingLeft;		
		walkingRight = iwalkingRight;
				
		shootX = new int[20];
		shootY = new int[20];	
		shootSpeed = new int[20];
		shootMoving = new boolean[20];
		
		gotThere = false;	
	}
	public void setPosition(int tx, int ty)
	{
		x = tx + 4;
		y = ty;
		bx = x + width;
		by = y + height;
		
		initX = x;
		initY = y;
		
		gameState = sFallingLeft;			
	}
	public void handle(level lvArray)
	{
	int xcheck = 0, ycheck = 0;
	int ygrid = 0;
	int place = 0;
	
		bx = x + width;
		by = y + height;
		
		 	while (xcheck < lvArray.levelSet.levelWidth && ycheck < lvArray.levelSet.levelHeight)
			{ // multi purpose collision detection loop
					
					if (gameState == sFallingLeft || gameState == sFallingRight) // if player is falling
					{ // fall till he hits a block
						bx = x + width;
						by = y + height;
											
							if (lvArray.levelArray[(int)(by / 25)][(int)((x + (width / 2)) / 25)] > 0 && 
							    lvArray.levelArray[(int)(by / 25)][(int)((bx - (width / 2)) / 25)] > 0)
							{ 
								ygrid = (int)(y / 25);
								y = ygrid * 25;
															
									switch (gameState)
									{
										case sFallingLeft:
											gameState = sStillLeft;
										break;
										case sFallingRight:
											gameState = sStillRight;
										break;
									}
								break;
							}
							else
							{
								y += 5;
								break;							
							}
					}	
			
				xcheck++;
				
					if (xcheck == lvArray.levelSet.levelWidth)
					{
						xcheck = 0;
						ycheck++;
					}
			}
			
			while (place < 20)
			{
				if (shootMoving[place] == true)
				{
					if (lvArray.levelArray[(int)(shootY[place] / 25)][(int)((shootX[place] + shootSpeed[place]) / 25)] != 0 ||
					    shootX[place] < 0 || shootX[place] > 500)
					{
						shootX[place] = 0;
						shootY[place] = 0;
						shootMoving[place] = false;
					}
					else
					{
						shootX[place] += shootSpeed[place];
					}
				}
				place++;
			}	
			if (items == lvArray.itemAmount)
			{
				if (lvArray.levelArray[(int)(initX / 25) + 1][(int)(initY / 25)] == 0)
				{
					teleportX = initX + 25;
				}
				else
				{
					if (lvArray.levelArray[(int)(initX / 25) - 1][(int)(initY / 25)] == 0)
					{
						teleportX = initX - 25;
					}
				}
				teleportY = initY;
				teleporterActive = true;	
				items = 0;			
			}	
			if (teleporterActive == true)
			{
				if (x < teleportX + 25 && x + 25 > teleportX &&
			         y < teleportY + 25 && y + 25 > teleportY)
				{
					gotThere = true;
					teleporterActive = false;									
				}
			}	
	}	
	public void draw(Graphics g)
	{
	int place = 0;
	
			switch(gameState)
			{ 
				case sStillLeft:
					g.drawImage(stillLeft, x - 4, y, this);		
				break;
				case sStillRight:
					g.drawImage(stillRight, x - 4, y, this);										
				break;				
				case sFlyingCenterL:
					g.drawImage(flyingCenterL, x - 4, y, this);										
				break;				
				case sFlyingCenterR:
					g.drawImage(flyingCenterR, x - 4, y, this);					
				break;				
				case sFlyingLeft:
					g.drawImage(flyingLeft, x - 4, y, this);					
				break;				
				case sFlyingRight:
					g.drawImage(flyingRight, x - 4, y, this);				
				break;				
				case sDeadLeft:
					g.drawImage(deadLeft, x - 4, y, this);					
				break;				
				case sDeadRight:
					g.drawImage(deadRight, x - 4, y, this);					
				break;				
				case sWalkingLeft:
					g.drawImage(walkingLeft, x - 4, y, this);					
				break;				
				case sWalkingRight:
					g.drawImage(walkingRight, x - 4, y, this);					
				break;				
				case sFallingLeft:
					g.drawImage(stillLeft, x - 4, y, this);					
				break;				
				case sFallingRight:					
					g.drawImage(stillRight, x - 4, y, this);					
				break;				
			}					
			while (place < 20)
			{
					if (shootMoving[place] == true)
					{
						g.setColor(Color.green);
						g.drawLine(shootX[place], shootY[place], shootX[place] + 10, shootY[place]);
					}
				place++;
			}
			if (teleporterActive == true)
			{
				g.drawImage(teleport, teleportX, teleportY, this);
			}
	}
	public void up(level lvArray, score sc)
	{
		if (sc.fuel > 0 && sc.died == false)
		{
				if (lvArray.levelArray[(int)((y - 2) / 25)][(int)(x + (width / 2)) / 25] == 0 &&
				    lvArray.levelArray[(int)((y - 2) / 25)][(int)((bx - (width / 2)) / 25)] == 0)
				{
						switch(gameState)
						{
							case sStillLeft:
								gameState = sFlyingCenterL;
							break;
							case sStillRight:
								gameState = sFlyingCenterR;							
							break;						
							case sWalkingLeft:
								gameState = sFlyingLeft;
							break;
							case sWalkingRight:
								gameState = sFlyingRight;							
							break;
							case sFallingLeft:
								gameState = sFlyingCenterL;							
							break;
							case sFallingRight:
								gameState = sFlyingCenterR;							
							break;
						}
					y -= 2;
				}					
			sc.fuel -= 0.1;
		}
		else
		{
			if (gameState != sDeadLeft && gameState != sDeadRight)
			{
				if (gameState % 2 == 1)
				{							
					gameState = sFallingLeft;			
				}
				else
				{					
					gameState = sFallingRight;		
				}
			}				
		}
	}
	public void left(level lvArray, score sc)
	{
		if (sc.died == false)
		{
			bx = x + width;
			by = y + height; 
			
				switch(gameState)
				{		
					case sStillLeft:
						gameState = sWalkingLeft;
					break;
					case sFlyingCenterL:				
						gameState = sFlyingLeft;
					break;
					case sStillRight:
						gameState = sWalkingLeft;
					break;				
					case sFlyingCenterR:
						gameState = sFlyingLeft;
					break;
					case sFallingRight:
						gameState = sFallingLeft;
					break;					
				}
				if (lvArray.levelArray[(int)(y / 25)][(int)(x + (width / 2) - 8 - 1) / 25] == 0 && 
				    lvArray.levelArray[(int)(y / 25)][(int)((bx - (width / 2) - 8 - 1) / 25)] == 0)
				{
					x -= 2;
				}
				if ((y / 25) + 1 < 20)
				{
					if (lvArray.levelArray[(int)(y / 25) + 1][(int)(x / 25)] == 0 && 
					    gameState == sWalkingLeft)
					{
						gameState = sFallingLeft;
					} 
				}
			}
	}
	public void right(level lvArray, score sc)
	{	
		if (sc.died == false)
		{	
			bx = x + width;
			by = y + height; 
				
				switch(gameState)
				{
					case sStillLeft:
						gameState = sWalkingRight;
					break;
					case sFlyingCenterL:				
						gameState = sFlyingRight;
					break;
					case sFallingLeft:
						gameState = sFallingRight;
					break;													
					case sStillRight:
						gameState = sWalkingRight;
					break;				
					case sFlyingCenterR:
						gameState = sFlyingRight;
					break;								
				}	
				if (lvArray.levelArray[(int)(y / 25)][(int)(x + (width / 2) + 8 + 1) / 25] == 0 && 
				    lvArray.levelArray[(int)(y / 25)][(int)((bx - (width / 2) + 8 + 1) / 25)] == 0)
				{
					x += 2;
				}
				if ((y / 25) + 1 < 20)
				{			
					if (lvArray.levelArray[(int)(y / 25) + 1][(int)(x / 25)] == 0 && 
					    gameState == sWalkingRight)
					{
						gameState = sFallingRight;
					}			
				}
			}				
	}
	public void shoot(score sc)
	{
	int place = 0;
		
			if (sc.ammo > 0)
			{		
				while (place < 20)
				{
						if (shootMoving[place] == false)
						{
								if (gameState % 2 == 1)
								{							
									shootSpeed[place] = -5;	
									shootX[place] = x - 5 + shootSpeed[place];								
								}
								else
								{					
									shootSpeed[place] = 5;
									shootX[place] = x + 5 + shootSpeed[place];									
								}					
						
							shootMoving[place] = true;
							shootY[place] = y + 10;
							sc.ammo--;
							break;
						}
					place++;
				}
			}
	}
	public void stopUp(score sc)
	{
		if (sc.died == false)
		{
			switch(gameState)
			{
				case sFlyingCenterL:
					gameState = sFallingLeft;
				break;
				case sFlyingCenterR:
					gameState = sFallingRight;				
				break;
				case sFlyingLeft:
					gameState = sFallingLeft;
				break;
				case sFlyingRight:
					gameState = sFallingRight;
				break;
				
			}
		}
	}
	public void stopLeft(score sc)
	{
		if (sc.died == false)
		{
			gameState = sFallingLeft;		
		}
	}	
	public void stopRight(score sc)
	{
		if (sc.died == false)
		{	
			gameState = sFallingRight;
		}		
	}
	public void die(score sc)
	{
		if (sc.died != true)
		{
				if (gameState % 2 == 1)
				{
					gameState = sDeadLeft;	
				}
				else
				{
					gameState = sDeadRight;
				}
			sc.died = true;
			sc.lifeDown();			
		}			
	}
	public void reset(score sc)
	{
		x = initX;
		y = initY;
		bx = x + width;
		by = y + height;
		sc.fuel = 25;
		sc.died = false;
		gameState = sFallingLeft;
	}
}