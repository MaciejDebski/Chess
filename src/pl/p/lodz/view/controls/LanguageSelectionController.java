package pl.p.lodz.view.controls;

import pl.p.lodz.engine.language.LanguageNotSupported;
import pl.p.lodz.engine.ui.UIState;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import static pl.p.lodz.engine.language.Language.GetLanguageByName;
import static pl.p.lodz.engine.language.Language.ListAllAvailableLanguages;
import static pl.p.lodz.engine.settings.GameConstants.DEFAULT_LANGUAGE;
import static pl.p.lodz.engine.settings.GlobalSettings.getGS;

/**
 * Created by Taberu on 2017-03-31.
 */
public class LanguageSelectionController {
    @FXML
    private Button languageselection_bt_OK;
    @FXML
    private ComboBox<String> languageselection_combobox_language;

    @FXML
    private void initialize() {
        for(String languageName : ListAllAvailableLanguages()){
            languageselection_combobox_language.getItems().add(languageName);
        }

        languageselection_bt_OK.setOnMouseClicked(event -> {
            UIState.ChangeUI(UIState.UITypes.profiles);

            try{
                getGS().LANGUAGE = GetLanguageByName(languageselection_combobox_language.getValue());
            }
            catch(LanguageNotSupported e){
                getGS().LANGUAGE = DEFAULT_LANGUAGE();
            }

            getGS().SerializeGlobalSettings();
        });

        languageselection_combobox_language.setOnAction(event -> {
           languageselection_bt_OK.setDisable(false);
        });

    }
}
