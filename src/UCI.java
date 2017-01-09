import java.util.*;
public class UCI 
{
    static String ENGINENAME="Cosmos v1.2";
    public static void uciCommunication() 
	{
        Scanner input = new Scanner(System.in);
        while (true)
        {
            String inputString=input.nextLine();
          //  System.out.println(Cosmos.whiteToMove);
            if ("uci".equals(inputString))
            {
                inputUCI();
            }
            else if (inputString.startsWith("setoption"))
            {
                inputSetOption(inputString);
            }
            else if ("isready".equals(inputString))
            {
                inputIsReady();
            }
            else if ("ucinewgame".equals(inputString))
            {
                inputUCINewGame();
            }
            else if (inputString.startsWith("position"))
            {
                inputPosition(inputString);
                //inputPrint();
            }
            else if (inputString.startsWith("go"))
            {
                inputGo();
            }
            else if (inputString.equals("quit"))
            {
                inputQuit();
            }
            else if ("print".equals(inputString))
            {
                inputPrint();
            }
            else if (inputString.startsWith("perft"))
            {
            	Perft.perftTotalMoveCounter = 0;
            	Perft.captures = 0;
            	Perft.promotions = 0;
            	Perft.Castel = 0;
            	Perft.perftMaxDepth = inputString.charAt(6)-'0';
            	Perft.perftHead(Cosmos.wPawn,Cosmos.wRook,Cosmos.wKnight, Cosmos.wBishop, Cosmos.wKing, Cosmos.wQueen, Cosmos.bPawn,Cosmos.bRook,Cosmos.bKnight, Cosmos.bBishop, Cosmos.bKing, Cosmos.bQueen,Cosmos.wCastleQ,Cosmos.wCastleK,Cosmos.bCastleQ,Cosmos.bCastleK,true,0);
            	System.out.println("Total nodes: "+ Perft.perftTotalMoveCounter);
            	System.out.println("Total Captures: "+ Perft.captures);
            	System.out.println("Total Castle: "+ Perft.Castel);
            	System.out.println("Total Checks: "+ Perft.checks);
            	System.out.println("Total Promotions: "+ Perft.promotions);
            }
            else if ("score".equals(inputString))
            {
            	Evaluation.score = 0;
            	System.out.println("Final score: "+Evaluation.evaluate(Cosmos.wPawn,Cosmos.wKnight,Cosmos.wBishop,Cosmos.wRook,Cosmos.wQueen,Cosmos.wKing,Cosmos.bPawn,Cosmos.bKnight,Cosmos.bBishop,Cosmos.bRook,Cosmos.bQueen,Cosmos.bKing,!Cosmos.whiteToMove,Cosmos.wCastleQ,Cosmos.wCastleK,Cosmos.bCastleQ,Cosmos.bCastleK));
            }
            else if("who".equals(inputString))
            {
            	System.out.println(Cosmos.whiteToMove);
            }
            else if(inputString.startsWith("depth"))
            {
            	Cosmos.searchDepth = inputString.charAt(6)-'0';
            }
            else if("toggle".equals(inputString))
            {
            	Cosmos.debug = !Cosmos.debug;
            }
            else if("toggle1".equals(inputString))
            {
            	Cosmos.debug1 = !Cosmos.debug1;
            }
            else if("count".equals(inputString))
            {
            	System.out.println(Cosmos.moveCounter);
            }
            else if("display".equals(inputString))
            {
            	Cosmos.g.disp();
            }
           // System.out.println(inputString);
        }
    }
    public static void inputUCI() 
	{
        System.out.println("id name "+ENGINENAME);
        System.out.println("id author Rajdeep And Varsha and Anushka");
        //options go here
        System.out.println("uciok");
    }
    public static void inputSetOption(String inputString) 
	{
        //set options
    }
    public static void inputIsReady() 
	{
         System.out.println("readyok");
    }
    public static void inputUCINewGame() 
	{
    		//to do 
    }
    public static void inputPosition(String input) 
	{
        input=input.substring(9).concat(" ");
        if (input.contains("startpos "))
			{
				input=input.substring(9);
				Move_Generation.importFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
			}
        else if (input.contains("fen")) 
		{
            input=input.substring(4);
            Move_Generation.importFEN(input);
        }
        if (input.contains("moves")) 
		{
            input=input.substring(input.indexOf("moves")+6);

            Gui.lastLabel.setText(input);
            while (input.length()>0)
            {
                String moves;
                if (Cosmos.whiteToMove)
        		{
                    moves=Move_Generation.whiteMove(Cosmos.wPawn,Cosmos.wRook,Cosmos.wKnight, Cosmos.wBishop, Cosmos.wKing, Cosmos.wQueen, Cosmos.bPawn,Cosmos.bRook,Cosmos.bKnight, Cosmos.bBishop, Cosmos.bKing, Cosmos.bQueen,Cosmos.wCastleQ,Cosmos.wCastleK,Cosmos.bCastleQ,Cosmos.bCastleK);
                } 
        		else 
        		{
                    moves=Move_Generation.blackMove(Cosmos.wPawn,Cosmos.wRook,Cosmos.wKnight, Cosmos.wBishop, Cosmos.wKing, Cosmos.wQueen, Cosmos.bPawn,Cosmos.bRook,Cosmos.bKnight, Cosmos.bBishop, Cosmos.bKing, Cosmos.bQueen,Cosmos.wCastleQ,Cosmos.wCastleK,Cosmos.bCastleQ,Cosmos.bCastleK);
                }
                algebraToMove(input,moves);
               
                input=input.substring(input.indexOf(' ')+1);
            }
        }
    }
    public static void inputGo() 
	{
        String moves;
        if (Cosmos.whiteToMove)
        {
            moves=Move_Generation.whiteMove(Cosmos.wPawn,Cosmos.wRook,Cosmos.wKnight, Cosmos.wBishop, Cosmos.wKing, Cosmos.wQueen, Cosmos.bPawn,Cosmos.bRook,Cosmos.bKnight, Cosmos.bBishop, Cosmos.bKing, Cosmos.bQueen,Cosmos.wCastleQ,Cosmos.wCastleK,Cosmos.bCastleQ,Cosmos.bCastleK);
        }
        else
        {
            moves=Move_Generation.blackMove(Cosmos.wPawn,Cosmos.wRook,Cosmos.wKnight, Cosmos.wBishop, Cosmos.wKing, Cosmos.wQueen, Cosmos.bPawn,Cosmos.bRook,Cosmos.bKnight, Cosmos.bBishop, Cosmos.bKing, Cosmos.bQueen,Cosmos.wCastleQ,Cosmos.wCastleK,Cosmos.bCastleQ,Cosmos.bCastleK);
        }
    	int score = Principle_variation.alphaBeta(-10000, 10000, Cosmos.wPawn,Cosmos.wRook,Cosmos.wKnight, Cosmos.wBishop, Cosmos.wKing, Cosmos.wQueen, Cosmos.bPawn,Cosmos.bRook,Cosmos.bKnight, Cosmos.bBishop, Cosmos.bKing, Cosmos.bQueen,Cosmos.wCastleQ,Cosmos.wCastleK,Cosmos.bCastleQ,Cosmos.bCastleK, Cosmos.whiteToMove, 0);
    	
        if(Cosmos.debug)
        	System.out.println("after pv "+score);
        int index= Cosmos.bestMoveIndex;
        String move = moveToAlgebra(moves.substring(index,index+5));
        if(Cosmos.debug)
        {
	        System.out.println("moves "+moves);
	        System.out.println("index "+index);
	        System.out.println("raw move "+moves.substring(index,index+5));
	        System.out.println("move "+ move);
        }
        //System.out.println("bestmove "+);
        inputPosition("position moves "+moveToAlgebra(moves.substring(index,index+5)));
        Gui.lastLabel.setText(moveToAlgebra(moves.substring(index,index+5)));
    }
    public static String moveToAlgebra(String move) 
	{
        String append="";
        //int start=0,end=0;
        int start=(Character.getNumericValue(move.charAt(1))*8)+(Character.getNumericValue(move.charAt(2)));
		int end=(Character.getNumericValue(move.charAt(3))*8)+(Character.getNumericValue(move.charAt(4)));
		char moveType = move.charAt(0);
		if(moveType != ' ' && moveType != 'C' && moveType != 'c')
		{
			append = append + moveType;
		}
        String returnMove="";
        returnMove+=(char)('a'+(start%8));
        returnMove+=(char)('8'-(start/8));
        returnMove+=(char)('a'+(end%8));
        returnMove+=(char)('8'-(end/8));
        returnMove+=append;
        if(Cosmos.debug)
        {
	        System.out.println("movetoalgebra");
	        System.out.println("move "+move);
	        System.out.println("return string "+returnMove);
        }
        return returnMove;
    }
    public static void algebraToMove(String input,String moves) 
	{
        int start=0,end=0;
        char moveType=' ';
        int from=(input.charAt(0)-'a')+(8*('8'-input.charAt(1)));
        int to=(input.charAt(2)-'a')+(8*('8'-input.charAt(3)));
        for (int i=0;i<moves.length();i+=5) 
		{
        	start=(Character.getNumericValue(moves.charAt(i+1))*8)+(Character.getNumericValue(moves.charAt(i+2)));
    		end=(Character.getNumericValue(moves.charAt(i+3))*8)+(Character.getNumericValue(moves.charAt(i+4)));
    		moveType = moves.charAt(i);
            if ((start==from) && (end==to)) 
			{
                System.out.println("algebra to move");
            	System.out.println("Engines "+moves.substring(i, i+5));
                System.out.println("GUI"+input);
            	if ((input.charAt(4)==' ') || (Character.toUpperCase(input.charAt(4))==Character.toUpperCase(moveType))) 
				{
                	if(moveType==' ')
                	{
                		//int start=(Character.getNumericValue(moves.charAt(i+1))*8)+(Character.getNumericValue(moves.charAt(i+2)));
                		if (((1L<<start)&Cosmos.wKing)!=0) 
                		{
                			Cosmos.wCastleQ=false;
                			Cosmos.wCastleK=false;
                		}
                        else if (((1L<<start)&Cosmos.bKing)!=0) 
                        {
                        	Cosmos.bCastleQ=false; 
                        	Cosmos.bCastleK=false;
                        }
                        else if (((1L<<start)&Cosmos.wRook&(1L<<63))!=0) 
                        {
                        	Cosmos.wCastleK=false;
                        }
                        else if (((1L<<start)&Cosmos.wRook&(1L<<56))!=0) 
                        {
                        	Cosmos.wCastleQ=false;
                        }
                        else if (((1L<<start)&Cosmos.bRook&(1L<<7))!=0) 
                        {
                        	Cosmos.bCastleK=false;
                        }
                        else if (((1L<<start)&Cosmos.bRook&1L)!=0) 
                        {
                        	Cosmos.bCastleQ=false;
                        }
                	}
                	Cosmos.wPawn=Move_Generation.makeMove(Cosmos.wPawn,start,end,moveType, 'P');
                	Cosmos.wKnight=Move_Generation.makeMove(Cosmos.wKnight, start,end,moveType, 'N');
                    Cosmos.wBishop=Move_Generation.makeMove(Cosmos.wBishop, start,end,moveType, 'B');
                    Cosmos.wRook=Move_Generation.makeMove(Cosmos.wRook,start,end,moveType, 'R');
                    Cosmos.wQueen=Move_Generation.makeMove(Cosmos.wQueen, start,end,moveType, 'Q');
                    Cosmos.wKing=Move_Generation.makeMove(Cosmos.wKing, start,end,moveType, 'K');
                    Cosmos.bPawn=Move_Generation.makeMove(Cosmos.bPawn, start,end,moveType, 'p');
                    Cosmos.bKnight=Move_Generation.makeMove(Cosmos.bKnight, start,end,moveType, 'n');
                    Cosmos.bBishop=Move_Generation.makeMove(Cosmos.bBishop, start,end,moveType, 'b');
                    Cosmos.bRook=Move_Generation.makeMove(Cosmos.bRook, start,end,moveType, 'r');
                    Cosmos.bQueen=Move_Generation.makeMove(Cosmos.bQueen, start,end,moveType, 'q');
                    Cosmos.bKing=Move_Generation.makeMove(Cosmos.bKing, start,end,moveType, 'k');
                	Cosmos.whiteToMove=!Cosmos.whiteToMove;
                    break;
                }
            }
        }
    }
    public static void inputQuit()
	{
        System.exit(0);
    }
    public static void inputPrint() 
	{
       Move_Generation.drawArray(Cosmos.wPawn,Cosmos.wRook,Cosmos.wKnight, Cosmos.wBishop, Cosmos.wKing, Cosmos.wQueen, Cosmos.bPawn,Cosmos.bRook,Cosmos.bKnight, Cosmos.bBishop, Cosmos.bKing, Cosmos.bQueen);
        
	}
    
    /*
    public static void main(String[] args) 
    {
		uciCommunication();
		
	}
	*/
}