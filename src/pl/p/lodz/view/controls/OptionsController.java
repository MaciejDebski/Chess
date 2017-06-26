package pl.p.lodz.view.controls;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import pl.p.lodz.engine.language.LanguageNotSupported;
import pl.p.lodz.engine.settings.ProfileSettings;
import pl.p.lodz.engine.ui.UIState;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

import static pl.p.lodz.engine.language.Language.GetLanguageByName;
import static pl.p.lodz.engine.language.Language.ListAllAvailableLanguages;
import static pl.p.lodz.engine.settings.GameConstants.DEFAULT_LANGUAGE;
import static pl.p.lodz.engine.settings.GlobalSettings.getGS;
import static pl.p.lodz.engine.settings.ProfileSettings.getProfileSettings;

/**
 * Created by Taberu on 2017-03-28.
 */
public class OptionsController {
    @FXML
    private CheckBox options_chb_maximized;
    @FXML
    private CheckBox options_chb_fullscreen;
    @FXML
    private Button options_bt_cancel;
    @FXML
    private Button options_bt_save;
    @FXML
    private ComboBox<String> options_combobox_language;
    @FXML
    private ChoiceBox<Integer> options_choicebox_gametime;
    @FXML
    private CheckBox options_checkbox_showpossiblemoveshades;
    @FXML
    private CheckBox options_checkbox_inverttheboard;

    @FXML
    private void initialize() {
        for(String languageName : ListAllAvailableLanguages()){
            options_combobox_language.getItems().add(languageName);
        }
        options_combobox_language.getSelectionModel().select(getGS().LANGUAGE.ShortName());

        try {
            ProfileSettings ps = getProfileSettings();
            options_chb_fullscreen.setSelected(ps.system_fullscreen);
            options_chb_maximized.setSelected(ps.system_maximized);
        }
        catch(NullPointerException e){
            e.printStackTrace(System.err);
        }

        options_checkbox_showpossiblemoveshades.setSelected(getProfileSettings().getRules().isShowPossibleMoveShades());

        options_choicebox_gametime.getItems().addAll(0, 3, 5, 7, 10, 15, 20, 30, 60);
        options_choicebox_gametime.setValue(getProfileSettings().getRules().getTimerLimit());

        options_checkbox_inverttheboard.setSelected(getProfileSettings().getRules().isInvertTheBoard());

        options_bt_cancel.setOnMouseClicked(event -> {
            UIState.ChangeUI(UIState.GetPreviousUI());
        });

        options_bt_save.setOnMouseClicked(event -> {
            try{
                // SCREEN
                ProfileSettings ps = getProfileSettings();
                ps.system_maximized = options_chb_maximized.isSelected();
                ps.system_fullscreen = options_chb_fullscreen.isSelected();

                // LANGUAGE
                if(options_combobox_language.getValue() != null) {
                    getGS().LANGUAGE = GetLanguageByName(options_combobox_language.getValue());
                }

                // GAMEPLAY
                ps.getRules().setShowPossibleMoveShades(options_checkbox_showpossiblemoveshades.isSelected());
                ps.getRules().setTimerLimit(options_choicebox_gametime.getValue());
                ps.getRules().setInvertTheBoard(options_checkbox_inverttheboard.isSelected());
            }
            catch(NullPointerException e){
                throw e;
            }
            catch(LanguageNotSupported e){
                getGS().LANGUAGE = DEFAULT_LANGUAGE();
            }

            getGS().SerializeGlobalSettings();

            UIState.ChangeUI(UIState.GetPreviousUI());
        });



    }

}
