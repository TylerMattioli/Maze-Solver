import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MazeSolver {
	public String maze[][];
	public int[] start;
	public int[] finish;
	public int xLimit;
	public int yLimit;
	public String path[][];
	public String correctPath[][];
    public int[][] stepsArray = new int[yLimit][xLimit];
	Scanner input;
	public void translateMaze(){
		try{
			input = new Scanner(new File("Maze"));	//name the maze file Maze or it won't be found
		}catch(FileNotFoundException fnfe){
			System.err.println("Please create a maze named 'Maze.txt' in "
					+ "the containing folder!");
			fnfe.printStackTrace();
			System.exit(1);
		}
		int xSize = 12;
		int ySize = 12;
		maze = new String[xSize][ySize];
		path = new String[xSize][ySize];
		correctPath = new String[xSize][ySize];
		xLimit = xSize-1;
		yLimit = ySize-1;
		String currentLine;
		int y = yLimit;
		String symbol;
		while(input.hasNextLine()){
			currentLine = input.nextLine();
			for(int x=0; x<currentLine.length(); x=x+2){
				symbol = currentLine.substring(x, x+1);
				if(symbol.equals("F")){
					finish = new int[]{x/2, y};
				}
				maze[x/2][y] = symbol;
			}
			y--;
		}
	}
	
	public void findStart() {	//looks for the "." on the outside to find the start
		for(int x = 0; x <= xLimit; x++) { // search top
			if(maze[x][0].equals(".")){
				start= new int[]{x, 0};
				return;
			}
		}
		for(int x = 0; x <= xLimit; x++) { // search bottom
			if(maze[x][yLimit].equals(".")){
				start= new int[]{x, yLimit};
				return;
			}
		}
		for(int y = 0; y <= yLimit; y++) { // search left
			if(maze[0][y].equals(".")){
				start= new int[]{0, y};
				return;
			}
		}
		for(int y = 0; y <= yLimit; y++) { // search right
			if(maze[xLimit][y].equals(".")){
				start= new int[]{xLimit, y};
				return;
			}
		}
	}
	
	public boolean traverse(int x, int y){	//traverse the maze via right hand rule
		if(x > xLimit || y > yLimit || x < 0 || y < 0){ // if the move is out of bounds
			return false;
		}else if(maze[x][y].equals("#")){ // if the move is a wall
			return false;
		}else if(path[x][y] != null && path[x][y].equals("x")){ // if the move was already done
			return false;
		}if(maze[x][y].equals("F")){ // if the move is the finish
			System.out.println("You found the finish!");
			return true;
		}
		path[x][y] = "x";
		if(traverse(x+1, y)){ // RIGHT
			correctPath[x][y]= "x";
			return true;
		}
		if(traverse(x, y+1)){ // UP
			correctPath[x][y]= "x";
			return true;
		}
		if(traverse(x-1, y)){ // LEFT
			correctPath[x][y]= "x";
			return true;
		}
		if(traverse(x, y-1)){ // DOWN
			correctPath[x][y]= "x";
			return true;
		}
		return false;
	}
	public void addPath(){	//adds the path used and changes start to "S" and keeps finish "F"
		for(int y=yLimit; y>=0; y--){
			for(int x=0; x<=xLimit; x++){
				if(correctPath[x][y] != null){
					maze[x][y]=correctPath[x][y];
				}
			}
		}
		maze[start[0]][start[1]] = "S";
		maze[finish[0]][finish[1]] = "F";
	}
	public void printMaze(){	//prints the maze
		for(int y=yLimit; y>=0; y--){
			for(int x=0; x<=xLimit; x++){
				System.out.print(maze[x][y] + " ");
			}
			System.out.println();
		}
	}
	public void go(){	//all the methods to solve and output
		translateMaze();
		findStart();
		traverse(start[0], start[1]);
		addPath();
		printMaze();
	}
}