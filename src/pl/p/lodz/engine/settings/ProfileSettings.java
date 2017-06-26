package pl.p.lodz.engine.settings;

import pl.p.lodz.chess.rules.board.Rules;
import pl.p.lodz.engine.profiles.Profile;

import java.io.Serializable;

import static pl.p.lodz.engine.settings.GameConstants.SYSTEM_FULLSCREEN;
import static pl.p.lodz.engine.settings.GameConstants.SYSTEM_MAXIMIZED;

/**
 * Created by Taberu on 2017-03-27.
 */
public class ProfileSettings implements Serializable {
    public boolean system_maximized = SYSTEM_MAXIMIZED;
    public boolean system_fullscreen = SYSTEM_FULLSCREEN;

    private Rules rules = new Rules();

    public Rules getRules() {
        return rules;
    }

    public static ProfileSettings getProfileSettings(){
        if(Profile.GetCurrentProfile() == null){
            throw new NullPointerException("Profile is not loaded and you can't get the settings!");
        }

        return Profile.GetCurrentProfile().GetCurrentProfileSettings();
    }


    private void ResetProfileSettings(){
        system_maximized = SYSTEM_MAXIMIZED;
        system_fullscreen = SYSTEM_FULLSCREEN;
    }

}
