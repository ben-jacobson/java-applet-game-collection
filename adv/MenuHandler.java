import java.applet.*;
import java.awt.*;

public class MenuHandler extends Applet
{
static final int handCursor = 0;
static final int lookCursor = 1;
static final int talkCursor = 2;
static final int waitCursor = 3;
static final int standardCursor = 4;

static final int iconAmount = 5;

static final int unpressed = 0;
static final int pressed = 1;

Icon menuIcons[];

int cursorType;

	MenuHandler(Image handIcon, Image lookIcon, Image TalkIcon, Image invIcon, Image settingIcon)
	{
		menuIcons = new Icon[iconAmount];
		menuIcons[0] = new Icon(handIcon, 300, 5, 40, 40);		
		menuIcons[1] = new Icon(lookIcon, 340, 5, 40, 40);
		menuIcons[2] = new Icon(TalkIcon, 380, 5, 40, 40);		
		menuIcons[3] = new Icon(invIcon, 420, 5, 40, 40);
		menuIcons[4] = new Icon(settingIcon, 460, 5, 40, 40);	
		
		cursorType = standardCursor;
	}
	void checkMousePosition(int x, int y)
	{
	int checking = 0;
	
		if (x < menuIcons[iconAmount - 1].bottomRightX && x > menuIcons[0].topLeftX && 
		    y < menuIcons[0].bottomRightY && y > menuIcons[0].topLeftY)
		{
			while (checking < iconAmount)
			{
				menuIcons[checking].showIcon();
				checking++;
			}
		}
		else
		{
			while (checking < iconAmount)
			{
				menuIcons[checking].hideIcon();
				checking++;
			}		
		}
	}
	void checkMouseDowns(int x, int y)
	{
	int checking = 0;
	
			while (checking < iconAmount)
			{
					if (menuIcons[checking].pointingTo(x, y) == true)
					{
						menuIcons[checking].eventState = pressed;
						
							if (checking < iconAmount - 2)
							{
								cursorType = checking;
							}
							else
							{
								cursorType = standardCursor;
							}
					}				
				checking++;				
			}
	}
	void checkMouseUps(int x, int y)
	{
	int checking = 0;
		
			while (checking < iconAmount)
			{
				menuIcons[checking].eventState = unpressed;
				checking++;
			}	
	}	
	boolean inventoryPressed(int x, int y)
	{
		if (menuIcons[3].eventState == pressed)
		{
			menuIcons[3].eventState = unpressed;
			return true;
		}
		else
		{
			return false;
		}
	}
	boolean settingsPressed(int x, int y)
	{
		if (menuIcons[4].eventState == pressed)
		{
			menuIcons[4].eventState = unpressed;
			return true;
		}
		else
		{
			return false;
		}	
	}
	void hideAllIcons()
	{
	int checking = 0;
	
		while (checking < iconAmount)
		{
			menuIcons[checking].eventState = unpressed;
			menuIcons[checking].hideIcon();
			checking++;
		}
	}
	void showIcons()
	{
	int checking = 0;
	
		while (checking < iconAmount)
		{
			menuIcons[checking].showIcon();
			checking++;
		}
	}	
	void Paint(Graphics g)
	{
	int checking = 0;
	
			while (checking < iconAmount)
			{
				menuIcons[checking].Paint(g);
				checking++;
			}
	}
}