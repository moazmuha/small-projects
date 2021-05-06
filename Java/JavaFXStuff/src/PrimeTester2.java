import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PrimeTester2 extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {

		VBox pane = new VBox(5);
		pane.setPadding(new Insets(5));

		pane.getChildren().add(new PrimeTesterPanel());

		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.setTitle("Prime Number Checker");
		stage.show();
	}
}
