package 
{
	import net.flashpunk.Engine;
	import net.flashpunk.FP;
	
	/**
	 * ...
	 * @author People
	 */
	public class Main extends Engine 
	{
		public static var SCREEN_WIDTH:Number = 800;
		public static var SCREEN_HEIGHT:Number = 600;
		public static var FPS:Number = 60;
		
		public function Main():void 
		{
			super(SCREEN_WIDTH, SCREEN_HEIGHT, FPS, false);
			//FP.world = new MyWorld();
		}
		override public function init():void {
			FP.screen.color = 0x222233;
			FP.world = new StartWorld();
		}
	}
	
}