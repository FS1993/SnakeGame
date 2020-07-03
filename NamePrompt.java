
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

import javax.swing.SwingUtilities;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NamePrompt extends Application {
	
	Long score;
	ObservableList<Score> scores;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Button submitButton = new Button("Submit");
    	submitButton.setMaxWidth(Double.MAX_VALUE);
    	Label infoLabel = new Label("Game over. Type in your name below in order to save your score.");
    	TextArea nameTextArea = new TextArea();
    	nameTextArea.setMaxHeight(30);
    	VBox vbContent = new VBox();
    	vbContent.setSpacing(10);
    	vbContent.setPadding(new Insets(0, 20, 10, 20));
    	vbContent.getChildren().addAll(infoLabel, nameTextArea, submitButton);
    	scores = FXCollections.observableArrayList();
    	submitButton.setOnAction(event ->{
    		Score totalScore = new Score();
    		totalScore.setName(nameTextArea.getText());
    		totalScore.setScore(this.score);
    		try {
		         FileOutputStream fileOut =
		         new FileOutputStream("highscores.dat", true);
		         ObjectOutputStream out = new ObjectOutputStream(fileOut);
		         out.writeObject(totalScore);
		         out.close();
		         fileOut.close();
		      } catch (IOException i) {
		         i.printStackTrace();
		      }
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
				e2.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				
			}
			//End of information from mentioned source.
			primaryStage.close();
			Stage primaryStage2 = new Stage();
		    HighScores highScores = new HighScores();
		    highScores.scores = scores;
		    try {
				highScores.start(primaryStage2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		    
    		
    		
    	});
    	Scene scene = new Scene(vbContent, 400, 120);
        primaryStage.setTitle("Game Over");
        primaryStage.setScene(scene);
        primaryStage.show();
	}

}
