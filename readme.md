This is a collection of Java Applet games that I had built during the later part of high school. 

At the time I figured it would be worth learning Java in order to get learn a bit about web and mobile app development. Thankfully these days we have much better platforms for this sort of thing. I know Flash was a big thing at the time, but for some reason I wasn’t able to get my head around the workflows of it. Or maybe I lacked the patience. 

These are all Java Applets with a mixture of source code and compiled bytecode. These things use to run as little embedded canvas-like windows within your web browser (https://en.wikipedia.org/wiki/Java_applet). Java Applets have been deprecated, so I get the feeling the only way to get them running is to hack together a special config for them. One of these days, I’ll have to spin up a VM for it. perhaps running Windows XP with old versions of IE and Firefox w/ Java applets enabled.   

Here is a list of the games: 
- Asteroids - 2004 - A basic Asteroids clone, even kept the name
- JBall - 2004 - Breakout clone
- JetMan - 2004 - A sort-of clone of the dos game JetPack (https://classicreload.com/jetpack.html). Mine is much simpler platformer where you need to manage your fuel levels of your Jetpack, while you collect your ships spare parts to get off an alien planet (A bit like Commander Keen 1)
- Pong - 2004 - Pong clone
- Tetri - 2004 - Pretty self explanatory
- TetriII - 2005 - Sequal to above, witth smoother animations and better graphics
- Sokoban - 2008 - Another clone
- ADV - 2006 -  Unlike the above, this one is actually worth going into detail about. I spent some time developing a Sierra SCI style (http://sci.sierrahelp.com/Documentation/SCISpecifications/index.html, think Liesure Suit Larry 5, Space Quest 4, Police Quest 3, etc) point and click adventure engine. The engine took care of most of what you wanted to do in devloping such a game, it let you define objects, rooms, etc while scripting your own events in plain old Java. It had an icons manager (Move, Grab, etc) an inventory manager, an x-window style message drawer, and the room management system would allow you to define areas of the screen with draw priorities, giving you the ability to walk in-front of or behind areas of the screen. It also let you set up areas of the screen that would trigger events if walked on or interacted with via the mouse. Unfortunately, I didn’t plan a great deal before starting this project so a lot of the code feels “hacky”. You could probably develop a simple point-and-click game with it, but some of the more advanced features would probably need you to augment the engine code in some way. Eg, I doubt you could recreate Monkey Island with it easily, but perhap something like Hugo’s House of Horrors?. I love those games!







