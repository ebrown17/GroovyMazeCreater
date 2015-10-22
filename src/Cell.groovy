
class Cell {
	
	Integer row
	Integer column
	boolean visited = false
	boolean start = false
	boolean end = false
	char state
	Cell parent
	
	String location(){
		return "$row$column"
	}
	
	Cell opposite(Maze maze,Cell cell){
		//println 
		println "curent row compared to parent ${cell.row} ${cell.parent.row} compared ${cell.row.compareTo(cell.parent.row)}"
		println "curent column compared to parent ${cell.column} ${cell.parent.column} compared ${cell.column.compareTo(cell.parent.column)}"
		
	
		if(cell.row+(cell.row.compareTo(cell.parent.row)) < maze.rows){
			if(cell.row.compareTo(cell.parent.row)!=0){
				Cell test = maze.maze[cell.row+(cell.row.compareTo(cell.parent.row))][cell.column]
				test.parent=cell
				
				return test	
			}
		}
		if(cell.column+(cell.column.compareTo(cell.parent.column))<maze.columns){	
			if(cell.column.compareTo(cell.parent.column)!=0){
				Cell test = maze.maze[cell.row][cell.column+(cell.column.compareTo(cell.parent.column))]
				test.parent=cell
				
				return test
			}
		}	
		return null
	}
	

}
