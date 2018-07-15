import java.awt.*;
import java.applet.*;
import java.net.*;

public class Sokoban extends Applet implements Runnable
{
private Image dbImage;
private Graphics dbg;

static final int up = 0;
static final int down = 1;
static final int left = 2;
static final int right = 3;

static final int blank = 0;
static final int nextLevel = 1;
static final int outOfMoves = 2;
static final int endGame = 3;

guiClass gameGui;
Image guiDisplay;

levelHandler gameLevelHandler;
Image blankSquare, brickWall, floor, floorHole;

Player gamePlayer;
Image player, boulder, diamond;

boulderHandler gameBoulders;

      public void init() // all marked 'copy' need to be done every time a level change
      {      		
      	System.out.println("Init");	      	      
	      	      
	      blankSquare = getImage(getCodeBase(),"blankSquare.gif");
	      brickWall = getImage(getCodeBase(),"brickSquare.gif");
	      floor = getImage(getCodeBase(), "floorSquare.gif");
	      floorHole = getImage(getCodeBase(), "holeSquare.gif");
	     	      
	      gameLevelHandler = new levelHandler(blankSquare, brickWall, floor, floorHole);
	      
	      player = getImage(getCodeBase(),"playerImage.gif");
	      gamePlayer = new Player(player);
	      gamePlayer.setPosition(gameLevelHandler.returnPlayerStartX(), gameLevelHandler.returnPlayerStartY()); // copy
	      
	      boulder = getImage(getCodeBase(), "boulder.gif");
	      diamond = getImage(getCodeBase(), "diamond.gif");
	      gameBoulders = new boulderHandler(boulder, diamond);	      
	      gameBoulders.getBoulders(gameLevelHandler.returnBouldersX(), gameLevelHandler.returnBouldersY(), gameLevelHandler.returnBouldersState());	  // copy     
	      gameBoulders.getHoles(gameLevelHandler.returnHolesX(), gameLevelHandler.returnHolesY()); // copy
	      
	      guiDisplay = getImage(getCodeBase(), "sokobanGUI.gif");
	      gameGui = new guiClass(guiDisplay);      
		   gameGui.setData(gameLevelHandler.returnLevelName(), gameLevelHandler.returnMaxGems(), gameLevelHandler.returnMaxMoves()); // copy
      }      
      public void start() 
      {
        	System.out.println("Start");
      	Thread th = new Thread(this);
	      th.start();
      } 
      public void run()
      {      
         System.out.println("Run");
	      Thread.currentThread().setPriority(Thread.MIN_PRIORITY); // not sure if necessary     
	      
	      	while(true)
		      {
		      	gameGui.checkMessages(gameLevelHandler.currentLevel);
		      	repaint();
		      }
      }     
      public void stop() 
      {
      	System.out.println("Stop");
      }
      public void destroy() 
      {
        	System.out.println("Destroy");      
      }
      public void paint (Graphics g) 
      {
			gameLevelHandler.drawLevel(g);
			gameBoulders.draw(g);
			gamePlayer.draw(g);
      	gameGui.draw(g);			
      }
      public void update (Graphics g)
      {
				if (dbImage == null)
				{				
				      dbImage = createImage (this.getSize().width, this.getSize().height);
				      dbg = dbImage.getGraphics ();				
				}      
								
			dbg.setColor (getBackground ());
			dbg.fillRect (0, 0, this.getSize().width, this.getSize().height);
			
			dbg.setColor (getForeground());
			paint (dbg);
			
			g.drawImage (dbImage, 0, 0, this);			
      }
      public boolean keyUp(Event e, int key)
      {
      	if (gameGui.message == 0)
	      {
	      		switch(key)
			      {
			      	case Event.UP:
				      	if (gameLevelHandler.canPlayerMove(gamePlayer.x, gamePlayer.y - 1) == true && 
					          gameBoulders.checkMoveBoulders(gamePlayer.x, gamePlayer.y, up, gameLevelHandler.returnTile(gamePlayer.x, gamePlayer.y - 2)) == true)
					          {
						      	gamePlayer.move(up);
							      gameGui.addMove();
							      
							      	if (gameBoulders.collectedGems(gamePlayer.x, gamePlayer.y) == true)
								      {
											gameGui.addGem();
								      }
						      }
				      	break;
			      	case Event.DOWN:
				      	if (gameLevelHandler.canPlayerMove(gamePlayer.x, gamePlayer.y + 1) && 
					          gameBoulders.checkMoveBoulders(gamePlayer.x, gamePlayer.y, down, gameLevelHandler.returnTile(gamePlayer.x, gamePlayer.y + 2)) == true)
					      	{
				      			gamePlayer.move(down);	
							      gameGui.addMove();						      		      
					      						      
							      	if (gameBoulders.collectedGems(gamePlayer.x, gamePlayer.y) == true)
								      {
											gameGui.addGem();
								      }
					      	}
				      	break;
			      	case Event.LEFT:
				      	if (gameLevelHandler.canPlayerMove(gamePlayer.x - 1, gamePlayer.y) && 
					          gameBoulders.checkMoveBoulders(gamePlayer.x, gamePlayer.y, left, gameLevelHandler.returnTile(gamePlayer.x - 2, gamePlayer.y)) == true)
					          {
				      			gamePlayer.move(left);	
							      gameGui.addMove();						      		      
					      							      
							      	if (gameBoulders.collectedGems(gamePlayer.x, gamePlayer.y) == true)
								      {
											gameGui.addGem();
								      }
					      	 }
				      	break;
			      	case Event.RIGHT:
				      	if (gameLevelHandler.canPlayerMove(gamePlayer.x + 1, gamePlayer.y) && 
					          gameBoulders.checkMoveBoulders(gamePlayer.x, gamePlayer.y, right, gameLevelHandler.returnTile(gamePlayer.x + 2, gamePlayer.y)) == true)
					          {
						      	gamePlayer.move(right);	
							      gameGui.addMove();						      
							      
							      	if (gameBoulders.collectedGems(gamePlayer.x, gamePlayer.y) == true)
								      {
											gameGui.addGem();
								      }						      
					      	 }	      
				      	break;				      				      				      
			      }
		      }
		      else
		      {
		      	if (key == Event.ENTER)
			      {		 
			      	if (gameGui.message == nextLevel)
				   	{
					   	if (gameLevelHandler.currentLevel < 5 - 1)
						   {
						   	gameLevelHandler.currentLevel++;
								gameLevelHandler.initLevelData();
								gameLevelHandler.changeLevel(gameLevelHandler.currentLevel);					   
						      gamePlayer.setPosition(gameLevelHandler.returnPlayerStartX(), gameLevelHandler.returnPlayerStartY());    
						      gameBoulders.getBoulders(gameLevelHandler.returnBouldersX(), gameLevelHandler.returnBouldersY(), gameLevelHandler.returnBouldersState());     
						      gameBoulders.getHoles(gameLevelHandler.returnHolesX(), gameLevelHandler.returnHolesY());	
							   gameGui.setData(gameLevelHandler.returnLevelName(), gameLevelHandler.returnMaxGems(), gameLevelHandler.returnMaxMoves());
				      		gameGui.message = 0;
					      }
					      else
					      {
					      	gameGui.message = endGame;
					      }
				      }
			      	if (gameGui.message == outOfMoves)
				   	{
							gameLevelHandler.initLevelData();
							gameLevelHandler.changeLevel(gameLevelHandler.currentLevel);					   
					      gamePlayer.setPosition(gameLevelHandler.returnPlayerStartX(), gameLevelHandler.returnPlayerStartY());     
					      gameBoulders.getBoulders(gameLevelHandler.returnBouldersX(), gameLevelHandler.returnBouldersY(), gameLevelHandler.returnBouldersState());     
					      gameBoulders.getHoles(gameLevelHandler.returnHolesX(), gameLevelHandler.returnHolesY()); 	
						   gameGui.setData(gameLevelHandler.returnLevelName(), gameLevelHandler.returnMaxGems(), gameLevelHandler.returnMaxMoves());
			      		gameGui.message = 0;
				      }
				      // end game just leaves hanging				      
			      }
		      }
      	return false;
      }
}	