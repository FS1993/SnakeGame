
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game extends Application {

	int rows, columns,
		blockSize = 40;
	int direction = 2;
	long score = 0,
		 time = 0;
	int counter = 0;
	int seconds = 0,
    	minutes = 0,
    	hours = 0,
    	mapSize;
	boolean isHit = false,
			qPressed = false,
			ctrlPressed = false,
			shiftPressed = false;
	ObservableList<Node> snake;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		mapSize = rows*columns;
		Pane root = new Pane();
		Label timeLabel = new Label("Time: 00:00:00");
		root.setPrefSize(columns*blockSize, rows*blockSize);
		Rectangle food = new Rectangle(blockSize, blockSize);
		Rectangle superFood = new Rectangle(blockSize, blockSize);
		ThreadLocalRandom random = ThreadLocalRandom.current();
		int randX = random.nextInt(0, columns)*blockSize;
		int randY = random.nextInt(0, rows)*blockSize;
		food.setFill(Color.RED);	
		food.setTranslateX(randX);
		food.setTranslateY(randY);
		superFood.setFill(Color.DEEPPINK);	
		superFood.setTranslateX(-40);
		superFood.setTranslateY(-40);
		
		Rectangle head = new Rectangle(blockSize, blockSize);
		head.setFill(Color.DARKGREEN);
		Group snakeBody = new Group();
		snakeBody.getChildren().add(head);
		snake = snakeBody.getChildren();
		Timeline timeline = new Timeline();
		
		KeyFrame frame = new KeyFrame(Duration.seconds(0.2), event -> {
			Node tail = null;
			if (snake.size() > 0)
				tail = snake.get(snake.size()-1);
			
			for (int i = snake.size()-1; i >= 1; i--) {
				snake.get(i).setTranslateY(snake.get(i-1).getTranslateY());
				snake.get(i).setTranslateX(snake.get(i-1).getTranslateX());
			}
		
			double x = 0;
			double y = 0;
			if (snake.size() > 0) {
				x = tail.getTranslateX();
				y = tail.getTranslateY();
			}
			switch(direction) {
			
			case 0:
				head.setTranslateY(head.getTranslateY()-blockSize);
				break;
			case 1:
				
				head.setTranslateY(head.getTranslateY()+blockSize);
				break;
			case 2:

				head.setTranslateX(head.getTranslateX()+blockSize);
				break;
			case 3:	

				head.setTranslateX(head.getTranslateX()-blockSize);
				break;
			}
			
			for (Node part : snake) {
				if ((part != head) && (head.getTranslateX() == part.getTranslateX()) && (head.getTranslateY() == part.getTranslateY())){
					
					if(isHit == false) {
						NamePrompt namePrompt = new NamePrompt();
						namePrompt.score = (Long) (score/mapSize)-time;
						try {
			    			Stage primaryStage2 = new Stage();
			    			namePrompt.start(primaryStage2);
		    			}
		    			catch (Exception e2) {
		    				e2.printStackTrace();
		    			}
						isHit = true;
						primaryStage.close();
					}
				}
			}
			if(head.getTranslateX() == food.getTranslateX() && head.getTranslateY() == food.getTranslateY()) {
				
				food.setTranslateX(random.nextInt(0, columns)*blockSize);
				food.setTranslateY(random.nextInt(0, rows)*blockSize);
				
				for(int i = 0; i < snake.size(); i++) {
					if ((food.getTranslateX() == snake.get(i).getTranslateX()) && (food.getTranslateY() == snake.get(i).getTranslateY())) {
						food.setTranslateX(random.nextInt(0, columns)*blockSize);
						food.setTranslateY(random.nextInt(0, rows)*blockSize);
						i = 0;
					}
				}
				Rectangle part = new Rectangle(blockSize, blockSize);
				snake.add(part);
				score+=1000;
				counter++;
				
				part.setFill(Color.DARKGREEN);
				part.setTranslateX(x);
				part.setTranslateY(y);
			}
			
			if (counter != 0 && counter % 10 == 0) {
				superFood.setTranslateX(random.nextInt(0, columns)*blockSize);
				superFood.setTranslateY(random.nextInt(0, rows)*blockSize);
				for(int i = 0; i < snake.size(); i++) {
					if ((superFood.getTranslateX() == snake.get(i).getTranslateX()) && (food.getTranslateY() == snake.get(i).getTranslateY())) {
						superFood.setTranslateX(random.nextInt(0, columns)*blockSize);
						superFood.setTranslateY(random.nextInt(0, rows)*blockSize);
						i = 0;
					}
					else if ((superFood.getTranslateX() == food.getTranslateX()) && (superFood.getTranslateY() == food.getTranslateY())) {
						superFood.setTranslateX(random.nextInt(0, columns)*blockSize);
						superFood.setTranslateY(random.nextInt(0, rows)*blockSize);
						i = 0;
					}
				}
				
				counter++;
			}
			
			if (head.getTranslateX() == superFood.getTranslateX() && head.getTranslateY() == superFood.getTranslateY()) {
				superFood.setTranslateX(-40);
				superFood.setTranslateY(-40);
				if(snake.size() > 1) {
					snake.remove(snake.size()-1);
				}
				score += 1000;
			}
			if(head.getTranslateX()  >= blockSize*columns || head.getTranslateX() < 0 ||
			   head.getTranslateY()  >= blockSize*rows || head.getTranslateY() < 0) {
				if(isHit == false) {
					NamePrompt namePrompt = new NamePrompt();
					namePrompt.score = (Long) (score/mapSize)-time;
					try {
		    			Stage primaryStage2 = new Stage();
		    			
		    			namePrompt.start(primaryStage2);
	    			}
	    			catch (Exception e2) {
	    				e2.printStackTrace();
	    			}
					isHit = true;
					primaryStage.close();
				}
			}
		});
		
		KeyFrame timerFrame = new KeyFrame(Duration.seconds(1), event->{
			time++;
			seconds++;
			if (seconds == 60) {
				seconds = 0;
				minutes++;
			}
			
			if (minutes == 60) {
				minutes = 0;
				hours++;
			}	
			if (seconds < 10 && minutes < 10 && hours < 10)
				timeLabel.setText("Time: 0"+hours+":0"+minutes+":0"+seconds);
			else if (seconds >= 10 && minutes < 10 && hours < 10)
				timeLabel.setText("Time: 0"+hours+":0"+minutes+":"+seconds);
			else if (seconds < 10 && minutes >= 10 && hours < 10)
				timeLabel.setText("Time: 0"+hours+":"+minutes+":0"+seconds);
			else if (seconds >= 10 && minutes >= 10 && hours < 10)
				timeLabel.setText("Time: 0"+hours+":"+minutes+":"+seconds);
			else if (seconds < 10 && minutes >= 10 && hours >= 10)
				timeLabel.setText("Time: "+hours+":"+minutes+":0"+seconds);
			else if (seconds < 10 && minutes < 10 && hours >= 10)
				timeLabel.setText("Time: "+hours+":0"+minutes+":0"+seconds);
			else if (seconds >= 10 && minutes >= 10 && hours >= 10)
				timeLabel.setText("Time: "+hours+":"+minutes+":"+seconds);
			else if (seconds < 10 && minutes >= 10 && hours >= 10)
				timeLabel.setText("Time: "+hours+":"+minutes+":0"+seconds);
			
			
		}
		);
		Timeline timeline2 = new Timeline();
		timeline2.getKeyFrames().add(timerFrame);
		timeline2.setCycleCount(Timeline.INDEFINITE);
		
		timeline.getKeyFrames().add(frame);
		timeline.setCycleCount(Timeline.INDEFINITE);
		root.getChildren().addAll(timeLabel, food, snakeBody, superFood);
		Scene scene = new Scene(root);
		timeline.play();
		timeline2.play();
		scene.setOnKeyPressed(event -> {
		
			switch(event.getCode()) {
			case W:
				if (direction != 1)
					direction = 0;
				break;
			case S:
				if (direction != 0)
					direction = 1;
				break;
			case A:
				if (direction != 2)
					direction = 3;
				break;
			case D:
				if (direction != 3)
					direction = 2;
				break;
			case CONTROL:
				ctrlPressed = true;
				if (ctrlPressed == true && shiftPressed == true && qPressed == true) {
					timeline.pause();
					timeline2.pause();
					primaryStage.close();
					Main main = new Main();
	    			try {
		    			Stage primaryStage2 = new Stage();
		    			main.start(primaryStage2);
	    			}
	    			catch (Exception e2) {
	    				e2.printStackTrace();
	    			}	
				}
				break;
			case Q:
				qPressed = true;
				if (ctrlPressed == true && shiftPressed == true && qPressed == true) {
					timeline.pause();
					timeline2.pause();
					primaryStage.close();
					Main main = new Main();
	    			try {
		    			Stage primaryStage2 = new Stage();
		    			main.start(primaryStage2);
	    			}
	    			catch (Exception e2) {
	    				e2.printStackTrace();
	    			}	
				}
				break;
			case SHIFT:
				shiftPressed = true;
				if (ctrlPressed == true && shiftPressed == true && qPressed == true) {
					timeline.pause();
					timeline2.pause();
					primaryStage.close();
					Main main = new Main();
	    			try {
		    			Stage primaryStage2 = new Stage();
		    			main.start(primaryStage2);
	    			}
	    			catch (Exception e2) {
	    				e2.printStackTrace();
	    			}	
				}
				break;
			}
			
		});
		scene.setOnKeyReleased(event -> {
			switch(event.getCode()) {
			case CONTROL:
				ctrlPressed = false;
				break;
			case Q:
				qPressed = false;
				break;
			case SHIFT:
				shiftPressed = false;
				break;
			}
		});
		
        primaryStage.setTitle("Snake");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
	}

}
