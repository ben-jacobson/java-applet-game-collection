import java.applet.*;
import java.awt.*;

public class JetMan extends Applet implements Runnable
{ 
private Image dbImage;
private Graphics dbg;

Image[] backgrounds;
Image title, credits;
Image gameOver;
gui gameGui;

Image overlay;
score gameScore;

Image block1, block2, block3, block4;
level gameLevel;

Image stillLeft, stillRight, flyingCenterL, flyingCenterR, flyingLeft, flyingRight, 
      deadLeft, deadRight, walkingLeft, walkingRight;
player gamePlayer;
Image teleporter;

Image arachnoLeft, arachnoRight;
arachno[] gameArachno;

Image wingherdLeft, wingherdRight;
wingherd[] gameWingherd;

Image phillisLeft, phillisRight;
phillis[] gamePhillis;

Image gangroLeft, gangroRight;
Image plasma;
gangro[] gameGangro;

Image cybaLeft, cybaRight;
cyba[] gameCyba;

Image[] regulars;
int countRegs;
Image[] uniques;
int countUniq;
item[] items;

Image ammo, fuel;
item[] invent;

int levelNum;

boolean pressedUp, pressedLeft, pressedRight;
boolean pressedSpace;

	public void init() 
	{ 
	int place = 0;
	
		System.out.println(">> Init <<");
		
		levelNum = 1;
		
		title = getImage(getCodeBase(),"intro.gif");
		credits = getImage(getCodeBase(),"credits.gif");
		gameOver = getImage(getCodeBase(),"gameOver.gif");
		
		gameGui = new gui(title, credits, gameOver);
		
		backgrounds = new Image[5];
		
		backgrounds[0] = getImage(getCodeBase(),"level1Background.gif");
		backgrounds[1] = getImage(getCodeBase(),"level2Background.gif");
		backgrounds[2] = getImage(getCodeBase(),"level3Background.gif");
		backgrounds[3] = getImage(getCodeBase(),"level4Background.gif");
		backgrounds[4] = getImage(getCodeBase(),"level5Background.gif");	
		
		gameGui.getBackgrounds(backgrounds);							
		
		overlay = getImage(getCodeBase(),"hudOverLay.gif");
		gameScore = new score(overlay);
		
		stillLeft = getImage(getCodeBase(),"playerStillLeft.gif"); 
		stillRight = getImage(getCodeBase(),"playerStillRight.gif"); 
		flyingCenterL = getImage(getCodeBase(),"playerFlyingCenterLeft.gif"); 
		flyingCenterR = getImage(getCodeBase(),"playerFlyingCenterRight.gif"); 
		flyingLeft = getImage(getCodeBase(),"playerFlyingLeft.gif"); 
		flyingRight = getImage(getCodeBase(),"playerFlyingRight.gif"); 
		deadLeft = getImage(getCodeBase(),"playerDeadLeft.gif");
	     deadRight = getImage(getCodeBase(),"playerDeadRight.gif");
		walkingLeft = getImage(getCodeBase(),"playerWalkingLeft.gif");
	     walkingRight = getImage(getCodeBase(),"playerWalkingRight.gif");	
		teleporter = getImage(getCodeBase(),"teleporter.gif");	     
	     	     	
	     gamePlayer = new player(stillLeft, stillRight, flyingCenterL, 
	                             flyingCenterR, flyingLeft, flyingRight, deadLeft, 
	                             deadRight, walkingLeft, walkingRight, teleporter);
	                             
	     arachnoLeft = getImage(getCodeBase(),"arachnoLeft.gif");
	     arachnoRight = getImage(getCodeBase(),"arachnoRight.gif");
	     gameArachno = new arachno[10];
	     	     
			while (place < 10)
			{
	     		gameArachno[place] = new arachno(arachnoLeft,arachnoRight);	
			     place++;		
			}			
		place = 0;
	     
	     wingherdLeft = getImage(getCodeBase(),"wingHerdLeft.gif");
	     wingherdRight = getImage(getCodeBase(),"wingHerdRight.gif");
	     gameWingherd = new wingherd[10];
	     
			while (place < 10)
			{
	    	     	gameWingherd[place] = new wingherd(wingherdLeft,wingherdRight);				         		         
			     place++;				         	
			}	     
		place = 0;
	     	     
	     phillisLeft = getImage(getCodeBase(),"phillisLeft.gif");
	     phillisRight = getImage(getCodeBase(),"phillisRight.gif");
	     gamePhillis = new phillis[10];

			while (place < 10)
			{
			     gamePhillis[place] = new phillis(phillisLeft,phillisRight);			     			     
			     place++;				     		
			}	     
		place = 0;
	     
	     gangroLeft = getImage(getCodeBase(),"gangroLeft.gif");
	     gangroRight = getImage(getCodeBase(),"gangroRight.gif");
	     plasma = getImage(getCodeBase(),"plasmaBlob.gif");	     
	     gameGangro = new gangro[10];
	     
			while (place < 10)
			{     	     
			     gameGangro[place] = new gangro(gangroLeft, gangroRight, plasma);
			     place++;				     
	     	}
		place = 0;
		
		cybaLeft = getImage(getCodeBase(),"cyberLeft.gif");
		cybaRight = getImage(getCodeBase(),"cyberRight.gif");
		gameCyba = new cyba[10];
		
			while (place < 10)
			{
				gameCyba[place] = new cyba(cybaLeft, cybaRight);
				place++;
			}	
		
		regulars = new Image[5];
		uniques = new Image[5];
		items = new item[10];
		
		regulars[0] = getImage(getCodeBase(),"part1.gif");
		regulars[1] = getImage(getCodeBase(),"part2.gif");
		regulars[2] = getImage(getCodeBase(),"part3.gif");
		regulars[3] = getImage(getCodeBase(),"part4.gif");
		regulars[4] = getImage(getCodeBase(),"part5.gif");

		uniques[0] = getImage(getCodeBase(),"unique1.gif");
		uniques[1] = getImage(getCodeBase(),"unique2.gif");
		uniques[2] = getImage(getCodeBase(),"unique3.gif");
		uniques[3] = getImage(getCodeBase(),"unique4.gif");
		uniques[4] = getImage(getCodeBase(),"unique5.gif");
		
		countRegs = 0;
		countUniq = 5;										
		
			while (countRegs < 5)
			{
				items[countRegs] = new item(regulars[countRegs]);
				countRegs++;				
			} 
			
		countRegs = 0;
		
			while (countUniq < 10)
			{
				items[countUniq] = new item(uniques[countRegs]);
				countRegs++;
				countUniq++;
			}  		     		
		
		ammo = getImage(getCodeBase(),"laser.gif"); 
		fuel = getImage(getCodeBase(),"fuel.gif");
		invent = new item[10];
		place = 0;
		
			while (place < 10)
			{
				invent[place] = new item();
				place++;
			}
	
		block1 = getImage(getCodeBase(),"tile1.gif");
		block2 = getImage(getCodeBase(),"tile2.gif");
		block3 = getImage(getCodeBase(),"tile3.gif");
		block4 = getImage(getCodeBase(),"tile4.gif");
		gameLevel = new level(block1, block2, block3, block4, fuel, ammo); // done last to init player and all enemies
		
		gameLevel.setLevel(levelNum, gameArachno, gameWingherd, gamePhillis, gameGangro, gameCyba, items, invent, gameGui);
		gameLevel.dumpPlayerPosition(gamePlayer);		
	}
	
	public void start() 
	{ 
		System.out.println(">> Start <<");
		Thread th = new Thread(this);
		th.start();
	}

	public void stop() 
	{ 
		System.out.println(">> Stop <<");
	}
	
	public void destroy() 
	{ 
		System.out.println(">> Destroy <<");
	}
	
	public void run () 
	{ 
	int handlePlace = 0;
	int checkPlace = 0;
	
		System.out.println(">> Run <<");
		
			while (true)
			{ 
				// repaint the applet
				repaint();
				
					if (gameGui.gamePhase == 1)
					{
						gamePlayer.handle(gameLevel);
						gameScore.handle();
						
							while (handlePlace < 10)
							{
								gameLevel.handle(gamePlayer, gameLevel, gameArachno[handlePlace], gameWingherd[handlePlace], 
								                 gamePhillis[handlePlace], gameGangro[handlePlace], gameCyba[handlePlace], items[handlePlace],
								                 invent[handlePlace], gameScore); 
								                 
								handlePlace++; // dont worry, the gamePlayer isnt handled 11 times a cycle. only once
							}
							
						handlePlace = 0;				
							
							if (pressedLeft == true)
							{
								gamePlayer.left(gameLevel, gameScore);
							}
							if (pressedRight == true)
							{
								gamePlayer.right(gameLevel, gameScore);
							}
							if (pressedUp == true)
							{
								gamePlayer.up(gameLevel, gameScore);
							}
							if (pressedSpace == true)
							{
								if (gameScore.died == true)
								{
									gamePlayer.reset(gameScore);
								}
							}	
							if (gameScore.lives == -1)
							{
								gameGui.gamePhase = 2;
							}
							if (gamePlayer.gotThere == true)
							{
								levelNum++;
								gameLevel.setLevel(levelNum, gameArachno, gameWingherd, gamePhillis, gameGangro, gameCyba, items, invent, gameGui);
								gameLevel.dumpPlayerPosition(gamePlayer);
								gamePlayer.gotThere = false;									
							}												
					}
					try
					{ 
						// Stop thread for 25 milliseconds
						Thread.sleep (25);

					}
					catch (InterruptedException ex)
					{ 
						// do nothing
					}
//				Thread.currentThread().setPriority(Thread.MAX_PRIORITY);			
			}		
	}
	
	public void paint (Graphics g) 
	{ 
	int drawPlace = 0;
	
		System.out.println(">> Paint <<");	
		gameGui.draw(g);
		
			if (gameGui.gamePhase == 1)
			{	
				gameLevel.draw(g);
					
					while (drawPlace < 10)
					{
							if (gameArachno[drawPlace].active == true)
							{
								gameArachno[drawPlace].draw(g);
							}
							if (gameWingherd[drawPlace].active == true)
							{				
								gameWingherd[drawPlace].draw(g);
							}
							if (gamePhillis[drawPlace].active == true)
							{
								gamePhillis[drawPlace].draw(g);
							}
							if (gameGangro[drawPlace].active == true)
							{				
								gameGangro[drawPlace].draw(g);
								gameGangro[drawPlace].drawPlasma(g);
							}
							if (gameCyba[drawPlace].active == true)
							{
								gameCyba[drawPlace].draw(g);
								gameCyba[drawPlace].drawShot(g);
							}
							if (items[drawPlace].active == true)
							{
								items[drawPlace].draw(g);
							}
							if (invent[drawPlace].active == true)
							{
								invent[drawPlace].draw(g);
							}
						drawPlace++;
					}					
				
				gamePlayer.draw(g);	
				gameScore.draw(g);	
			}
	}
	
	public void update (Graphics g)
	{
		// initialize buffer
		
			if (dbImage == null)
			{
				dbImage = createImage (this.getSize().width, this.getSize().height);
				dbg = dbImage.getGraphics ();
			}

		// clear screen in background
		dbg.setColor (getBackground ());
		dbg.fillRect (0, 0, this.getSize().width, this.getSize().height);

		// draw elements in background
		dbg.setColor (getForeground());
		paint (dbg);

		// draw image on the screen
		g.drawImage (dbImage, 0, 0, this);
	}
	public boolean mouseDown(Event e, int x, int y)
	{
			if (gameGui.gamePhase == 0) // intro
			{
				gameGui.gamePhase = 1; // game
			}	
			if (gameGui.gamePhase == 2) // gameOver
			{
				init();
			}
		return true;
	}	
	public boolean keyUp(Event e, int key)
	{
			if (key == e.UP)
			{
				gamePlayer.stopUp(gameScore);
				pressedUp = false;
			}
			if (key == e.LEFT)
			{		
				gamePlayer.stopLeft(gameScore);
				pressedLeft = false;
			}
			if (key == e.RIGHT)
			{		
				gamePlayer.stopRight(gameScore);
				pressedRight = false;
			}	
			if (key == 32)
			{
					if (gameScore.died == false)
					{
						gamePlayer.shoot(gameScore);
					}			
				pressedSpace = false;
			}				
		return true;
	}
	public boolean keyDown(Event e, int key)
	{
			if (key == e.UP)
			{
				pressedUp = true;
			}
			if (key == e.LEFT)
			{
				pressedLeft = true;
			}
			if (key == e.RIGHT)
			{
				pressedRight = true;
			}
			if (key == 32)
			{
				pressedSpace = true;
			}	
			if (key == 'q')
			{
				if (levelNum < 5)
				{
					levelNum++;
					gameLevel.setLevel(levelNum, gameArachno, gameWingherd, gamePhillis, gameGangro, gameCyba, items, invent, gameGui);
					gameLevel.dumpPlayerPosition(gamePlayer);			
				}
				else
				{
					gameGui.gamePhase = 3;
				}
			}						
		return true;
	}

} 
