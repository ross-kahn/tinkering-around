package  
{
	import net.flashpunk.graphics.Image;
	import net.flashpunk.graphics.Text;
	import net.flashpunk.World;
	import net.flashpunk.FP;
	import net.flashpunk.utils.Input;
	/**
	 * ...
	 * @author Taylor Nycum
	 */
	public class TutorialWorld extends World
	{
		[Embed(source="../assets/help screen.PNG")] private const TUTORIAL_WORLD:Class;
		
		public function TutorialWorld() 
		{
			var i:Image = new Image(TUTORIAL_WORLD);
			addGraphic(i);
		}
		
		public override function update():void
		{
			if (Input.mousePressed)
			{
				FP.world = new MenuWorld();	
			}			
		}
		
	}

}