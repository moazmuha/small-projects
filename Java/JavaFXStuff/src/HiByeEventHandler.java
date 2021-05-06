import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class HiByeEventHandler implements EventHandler<ActionEvent> {
	private TextField txt;

	public HiByeEventHandler(TextField txt) {
		this.txt = txt;
	}

	public void handle(ActionEvent event) {
		String msg = ((Button) (event.getSource())).getText() + " pressed ";
		this.txt.setText(msg);
		System.out.println(((Button) event.getSource()).getText () + " button pressed");
		return;
	}
}
