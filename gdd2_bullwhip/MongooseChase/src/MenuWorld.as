package  
{
	import net.flashpunk.graphics.Text;
	import net.flashpunk.World;
	import net.flashpunk.FP;
	import net.flashpunk.utils.Input;
	import net.flashpunk.utils.Key;
	import flash.system.System;
	
	/**
	 * ...
	 * @author Nathan Popham
	 */
	public class MenuWorld extends World
	{
		
		public function MenuWorld() 
		{
			addGraphic(new Text("1 - Easy\n\n2 - Hard\n\n3 - How To Play\n\nESC - Quit", FP.width / 2 - 50, FP.height / 2 - 50));
		}
		
		public override function update():void
		{
			if (Input.pressed(Key.DIGIT_1))
			{
				MyWorld.difficulty = 2;
				FP.world = new MyWorld();
				
			}
			else if (Input.pressed(Key.DIGIT_2))
			{				
				MyWorld.difficulty = 1;
				FP.world = new MyWorld();				
			} else if (Input.pressed(Key.DIGIT_3)) 
			{
				FP.world = new TutorialWorld();
			} else if (Input.pressed(Key.ESCAPE)) 
			{
				System.exit(0);
			}
			
		}
		
	}

}