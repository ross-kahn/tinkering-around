package  {
	
	import flash.display.MovieClip;
	import flashx.textLayout.conversion.PlainTextExporter;
	import flash.events.KeyboardEvent;
	import flash.ui.Keyboard;
	import flash.events.Event;
	
	
	public class Swinging extends MovieClip {
		
		private var jumping:Boolean;
		private var swinging:Boolean;
		
		private var curAngle:Number;
		private var ropeLength:Number = 100;
		
		private var xVeloc:Number;
		private var accel:Number;
		
		public function Swinging() {
			// constructor code
			stage.addEventListener(KeyboardEvent.KEY_DOWN, doMotion);
			//startPosY = player.y;
			xVeloc = 5;
			accel = .5;
		}
		
		public function doMotion(e:KeyboardEvent):void{
			if( e.keyCode == Keyboard.RIGHT){
				player.x += 5;
			}else if(e.keyCode == Keyboard.LEFT){
				player.x -= 5;
			}else if(e.keyCode == Keyboard.SPACE){
				trace(findDistance(player.x, player.y, peg.x, peg.y));
				trace("player: " + player.x + "," + player.y);
				trace("peg: " + peg.x + "," + peg.y + "\n");
					if( (findDistance(player.x, player.y, peg.x, peg.y) < 180) && (player.x < peg.x - 20) ){
					swing();
				}
			}
		}

		public function findDistance(x1, y1, x2, y2):Number{
			var xVal:Number = (x2 - x1) * (x2 - x1);
			var yVal:Number = (y2 - y1) * (y2 - y1);
			
			return Math.sqrt(xVal + yVal);
		}
		
		public function swing():void{
			//jump = true;
			stage.removeEventListener(KeyboardEvent.KEY_DOWN, doMotion);
			
			// FIND CURRENT ANGLE
			var yA = ropeLength * Math.cos(3 * Math.PI / 4);
			var xA = ropeLength * Math.sin(3 * Math.PI / 4);
			
			player.y = peg.y + yA;
			player.x = peg.x + xA;
			curAngle = Math.atan2(player.y, -xA);
			trace("Curangle: " + (curAngle * 180 / Math.PI));
			addEventListener(Event.ENTER_FRAME, doSwing);
		}
		
		public function doSwing(event:Event):void{
			
			player.x = peg.x - Math.sin(curAngle) * ropeLength;
			player.y = peg.y - Math.cos(curAngle) * ropeLength;
			curAngle = (curAngle + .1) % (2 * Math.PI);
			updateRope();
			trace(curAngle);
			//removeEventListener(Event.ENTER_FRAME, doSwing);
			var stopAngle:Number = Math.PI / 4;
			trace("STOPANGLE " + stopAngle);
			if(curAngle == stopAngle){
				removeEventListener(Event.ENTER_FRAME, doSwing);
				//player.y = stage.height;
			}
			
		}
		
		public function updateRope():void{
			graphics.clear();
			graphics.lineStyle(3, 0x996633);
			graphics.moveTo(player.x, player.y);
			graphics.lineTo(peg.x, peg.y);
		}
	}
}
