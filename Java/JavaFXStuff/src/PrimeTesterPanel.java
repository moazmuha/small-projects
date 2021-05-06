import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

public class PrimeTesterPanel extends FlowPane implements EventHandler<ActionEvent> {

    TextField inputTxt;
    TextField outputTxt;

	public PrimeTesterPanel() {

        Button b;

        inputTxt = new TextField();
		inputTxt.setPrefColumnCount(5);
		this.getChildren().add(inputTxt);

		b = new Button("Is Prime?");
		this.getChildren().add(b);
        b.setOnAction(this);
        
        outputTxt = new TextField();
		outputTxt.setPrefColumnCount(10);
        this.getChildren().add(outputTxt);
        outputTxt.setEditable(false);
	}

    private boolean isPrime(int n){
		for(int i=2;i<n;i++){
			if(n%i == 0) return false;
		}
		return true;
    }

	public void handle(ActionEvent event) {
        try {
		int n=Integer.parseInt(this.inputTxt.getText());
        boolean isPrime = isPrime(n);
        if (isPrime){
            this.outputTxt.setText("yes");
        }else{this.outputTxt.setText("no");}
		} catch(NumberFormatException nfe){
            System.out.println("invalid number");
            this.outputTxt.setText("invalid number");
		}
		//tf2.setText(((Button) (event.getSource())).getText());
		return;
	}

}
