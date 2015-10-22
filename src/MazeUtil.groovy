import java.util.ArrayList;

class MazeUtil {
	
	
	//static boolean[][] wasHere = new boolean[rows][columns];
	//static boolean[][] correctPath = new boolean[rows][columns];
		

	
	static printMaze(Maze maze){
		System.out.println();
		for(int i=0;i<maze.rows;i++){
			for(int j=0;j<maze.columns;j++)
				System.out.print(maze.maze[i][j].state);
			System.out.println();
		}
	}
	
	static printMazeSolvedRecursive(Maze maze){
		def count =0
		for(int i=0;i<maze.rows;i++){
			for(int j=0;j<maze.columns;j++){
				if(maze.correctPath[i][j]==true && maze.maze[i][j].state!='S'){
					count++
					print maze.rightWay
				} else {
					print maze.maze[i][j].state;
				}
			}
			println ''
		}
		
		println "$count moves to solve maze using recursion"
	}
	
	static ArrayList getNeighbors(Cell home, Maze maze){
		def neighbors = []
		for(x in -1..1){
			for(y in -1..1){
				if(x==0&&y==0 || x!=0&&y!=0){ continue }
				try{
					if(maze.maze[home.row+x][home.column+y].state==maze.path ||maze.maze[home.row+x][home.column+y].start==true ){continue;}
				} catch(Exception e) { continue }
			
				try{
					def count =0
					
					if(maze.maze[home.row+x][home.column+y-1].state==maze.wall && (home.column+y-1)>=0 ){count++}
					if(maze.maze[home.row+x][home.column+y+1].state==maze.wall ){count++}
					if(maze.maze[home.row+x+1][home.column+y].state==maze.wall){count++}
					if(maze.maze[home.row+x-1][home.column+y].state==maze.wall && (home.row+x-1) >=0){count++}
					
					if(count>=3){
						if(home.row+x > 0 && home.row+x < maze.rows && home.column+y > 0 && home.column+y < maze.columns ){
							Cell cell = maze.maze[home.row+x][home.column+y]
							cell.parent=maze.maze[home.row][home.column]
							neighbors.add(cell)
						}
					}
				} catch(Exception e) { continue }
			}
		}
		return neighbors
	}
	
	static generateMazePrim(Maze maze){
		maze.startCell = maze.unVisited.get((int)Math.random()*maze.unVisited.size)
		maze.startCell.start=true
		maze.startCell.state='S'
		maze.currentCell = maze.startCell
		
		def frontier = []
		
		def neighbors= getNeighbors(maze.currentCell,maze)
		
		neighbors.each { cell -> frontier.add(cell)  }
		
		
		
		Cell last = null
		while(!frontier.isEmpty()){
			println frontier.size
			Cell choosenCell = frontier.get((int)Math.random()*frontier.size)
			Cell oppositeCell = choosenCell.opposite(maze,choosenCell)
			
			/*println" cur ${maze.currentCell.row} ${maze.currentCell.column } "
			println" opp ${maze.oppositeCell.row} ${ maze.oppositeCell.column } "*/
			try{
				if(maze.maze[choosenCell.row][choosenCell.row].state==maze.wall){
					if(maze.maze[oppositeCell.row][oppositeCell.column].state==maze.wall){
						
						// open path between the nodes
						maze.maze[choosenCell.row][choosenCell.column].state=maze.path
						maze.maze[oppositeCell.row][oppositeCell.column].state=maze.path
 
						// store last node in order to mark it later
						last =oppositeCell
 
						// iterate through direct neighbors of node, same as earlier
						neighbors= getNeighbors(oppositeCell,maze)
		
						neighbors.each { cell -> frontier.add(cell)  }
					}
				}
			} catch(Exception e){
			println e
			}
			
			
			
			if(frontier.isEmpty()){
				last.state=maze.end;
			}
			frontier.remove(choosenCell)
			
		}
		
		
	}
	
	static generateMazeRecursiveBacktracker(Maze maze){
		
		maze.startCell = maze.unVisited.remove((int)Math.random()*maze.unVisited.size)
		maze.startCell.start=true
		maze.startCell.state='S'
		maze.currentCell = maze.startCell
		
		while(maze.unVisited.size>0){
			
				def neighbors= getNeighbors(maze.currentCell,maze)
				if(neighbors.size>0){
					
					Cell choosenCell = neighbors.remove((int)Math.random()*neighbors.size())
					choosenCell.visited=true
					maze.cellStack.push(maze.currentCell)
					
					maze.maze[choosenCell.row][choosenCell.column].state=maze.path
					
					maze.currentCell=choosenCell
					
					maze.unVisited-maze.currentCell
				}
				else if(maze.cellStack.size()!=0){
					maze.currentCell=maze.cellStack.pop()
				}
				else {
						if(maze.unVisited.size()==0)break;
						maze.currentCell = maze.unVisited.get((int)Math.random()*maze.unVisited.size)
						maze.currentCell.visited=true
						maze.unVisited.remove(maze.currentCell)
				}
			
				if(maze.unVisited.size==0){
					maze.endCell=maze.currentCell
					maze.endCell.visited=true
					maze.maze[maze.currentCell.row][maze.currentCell.column].state=maze.end
				}
			}
	}

	static solveMazeRecursive(Maze maze)  {		
		boolean b = recursiveSolve(maze.startCell.row, maze.startCell.column,maze)
	}
	
	static boolean recursiveSolve (int x, int y,Maze maze) {
			if (x == maze.endCell.row && y == maze.endCell.column) return true; // If you reached the end
			if (maze.maze[x][y].state == maze.wall || maze.wasHere[x][y]) return false;
			// If you are on a wall or already were here
			maze.wasHere[x][y] = true;
			if (x != 0) // Checks if not on left edge
				if (recursiveSolve(x-1, y,maze)) { // Recalls method one to the left
					maze.correctPath[x][y] = true; // Sets that path value to true;
					return true;
				}
			if (x != maze.rows - 1) // Checks if not on right edge
				if (recursiveSolve(x+1, y,maze)) { // Recalls method one to the right
					maze.correctPath[x][y] = true;
					return true;
				}
			if (y != 0)  // Checks if not on top edge
				if (recursiveSolve(x, y-1,maze)) { // Recalls method one up
					maze.correctPath[x][y] = true;
					return true;
				}
			if (y != maze.columns- 1) // Checks if not on bottom edge
				if (recursiveSolve(x, y+1,maze)) { // Recalls method one down
					maze.correctPath[x][y] = true;
					return true;
				}
			return false;
	}
}

