package pl.p.lodz.engine.settings;

import pl.p.lodz.engine.language.Language;
import pl.p.lodz.engine.serialization.SerializationHelper;
import pl.p.lodz.engine.serialization.SerializationLoadError;

import java.io.Serializable;

/**
 * Created by Taberu on 2017-03-27.
 */
public class GlobalSettings implements Serializable {
    public String PROFILES_DIRECTORY = GameConstants.DEFAULT_PROFILES_DIRECTORY;
    public Language LANGUAGE;

    public void SerializeGlobalSettings() {
        SerializationHelper.Serialize(GS, "gs.ser");
    }

    public void ResetGlobalSettings() {
        PROFILES_DIRECTORY = GameConstants.DEFAULT_PROFILES_DIRECTORY;
    }

    public static GlobalSettings getGS() {
        return GS;
    }

    // INITIALIZATION:

    private static GlobalSettings GS;
    static {
        try{
            GS = (GlobalSettings) SerializationHelper.Deserialize("gs.ser");
        }
        catch(SerializationLoadError error){
            GS = new GlobalSettings();
        }
    }

    private GlobalSettings() {
    }
}
