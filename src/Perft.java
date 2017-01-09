
public class Perft
{
    static int perftTotalMoveCounter=0;
    static int perftMoveCounter=0;
    static int perftMaxDepth;
    static int count = 1;
    static int Castel = 0 , captures = 0, promotions = 0 , checks = 0;
    public static String moveName(String move)
    {
        String moveString="";
        moveString+=""+(char)(move.charAt(2)+49);
        moveString+=""+('8'-move.charAt(1));
        moveString+=""+(char)(move.charAt(4)+49);
        moveString+=""+('8'-move.charAt(3));
        return moveString;
    }
    public static void perftHead(long wPawn,long wRook,long wKnight,long wBishop,long wKing,long wQueen,long bPawn,long bRook,long bKnight,long bBishop,long bKing,long bQueen,boolean wCastleQ,boolean wCastleK,boolean bCastleQ,boolean bCastleK,boolean whiteToMove,int depth  )
	{
		String moves;
        if (whiteToMove)
		{
            moves=Move_Generation.whiteMove(wPawn,wRook,wKnight, wBishop, wKing, wQueen, bPawn,bRook,bKnight, bBishop, bKing, bQueen,wCastleQ,wCastleK,bCastleQ,bCastleK);
        } 
		else 
		{
            moves=Move_Generation.blackMove(wPawn,wRook,wKnight, wBishop, wKing, wQueen, bPawn,bRook,bKnight, bBishop, bKing, bQueen,wCastleQ,wCastleK,bCastleQ,bCastleK);
        }
        for(int i = 0; i<moves.length();i=i+5)
        {
      
        	int start=(Character.getNumericValue(moves.charAt(i+1))*8)+(Character.getNumericValue(moves.charAt(i+2)));
    		int end=(Character.getNumericValue(moves.charAt(i+3))*8)+(Character.getNumericValue(moves.charAt(i+4)));
    		char moveType = moves.charAt(i);
        	long 	twPawn=Move_Generation.makeMove(wPawn,start,end,moveType, 'P'), twKnight=Move_Generation.makeMove(wKnight, start,end,moveType, 'N'),
                    twBishop=Move_Generation.makeMove(wBishop, start,end,moveType, 'B'), twRook=Move_Generation.makeMove(wRook,start,end,moveType, 'R'),
                    twQueen=Move_Generation.makeMove(wQueen, start,end,moveType, 'Q'), twKing=Move_Generation.makeMove(wKing, start,end,moveType, 'K'),
                    tbPawn=Move_Generation.makeMove(bPawn, start,end,moveType, 'p'), tbKnight=Move_Generation.makeMove(bKnight, start,end,moveType, 'n'),
                    tbBishop=Move_Generation.makeMove(bBishop, start,end,moveType, 'b'), tbRook=Move_Generation.makeMove(bRook, start,end,moveType, 'r'),
                    tbQueen=Move_Generation.makeMove(bQueen, start,end,moveType, 'q'), tbKing=Move_Generation.makeMove(bKing, start,end,moveType, 'k');
        			//tempasant = Move_Generation.makeMoveEmpasant(wPawn|bPawn,start,end,moveType);
        	
        	boolean twCastleQ=wCastleQ,twCastleK=wCastleK,tbCastleQ=bCastleQ,tbCastleK=bCastleK;
        	if(moveType==' ')
        	{
        		//int start=(Character.getNumericValue(moves.charAt(i+1))*8)+(Character.getNumericValue(moves.charAt(i+2)));
        		if (((1L<<start)&wKing)!=0) 
        		{
        			twCastleQ=false;
        			twCastleK=false;
        		}
                else if (((1L<<start)&bKing)!=0) 
                {
                	tbCastleQ=false; 
                	tbCastleK=false;
                }
                else if (((1L<<start)&wRook&(1L<<63))!=0) 
                {
                	twCastleK=false;
                }
                else if (((1L<<start)&wRook&(1L<<56))!=0) 
                {
                	twCastleQ=false;
                }
                else if (((1L<<start)&bRook&(1L<<7))!=0) 
                {
                	tbCastleK=false;
                }
                else if (((1L<<start)&bRook&1L)!=0) 
                {
                	tbCastleQ=false;
                }
        	}
        	if (((twKing&Move_Generation.AttackBlack(twPawn,twRook,twKnight, twBishop, twKing, twQueen,tbPawn,tbRook,tbKnight,tbBishop,tbKing,tbQueen))==0 && whiteToMove) ||(((tbKing&Move_Generation.AttackWhite(twPawn,twRook,twKnight, twBishop, twKing, twQueen,tbPawn,tbRook,tbKnight,tbBishop,tbKing,tbQueen))==0 && !whiteToMove))) 
			{
                perft(twPawn,twRook,twKnight, twBishop, twKing, twQueen,tbPawn,tbRook,tbKnight,tbBishop,tbKing,tbQueen,twCastleQ,twCastleK,tbCastleQ,tbCastleK,!whiteToMove,depth+1);
                System.out.println(moveName(moves.substring(i,i+5))+" "+perftMoveCounter);
				perftTotalMoveCounter+=perftMoveCounter;
				perftMoveCounter=0;
			}
        	else
        	{
        		checks++;
        	}
        	
        }
	}
	public static void perft(long wPawn,long wRook,long wKnight,long wBishop,long wKing,long wQueen,long bPawn,long bRook,long bKnight,long bBishop,long bKing,long bQueen,boolean wCastleQ,boolean wCastleK,boolean bCastleQ,boolean bCastleK,boolean whiteToMove,int depth  )
	{
		if(depth<perftMaxDepth)
		{
			String moves;
	        if (whiteToMove)
			{
	            moves=Move_Generation.whiteMove(wPawn,wRook,wKnight, wBishop, wKing, wQueen, bPawn,bRook,bKnight, bBishop, bKing, bQueen,wCastleQ,wCastleK,bCastleQ,bCastleK);
	        } 
			else 
			{
	            moves=Move_Generation.blackMove(wPawn,wRook,wKnight, wBishop, wKing, wQueen, bPawn,bRook,bKnight, bBishop, bKing, bQueen,wCastleQ,wCastleK,bCastleQ,bCastleK);
	        }
	        for(int i = 0; i<moves.length();i=i+5)
	        {
        		int start=(Character.getNumericValue(moves.charAt(i+1))*8)+(Character.getNumericValue(moves.charAt(i+2)));
        		int end=(Character.getNumericValue(moves.charAt(i+3))*8)+(Character.getNumericValue(moves.charAt(i+4)));
        		char moveType = moves.charAt(i);
	        	long 	twPawn=Move_Generation.makeMove(wPawn,start,end,moveType, 'P'), twKnight=Move_Generation.makeMove(wKnight, start,end,moveType, 'N'),
	                    twBishop=Move_Generation.makeMove(wBishop, start,end,moveType, 'B'), twRook=Move_Generation.makeMove(wRook,start,end,moveType, 'R'),
	                    twQueen=Move_Generation.makeMove(wQueen, start,end,moveType, 'Q'), twKing=Move_Generation.makeMove(wKing, start,end,moveType, 'K'),
	                    tbPawn=Move_Generation.makeMove(bPawn, start,end,moveType, 'p'), tbKnight=Move_Generation.makeMove(bKnight, start,end,moveType, 'n'),
	                    tbBishop=Move_Generation.makeMove(bBishop, start,end,moveType, 'b'), tbRook=Move_Generation.makeMove(bRook, start,end,moveType, 'r'),
	                    tbQueen=Move_Generation.makeMove(bQueen, start,end,moveType, 'q'), tbKing=Move_Generation.makeMove(bKing, start,end,moveType, 'k');
    					

	        	boolean twCastleQ=wCastleQ,twCastleK=wCastleK,tbCastleQ=bCastleQ,tbCastleK=bCastleK;
	        	if(moveType==' ')
	        	{
	        		if (((1L<<start)&wKing)!=0) 
	        		{
	        			twCastleQ=false;
	        			twCastleK=false;
	        		}
	                else if (((1L<<start)&bKing)!=0) 
	                {
	                	tbCastleQ=false; 
	                	tbCastleK=false;
	                }
	                else if (((1L<<start)&wRook&(1L<<63))!=0) 
	                {
	                	twCastleK=false;
	                }
	                else if (((1L<<start)&wRook&(1L<<56))!=0) 
	                {
	                	twCastleQ=false;
	                }
	                else if (((1L<<start)&bRook&(1L<<7))!=0) 
	                {
	                	tbCastleK=false;
	                }
	                else if (((1L<<start)&bRook&1L)!=0) 
	                {
	                	tbCastleQ=false;
	                }
	        	}
	        	if (((twKing&Move_Generation.AttackBlack(twPawn,twRook,twKnight, twBishop, twKing, twQueen,tbPawn,tbRook,tbKnight,tbBishop,tbKing,tbQueen))==0 && whiteToMove) ||(((tbKing&Move_Generation.AttackWhite(twPawn,twRook,twKnight, twBishop, twKing, twQueen,tbPawn,tbRook,tbKnight,tbBishop,tbKing,tbQueen))==0 && !whiteToMove))) 
				{
	                if (depth+1==perftMaxDepth) 
	                {
	                	perftMoveCounter++;
	                }
	                perft(twPawn,twRook,twKnight, twBishop, twKing, twQueen,tbPawn,tbRook,tbKnight,tbBishop,tbKing,tbQueen,twCastleQ,twCastleK,tbCastleQ,tbCastleK,!whiteToMove,depth+1);
	            }
	        	else
	        	{
	        		checks++;
	        	}
	        	
	        }
		}
	}
}
