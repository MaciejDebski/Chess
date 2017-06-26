package pl.p.lodz.chess.rules.pieces;

import pl.p.lodz.chess.GameState;
import pl.p.lodz.chess.gui.PieceInfo;
import pl.p.lodz.chess.rules.board.Board;
import pl.p.lodz.chess.rules.board.OutsideTheBoard;
import pl.p.lodz.chess.rules.board.algorithms.CheckMate;
import pl.p.lodz.chess.rules.board.player.Player;
import pl.p.lodz.chess.rules.board.Position;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Taberu on 2017-04-30.
 */
public abstract class Piece {
    private Board parentBoard;
    private Position CurrentPosition;

    private PieceInfo.PieceType pieceType;
    private boolean bIsSelected;
    private Player.Color playerColor;
    private boolean bUpperSet;

    protected Piece(Board parentBoard, PieceInfo.PieceType pieceType){
        this.parentBoard = parentBoard;
        this.pieceType = pieceType;
        this.playerColor = Player.Color.white;
    }

    public Board getParentBoard(){
        return parentBoard;
    }
    public Player getParentPlayer(){
        if(playerColor == Player.Color.white){
            return GameState.getInstance().getCurrentGame().getWhites();
        }
        return GameState.getInstance().getCurrentGame().getBlacks();
    }
    public Player getOpponent(){
        if(playerColor == Player.Color.white){
            return GameState.getInstance().getCurrentGame().getBlacks();
        }
        return GameState.getInstance().getCurrentGame().getWhites();
    }
    public Player.Color getPlayerColor(){
        return playerColor;
    }

    public PieceInfo.PieceType getPieceType() {
        return pieceType;
    }

    public void wearBlack(){
        this.playerColor = Player.Color.black;
    }

    public void wearWhite(){
        this.playerColor = Player.Color.white;
    }

    @Override
    public abstract String toString();

    public Position getCurrentPosition(){
        return CurrentPosition;
    }

    public void setCurrentPosition(Position newPosition){
        CurrentPosition = newPosition;
    }

    public boolean isSelected() {
        return bIsSelected;
    }

    public void setIsSelected(boolean bIsSelected) {
        this.bIsSelected = bIsSelected;
    }

    public boolean isUpperSet() {
        return bUpperSet;
    }

    public void setUpperSet(boolean bUpperSet) {
        this.bUpperSet = bUpperSet;
    }

    public PossibleMoves getPossibleMoves(){
        PossibleMoves pm = new PossibleMoves(getAllPossibleMoves());
        removeMovesNotHelpingKing(pm);
        removeIllegalMoves(pm);
        return pm;
    }
    public abstract ArrayList<Position> getAllPossibleMoves();
    public ArrayList<Position> getAttackMoves(){
        ArrayList<Position> threateningMoves = getAllPossibleMoves();
        if(this instanceof Pawn){
            threateningMoves.removeAll(((Pawn)this).getStandardForwardPositions());
        }
        return threateningMoves;
    }
    public void removeIllegalMoves(PossibleMoves allPossibleMoves){
        removeMovingAtAlliesAndKing(allPossibleMoves);
    }

    public void removeMovingAtAlliesAndKing(PossibleMoves possibleMoves){
        for(Iterator<Position> itr = possibleMoves.getPositions().iterator(); itr.hasNext();){
            Position position = itr.next();
            if (getParentBoard().IsPositionTakenByAlly(position, getPlayerColor())
                    || getParentBoard().IsPositionTakenBy(position, getParentPlayer().getOpponent().getKing()) ) {
                itr.remove();
            }
        }
    }

    public void removeMovesCausingCheck(PossibleMoves possibleMoves){
        for(Iterator<Position> itr = possibleMoves.getPositions().iterator(); itr.hasNext();){
            Position position = itr.next();
            if(CheckMate.isKingThreatenIfPieceMoves(this, position)){
                itr.remove();
            }
        }
    }

    public void removeMovesThreatenByOpponent(PossibleMoves possibleMoves){
        for(Iterator<Position> itr = possibleMoves.getPositions().iterator(); itr.hasNext();){
            Position position = itr.next();
            if(CheckMate.isPositionThreaten(getParentPlayer(), position)){
                itr.remove();
            }
        }
    }

    public ArrayList<Position> getPossibleMovesInDirection(Position.Directions direction, int distance) {
        ArrayList<Position> positions = new ArrayList<>();
        int distanceIndex = 0;
        Position pos;
        boolean bWithinTheBoard = true;
        boolean bAnotherNotBlocking = true;
        while (bWithinTheBoard && bAnotherNotBlocking && distanceIndex < distance) {
            try {
                pos = new Position(getCurrentPosition(), direction, ++distanceIndex);
                positions.add(pos);
                if (parentBoard.IsPositionTaken(pos)) {
                    bAnotherNotBlocking = false;
                }
            } catch (OutsideTheBoard e) {
                bWithinTheBoard = false;
            }
        }

        return positions;
    }

    public void pieceJustMoved(){}

    public static Piece newPieceUsingPieceType(PieceInfo.PieceType pieceType, Board parentBoard, Position pos, boolean isUpper){
        switch(pieceType){
            case pawn: return new Pawn(parentBoard, pos, isUpper);
            case bishop: return new Bishop(parentBoard, pos, isUpper);
            case knight: return new Knight(parentBoard, pos, isUpper);
            case king: return new King(parentBoard, pos, isUpper);
            case queen: return new Queen(parentBoard, pos, isUpper);
            case rook: return new Rook(parentBoard, pos, isUpper);
            default: return null;
        }
    }

    protected void removeMovesNotHelpingKing(PossibleMoves pm){
        if(getParentPlayer().isKingThreatened() && !(this instanceof King)) {
            for (Iterator<Position> itr = pm.getPositions().iterator(); itr.hasNext(); ) {
                if (!CheckMate.isRescueMoveEffective(
                        getParentPlayer().getKing(),
                        itr.next(),
                        getParentPlayer().getKingThreateningPiece() )) {
                    itr.remove();
                }
            }
        }
    }
}
