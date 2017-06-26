package pl.p.lodz.chess.rules.board;

import pl.p.lodz.chess.rules.pieces.Piece;

/**
 * Created by Taberu on 2017-05-01.
 */
public class OutsideTheBoard extends RuntimeException {
    private int Width, Height;
    OutsideTheBoard(int WidthIndex, int HeightIndex){
        this.Width = WidthIndex;
        this.Height = HeightIndex;
    }

    public int GetWidthIndex(){
        return Width;
    }

    public int GetHeightIndex(){
        return Height;
    }
}
