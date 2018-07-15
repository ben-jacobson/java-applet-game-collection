public class waypoint
{
int[] x;
int[] y;

	waypoint(int x1, int y1, int x2, int y2)
	{
		x = new int[2];
		y = new int[2];
		x[0] = x1;
		y[0] = y1;
		x[1] = x2;
		y[1] = y2;
	}
}