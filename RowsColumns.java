
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class RowsColumns extends Application {

	private int minRows = 10,
				maxRows = 19,
				minCols = 10,
				maxCols = 35;
	@Override
	public void start(Stage primaryStage) throws Exception {
		
    	Button playButton = new Button("Play");
    	playButton.setMaxWidth(Double.MAX_VALUE);
    	Label infoLabel = new Label("Type in preferred size in columns and rows.");
    	Label columnsLabel = new Label("Columns:");
    	TextArea columnsTextArea = new TextArea();
    	columnsTextArea.resize(10, 30);
    	Label rowsLabel = new Label("Rows:");
    	TextArea rowsTextArea = new TextArea();
    	rowsTextArea.setMaxHeight(30);
    	columnsTextArea.setMaxHeight(30);
    	//Source: https://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
    	rowsTextArea.textProperty().addListener(new ChangeListener<String>() {
    	    @Override
    	    public void changed(ObservableValue<? extends String> observable, String oldValue, 
    	        String newValue) {
    	        if (!newValue.matches("\\d*")) {
    	            rowsTextArea.setText(newValue.replaceAll("[^\\d]", ""));
    	        }
    	    }
    	});
    	
    	columnsTextArea.textProperty().addListener(new ChangeListener<String>() {
    	    @Override
    	    public void changed(ObservableValue<? extends String> observable, String oldValue, 
    	        String newValue) {
    	        if (!newValue.matches("\\d*")) {
    	            columnsTextArea.setText(newValue.replaceAll("[^\\d]", ""));
    	        }
    	    }
    	});
    	//End of information from the source mentioned above.
    	VBox vbContent = new VBox();
    	
    	vbContent.setSpacing(10);
    	vbContent.setPadding(new Insets(0, 20, 10, 20));
    	vbContent.getChildren().addAll(infoLabel, columnsLabel, columnsTextArea, rowsLabel, rowsTextArea, playButton);
    	
    	playButton.setOnAction(event -> {
    		try {
	    		Integer rows = Integer.parseInt(rowsTextArea.getText());
	    		Integer columns = Integer.parseInt(columnsTextArea.getText());
	    		if (rows < minRows) {
	    			Alert alert = new Alert(AlertType.WARNING, "Minimum rows' value is "+minRows+".");
	        		alert.show();
	    		}
	    		if (columns < minCols) {
	    			Alert alert = new Alert(AlertType.WARNING, "Minimum columns' value is "+minCols+".");
	        		alert.show();
	    		}
	    		if (rows > maxRows) {
	    			Alert alert = new Alert(AlertType.WARNING, "Maximum rows' value is "+maxRows+".");
	        		alert.show();
	    		}
	    		if (columns > maxCols) {
	    			Alert alert = new Alert(AlertType.WARNING, "Maximum columns' value is "+maxCols+".");
	        		alert.show();
	    		}
	    		if((rows >= minRows && rows <= maxRows) && (columns >= minCols && columns <= maxCols)) {

	    			Game game = new Game();
	    			game.rows = rows;
	    			game.columns = columns;
	    			try {
		    			Stage primaryStage2 = new Stage();
		    			game.start(primaryStage2);
	    			}
	    			catch (Exception e2) {
	    				e2.printStackTrace();
	    			}
	    		}
	    		primaryStage.close();
    		}
    		catch (Exception e) {
    			Alert alert = new Alert(AlertType.WARNING, "Values are missing.");
        		alert.show();
    		}	
    	});
    	
        Scene scene = new Scene(vbContent, 270, 220);
        primaryStage.setTitle("Choose size");
        primaryStage.setScene(scene);
        primaryStage.show();
        //primaryStage.setResizable(false);
    }
	
	

}
