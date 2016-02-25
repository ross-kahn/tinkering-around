package  
{
	import flash.display.Graphics;
	import flash.text.engine.TypographicCase;
	import net.flashpunk.Entity;
	import net.flashpunk.Graphic;
	import net.flashpunk.graphics.Image;
	import net.flashpunk.graphics.Text;
	import net.flashpunk.World;
	import net.flashpunk.FP;
	import net.flashpunk.utils.Input;
	import net.flashpunk.Sfx;
	
	/**
	 * ...
	 * @author People
	 */
	public class MyWorld extends World
	{		
		public static var difficulty:uint = 2;
		public var distance:uint = 0;
		
		public const END_DISTANCE:uint = 24000;
		public const GLORY_TIME:uint = 5;
		
		public const PERIOD_AVERAGE:uint 	 = 1.5;
		public const PERIOD_VARIATION:Number = .4;
		
		public const ON_GROUND:uint = Main.SCREEN_HEIGHT - 56;
		
		public var time:Number = 0;
		public var nextPeriod:Number = 0;
		
		public var player:Player;
		public var lastPlayerX:Number;
		
		public var hunter:ChasingEnemy;
		
		public var blocks:Vector.<Block>;
		private var foremostBlock:uint = 0;
		public var treez:Vector.<Treez>;
		private var foremostTree:uint = 0;
		
		public var enemies:Vector.<Enemy>;
		public var traps:Vector.<Trap>;
		public var whipSegments:Vector.<Graphic>;
		public var enemiesSpawned:int = 0;
		
		[Embed(source = "../assets/mongooseHead.gif")] public const HEAD:Class;
		public var playerHead:Image = new Image(HEAD);
		
		[Embed(source = "../assets/hunterHead.png")] public const HEAD2:Class;
		public var enemyHead:Image = new Image(HEAD2);
		
		[Embed(source = "../assets/snake_segment.PNG")] private const SNAKE_SEGMENT:Class;
		[Embed(source = "../assets/snake_face.PNG")] private const SNAKE_HEAD:Class;
		
		[Embed(source = "../assets/hunter dead.PNG")] private const ENEMY_DEAD:Class;
		
		[Embed(source = "../assets/sound/whip.mp3")] private const WHIP:Class;
		public var whipSound:Sfx = new Sfx(WHIP);
		
		[Embed(source = "../assets/sound/Amazing Plan.mp3")] private static const AMAZING_PLAN:Class;
		public static var soundtrack:Sfx = new Sfx(AMAZING_PLAN, playSoundtrack);
		
		public function MyWorld() 
		{
			enemies = new Vector.<Enemy>();
			traps = new Vector.<Trap>();
			treez = new Vector.<Treez>();
			whipSegments = new Vector.<Graphic>;
			
			for (i = 0; i < 4; i++)
			{
				var tree:Treez = new Treez();
				tree.x = i * 400;
				//tree.y = FP.height - 174 - 32;
				//tree.height = FP.height;
				treez.push(add(tree));		
				foremostTree = tree.x;
			}
			
			player = new Player();
			player.x = 350;
			player.y = 400;
			lastPlayerX = player.x;			
			add(player);
			
			blocks = new Vector.<Block>();
			for (var i:int = 0; i < 40; i++)
			{
				var block:Block = new Block(i, (600 / 32));
				blocks.push(add(block));
				foremostBlock = block.x;
			}
			
			playerHead.x = 0;
			playerHead.y = FP.height - 16;
			addGraphic(playerHead);
			
			enemyHead.x = 0;
			enemyHead.y = FP.height - 16;
			addGraphic(enemyHead);
			
			hunter = new ChasingEnemy(player.x - 500, FP.height - 16, player);
			hunter.y -= hunter.height;
			add(hunter);
			
			playSoundtrack();
		}
		
		public static function playSoundtrack():void
		{
			
			soundtrack.stop();
			soundtrack.play(.5);		
		}
		
		
		public override function update():void
		{
			super.update();
			
			//enemy/trap/peg/placement
			time += FP.elapsed;
			
			if ( whipSegments.length > 0 ) {
				player.graphic = player.spr_no_snake;
				whipSegments.pop().visible = false;
				for each (var segment:Graphic in whipSegments) {
					segment.x += player.xVelocity;
				}
			} else {
				player.graphic = player.spr;
			}
			
			if (distance < END_DISTANCE)
			{	
			
				if (time >= nextPeriod)
				{
					nextPeriod = time + PERIOD_AVERAGE + PERIOD_VARIATION * (2 * FP.random - 1) - PERIOD_VARIATION * distance / END_DISTANCE;
					
					var randChoice:Number = FP.random;
					
					
					if (randChoice <= .40)
					{
						if (traps.length < 5)
						{
						var t:Trap = new Trap(player.x + FP.width, FP.height - 16, player);
						t.y -= t.height;
						traps.push(t);
						add(t);
						}
					}
					else if (randChoice <= .72)
					{
						if(enemies.length < 5){
						var e:Enemy = new Enemy(player.x + FP.width, FP.height - 16, player);
						e.y -= e.height;
						if ( difficulty == 1 ) {
							e.y += 50;
						}
						enemies.push(e);
						add(e);
						}
					}
					else
					{
						add(new Peg(player.x + FP.width, ON_GROUND - 250, player));					
					}
				}
			}
			
			
			if (Input.mousePressed && whipSegments.length == 0) {
				animateWhip();
			}
			
			
			//quick hack -- move the floor with the player
			var distMoved:Number = player.x - lastPlayerX;
			lastPlayerX = player.x;
			
			if (distance > END_DISTANCE)
			{
				FP.world = new WinWorld();
			}
			
			distance += distMoved;
			
			playerHead.x = camera.x + FP.width * distance / END_DISTANCE - 10;
			
			enemyHead.x = camera.x + FP.width * (distance - (player.x - hunter.x)) / END_DISTANCE - 10;
			
			for each (var block:Block in blocks)
			{
				if (block.x < FP.camera.x - block.width)
				{
					block.x = foremostBlock = foremostBlock + block.width;			
				}
			}
			
			for (var x:int = enemies.length - 1; x >= 0; x-- )
			{
				if (enemies[x].toRemove)
				{
					var enemy_dead_graphic:Image = new Image(ENEMY_DEAD);
					enemy_dead_graphic.x = enemies[x].x;
					enemy_dead_graphic.y = 517;
					addGraphic(enemy_dead_graphic);
					
					remove(enemies[x])
					enemies.splice(x, 1);
				}
				else if (enemies[x].x < FP.camera.x - enemies[x].width - 1)
				{
					remove(enemies[x]);
					enemies.splice(x, 1);
				}	
			}
			
			for (var y:int = traps.length - 1; y >= 0; y--)
			{
				if (traps[y].toRemove)
				{
					remove(traps[y])
					traps.splice(y, 1);
				}
				else if (traps[y].x < FP.camera.x - traps[y].width - 1)
				{
					remove(traps[y]);
					traps.splice(y, 1);
				}	
			}
			
			
			for (var i:uint = 0; i < treez.length; i++)
			{
				if (treez[i].x < FP.camera.x - 400)
				{
					treez[i].x = foremostTree = foremostTree + 400;
				}
				
			}
			
		}
		
		// Draw a whip from the player to the mouse cursor when it is clicked
		private function animateWhip():void {			
			
			// x, y of the mouse in world coords
			var mouseX:Number = FP.world.mouseX;
			var mouseY:Number = FP.world.mouseY;
			
			// x,y of the player's hand coordinates
			var startWhipX:Number = player.x + 105;
			var startWhipY:Number = player.y + 50;
			
			// get the distance from the player's hand to the point that was clicked
			var distance:Number = FP.distance(startWhipX, startWhipY, mouseX, mouseY);
			
			// get the angle (degrees) between the two points
			var angle:Number = FP.angle(startWhipX, startWhipY, mouseX, mouseY);
			
			// number of segments to draw from the hand to the click point
			var numSegments:int = distance / 25; // 25 is the width of a segment
			
			// draw each segment
			for ( var i:Number = 0; i < numSegments; i++ ) {
				var snakeSegment:Image = new Image(SNAKE_SEGMENT);
				snakeSegment.x = startWhipX + i * (25 * Math.cos(angle * FP.RAD));
				snakeSegment.y =  startWhipY + i * (25 * Math.sin(angle * FP.RAD));
				snakeSegment.angle = angle - 90;
				addGraphic(snakeSegment);
				whipSegments.push(snakeSegment);
			}
			
			// draw the head of the snake whip
			var snakeHead:Image = new Image(SNAKE_HEAD);
			snakeHead.x = startWhipX + (numSegments - 1) * (25 * Math.cos(angle * FP.RAD));
			snakeHead.y =  startWhipY + (numSegments - 1) * (25 * Math.sin(angle * FP.RAD));
			snakeHead.angle = angle - 90;
			addGraphic(snakeHead);
			whipSegments.push(snakeHead);
			
			whipSound.play();
		}
		
		
	}

}