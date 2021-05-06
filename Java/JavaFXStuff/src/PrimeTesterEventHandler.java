import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;

public class PrimeTesterEventHandler implements EventHandler<ActionEvent> {
    private TextField inputTxt;
    private TextField outputTxt;

	public PrimeTesterEventHandler(TextField inputTxt, TextField outputTxt) {
        this.inputTxt = inputTxt;
        this.outputTxt = outputTxt;
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
		return;
	}
}
