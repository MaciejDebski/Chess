package pl.p.lodz.engine.ui;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import pl.p.lodz.engine.settings.GlobalSettings;
import pl.p.lodz.chess.Main;


/**
 * Created by Taberu on 2017-03-19.
 */
public class UIState {

    private static final String MainWindowTitle = "Chess";

    private static Main MainAppRef;
    private static Stage PrimaryStage;
    private static UITypes CurrentUI;
    private static UITypes PreviousUI;

    public enum UITypes {
        language_selection,
        main_menu,
        profiles,
        profiles_directory,
        new_profile,
        options,
        character_creation,
        character_selection,
        chess_board
    }

    public static void ChangeUI(UITypes type) {
        PreviousUI = CurrentUI;
        CurrentUI = type;

        switch (type) {
            case language_selection: {
                ShowUI_("../view/controls/LanguageSelection.fxml");
                break;
            }
            case main_menu: {
                ShowUI_("../view/controls/MainMenu.fxml");
                break;
            }
            case profiles: {
                ShowUI_("../view/controls/Profiles.fxml");
                break;
            }
            case profiles_directory: {
                ShowUI_("../view/controls/ProfilesDirectory.fxml");
                break;
            }
            case new_profile: {
                ShowUI_("../view/controls/NewProfile.fxml");
                break;
            }
            case options: {
                ShowUI_("../view/controls/Options.fxml");
                break;
            }
            case character_creation: {
                ShowUI_("../view/controls/CharacterCreation.fxml");
                break;
            }
            case character_selection: {
                ShowUI_("../view/controls/CharacterSelection.fxml");
                break;
            }
            case chess_board: {
                ShowUI_("../view/chess/Board.fxml");
                break;
            }


        }
    }

    public static UITypes GetPreviousUI(){
        return PreviousUI;
    }

    public static File ShowDirectoryChooser(File defaultDirectory) throws NullPointerException {
        DirectoryChooser chooser = new DirectoryChooser();
        // TODO: Localize this text;
        chooser.setTitle("Pick profiles directory");
        if(defaultDirectory != null && defaultDirectory.isDirectory()) {
            chooser.setInitialDirectory(defaultDirectory);
        }
        else{
            chooser.setInitialDirectory(new File("."));
        }
        File choseDirectory = chooser.showDialog(PrimaryStage);
        if(choseDirectory == null){
            throw new NullPointerException("DirectoryChooser returned null pointer!");
        }
        return choseDirectory;
    }

    public static void ShowPopup(Alert.AlertType PopupType, String Message){
        Alert alert = new Alert(PopupType, Message);
        alert.showAndWait();
    }

    public static void SetMainAppRef(Main NewMainAppRef){
        MainAppRef = NewMainAppRef;
    }

    public static void SetPrimaryStage(Stage NewPrimaryStage){
        PrimaryStage = NewPrimaryStage;
    }

    public static void Initialize(Main NewMainAppRef, Stage NewPrimaryStage){
        if(NewMainAppRef == null || NewPrimaryStage == null){
            return;
        }

        MainAppRef = NewMainAppRef;
        PrimaryStage = NewPrimaryStage;

        if(GlobalSettings.getGS().LANGUAGE == null) {
            ChangeUI(UITypes.language_selection);
        }
        else {
            ChangeUI(UITypes.profiles);
        }
    }

    public static void ExitApp(){
        Platform.exit();
        System.exit(0);
    }

    private static void ShowUI_(String UILocation) {
        if(PrimaryStage == null || MainAppRef == null || UILocation.isEmpty() ){
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(MainAppRef.getClass().getResource(UILocation));
            Parent root = loader.load();
            PrimaryStage.setTitle(MainWindowTitle);
            PrimaryStage.setScene(new Scene(root, 300, 275));
            PrimaryStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static Stage getPrimaryStage() {
        return PrimaryStage;
    }

}
