import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class ImageMaker 
{
	public static int getInput(int min, int max, String reprompt, Scanner scan)
	{
		int input = 0;	//User's input.
		boolean inputAccepted = false;	//Whether a valid input has been given 
		
		//Check whether input is valid before accepting it
		do
		{
			if(scan.hasNextInt())
			{
				int temp = scan.nextInt();	//Temporary holder of user's int.
				if(temp >= min && temp <=max)
				{
					input = temp;
					inputAccepted = true;
				}
					
				scan.nextLine();
			}
			else
				scan.nextLine();
			
			if(!inputAccepted)
				System.out.print(reprompt);
			
		}while(!inputAccepted);
		
		return input;
	}
	
	
	public static File chooseBackground(ArrayList<File> bG, Scanner scan) 
	throws IOException
	{
		int choice;					//User's choice of background image file.
		String fileName;				//Name of file submitted by the user.
		boolean fileAccepted = false;	//whether user has given a valid file.
		File retFile;					//File to be returned.
		String retFileName = "";		//Name of return file.
		boolean inList = false;			//Whether a file is already in the list.
		
		//Message to be displayed 
		String startDisp = "Choose background image file:\n";
		
		//Adds file names to the message
		for(int i = 0; i < bG.size(); i++)
		{
			startDisp += (i+1) + ". " + bG.get(i).getName() + "\n";
		}
		startDisp += (bG.size() + 1) + ". Load new image file\n";
		startDisp += "> ";
		
		//Displays message
		System.out.print(startDisp);
		
		//Accepts valid choice, re-prompts if choice is not valid
		choice = getInput(1, bG.size() + 1, 
						  "Must be 1-" + (bG.size() + 1) + ": ", scan);
		
		//If one of the existing files is chosen, returns that one, if the last
		//option is chosen, get a valid file name from the user
		if(choice >= 1 && choice <= bG.size())
			retFileName = bG.get(choice - 1).getName();
		else
		{
			do
			{
				inList = false;
				System.out.print("Enter filename: ");
				
				fileName = scan.nextLine();
				//temporary file, for the purpose of checking whether user's
				//file and file name are valid:
				File userFile = new File(fileName);
				
				//Sees if the filename is already on the list
				for(int i = 0; i < bG.size(); i++)
				{
					if(  fileName.equals( bG.get(i).getName() )  )
					{
						System.out.println(fileName + " is already in list");
						inList = true;
					}
				}
				
				//Checks if the file with that name exists
				if(!userFile.exists())
					System.out.println("File not found.");
				
				//If the user's file is valid, assign it as the file to be 
				//returned
				if(!inList && userFile.exists())
				{
					retFileName = userFile.getName();
					fileAccepted = true;
				}
					
			}while(!fileAccepted);
		}
		
		//Assign name to the return file
		retFile = new File(retFileName);
		//Create buffered image of the file to get the dimension
		BufferedImage retFileBI = ImageIO.read(retFile);
		//Print out image dimension
		System.out.println("Background Image: " + retFile.getName() + 
				" ("+ retFileBI.getWidth()+"x"+ retFileBI.getHeight()+")");
		//Save the file
		File saveFile = new File("ImageMaker_saved.jpg");
		ImageIO.write(retFileBI, "JPG", saveFile);
		
		return retFile;
	}
	
	
	public static int mainMenu(Scanner scan)
	{
		//Print out main menu options.
		System.out.print("Main menu\n"
				+ "1. Change background image\n"
				+ "2. Add shapes to shape list\n"
				+ "3. Draw and fill shapes onto current background\n"
				+ "4. Write current image to a file\n"
				+ "5. Quit\n"
				+ "> ");
		
		//Return user's main menu option
		return getInput(1, 5, "Must be 1-5: ", scan);
		
	}
	
	
	public static void addShapes(Scanner scan, ArrayList<Shape> shapes)
	{
		//VARIABLES
		int choice;		//User's choice of option of adding shapes.
		String shapeType = ""; 	//Type of shape chosen by the user.
		String addMore = "n";	//Whether the user wants to add more shapes.
		String fileName;		//Name of user-submitted file.
		File readFile = null;	//User-submitted file.
		//Array list of lines of String from a user-submitted file.
		ArrayList<String> lines = new ArrayList<String>();
		
		//DATA FOR SHAPES
		//FOR ALL SHAPES:
		int r,	//Red value.
		g,		//Green value.
		b, 		//Blue value.
		//FOR RECT AND ARC:
		cX, 	//X-coord of upper left corner of rectangle.
		cY, 	//Y-coord of upper left corner of rectangle.
		w, 		//Rectangle width.
		h,		//Rectangle height.
		//FOR ARC
		iA,		//Initial angle.
		fA,		//Final angle.
		//FOR POLY
		n;		//Number of points in a polygon
		//ArrayList of X-coord of polygon points
		ArrayList<Integer> nX = new ArrayList<Integer>();
		//ArrayList of Y-coord of polygon points
		ArrayList<Integer> nY = new ArrayList<Integer>();
		
		//Display options for adding shapes
		System.out.print("How do you want to add shapes?\n"
						+ "1. Add shapes from menu\n"
						+ "2. Read shapes from a file\n"
						+ "> ");
		
		//Get user's choice for adding shapes
		choice = getInput(1, 2, "Must be 1-2: ", scan);
		
		//If user chooses 1, prompt the user for shape information, if 2 prompt
		//for a file from which shape information is read.
		if(choice == 1)
		{
			do
			{
				System.out.print("Choose shape (rect, arc, poly): ");
				do
				{
					//Temporary string for validation:
					String temp = scan.nextLine(); 
					if(temp.equals("rect") || temp.equals("arc") 
					|| temp.equals("poly"))
						shapeType = temp;
					else
						System.out.print("Must be rect, arc or poly: ");
					
				}while(shapeType.equals(""));
				
				//Add new shape to shape list
				Shape newShape = new Shape(shapeType);	//Most recent shape.
				shapes.add(newShape);
				
				//Ask user for color values of the shape
				newShape.askColor(scan);
				
				//Prompt for shape data according to the chosen shape type
				if(shapeType.equals("rect"))
					newShape.askRect(scan);
				else if(shapeType.equals("arc"))
					newShape.askArc(scan);
				else if(shapeType.equals("poly"))
				{
					newShape.askPoly(scan);
					scan.nextLine();
				}
					
				//Ask if the user wants to add another shape
				System.out.print("Do you want to add another shape? (y|n): ");
				if(scan.hasNextLine())
					addMore = scan.nextLine();
				
			}while(addMore.equals("y"));
			
			//Print out how many shapes added to the shape list
			System.out.println("Added " + shapes.size() + " shapes to shape list");
		}
		
		//Option 2: get file from user
		else if(choice == 2)
		{
			System.out.print("Enter filename: ");
			fileName = scan.nextLine(); 
			readFile = new File(fileName);	
			
			//Print this if file is not found
			if(!readFile.exists())
				System.out.println("Unable to read from that file");
			else
			{
				//Get scanner to read the file
				try 
				{
					scan = new Scanner(readFile);
				} 
				catch (FileNotFoundException e) 
				{
				}
				
				//Add lines of String from the file to the ArrayList "lines"
				while(scan.hasNextLine())
					lines.add(scan.nextLine());

				//Go through "lines" and create a shape object based on 
				//data on each line 
				for(int i = 0; i < lines.size(); i++)
				{
					//Get scanner to read "lines"
					scan = new Scanner(lines.get(i));
					
					//The first word of a line, denoting shape type
					String firstWord = scan.next();
					
					//Create shape based on the first word of a line
					
					//Create a rect
					if (firstWord.equals("rect")) 
					{
						//Create new rect Shape
						Shape fileRect = new Shape("rect");

						//Read data from file
						r = scan.nextInt();
						g = scan.nextInt();
						b = scan.nextInt();
						cX = scan.nextInt();
						cY = scan.nextInt();
						w = scan.nextInt();
						h = scan.nextInt();
						
						//Set values for the rect Shape
						/*
						fileRect.setRed(r);
						fileRect.setGreen(g);
						fileRect.setBlue(b);
						*/
						fileRect.setColor(r, g, b);
						fileRect.setRect(cX, cY, w, h);
						
						//Add the rect Shape to ArrayList of Shapes
						shapes.add(fileRect);
						
						//Display information about the newest Shape
						System.out.println("Created: rect " + r + " " + g + " " + b 
								+ " " + cX + " " + cY + " " + w + " " + h);
					} 
					//Create an arc
					else if (firstWord.equals("arc")) 
					{
						//Create new arc Shape
						Shape fileArc = new Shape("arc");

						//Read data from file
						r = scan.nextInt();
						g = scan.nextInt();
						b = scan.nextInt();
						cX = scan.nextInt();
						cY = scan.nextInt();
						w = scan.nextInt();
						h = scan.nextInt();
						iA = scan.nextInt();
						fA = scan.nextInt();

						//Set values for the arc Shape
						/*
						fileArc.setRed(r);
						fileArc.setGreen(g);
						fileArc.setBlue(b);
						*/
						fileArc.setColor(r, g, b);
						fileArc.setArc(cX, cY, w, h, iA, fA);
						
						//Add the arc Shape to ArrayList of Shapes
						shapes.add(fileArc);

						//Display information about the newest Shape
						System.out.println("Created: arc " + r + " " + g + " " + b 
								+ " " + cX + " " + cY + " " + w + " " + h 
								+ " " + iA + " " + fA);
					}
					//Create a poly
					else if (firstWord.equals("poly")) 
					{
						//String to display after creating a poly Shape
						String createString;	

						//Create a new poly Shape
						Shape filePoly = new Shape("poly");
						
						//Read data from file
						r = scan.nextInt();
						g = scan.nextInt();
						b = scan.nextInt();
						n = scan.nextInt();

						//Read points coordinates from file
						for(int j = 0; j < n; j++)
						{
							nX.add(scan.nextInt());
							nY.add(scan.nextInt());
						}
						
						//Set values for poly Shape
						/*
						filePoly.setRed(r);
						filePoly.setGreen(g);
						filePoly.setBlue(b);
						*/
						filePoly.setColor(r, g, b);
						filePoly.setPoly(n, nX, nY);
						
						//Add poly Shape to ArrayList of Shapes
						shapes.add(filePoly);
						
						//Add to createString
						createString = "Created: poly " + r + " " + g + " " + b 
								+ " " + n;
						
						for(int j = 0; j < nX.size(); j++)
							createString += " " + nX.get(j) + " " + nY.get(j);
						
						//Display information about the newest Shape
						System.out.println(createString);
					}
				}
				//Print out how many shapes added to the shape list
				System.out.println("Added " + shapes.size() + " shapes to shape list");
			}
		}
	}
	
	
	public static BufferedImage draw(ArrayList<Shape> shapes, File file) 
			throws IOException
	{
		//Create a Buffered Image instance based on the file
		BufferedImage buffImg = ImageIO.read(file);
		//Create a Graphics image for drawing
		Graphics graphics = buffImg.getGraphics();
		
		//Go through shape ArrayList and draw each shape, and display message
		//telling user what shape has been added to the image
		for(int i = 0; i < shapes.size(); i++)
		{
			shapes.get(i).drawShape(graphics);
			
			if(shapes.get(i).equals("rect"))
				System.out.println("add rectangle to image");
			else
				System.out.println("add " + shapes.get(i).getType() + " to image");
		}
		return buffImg;
	} 
	
	
	public static void writeToFile(BufferedImage buffImg, Scanner scan)
	{
		System.out.print("Enter filename: ");
		String saveFileName = scan.next();
		File saveFile = new File (saveFileName);
		
		if(saveFile.exists())
			System.out.println("Sorry, that file name is taken");
		
		try 
		{
			ImageIO.write(buffImg, "JPG", saveFile);
			System.out.println("Current image written to " + saveFileName);
		} 
		catch (IOException e) 
		{
			System.out.print("Error in saving file");
		}
	}
	
	
	public static void main(String[] args) throws IOException 
	{
		//Construct scanner
		Scanner sally = new Scanner(System.in);
		
		File imgFile;	//User's choice of background image file.
		ArrayList<Shape> shapes = new ArrayList<Shape>(); //Array list of shapes
		int menuChoice;	//User's choice of main menu option.
		boolean quit = false;	//Whether the user wants to quit.
		BufferedImage buffImg = null;
		File saved = new File("saved.jpg");
		
		//Array list of background image JPG files
		ArrayList<File> background = new ArrayList<File>();
			background.add(new File("red.jpg"));
			background.add(new File("green.jpg"));
			background.add(new File("blue.jpg"));
			background.add(new File("black.jpg"));
			background.add(new File("white.jpg"));

		//Display welcome message
		System.out.println("Welcome to ImageMaker");
		//Display menu of background image options
		imgFile = chooseBackground(background, sally);
		
		do
		{
			//Display main menu and get user's choice
			menuChoice = mainMenu(sally);
			
			//Direct user to chosen function
			if(menuChoice == 1)
				imgFile = chooseBackground(background, sally);
			if(menuChoice == 2)
				addShapes(sally, shapes);
			else if(menuChoice == 3)
				buffImg = draw(shapes, imgFile);
			else if(menuChoice == 4)
				writeToFile(buffImg, sally);
			else if(menuChoice == 5)
			{
				ImageIO.write(buffImg, "JPG", saved);
				System.out.println("Current image written to " + saved.getName());
				quit = true;
			}
				
		}while(!quit);
		
	}

}
