
class Main {

	static void main(args){	
		
		
		Maze maze = new Maze(30,90)	
		
		MazeUtil.generateMazeRecursiveBacktracker(maze)
		//MazeUtil.generateMazePrim(maze)
		
		MazeUtil.printMaze(maze)		
		
						
		
		
		
	}	
	
}
