import java.awt.*;
import java.applet.*;

public class boulderHandler extends Applet
{
static final int up = 0;
static final int down = 1;
static final int left = 2;
static final int right = 3;

static final int disused = 0, rock = 1, gem = 2;

static final int maxBoulders = 16;

static final int tileWidth = 16;
static final int tileHeight = 16;

boulder gameBoulders[];
int holesX[], holesY[];
Image boulder, diamond;

	boulderHandler(Image boulderImg, Image diamondImg)
	{
	int newBoulderCount = 0;
	
		boulder = boulderImg;
		diamond = diamondImg;
		
		holesX = new int[maxBoulders];
		holesY = new int[maxBoulders];
		
		gameBoulders = new boulder[maxBoulders];
				
			while	(newBoulderCount < maxBoulders)
			{
				gameBoulders[newBoulderCount] = new boulder();
				newBoulderCount++;
			}				
	}
	public void getBoulders(int bouldersX[], int bouldersY[], int bouldersState[])
	{
	int scan = 0;
		
		while (scan < maxBoulders)
		{
			gameBoulders[scan].changePosition(bouldersX[scan], bouldersY[scan]);
			gameBoulders[scan].state = bouldersState[scan];
			scan++;
		}
	}
	public void getHoles(int gameHolesX[], int gameHolesY[])
	{
	int scan = 0;
		
		while (scan < maxBoulders)
		{
			holesX[scan] = gameHolesX[scan];
			holesY[scan] = gameHolesY[scan];
			scan++;
		}	
	}
	public boolean checkMoveBoulders(int xpos, int ypos, int direction, int nextTile)
	{
	int scan = 0, scanOthers = 0, scanHoles = 0;
	boolean canMove = true;
	
			while (scan < maxBoulders)
			{
					switch(direction)
					{
						case up:
							if (ypos == gameBoulders[scan].y + 1 && xpos == gameBoulders[scan].x && gameBoulders[scan].state == rock)
							{
									while (scanOthers < maxBoulders)
									{
											if (gameBoulders[scan].x == gameBoulders[scanOthers].x &&
											    gameBoulders[scan].y == gameBoulders[scanOthers].y + 1 &&
											    gameBoulders[scanOthers].state != disused)
											{
												canMove = false;
												break;
											}
										scanOthers++;
									}
									if (canMove == true && nextTile != 1)
									{
										gameBoulders[scan].move(up);
										
											while (scanHoles < maxBoulders)
											{
													if (gameBoulders[scan].x == holesX[scanHoles] &&
													    gameBoulders[scan].y == holesY[scanHoles])
													{
														gameBoulders[scan].state = gem;
														break;
													}
												scanHoles++;
											}																			
									}
									else
									{
										canMove = false;
									}										
							}
							break;
						case down:
							if (ypos == gameBoulders[scan].y - 1 && xpos == gameBoulders[scan].x && gameBoulders[scan].state == rock)
							{
									while (scanOthers < maxBoulders)
									{
											if (gameBoulders[scan].x == gameBoulders[scanOthers].x &&
											    gameBoulders[scan].y == gameBoulders[scanOthers].y - 1 &&
											    gameBoulders[scanOthers].state != disused)
											{
												canMove = false;
												break;
											}
										scanOthers++;
									}
									if (canMove == true && nextTile != 1)
									{
										gameBoulders[scan].move(down);
										
											while (scanHoles < maxBoulders)
											{
													if (gameBoulders[scan].x == holesX[scanHoles] &&
													    gameBoulders[scan].y == holesY[scanHoles])
													{
														gameBoulders[scan].state = gem;
														break;
													}
												scanHoles++;
											}											
									}
									else
									{
										canMove = false;
									}											
							}
							break;
						case left:
							if (ypos == gameBoulders[scan].y && xpos == gameBoulders[scan].x + 1 && gameBoulders[scan].state == rock)
							{
									while (scanOthers < maxBoulders)
									{
											if (gameBoulders[scan].x == gameBoulders[scanOthers].x + 1 &&
											    gameBoulders[scan].y == gameBoulders[scanOthers].y &&
											    gameBoulders[scanOthers].state != disused)
											{
												canMove = false;
												break;
											}
										scanOthers++;
									}
									if (canMove == true && nextTile != 1)
									{
										gameBoulders[scan].move(left);
										
											while (scanHoles < maxBoulders)
											{
													if (gameBoulders[scan].x == holesX[scanHoles] &&
													    gameBoulders[scan].y == holesY[scanHoles])
													{
														gameBoulders[scan].state = gem;
														break;
													}
												scanHoles++;
											}											
									}	
									else
									{
										canMove = false;
									}										
							}
							break;
						case right:
							if (ypos == gameBoulders[scan].y && xpos == gameBoulders[scan].x - 1 && gameBoulders[scan].state == rock)
							{							
									while (scanOthers < maxBoulders)
									{
											if (gameBoulders[scan].x == gameBoulders[scanOthers].x - 1 &&
											    gameBoulders[scan].y == gameBoulders[scanOthers].y &&
											    gameBoulders[scanOthers].state != disused)
											{
												canMove = false;
												break;
											}
										scanOthers++;
									}
									if (canMove == true && nextTile != 1)
									{
										gameBoulders[scan].move(right);
										
											while (scanHoles < maxBoulders)
											{
													if (gameBoulders[scan].x == holesX[scanHoles] &&
													    gameBoulders[scan].y == holesY[scanHoles])
													{
														gameBoulders[scan].state = gem;
														break;
													}
												scanHoles++;
											}											
									}
									else
									{
										canMove = false;
									}								
							}
							break;																					
					}
				scan++;
			}
		return canMove;
	}
	public boolean collectedGems(int xpos, int ypos)
	{
	int scan = 0;
	boolean collected = false;
	
			while (scan < maxBoulders)
			{
					if (gameBoulders[scan].state == gem && xpos == gameBoulders[scan].x && ypos == gameBoulders[scan].y)
					{
						gameBoulders[scan].state = disused;
						collected = true;
					}
				scan++;
			}				
		return collected;
	}	
	public void draw(Graphics g)
	{
	int boulderCount = 0;
	
		while(boulderCount < maxBoulders)
		{
				if (gameBoulders[boulderCount].state == rock)
				{
					g.drawImage(boulder, gameBoulders[boulderCount].x * tileWidth, gameBoulders[boulderCount].y * tileHeight, this);
				}
				if (gameBoulders[boulderCount].state == gem)
				{
					g.drawImage(diamond, gameBoulders[boulderCount].x * tileWidth, gameBoulders[boulderCount].y * tileHeight, this);
				}
			boulderCount++;
		}
	}
}