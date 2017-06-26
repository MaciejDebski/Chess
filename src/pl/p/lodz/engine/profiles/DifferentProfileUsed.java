package pl.p.lodz.engine.profiles;

/**
 * Created by Taberu on 2017-03-26.
 */
public class DifferentProfileUsed extends RuntimeException {
    public DifferentProfileUsed(){
        super("Different profile is already loaded and played!");
    }
}
