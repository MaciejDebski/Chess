package pl.p.lodz.engine.language;

import java.io.Serializable;

/**
 * Created by Taberu on 2017-03-31.
 */
public abstract class Language implements Serializable {
    public abstract String ShortName();

    public static String[] ListAllAvailableLanguages() {
        return new String[]{"PL", "EN"};
    }

    public static Language GetLanguageByName(String name) throws LanguageNotSupported {
        switch (name){
            case "PL":{
                return new Polish();
            }
            case "EN":{
                return new English();
            }
            default:
                throw new LanguageNotSupported(name);
        }
    }

}
