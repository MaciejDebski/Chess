package pl.p.lodz.view.controls;

import pl.p.lodz.engine.ui.UIState;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;

import static pl.p.lodz.engine.ui.UIState.ShowDirectoryChooser;
import static pl.p.lodz.engine.settings.GlobalSettings.getGS;

/**
 * Created by Taberu on 2017-03-31.
 */
public class ProfilesDirectoryController {
    @FXML
    private Button profilesdirectory_bt_find;
    @FXML
    private TextField profilesdirectory_textfield_path;
    @FXML
    private Button profilesdirectory_bt_cancel;
    @FXML
    private Button profilesdirectory_bt_save;

    @FXML
    private void initialize() {
        try {
            profilesdirectory_textfield_path.setText(new File(getGS().PROFILES_DIRECTORY).getCanonicalPath());
        }
        catch(IOException e){
            e.printStackTrace(System.err);
            profilesdirectory_textfield_path.setText(new File(getGS().PROFILES_DIRECTORY).getAbsolutePath());
        }

        profilesdirectory_bt_find.setOnMouseClicked(event -> {
            try {
                profilesdirectory_textfield_path.setText(ShowDirectoryChooser(new File(getGS().PROFILES_DIRECTORY)).getPath());
            }
            catch(NullPointerException e){
                e.printStackTrace(System.err);
            }
        });

        profilesdirectory_bt_cancel.setOnMouseClicked(event -> {
            UIState.ChangeUI(UIState.UITypes.profiles);
        });

        profilesdirectory_bt_save.setOnMouseClicked(event -> {
            File NewPath = new File(profilesdirectory_textfield_path.getText());

            if(NewPath.isDirectory()){
                getGS().PROFILES_DIRECTORY = NewPath.getPath();
                getGS().SerializeGlobalSettings();
                UIState.ChangeUI(UIState.UITypes.profiles);
            }
            else {
                // TODO: Localize this text;
                UIState.ShowPopup(Alert.AlertType.WARNING, "The entered directory does not exist!");
            }
        });
    }
}
