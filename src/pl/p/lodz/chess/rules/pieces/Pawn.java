package pl.p.lodz.chess.rules.pieces;

import pl.p.lodz.chess.gui.PieceInfo;
import pl.p.lodz.chess.rules.board.Board;
import pl.p.lodz.chess.rules.board.OutsideTheBoard;
import pl.p.lodz.chess.rules.board.Position;

import java.util.ArrayList;

/**
 * Created by Taberu on 2017-05-01.
 */
public class Pawn extends Piece {
    private boolean bFirstMove = true;
    private boolean bEnPassant = false;

    public Pawn(Board parentBoard){
        super(parentBoard, PieceInfo.PieceType.pawn);
    }

    public Pawn(Board parentBoard, Position position, boolean isUpper){
        this(parentBoard);
        setCurrentPosition(position);
        setUpperSet(isUpper);
    }

    public boolean isFirstMove() {
        return bFirstMove;
    }

    public void setFirstMove(boolean bFirstMove) {
        this.bFirstMove = bFirstMove;
    }

    @Override
    public String toString(){
        return "";
    }

    @Override
    public void pieceJustMoved(){
        if(bFirstMove) {
            bFirstMove = false;
            bEnPassant = true;
            getParentBoard().setEnPassantExecutor(this);
        }
        else{
            bEnPassant = false;
        }

    }

    public ArrayList<Position> getAllPossibleMoves(){
        ArrayList<Position> positions = new ArrayList<>();

        positions.addAll(getStandardForwardPositions());
        positions.addAll(getPossibleCapturePositions());
        getEnPassantPositions(positions, Position.Directions.left);
        getEnPassantPositions(positions, Position.Directions.right);

        return positions;
    }

    public ArrayList<Position> getStandardForwardPositions(){
        ArrayList<Position> positions = new ArrayList<>();
        int distance = 1;
        if(bFirstMove){
            distance = 2;
        }

        if(isUpperSet()){
            positions.addAll( getPossibleMovesInDirection(Position.Directions.down, distance) );
        }
        else{
            positions.addAll( getPossibleMovesInDirection(Position.Directions.up, distance) );
        }

        return positions;
    }

    @Override
    public void removeIllegalMoves(PossibleMoves allPossibleMoves){
        super.removeIllegalMoves(allPossibleMoves);
        removeIllegalCapturePositions(allPossibleMoves);
        removeMovesBlockedByEnemies(allPossibleMoves);
    }

    public boolean isEnPassant(){
        return bEnPassant;
    }

    public void setEnPassant(boolean bEnPassant) {
        this.bEnPassant = bEnPassant;
    }

    private void getEnPassantPositions(ArrayList<Position> arrayToSaveInto, Position.Directions direction){
        Pawn executor = getEnPassantExecutor(direction);
        if(executor == null){
            return;
        }

        if (direction == Position.Directions.left) {
            if(isUpperSet()) {
                arrayToSaveInto.add(new Position(getCurrentPosition(), Position.Directions.down_left, 1));
            }
            else {
                arrayToSaveInto.add(new Position(getCurrentPosition(), Position.Directions.up_left, 1));
            }
        } else if(direction == Position.Directions.right) {
            if(isUpperSet()) {
                arrayToSaveInto.add(new Position(getCurrentPosition(), Position.Directions.down_right, 1));
            }
            else {
                arrayToSaveInto.add(new Position(getCurrentPosition(), Position.Directions.up_right, 1));
            }
        }
    }

    private Pawn getEnPassantExecutor(Position.Directions direction){
        try {
            Piece executor = getParentBoard().getPiece(new Position(getCurrentPosition(), direction, 1));
            if(executor != null && executor instanceof Pawn && ((Pawn)executor).bEnPassant){
                return (Pawn)executor;
            }
        }
        catch(OutsideTheBoard e){
            return null;
        }
        return null;
    }

    public Pawn getEnPassatCapturedPawn(Position capturingPosition){
        Position.Directions direction;
        if(isUpperSet()){
            direction = Position.Directions.up;
        }
        else{
            direction = Position.Directions.down;
        }
        Piece captured = getParentBoard().getPiece(new Position(capturingPosition, direction, 1));
        if(captured != null && captured instanceof Pawn && ((Pawn)captured).bEnPassant){
            return (Pawn)captured;
        }
        return null;
    }

    public ArrayList<Position> getPossibleCapturePositions(){
        ArrayList<Position> positions = new ArrayList<>();
        Position attackLeft;
        Position attackRight;
        if(isUpperSet()){
            try {
                attackLeft = new Position(getCurrentPosition(), Position.Directions.down_left, 1);
                positions.add(attackLeft);
            }catch(OutsideTheBoard e){}
            try {
                attackRight = new Position(getCurrentPosition(), Position.Directions.down_right, 1);
                positions.add(attackRight);
            }catch(OutsideTheBoard e){}
        }
        else{
            try {
                attackLeft = new Position(getCurrentPosition(), Position.Directions.up_left, 1);
                positions.add(attackLeft);
            }catch(OutsideTheBoard e){}
            try {
                attackRight = new Position(getCurrentPosition(), Position.Directions.up_right, 1);
                positions.add(attackRight);
            }catch(OutsideTheBoard e){}
        }
        return positions;
    }

    private void removeIllegalCapturePositions(PossibleMoves possibleMoves){
        getPossibleCapturePositions().forEach(position -> {
            if(! getParentBoard().IsPositionTakenByEnemy(position, this.getPlayerColor())){
                possibleMoves.removeMove(position);
            }
        });
    }

    private void removeMovesBlockedByEnemies(PossibleMoves possibleMoves){
        for(Position forwardMoves : getStandardForwardPositions() ){
            if(getParentBoard().IsPositionTaken(forwardMoves) ){
                possibleMoves.removeMove(forwardMoves);
            }
        }
    }

}
