package ui;
import java.util.Scanner;
/**
 * A program that models a Connect4 game
 * 
 *
 * @author Jaehoon Cho
 * @version 2.0 June 9th, 2019
 */

public class Connect4TextConsole extends Connect4GUI
{
	
	/**
	 * The main method that will print the game 
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		Connect4 game = new Connect4();
		Connect4GUI game2= new Connect4GUI();
		
		int rows = 6;
		int columns;
		int count = 0;
		System.out.println("Press G if you would like to play Connect4 GUI or C if you would like to play Connect4 console-based UI");
		String choice0 = in.next();
		if(choice0.equals("G"))
		{
			Connect4GUI.launch(args);
		}
		else if(choice0.equals("C"))
		{
			game.showBoard();
			System.out.println("Begin Game. Enter 'P' if you want to play against another player; enter 'C' to play against computer");
			try
			{
				String choice = in.next();
				if(choice.equals("P"))
				{
					while(game.winner()!=true)
					{
						if(count % 2==0)
						{
							System.out.println("PlayerX-your turn. Choose a column number from 1-7");
						}
						else
						{
							System.out.println("PlayerO-your turn. Choose a column number from 1-7");
						}
						columns = in.nextInt();
						if(columns>7 || columns<0)
						{
							System.out.println("Invalid move");
						}
						else if(game.getBoardAt(0,columns-1)=='O' || game.getBoardAt(0,columns-1) =='X')
						{
							System.out.println("Invalid move");
						}
						else
						{
							game.placeMove(rows-1, columns-1, count);
							game.showBoard();
							count++;
						}
					}	
					if(count % 2 == 0)
					{
						System.out.println("Player O Won the Game");
					}
					else
					{
						System.out.println("Player X Won the Game");
					}
				}
				else if(choice.equals("C"))
				{
					while(game.winner()!=true)
					{
						Connect4ComputerPlayer computer = new Connect4ComputerPlayer(game);
						if(count % 2==0)
						{
							System.out.println("PlayerX-your turn. Choose a column number from 1-7");
							columns = in.nextInt();
							if(columns>7 || columns<0)
							{
								System.out.println("Invalid move");
							}
							else if(game.getBoardAt(0,columns-1)=='O' || game.getBoardAt(0,columns-1) =='X')
							{
								System.out.println("Invalid move");
							}
							else
							{
								game.placeMove(rows-1, columns-1, count);
								game.showBoard();
								count++;
							}	
						}
						else
						{
							System.out.println("Computer's turn");
							computer.placeCompMove(count);
							game.showBoard();
							count++;
						}
			
					}
					if(count % 2 == 0)
					{
						System.out.println("Player O Won the Game");
					}
					else
					{
						System.out.println("Player X Won the Game");
					}
				}
				else
				{
					System.out.println("Wrong value");
				}
			}
			catch(RuntimeException except)
			{
				System.out.println("there is a runtime exception");
			}
		}
	}

}
