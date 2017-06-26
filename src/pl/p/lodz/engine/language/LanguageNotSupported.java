package pl.p.lodz.engine.language;

/**
 * Created by Taberu on 2017-03-31.
 */
public class LanguageNotSupported extends RuntimeException {
    public String SelectedLanguage;

    LanguageNotSupported(String SelectedLanguage){
        super("Provided language name is probably misspelled or language is not supported!");
        this.SelectedLanguage = SelectedLanguage;
    }
}
