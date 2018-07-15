public class score
{
int score;
int lives;

	public score()
	{
		score = 0;
		lives = 3;
	}
	public void addToScore(int amount)
	{
		score += amount;
	}
	public void subFromScore(int amount)
	{
		if ((returnScore() - amount) < 0)
		{
			score = 0;
		}
		else
		{
			score -= amount;
		}
	}
	public void subFromLives()
	{
		if (lives > 0)
		{
			lives--;
		}
		else
		{
			lives = 0;
		}
	}
	public int returnScore()
	{
		return score;
	}
	public int returnLives()
	{
		return lives;
	}	
}