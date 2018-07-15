import java.applet.*;
import java.awt.*;

public class StaticItemHandler
{
static final int maxStatics = 64;

StaticItem staticItems[];

	StaticItemHandler(String staticNames[], Image staticItem[], int x[], int y[], int w[], int h[], int room[], int cellAnim[], int animSpeeds[])
	{
	int checking = 0;
	
		staticItems = new StaticItem[maxStatics];
		
			while (checking < maxStatics)
			{
				staticItems[checking] = new StaticItem(staticItem[checking], x[checking], y[checking], w[checking], h[checking], room[checking]);
				staticItems[checking].item.speed = animSpeeds[checking];
				staticItems[checking].item.switchAnimation(0, cellAnim[checking]);
				staticItems[checking].name = staticNames[checking];
				checking++;
			}
	}
	void Handle(int currentRoom)
	{
	int checking = 0;
	
		while (checking < maxStatics)
		{		
				if (staticItems[checking].inRoom == currentRoom)
				{
					staticItems[checking].Handle();
				}
			checking++;
		}
	}
	boolean canMove(Player PlayerCheck, int room, int direction)
	{
	int checking = 0;
	boolean returnTrue = true;
	
				while(checking < maxStatics)
				{
						if (staticItems[checking].inRoom == room && staticItems[checking].hidden == false)
						{
						int xcheck = 0, ycheck = 0;
						
								switch(direction)
								{
									case Player.up:
									
										xcheck = PlayerCheck.topLeftX;
										
											if (PlayerCheck.topLeftY - PlayerCheck.walkSpeed < staticItems[checking].bottomRightY &&
											    PlayerCheck.topLeftY - PlayerCheck.walkSpeed > staticItems[checking].topLeftY)
											{
													while (xcheck < PlayerCheck.bottomRightX)
													{
															if (xcheck < staticItems[checking].bottomRightX && xcheck > staticItems[checking].topLeftX)
															{
																returnTrue = false;
																break;
															}
														xcheck++;
													}
										   }
										   
									break;
									case Player.down:			
															
										xcheck = PlayerCheck.topLeftX;
										
											if (PlayerCheck.bottomRightY + PlayerCheck.walkSpeed > staticItems[checking].topLeftY &&
											    PlayerCheck.topLeftY + PlayerCheck.walkSpeed < staticItems[checking].topLeftY)
											{
													while (xcheck < PlayerCheck.bottomRightX)
													{
															if (xcheck < staticItems[checking].bottomRightX && xcheck > staticItems[checking].topLeftX)
															{
																returnTrue = false;
																break;
															}
														xcheck++;
													}
											}	
																				
									break;
									case Player.left:
									
										ycheck = PlayerCheck.topLeftY;
										
											if (PlayerCheck.topLeftX - PlayerCheck.walkSpeed < staticItems[checking].bottomRightX  &&
											    PlayerCheck.bottomRightX - PlayerCheck.walkSpeed > staticItems[checking].topLeftX)
											{
													while (ycheck < PlayerCheck.bottomRightY)
													{
															if (ycheck > staticItems[checking].topLeftY && ycheck < staticItems[checking].bottomRightY)
															{
																returnTrue = false;
																break;
															}
														ycheck++;
													}
											}										
									
									break;
									case Player.right:
									
										ycheck = PlayerCheck.topLeftY;
										
											if (PlayerCheck.bottomRightX + PlayerCheck.walkSpeed < staticItems[checking].bottomRightX  &&
											    PlayerCheck.bottomRightX + PlayerCheck.walkSpeed > staticItems[checking].topLeftX)
											{
													while (ycheck < PlayerCheck.bottomRightY)
													{
															if (ycheck > staticItems[checking].topLeftY && ycheck < staticItems[checking].bottomRightY)
															{
																returnTrue = false;
																break;
															}
														ycheck++;
													}
											}	
																												
									break;
								}
						}
					checking++;		
				}
			return returnTrue;
	}	
}