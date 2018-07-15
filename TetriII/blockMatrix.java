import java.applet.*;
import java.awt.*;

public class blockMatrix extends Applet
{
static final int blockColours = 7;
static final int blank = -1;

static final int width = 14;
static final int height = 24;

static final int blockWidth = 20;
static final int blockHeight = 20;

int blockPieceX[] = {6,7,6,7};
int blockPieceY[] = {1,1,2,2};
int LpieceX[] = {6,6,6,7};
int LpieceY[] = {1,2,3,3};
int LpieceX2[] = {7,7,7,6};
int LpieceY2[] = {1,2,3,3};
int TpieceX[] = {5,6,7,6};
int TpieceY[] = {1,1,1,2};
int ZpieceX[] = {6,7,7,8};
int ZpieceY[] = {1,1,2,2};
int SpieceX[] = {7,8,6,7};
int SpieceY[] = {1,1,2,2};
int IpieceX[] = {6,6,6,6};
int IpieceY[] = {1,2,3,4};

block theBlock;

int [][]gameBlockMatrix;
Image []blockImage;
Image []nextBlockImage;

int nextBlock;
boolean cannotFitBlock;

int score;
int level;

	blockMatrix(Image []blocks, Image []nextBlocks)
	{
	int imagePlace = 0;
	int xpos = 0, ypos = 0;
	
		gameBlockMatrix = new int[width][height];
		
			while (xpos < width && ypos < height) // clear the block matrix
			{
				gameBlockMatrix[xpos][ypos] = blank; // blank
				xpos++;
				
					if (xpos == width)
					{
						xpos = 0;
						ypos++;
					}
			}
			
		blockImage = new Image[blockColours];
		nextBlockImage = new Image[7];
		
			while (imagePlace < blockColours)
			{
				blockImage[imagePlace] = blocks[imagePlace];
				nextBlockImage[imagePlace] = nextBlocks[imagePlace];	// theres 7 colours and 7 types :) cheetah!!			
				imagePlace++;
			}
			
		theBlock = new block(blockImage);	
		
		nextBlock	= (int)(Math.random() * 7);		
		cannotFitBlock = false;
		
		score = 0;
	}
	public void handle(boolean downKey)
	{
	int blockPlace = 0;
	int speed = 0;
	int blockType;
	
			if (theBlock.blockFinished == true)
			{						
					while (blockPlace < 4)
					{
						gameBlockMatrix[theBlock.xpos[blockPlace]][(theBlock.ypos[blockPlace] / blockHeight)] = theBlock.colour;
						blockPlace++;
					}
				
				blockType = nextBlock;
				nextBlock	= (int)(Math.random() * 7); 
				
					switch (blockType)
					{
						case 0: // block piece
							theBlock.initBlock(blockPieceX, blockPieceY, gameBlockMatrix);
						break;
						case 1: // L piece 1
							theBlock.initBlock(LpieceX, LpieceY, gameBlockMatrix);
						break;	
						case 2: // L piece 2
							theBlock.initBlock(LpieceX2, LpieceY2, gameBlockMatrix);
						break;	
						case 3: // T piece 
							theBlock.initBlock(TpieceX, TpieceY, gameBlockMatrix);
						break;	
						case 4: // Z piece 
							theBlock.initBlock(ZpieceX, ZpieceY, gameBlockMatrix);
						break;	
						case 5: // S piece 
							theBlock.initBlock(SpieceX, SpieceY, gameBlockMatrix);
						break;	
						case 6: // I piece 
							theBlock.initBlock(IpieceX, IpieceY, gameBlockMatrix);
						break;																													
					}
					if (theBlock.blockOk != true)
					{
						cannotFitBlock = true;					
					}								
			}
			if (downKey == false)
			{
				speed = level;
			}
			else
			{
				speed = level + 10;
			}
		theBlock.moveDown(gameBlockMatrix, speed);									
		checkLines();
	}
	private void checkLines()
	{
	int xpos = 0, ypos = 0;
	int blockCount = 0;
	
			while(blockCount < width && ypos < height)
			{
					if (gameBlockMatrix[xpos][ypos] != blank)
					{
						blockCount++;
					}
					if (blockCount == width)
					{
					int place = 0;
					int xposChange = 0, yposChange = ypos;
					
							while (place < width)
							{
								gameBlockMatrix[place][ypos] = blank;
								place++;
							}
							while (yposChange > 0)
							{
								gameBlockMatrix[xposChange][yposChange] = gameBlockMatrix[xposChange][yposChange - 1];					
								xposChange++;
									
									if (xposChange == width)
									{
										xposChange = 0;
										yposChange--;
									}							
							}
						score += 10;						
					}
				xpos++;
				
					if (xpos == width)
					{
						xpos = 0;
						ypos++;
						blockCount = 0;
					}
			}
	}
	public void draw(Graphics g)
	{
	int xpos = 0, ypos = 0;
	
			while (xpos < width && ypos < height)
			{
					if (gameBlockMatrix[xpos][ypos] > blank && gameBlockMatrix[xpos][ypos] < blockColours)
					{
						g.drawImage(blockImage[gameBlockMatrix[xpos][ypos]], (xpos * blockWidth) + 7, (ypos * blockHeight) + 7, this);
					}
				xpos++;
				
					if (xpos == width)
					{
						xpos = 0;
						ypos++;
					}						
			}
		theBlock.draw(g);		
		g.drawImage(nextBlockImage[nextBlock],407,7,this);		
	}
}