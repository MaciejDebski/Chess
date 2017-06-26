package pl.p.lodz.chess.rules.board.player;

import pl.p.lodz.chess.gui.PieceInfo;
import pl.p.lodz.chess.rules.board.Board;
import pl.p.lodz.chess.rules.board.Time;
import pl.p.lodz.chess.rules.board.algorithms.CheckMate;
import pl.p.lodz.chess.rules.pieces.Piece;
import pl.p.lodz.chess.rules.pieces.PieceSet;

/**
 * Created by Taberu on 2017-05-30.
 */
public abstract class Player {
    public enum Color {
        white,
        black
    }

    private PieceSet set = new PieceSet();
    private Color PlayerColor = Color.white;
    private Time GameTimer = new Time();
    private Piece kingThreateningPiece;

    public Player(Color color){
        PlayerColor = color;
    }

    public boolean isPieceAllowedToMove(Board currentGame, Piece piece){
        //if(kingThreateningPiece != null){
        //    return CheckMate.canPieceRescueKing(currentGame, piece, kingThreateningPiece );
        //}
        return piece.getPlayerColor() == currentGame.getCurrentPlayer().getPlayerColor();
    }

    public Piece getKingThreateningPiece() {
        return kingThreateningPiece;
    }

    public boolean isKingThreatened(){
        return kingThreateningPiece != null;
    }

    public void setKingThreateningPiece(Piece kingThreateningPiece) {
        this.kingThreateningPiece = kingThreateningPiece;
    }

    public void clearKingThreateningPiece(){
        kingThreateningPiece = null;
    }

    public PieceSet getSet() {
        return set;
    }

    public Piece getKing(){
        for(Piece piece : set.getActivePieces()){
            if(piece.getPieceType() == PieceInfo.PieceType.king){
                return piece;
            }
        }
        return null;
    }

    public Color getPlayerColor() {
        return PlayerColor;
    }
    public Time getGameTimer() {
        return GameTimer;
    }

    public abstract void generateStartingSet(Board parentBoard);
    public abstract void UpdateGameTimer(Time time);
    public abstract Player getOpponent();
}
