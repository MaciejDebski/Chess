package pl.p.lodz.chess.rules.board.algorithms;

import pl.p.lodz.chess.rules.board.Board;
import pl.p.lodz.chess.rules.board.Position;
import pl.p.lodz.chess.rules.board.player.Player;
import pl.p.lodz.chess.rules.pieces.King;
import pl.p.lodz.chess.rules.pieces.Piece;
import pl.p.lodz.chess.rules.pieces.PossibleMoves;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Taberu on 2017-06-01.
 */
public class CheckMate {
    private static boolean[][] threatMapWhite = new boolean[Board.HEIGHT][Board.WIDTH];
    private static boolean[][] threatMapBlack = new boolean[Board.HEIGHT][Board.WIDTH];
    private static PossibleMoves whiteKingPossibleMoves;
    private static PossibleMoves blackKingPossibleMoves;

    public static boolean checkForCheck(Piece threateningPiece){
        return checkForCheck(threateningPiece.getOpponent().getKing(), new PossibleMoves(threateningPiece.getAttackMoves()) );
    }

    public static boolean checkForCheck(Piece king, PossibleMoves threatMoves){
        if(king == null || !(king instanceof King)){
            return false;
        }

        for(Position position : threatMoves.getPositions()){
            if(king.getParentBoard().IsPositionTakenBy(position, king)){
                return true;
            }
        }

        return false;
    }

    public static boolean checkForCheckMate(Piece threateningPiece){
        if(canKingEscape(threateningPiece.getOpponent().getKing())){
            return false;
        }

        for(Piece opponentPiece : threateningPiece.getOpponent().getSet().getActivePieces()){
            if(canPieceRescueKing(opponentPiece, threateningPiece)){
                return false;
            }
        }
        return true;
    }

    public static boolean isPositionThreaten(Player player, Position position) {
        return getThreatMapByPlayer(player.getOpponent())[position.getY()][position.getX()];
    }

    public static boolean isRescueMoveEffective(Piece king, Position rescueMove, Piece threat){
        if(rescueMove.equals(threat.getCurrentPosition())){
            return true;
        }
        king.getParentBoard().setHypotheticallyOccupiedPosition(rescueMove);
        PossibleMoves threatMoves = new PossibleMoves(threat.getAttackMoves() );
        king.getParentBoard().setHypotheticallyOccupiedPosition(null);
        return !checkForCheck(king, threatMoves);
    }

    public static boolean canPieceRescueKing(Piece piece, Piece threat){
        PossibleMoves possibleMoves = new PossibleMoves(piece.getAllPossibleMoves());
        if(piece instanceof King) {
            piece.removeMovesThreatenByOpponent(possibleMoves);
        }
        for(Position rescueMove : possibleMoves.getPositions() ){
            if(rescueMove.equals(threat.getCurrentPosition())){
                return true;
            }
            for(Position threatPos : threat.getAllPossibleMoves() ){
                if(rescueMove.equals(threatPos)){
                    if(rescueMove.equals(threat.getCurrentPosition()) || isRescueMoveEffective(piece.getParentPlayer().getKing(), rescueMove, threat )){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isKingThreatenIfPieceMoves(Piece piece, Position move){
        boolean bIsKingThreaten = false;

        piece.getParentBoard().setHypotheticallyRemovedPosition(piece.getCurrentPosition());
        piece.getParentBoard().setHypotheticallyOccupiedPosition(move);
        for(Piece enemy : piece.getOpponent().getSet().getActivePieces()){
            bIsKingThreaten = checkForCheck(enemy);
            if(bIsKingThreaten){
                if (move.equals(enemy.getCurrentPosition())) {
                    bIsKingThreaten = false;
                }
                else{
                    break;
                }
            }
        }
        piece.getParentBoard().setHypotheticallyRemovedPosition(null);
        piece.getParentBoard().setHypotheticallyOccupiedPosition(null);

        return bIsKingThreaten;
    }

    public static void refreshThreatMaps(Board currentGame){
        for(int i = 0; i < Board.HEIGHT; ++i){
            for(int j = 0; j < Board.WIDTH; ++j){
                threatMapWhite[i][j] = false;
                threatMapBlack[i][j] = false;
            }
        }

        generateThreatBesideKings(currentGame.getWhites());
        generateThreatBesideKings(currentGame.getBlacks());

        generateThreatCausedByKing(currentGame.getWhites());
        generateThreatCausedByKing(currentGame.getBlacks());
        generateThreatCausedByKing(currentGame.getWhites());
    }

    private static boolean canKingEscape(Piece king){
        if(king == null || !(king instanceof King)){
            return true;
        }


        return getKingsPossibleMoves(king).size() != 0;
    }

    private static void generateThreatBesideKings(Player player){
        for(Piece piece : player.getSet().getActivePieces()){
            if(!(piece instanceof King)){
                for(Position position : piece.getAttackMoves()){
                    setThreat(player, position, true);
                }
            }
        }
    }

    private static void generateThreatCausedByKing(Player player){
        Piece king = player.getKing();
        if(king == null){
            return;
        }

        getKingsPossibleMoves(king).forEach(position -> {
            setThreat(player, position, true);
        });
    }

    private static ArrayList<Position> getKingsPossibleMoves(Piece king){
        ArrayList<Position> kingPossibleMoves = king.getAllPossibleMoves();
        king.removeMovingAtAlliesAndKing(new PossibleMoves(kingPossibleMoves));
        for(Iterator<Position> itr = kingPossibleMoves.iterator(); itr.hasNext();){
            Position pos = itr.next();
            if(getThreat(king.getOpponent(), pos)){
                itr.remove();
            }
        }
        return kingPossibleMoves;
    }

    private static boolean[][] getThreatMapByPlayer(Player player){
        if(player.getPlayerColor() == Player.Color.white){
            return threatMapWhite;
        }
        return threatMapBlack;
    }

    private static boolean getThreat(Player player, Position position){
        return getThreatMapByPlayer(player)[position.getY()][position.getX()];
    }

    private static void setThreat(Player player, Position position, boolean threat){
        getThreatMapByPlayer(player)[position.getY()][position.getX()] = threat;
    }

    private static void setThreatWhite(Position position, boolean threat){
        threatMapWhite[position.getY()][position.getX()] = threat;
    }

    private static void setThreatBlack(Position position, boolean threat){
        threatMapBlack[position.getY()][position.getX()] = threat;
    }
}
