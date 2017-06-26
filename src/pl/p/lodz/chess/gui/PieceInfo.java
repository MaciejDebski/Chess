package pl.p.lodz.chess.gui;

import pl.p.lodz.chess.rules.pieces.Piece;

/**
 * Created by Taberu on 2017-06-01.
 */
public class PieceInfo {
    public enum PieceType{
        pawn,
        bishop,
        knight,
        king,
        queen,
        rook
    }

    private PieceType pieceType;
    private PlayerInfo playerInfo;
    private PositionInfo position;

    public PieceInfo(PieceType pieceType, PlayerInfo playerInfo, PositionInfo position){
        this.pieceType = pieceType;
        this.playerInfo = playerInfo;
        this.position = position;
    }

    public PieceInfo(Piece piece){
        this.pieceType = piece.getPieceType();
        this.playerInfo = new PlayerInfo(piece.getPlayerColor());
        this.position = new PositionInfo(piece.getCurrentPosition());
    }

    public PieceType getPieceType(){
        return this.pieceType;
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public PositionInfo getPosition(){
        return this.position;
    }
}
