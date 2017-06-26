package pl.p.lodz.chess.rules.pieces;

import pl.p.lodz.chess.rules.board.Board;
import pl.p.lodz.chess.rules.board.player.Player;
import pl.p.lodz.chess.rules.board.Position;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Taberu on 2017-05-26.
 */
public class PieceSet {
    private ArrayList<Piece> ActivePieces = new ArrayList<>();
    private ArrayList<Piece> DefeatedPieces = new ArrayList<>();

    public PieceSet(){}

    public PieceSet(Player.Color color, boolean isUpper, Piece[] pieces){
        ActivePieces = new ArrayList<>(Arrays.asList(pieces));
        ColorSet(color);
        setUpperSet(isUpper);
    }

    public ArrayList<Piece> getActivePieces() {
        return ActivePieces;
    }

    public void setActivePieces(ArrayList<Piece> activePieces) {
        ActivePieces = activePieces;
    }

    public void makeDefeated(Piece piece){
        ActivePieces.remove(piece);
        DefeatedPieces.add(piece);
    }

    public boolean DoesAnyActivePieceLeft(){
        return ActivePieces.isEmpty();
    }

    public void ColorSet(Player.Color color){
        if(color == Player.Color.white) {
            ActivePieces.forEach( Piece::wearWhite );
        }
        else{
            ActivePieces.forEach( Piece::wearBlack );
        }
    }

    public void setUpperSet(boolean isUpper){
        if(isUpper) {
            for(Piece piece : ActivePieces){
                piece.setUpperSet(true);
            }
        }
        else{
            for(Piece piece : ActivePieces){
                piece.setUpperSet(false);
            }
        }
    }

    public static ArrayList<Piece> getStartingSet(Board parentBoard, boolean upperSet){
        ArrayList<Piece> startingSet = new ArrayList<>(16);
        int pawnRow = 6;
        int rookRow = 7;
        if(upperSet) {
            pawnRow -= 5;
            rookRow -= 7;
        }

        for(int i = 0; i < 8; ++i){
            startingSet.add(new Pawn(parentBoard, new Position(i, pawnRow), upperSet) );
        }
        startingSet.add(new Rook(parentBoard, new Position(0,rookRow), upperSet));
        startingSet.add(new Knight(parentBoard, new Position(1,rookRow), upperSet));
        startingSet.add(new Bishop(parentBoard, new Position(2,rookRow), upperSet));
        startingSet.add(new Queen(parentBoard, new Position(3,rookRow), upperSet));
        startingSet.add(new King(parentBoard, new Position(4,rookRow), upperSet));
        startingSet.add(new Bishop(parentBoard, new Position(5,rookRow), upperSet));
        startingSet.add(new Knight(parentBoard, new Position(6,rookRow), upperSet));
        startingSet.add(new Rook(parentBoard, new Position(7,rookRow), upperSet));

        return startingSet;
    }

}
