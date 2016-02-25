// The scenario:
//----------------------------
//----------------------------
 
/*
 
Someone has written a really bad image format to store our avatars (they haven't really, but bear with us).
The format is a JavaScript object containing a 1d array of CSS-style hex strings and some additional metadata.
 
Use this format to complete the challenges below!
 
*/
 
// Here are the challenges:
//----------------------------
//----------------------------
 
/*
 
# Challenge 1: Write a function to print out the separate RGB values for each pixel in image1. 
(extra credit: use a module or library to output some standard image format representation [png/bmp/jpg] of the image)
 
# Challenge 2: Write a function which takes image1 and returns a copy rotated 90 degrees clockwise.
(extra credit: allow arbitrary rotation)
 
# Challenge 3: Write a function with the signature: (image1, image2, merge_amount), which returns an object 
in the format above with each pixel of image1 and image2 merged according to merge amount, such that 
merge_amount=0 is all image1, merge_amount=1 is all image2 and merge_amount=0.5 is a 50% mix of the two images. 
(extra credit: implement more than one blending mode)
 
*/
 
// The data for your challenges:
//----------------------------
//----------------------------
 
var image1      = {};
image1.width    = 12;
image1.height   = 12;
image1.data     = [ 'ffffff', 'ffff8a', '898943', '4343cb', 'cacaf3', 'f3f3fb', 'fafbfb', 'fbfbf3', 'f3f3e8', 'e9e9f0', 'f1f1ff', 'ffffff', 'fffffe', 'fefe6e', '6e6e01', '000005', '030286', '8686e9', 'eaebd7', 'd6d7e0', 'dfdfec', 'ededf4', 'f5f5ff', 'ffffff', 'ffffff', 'ffffc0', 'bfbf31', '343573', '7b7da3', 'a7a8f0', 'f2f3e0', 'e9ebe1', 'ecede5', 'e6e7fc', 'fcfcff', 'ffffff', 'ffffff', 'ffff91', '8b8b97', 'a6a960', '959d9e', 'bec098', '9596bf', 'e1e5a3', 'c7cce6', 'e9eafb', 'fbfbff', 'ffffff', 'fffffa', 'fafaa7', 'a4a55d', '61639f', 'bdbd66', '767311', '1a1675', '84839d', 'b3b1e3', 'e5e5fb', 'fbfcfc', 'fcfcf9', 'fafad6', 'd8d8e1', 'e5e4d4', 'cdd1bc', '435b5d', '10253b', '182048', '0010a3', '233ff5', 'f0f4ee', 'f2f1e9', 'eaeafe', 'fefef3', 'f3f4ef', 'f0f0f3', 'f5f7d7', 'b5b8ca', '537297', '003499', '2545c4', '9fa4fb', 'fffff8', 'f9f9f7', 'f7f7ff', 'ffffff', 'fffff8', 'f8f8fb', 'fbfbd1', 'd8d743', '565116', '211ccc', 'dfd9ff', 'ffffaa', 'a9a9d7', 'd7d7ff', 'ffffff', 'ffffff', 'fffffe', 'fefec6', 'c7c712', '101000', '00001a', '1818cb', 'cbcbe4', 'e4e51c', '1b1bb2', 'b2b2ff', 'ffffff', 'ffffff', 'ffffff', 'ffff85', '85851a', '181873', '71716b', '6a6a79', '77773b', '393905', '0304b5', 'b5b5ff', 'fffffe', 'fefefd', 'fdfde8', 'e9ead4', 'd4d5ae', 'adaeff', 'ffffff', 'fffff9', 'f9f922', '202000', '00009f', '9f9fff', 'fffff6', 'f6f7e7', 'e9e9d9', 'dadbd9', 'dadbda', 'dcddc9', 'cbcdca', 'cbcdd4', 'd5d6a3', 'a3a391', '9091dc', 'dcdcf9', 'ffffff' ];
 
var image2      = {};
image2.width    = 12;
image2.height   = 12;
image2.data     = [ 'ffffff', 'ffff8d', '8c8c44', '4444ca', 'c9caf3', 'f3f5fb', 'fbf6fb', 'fbf6fc', 'f8b4fc', 'f56afb', 'f7b4fe', 'ffffff', 'fffffe', 'fefe6f', '6d6d00', '000005', '040e8f', '8c3ffc', 'f75df0', 'e649f7', 'ef4dfd', 'f77ffc', 'fac2fe', 'ffffff', 'ffffff', 'ffffbe', 'bebe38', '343079', '7576b2', 'ab4cff', 'fa49fa', 'ef91fc', 'f28dfc', 'f359fd', 'fdf0ff', 'ffffff', 'ffffff', 'ffff8e', '8d93a5', '9d9491', '826dc2', 'bba69b', '9671dd', 'd5d3c7', 'bbbbf7', 'ef9ffd', 'fde1ff', 'ffffff', 'fffffd', 'fce6a9', 'a89069', '644bb2', 'b49b70', '726717', '1a1a7e', '8075ad', 'af8ceb', 'e89bfe', 'fde3fe', 'fdeefd', 'fbe9f3', 'e83efa', 'f241e0', 'd842a2', '56554d', '183231', '1a2a36', '002783', '2d55fc', 'f5adfe', 'fb8ffd', 'f66efe', 'fef9fb', 'f8bfff', 'f98dff', 'fe7cd3', 'b876ad', '596573', '0e5082', '2d58bc', '9f7dff', 'ff9aff', 'ffb0fc', 'fbcdff', 'ffffff', 'fffffd', 'fbd8ff', 'ffa4d4', 'd7884b', '54281b', '2015d8', 'e159ff', 'ff7bb0', 'ae4ad8', 'd8d0ff', 'ffffff', 'ffffff', 'ffffff', 'fed9ca', 'c98313', '121000', '00061c', '1911d8', 'd23beb', 'ea4821', '1f0cb2', 'b2b9ff', 'ffffff', 'fffffe', 'ffffff', 'ffbc8b', '89461b', '1a2776', '74736c', '6b6e80', '7e6d3e', '3c2806', '030ab5', 'b5b4ff', 'fffffe', 'fefefc', 'fcfefa', 'f471e7', 'df25b1', 'af76ff', 'ffffff', 'fffff6', 'f6fc24', '222700', '0000a1', 'a0a0ff', 'fffff7', 'f7f7e8', 'e9ede0', 'dec1e6', 'e294df', 'e0c1cb', 'cdd4cc', 'cecfd5', 'd6d7a3', 'a2a390', '8f90db', 'dbdcf9', 'ffffff' ];
 


//You can put your code below!
//----------------------------

//Challenge 1 and extra credit
convertAndPrintHexArray(image1.data);
drawClassDojoAvatar(image1);
drawClassDojoAvatar(image2);

//Challenge 2 and extra credit (only 90 degree rotations)
rotateImage(image1, 1);


//Challenge 3
mergeImages(image1, image2, .5);






// Data-holder object for an RGB pixel
function Pixel(red, green, blue){
	this.red = red;
	this.blue = blue;
	this.green = green;
}


/**
 * Prints out RGB values from a list of hex values.
 * @param data		Hex image data from a ClassDojo image
 * @returns			Array of RGB values
 */
function convertAndPrintHexArray(data){
	
	var curPixel;
	
	document.write("<p><strong>Image R/G/B</strong></br>");
	for(var i=0; i<data.length; i++)	{
		curPixel = hexToRGB( data[i] );
		document.write("R:" + curPixel.red + " G: " + curPixel.green + " B: " + curPixel.blue + "</br>");
	}
	document.write("</p>");
} 


/**
 * Takes a ClassDojo image object and rotates it clockwise by 90 degrees multiple
 * times, based on multiplierClockwiseRotation. If multiplierClockwiseRotation is
 * not defined, by default this function rotates only once.
 * @param image								ClassDojo image to rotate
 * @param multiplierClockwiseRotation		How many times to rotate
 * @returns {Array}							Hex array of the new rotated image's pixels
 */
function rotateImage(image, multiplierClockwiseRotation){
	
	// Only rotate once by default if a multiplier was not supplied
	if( multiplierClockwiseRotation == undefined){
		multiplierClockwiseRotation =1;
	}
	
	var newPixels = new Array(image.height);	// 2d array. Contains lists of Pixel objects
	var newColumn = new Array(image.width);		// Represents one row. Each pixel inside is the 'column'
	
	var origPixels = hexArrayToPNGArray(image);
	var n = origPixels.length;		// Size of the matrix. Must be n X n (a square image)
	
	for(var mult=0; mult<multiplierClockwiseRotation; mult++){
		newPixels = new Array(image.height);
		for( var x=0; x<n; x++){
			
			newColumn = new Array(image.width);
			newPixels[x] = newColumn;
			for( var y=0; y<n; y++){
				
				// Finds the proper pixel for the rotated image
				newPixels[x][y] = origPixels[n-y-1][x];
			}
		}
		origPixels = newPixels;
	}

	// Draws rotated image to html
	document.write('<img title="Rotated Avatar" src="data:image/png;base64,'+getImageBase64(newPixels, image)+'">');
	
	
	// Returns rotated data in ClassDojo hex string-array format
	return nestedRGBToHexArray(newPixels);
}


/**
 * Returns a hex array of pixel data where each pixel of image1 and image2 are 
 * merged according to merge amount, such that merge_amount=0 is all image1,
 * merge_amount=1 is all image2 and merge_amount=0.5 is a 50% mix of the two images. 
 * @param img1			First image to merge
 * @param img2			Second image to merge
 * @param mergeAmount	Amount the images are merged on a scale of 0 (all img1) to 1 (all img2)
 * @returns
 */
function mergeImages(img1, img2, mergeAmount){
	
	// Constructor takes height, width, and color-depth
	var img = new PNGlib(12, 12, 256);
	
	var n = img1.width; 	// Square size of the image
	
	var img1Pixels = hexArrayToPNGArray(img1);	// PNGArray for image 1
	var img2Pixels = hexArrayToPNGArray(img2);	// PNGArray for image 2
	var newPixels = new Array(n);				// Array for merged pixels
	
	var pixel1, pixel2;		// Current pixel for image1 and image 2, respectively
	
	var newRed, newGreen, newBlue;	// New red, green, and blue RGB value
	
	var image2Merge = mergeAmount;		// Merge multiplier for image 2
	var image1Merge = 1-mergeAmount;	// Merge multiplier for image 1
	
	
	
	// Iterate through the nested pixel array
	for (var x=0; x<n; x++){
		var newColumn = new Array(n);
		for(var y=0; y<n; y++){
			
			pixel1 = img1Pixels[y][x];	// retrieve pixel at current location in image
			pixel2 = img2Pixels[y][x];
			
			
			// Create new RGB values for the merged image
			newRed = Math.floor(((pixel1.red * image1Merge) + (pixel2.red * image2Merge)));
			newGreen = Math.floor(((pixel1.green * image1Merge) + (pixel2.green * image2Merge)));
			newBlue = Math.floor(((pixel1.blue * image1Merge) + (pixel2.blue * image2Merge)));
			
			//document.write("<p>"+ newRed +","+ newGreen +","+ newBlue +"</p>");
			
			// Insert the new pixel into the correct position in the current row
			newColumn[y] = new Pixel(newRed, newGreen, newBlue);
			
			// Set the pixel color in the image buffer at that location
			img.buffer[img.index(x, y)] = img.color(newRed, newGreen, newBlue);
			
			
		}
		
		// Put the newly constructed pixel row into the array of rows
		newPixels[x] = newColumn;
	}
	
	// Insert the image into the html
	drawMergedAvatar(img.getBase64());

	// Return the new pixel array, formatted in the ClassDojo hex string-array style
	return nestedRGBToHexArray(newPixels);
}

/**
 * Draws a ClassDojo-formatted image by converting the image to a .PNG and 
 * then writing it to an <img> block and inserting it into the html
 * @param image		ClassDojo-formatted image to draw
 */
function drawClassDojoAvatar(image){
	
	// Construct a nested pixel array of RGB values from hex array
	var pixels = hexArrayToPNGArray(image);
	
	document.write('<img title="ClassDojo Avatar" src="data:image/png;base64,'+getImageBase64(pixels, image)+'">');
}






// HELPER FUNCTIONS
// -----------------------------
// -----------------------------






/**
 * Takes a PNGArray of nested Pixel objects, flattens it, and converts the 
 * pixels to hex strings.
 * @param nestedArray	Nested array of pixels to flatten and convert to a hex array
 * @returns				Flat array of hex strings
 */
function nestedRGBToHexArray(nestedArray){

	var n = nestedArray.length;
	
	var hexArray = new Array(n*n);
	
	var flatIndex = 0;
	for(var row=0; row<nestedArray.length; row++){
		var data = nestedArray[row];
		for(var col=0; col<data.length; col++){
			hexArray[flatIndex] = RGBPixelToHex(nestedArray[row][col]);
			flatIndex++;
		}
	}
	
	return hexArray;
}
 


/**
 * Converts Pixels in a PNGArray of a ClassDojo image into a base64 string that
 * can be written to the document with an <img> tag
 * @param image			ClassDojo type image object
 * @param pixels		PNGArray of the image's data
 * @returns {String}	Base64 URL that can be used with an <img> tag		
 */
function getImageBase64(pixels, image){
	
	// Constructor takes height, width, and color-depth
	var img = new PNGlib(12, 12, 256);
	
	var curPixel;	// Current pixel to draw
	
	// Iterate through the nested pixel array
	for (var x=0; x<image.width; x++){
		for(var y=0; y<image.height; y++){
			curPixel = pixels[y][x];	// retrieve pixel at current location in image
			
			// Set the pixel color in the image buffer at that location
			img.buffer[img.index(x, y)] = img.color(curPixel.red, curPixel.green, curPixel.blue);
		}
	}
	return img.getBase64();
}

/**
 * Convert a flat array of hex strings into a nested array of RGB Pixels
 * @param image			ClassDojo type image object to convert
 * @returns {Array}		Nested array ([row][column]) of Pixels
 */
function hexArrayToPNGArray(image){

	var nestedPixelArray = new Array(image.height);	// 2d array. Contains lists of Pixel objects
	var curColumn = new Array(image.width);			// Represents one row. Each pixel inside is the 'column'
	
	var curHex = '';	// Hexadecimal pixel color value from given ClassDojo image
	
	// To construct a new 'pixel' object to put into the nested array
	var pixel;
	
	var colIndex = 0;	// Current column of the current row of the nested data
	var rowIndex = 0;	// current row of the nested data
	
	// Iterate over the hexadecimal pixel data, currently in a flat array, 
	// and transform into a nested array
	for ( var flatIndex=0; flatIndex<image.data.length+1; flatIndex++){
		
		// When the column index has reached it's "width", complete
		// the current column list (AKA 'row') and add it to the 
		// nested array
		if(colIndex == image.width){
			
			nestedPixelArray[rowIndex] = curColumn;
			curColumn = new Array(image.width);
			colIndex = 0;	// Kind of like resetting a typewriter after line is done
			rowIndex++;		// Go to next line
			
			// Necessary to add the last column list
			if( rowIndex == image.height){
				break;
			}
		}
		
		// Get the hex value from the flat array
		curHex = image.data[flatIndex];
		
		// Convert the hex into a Pixel object with RGB fields
		pixel = hexToRGB(curHex);
				
		// Add the Pixel to the row and increment the column counter
		curColumn[colIndex] = pixel;
		colIndex++;
	}
	
	return nestedPixelArray;
}

/**
 * Turns a hexadecimal string into an RGB 'Pixel' object.
 * For simplicity, assumes that the hex string input is a properly formatted
 * 6-character valid hex code. More error checking could be put in if needed.
 * @param hex		Hexadecimal string
 * @returns			Pixel object with RGB values
 */
function hexToRGB(hex){
	
	// Hex values for RGB
	var red = hex.slice(0,2);
	var green = hex.slice(2,4);
	var blue = hex.slice(4,6);

	// parse hex values into integers
	red = parseInt(red, 16);
	green = parseInt(green, 16);
	blue = parseInt(blue, 16);

	
	return new Pixel(red,green,blue);
	
}

/**
 * Convert an RGB 'Pixel' into a hex string
 * @param pixel		Pixel object to convert
 * @returns			Properly formatted 6-character hex string
 */
function RGBPixelToHex(pixel){
	
	var red = pixel.red.toString(16);
	if(red.length == 1){
		red = '0' + red;
	}
	
	var green = pixel.green.toString(16);
	if(green.length == 1){
		green = '0' + green;
	}
	
	var blue = pixel.blue.toString(16);
	if(blue.length == 1){
		blue = '0' + blue;
	}
	
	return red + green + blue;	
	
}

function drawMergedAvatar(base64String){
	
	document.write('<img title="Merged Avatar" src="data:image/png;base64,'+base64String+'">');
}