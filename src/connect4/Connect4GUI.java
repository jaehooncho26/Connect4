package ui;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
/**
 * A Connect4 game that is made using JavaFX
 * 
 * @author Jaehoon Cho
 * @version version3
 */
public class Connect4GUI extends Application
{
    /** A boolean that will decide if the move will be Red or Yellow */
    private boolean move = true;
    /** The board where the Connect4game will be played on */
    private Connect4Circle[][] board = new Connect4Circle[6][7];
    /** The pane which will hold the game */
    private Pane pane = new Pane();
    /**
     * The main method that will launch the game
     * 
     * @param args
     */
    
    
    public static void main(String[] args)
    {  
        launch(args);  
    }
    /**
     * The start where the Connect4 board is made
     * 
     * @param stage the stage that will be set
     */
    @Override
    public void start(Stage stage) throws Exception
    {
        Shape shape = new Rectangle((8) * 75, (7) * 75);
        for(int y = 0; y < 6; y++)
        {
            for(int x = 0; x < 7; x++)
            {
                Circle circle = new Circle(75 / 2);
                circle.setCenterX(75 / 2);
                circle.setCenterY(75 / 2);
                circle.setTranslateX(x * (75 + 5) + 75 / 4);
                circle.setTranslateY(y * (75 + 5) + 75 / 4);
                shape = Shape.subtract(shape, circle);
           }
        }
        pane.getChildren().add(shape);
        List<Rectangle> list = new ArrayList<>(); 
        for(int x = 0; x < 7; x++) 
        {
            Rectangle rect = new Rectangle(75, (7) * 75);
            rect.setTranslateX(x * (80) + 75 / 4);
            rect.setTranslateY((5)*(80) + 75 / 4);
            rect.setFill(Color.TRANSPARENT);
            rect.setOnMouseEntered(e -> rect.setFill(Color.rgb(0, 0, 50, 0.3)));
            rect.setOnMouseExited(e -> rect.setFill(Color.TRANSPARENT));
            final int column = x;
            rect.setOnMouseClicked(e -> makeMove(new Connect4Circle(move), column));  
            list.add(rect);
        }
        pane.getChildren().addAll(list);
        stage.setTitle("Connect4");
        stage.setScene(new Scene(pane));
        stage.show();
    }
    /**
     * This method will place the piece onto the Connect4 board
     * 
     * @param circ the piece that will be placed on the Connect4 board
     * @param column the column that we will choose to play the move on
     */
    private void makeMove(Connect4Circle circ, int column)
    {
        int row = 5;
        while(row >= 0)
        {
        	if(!getBoardAt(column, row).isPresent())
        	{
                break;
        	}
            row--;
        }
        if(row < 0)
        {
            return;
        }
        board[row][column] = circ;
        pane.getChildren().add(circ);
        circ.setTranslateX(column * (80) + 75 / 4);
        final int currentRow = row;       
        TranslateTransition transition = new TranslateTransition(Duration.millis(10), circ);
        transition.setToY(row * (80) + 75 / 4);
        transition.setOnFinished(e -> 
        {
            if(winner(column, currentRow))
            {
            	if(move==true)
            	{
            		System.out.println("Red Wins");
            	}
            	else
            	{
            		System.out.println("Yellow Wins");
            	}
            	try 
            	{
					TimeUnit.SECONDS.sleep(3);
				} catch (InterruptedException e1) 
            	{
					e1.printStackTrace();
				}
            	Scanner in = new Scanner(System.in);
            	System.out.println("Thanks for playing! Press 0 to exit");
            	int exit = in.nextInt();
            	if(exit==0)
            	{
            		System.exit(-1);
            	}
            	in.close();
            }
            
            move = !move;
        });
        transition.play();
    }
    /**
     * This boolean method will return true if there is a winner
     * 
     * @param column the column of the move that will be checked
     * @param row the row of the move that will be checked
     * @return will return true if there is winner and false if there is not
     */
    private boolean winner(int column, int row)
    {
        List<Point2D> vertical = IntStream.rangeClosed(row - 3, row + 3)
                .mapToObj(r -> new Point2D(column, r))
                .collect(Collectors.toList());      
        List<Point2D> horizontal = IntStream.rangeClosed(column - 3, column + 3)
                .mapToObj(c -> new Point2D(c, row))
                .collect(Collectors.toList());
        Point2D topLeft = new Point2D(column - 3, row - 3);
        List<Point2D> diagonal1 = IntStream.rangeClosed(0, 6)
                .mapToObj(i -> topLeft.add(i, i))
                .collect(Collectors.toList());
        Point2D botLeft = new Point2D(column - 3, row + 3);
        List<Point2D> diagonal2 = IntStream.rangeClosed(0, 6)
                .mapToObj(i -> botLeft.add(i, -i))
                .collect(Collectors.toList());
        int chain = 0;
        for(Point2D p : vertical)
        {
            int c = (int) p.getX();
            int r = (int) p.getY();
            Connect4Circle circle = getBoardAt(c, r).orElse(new Connect4Circle(!move));
            if(circle.token == move)
            {
                chain++;
                if(chain == 4)
                {
                    return true;
                }
            } 
            else
            {
                chain = 0;
            }
        }
        for(Point2D p : horizontal)
        {
            int c = (int) p.getX();
            int r = (int) p.getY();
            
            Connect4Circle circle = getBoardAt(c, r).orElse(new Connect4Circle(!move));
            if(circle.token == move)
            {
                chain++;
                if(chain == 4)
                {
                    return true;
                }
            } 
            else
            {
                chain = 0;
            }
        }
        for(Point2D p : diagonal1)
        {
            int c = (int) p.getX();
            int r = (int) p.getY();
            
            Connect4Circle circle = getBoardAt(c, r).orElse(new Connect4Circle(!move));
            if(circle.token == move)
            {
                chain++;
                if(chain == 4)
                {
                    return true;
                }
            } 
            else
            {
                chain = 0;
            }
        }
        for(Point2D p : diagonal2)
        {
            int c = (int) p.getX();
            int r = (int) p.getY();
            
            Connect4Circle circle = getBoardAt(c, r).orElse(new Connect4Circle(!move));
            if(circle.token == move)
            {
                chain++;
                if(chain == 4)
                {
                    return true;
                }
            } 
            else
            {
                chain = 0;
            }
        }
        return false;
    }
   /**
     * This will return the specific piece that identifies the corresponding column and row
     * @param column column of the specific piece
     * @param row row of the specific piece
     * @return will return the specific piece that identifies the corresponding column and row
     */
    private Optional<Connect4Circle> getBoardAt(int column, int row)
    {
        if(column < 0 || column >= 7 || row < 0 || row >= 6)
        {
            return Optional.empty();
        }
        
        return Optional.ofNullable(board[row][column]);
    }
    /**
     * An inner class that will be the red or yellow piece that users will put on the connect4 board
     * 
     * @author jaehoon
     * @version version3
     *
     */
    private static class Connect4Circle extends Circle
    {
    	/** A boolean instant variable that will decide if it the piece is red or yellow */
        private final boolean token;
        
        /**
         * Constructor method
         * 
         * @param token a boolean that will decide if piece is red or yellow
         */
        public Connect4Circle(boolean token)
        {
            super(75/ 2, token? Color.RED : Color.YELLOW);
            this.token = token;       
            setCenterX(75 / 2);
            setCenterY(75 / 2);
        }    
    }
}