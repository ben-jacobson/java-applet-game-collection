import java.awt.*;
import java.applet.*;

public class guiClass extends Applet
{
static final int blank = 0;
static final int nextLevel = 1;
static final int outOfMoves = 2;
static final int endGame = 3;

static final int maxLevels = 5 - 1;

Image gui;
int maxMoves, maxGems; // for current level

String levelName;
int movesMade, gemsCollected;

Font guiFont;
Font messageFont;

int message;

	guiClass(Image guiImage)
	{
		gemsCollected = 0;
		movesMade = 0;
		gui = guiImage;
		guiFont = new Font("Arial", Font.BOLD, 11);
		messageFont = new Font("Arial", Font.BOLD, 12);
		
		message = blank;
	}
	public void addGem()
	{
		gemsCollected++;
	}
	public void addMove()
	{
		movesMade++;
	}
	public void setData(String name, int gems, int moves) // also resets current stats
	{
		levelName = name;
		maxGems = gems;
		maxMoves = moves;	
		gemsCollected = 0;
		movesMade = 0;			
	}
	public void checkMessages(int currentLevel)
	{
			if (movesMade > maxMoves - 1)
			{
				message = outOfMoves;
			}
			if (gemsCollected > maxGems - 1)
			{
				if (currentLevel > maxLevels - 1)
				{
					message = endGame;
				}
				if (message != endGame)
				{
					message = nextLevel;
				}
			}												
	}
	public void endGameMessage()
	{
		message = endGame;
	}	
	public void draw(Graphics g)
	{
		g.setFont(guiFont);
		g.drawImage(gui, 0, 192, this);
		g.setColor(Color.RED);
		g.drawString(levelName, 188, 208);
		g.drawString("Gems: " + gemsCollected + "/" + maxGems, 188, 220);
		g.drawString("Moves: " + movesMade + "/" + maxMoves, 188, 232);		
		
			if (message > blank)
			{
				g.setColor(Color.WHITE);
				g.fillRect(50,50,200,100);
				g.setColor(Color.GRAY);
				g.fillRect(52,52,196,96);
				g.setColor(Color.BLACK);
				g.setFont(messageFont);
				
					switch(message)
					{
						case nextLevel:
							g.drawString("Good, Now to the Next Level",70,105);
							break;
						case outOfMoves:
							g.drawString("Sorry, You're out of Moves",70,105);						
							break;
						case endGame:
							g.drawString("Congratulations,", 97, 98);		
							g.drawString(" You've finished the Game", 75, 113);				
							break;
					}
			}
	}
}