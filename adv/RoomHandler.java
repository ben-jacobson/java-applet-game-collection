import java.applet.*;
import java.awt.*;
import java.awt.image.*;
import java.net.*;

public class RoomHandler extends Applet
{
static final int maxRooms = 64;
static final int maxPriBands = 16;

static final int black = 0;  // for control screens
static final int white = 1;
static final int red = 2;
static final int yellow = 3;
static final int green = 4;
static final int lightBlue = 5;
static final int blue = 6;
static final int purple = 7;

static final int up = 0;
static final int down = 1;
static final int left = 2;
static final int right = 3;

static final int roomWidth = 800;
static final int roomHeight = 500;

boolean debugViewControl;

Image roomImage[];
Image roomControlImage[];
Image roomPriorityImage[];

int emptyScreen[];

int controlScreen[];
int priorityScreen[][];
Image priScreen[];

int currentRoom;

int roomEventState;

	RoomHandler (Image room[], Image control[], Image priority[])
	{									
		roomImage = new Image[maxRooms];
		roomControlImage = new Image[maxRooms];
		roomPriorityImage = new Image[maxRooms];			
		
		priScreen = new Image[maxPriBands];
		
		currentRoom = 0;
	
		roomImage = room;	
		roomControlImage = control;
		
		emptyScreen = new int[roomWidth * roomHeight];
		
		priorityScreen = new int[maxPriBands][roomWidth * roomHeight];			
		roomPriorityImage = priority;	
			
		debugViewControl = false;		
		
		controlScreen = new int[roomWidth * roomHeight];
		roomEventState = black;		
		changeRoom(0);
	}	
	void changeRoom(int room)
	{
	BufferedImage bufferImage = new BufferedImage(roomWidth, roomHeight, BufferedImage.TYPE_INT_RGB);
	Graphics2D g2 = bufferImage.createGraphics();
	int rgb = 0, r = 0, g = 0, b = 0;
	int xcheck = 0, ycheck = 0;
	
		currentRoom = room;	
		clearPriScreenInt();
		clearPriImages();					
		g2.drawImage(roomControlImage[room], 0, 0, this);
			
			while (ycheck < roomHeight) // for control and event
			{
				rgb = bufferImage.getRGB(xcheck,ycheck);
				r = (rgb >> 16) & 0xff;
				g = (rgb >> 8) & 0xff;
				b = rgb & 0xff;			
				
					if (r == 0 && g == 0 && b == 0) // black
					{		
						controlScreen[(ycheck * roomWidth) + xcheck] = black;
					}
					if (r == 255 && g == 255 && b == 255) // white
					{		
						controlScreen[(ycheck * roomWidth) + xcheck] = white; 						
					}
					if (r == 255 && g == 0 && b == 0) // red
					{
						controlScreen[(ycheck * roomWidth) + xcheck] = red; 					
					}	
					if (r == 255 && g == 255 && b == 0) // yellow
					{
						controlScreen[(ycheck * roomWidth) + xcheck] = yellow; 					
					}	
					if (r == 0 && g == 255 && b == 0) // green
					{
						controlScreen[(ycheck * roomWidth) + xcheck] = green; 					
					}	
					if (r == 0 && g == 255 && b == 255) // lightBlue
					{
						controlScreen[(ycheck * roomWidth) + xcheck] = lightBlue; 					
					}	
					if (r == 0 && g == 0 && b == 255) // blue
					{
						controlScreen[(ycheck * roomWidth) + xcheck] = blue; 					
					}
					if (r == 255 && g == 0 && b == 255) // purple
					{
						controlScreen[(ycheck * roomWidth) + xcheck] = purple; 
					}																																						
				xcheck++;
				
					if (xcheck > roomWidth - 1)
					{
						xcheck = 0;
						ycheck++;							
					}
			}			
						
		getPriorityScreens(room);
		g2.dispose();		
	}
	void getPriorityScreens(int room)
	{
	int count = 0;
	int wholePriorityScreen[];
	int wholeRoomScreen[];
	int xcheck = 0, ycheck = 0;
	int rgb = 0, r = 0, g = 0, b = 0;
	
		wholePriorityScreen = getData(roomPriorityImage[room]);
		
		wholeRoomScreen = new int[roomWidth * roomHeight];	
		wholeRoomScreen = getData(roomImage[room]);			
										
				while (ycheck < roomHeight)
				{
					rgb = wholePriorityScreen[(ycheck * roomWidth) + xcheck];
					r = (rgb >> 16) & 0xff;
					g = (rgb >> 8) & 0xff;
					b = rgb & 0xff;
						
						if (r == 0 && g == 0 && b == 0)
						{
							priorityScreen[0][(ycheck * roomWidth) + xcheck] = wholeRoomScreen[(ycheck * roomWidth) + xcheck];																
						}
						if (r == 0 && g == 0 && b == 198)
						{
							priorityScreen[1][(ycheck * roomWidth) + xcheck] = wholeRoomScreen[(ycheck * roomWidth) + xcheck];
						}
						if (r == 0 && g == 0 && b == 255)
						{
							priorityScreen[2][(ycheck * roomWidth) + xcheck] = wholeRoomScreen[(ycheck * roomWidth) + xcheck];								
						}
						if (r == 0 && g == 198 && b == 0)
						{
							priorityScreen[3][(ycheck * roomWidth) + xcheck] = wholeRoomScreen[(ycheck * roomWidth) + xcheck];							
						}	
						if (r == 0 && g == 198 && b == 198)
						{
							priorityScreen[4][(ycheck * roomWidth) + xcheck] = wholeRoomScreen[(ycheck * roomWidth) + xcheck];							
						}
						if (r == 0 && g == 255 && b == 0)
						{
							priorityScreen[5][(ycheck * roomWidth) + xcheck] = wholeRoomScreen[(ycheck * roomWidth) + xcheck];							
						}	
						if (r == 0 && g == 255 && b == 255)
						{
							priorityScreen[6][(ycheck * roomWidth) + xcheck] = wholeRoomScreen[(ycheck * roomWidth) + xcheck];							
						}																										
						if (r == 132 && g == 0 && b == 0)
						{
							priorityScreen[7][(ycheck * roomWidth) + xcheck] = wholeRoomScreen[(ycheck * roomWidth) + xcheck];							
						}		
						if (r == 132 && g == 132 && b == 132)
						{
							priorityScreen[8][(ycheck * roomWidth) + xcheck] = wholeRoomScreen[(ycheck * roomWidth) + xcheck];							
						}
						if (r == 198 && g == 0 && b == 0)
						{
							priorityScreen[9][(ycheck * roomWidth) + xcheck] = wholeRoomScreen[(ycheck * roomWidth) + xcheck];							
						}
						if (r == 198 && g == 0 && b == 198)
						{
							priorityScreen[10][(ycheck * roomWidth) + xcheck] = wholeRoomScreen[(ycheck * roomWidth) + xcheck];
						}
						if (r == 198 && g == 198 && b == 198)
						{
							priorityScreen[11][(ycheck * roomWidth) + xcheck] = wholeRoomScreen[(ycheck * roomWidth) + xcheck];							
						}
						if (r == 255 && g == 66 && b == 66)
						{
							priorityScreen[12][(ycheck * roomWidth) + xcheck] = wholeRoomScreen[(ycheck * roomWidth) + xcheck];							
						}
						if (r == 255 && g == 66 && b == 255)
						{
							priorityScreen[13][(ycheck * roomWidth) + xcheck] = wholeRoomScreen[(ycheck * roomWidth) + xcheck];							
						}
						if (r == 255 && g == 255 && b == 0)
						{
							priorityScreen[14][(ycheck * roomWidth) + xcheck] = wholeRoomScreen[(ycheck * roomWidth) + xcheck];							
						}
						if (r == 255 && g == 255 && b == 255)
						{
							priorityScreen[15][(ycheck * roomWidth) + xcheck] = wholeRoomScreen[(ycheck * roomWidth) + xcheck];
						}
						else
						{
							//priorityScreen[0][(ycheck * roomWidth) + xcheck] = wholeRoomScreen[(ycheck * roomWidth) + xcheck];
							priorityScreen[0][(ycheck * roomWidth) + xcheck] = 0;							
						}																																	
						
					xcheck++;
					
						if (xcheck > roomWidth - 1)
						{
							xcheck = 0;
							ycheck++;
						}
				}	
		convertData(wholeRoomScreen);				
	}
	void clearPriScreenInt()
	{
	int check = 0;
	int x = 0, y = 0;
		
			while (check < maxPriBands)
			{
				System.arraycopy(emptyScreen, 0, priorityScreen[check], 0, roomWidth * roomHeight);
				check++;
			}
			
		System.arraycopy(emptyScreen, 0, controlScreen, 0, roomWidth * roomHeight);				
	}
	void clearPriImages()
	{
	BufferedImage tempBlank = new BufferedImage(roomWidth, roomHeight, BufferedImage.TYPE_INT_RGB);
	Graphics2D g2 = tempBlank.createGraphics();
	int check = 0;
		
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, roomWidth, roomHeight);
	
			while (check < maxPriBands)
			{
				priScreen[check] = tempBlank;	
				check++;
			}
		g2.dispose();
	}
	int[] getData(Image source)
	{
	BufferedImage sourceScreen = new BufferedImage(roomWidth, roomHeight, BufferedImage.TYPE_INT_ARGB);
	Graphics2D canvas = sourceScreen.createGraphics();
	int xcheck = 0, ycheck = 0; 
	int returnData[];
	
		canvas.drawImage(source, 0, 0, this);
		returnData = new int[roomWidth * roomHeight];
		
			while (ycheck < roomHeight)
			{				
				returnData[(ycheck * roomWidth) + xcheck] = sourceScreen.getRGB(xcheck, ycheck);							
				xcheck++;
																	
					if (xcheck > roomWidth - 1)
					{
						xcheck = 0;
						ycheck++;
					}				
			}
		canvas.dispose();	
		return returnData;	
	}
	void convertData(int roomImage[]) // converts priority data to images[]
	{
	int check = 0;		
				
			while (check < maxPriBands)
			{
				priScreen[check] = createImage(new MemoryImageSource(roomWidth, roomHeight, ColorModel.getRGBdefault(), priorityScreen[check], 0, roomWidth));
				check++;
			}
	}
	boolean canMove(Player PlayerCheck, int direction)
	{
	int checking = 0;
	boolean cando = true;
	
	int xcheck = 0, ycheck = 0;
	
			switch(direction)
			{
				case up:

					xcheck = PlayerCheck.topLeftX;
					
						while (xcheck < PlayerCheck.bottomRightX)
						{
								if (controlScreen[((PlayerCheck.topLeftY - 1) * roomWidth) + xcheck] == white)
								{
									cando = false;
								}
							xcheck++;
						}					
													
				break;
				case down:
					
						xcheck = PlayerCheck.topLeftX;
						
							while (xcheck < PlayerCheck.bottomRightX)
							{
									if (controlScreen[((PlayerCheck.bottomRightY + 1) * roomWidth) + xcheck] == white)
									{
										cando = false;
									}								
								xcheck++;
							}		
													
				break;
				case left:
					
						ycheck = PlayerCheck.topLeftY;
						
							while (ycheck < PlayerCheck.bottomRightY)
							{
									if (controlScreen[(ycheck * roomWidth) + PlayerCheck.topLeftX - 1] == white)
									{
										cando = false;
									}								
								ycheck++;
							}						
				
				break;
				case right:
					
						ycheck = PlayerCheck.topLeftY;
						
							while (ycheck < PlayerCheck.bottomRightY)
							{
									if (controlScreen[(ycheck * roomWidth) + PlayerCheck.bottomRightX + 1] == white)
									{
										cando = false;
									}									
								ycheck++;
							}						
								
				break;
			}
			if (cando == false)
			{
				return false;
			}
			else
			{
				return true;
			}
	}
	int returnRoomEventState(Player playerCheck)
	{
	int returnValue = black;
	int topLeftX = playerCheck.topLeftX, topLeftY = playerCheck.topLeftY;
	int bottomRightX = playerCheck.bottomRightX, bottomRightY = playerCheck.bottomRightY;
	int checkerX = topLeftX, checkerY = topLeftY;
		
			while(checkerY < bottomRightY + 1)
			{
					if (controlScreen[(checkerY * roomWidth) + checkerX] > 1)
					{
						roomEventState = controlScreen[(checkerY * roomWidth) + checkerX];					
						returnValue = roomEventState;
						//break;	
					}
				checkerX++;
				
					if (checkerX > bottomRightX - 1)
					{
						checkerX = topLeftX;
						checkerY++;
					}
			}			
			
		return returnValue;	
	}
	void Paint (Graphics g)
	{	
		if (debugViewControl == false)
		{
			g.drawImage(roomImage[currentRoom], 0, 0, this);		
		}
		else
		{
			g.drawImage(roomControlImage[currentRoom], 0, 0, this);
		}
	}
	void PaintPriorityBand(Graphics g, int band)
	{
		g.drawImage(priScreen[band], 0, 0, this);
	}
}