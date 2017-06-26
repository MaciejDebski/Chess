package pl.p.lodz.view.controls;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import pl.p.lodz.chess.GameState;
import pl.p.lodz.engine.ui.UIState;

/**
 * Created by Taberu on 2017-03-18.
 */
public class MainMenuController {
    @FXML
    private Button mainmenu_bt_play;
    @FXML
    private Button mainmenu_bt_switchprofile;
    @FXML
    private Button mainmenu_bt_options;
    @FXML
    private Button mainmenu_bt_exit;

    @FXML
    private void initialize() {
        mainmenu_bt_play.setOnMouseClicked((event) -> {
            GameState.getInstance().StartTheGame();
        });

        mainmenu_bt_switchprofile.setOnMouseClicked((event) -> {
            UIState.ChangeUI(UIState.UITypes.profiles);
        });

        mainmenu_bt_options.setOnMouseClicked((event) -> {
            UIState.ChangeUI(UIState.UITypes.options);
        });

        mainmenu_bt_exit.setOnMouseClicked((event) -> {
            UIState.ExitApp();
        });

    }

}
