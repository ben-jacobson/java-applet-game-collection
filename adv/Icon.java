import java.applet.*;
import java.awt.*;
import java.net.*;

public class Icon extends Applet
{
static final int unpressed = 0;
static final int pressed = 1;

Image Icon;

int topLeftX, topLeftY;
int bottomRightX, bottomRightY;

int eventState;
boolean visible;

		Icon(Image iconImage, int x, int y, int width, int height)
		{
			Icon = iconImage;
			
			topLeftX = x;
			topLeftY = y;
			
			bottomRightX = x + width;
			bottomRightY = y + height;
			
			eventState = unpressed;
			visible = true;
		}
		boolean pointingTo(int mouseX, int mouseY)
		{
				if (visible == true)
				{
						if (mouseX < bottomRightX && mouseX > topLeftX &&	
							 mouseY < bottomRightY && mouseY > topLeftY)
						{
							return true;
						}
						else
						{
							return false;
						}
				}
				else
				{
					return false;
				}
		}
		void hideIcon()
		{
			visible = false;
		}
		void showIcon()
		{
			visible = true;
		}
		void Paint(Graphics g)
		{
		int width = bottomRightX - topLeftX;
		int height = bottomRightY - topLeftY;
		int position = eventState * width;	
		
				if (visible == true)
				{
					g.drawImage(Icon, topLeftX, topLeftY, bottomRightX, bottomRightY, position, 0, position + width, height, this);		
				}
		}
}