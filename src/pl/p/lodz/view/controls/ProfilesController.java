package pl.p.lodz.view.controls;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import pl.p.lodz.chess.GameState;
import pl.p.lodz.engine.profiles.Profile;
import pl.p.lodz.engine.serialization.SerializationLoadError;
import pl.p.lodz.engine.ui.UIState;

/**
 * Created by Taberu on 2017-03-20.
 */
public class ProfilesController {
    @FXML
    private ComboBox<String> profiles_combobox;
    @FXML
    private Button profiles_bt_newprofile;
    @FXML
    private Button profiles_bt_useselected;
    @FXML
    private Button profiles_bt_choosepath;
    @FXML
    private Button profiles_bt_exit;
    @FXML
    private Text profiles_label_warning;

    @FXML
    private void initialize() {
        Profile.RefreshListOfFiles();
        for (String ProfileName : Profile.GetAvailableProfileNames()) {
            profiles_combobox.getItems().add(ProfileName);
        }

        profiles_combobox.setOnAction(ActionEvent ->
            profiles_label_warning.setVisible(false)
        );

        profiles_bt_useselected.setOnMouseClicked(event -> {
            if(profiles_combobox.getValue() == null) {
                UIState.ChangeUI(UIState.UITypes.new_profile);
            }

            try{
                Profile.SwitchProfile(profiles_combobox.getValue());
                GameState.getInstance().StartTheMainMenu();
            }
            catch(SerializationLoadError e){
                profiles_label_warning.setVisible(true);
                profiles_label_warning.setText("Profile can't be loaded! It's corrupted or there is no such file!");
            }
        });

        profiles_bt_newprofile.setOnMouseClicked(event -> {
            UIState.ChangeUI(UIState.UITypes.new_profile);
        });

        profiles_bt_choosepath.setOnMouseClicked(event -> {
            UIState.ChangeUI(UIState.UITypes.profiles_directory);
        });

        profiles_bt_exit.setOnMouseClicked(event -> {
           UIState.ExitApp();
        });

    }

}
