public class score
{
final static int maxLevels = 5;
int score, level;

	score()
	{
		score = 0;
		level = 1;
	}
	public void addToScore(int amount)
	{
		score += amount;
	}
	public void nextLevel()
	{
		if (level < 3)
		{		
			level++;
		}
	}
	public int calculateSpeed()
	{
		return (maxLevels - level) / 2;
	}
}