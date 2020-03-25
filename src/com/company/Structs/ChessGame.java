package com.company.Structs;

import com.company.Classes.Constant;
import com.company.Classes.myFunc;
import com.company.Main;

import java.util.ArrayList;
import java.util.List;

public class ChessGame {
    ChessPiece[][] theGamePieces;
    public List<ChessMove> moveHistory;
    public List<ChessMove> killHistory;
    ChessPiece[][] theGamePiecesBeforeMove;


    ChessMove LastMove;

    Player player1;
    Player player2;

    public Player currentPlayer;
    int limit;
    int currentMoves=0;


    public ChessGame(Player p1,Player p2,int limit){
        this.player1=p1;
        this.player2=p2;
        this.currentMoves=0;
        this.limit=limit;

        theGamePieces=new ChessPiece[9][9];
        theGamePiecesBeforeMove=new ChessPiece[9][9];
        moveHistory=new ArrayList<ChessMove>();
        //=========================================

        if(p1.getPlayerColor()==p2.getPlayerColor()){
            //Wrong player colors :
            p1.setPlayerColor(Constant.PlayerColor.White);
            p2.setPlayerColor(Constant.PlayerColor.Black);
            //colors fixed.
        }

        for (ChessPiece p:
            ChessPiece.CreatePieces(p1)) {
            theGamePieces[p.OriginalX][p.OriginalY]=p;
            p.isFirstMove=true;
        }
        for (ChessPiece p:
            ChessPiece.CreatePieces(p2)) {
            theGamePieces[p.OriginalX][p.OriginalY]=p;
            p.isFirstMove=true;
        }
        p1.undo_remain=2;
        p2.undo_remain=2;
        SwitchPlayer(1);
        //========================================
        //PrintBoard(true);
    }


    public void SwitchPlayer(int pIndex){

        if(pIndex<=0){//If index == 0  toggle the Player
            if(currentPlayer!=player1){
                pIndex=1;
            }else{
                pIndex=2;
            }
        }

        //=== set player from index
        if(pIndex==1)currentPlayer=player1;
        else if(pIndex==2)currentPlayer=player2;
        if(currentPlayer!=null){
            if(Constant._isDebug){
                System.out.println(" >> Its "+currentPlayer.getPlayerName()+"'s turn ! "
                +"\n    > playerColor : "+currentPlayer.getPlayerColor()
                //+"\n    > player : "+currentPlayer.get
                );
            }
            SaveCurrentBoard();

            currentPlayer.selectedPiece=null;
            currentPlayer.hasMoved=false;
            currentPlayer.usedUndo=false;
        }
    }
    public void SaveCurrentBoard(){
        theGamePiecesBeforeMove=myFunc.CloneChessBoard(theGamePieces);
    }
    public boolean MakeMove(int tx,int ty) {
        int fx=currentPlayer.selectedPiece.x;
        int fy=currentPlayer.selectedPiece.y;
        return MakeMove(fx,fy,tx,ty);
    }
    public boolean MakeMove(int fx,int fy,int tx,int ty) {
        if(currentPlayer.hasMoved){
            System.out.println(Constant.errAlreadyMoved);
            return false;
        }
        if (fx < 1 || fy < 1 || tx < 1 || ty < 1
                || fx > 8 || fy > 8 || tx > 8 || ty > 8) {
            System.out.println(Constant.errPieceOutOfRange);
            return false;
        }
        if(currentPlayer.selectedPiece == null) {
            System.out.println(Constant.errHasNotSelected);
            return false;
        }

        ChessPiece fromPiece=theGamePieces[fx][fy];
        ChessPiece toPiece=theGamePieces[tx][ty];
        if (fx == tx && fy == ty) {
            //invalid same pos
            return false;
        }


        if (fromPiece == null) {
            System.out.println(Constant.errNoPiece);
            return false;
        }
        if (toPiece != null && fromPiece.OwnerColor == toPiece.OwnerColor) {
            if(Constant._isDebug){
                System.out.println(Constant.errCantMoveThere+" (Same color) : "+fromPiece.mapIcon+" and "+toPiece.mapIcon);
            }else{
                System.out.println(Constant.errCantMoveThere);
            }
            return false;
        }
        if (fromPiece.Owner != currentPlayer) {
            //invalid , not my piece
            return false;
        }

        if (fromPiece == toPiece){

            if(Constant._isDebug){
                System.out.println(Constant.errCantMoveThere+" (Same Piece) ");
            }else{
                System.out.println(Constant.errCantMoveThere);
            }
        }

        if(!IsValidMove(fromPiece,toPiece,fx,fy,tx,ty)){

            if(Constant._isDebug){
                System.out.println(Constant.errCantMoveThere+" (Invalid Move) ");
            }else{
                System.out.println(Constant.errCantMoveThere);
            }
            return false;
        }



        //===================================
        //Make the move
        if(toPiece==null)System.out.println(Constant.successMoved);
        else System.out.println(Constant.successMovedAndDestroyed);
        LastMove=new ChessMove(moveHistory.size(),theGamePieces,fx,fy,tx,ty);//Save History


        //-------------
        theGamePieces[tx][ty]=null;//Capture if any exists
        //-------------

        fromPiece.x=tx;
        fromPiece.y=ty;

        theGamePieces[tx][ty]=fromPiece;//move
        theGamePieces[fx][fy]=null;//Set old to null


        fromPiece.isFirstMove=false;
        currentPlayer.hasMoved=true;


        return true;
    }

    public ChessPiece getPieceByPosition(int fx,int fy){
        if(fx<1||fy<1||fx>8||fy>8){
            return null;
        }
        return theGamePieces[fx][fy];
    }
    public boolean currentPlayerSelect(int fx,int fy){
            if(fx<1||fy<1||fx>8||fy>8){
                System.out.println(Constant.errPieceOutOfRange);
                return false;
            }
            ChessPiece sp=getPieceByPosition(fx,fy);
        if(currentPlayer==null){
            //ERROR Current player is null ! (Should never happen)
            System.out.println(Constant.errUnknown);
            return false;
        }
            if(sp==null){
                System.out.println(Constant.errNoPiece);
                return false;
            }

            if (sp.Owner != currentPlayer) {
                    //invalid , not my piece
                    System.out.println(Constant.errIsYourEnemyPiece);
                    if(Constant._isDebug){
                        System.out.println(" [deubg] "+sp.mapIcon+" is a piece of "+sp.Owner.getPlayerName()+" : "+sp.OwnerColor);
                    }
                    return false;
            }

            currentPlayer.selectedPiece=sp;

            if(Constant._isDebug){
                System.out.println(Constant.successSelected+" "+sp.mapIcon+" ");
            }
            else  System.out.println(Constant.successSelected);

          return true;
    }
    public boolean currentPlayerDeselect(){
        if(currentPlayer.selectedPiece==null){
            System.out.println(Constant.errNoPiece);
            return false;
        }
        currentPlayer.selectedPiece=null;
        System.out.println(Constant.successDeselected);
        return true;
    }
    public boolean currentPlayerEndTurn(){
        if(currentPlayer==null|| !currentPlayer.hasMoved){
            System.out.println(Constant.errHasNotMoved);
            return false;
        }
        System.out.println(Constant.successTurnCompleted);

        moveHistory.add(LastMove);//Add Move His tory
        if(LastMove.toPiece!=null)killHistory.add(LastMove);

        SwitchPlayer(0);//Switch to next player
        return true;
    }
    public void currentPlayerShowTurn(){

        System.out.println(Constant.strShowTurn
                .replace("[player]",currentPlayer.getPlayerName())
                .replace("[color]",currentPlayer.getPlayerColor().toString())
        );
    }
    public boolean currentPlayerUndo(){

        if(currentPlayer.undo_remain<=0){
            System.out.println(Constant.errAlreadyUsedAllUndo);
            return false;
        }
        if(currentPlayer.usedUndo){
            System.out.println(Constant.errAlreadyUsedThisTurnUndo);
            return false;
        }
        if(!currentPlayer.hasMoved){
            System.out.println(Constant.errHasNotMovedBeforeUndo);
            return false;
        }
        //=============================================
        for (int i = 0; i <=8 ; i++) {
            for (int j = 0; j <=8 ; j++) {
                theGamePieces[i][j]=theGamePiecesBeforeMove[i][j];
            }
        }
        //=============================================
        currentPlayer.hasMoved=false;
        currentPlayer.usedUndo=true;

        System.out.println(Constant.successUndo);

        if(Constant._isDebug){
            PrintBoard(true,true);
        }

        return true;
    }
    public void currentPlayerShowUndoNumber(){

        System.out.println(Constant.strShowUndoNum
                .replace("[n]",currentPlayer.undo_remain+"")

        );
    }

    public boolean IsValidMove(ChessPiece fromPiece,ChessPiece toPiece,int fx,int fy,int tx,int ty){
        if(fromPiece==null)return false;
        if(toPiece!=null&&(fromPiece.OwnerColor == toPiece.OwnerColor))return false;
        switch (fromPiece.name){
            case "king":
                return myFunc.Distance(fx,fy,tx,ty)<=1;
            case "pawn":
                int multiplier=1;
                if(fromPiece.OwnerColor== Constant.PlayerColor.Black) {
                    multiplier=-1;
                }
                    if (fromPiece.isFirstMove && tx - fx == 2*multiplier && ty == tx) return true;
                    else if (ty == fy && tx - fx == 1*multiplier) return true;
                    else if (toPiece != null && toPiece.Owner != this.currentPlayer && tx - fx == 1*multiplier && Math.abs(ty - fy) == 1)
                        return true;
                    else return false;


            case "knight":
                if((Math.abs(tx-fx)==2&& Math.abs(ty-fy)==1) || (Math.abs(ty-fx)==1 && Math.abs(ty-fy)==2)) return true;
                else return false;
            case "rook":
                if(ty==fy || tx==fx){
                    if(isFreeSpace(fx,fy,tx,ty)) return true;
                }
                return false;
            case "bishop":
                if(Math.abs(tx-fx)==Math.abs(ty-fy) && isFreeSpace(fx, fy, tx, ty)) return true;
                else return false;
            case "queen":
                if(ty==fy || tx==fx){
                    if(isFreeSpace(fx,fy,tx,ty)) return true;
                }
                else if(Math.abs(tx-fx)==Math.abs(ty-fy) && isFreeSpace(fx, fy, tx, ty)) return true;
                else return false;
        }
        return true;
    }
    private boolean isFreeSpace(int fx,int fy,int tx,int ty){
        if(fx==tx){
            if(ty<fy){
                int tmp=fy;
                fy=ty;
                ty=tmp;
            }
            for(int i=fy+1;i<ty;i++){
                if (theGamePieces[fx][i]!=null) return false;
            }
            return true;
        }
        else if(fy==ty){
            if(tx<fx){
                int tmp=fx;
                fx=tx;
                tx=tmp;
            }
            for(int i=fx+1;i<tx;i++){
                if (theGamePieces[fx][i]!=null) return false;
            }
            return true;
        }
        else if(Math.abs(tx-fx)==Math.abs(ty-fy)){
            if(tx<fx){
                int tmp=fx;
                fx=tx;
                tx=tmp;
            }
            if(ty<fy){
                int tmp=fy;
                fy=ty;
                ty=tmp;
            }
            for(int i=1;i<ty-fy;i++)
                if (theGamePieces[fx+i][fy+i]!=null) return false;
            return true;
        }
        else return false;
    }
    public void PrintBoard(boolean fancy,boolean flip){
        boolean IsFirst=true;
        System.out.println("");

        if(!fancy)fancy=Constant._isDebug;

        for (int i = 1; i < theGamePieces.length; i++) {
            IsFirst=true;
            for (int j = 1; j < theGamePieces[i].length; j++) {
                ChessPiece p;
                if(flip){
                    p=theGamePieces[9-i][j];
                }else{
                    p=theGamePieces[i][j];
                }


                if(IsFirst)IsFirst=false;
                else System.out.print("|");

                if (fancy){
                    if(p==null) System.out.print(" ");
                    else System.out.print(p.mapIcon);
                }else{
                    if(p==null) System.out.print("  ");
                    else System.out.print(p.mapChar);
                }

            }
            System.out.print("\n");
        }
    }
    public static void startTheGame(String p1,String p2,int limit){
        if (!p1.matches(Constant.regexAcceptableCharacters)) {
            System.out.println(Constant.errInvalidUsername);
            return;
        } else if (limit < 0) {
            System.out.println(Constant.errInvalidLimit);
            return;
        } else if (p1.equals(p2)) {
            System.out.println(Constant.errChooseAnotherPlayer);
            return;
        } else {
            var p1c=Player.getPlayerByName(p1);
            var p2c=Player.getPlayerByName(p2);
            if (p1c == null) {
                System.out.println(Constant.errNotExistPlayer);
                return;
            }
            else if (p2c == null) {
                System.out.println(Constant.errNotExistPlayer);
                return;
            }else{
                Main.theGame = new ChessGame(p1c, p2c, limit);
                Menu.setMenuSituation(Menu.situation.gameMenu);
                System.out.println(Constant.successNewGame
                        .replace("[first]",p1)
                        .replace("[second]",p2)
                        .replace("[limit]",limit+"")
                )
                        ;
            }

        }
    }
}
