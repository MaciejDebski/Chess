package pl.p.lodz.view.chess;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import pl.p.lodz.chess.GameState;
import pl.p.lodz.chess.gui.*;
import pl.p.lodz.chess.rules.board.player.Player;

import java.util.ArrayList;

import static pl.p.lodz.engine.settings.ProfileSettings.getProfileSettings;

/**
 * Created by Taberu on 2017-05-01.
 */
public class BoardController {
    @FXML
    private Pane board_pane_main;
    @FXML
    private ImageView board_image_background;
    @FXML
    private GridPane board_gridpane_mainboard;
    @FXML
    private AnchorPane board_anchor_mainboard;
    @FXML
    private ListView<String> board_listview_ANWhite;
    @FXML
    private ListView<String> board_listview_ANBlack;
    @FXML
    private Label board_label_White;
    @FXML
    private Label board_label_WTime;
    @FXML
    private Label board_label_Black;
    @FXML
    private Label board_label_BTime;
    @FXML
    private Label board_label_uppermessage;
    @FXML
    private Label board_label_bottommessage;
    @FXML
    private VBox board_vbox_messages;
    @FXML
    private Button board_button_rematch;

    private ArrayList<Pane> AllShadesAndHighlights = new ArrayList<>();
    private boolean bClickEventConsumed = false;

    private final static String whitePawn = "file:src/pl/p/lodz/view/images/w1.png";
    private final static String whiteRook = "file:src/pl/p/lodz/view/images/w2.png";
    private final static String whiteKnight = "file:src/pl/p/lodz/view/images/w3.png";
    private final static String whiteBishop = "file:src/pl/p/lodz/view/images/w4.png";
    private final static String whiteQueen = "file:src/pl/p/lodz/view/images/w5.png";
    private final static String whiteKing = "file:src/pl/p/lodz/view/images/w6.png";

    private final static String blackPawn = "file:src/pl/p/lodz/view/images/b1.png";
    private final static String blackRook = "file:src/pl/p/lodz/view/images/b2.png";
    private final static String blackKnight = "file:src/pl/p/lodz/view/images/b3.png";
    private final static String blackBishop = "file:src/pl/p/lodz/view/images/b4.png";
    private final static String blackQueen = "file:src/pl/p/lodz/view/images/b5.png";
    private final static String blackKing = "file:src/pl/p/lodz/view/images/b6.png";

    private final static String shadeImg = "file:src/pl/p/lodz/view/images/shade.png";
    private final static String highlightImg = "file:src/pl/p/lodz/view/images/highlight.png";

    public void spawnPiece(PieceInfo pieceInfo){
        Pane pane = addImageToGrid(
                getProperImageURL(pieceInfo.getPieceType(), pieceInfo.getPlayerInfo()),
                pieceInfo.getPosition().getX(),
                pieceInfo.getPosition().getY()
        );
        pane.setOnMouseClicked( this::PieceClicked );
    }

    public void removePiece(PieceInfo pieceInfo){
        try {
            Pane NodeToDelete = (Pane) getNodeFromGridPane(pieceInfo.getPosition().getX(), pieceInfo.getPosition().getY());
            board_gridpane_mainboard.getChildren().remove(NodeToDelete);
        }
        catch(IndexOutOfBoundsException e){
            e.printStackTrace(System.err);
        }
    }

    public void removeAllPieces(){
        board_gridpane_mainboard.getChildren().removeAll(board_gridpane_mainboard.getChildren());
    }

    public void movePiece(MoveInfo moveInfo){
        try {
            removePiece(moveInfo.getPieceBeforeMovement());
            spawnPiece(moveInfo.getPieceAfterMovement());
        }
        catch(IndexOutOfBoundsException e){
            e.printStackTrace(System.err);
        }
    }

    public void showPossibleMove(PositionInfo positionInfo){
        Pane shade = addImageToGrid(shadeImg, positionInfo.getX(), positionInfo.getY());
        AllShadesAndHighlights.add(shade);
        shade.setOnMouseClicked( this::PossibleMoveClicked );
        if(!getProfileSettings().getRules().isShowPossibleMoveShades()) {
            shade.setOpacity(0.0);
        }
    }

    public void hideAllPossibleMoves(){
        for(Pane pane : AllShadesAndHighlights){
            board_gridpane_mainboard.getChildren().remove(pane);
        }
    }

    public void highlightPosition(PositionInfo positionInfo){
        Pane highlight = addImageToGrid(highlightImg, positionInfo.getX(), positionInfo.getY());
        AllShadesAndHighlights.add(highlight);
    }

    public void addANMove(ANInfo anInfo){
        if(anInfo.getPlayerInfo().getPlayer() == Player.Color.white){
            board_listview_ANWhite.getItems().add(Integer.toString(getMoveCount()) + ".    " + anInfo.getAN());
            return;
        }
        board_listview_ANBlack.getItems().add(Integer.toString(getMoveCount()) + ".    " + anInfo.getAN());
    }

    public void switchPlayer(PlayerInfo playerInfo){
        if(playerInfo.getPlayer() == Player.Color.white){
            board_label_White.setText("[WHITE]*");
            board_label_Black.setText("BLACK");
            return;
        }
        board_label_White.setText("WHITE");
        board_label_Black.setText("[BLACK]*");
    }

    public int getMoveCount(){
        return board_listview_ANWhite.getItems().size() + board_listview_ANBlack.getItems().size();
    }

    public void updateTime(TimeInfo timeInfo){
        if(timeInfo.getPlayerInfo().getPlayer() == Player.Color.white){
            board_label_WTime.setText(timeInfo.getTime());
            return;
        }
        board_label_BTime.setText(timeInfo.getTime());
    }

    public void showUpperMessage(String message){
        board_label_uppermessage.setText(message);
        board_vbox_messages.setVisible(true);
        board_label_uppermessage.setVisible(true);
    }

    public void showBottomMessage(String message){
        board_label_bottommessage.setText(message);
        board_vbox_messages.setVisible(true);
        board_label_bottommessage.setVisible(true);
    }

    public void hideMessages(){
        board_label_uppermessage.setVisible(false);
        board_label_bottommessage.setVisible(false);
        board_vbox_messages.setVisible(false);
        board_button_rematch.setVisible(false);
    }

    public void finishMatch(PlayerInfo playerInfo){
        board_button_rematch.setVisible(true);
        if(playerInfo.getPlayer() == Player.Color.white){
            if(!getProfileSettings().getRules().isInvertTheBoard()) {
                showUpperMessage("Black Lost");
                showBottomMessage("White Won");
            }
            else{
                showUpperMessage("White Won");
                showBottomMessage("Black Lost");
            }
        }
        else{
            if(!getProfileSettings().getRules().isInvertTheBoard()) {
                showUpperMessage("Black Won");
                showBottomMessage("White Lost");
            }
            else{
                showUpperMessage("White Lost");
                showBottomMessage("Black Won");
            }
        }
    }

    public void closeSession(){
        hideMessages();
        hideAllPossibleMoves();
        board_gridpane_mainboard.getChildren().removeAll();
    }



    @FXML
    private void initialize() {
        board_image_background.fitWidthProperty().bind(board_anchor_mainboard.widthProperty());
        board_image_background.fitHeightProperty().bind(board_anchor_mainboard.heightProperty());

        board_pane_main.widthProperty().addListener( this::keepBoardAsSquare );
        board_pane_main.heightProperty().addListener( this::keepBoardAsSquare );

        board_gridpane_mainboard.setOnMouseClicked( this::BoardClicked );

        board_label_White.setText("WHITE");
        board_label_Black.setText("BLACK");

        board_button_rematch.setOnMouseClicked( this::rematchButtonClicked );
        hideMessages();

        Display.setOnPieceMoved( this::movePiece );
        Display.setOnPieceSpawned( this::spawnPiece );
        Display.setOnPieceRemoved( this::removePiece );
        Display.setOnPossibleMoveShowed( this::showPossibleMove );
        Display.setOnAllPossibleMovesRemoved( this::hideAllPossibleMoves );
        Display.setOnPositionHighlighted( this::highlightPosition );
        Display.setOnANEntryAdded( this::addANMove );
        Display.setOnPlayerSwitched( this::switchPlayer );
        Display.setOnTimeChanged( this::updateTime );
        Display.setOnMatchFinished( this::finishMatch );
        Display.setOnSessionClosed( this::closeSession );
    }

    private void keepBoardAsSquare(ObservableValue<? extends Number> ov, Number t, Number NewWidthOrHeight){
        if(shouldBoardBeResized(NewWidthOrHeight)){
            board_anchor_mainboard.setPrefHeight(NewWidthOrHeight.doubleValue());
            board_anchor_mainboard.setPrefWidth(NewWidthOrHeight.doubleValue());
            resizeBoardPadding();
        }
    }

    private boolean shouldBoardBeResized(Number NewWidthOrHeight){
        double boardNewWidthOrHeight = NewWidthOrHeight.doubleValue();
        double boardCurrentWidth = board_pane_main.getWidth();
        double boardCurrentHeight = board_pane_main.getHeight();
        double boardMinimumWidth = board_anchor_mainboard.getMinWidth();
        return (boardNewWidthOrHeight < boardCurrentWidth || boardNewWidthOrHeight < boardCurrentHeight) && boardNewWidthOrHeight > boardMinimumWidth;
    }

    private void resizeBoardPadding(){
        AnchorPane.setTopAnchor(board_gridpane_mainboard, board_anchor_mainboard.getPrefHeight() * 0.0352941176470588);
        AnchorPane.setLeftAnchor(board_gridpane_mainboard, board_anchor_mainboard.getPrefWidth() * 0.0411764705882353);
        AnchorPane.setRightAnchor(board_gridpane_mainboard, board_anchor_mainboard.getPrefWidth() * 0.0392156862745098);
        AnchorPane.setBottomAnchor(board_gridpane_mainboard, board_anchor_mainboard.getPrefHeight() * 0.0372549019607843);
    }

    private Pane addImageToGrid(String pathToImage, int column, int row){
        Image Shade = new Image(pathToImage);
        ImageView NewImageView = new ImageView(Shade);
        Pane NewPane = new Pane();
        NewPane.getChildren().add(NewImageView);
        board_gridpane_mainboard.add(NewPane, column, row);

        GridPane.setFillWidth(NewPane, true);
        GridPane.setFillHeight(NewPane, true);
        NewImageView.fitWidthProperty().bind(NewPane.widthProperty());
        NewImageView.fitHeightProperty().bind(NewPane.heightProperty());

        return NewPane;
    }

    private void PieceClicked(MouseEvent e){
        bClickEventConsumed = true;
        Node nodeClicked = GetNodeClicked(e);
        GameState.getInstance().getCurrentGame().PieceClicked(
            GridPane.getColumnIndex(nodeClicked),
            GridPane.getRowIndex(nodeClicked)
        );
    }

    private void BoardClicked(MouseEvent e){
        if(bClickEventConsumed){
            bClickEventConsumed = false;
            return;
        }

        if(GameState.getInstance().getCurrentGame().IsAnyPieceSelected()) {
            GameState.getInstance().getCurrentGame().UnselectPiece(null);
        }
    }

    private void PossibleMoveClicked(MouseEvent e){
        bClickEventConsumed = true;
        Node nodeClicked = GetNodeClicked(e);
        GameState.getInstance().getCurrentGame().MoveSelectedPiece(
            GridPane.getColumnIndex(nodeClicked),
            GridPane.getRowIndex(nodeClicked)
        );
    }

    private Node GetNodeClicked(MouseEvent e){
        Node nodeClicked = e.getPickResult().getIntersectedNode();
        if( nodeClicked instanceof ImageView ){
            nodeClicked = e.getPickResult().getIntersectedNode().getParent();
        }
        return nodeClicked;
    }

    private Node getNodeFromGridPane(int column, int row) {
        for (Node node : board_gridpane_mainboard.getChildren()) {
            try {
                if (GridPane.getColumnIndex(node) == column && GridPane.getRowIndex(node) == row) {
                    return node;
                }
            }
            catch(NullPointerException e){}
        }
        return null;
    }

    private void rematchButtonClicked(MouseEvent e){

    }

    private String getProperImageURL(PieceInfo.PieceType pieceType, PlayerInfo playerInfo){
        if(playerInfo.getPlayer() == Player.Color.white){
            switch(pieceType){
                case pawn: return whitePawn;
                case bishop: return whiteBishop;
                case knight: return whiteKnight;
                case king: return whiteKing;
                case queen: return whiteQueen;
                case rook: return whiteRook;
            }
        }
        switch(pieceType){
            case pawn: return blackPawn;
            case bishop: return blackBishop;
            case knight: return blackKnight;
            case king: return blackKing;
            case queen: return blackQueen;
            case rook: return blackRook;
        }
        return "";
    }
}
