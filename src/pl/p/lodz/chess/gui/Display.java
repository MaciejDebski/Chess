package pl.p.lodz.chess.gui;

/**
 * Created by Taberu on 2017-06-01.
 */
public class Display {
    private static MoveEvent OnPieceMoved;
    private static PieceEvent OnPieceSpawned;
    private static PieceEvent OnPieceRemoved;
    private static PositionEvent OnPossibleMoveShowed;
    private static ActionEvent OnAllPossibleMovesHided;
    private static PositionEvent OnPositionHighlighted;
    private static ANEvent OnANEntryAdded;
    private static PlayerEvent OnPlayerSwitched;
    private static TimeEvent OnTimeChanged;
    private static PlayerEvent OnMatchFinished;
    private static ActionEvent OnSessionClosed;

    public static void firePieceMoved(MoveInfo moveInfo){
        if (OnPieceMoved != null){
            OnPieceMoved.fireEvent(moveInfo);
        }
    }
    public static void firePieceSpawned(PieceInfo piece) {
        if (OnPieceSpawned != null){
            OnPieceSpawned.fireEvent(piece);
        }
    }
    public static void firePieceRemoved(PieceInfo piece) {
        if (OnPieceRemoved != null){
            OnPieceRemoved.fireEvent(piece);
        }
    }
    public static void firePossibleMoveShowed(PositionInfo position) {
        if (OnPossibleMoveShowed != null){
            OnPossibleMoveShowed.fireEvent(position);
        }
    }
    public static void fireAllPossibleMovesHided() {
        if (OnAllPossibleMovesHided != null){
            OnAllPossibleMovesHided.fireEvent();
        }
    }
    public static void firePositionHighlighted(PositionInfo position) {
        if (OnPositionHighlighted != null){
            OnPositionHighlighted.fireEvent(position);
        }
    }
    public static void fireANEntryAdded(ANInfo algebraicNotation) {
        if (OnANEntryAdded != null){
            OnANEntryAdded.fireEvent(algebraicNotation);
        }
    }
    public static void firePlayerSwitched(PlayerInfo playerInfo) {
        if (OnPlayerSwitched != null){
            OnPlayerSwitched.fireEvent(playerInfo);
        }
    }
    public static void fireTimeChanged(TimeInfo timeInfo) {
        if (OnTimeChanged != null){
            OnTimeChanged.fireEvent(timeInfo);
        }
    }
    public static void fireMatchFinished(PlayerInfo playerInfo) {
        if (OnMatchFinished != null){
            OnMatchFinished.fireEvent(playerInfo);
        }
    }

    public static void fireSessionClosed(){
        if(OnSessionClosed != null){
            OnSessionClosed.fireEvent();
        }
    }

    public static void setOnPieceMoved(MoveEvent eventListener){
        OnPieceMoved = eventListener;
    }

    public static void setOnPieceSpawned(PieceEvent eventListener){
        OnPieceSpawned = eventListener;
    }

    public static void setOnPieceRemoved(PieceEvent eventListener){
        OnPieceRemoved = eventListener;
    }

    public static void setOnPossibleMoveShowed(PositionEvent eventListener) {
        OnPossibleMoveShowed = eventListener;
    }

    public static void setOnAllPossibleMovesRemoved(ActionEvent eventListener) {
        OnAllPossibleMovesHided = eventListener;
    }

    public static void setOnPositionHighlighted(PositionEvent eventListener) {
        OnPositionHighlighted = eventListener;
    }

    public static void setOnANEntryAdded(ANEvent eventListener) {
        OnANEntryAdded = eventListener;
    }

    public static void setOnPlayerSwitched(PlayerEvent eventListener) {
        OnPlayerSwitched = eventListener;
    }

    public static void setOnTimeChanged(TimeEvent eventListener) {
        OnTimeChanged = eventListener;
    }

    public static void setOnMatchFinished(PlayerEvent eventListener) {
        OnMatchFinished = eventListener;
    }

    public static void setOnSessionClosed(ActionEvent eventListener){
        OnSessionClosed = eventListener;
    }
}
