package pl.p.lodz.chess.rules.board;

import pl.p.lodz.chess.GameState;
import pl.p.lodz.chess.gui.*;
import pl.p.lodz.chess.rules.board.algebraicnotation.AN;
import pl.p.lodz.chess.rules.board.algorithms.CheckMate;
import pl.p.lodz.chess.rules.board.player.Blacks;
import pl.p.lodz.chess.rules.board.player.Player;
import pl.p.lodz.chess.rules.board.player.Whites;
import pl.p.lodz.chess.rules.pieces.King;
import pl.p.lodz.chess.rules.pieces.Pawn;
import pl.p.lodz.chess.rules.pieces.Piece;
import pl.p.lodz.chess.rules.pieces.PossibleMoves;

import java.io.Serializable;

/**
 * Created by Taberu on 2017-04-30.
 */
public class Board implements Serializable {
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;
    private Piece[][] MainBoard = new Piece[HEIGHT][WIDTH];
    private Whites whites = new Whites();
    private Blacks blacks = new Blacks();
    private Player currentPlayer = whites;
    private Piece SelectedPiece;
    private Position hypotheticallyOccupiedPosition;
    private Position hypotheticallyRemovedPosition;
    private Pawn enPassantExecutor;

    public static boolean IsBoardIndex(int X, int Y){
        return X >= 0 && X < WIDTH && Y >= 0 && Y < HEIGHT;
    }

    public void StartNewGame(){
        whites.generateStartingSet(this);
        blacks.generateStartingSet(this);
        SpawnAllActivePieces();
        currentPlayer = getPlayerByColor(Player.Color.white);
        currentPlayer.getGameTimer().unpause();
        CheckMate.refreshThreatMaps(this);
        Display.fireTimeChanged(new TimeInfo(Player.Color.white, whites.getGameTimer()));
        Display.fireTimeChanged(new TimeInfo(Player.Color.black, blacks.getGameTimer()));
    }

    public Piece getPiece(Position position){
        return MainBoard[position.getY()][position.getX()];
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void switchCurrentPlayer(){
        setCurrentPlayer(currentPlayer.getOpponent());
        Display.firePlayerSwitched(new PlayerInfo(currentPlayer.getPlayerColor()));
    }

    public Player getOpponent(){
        return getCurrentPlayer().getOpponent();
    }

    public Player getOpponent(Piece piece){
        if(piece.getPlayerColor() == Player.Color.white){
            return blacks;
        }
        return whites;
    }

    public Player.Color getOpponentColor(Player.Color player){
    	if(player == Player.Color.white){
    		return Player.Color.black;
    	}
    	return Player.Color.white;
    }

    public void setCurrentPlayer(Player player){
        if(player == currentPlayer){
            return;
        }
        currentPlayer.getGameTimer().pause();
        player.getGameTimer().unpause();
        currentPlayer = player;
    }

    public void PieceClicked(int X, int Y){
        Piece piece = getPiece(new Position(X, Y));
        if(piece == null){
            return;
        }

        if(!getCurrentPlayer().isPieceAllowedToMove(this, piece)){
            return;
        }
        
        if(SelectedPiece == null){
            SelectPiece(piece);
        }
        else if(SelectedPiece != piece){
            UnselectPiece(SelectedPiece);
            SelectPiece(piece);
        }
        else{
            UnselectPiece(SelectedPiece);
        }
    }

    public void MoveSelectedPiece(int X, int Y){
        Position pos;
        try{
            pos = new Position(X, Y);
        }
        catch(OutsideTheBoard e){
            return;
        }

        if(pos.equals(getKing(currentPlayer).getCurrentPosition() ) ){
            return;
        }

        boolean bCaptured = captureProcedure(pos);
        boolean bEnPassant = enPassantProcedure(SelectedPiece, pos);

        displayMovingPiece(SelectedPiece, pos, bCaptured, bEnPassant);
        Piece movedPiece = SelectedPiece;
        movePieceToPosition(movedPiece, pos);
        movedPiece.pieceJustMoved();
        UnselectPiece(movedPiece);
        tryToCheckMateOpponent(movedPiece);
        switchCurrentPlayer();
    }

    public void displayMovingPiece(Piece beforeMovement, Position destination, boolean bCaptured, boolean bEnPassant){
        Display.firePieceMoved(new MoveInfo(beforeMovement, beforeMovement.getCurrentPosition(), destination));
        Display.fireANEntryAdded(
                new ANInfo(
                        new AN(beforeMovement, destination, bCaptured, bEnPassant),
                        beforeMovement.getPlayerColor()
                )
        );
    }

    public void capturePiece(Piece captured){
        MainBoard[captured.getCurrentPosition().getY()][captured.getCurrentPosition().getX()] = null;
        captured.getParentPlayer().getSet().makeDefeated(captured);
        Display.firePieceRemoved(new PieceInfo(captured));
    }

    public boolean IsAnyPieceSelected(){
        return SelectedPiece != null;
    }

    public void UnselectPiece(Piece piece){
        Display.fireAllPossibleMovesHided();
        SelectedPiece = null;
        if(piece != null) {
            piece.setIsSelected(false);
        }
    }

    public Position getHypotheticallyOccupiedPosition() {
        return hypotheticallyOccupiedPosition;
    }

    public void setHypotheticallyOccupiedPosition(Position hypotheticallyOccupiedPosition) {
        this.hypotheticallyOccupiedPosition = hypotheticallyOccupiedPosition;
    }

    public Position getHypotheticallyRemovedPosition() {
        return hypotheticallyRemovedPosition;
    }

    public void setHypotheticallyRemovedPosition(Position hypotheticallyRemovedPosition) {
        this.hypotheticallyRemovedPosition = hypotheticallyRemovedPosition;
    }

    public boolean IsPositionTaken(Position position){
        return (getPiece(position) != null || position.equals(hypotheticallyOccupiedPosition))
                && !position.equals(hypotheticallyRemovedPosition);
    }

    public boolean IsPositionTakenByEnemy(Position position, Player.Color allyColor){
        return getPiece(position) != null
                && getPiece(position).getPlayerColor() != allyColor
                || position.equals(hypotheticallyOccupiedPosition)
                && !position.equals(hypotheticallyRemovedPosition);
    }

    public boolean IsPositionTakenByAlly(Position position, Player.Color allyColor){
        return getPiece(position) != null
                && getPiece(position).getPlayerColor() == allyColor
                || position.equals(hypotheticallyOccupiedPosition)
                && !position.equals(hypotheticallyRemovedPosition);
    }

    public boolean IsPositionTakenBy(Position position, Piece piece){
    	if(getPiece(position) != null){
    		return getPiece(position).getPieceType() == piece.getPieceType()
                    && getPiece(position).getPlayerColor() == piece.getPlayerColor();
    	}
    	return false;
    }

    public boolean spawnPiece(Piece piece, Position position){
        if(IsPositionTaken(position)){
            return false;
        }

        MainBoard[piece.getCurrentPosition().getY()][piece.getCurrentPosition().getX()] = piece;
        return true;
    }

    public Whites getWhites() {
        return whites;
    }

    public Blacks getBlacks() {
        return blacks;
    }

    public Player getPlayerByColor(Player.Color color){
        if(color == Player.Color.white){
            return whites;
        }
        return blacks;
    }

    public Piece getKing(Player player) throws NullPointerException {
        Piece king = player.getKing();
        if(king == null){
            throw new NullPointerException();
        }
        return king;
    }

    public void setEnPassantExecutor(Pawn enPassantExecutor) {
        this.enPassantExecutor = enPassantExecutor;
    }

    private void movePieceToPosition(Piece piece, Position pos){
        MainBoard[pos.getY()][pos.getX()] = MainBoard[piece.getCurrentPosition().getY()][piece.getCurrentPosition().getX()];
        MainBoard[piece.getCurrentPosition().getY()][piece.getCurrentPosition().getX()] = null;
        piece.setCurrentPosition(pos);
    }

    private void SelectPiece(Piece piece){
        Display.firePositionHighlighted(new PositionInfo(piece.getCurrentPosition()) );

        PossibleMoves pm = piece.getPossibleMoves();
        if(!(piece instanceof King)){
            piece.removeMovesCausingCheck(pm);
        }

        Piece king = getOpponent().getKing();
        if(king != null) {
            pm.removeMove(king.getCurrentPosition());
        }

        ShadeAllPossibleMoves(pm);

        SelectedPiece = piece;
        piece.setIsSelected(true);
    }

    private void ShadeAllPossibleMoves(PossibleMoves possibleMoves){
        if(possibleMoves.getPositions() != null && !possibleMoves.getPositions().isEmpty()) {
            possibleMoves.getPositions().forEach((position) -> {
                if (position != null) {
                    Display.firePossibleMoveShowed(new PositionInfo(position));
                }
            });
        }
    }

    private void SpawnAllActivePieces(){
        whites.getSet().getActivePieces().forEach((piece) -> {
            MainBoard[piece.getCurrentPosition().getY()][piece.getCurrentPosition().getX()] = piece;
            Display.firePieceSpawned(new PieceInfo(piece));
        });
        blacks.getSet().getActivePieces().forEach((piece) -> {
            GameState.getInstance().getCurrentGame().spawnPiece(piece, piece.getCurrentPosition());
            Display.firePieceSpawned(new PieceInfo(piece));
        });
    }

    private void tryToCheckMateOpponent(Piece movedPiece) {
        CheckMate.refreshThreatMaps(this);
        if(CheckMate.checkForCheck(movedPiece) ) {
            if(CheckMate.checkForCheckMate(movedPiece) ){
                GameState.getInstance().endSession(movedPiece.getParentPlayer() );
            }
            movedPiece.getOpponent().setKingThreateningPiece(movedPiece);
        }
        else{
            movedPiece.getOpponent().clearKingThreateningPiece();
        }
    }

    private boolean captureProcedure(Position pos){
        if(IsPositionTaken(pos)){
            capturePiece( getPiece(pos) );
            return true;
        }
        return false;
    }

    private boolean enPassantProcedure(Piece movedPiece, Position destination){
        if(movedPiece instanceof Pawn){
            Pawn captured = ((Pawn)movedPiece).getEnPassatCapturedPawn(destination);
            if(captured != null){
                capturePiece(captured);
                return true;
            }
        }
        clearEnPassantAfterMove();
        return false;
    }

    private void clearEnPassantAfterMove(){
        if(enPassantExecutor != null){
            enPassantExecutor.setEnPassant(false);
        }
    }

}
