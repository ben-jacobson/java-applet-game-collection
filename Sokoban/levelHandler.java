import java.awt.*;
import java.applet.*;

/* level Data Defines
0 = black square
1 = brick wall
2 = concrete floor
3 = player start position
4 = boulder
5 = hole (convert boulder)
*/

public class levelHandler extends Applet
{
static final int levelWidth = 20;
static final int levelHeight = 12;

static final int maxBoulders = 16;

static final int tileWidth = 16;
static final int tileHeight = 16;

static final int disused = 0, rock = 1, gem = 2;

Image blankSquare, brickWall, floor, floorHole;

level levelData[];
int currentLevel;

int playerStartX, playerStartY;

int bouldersX[];
int bouldersY[];
int bouldersState[];

int holesX[];
int holesY[];

	levelHandler(Image blank, Image brick, Image floorSquare, Image holeSquare)
	{
		blankSquare = blank;
		brickWall = brick;
		floor = floorSquare;
		floorHole = holeSquare;
		
		levelData = new level[5];
		
		levelData[0] = new level("SOKOBAN!", 250);
		levelData[1] = new level("level twoe", 300);
		levelData[2] = new level("level pee", 300);
		levelData[3] = new level("penis", 300);	
		levelData[4] = new level("ANAL VIRGINITY", 300);		
		
		bouldersX = new int[maxBoulders];
		bouldersY = new int[maxBoulders];
		bouldersState = new int[maxBoulders];
		
		holesX = new int[maxBoulders];
		holesY = new int[maxBoulders];
		
		initLevelData();
		changeLevel(0);	
	}
	public void initLevelData()
	{
	int levelOne[][] = {{0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0},
		                 {0,0,0,0,1,2,2,2,1,0,0,0,0,0,0,0,0,0,0,0},
		                 {0,0,0,0,1,4,2,2,1,0,0,0,0,0,0,0,0,0,0,0},
		                 {0,0,1,1,1,2,2,4,1,1,0,0,0,0,0,0,0,0,0,0},
		                 {0,0,1,2,2,4,2,4,2,1,0,0,0,0,0,0,0,0,0,0},
		                 {1,1,1,2,1,2,1,1,2,1,0,0,0,1,1,1,1,1,1,0},
		                 {1,2,2,2,1,2,1,1,2,1,1,1,1,1,2,2,5,5,1,0},
		                 {1,2,4,2,2,4,2,2,2,2,2,2,2,2,2,2,5,5,1,0},
		                 {1,1,1,1,1,2,1,1,1,2,1,3,1,1,2,2,5,5,1,0},
		                 {0,0,0,0,1,2,2,2,2,2,1,1,1,1,1,1,1,1,1,0},
		                 {0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0},
		                 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
		                 
	int levelTwo[][] = {{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		                 {0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0},
		                 {0,1,5,5,0,0,1,0,0,0,0,0,1,1,1,0,0,0,0,0},
		                 {0,1,5,5,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0},
		                 {0,1,5,5,0,0,1,4,1,1,1,1,0,0,1,0,0,0,0,0},
		                 {0,1,5,5,0,0,0,0,0,0,1,1,0,0,1,0,0,0,0,0},
		                 {0,1,5,5,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0},
		                 {0,1,1,1,1,1,1,0,0,0,0,0,0,0,1,0,0,0,0,0},
		                 {0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0},
		                 {0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0},
		                 {0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0},
		                 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};

	int levelThree[][] = {{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		                   {0,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1,0,0,0,0},
		                   {0,1,3,2,2,2,2,5,1,0,0,0,0,1,1,0,0,0,0,0},
		                   {0,1,2,4,2,2,2,2,1,0,0,0,1,1,1,1,0,0,0,0},
		                   {0,1,2,2,2,2,2,2,1,0,0,0,0,1,0,0,0,0,0,0},
		                   {0,1,2,2,2,2,2,2,1,0,0,0,0,1,0,0,0,0,0,0},
		                   {0,1,2,2,2,2,2,2,1,0,0,0,0,0,0,0,0,0,0,0},
		                   {0,1,2,2,2,2,2,2,1,1,1,0,0,0,0,0,0,0,0,0},
		                   {0,1,5,2,2,2,2,2,2,5,1,0,0,0,0,0,0,0,0,0},
		                   {0,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0},
		                   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		                   {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}};

	int levelFour[][] = {{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		                  {0,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1,0,0,0,0},
		                  {0,1,3,2,2,2,2,5,1,0,0,0,0,1,1,0,0,0,0,0},
		                  {0,1,2,4,2,2,2,2,1,0,0,0,1,1,1,1,0,0,0,0},
		                  {0,1,2,2,2,2,2,2,1,0,0,0,0,0,1,0,0,0,0,0},
		                  {0,1,2,2,2,2,4,2,1,0,0,0,0,1,1,1,0,0,0,0},
		                  {0,1,2,2,2,2,2,2,1,0,0,0,0,0,0,0,0,0,0,0},
		                  {0,1,2,4,2,2,2,2,1,1,1,0,0,0,0,0,0,0,0,0},
		                  {0,1,5,2,2,2,2,2,2,5,1,0,0,0,0,0,0,0,0,0},
		                  {0,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0},
		                  {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		                  {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}};
		                 
	int levelFive[][] = {{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		                  {0,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1,0,0,0,0},
		                  {0,1,3,2,2,2,2,5,1,0,0,0,0,1,1,0,0,0,0,0},
		                  {0,1,2,4,2,2,2,2,1,0,0,0,1,1,1,1,0,0,0,0},
		                  {0,1,2,2,2,2,2,2,1,0,0,0,0,0,1,0,0,0,0,0},
		                  {0,1,2,2,2,2,2,2,1,0,0,0,0,1,1,1,0,0,0,0},
		                  {0,1,2,2,2,2,2,2,1,0,0,0,0,0,1,0,0,0,0,0},
		                  {0,1,2,2,2,2,2,2,1,1,1,0,0,1,1,1,0,0,0,0},
		                  {0,1,5,2,2,2,2,2,2,5,1,0,0,0,0,0,0,0,0,0},
		                  {0,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0},
		                  {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		                  {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}};		                 		                 		                 		                 
		                 
		levelData[0].levelData = levelOne;
		levelData[1].levelData = levelTwo;
		levelData[2].levelData = levelThree;
		levelData[3].levelData = levelFour;
		levelData[4].levelData = levelFive;						                          
	}
	public void changeLevel(int levelNumber)
	{
	int currentBoulder = 0, currentHole = 0;
	int scanX = 0, scanY = 0;
	
	int clearBoulders = 0;
	
			while (clearBoulders < maxBoulders)
			{
				bouldersState[clearBoulders] = disused;				
				clearBoulders++;
			}
	
		currentLevel = levelNumber;
		levelData[currentLevel].maxGems = 0;
		
			while (scanY < levelHeight)
			{
					if (levelData[currentLevel].levelData[scanY][scanX] == 3) // player start position
					{
						playerStartX = scanX;
						playerStartY = scanY;
						levelData[currentLevel].levelData[scanY][scanX] = 2; // floor
					}
					if (levelData[currentLevel].levelData[scanY][scanX] == 4 && currentBoulder < maxBoulders)
					{
						bouldersX[currentBoulder] = scanX;
						bouldersY[currentBoulder] = scanY;
						bouldersState[currentBoulder] = rock;
						levelData[currentLevel].levelData[scanY][scanX] = 2; // floor
						levelData[currentLevel].maxGems++;						
						currentBoulder++;					
					}
					if (levelData[currentLevel].levelData[scanY][scanX] == 5 && currentHole < maxBoulders)
					{
						holesX[currentHole] = scanX;
						holesY[currentHole] = scanY;
						currentHole++;
					}
			
				scanX++;
					
					if (scanX > levelWidth - 1)
					{
						scanX = 0;
						scanY++;
					}
			}
	}
	public int returnPlayerStartX()
	{
		return playerStartX;
	}
	public int returnPlayerStartY()
	{
		return playerStartY;
	}
	public int[] returnBouldersX()
	{
		return bouldersX;
	}
	public int[] returnBouldersY()
	{
		return bouldersY;
	}
	public int[] returnHolesX()
	{
		return holesX;	
	}
	public int[] returnHolesY()
	{
		return holesY;
	}
	public int[] returnBouldersState()
	{
		return bouldersState;
	}
	public int returnTile(int xpos, int ypos)
	{
		return levelData[currentLevel].levelData[ypos][xpos];
	}
	public int returnMaxMoves()
	{
		return levelData[currentLevel].maxMoves;
	}
	public int returnMaxGems()
	{
		return levelData[currentLevel].maxGems;
	}
	public String returnLevelName()
	{
		return levelData[currentLevel].levelName;
	}
	public boolean canPlayerMove(int xpos, int ypos)
	{
		if (levelData[currentLevel].levelData[ypos][xpos] != 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public void drawLevel(Graphics g)
	{
	int x = 0, y = 0;
	
			while (y < levelHeight)
			{
					switch(levelData[currentLevel].levelData[y][x])
					{
						case 0: // Blank Square (black)
							g.drawImage(blankSquare, x * tileWidth, y * tileHeight, this);
							break;
						case 1: // Brick Wall
							g.drawImage(brickWall, x * tileWidth, y * tileHeight, this);
							break;							
						case 2: // Floor Tile
							g.drawImage(floor, x * tileWidth, y * tileHeight, this);
							break;
						case 5: // Floor Holes
							g.drawImage(floorHole, x * tileWidth, y * tileHeight, this);
							break;																
					}				
				x++;
					
					if (x > levelWidth - 1)
					{
						x = 0;
						y++;
					}			
			}
	}
}