
public class Principle_variation 
{
	public static int getFirstLegalMove(String moves,long wPawn,long wRook,long wKnight,long wBishop,long wKing,long wQueen,long bPawn,long bRook,long bKnight,long bBishop,long bKing,long bQueen,boolean wCastleQ,boolean wCastleK,boolean bCastleQ,boolean bCastleK,boolean whiteToMove)
	{
        for (int i=0;i<moves.length();i+=5) 
		{
        	int start=(Character.getNumericValue(moves.charAt(i+1))*8)+(Character.getNumericValue(moves.charAt(i+2)));
    		int end=(Character.getNumericValue(moves.charAt(i+3))*8)+(Character.getNumericValue(moves.charAt(i+4)));
    		char moveType = moves.charAt(0);   
    		long 	twPawn=Move_Generation.makeMove(wPawn,start,end,moveType, 'P'), twKnight=Move_Generation.makeMove(wKnight, start,end,moveType, 'N'),
                    twBishop=Move_Generation.makeMove(wBishop, start,end,moveType, 'B'), twRook=Move_Generation.makeMove(wRook,start,end,moveType, 'R'),
                    twQueen=Move_Generation.makeMove(wQueen, start,end,moveType, 'Q'), twKing=Move_Generation.makeMove(wKing, start,end,moveType, 'K'),
                    tbPawn=Move_Generation.makeMove(bPawn, start,end,moveType, 'p'), tbKnight=Move_Generation.makeMove(bKnight, start,end,moveType, 'n'),
                    tbBishop=Move_Generation.makeMove(bBishop, start,end,moveType, 'b'), tbRook=Move_Generation.makeMove(bRook, start,end,moveType, 'r'),
                    tbQueen=Move_Generation.makeMove(bQueen, start,end,moveType, 'q'), tbKing=Move_Generation.makeMove(bKing, start,end,moveType, 'k');
    		if (((twKing&Move_Generation.AttackBlack(twPawn,twRook,twKnight, twBishop, twKing, twQueen,tbPawn,tbRook,tbKnight,tbBishop,tbKing,tbQueen))==0 && whiteToMove) ||(((tbKing&Move_Generation.AttackWhite(twPawn,twRook,twKnight, twBishop, twKing, twQueen,tbPawn,tbRook,tbKnight,tbBishop,tbKing,tbQueen))==0 && !whiteToMove))) 
			{
               // System.out.println("first legal move "+ i);
    			return i;
            }
        }
        return -1;
    }
	public static int alphaBeta(int alpha,int beta,long wPawn,long wRook,long wKnight,long wBishop,long wKing,long wQueen,long bPawn,long bRook,long bKnight,long bBishop,long bKing,long bQueen,boolean wCastleQ,boolean wCastleK,boolean bCastleQ,boolean bCastleK,boolean whiteToMove,int depth)
	{
		if (depth == Cosmos.searchDepth)
        {
           return - Evaluation.evaluate(wPawn, wKnight, wBishop, wRook, wQueen, wKing, bPawn, bKnight, bBishop, bRook, bQueen, bKing, whiteToMove,wCastleQ,wCastleK,bCastleQ,bCastleK);
           
            
        }
		String moves;
        if (whiteToMove) 
		{
            moves=Move_Generation.whiteMove(wPawn,wRook,wKnight, wBishop, wKing, wQueen, bPawn,bRook,bKnight, bBishop, bKing, bQueen,wCastleQ,wCastleK,bCastleQ,bCastleK);
        }
		else 
		{
            moves=Move_Generation.blackMove(wPawn,wRook,wKnight, wBishop, wKing, wQueen, bPawn,bRook,bKnight, bBishop, bKing, bQueen,wCastleQ,wCastleK,bCastleQ,bCastleK);
        }
        //sortMoves();
        int firstLegalMove = getFirstLegalMove(moves,wPawn,wRook,wKnight, wBishop, wKing, wQueen, bPawn,bRook,bKnight, bBishop, bKing, bQueen,wCastleQ,wCastleK,bCastleQ,bCastleK,whiteToMove);
        if (firstLegalMove == -1)
        {
            return whiteToMove ? Cosmos.MATE_SCORE : -Cosmos.MATE_SCORE;
        }
        for (int i=firstLegalMove;i<moves.length();i+=5) 
		{
            int score=0;
            Cosmos.moveCounter++;
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
    			score = alphaBeta(alpha,beta,twPawn,twRook,twKnight, twBishop, twKing, twQueen, tbPawn,tbRook,tbKnight, tbBishop, tbKing, tbQueen,twCastleQ,twCastleK,tbCastleQ,tbCastleK,!whiteToMove,depth+1);
    			if(whiteToMove)
    	           {
    	        	   if(score<beta)
    	        	   {
    	        		   beta = score;
    	        		   if(depth == 0 )
    	            	   {
    	            		   Cosmos.bestMoveIndex = i;
    	            	   }
    	        	   }
    	        	}
    	           else
    	    	   {
    	    		   if(score > alpha)
    	    		   {
    	    			   alpha = score;
    	    			   if(depth == 0)
    	            	   {
    	            		   Cosmos.bestMoveIndex = i;
    	            	   }
    	    		   }
    	    	   }
    	           if (alpha>=beta) 
    				{
    	               if (whiteToMove) 
    					{	
    						return beta;
    					} 
    					else 
    					{
    						return alpha;
    					}
    	           }
            }
    		
        }
        if (whiteToMove) 
		{	
			return beta;
		} 
		else 
		{
			return alpha;
		}
	}
	public static int pvSearch(int alpha,int beta,long wPawn,long wRook,long wKnight,long wBishop,long wKing,long wQueen,long bPawn,long bRook,long bKnight,long bBishop,long bKing,long bQueen,boolean wCastleQ,boolean wCastleK,boolean bCastleQ,boolean bCastleK,boolean whiteToMove,int depth)
	{//using fail soft with negamax
        int bestScore;
        
        if (depth == Cosmos.searchDepth)
        {
            
        	
        	bestScore = Evaluation.evaluate(wPawn, wKnight, wBishop, wRook, wQueen, wKing, bPawn, bKnight, bBishop, bRook, bQueen, bKing, whiteToMove,wCastleQ,wCastleK,bCastleQ,bCastleK);
            if(Cosmos.debug)
            {
            	System.out.println("IN Final pv node" );
            	Move_Generation.drawArray(wPawn,wRook,wKnight, wBishop, wKing, wQueen, bPawn,bRook,bKnight, bBishop, bKing, bQueen);
            	System.out.println("Raw Score "+Evaluation.evaluate(wPawn, wKnight, wBishop, wRook, wQueen, wKing, bPawn, bKnight, bBishop, bRook, bQueen, bKing, whiteToMove,wCastleQ,wCastleK,bCastleQ,bCastleK));
            	System.out.println( "White is playing "+whiteToMove);
            	System.out.println();
            }
            
            if(whiteToMove)
            	return -bestScore;
            else
            	return -bestScore;
        }
        String moves;
        if (whiteToMove) 
		{
            moves=Move_Generation.whiteMove(wPawn,wRook,wKnight, wBishop, wKing, wQueen, bPawn,bRook,bKnight, bBishop, bKing, bQueen,wCastleQ,wCastleK,bCastleQ,bCastleK);
        }
		else 
		{
            moves=Move_Generation.blackMove(wPawn,wRook,wKnight, wBishop, wKing, wQueen, bPawn,bRook,bKnight, bBishop, bKing, bQueen,wCastleQ,wCastleK,bCastleQ,bCastleK);
        }
        //sortMoves();
        int firstLegalMove = getFirstLegalMove(moves,wPawn,wRook,wKnight, wBishop, wKing, wQueen, bPawn,bRook,bKnight, bBishop, bKing, bQueen,wCastleQ,wCastleK,bCastleQ,bCastleK,whiteToMove);
        if (firstLegalMove == -1)
        {
            return whiteToMove ? Cosmos.MATE_SCORE : -Cosmos.MATE_SCORE;
        }
        int start=(Character.getNumericValue(moves.charAt(firstLegalMove+1))*8)+(Character.getNumericValue(moves.charAt(firstLegalMove+2)));
		int end=(Character.getNumericValue(moves.charAt(firstLegalMove+3))*8)+(Character.getNumericValue(moves.charAt(firstLegalMove+4)));
		char moveType = moves.charAt(firstLegalMove);
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
    	
    	if(false)
    	{
    		System.out.println("Before CALLING PV at depth "+depth);
        	Move_Generation.drawArray(twPawn,twRook,twKnight, twBishop, twKing, twQueen,tbPawn,tbRook,tbKnight, tbBishop, tbKing, tbQueen);
        	System.out.println("score at this node "+Evaluation.evaluate(twPawn, twKnight, twBishop, twRook, twQueen, twKing, tbPawn, tbKnight, tbBishop, tbRook, tbQueen, tbKing, whiteToMove,wCastleQ,wCastleK,bCastleQ,bCastleK));
        	System.out.println();
    	}
    	
        bestScore = pvSearch(alpha,beta,twPawn,twRook,twKnight, twBishop, twKing, twQueen, tbPawn,tbRook,tbKnight, tbBishop, tbKing, tbQueen,twCastleQ,twCastleK,tbCastleQ,tbCastleK,!whiteToMove,depth+1);

        if(false)
        {
        	System.out.println("PV Move at depth "+depth+" move "+moves.substring(firstLegalMove,firstLegalMove+5)+" score "+bestScore);
        	System.out.println("pv alpha = "+alpha);
        	System.out.println("pv beta = "+beta);
        	System.out.println();
        	//System.out.println("Test SCore " + Evaluation.evaluate(twPawn, twKnight, twBishop, twRook, twQueen, twKing, tbPawn, tbKnight, tbBishop, tbRook, tbQueen, tbKing, whiteToMove));
        }
        Cosmos.moveCounter++;
        if (Math.abs(bestScore) == Cosmos.MATE_SCORE)
        {
            return bestScore;
        }
        if(whiteToMove)
        {
     	   if(bestScore<beta)
     	   {
     		   beta = bestScore;
     	   }
     	}
        else
 	   {
 		   if(bestScore > alpha)
 		   {
 			   alpha = bestScore;
 		   }
 	   }
        if (alpha>=beta) 
		{
           if (whiteToMove) 
			{	
				return beta;
			} 
			else 
			{
				return alpha;
			}
       }
        Cosmos.bestMoveIndex = firstLegalMove;
        for (int i=firstLegalMove+5;i<moves.length();i+=5) 
		{
            int score;
            Cosmos.moveCounter++;
            start=(Character.getNumericValue(moves.charAt(i+1))*8)+(Character.getNumericValue(moves.charAt(i+2)));
    		end=(Character.getNumericValue(moves.charAt(i+3))*8)+(Character.getNumericValue(moves.charAt(i+4)));
    		moveType = moves.charAt(i);
        	twPawn=Move_Generation.makeMove(wPawn,start,end,moveType, 'P');
        	twKnight=Move_Generation.makeMove(wKnight, start,end,moveType, 'N');
            twBishop=Move_Generation.makeMove(wBishop, start,end,moveType, 'B');
        	twRook=Move_Generation.makeMove(wRook,start,end,moveType, 'R');
            twQueen=Move_Generation.makeMove(wQueen, start,end,moveType, 'Q');
            twKing=Move_Generation.makeMove(wKing, start,end,moveType, 'K');
            tbPawn=Move_Generation.makeMove(bPawn, start,end,moveType, 'p');
            tbKnight=Move_Generation.makeMove(bKnight, start,end,moveType, 'n');
            tbBishop=Move_Generation.makeMove(bBishop, start,end,moveType, 'b');
            tbRook=Move_Generation.makeMove(bRook, start,end,moveType, 'r');
            tbQueen=Move_Generation.makeMove(bQueen, start,end,moveType, 'q');
            tbKing=Move_Generation.makeMove(bKing, start,end,moveType, 'k');
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
             score = zWSearch(beta,twPawn,twRook,twKnight,twBishop,twKing,twQueen, tbPawn,tbRook,tbKnight,tbBishop,tbKing,tbQueen,twCastleQ,twCastleK,tbCastleQ,tbCastleK,!whiteToMove,depth+1);
             if(whiteToMove)
             {
          	   if(score<beta)
          	   {
          		   beta =  pvSearch(alpha,beta,twPawn,twRook,twKnight, twBishop, twKing, twQueen, tbPawn,tbRook,tbKnight, tbBishop, tbKing, tbQueen,twCastleQ,twCastleK,tbCastleQ,tbCastleK,!whiteToMove,depth+1);
          		 Cosmos.bestMoveIndex = i;
          	   }
          	}
            else
      	   {
      		   if(bestScore > alpha)
      		   {
      			   alpha = pvSearch(alpha,beta,twPawn,twRook,twKnight, twBishop, twKing, twQueen, tbPawn,tbRook,tbKnight, tbBishop, tbKing, tbQueen,twCastleQ,twCastleK,tbCastleQ,tbCastleK,!whiteToMove,depth+1);
      			 Cosmos.bestMoveIndex = i;
      		   }
      	   }
             

            if ((score != Cosmos.NULL_INT) )
            {
            	if (alpha>=beta) 
        		{
                   if (whiteToMove) 
        			{	
        				return beta;
        			} 
        			else 
        			{
        				return alpha;
        			}
               }
                bestScore = score;
                if (Math.abs(bestScore) == Cosmos.MATE_SCORE)
                {
                    return bestScore;
                }
            }
        }
        if (whiteToMove) 
		{	
			return beta;
		} 
		else 
		{
			return alpha;
		}
 
    }
	public static int zWSearch(int beta,long wPawn,long wRook,long wKnight,long wBishop,long wKing,long wQueen,long bPawn,long bRook,long bKnight,long bBishop,long bKing,long bQueen,boolean wCastleQ,boolean wCastleK,boolean bCastleQ,boolean bCastleK,boolean whiteToMove,int depth) 
	{//fail-hard zero window search, returns either beta-1 or beta
        int score = Integer.MIN_VALUE;
     //   alpha == beta - 1;
        //this is either a cut- or all-node
        if (depth == Cosmos.searchDepth)
        {
        	score = Evaluation.evaluate(wPawn, wKnight, wBishop, wRook, wQueen, wKing, bPawn, bKnight, bBishop, bRook, bQueen, bKing, whiteToMove,wCastleQ,wCastleK,bCastleQ,bCastleK);
        	return -score;
        }
            String moves;
            if (whiteToMove)
    		{
                moves=Move_Generation.whiteMove(wPawn,wRook,wKnight, wBishop, wKing, wQueen, bPawn,bRook,bKnight, bBishop, bKing, bQueen,wCastleQ,wCastleK,bCastleQ,bCastleK);
            } 
    		else 
    		{
                moves=Move_Generation.blackMove(wPawn,wRook,wKnight, wBishop, wKing, wQueen, bPawn,bRook,bKnight, bBishop, bKing, bQueen,wCastleQ,wCastleK,bCastleQ,bCastleK);
            }
            //sortMoves();
            for (int i=0;i<moves.length();i+=5) 
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
            	//System.out.println("in zw score "+score);
            	if (((twKing&Move_Generation.AttackBlack(twPawn,twRook,twKnight, twBishop, twKing, twQueen,tbPawn,tbRook,tbKnight,tbBishop,tbKing,tbQueen))==0 && whiteToMove) ||(((tbKing&Move_Generation.AttackWhite(twPawn,twRook,twKnight, twBishop, twKing, twQueen,tbPawn,tbRook,tbKnight,tbBishop,tbKing,tbQueen))==0 && !whiteToMove))) 
                {
                    score = zWSearch(beta,twPawn,twRook,twKnight, twBishop, twKing, twQueen,tbPawn,tbRook,tbKnight,tbBishop,tbKing,tbQueen,twCastleQ,twCastleK,tbCastleQ,tbCastleK,!whiteToMove,depth+1);

                }
            	
                if (score >= beta)
                {
                    return score;//fail-hard beta-cutoff
                }
            }
        return beta - 1;//fail-hard, return alpha
    }
	
}
