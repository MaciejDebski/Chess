package pl.p.lodz.view.controls;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import pl.p.lodz.engine.profiles.Profile;
import pl.p.lodz.engine.profiles.ProfileNameAlreadyTaken;
import pl.p.lodz.engine.ui.UIState;

/**
 * Created by Taberu on 2017-03-25.
 */
public class NewProfileController {
    @FXML
    private TextField newprofile_profilename;
    @FXML
    private Button newprofile_bt_create;
    @FXML
    private Button newprofile_bt_cancel;
    @FXML
    private Label newprofile_label_warning;

    @FXML
    private void initialize() {
        newprofile_bt_create.setOnMouseClicked(event -> {
            CreateNewProfile();
        });

        newprofile_profilename.setOnKeyPressed((event)->{
            if(event.getCode() == KeyCode.ENTER){
                CreateNewProfile();
            }
        });

        newprofile_bt_cancel.setOnMouseClicked(event -> {
            UIState.ChangeUI(UIState.UITypes.profiles);
        });
    }

    private void CreateNewProfile(){
        if(newprofile_profilename.getText().matches("[a-zA-Z[0-9]]+") ){
            newprofile_label_warning.setVisible(false);
            try{
                Profile.CreateNewProfile(newprofile_profilename.getText());
            }
            catch(ProfileNameAlreadyTaken taken){
                newprofile_label_warning.setText("Profile named \"" + taken.getProfileName() + "\" already exists!");
                newprofile_label_warning.setVisible(true);
                return;
            }
            UIState.ChangeUI(UIState.UITypes.profiles);
        }
        else{
            newprofile_label_warning.setVisible(true);
            newprofile_label_warning.setText("Your name has to contain only letters and numbers!");
        }
    }

}
