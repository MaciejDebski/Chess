package pl.p.lodz.engine.serialization;

import java.io.*;

/**
 * Created by Taberu on 2017-03-27.
 */
public class SerializationHelper {
    public static void Serialize(Object ObjectToSerialize, String PathIncludingExtension) throws NullPointerException, SerializationSaveError {
        try {
            if(ObjectToSerialize == null){
                throw new NullPointerException();
            }
            FileOutputStream fileOut = new FileOutputStream(PathIncludingExtension);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(ObjectToSerialize);
            objectOut.close();
            fileOut.close();
        }catch(IOException error) {
            throw new SerializationSaveError(error.getMessage(), error.getStackTrace());
        }
    }

    public static Object Deserialize(String PathIncludingExtension) throws SerializationLoadError {
        Object Deserialized;
        try {
            FileInputStream fileIn = new FileInputStream(PathIncludingExtension);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Deserialized = objectIn.readObject();
            objectIn.close();
            fileIn.close();
        }catch(IOException | ClassNotFoundException error) {
            throw new SerializationLoadError(error.getMessage(), error.getStackTrace());
        }
        return Deserialized;
    }
}
