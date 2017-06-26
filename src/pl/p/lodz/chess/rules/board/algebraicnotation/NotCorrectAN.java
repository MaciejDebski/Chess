package pl.p.lodz.chess.rules.board.algebraicnotation;

/**
 * Created by Taberu on 2017-05-26.
 */
public class NotCorrectAN extends RuntimeException {
    private String comment;
    private String incorrectness;

    public NotCorrectAN(char letter, String comment){
        incorrectness = String.valueOf(letter);
        this.comment = comment;
    }

    public NotCorrectAN(int number, String comment){
        incorrectness = String.copyValueOf(Character.toChars(number));
        this.comment = comment;
    }

    public NotCorrectAN(String incorrectness, String comment){
        this.comment = comment;
    }

    public String getIncorrectness(){
        return incorrectness;
    }

    public String getComment(){
        return comment;
    }
}
