package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class Board {
	private int numRows;
	private int numColumns;
	public static int BOARD_SIZE;
	private BoardCell[][] board;
	private static Map<Character, String> rooms;
	private Map<BoardCell, LinkedList<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private String boardConfigFile;
	private String roomConfigFile;

	public Board(){
		boardConfigFile = "ClueFiles/ClueLayout.csv";
		roomConfigFile = "ClueFiles/ClueLegend.txt";
		rooms = new HashMap<Character, String>();
	}

	public Board(String boardConfigFile, String roomConfigFile) {
		this.boardConfigFile = boardConfigFile;
		this.roomConfigFile = roomConfigFile;
		rooms = new HashMap<Character, String>();
		adjMatrix = new HashMap<BoardCell, LinkedList<BoardCell>>();
	}

	public void initialize(){
		try {
			loadRoomConfig();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (BadConfigFormatException e) {
			System.out.println(e);
		}
		try {
			loadBoardConfig();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (BadConfigFormatException e) {
			System.out.println(e);
		}
	}

	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException{
		FileReader reader = new FileReader(this.roomConfigFile);
		Scanner in = new Scanner(reader);
		while( in.hasNextLine() )
		{	
			//check it is of the right format
			String line = in.nextLine();
			// make sure there are 2 delimiters (,).
			if( line.length() - line.replace(",", "").length() != 2 )
				throw new BadConfigFormatException(roomConfigFile);
		}
		in.close();
		reader = new FileReader(this.roomConfigFile);
		in = new Scanner(reader);
		in.useDelimiter(", ");
		while( in.hasNext() )
		{	
			String c = "";
			c = in.next();
			String room = "";
			room = in.next();
			rooms.put(c.charAt(0), room);

			// place holder for type - aka card/other.
			String type = in.nextLine();
		}
		in.close();

		System.out.println(rooms);
	}

	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException{
		FileReader reader = new FileReader(this.boardConfigFile);
		Scanner in = new Scanner(reader);
		String room;
		String line = in.next();
		int rows = 0; int cols = line.length() - line.replace(",", "").length() + 1;
		// get num rows/cols
		while( in.hasNext() )
		{
			line = in.next();
			if(cols != line.length() - line.replace(",", "").length() + 1)
				throw new BadConfigFormatException(boardConfigFile);
			rows++;

		}

		numRows = rows + 1;
		numColumns = cols;
		board = new BoardCell[numRows][numColumns];
		in.close();

		reader = new FileReader(this.boardConfigFile);
		in = new Scanner(reader);
		in.useDelimiter(",");
		int i = 0; int j = 0;
		// now read the file 
		while( in.hasNext() )
		{
			if(j == numColumns - 1 ){
				room = in.nextLine();
				room = room.replace(",", "");
			}
			else
				room = in.next();
			if(!rooms.containsKey( room.charAt(0) ) )
				throw new BadConfigFormatException(boardConfigFile);
			if( room.length() == 2 )
			{
				board[i][j] = new BoardCell(i, j, room.charAt(0), room.charAt(1));
			}
			else{
				// Just a room, no door.
				// constructor will handle invalid door direction.
				board[i][j] = new BoardCell(i, j, room.charAt(0), ' ');
			}

			if( j == numColumns - 1){
				i++; j = 0;
			}
			else
				j++;
		}
		in.close();
	}

	public void calcAdjacencies(){}

	public void calcTargets(int row, int column, int pathLength){
		
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();

		visited.add(board[row][column]);
		
		findAllTargets(board[row][column], pathLength);
	}

	public void findAllTargets(BoardCell boardCell, int pathLength) {
		
		LinkedList<BoardCell> list = new LinkedList<BoardCell>(getAdjList(boardCell.getRow(), boardCell.getCol()));
		
		
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
			if(pathLength == 1 || c.isDoorway())
				targets.add(c);
			else{
				findAllTargets(c, pathLength - 1);
			}
			visited.remove(c);
		}
		
		
	}

	public BoardCell getCellAt(int row, int column){return board[row][column];}

	public static Map<Character, String> getRooms() {
		
		return rooms;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public LinkedList<BoardCell> getAdjList(int i, int j) {

		
		if( adjMatrix.containsKey(board[i][j]) )
		{
			return adjMatrix.get(board[i][j]);
		}

		LinkedList<BoardCell> list = new LinkedList<BoardCell>();

		if(i < 0 && j < 0 && i > numRows && j > numColumns){return null;}
		
		if(board[i][j].getInitial() == 'W' || board[i][j].isDoorway())
		{
		
	
			//up
			if(i - 1  >= 0 && (board[i-1][j].getInitial() == 'W' || board[i-1][j].getDoorDirection() == DoorDirection.DOWN)){
				list.add(board[i - 1][j]);
	
			}
	
			//bottom
			if(i + 1  < numRows && (board[i + 1][j].getInitial() == 'W' || board[i + 1][j].getDoorDirection() == DoorDirection.UP)){
				list.add(board[i + 1][j]);
	
			}
	
			//left
			if(j - 1  >= 0 && (board[i][j - 1].getInitial() == 'W' || board[i][j - 1].getDoorDirection() == DoorDirection.RIGHT)){
				list.add(board[i][j - 1]);
	
			}
	
			//right 
			if(j + 1  < numColumns && (board[i][j + 1].getInitial() == 'W' || board[i][j + 1].getDoorDirection() == DoorDirection.LEFT)){
				list.add(board[i][j + 1]);
	
			}
		}
		adjMatrix.put(board[i][j], list);
		
		

		return  adjMatrix.get(board[i][j]);
	}

	public Set<BoardCell> getTargets() {
		
		return targets;
	}

}





