package ui;
import java.util.Random;
/**
 * A program that models a Connect4 game
 * This class will generate a Computer opponent for the game
 * 
 *
 * @author Jaehoon Cho
 * @version 2.0 June 9th, 2019
 */
public class Connect4ComputerPlayer
{
	/** The amount of rows that board[][] will have */
	private final int rows=6;
	/** The amount of columns that board[][] will have */
	private final int columns=7;
	/** The board in which the game will be played on */
	private char[][] board;
	//instance variable
	
	/**
	 * Constructor that will lock board to whatever the Connect4 board will be
	 * 
	 * @param game Connect4 board
	 */
	public Connect4ComputerPlayer(Connect4 game)
	{
		board = game.getBoard();
	}
	
	/**
	 * method that will generate a random move that the computer opponent will make for the game Connect4
	 * 
	 * @param count determines whether the move that will be placed is an X or an O
	 */
	public void placeCompMove(int count)
	{
		Random random = new Random();
		int r = 5;
		int c = random.nextInt(columns-1);
		if(board[r-4][c]=='O' || board[r-4][c] =='X')
		{
			r=r-5;
			if(count % 2==0)
			{
				board[r][c]='X';
			}
			else
			{
				board[r][c]='O';
			}
		}
		else if(board[r-3][c]=='O' || board[r-3][c] =='X')
		{
			r=r-4;
			if(count % 2==0)
			{
				board[r][c]='X';
			}
			else
			{
				board[r][c]='O';
			}
		}
		else if(board[r-2][c]=='O' || board[r-2][c] =='X')
		{
			r=r-3;
			if(count % 2==0)
			{
				board[r][c]='X';
			}
			else
			{
				board[r][c]='O';
			}
		}
		else if(board[r-1][c]=='O' || board[r-1][c] =='X')
		{
			r=r-2;
			if(count % 2==0)
			{
				board[r][c]='X';
			}
			else
			{
				board[r][c]='O';
			}
		}
		else if(board[r][c]=='O' || board[r][c] =='X')
		{
			r=r-1;
			if(count % 2==0)
			{
				board[r][c]='X';
			}
			else
			{
				board[r][c]='O';
			}
		}
		else
		{
			if(count % 2==0)
			{
				board[r][c]='X';
			}
			else
			{
				board[r][c]='O';
			}
		}
	}
}
