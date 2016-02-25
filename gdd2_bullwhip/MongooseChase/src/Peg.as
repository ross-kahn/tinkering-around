package  {
	
	import flash.display.MovieClip;
	import flashx.textLayout.conversion.PlainTextExporter;
	import flash.events.KeyboardEvent;
	import flash.ui.Keyboard;
	import flash.events.Event;
	import net.flashpunk.Entity;
	import net.flashpunk.FP;
	import net.flashpunk.graphics.Spritemap;
	import net.flashpunk.utils.Input;
	
	
	public class Peg extends Entity 
	{		
		[Embed(source="/../assets/peg.png")] private const PEG:Class;
		public var spr:Spritemap = new Spritemap(PEG, 32, 32);
		
		
		private var startPosY:Number;
		private var player:Player;
		private var jumping:Boolean;
		private var swinging:Boolean;
		private var curAngle:Number;
		private var curRadius:Number;
		
		public function Peg(x:uint, y:uint, player:Player) {
			this.x = x;
			this.y = y;
			
			this.player = player;
			
			spr.add("idle", [ 0 ], 0, true);
			spr.add("hover", [ 1 ], 0, true);
			spr.add("outOfRange", [2], 0, true);
			spr.scale = MyWorld.difficulty;
			this.graphic = spr;
			
			setHitbox(32 * MyWorld.difficulty, 32 * MyWorld.difficulty);
			type = "obstacle";
			
			
		}

		
		public function inRange(x1:uint, x2:uint):Boolean
		{
			
			return (x1 < x2 && x2 - x1 <= player.WHIP_RANGE);
		}

		public function findDistance(x1:Number, y1:Number, x2:Number, y2:Number):Number{
			var xVal:Number = (x2 - x1) * (x2 - x1);
			var yVal:Number = (y2 - y1) * (y2 - y1);
			
			return Math.sqrt(xVal + yVal);
		}
		
		public function jump():void
		{
			player.y -= 10;
			player.yVelocity -= 10;
			player.xVelocity += 12;
		}

		public function doJump():void {	
			
			if(player.y > this.y){
				player.y -= 3;
			}else{
				curAngle = 1;
				curRadius = findDistance(player.x, player.y, this.x, this.y);
				jumping = false;				
				swinging = true;
			}
			updateRope();
			
		}		
		
		public function doSwing():void{
			updateRope();
			var radian:Number = (curAngle * Math.PI / 180) % (2 * Math.PI);
			player.x = this.x + Math.sin(radian) * curRadius;
			player.y = this.y + Math.cos(radian) * curRadius;
			curAngle += 3;
			if(Math.floor(radian) == 1){
				swinging = false;
			}
		}
		
		public function updateRope():void{
			//FP.buffer.clear();
			//graphics.lineStyle(3, 0x996633);
			//graphics.moveTo(player.x, player.y - 25);
			//graphics.lineTo(peg.x, peg.y);
		}
		
		
		public override function update():void
		{
			var inRangeY:Boolean = inRange(player.y, this.y);
			
			if(inRange)
				spr.play("idle");
			else
				spr.play("outOfRange");
			
			if (collidePoint(x, y, FP.world.mouseX, FP.world.mouseY) && !swinging && !jumping) 
			{
				if (inRange && player.x < (this.x - 150))
				{
					spr.play("hover");
					if (Input.mousePressed)
					{						
						jump();
					}
				}
			}
			
			
			//if (jumping)
				//doJump();
			//else if (swinging)
				//doSwing();
			
		}
	}
}
