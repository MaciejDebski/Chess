package pl.p.lodz.chess.gui;

import pl.p.lodz.chess.rules.board.Position;
import pl.p.lodz.chess.rules.pieces.Piece;

/**
 * Created by Taberu on 2017-06-01.
 */
public class MoveInfo {
    private final PieceInfo pieceBeforeMovement;
    private final PieceInfo pieceAfterMovement;

    public MoveInfo(Piece piece, Position start, Position destination){
        this.pieceBeforeMovement = new PieceInfo(piece.getPieceType(), new PlayerInfo(piece.getPlayerColor()), new PositionInfo(start));
        this.pieceAfterMovement = new PieceInfo(piece.getPieceType(), new PlayerInfo(piece.getPlayerColor()), new PositionInfo(destination));
    }

    public PieceInfo getPieceBeforeMovement() {
        return pieceBeforeMovement;
    }

    public PieceInfo getPieceAfterMovement() {
        return pieceAfterMovement;
    }
}
