package clueTests;

import java.util.LinkedList;
import java.util.Set;

//Doing a static import allows me to write assertEquals rather than
//assertEquals
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class OurAdjTests {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	@BeforeClass
	public static void setUp() {
		board = new Board("clueLayout.csv", "ClueFiles/ClueLegend.txt");
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test a corner
		LinkedList<BoardCell> testList = board.getAdjList(0, 0);
		
		assertEquals(0, testList.size());
		// Test one that has walkway underneath
		testList = board.getAdjList(2, 0);
		assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(15, 20);
		assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(18, 11);
		assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(14, 12);
		assertEquals(0, testList.size());
		// Test one in a corner of room
		testList = board.getAdjList(20, 20);
		assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	// These tests are PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 
		LinkedList<BoardCell> testList = board.getAdjList(11, 5);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(11, 6)));
		// TEST DOORWAY LEFT 
		testList = board.getAdjList(9, 15);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(9, 14)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(4, 6);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(5, 6)));
		//TEST DOORWAY UP
		testList = board.getAdjList(7, 20);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(6, 20)));
		
	}
	
	// Test adjacency at entrance to rooms
	// These tests are GREEN in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		LinkedList<BoardCell> testList = board.getAdjList(4, 3);
		System.out.println(testList);
		assertTrue(testList.contains(board.getCellAt(4, 2)));
		assertTrue(testList.contains(board.getCellAt(4, 4)));
		assertTrue(testList.contains(board.getCellAt(5, 3)));
		assertEquals(3, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(5, 6);
		assertTrue(testList.contains(board.getCellAt(5, 7)));
		assertTrue(testList.contains(board.getCellAt(5, 5)));
		assertTrue(testList.contains(board.getCellAt(4, 6)));
		assertTrue(testList.contains(board.getCellAt(6, 6)));
		assertEquals(4, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(9, 14);
		assertTrue(testList.contains(board.getCellAt(9, 15)));
		assertTrue(testList.contains(board.getCellAt(9, 13)));
		assertTrue(testList.contains(board.getCellAt(8, 14)));
		assertTrue(testList.contains(board.getCellAt(10, 14)));
		assertEquals(4, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(6, 20);
		assertTrue(testList.contains(board.getCellAt(6, 21)));
		assertTrue(testList.contains(board.getCellAt(6, 19)));
		assertTrue(testList.contains(board.getCellAt(7, 20)));
		assertEquals(3, testList.size());
	}

	// Test a variety of walkway scenarios
	// These tests are LIGHT PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on top edge of board, just one walkway piece
		LinkedList<BoardCell> testList = board.getAdjList(0, 4);
		assertTrue(testList.contains(board.getCellAt(1, 4)));
		assertEquals(1, testList.size());
		
		// Test on left edge of board, three walkway pieces
		testList = board.getAdjList(6, 0);
		assertTrue(testList.contains(board.getCellAt(5, 0)));
		assertTrue(testList.contains(board.getCellAt(6, 1)));
		assertTrue(testList.contains(board.getCellAt(7, 0)));
		assertEquals(3, testList.size());

		// Test between two rooms, walkways right and left
		testList = board.getAdjList(14, 2);
		assertTrue(testList.contains(board.getCellAt(14, 3)));
		assertTrue(testList.contains(board.getCellAt(14, 1)));
		assertEquals(2, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(15,6);
		assertTrue(testList.contains(board.getCellAt(15, 5)));
		assertTrue(testList.contains(board.getCellAt(15, 7)));
		assertTrue(testList.contains(board.getCellAt(16, 6)));
		assertTrue(testList.contains(board.getCellAt(14, 6)));
		assertEquals(4, testList.size());
		
		// Test on bottom edge of board, next to 1 room piece
		testList = board.getAdjList(21, 14);
		assertTrue(testList.contains(board.getCellAt(21, 13)));
		assertTrue(testList.contains(board.getCellAt(20, 14)));
		assertEquals(2, testList.size());
		
		// Test on right edge of board, next to 1 room piece
		testList = board.getAdjList(14, 22);
		assertTrue(testList.contains(board.getCellAt(14, 21)));
		assertTrue(testList.contains(board.getCellAt(13, 22)));
		assertEquals(2, testList.size());

		// Test on walkway next to  door that is not in the needed
		// direction to enter
		testList = board.getAdjList(5, 2);
		assertTrue(testList.contains(board.getCellAt(5, 3)));
		assertTrue(testList.contains(board.getCellAt(5, 1)));
		assertTrue(testList.contains(board.getCellAt(6, 2)));
		assertEquals(3, testList.size());
	}
	
	
	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(21, 6, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(20, 6)));
		assertTrue(targets.contains(board.getCellAt(21, 5)));	
		
		board.calcTargets(14, 0, 1);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(14, 1)));
		assertTrue(targets.contains(board.getCellAt(13, 0)));			
	}
	
	// Tests of just walkways, 2 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(18, 5, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(16, 5)));
		assertTrue(targets.contains(board.getCellAt(17, 6)));
		assertTrue(targets.contains(board.getCellAt(19, 6)));
		assertTrue(targets.contains(board.getCellAt(20, 5)));
		
		board.calcTargets(14, 0, 2);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(12, 0)));
		assertTrue(targets.contains(board.getCellAt(14, 2)));				
	}
	
	// Tests of just walkways, 4 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(6, 22, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(6, 18)));
		assertTrue(targets.contains(board.getCellAt(7, 20)));
		
		// Includes a path that doesn't have enough length
		board.calcTargets(21, 13, 4);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(17, 13)));
		assertTrue(targets.contains(board.getCellAt(20, 14)));	
		assertTrue(targets.contains(board.getCellAt(18, 14)));	
		assertTrue(targets.contains(board.getCellAt(19, 13)));	
	}	
	
	// Tests of just walkways plus one door, 6 steps
	// These are LIGHT BLUE on the planning spreadsheet

	@Test
	public void testTargetsSixSteps1() {
		board.calcTargets(8, 0, 6);
		Set<BoardCell> targets= board.getTargets();
		//assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCellAt(5, 3)));
		assertTrue(targets.contains(board.getCellAt(5, 1)));	
		assertTrue(targets.contains(board.getCellAt(6, 2)));	
		assertTrue(targets.contains(board.getCellAt(6, 4)));	
	
	}	
	
	
	// Test getting into a room
	// These are LIGHT BLUE on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away
		board.calcTargets(16, 13, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(5, targets.size());
		// directly up and down
		assertTrue(targets.contains(board.getCellAt(14, 13)));
		assertTrue(targets.contains(board.getCellAt(18, 13)));
		// one up/down, one left/right
		assertTrue(targets.contains(board.getCellAt(17, 12)));
		assertTrue(targets.contains(board.getCellAt(17, 14)));
		assertTrue(targets.contains(board.getCellAt(15, 14)));
	}
	
	// Test getting into room, doesn't require all steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		board.calcTargets(12, 6, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(11, targets.size());
		// directly up and down
		assertTrue(targets.contains(board.getCellAt(15, 6)));
		assertTrue(targets.contains(board.getCellAt(9, 6)));
		// right then down
		assertTrue(targets.contains(board.getCellAt(13, 8)));
		assertTrue(targets.contains(board.getCellAt(14, 7)));
		// down then left/right
		assertTrue(targets.contains(board.getCellAt(14, 5)));
		// right then up
		assertTrue(targets.contains(board.getCellAt(10, 7)));
		// into the rooms
		assertTrue(targets.contains(board.getCellAt(11, 5)));
		assertTrue(targets.contains(board.getCellAt(10, 5)));		
		// 
		assertTrue(targets.contains(board.getCellAt(11, 6)));
		assertTrue(targets.contains(board.getCellAt(12, 7)));
		assertTrue(targets.contains(board.getCellAt(13, 6)));
		
	}

	// Test getting out of a room
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(3, 18, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(3, 17)));
		// Take two steps
		board.calcTargets(3, 18, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 17)));
		assertTrue(targets.contains(board.getCellAt(3, 16)));
		assertTrue(targets.contains(board.getCellAt(2, 17)));
	}

}