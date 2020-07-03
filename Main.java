
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.plaf.synth.Region;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
    	
    	Button newGameButton = new Button("New game");
    	Button highScoreButton = new Button("High Score");
    	Button exitButton = new Button("Exit");
    	newGameButton.setMaxWidth(Double.MAX_VALUE);
    	highScoreButton.setMaxWidth(Double.MAX_VALUE);
    	exitButton.setMaxWidth(Double.MAX_VALUE);
    	Label welcomeLabel = new Label("Welcome to the Snake game!");
    	welcomeLabel.setFont(new Font("Times New Roman", 16));
    	welcomeLabel.setAlignment(Pos.CENTER);
    	
    	VBox vbButtons = new VBox();
    	
    	vbButtons.setSpacing(10);
    	vbButtons.setPadding(new Insets(10, 20, 10, 20));
    	vbButtons.getChildren().addAll(welcomeLabel, newGameButton, highScoreButton, exitButton);
    	
    	newGameButton.setOnAction(event -> {
    		primaryStage.close();
    		RowsColumns rnc = new RowsColumns();
    		try {
    			Stage primaryStage2 = new Stage();
				rnc.start(primaryStage2);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	});
    	ObservableList<Score> scores;
    	scores = FXCollections.observableArrayList();
    	highScoreButton.setOnAction(event -> {
    		//Source: https://stackoverflow.com/questions/27409718/java-reading-multiple-objects-from-a-file-as-they-were-in-an-array
			FileInputStream fileIn;
			try {
				fileIn = new FileInputStream("highscores.dat");
				
				boolean isExist = true;

			    while(isExist){
			    	ObjectInputStream ois = new ObjectInputStream(fileIn);
			        if(fileIn.available() != 0){
			         Score s = (Score) ois.readObject();    
			         scores.add(s);
			        }
			        else{
			        isExist =false;
			        }
			    }
			} catch (FileNotFoundException e2) {
			
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				
			}
			//End of information from mentioned source.
			Stage primaryStage2 = new Stage();
		    HighScores highScores = new HighScores();
		    highScores.scores = scores;
		    try {
				highScores.start(primaryStage2);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	});
    	
    	exitButton.setOnAction(event -> {
    		System.exit(0);
    	});
        Scene scene = new Scene(vbButtons, 235, 150);
        primaryStage.setTitle("Snake");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
    }


    public static void main(String[] args) {
        launch(args);
    }
}