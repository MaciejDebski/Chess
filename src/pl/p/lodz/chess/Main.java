package pl.p.lodz.chess;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.p.lodz.engine.ui.UIState;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        UIState.Initialize(this, primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
