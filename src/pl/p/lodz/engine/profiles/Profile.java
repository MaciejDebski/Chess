package pl.p.lodz.engine.profiles;

import pl.p.lodz.chess.NoCurrentGameProgress;
import pl.p.lodz.chess.rules.board.Board;
import pl.p.lodz.engine.serialization.SerializationHelper;
import pl.p.lodz.engine.serialization.SerializationLoadError;
import pl.p.lodz.engine.serialization.SerializationSaveError;
import pl.p.lodz.engine.settings.GameConstants;
import pl.p.lodz.engine.settings.ProfileSettings;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import static pl.p.lodz.engine.settings.GlobalSettings.getGS;

/**
 * Created by Taberu on 2017-03-19.
 */
public class Profile implements java.io.Serializable {
    public Profile(String ProfileName){
        this.ProfileName = ProfileName;
    }

    private String ProfileName;
    private boolean bIsSerialized;
    private ProfileSettings CurrentSettings = new ProfileSettings();
    private Board CurrentGameProgress;

    public String toString(){
        return ProfileName;
    }

    public ProfileSettings GetCurrentProfileSettings(){
        return this.CurrentSettings;
    }
    public Board getCurrentGameProgerss() throws NoCurrentGameProgress {
        if(CurrentGameProgress == null){
            throw new NoCurrentGameProgress();
        }
        return CurrentGameProgress;
    }

    public void saveCurrentGameProgress(Board newProgress){
        CurrentGameProgress = newProgress;
        SerializeProfile(this);
    }

    // STATIC INTERFACE:

    public static ArrayList<File> GetAllAvailableProfiles() {
        return ProfileFiles;
    }

    public static Integer GetNumberOfAvailableProfiles(){
        return ProfileFiles.size();
    }

    public static String[] GetAvailableProfileNames(){
        String ProfileNameList[];
        ProfileNameList = new String[ProfileFiles.size()];
        for(int i = 0; i < ProfileNameList.length; ++i){
            // TODO: find better way to erase file extensions (.ser);
            ProfileNameList[i] = ProfileFiles.get(i).getName().substring(0, ProfileFiles.get(i).getName().length() - 4);
            System.out.print("Name: ");
            System.out.println(ProfileFiles.get(i).getName());
        }
        return ProfileNameList;
    }

    public static void CreateNewProfile(String NewProfileName) throws ProfileNameAlreadyTaken {
        if(IsProfileNameTaken(NewProfileName)){
            throw new ProfileNameAlreadyTaken(NewProfileName);
        }

        Profile NewProfile = new Profile(NewProfileName);
        try{
            ProfileFiles.add(SerializeProfile(NewProfile));
        }
        catch(SerializationSaveError error){
            System.out.println(error.getMessage());
            error.printStackTrace();
        }
    }

    public static File SerializeProfile(Profile ProfileToSerialize) throws NullPointerException, SerializationSaveError {
        try {
            SerializationHelper.Serialize(ProfileToSerialize, getGS().PROFILES_DIRECTORY + File.separator + ProfileToSerialize.ProfileName + ".ser");
            ProfileToSerialize.bIsSerialized = true;
        }catch(SerializationSaveError | NullPointerException error) {
            throw error;
        }
        return new File(ProfileToSerialize.ProfileName + ".ser");
    }

    public static Profile DeserializeProfile(String ProfileName) throws SerializationLoadError {
        Profile Deserialized;
        try {
            Deserialized = (Profile) SerializationHelper.Deserialize(getGS().PROFILES_DIRECTORY + File.separator + ProfileName + ".ser");
        }catch(SerializationLoadError error) {
            throw error;
        }
        return Deserialized;
    }

    public static Profile GetCurrentProfile() {
        if(CurrentProfile != null){
            CurrentProfile.bIsSerialized = false;
        }
        return CurrentProfile;
    }

    public static void SetCurrentProfile(Profile NewProfile) throws DifferentProfileUsed {
        if(CurrentProfile == null && NewProfile != null){
            CurrentProfile = NewProfile;
            return;
        }
        if(CurrentProfile != NewProfile) {
            throw new DifferentProfileUsed();
        }
    }

    public static void LoadAndSetProfile(String ProfileName) throws DifferentProfileUsed, SerializationLoadError {
        if(ProfileName == null){
            return;
        }

        try {
            SetCurrentProfile( DeserializeProfile(ProfileName) );
        }
        catch(SerializationLoadError | DifferentProfileUsed error){
            throw error;
        }
    }

    public static boolean IsCurrentProfile(String ProfileName){
        return CurrentProfile != null && ProfileName.equals(CurrentProfile.ProfileName);
    }

    public static void SwitchProfile(String ProfileName) throws SerializationLoadError {
        if(IsCurrentProfile(ProfileName)){
            return;
        }

        try{
            if(CurrentProfile != null && !CurrentProfile.bIsSerialized){
                SerializeProfile(CurrentProfile);
            }
            CurrentProfile = DeserializeProfile(ProfileName);
        }
        catch(SerializationLoadError error){
            throw error;
        }
    }

    public static void RefreshListOfFiles(){
        ProfileFiles.clear();
        ProfileFiles.addAll(ListAllFiles());
    }

    public static boolean IsProfileNameTaken(String ProfileName) {
        RefreshListOfFiles();
        for(File current : ProfileFiles ){
            if (current.getName().equals(ProfileName + ".ser")) {
                return true;
            }
        }
        return false;
    }


    // PRIVATE STATIC:

    private static void InitializeListOfFiles(){
        if(ProfileFiles.isEmpty()){
            ProfileFiles.addAll(ListAllFiles());
        }
    }

    private static ArrayList<File> ListAllFiles(){
        File ProfilesDirectory = new File(getGS().PROFILES_DIRECTORY);
        if(!ProfilesDirectory.isDirectory()){
            CreateAndSetDefaultDirectory();
            ProfilesDirectory = new File(getGS().PROFILES_DIRECTORY);
        }

        File[] FilteredFiles = ProfilesDirectory.listFiles( (File dir, String filename) -> {
            System.out.println(filename);
            return filename.endsWith(".ser");
        });
        return new ArrayList<> (Arrays.asList(FilteredFiles));
    }

    private static void CreateAndSetDefaultDirectory() {
        getGS().PROFILES_DIRECTORY = GameConstants.DEFAULT_PROFILES_DIRECTORY;
        File defaultDirectory = new File(getGS().PROFILES_DIRECTORY);
        if(!defaultDirectory.isDirectory() ){
            defaultDirectory.mkdir();
        }
    }

    private static ArrayList<File> ProfileFiles = new ArrayList<>();
    private static Profile CurrentProfile;

}
