package clueGame;

public class BoardCell {

	private int row;
	private int col;
	private char initial;
	private DoorDirection dd;
	
	public BoardCell(int row, int col){
		super();
		this.row = row;
		this.col = col;
	}
	
	public BoardCell(int row, int col, char initial, char door) {
		super();
		this.row = row;
		this.col = col;
		this.initial = initial;
		
		switch( door ){
		case 'L': 
			dd = DoorDirection.LEFT;
			break;
		case 'R': 
			dd = DoorDirection.RIGHT;
			break;
		case 'U':
			dd = DoorDirection.UP;
			break;
		case 'D':
			dd = DoorDirection.DOWN;
			break;
		default:
			dd = DoorDirection.NONE;
			break;
		}
	}

	private void setRow(int row) {

	}

	public boolean isWalkway(){
		return initial == 'W';
	}
	
	public boolean isRoom(){
		return initial != 'W';
	}
	
	public boolean isDoorway(){
		return !(dd == DoorDirection.NONE);
	}
	
	
	
	public String toString() {
		return "BoardCell [row=" + getRow() + ", col=" + col + "]";
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public DoorDirection getDoorDirection() {
		return dd;
	}

	public char getInitial() {
		return initial;
	}
	
	
	
	


	
	
	
}
