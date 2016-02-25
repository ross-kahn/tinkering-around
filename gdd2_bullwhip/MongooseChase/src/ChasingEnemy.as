package  
{
	import net.flashpunk.graphics.Spritemap;
	import net.flashpunk.FP;
	
	/**
	 * ...
	 * @author ...
	 */
	public class ChasingEnemy extends Enemy 
	{
		private const minSpeed:Number = 4;
		private const increment:Number = .2 * (MyWorld.difficulty == 1 ? 2 : 1);
		private const focusDistance:Number = 150;
		[Embed(source="/../assets/chasehunters run.png")] private const CHASINGENEMY:Class;
		
		public function ChasingEnemy(posX:Number, posY:Number, player:Player) 
		{
			super(posX, posY, player);
			this.spr = new Spritemap(CHASINGENEMY, 100, 115);
			spr.scale = 1;
			speed = 0;
			
			spr.add("run", [ 0, 1, 2, 3 ], 20, true);
			this.graphic = spr;
			spr.play("run");
			
			setHitbox(70, 115);
		}
		
		override public function update():void {
			var distance:Number = FP.distance(x, y, player.x, player.y);
			
			
			speed = minSpeed + increment * (player.x - this.x) / focusDistance;
			// Catch up to the player if behind by a lot
			/*if ( distance >= 600) {
				speed = minSpeed * 1.1;
			} else if ( distance < 300 ) {
				speed = minSpeed * 0.95;
			} else {
				speed = minSpeed;
			}*/
			
			// Remove this comment as soon as traps are deleted as they pass the left
			// edge of the screen
			/*var obsCollision:Obstacle = collide("obstacle", x, y) as Obstacle;
			if (obsCollision) 
			{
				speed = 0;
			}*/
			
			x += speed;
		}
	}

}