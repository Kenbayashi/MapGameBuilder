import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StartController {

    @FXML private Button startButton;

    public void startAction() {
        MapGame.getInstance().toGame();
    }

    public void startButtonAction(ActionEvent event) {
        startAction();
      }
}
