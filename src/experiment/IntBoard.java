package experiment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	
	private int length;
	private int width;
	
	private Map<BoardCell, LinkedList<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private BoardCell[][] grid;
	
	
	public IntBoard(int length, int width) {
		super();
		
		// set up the board and adjs
		grid = new BoardCell[width][length];
		for( int i = 0; i < width; i++ )
		{
			for( int j = 0; j < length; j++ )
			{
				grid[i][j] = new BoardCell(i, j);
			}
		}
		
		adjMtx = new HashMap<BoardCell, LinkedList<BoardCell>>();
		
	}
	
	public int getRowLength(){
		
		int length = grid.length;
		
		return length;
	}
	public int getColLength(){
		
		int length = grid[0].length;
		
		return length;
	}
	public IntBoard(){}
	public void calcAdjacencies(){}
	public BoardCell getCell(int row, int col){
		
		if(row < 0 && col < 0 && row > getRowLength() && col > getColLength()){return null;}
		return grid[row][col];
		}
	
	public void calcTargets(BoardCell cell, int pathLength) {
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();

		visited.add(cell);
		
		findAllTargets(cell, pathLength);
		
		
	}
	
	
	public void findAllTargets(BoardCell cell, int pathLength){
		
		LinkedList<BoardCell> list = new LinkedList<BoardCell>(getAdjList(cell));
		
		System.out.println(list);
		Iterator<BoardCell> iter = list.iterator();
		while(iter.hasNext())
		{
			BoardCell c = iter.next();
			if(visited.contains(c))
			{
				iter.remove();
			}
		}
		
		
		for(BoardCell c : list)
		{
			visited.add(c);
			if(pathLength == 1)
				targets.add(c);
			else{
				findAllTargets(c, pathLength - 1);
			}
			visited.remove(c);
		}
		
		
		
	}
	public Set<BoardCell> getTargets() {return targets;}
	
	public LinkedList<BoardCell> getAdjList(BoardCell cell){
		
		if( adjMtx.containsKey(cell) )
		{
			return adjMtx.get(cell);
		}
		
		LinkedList<BoardCell> list = new LinkedList<BoardCell>();
		
		if(cell.getRow() < 0 && cell.getCol() < 0 && cell.getRow() > getRowLength() && cell.getCol() > getColLength()){return null;}
		
			
			//up
			if(cell.getRow() - 1  >= 0){
				list.add(grid[cell.getRow() - 1][cell.getCol()]);

			}

			//bottom
			if(cell.getRow() + 1  < getRowLength()){
				list.add(grid[cell.getRow() + 1][cell.getCol()]);

			}

			//left
			if(cell.getCol() - 1  >= 0){
				list.add(grid[cell.getRow()][cell.getCol() - 1]);

			}

			//right 
			if(cell.getCol() + 1  < getColLength()){
				list.add(grid[cell.getRow()][cell.getCol() + 1]);

			}
			
			adjMtx.put(cell, list);
			
			return adjMtx.get(cell);
		}

}
