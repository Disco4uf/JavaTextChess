package com.company.Structs;

import com.company.Classes.myFunc;

public class ChessHistory   implements Cloneable{

    public final int index;
    public final int fromX,fromY;
    public final int toX,toY;
    public final ChessPiece[][] theGamePiecesBeforeMove;
    public final ChessPiece fromPiece;
    public final ChessPiece toPiece;

    public Object clone(){
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ChessHistory(int index, ChessPiece[][] theGamePiecesBeforeMove,
                        ChessPiece fromPiece, ChessPiece toPiece){

        this.index=index;
        this.fromPiece=fromPiece;
        this.toPiece=toPiece;

        if(fromPiece!=null) {
            this.fromX = fromPiece.x;
            this.fromY = fromPiece.y;
        }else{
            this.fromX =-1;
            this.fromY =-1;
        }
        if(toPiece!=null){
            this.toX=toPiece.x;
            this.toY=toPiece.y;
        }else{
            this.toX=-1;
            this.toY=-1;
        }
        this.theGamePiecesBeforeMove= myFunc.CloneChessBoard(theGamePiecesBeforeMove);

    }

    public ChessHistory(int index, ChessPiece[][] theGamePiecesBeforeMove, int fx, int fy, int tx, int ty){

        this.index=index;

        this.fromX=fx;
        this.fromY=fy;
        this.toX=tx;
        this.toY=ty;

        if(fx>=1&&fy>=1&&fx<=8&&fy<=8){
            fromPiece=theGamePiecesBeforeMove[fx][fy];
        }else{
            fromPiece=null;
        }

        if(tx>=1&&ty>=1&&tx<=8&&ty<=8){
            toPiece=theGamePiecesBeforeMove[tx][ty];
        }else{
            toPiece=null;
        }

        this.theGamePiecesBeforeMove= myFunc.CloneChessBoard(theGamePiecesBeforeMove);

    }

}
