package pl.p.lodz.engine.profiles;

/**
 * Created by Taberu on 2017-03-27.
 */
public class ProfileNameAlreadyTaken extends Exception {
    public ProfileNameAlreadyTaken(){
        super("Profile with such name already exists!");
    }

    public ProfileNameAlreadyTaken(String profileName){
        this();
        ProfileName = profileName;
    }

    private String ProfileName;

    public String getProfileName(){
        return ProfileName;
    }
}
