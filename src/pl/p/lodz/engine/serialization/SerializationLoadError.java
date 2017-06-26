package pl.p.lodz.engine.serialization;

/**
 * Created by Taberu on 2017-03-26.
 */
public class SerializationLoadError extends Exception {
    public SerializationLoadError(String str){
        super(str);
    }

    public SerializationLoadError(String str, StackTraceElement[] stackTraceElement){
        super(str);
        setStackTrace(stackTraceElement);
    }
}
