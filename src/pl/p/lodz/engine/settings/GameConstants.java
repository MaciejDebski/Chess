package pl.p.lodz.engine.settings;

import pl.p.lodz.engine.language.Language;
import pl.p.lodz.engine.language.Polish;

/**
 * Created by Taberu on 2017-03-26.
 */
public final class GameConstants {
    private GameConstants(){}

    // GLOBAL:

    public static final String DEFAULT_PROFILES_DIRECTORY = "./Profiles";
    public static final Language DEFAULT_LANGUAGE() {
        return new Polish();
    }



    // PROFILE DEFAULTS:

    public static final boolean SYSTEM_MAXIMIZED = false;
    public static final boolean SYSTEM_FULLSCREEN = false;

}
