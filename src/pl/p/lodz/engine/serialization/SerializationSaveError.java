package pl.p.lodz.engine.serialization;

/**
 * Created by Taberu on 2017-03-27.
 */
public class SerializationSaveError extends RuntimeException {
    public SerializationSaveError(){
        super("Probably, provided profile was a null pointer, or there was a problem with serialization!");
    }

    public SerializationSaveError(String str){
        super(str);
    }

    public SerializationSaveError(String str, StackTraceElement[] stackTraceElement){
        super(str);
        setStackTrace(stackTraceElement);
    }

}
