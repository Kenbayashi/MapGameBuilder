import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class MapGameBuilder extends Application {
    Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        primaryStage.setTitle("Map Game Builder");

        Scene builder = setPage("BuilderController.fxml");
        primaryStage.setScene(builder);
        primaryStage.show();
    }

    //fxmlファイルの読み込み
    private Scene setPage(String fxml) {
        try {
            Pane newPane = (Pane)FXMLLoader.load(getClass().getResource(fxml));
            Scene newScene = new Scene(newPane);
            return newScene;
        } catch(Exception e) {
            System.err.println(e);
        }

        return null;
    }
}
