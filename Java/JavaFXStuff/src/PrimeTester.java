import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class PrimeTester extends Application {
    public static void main(String[] args) {
		launch(args);
    }

	@Override
	public void start(Stage stage) {
		initUI(stage);
    }

	private void initUI(Stage stage) {

		// Create a layout pane that contains the elements
		HBox pane = new HBox(5);
		pane.setPadding(new Insets(10));

		// Create the button
		Button isPrime_btn = new Button("Is Prime?");

		// Put the buttons and textfeild on the pane
		TextField inputTxt = new TextField();
        inputTxt.setPrefColumnCount(5);
        TextField outputTxt = new TextField();
        outputTxt.setPrefColumnCount(10);
        outputTxt.setEditable(false);
		
		// Put the text field on the pane
        pane.getChildren().add(inputTxt);
        pane.getChildren().add(isPrime_btn);
        pane.getChildren().add(outputTxt);

		// Hook up the event handler that defines 
		// what to do when the button is clicked.
		isPrime_btn.setOnAction(new PrimeTesterEventHandler(inputTxt, outputTxt));

		// Use pane as the root of the scene
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		
		// Set the title of the window
		stage.setTitle("Prime Number Checker");
		
		// Show the window
		stage.show();	
	}

}
