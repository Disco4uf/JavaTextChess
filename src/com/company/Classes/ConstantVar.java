package com.company.Classes;

public class ConstantVar {
	public enum PlayerColor{
		white, black
	}
	public static boolean _isDebug=false;


	public  static String regexAcceptableCharacters="[a-zA-Z0-9]*";
	public  static String regexRegister="register (.*?) (.*?)$";
	public  static String regexLogin="login (.*?) (.*?)$";
	public  static String regexRemove="remove (.*?) (.*?)$";
	public  static String regexNewGame="new_game (.*?) (.*?)$";
	public  static String regexSelect="select (.*?),(.*?)$";
	public  static String regexMove="move (.*?),(.*?)$";
	public  static String regexSelectMove="sm (.*?),(.*?) (.*?),(.*?)$";

	public  static String errNotExistPlayer="no user exists with this username";
	public  static String errInvalidLimit="number should be positive to have a limit or 0 for no limit";
	public  static String errChooseAnotherPlayer="you must choose another player to start a game";
	public  static String errInvalidUsername="username format is invalid";
	public  static String errInvalidPass="password format is invalid";
	public  static String errUserAlreadyExist="a user exists with this username";
	public  static String errincorrectPass="incorrect password";
	public  static String errInvalidCmd="invalid command";
	public  static String errAlreadyMoved="already moved";
	public  static String errHasNotSelected="do not have any selected piece";
	public  static String errNoPiece="no piece on this spot";
	public  static String errIsYourEnemyPiece="you can only select one of your pieces";
	public  static String errCantMoveThere="cannot move to the spot";
	public  static String errPieceOutOfRange="wrong coordination";
	public  static String errHasNotMoved="you must move then proceed to next turn";
	public  static String errAlreadyUsedAllUndo="you cannot undo anymore";
	public  static String errHasNotMovedBeforeUndo="you must move before undo";
	public  static String errAlreadyUsedThisTurnUndo="you have used your undo for this turn";
	public  static String errUnknown="unknown error";

	public  static String successRegister="register successful";
	public  static String successLogin="login successful";
	public  static String successRemove="removed * successfully";
	public  static String successLogOut="logout successful";
	public  static String successNewGame="new game started successfully between [first] and [second] with limit [limit]";
	public  static String successSelected="selected";
	public  static String successDeselected="deselected";
	public  static String successMovedAndDestroyed="rival piece destroyed";
	public  static String successMoved="moved";
	public  static String successTurnCompleted="turn completed";
	public  static String successUndo="undo completed";


	public  static String strShowTurn="it is player [username] turn with color [color]";
	public  static String strShowUndoNum="you have [n] undo moves";
}
