package pl.p.lodz.chess.rules.board.algebraicnotation;

import pl.p.lodz.chess.rules.board.Position;
import pl.p.lodz.chess.rules.pieces.Piece;

import static java.lang.Character.isDigit;
import static java.lang.Character.toUpperCase;

/**
 * Created by Taberu on 2017-05-26.
 */
public class AN {
    String PieceAN = "";
    String captured = "";
    String enPassant = "";
    char letter;
    int digit;

    public AN(char letter, int digit) throws NotCorrectAN {
        try{
            setOnlyWhenCorrect(letter, digit);
        }
        catch(NotCorrectAN e){
            throw e;
        }
    }

    public AN(String an) throws NotCorrectAN {
        if(an.length() != 2){
            throw new NotCorrectAN(an, "Algebraic notation consists of 1 letter and 1 digit.");
        }
        try{
            setOnlyWhenCorrect(an.charAt(0), Character.getNumericValue(an.charAt(1)) );
        }
        catch(NotCorrectAN e){
            throw e;
        }
    }

    public AN(Piece piece, Position move, boolean capture, boolean enPassant) {
        PieceAN = piece.toString();
        letter = move.toAN().letter;
        digit = move.toAN().digit;
        if(capture){
            captured = "x";
        }
        if(enPassant){
            this.enPassant = "e.p. ";
        }
    }

    public Position toPosition(){
        return new Position(letterToColumn(), digitToRow());
    }

    @Override
    public String toString(){
        return enPassant + PieceAN + captured + letter + Integer.toString(digit);
    }



    private int digitToRow(){
        return digit - 1;
    }

    private int letterToColumn(){
        return letter - 'a';
    }

    private boolean isCorrectANLetter(char letter){
        Character ch = Character.toLowerCase(letter);
        return ch >= 'a' && ch <= 'h';
    }

    private boolean isCorrectANDigit(int digit){
        return digit >= 1 && digit <= 8;
    }

    private void setOnlyWhenCorrect(char letter, int digit) throws NotCorrectAN {
        if(!isCorrectANLetter(letter) ){
            throw new NotCorrectAN(letter, "The letter has to be a one from a-h.");
        }

        if(!isCorrectANDigit(digit) ){
            throw new NotCorrectAN(digit, "The integer has to be a digit from 1-8.");
        }

        this.letter = letter;
        this.digit = digit;
    }

}
