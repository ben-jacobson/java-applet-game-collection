import java.applet.*;
import java.awt.*;
import java.net.*;

public class blockMatrix extends Applet
{
boolean won;
boolean lost;

final static int width = 10;
final static int height = 24;

final static int none = 0;

final static int blackStatic = 1;
final static int whiteStatic = 2;
final static int redStatic = 3;
final static int greenStatic = 4;

int [][]matrix;
block blockData;

int[] blockx;
int[] blocky;
int blockColour;

boolean blockMade;

int[] nextBlockx;
int[] nextBlocky;
int nextBlockColour;

int xp, yp;

	blockMatrix(Image white, Image black, Image red, Image green)
	{
		won = false;
		lost = false;
				
		xp = 0;
		yp = 0;
		matrix = new int[width][height];
		blockData = new block(white, black, red, green);
		
		blockx = new int[4];
		blocky = new int[4];
		blockColour = 0;
		
		nextBlockx = new int[4];
		nextBlocky = new int[4];
		nextBlockColour = 0;
		
		blockMade = false;			
	}
	public void draw(Graphics g)
	{
	int xpos = 0, ypos = 0;
	int pos = 0, posTwo = 0;
	int nextx = 0, nexty = 0;
	int minx = 0, miny = 0;
	int temp = 0;
						
			while (xpos < width && ypos < height)
			{
					if (matrix[xpos][ypos] != none) // draw statics from matrix
					{
						temp = matrix[xpos][ypos];
						
							switch(temp)
							{
								case blackStatic:
									blockData.drawBlack(g, (xpos * 20) + 10, (ypos * 20) + 10);								
									break;	
								case whiteStatic:
									blockData.drawWhite(g, (xpos * 20) + 10, (ypos * 20) + 10);								
									break;	
								case redStatic:
									blockData.drawRed(g, (xpos * 20) + 10, (ypos * 20) + 10);								
									break;	
								case greenStatic:			
									blockData.drawGreen(g, (xpos * 20) + 10, (ypos * 20) + 10);														
									break;																																			
							}						
					}
					
				xpos++;	
							
					if (xpos == width)
					{
						xpos = 0;
						ypos++;
					}				
			}
			while (pos < 4)
			{
						switch(blockColour) // draw current working peice
						{
							case 1: // black
								blockData.drawBlack(g,(blockx[pos] * 20) + 10,(blocky[pos] * 20) + 10);					
								break; 
							case 2: // white
								blockData.drawWhite(g,(blockx[pos] * 20) + 10,(blocky[pos] * 20) + 10);			
								break;
							case 3: // red 
								blockData.drawRed(g,(blockx[pos] * 20) + 10,(blocky[pos] * 20) + 10);				
								break;
							case 4: // green
								blockData.drawGreen(g,(blockx[pos] * 20) + 10,(blocky[pos] * 20) + 10);				
								break;
						}
					pos++;
			}
						
			while (posTwo < 4) // get minx and miny for drawing next block
			{
				if (minx > nextBlockx[posTwo])
				{
					minx = nextBlockx[posTwo];
				}
				if (miny > nextBlocky[posTwo])
				{
					miny = nextBlocky[posTwo];
				}
				posTwo++;
			}
			
			posTwo = 0;
			
			while (posTwo < 4) // draw next block
			{
					switch (nextBlockColour)
					{
						case 1: // black
							blockData.drawBlack(g,((nextBlockx[posTwo] - minx) * 20) + 185,((nextBlocky[posTwo] - miny) * 20) + 28);					
							break; 
						case 2: // white
							blockData.drawWhite(g,((nextBlockx[posTwo] - minx) * 20) + 185,((nextBlocky[posTwo] - miny) * 20) + 28);			
							break;
						case 3: // red 
							blockData.drawRed(g,((nextBlockx[posTwo] - minx) * 20) + 185,((nextBlocky[posTwo] - miny) * 20) + 28);				
							break;
						case 4: // green
							blockData.drawGreen(g,((nextBlockx[posTwo] - minx) * 20) + 185,((nextBlocky[posTwo] - miny) * 20) + 28);				
							break;
					}
				posTwo++;
			}			
	}
	public void handle(score sc)
	{
	int line = checkLines();
	
			if (blockMade == false)
			{				
				makeBlock();
				nextBlock();										
				
					if (checkCollision() == true)
					{
						lost = true;
					}
										
				blockMade = true;
			}
			if (checkCollision() == false)
			{
				moveBlock();
			}
			else
			{
				stopBlock();
				blockMade = false;
			}
			if (line > 0)
			{
				clearLine(line);
				moveLine(line);
				sc.addToScore(100);
			}
			if (sc.score == 3000)
			{
				sc.score = 0;			
				
					if (sc.level == 3)
					{
						won = true;
					}
					else
					{
						sc.nextLevel();
					}
			}
	}
	private boolean checkCollision()
	{
	int xpos = 0, ypos = 0;
	int pos = 0;
	int ycheck = 0;

		while(pos < 4)
		{
				if (blocky[pos] < height - 1)
				{
					if (matrix[blockx[pos]][blocky[pos] + 1] > none)
					{
						return true;
						// unreachable break; statement.
					}
				}
				else
				{
					return true; // what a bitch!
				}
			pos++;
		}
		return false;
	}
	private void makeBlock()
	{
	int pos = 0;
	
		blockColour = nextBlockColour;
		
			while (pos < 4)
			{
				blockx[pos] = nextBlockx[pos];
				blocky[pos] = nextBlocky[pos];
				pos++;
			}
	}
	private void nextBlock()
	{
	int next = (int)(Math.random() * 7) + 1; // this will randomize the next block replace the one
	int pos = 0;
	
		nextBlockColour = (int)(Math.random() * 4) + 1; // between 1 and 4
		
			while (pos < 4)
			{
					switch(next)
					{
						case 1:
							nextBlockx[pos] = blockData.block1x[pos];
							nextBlocky[pos] = blockData.block1y[pos];						
							break;
						case 2:
							nextBlockx[pos] = blockData.block2x[pos];
							nextBlocky[pos] = blockData.block2y[pos];						
							break;
						case 3:
							nextBlockx[pos] = blockData.block3x[pos];
							nextBlocky[pos] = blockData.block3y[pos];
							break;
						case 4:
							nextBlockx[pos] = blockData.block4x[pos];
							nextBlocky[pos] = blockData.block4y[pos];						
							break;	
						case 5:
							nextBlockx[pos] = blockData.block5x[pos];
							nextBlocky[pos] = blockData.block5y[pos];						
							break;	
						case 6:
							nextBlockx[pos] = blockData.block6x[pos];
							nextBlocky[pos] = blockData.block6y[pos];						
							break;	
						case 7:
							nextBlockx[pos] = blockData.block7x[pos];
							nextBlocky[pos] = blockData.block7y[pos];						
							break;																								
					}					
				pos++;
			}
	}
	private void moveBlock()
	{
	int pos = 0;
	
		while (pos < 4)
		{	
			blocky[pos] = blocky[pos] + 1;
			pos++;
		}				
	}
	private void stopBlock()
	{
	int pos = 0;
	
			while (pos < 4)
			{
				matrix[blockx[pos]][blocky[pos]] = blockColour;
				pos++;
			}
	}
	public void rotateBlock()
	{	
	int[][] shapeArray;
	int[][] temp;
	int xpos = 0, ypos = 0, xposNeg = 3;
	int minx = 0, miny = 0;
	int pos = 0, dec = 2;
	int success = 0;

		shapeArray = new int[4][4];
		temp = new int[4][4];
		minx = blockx[pos];
		miny = blocky[pos];
		
			while (pos < 4)
			{
					if (blockx[pos] < minx)
					{
						minx = blockx[pos];
					}
					if (blocky[pos] < miny)
					{
						miny = blocky[pos];
					}					
				pos++;
			}
			
		pos = 0;	
				
			while (pos < 4) // fill the shapeArray
			{
				shapeArray[blockx[pos] - minx][blocky[pos] - miny] = 1; 	
				pos++;
			}
			
			while (xpos < 4 && ypos < 4) // create a temp
			{
				temp[xpos][ypos] = shapeArray[xpos][ypos];																		
				xpos++;
				
					if (xpos == 4)
					{	
						xpos = 0;
						ypos++;
					}
			}
			
		xpos = 0;
		ypos = 0;
		
			while (xpos < 4 && ypos < 4) // rotate the new shapearray
			{
				shapeArray[xpos][ypos] = temp[ypos][xposNeg];	// flip on x axis aswell																			
				xpos++;
				xposNeg--;
				
					if (xpos == 4)
					{	
						xpos = 0;
						xposNeg = 3;
						ypos++;
					}								
			}
			
		pos = 0;
		xpos = 0;
		ypos = 0;
		
			while (pos < 4 && xpos < 4 && ypos < 4)
			{
					if (((minx + xpos) - 2) < width && ((minx + xpos) - 2) > 0 &&
					    (miny + ypos) < height)
					{
						if (matrix[(minx + xpos) - 2][(miny + ypos)] == none)
						{
							success++;
						}
					}
					else
					{
						success--;
					}
				pos++;
				xpos++;
					
					if (xpos == 4)
					{
						xpos = 0;
						ypos++;					
					}
			}
		
		pos = 0;
		xpos = 0;
		ypos = 0;
		
			if (success == 4)
			{
				while (pos < 4 && xpos < 4 && ypos < 4) // rebuild the new rotated shape
				{
						if (shapeArray[xpos][ypos] == 1)
						{
							blockx[pos] = (minx + xpos) - 2;																					
							blocky[pos] = (miny + ypos);
							pos++;
						}
					xpos++;
					
						if (xpos == 4)
						{
							xpos = 0;
							ypos++;
						}
				}
			}			
	}	
	public void fallBlock()
	{
		while (checkCollision() == false)
		{
			moveBlock();
		}
	}
	private int checkLines()
	{
	int xpos = 0, ypos = 0;
	int line = 0;
	
		while (xpos < width && ypos < height)
		{
				if (matrix[xpos][ypos] > none)
				{
					line++;
						
						if (line == width)
						{
							return ypos;
						}
				}
			xpos++;
			
				if (xpos == width)
				{
					xpos = 0;
					line = 0;
					ypos++;
				}
		}
		return 0;
	}
	private void clearLine(int which)
	{
	int xClear = 0;
	
			while (xClear < width)
			{
				matrix[xClear][which] = none;
				xClear++;
			}
	}
	private void moveLine(int which)
	{
	int xMove = 0, yMove = which;
	
		while (xMove < width && yMove > 0)
		{
				matrix[xMove][yMove] = matrix[xMove][yMove - 1];  
				
			xMove++;
			
				if (xMove == width)
				{
					xMove = 0;
					yMove--;
				}
		}
	}
	public void moveLeft()
	{
	int pos = 0;
	int success = 0;
	
		while (pos < 4 && blockx[pos] > 0)
		{	
				if (matrix[blockx[pos] - 1][blocky[pos]] == none && blockx[pos] > 0)
				{
					success++;
				}
				else
				{
					success--;
				}				
			pos++;
		}
		if (success == 4)
		{
			pos = 0;
			
				while (pos < 4)
				{
					blockx[pos] =  blockx[pos] - 1;
					pos++;		
				}
		}					
	}
	public void moveRight()
	{
	int pos = 0;
	int success = 0;
	
		while (pos < 4 && blockx[pos] < width - 1)
		{	
				if (matrix[blockx[pos] + 1][blocky[pos]] == none && blockx[pos] < width)
				{
					success++;
				}
				else
				{
					success--;
				}				
			pos++;
		}
		if (success == 4)
		{
			pos = 0;
			
				while (pos < 4)
				{
					blockx[pos] =  blockx[pos] + 1;
					pos++;		
				}
		}			
	}
}