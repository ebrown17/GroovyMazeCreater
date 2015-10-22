
class Maze {
	
	static def rightWay ='o'
	static def wall = '1'
	static def path = ' '
	static def end = 'E'
	static def start = 'S'
	
	def Cell startCell
	def Cell endCell
	def Cell currentCell
	def Cell oppositeCell
	def cellStack = []	
	def rows
	def columns
	def Cell[][] maze
	def unVisited =[]
	def boolean[][] wasHere
	def boolean[][] correctPath
	
	
	public Maze(int rows, int columns ){
		this.rows = rows
		this.columns = columns
				
		maze = new Cell[rows][columns]
		wasHere = new boolean[rows][columns]
		correctPath = new boolean[rows][columns]
		
		for(i in 0..<rows){
			for(j in 0..<columns){
				Cell cell = new Cell()
				cell.row = i
				cell.column = j
				cell.state = wall
				cell.parent = cell
				maze[i][j]=cell
				wasHere[i][j] = false;
				correctPath[i][j] = false;
				unVisited.add(cell)
			}
		}
	}
	
	
	
	
}