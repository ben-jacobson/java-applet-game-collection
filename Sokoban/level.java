import java.awt.*;
import java.applet.*;

public class level extends Applet
{
static final int levelWidth = 20;
static final int levelHeight = 12;

String levelName;
int levelData[][];

int maxMoves, maxGems;

	level(String theLevelName, int moves)
	{
		levelName = theLevelName;
		maxMoves = moves;
		levelData = new int[levelWidth][levelHeight];
	}
}