import java.applet.*;
import java.awt.*;

// comments: Some code from tetri 1 was very helpful here, pretty much all of it was from scratch, 
//           but some functions such as rotate, parts where taken from the previous game, who would of thought 
//           code that i wrote nearly 2 years ago would prove helpful :)

public class block extends Applet
{
static final int blockWidth = 20;
static final int blockHeight = 20;

static final int matrixWidth = 14 - 1;
static final int matrixHeight = 24;

static final int blockColours = 7;
static final int blank = -1;
Image []blockImage;

int []xpos; // y positions on screen, not grid, x positions on grid, not screen. confused yet :)
int []ypos;
int colour;

int []yposGrid; // where you can get the grid position for y, needs 2 b updated tho

boolean blockOk; // this is set if it is impossible for the block to move in2 the screen
boolean blockFinished;

	block(Image []blocks)
	{
	int imagePlace = 0;
	
		blockImage = new Image[blockColours];
		
			while (imagePlace < blockColours)
			{
				blockImage[imagePlace] = blocks[imagePlace];
				imagePlace++;
			}			
	
		xpos = new int[4];
		ypos = new int[4];
		colour = -1; // blank till fully initialised
		
		yposGrid = new int[4];
		
		blockOk = true;
		blockFinished = true;
	}
	public void moveLeft(int [][]matrix)
	{
	int place = 0;
	boolean canMove = true;
				
										
			while (place < 4)
			{
					if (xpos[place] < 0 + 1)
					{
						canMove = false;
						break;
					}
					else
					{
						if (matrix[xpos[place] - 1][(int)(ypos[place] / blockHeight)] != blank)
						{
							canMove = false;
							break;
						}
					}							
				place++;
			}			
		place = 0;
		
			while (place < 4 && canMove == true)
			{
				xpos[place]--;
				place++;
			}						
	}
	public void moveRight(int [][]matrix)
	{
	int place = 0;
	boolean canMove = true;
				
										
			while (place < 4)
			{
					if (xpos[place] > matrixWidth - 1)
					{
						canMove = false;
						break;
					}
					else
					{
						if (matrix[xpos[place] + 1][(int)(ypos[place] / blockHeight)] != blank)
						{
							canMove = false;
							break;
						}
					}							
				place++;
			}			
		place = 0;
		
			while (place < 4 && canMove == true)
			{
				xpos[place]++;
				place++;
			}		
	}
	public void moveDown(int [][]matrix, int speed)
	{
	int place = 0;
		
			while (place < 4)
			{
					if ((ypos[place] / blockHeight) == matrixHeight - 1 || matrix[xpos[place]][(ypos[place] / blockHeight) + 1] != blank)
					{
						blockFinished = true;
						break;
					}
				place++;
			}			
		place = 0;	
		
			while (blockFinished == false && place < 4)
			{
				ypos[place] += speed;
				place++;
			}						
	}
	private void getOriginalYpos()
	{
	int place = 0;
	int newypos = ypos[place];
	
		while (place < 4)
		{
			newypos = ypos[place];
			
				while (newypos % blockHeight > 10)
				{
					newypos--;
				}
			yposGrid[place] = newypos / blockHeight;
			place++;
		}
	}		
	public void rotate(int [][]matrix) // rotate counter-clockwise
	{
	int [][]matrix1 = {{0,0,0,0}, //original shape
		              {0,0,0,0},
		              {0,0,0,0},
		              {0,0,0,0}}; 
	int counterx = 0, countery = 0; 
	int [][]matrix2 = {{0,0,0,0}, // transformed shape
		              {0,0,0,0},
		              {0,0,0,0},
		              {0,0,0,0}}; 
	int counter2x = 0, counter2y = 4 - 1;
	
	int minx = 0, miny = 0;
	int pos = 0;
		
	getOriginalYpos();
	
	minx = xpos[pos];
	miny = yposGrid[pos];		
	
	boolean canMove = true;
		

		while (pos < 4)
		{
				if (xpos[pos] > matrixWidth - 1 || xpos[pos] < 0 + 1)
				{
					canMove = false;
					break;
				}
			pos++;
		}
		
		pos = 0;
		
		if (canMove == true)
		{
			while (pos < 4)
			{
					if (xpos[pos] < minx)
					{
						minx = xpos[pos];
					}
					if (ypos[pos] < miny)
					{
						miny = yposGrid[pos];
					}					
				pos++;
			}
			
		pos = 0;	
				
			while (pos < 4) // fill the shapeArray
			{					
				matrix1[yposGrid[pos] - miny][xpos[pos] - minx] = 1; 	
				pos++;
			}		
			
			while (countery < 4)
			{
				matrix2[counter2x][counter2y] = matrix1[counterx][countery];		
				counterx++;
				counter2y--;
				
					if (counterx > 4 - 1)
					{
						counterx = 0;
						counter2y = 4 - 1;
						
						countery++;
						counter2x++;
					}								
			}
		
		counterx = 0;
		countery = 0;
		pos = 0;
		
			while (pos < 4) // rebuild the new shape
			{		
					if (matrix2[countery][counterx] == 1) 
					{
						xpos[pos] = minx + counterx - 2;
						ypos[pos] = (miny + countery) * blockHeight;
						pos++;					
					}
					
				counterx++;
				
					if (counterx > 4 - 1)
					{
						counterx = 0;
						countery++;
					}				
			}		
		}
	}
	public void initBlock(int []blockX, int []blockY, int [][]blockMatrix)
	{	
	int blockPlace = 0;
		
			while (blockPlace < 4)
			{
				if (blockMatrix[blockX[blockPlace]][blockY[blockPlace]] != 0)
				{
					xpos[blockPlace] = blockX[blockPlace];
					ypos[blockPlace] = blockY[blockPlace] * blockHeight;
				}
				else // cannot add to matrix
				{
					blockOk = false;
				}
				blockPlace++;
			}
		colour = (int)(Math.random() * blockColours);
		blockFinished = false;
	}
	public void draw(Graphics g)
	{
	int place = 0;
	
		while (place < 4)
		{
			if (colour > blank && colour < blockColours)
			{
				g.drawImage(blockImage[colour], (xpos[place] * blockWidth) + 7, ypos[place] + 7, this);
			}
			place++;
		}
	}
}