
import java.util.Comparator;
import java.util.List;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HighScores extends Application {

	ObservableList<Score> scores;
	private TableView<Score> table = new TableView<>();
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		scores.sort(Comparator.comparingLong(Score::getScore).reversed());
		
		Scene scene = new Scene(new Group());
        primaryStage.setTitle("High Scores");
        primaryStage.setWidth(260);
        primaryStage.setHeight(435);
 
        
 
        table.setEditable(true);
 
        
        table.getItems().addAll(scores);
        
        TableColumn<Score, Long> totalScore = new TableColumn<Score, Long>("Total Score");
//        totalScore.setCellValueFactory(
//                new PropertyValueFactory<Score, Long>("score"));
        PropertyValueFactory<Score, Long> totalScoreCellValueFactory = new PropertyValueFactory<>("score");
        totalScore.setCellValueFactory(totalScoreCellValueFactory);
        
        TableColumn<Score, String> name = new TableColumn<Score, String>("Name");
//        name.setCellValueFactory(
//                new PropertyValueFactory<Score, String>("name"));
        PropertyValueFactory<Score, String> nameCellValueFactory = new PropertyValueFactory<>("name");
        name.setCellValueFactory(nameCellValueFactory);
        
        table.getColumns().addAll(name, totalScore);
        
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(0, 0, 0, 0));
        vbox.getChildren().addAll(table);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
 
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
		
	}

	
}
