package experiment;

public class BoardCell {

	private int row;
	private int col;
	private char intitial;
	
	public BoardCell(int row, int col) {
		super();
		this.setRow(row);
		this.col = col;
	}

	private void setRow(int row2) {
		// TODO Auto-generated method stub
		
	}

	public boolean isWalkway(){
		return false;}
	
	public boolean isRoom(){
		return false;}
	
	public boolean isDoorway(){
		return false;}
	
	
	@Override
	public String toString() {
		return "BoardCell [row=" + getRow() + ", col=" + col + "]";
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}


	
	
	
}
