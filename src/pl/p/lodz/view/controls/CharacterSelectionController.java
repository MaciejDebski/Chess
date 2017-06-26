package pl.p.lodz.view.controls;

import pl.p.lodz.engine.ui.UIState;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

/**
 * Created by Taberu on 2017-03-30.
 */
public class CharacterSelectionController {
    @FXML
    private Button characterselection_bt_cancel;
    @FXML
    private Button characterselection_bt_createnew;
    @FXML
    private Button characterselection_bt_play;
    @FXML
    private ListView<String> characterselection_listview;

    @FXML
    private void initialize() {
        characterselection_bt_cancel.setOnMouseClicked(event -> {
           UIState.ChangeUI(UIState.UITypes.main_menu);
        });
        characterselection_bt_createnew.setOnMouseClicked(event -> {
            UIState.ChangeUI(UIState.UITypes.character_creation);
        });
        characterselection_bt_play.setOnMouseClicked(event -> {
            // TODO: Start the game with selected character;
        });

    }

}
