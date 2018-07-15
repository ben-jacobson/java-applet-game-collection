import java.applet.*;
import java.awt.*;

public class level extends Applet
{
static final int blockWidth = 25, blockHeight = 25;
levels levelSet;

Image block1, block2, block3, block4;
int[][] levelArray;

Image fuel, ammo;

int playerX, playerY;
int writingPatrolX, writingPatrolY;

int arachnoPlace, wingherdPlace, phillisPlace, gangroPlace, cybaPlace;
int itemPlace, inventPlace;

int itemAmount;

	level(Image image1, Image image2, Image image3, Image image4, Image ifuel, Image iammo)
	{
		block1 = image1;
		block2 = image2;
		block3 = image3;
		block4 = image4;
		levelArray = new int[levelSet.levelHeight][levelSet.levelWidth];
		
		levelSet = new levels();
		
		playerX = 1; // this means initial playerX position is 1, then it is changed
		playerY = 1;
		
		writingPatrolX = 0;
		writingPatrolY = 0;	
		
		arachnoPlace = 0;
		wingherdPlace = 0;
		phillisPlace = 0;
		gangroPlace = 0;
		cybaPlace = 0;	
		
		itemPlace = 0;	
		inventPlace = 0;
		
		fuel = ifuel;
		ammo = iammo;
		
		itemAmount = 0;
	}
	public void dumpPlayerPosition(player gamePlayer)
	{
			gamePlayer.setPosition(playerX, playerY);
	}
	public void handle(player gamePlayer, level gameLevel, arachno gameArachno, wingherd gameWingherd, 
		              phillis gamePhillis, gangro gameGangro, cyba gameCyba, item objItem, item invItem, score sc)
	{ // later, a cyba will be needed too.
	
		if (gameArachno.active == true)
		{	
			gameArachno.handle(gamePlayer, sc);
		}
		if (gameWingherd.active == true)
		{		
			gameWingherd.handle(gamePlayer, sc);
		}
		if (gamePhillis.active == true)
		{		
			gamePhillis.handle(gamePlayer, sc);
		}
		if (gameGangro.active == true)
		{		
			gameGangro.handle(gamePlayer, gameLevel, sc);		
		}
		if (gameCyba.active == true)
		{
			gameCyba.handle(gamePlayer, gameLevel, sc);
		}
		if (objItem.active == true)
		{
			objItem.handle(gamePlayer, sc);
		}
		if (invItem.active == true)
		{
			invItem.handle(gamePlayer, sc);
		}
	}
	public void draw(Graphics g)
	{
	int xpos = 0, ypos = 0;
	
		while (xpos < levelSet.levelWidth && ypos < levelSet.levelHeight)
		{
				switch(levelArray[ypos][xpos])
				{
					case 1:
						g.drawImage(block1,xpos * 25,ypos * 25,this);
					break;					
					case 2:
						g.drawImage(block2,xpos * 25,ypos * 25,this);					
					break;					
					case 3:
						g.drawImage(block3,xpos * 25,ypos * 25,this);					
					break;		
					case 4:
						g.drawImage(block4,xpos * 25,ypos * 25,this);					
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
	public void setLevel(int level, arachno[] gameArachno, wingherd[] gameWingherd, 
	                     phillis[] gamePhillis, gangro[] gameGangro, cyba[] gameCyba,
	                     item[] items, item[] invent, gui theGui)
	{
		theGui.current = level - 1;
		
			switch(level)
			{
				case 1:
					clearArrays(gameArachno, gameWingherd, gamePhillis, gameGangro, gameCyba, items, invent);
					setArray(levelSet.level1, gameArachno, gameWingherd, gamePhillis, gameGangro, gameCyba, items, invent);
				break;			
				case 2:
					clearArrays(gameArachno, gameWingherd, gamePhillis, gameGangro, gameCyba, items, invent);				
					setArray(levelSet.level2, gameArachno, gameWingherd, gamePhillis, gameGangro, gameCyba, items, invent);		
				break;			
				case 3:
					clearArrays(gameArachno, gameWingherd, gamePhillis, gameGangro, gameCyba, items, invent);				
					setArray(levelSet.level3, gameArachno, gameWingherd, gamePhillis, gameGangro, gameCyba, items, invent);		
				break;			
				case 4:
					clearArrays(gameArachno, gameWingherd, gamePhillis, gameGangro, gameCyba, items, invent);				
					setArray(levelSet.level4, gameArachno, gameWingherd, gamePhillis, gameGangro, gameCyba, items, invent);		
				break;			
				case 5:
					clearArrays(gameArachno, gameWingherd, gamePhillis, gameGangro, gameCyba, items, invent);				
					setArray(levelSet.level5, gameArachno, gameWingherd, gamePhillis, gameGangro, gameCyba, items, invent);		
				break;
				case 6:
					theGui.gamePhase = 3;
				break;
			}
	}
	private void setArray(int[][] levelMatrix, arachno[] gameArachno, wingherd[] gameWingherd, 
	                      phillis[] gamePhillis, gangro[] gameGangro, cyba[] gameCyba,
	                      item[] items, item[] invent)
	{
	int xpos = 0, ypos = 0;
	
		while (xpos < levelSet.levelWidth && ypos < levelSet.levelHeight)
		{
				if (levelMatrix[ypos][xpos] > 0 - 1 && levelMatrix[ypos][xpos] < 5)
				{
					levelArray[ypos][xpos] = levelMatrix[ypos][xpos];
				}
				else
				{
						switch(levelMatrix[ypos][xpos])
						{
							case 5: // player
								playerX = xpos * 25;
								playerY = ypos * 25;
							break;
							case 6: // arachno 
								if (arachnoPlace < 10)
								{
									getWaypoints(levelMatrix, xpos * 25, ypos * 25);
									gameArachno[arachnoPlace].setWaypoints(xpos * 25, ypos * 25, writingPatrolX, writingPatrolY);
									arachnoPlace++;
								}
							break;
							case 7: // wingherd
								if (wingherdPlace < 10)
								{
									getWaypoints(levelMatrix, xpos * 25, ypos * 25);
									gameWingherd[wingherdPlace].setWaypoints(xpos * 25, ypos * 25, writingPatrolX, writingPatrolY);
									wingherdPlace++;
								}
							break;
							case 8: // phillis
								if (phillisPlace < 10)
								{
									getWaypoints(levelMatrix, xpos * 25, ypos * 25);
									gamePhillis[phillisPlace].setWaypoints(xpos * 25, ypos * 25, writingPatrolX, writingPatrolY);
									phillisPlace++;
								}
							break;
							case 9: // gangro
								if (gangroPlace < 10)
								{
									gameGangro[gangroPlace].setPosition(xpos * 25, ypos * 25);
									gangroPlace++;
								}
							break;
							case 10: // cyba
								if (cybaPlace < 10)
								{
									getWaypoints(levelMatrix, xpos * 25, ypos * 25);
									gameCyba[cybaPlace].setWaypoints(xpos * 25, ypos * 25, writingPatrolX, writingPatrolY);
									cybaPlace++;
								}
							break;
							case 11: // regular
								if (itemPlace < 5)
								{							
									items[itemPlace].setPosition(0, xpos * 25, ypos * 25);					
									itemPlace++;
									itemAmount++;									
								}
							break;							
							case 12: // unique
								if (itemPlace + 5 < 10)
								{
									items[itemPlace + 5].setPosition(1, xpos * 25, ypos * 25);
									itemPlace++; 
									itemAmount++;
								}							
							break;
							case 13: // fuel
								if (inventPlace < 10)
								{
									invent[inventPlace].setPosition(2, xpos * 25, ypos * 25);
									invent[inventPlace].toDraw = fuel;
									inventPlace++;
								}
							break;							
							case 14: // ammo
								if (inventPlace < 10)
								{
									invent[inventPlace].setPosition(3, xpos * 25, ypos * 25);
									invent[inventPlace].toDraw = ammo;
									inventPlace++;								
								}
							break;
						}
					levelArray[ypos][xpos] = 0;
				}		
			
			xpos++;
			
				if (xpos == levelSet.levelWidth)
				{
					xpos = 0;
					ypos++;
				}
		}
	}
	private void getWaypoints(int[][] array, int x, int y) // actual pos
	{
	int xpos = (int)(x / 25);
	int ypos = (int)(y / 25); // grid
	
		writingPatrolX = x + (3 * 25);
		writingPatrolY = y;
	
			while (xpos < levelSet.levelWidth)
			{
					if (array[ypos][xpos] == -1)
					{
						writingPatrolX = xpos * 25;
						break;
					}
				xpos++;
			}
	}
	private void clearArrays(arachno[] gameArachno, wingherd[] gameWingherd, 
	                         phillis[] gamePhillis, gangro[] gameGangro, cyba[] gameCyba,
	                         item[] items, item[] invent)
	{
	int place = 0;
	
			while (place < 10)
			{
				gameArachno[place].active = false;
				gameWingherd[place].active = false;
				gamePhillis[place].active = false;
				gameGangro[place].active = false;
				gameCyba[place].active = false;			
				items[place].active = false;
				invent[place].active = false;
				place++;
			}
		arachnoPlace = 0;
		wingherdPlace = 0;	
		phillisPlace = 0;
		gangroPlace = 0;		
		cybaPlace = 0;
		itemPlace = 0;
		itemAmount = 0;	
		inventPlace = 0;
	}
}