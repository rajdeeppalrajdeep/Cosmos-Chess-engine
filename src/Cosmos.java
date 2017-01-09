
public class Cosmos 
{
	public static long wPawn,wRook,wKnight, wBishop, wKing, wQueen, bPawn,bRook,bKnight, bBishop, bKing, bQueen,empasant;
	static boolean wCastleQ=true,wCastleK=true,bCastleQ=true,bCastleK=true; 
	static int MATE_SCORE=10000,NULL_INT=Integer.MIN_VALUE;
	static int searchDepth=4,moveCounter;
	static int bestMoveIndex = 0;
	static boolean whiteToMove = true;
	static boolean debug =false,debug1 = true;
	static Gui g;
	public static void main(String[] args) 
	{
		/*Move_Generation.makebitBoards();
		System.out.println("started");
		long start = System.currentTimeMillis();
		Perft.perftHead(wPawn,wRook,wKnight, wBishop, wKing, wQueen, bPawn,bRook,bKnight, bBishop, bKing, bQueen,wCastleQ,wCastleK,bCastleQ,bCastleK,true,0 );
		long end = System.currentTimeMillis();
		System.out.println("ended");
		System.out.println("time taken "+(end-start));*/
		g = new Gui();
		g.disp();
		//UCI.uciCommunication();
		
	}
}
