import java.util.Arrays;


public class Move_Generation
{

		static char[][] board = {  {'r','k','b','q','a','b','k','r'},
								   {'p','p','p','p',' ','p','p','p'},
								   {' ',' ',' ',' ',' ',' ',' ',' '},
								   {' ',' ',' ',' ','P',' ',' ',' '},
								   {' ',' ',' ',' ',' ',' ',' ',' '},
								   {' ',' ',' ',' ',' ',' ',' ','P'},
								   {'P','P','P',' ','P','P','P',' '},
								   {'R','K','B','Q','A','B','K','R'},
								};
		/*{  					   {'r','k','b','q','a','b','k','r'},
								   {'p','p','p','p','p','p','p','p'},
								   {' ',' ',' ',' ',' ',' ',' ',' '},
								   {' ',' ',' ',' ',' ',' ',' ',' '},
								   {' ',' ',' ',' ',' ',' ',' ',' '},
								   {' ',' ',' ',' ',' ',' ',' ',' '},
								   {'P','P','P','P','P','P','P','P'},
								   {'R','K','B','Q','A','B','K','R'},
								};
			*/
		//static long wPawn,wRook,wKnight, wBishop, wKing, wQueen, bPawn,bRook,bKnight, bBishop, bKing, bQueen;   //Piece bitboards 
		static long empty,occupied,notMyPiece;     // Other bitboards 
		//static boolean wCastleQ=true,wCastleK=true,bCastleQ=true,bCastleK=true;        //=true
		static long RankMasks8[] =    // pass value just by (bit/8) bit from the starting 
	    {
	        0xFFL, 0xFF00L, 0xFF0000L, 0xFF000000L, 0xFF00000000L, 0xFF0000000000L, 0xFF000000000000L, 0xFF00000000000000L
	    };
	    static long FileMasks8[] =   // pass value just by the (bit%8) from the starting
	    {
	        0x101010101010101L, 0x202020202020202L, 0x404040404040404L, 0x808080808080808L,
	        0x1010101010101010L, 0x2020202020202020L, 0x4040404040404040L, 0x8080808080808080L
	    };
	    static long DiagonalMasks8[] =  //  pass value by (s/8)+(s%8) to get the specific diagonal mask 
	    {
		0x1L, 0x102L, 0x10204L, 0x1020408L, 0x102040810L, 0x10204081020L, 0x1020408102040L,
		0x102040810204080L, 0x204081020408000L, 0x408102040800000L, 0x810204080000000L,
		0x1020408000000000L, 0x2040800000000000L, 0x4080000000000000L, 0x8000000000000000L
	    };
	    static long AntiDiagonalMasks8[] =     // pass value by (s/8)+7-(s%8) to get the specific anti-diagonal mask 
	    {
		0x80L, 0x8040L, 0x804020L, 0x80402010L, 0x8040201008L, 0x804020100804L, 0x80402010080402L,
		0x8040201008040201L, 0x4020100804020100L, 0x2010080402010000L, 0x1008040201000000L,
		0x804020100000000L, 0x402010000000000L, 0x201000000000000L, 0x100000000000000L
	    };
	    public static void drawArray(long WP,long WR,long WN,long WB,long WK,long WQ,long BP,long BR,long BN,long BB,long BK,long BQ) 
		{
	        String chessBoard[][]=new String[8][8];
	        for (int i=0;i<64;i++) 
			{
	            chessBoard[i/8][i%8]=" ";
	        }
	        for (int i=0;i<64;i++) 
			{
	            if (((WP>>i)&1)==1) {chessBoard[i/8][i%8]="P";}
	            if (((WN>>i)&1)==1) {chessBoard[i/8][i%8]="N";}
	            if (((WB>>i)&1)==1) {chessBoard[i/8][i%8]="B";}
	            if (((WR>>i)&1)==1) {chessBoard[i/8][i%8]="R";}
	            if (((WQ>>i)&1)==1) {chessBoard[i/8][i%8]="Q";}
	            if (((WK>>i)&1)==1) {chessBoard[i/8][i%8]="K";}
	            if (((BP>>i)&1)==1) {chessBoard[i/8][i%8]="p";}
	            if (((BN>>i)&1)==1) {chessBoard[i/8][i%8]="n";}
	            if (((BB>>i)&1)==1) {chessBoard[i/8][i%8]="b";}
	            if (((BR>>i)&1)==1) {chessBoard[i/8][i%8]="r";}
	            if (((BQ>>i)&1)==1) {chessBoard[i/8][i%8]="q";}
	            if (((BK>>i)&1)==1) {chessBoard[i/8][i%8]="k";}
	        }
	        for (int i=0;i<8;i++) 
			{
	            System.out.println(((8-i))+Arrays.toString(chessBoard[i]));
	        }
	        char[] a = {'a','b','c','d','e','f','g','h'};
	        System.out.println(" "+ Arrays.toString(a));
	    }
	    public static void importFEN(String fenString) 
		{
	        //not chess960 compatible
			Cosmos.wPawn=0; Cosmos.wKnight=0; Cosmos.wBishop=0;
		        Cosmos.wRook=0; Cosmos.wQueen=0; Cosmos.wKnight=0;
		        Cosmos.bPawn=0; Cosmos.bKing=0; Cosmos.bBishop=0;
		        Cosmos.bRook=0; Cosmos.bQueen=0; Cosmos.bKing=0;
		        Cosmos.wCastleK=false; Cosmos.wCastleQ=false;
		        Cosmos.bCastleK=false; Cosmos.bCastleQ=false;
			int charIndex = 0;
			int boardIndex = 0;
			while (fenString.charAt(charIndex) != ' ')
			{
				switch (fenString.charAt(charIndex++))
				{
				case 'P': Cosmos.wPawn |= (1L << boardIndex++);
					break;
				case 'p': Cosmos.bPawn |= (1L << boardIndex++);
					break;
				case 'N': Cosmos.wKnight |= (1L << boardIndex++);
					break;
				case 'n': Cosmos.bKnight |= (1L << boardIndex++);
					break;
				case 'B': Cosmos.wBishop |= (1L << boardIndex++);
					break;
				case 'b': Cosmos.bBishop |= (1L << boardIndex++);
					break;
				case 'R': Cosmos.wRook |= (1L << boardIndex++);
					break;
				case 'r': Cosmos.bRook |= (1L << boardIndex++);
					break;
				case 'Q': Cosmos.wQueen |= (1L << boardIndex++);
					break;
				case 'q': Cosmos.bQueen |= (1L << boardIndex++);
					break;
				case 'K': Cosmos.wKing |= (1L << boardIndex++);
					break;
				case 'k': Cosmos.bKing |= (1L << boardIndex++);
					break;
				case '/':
					break;
				case '1': boardIndex++;
					break;
				case '2': boardIndex += 2;
					break;
				case '3': boardIndex += 3;
					break;
				case '4': boardIndex += 4;
					break;
				case '5': boardIndex += 5;
					break;
				case '6': boardIndex += 6;
					break;
				case '7': boardIndex += 7;
					break;
				case '8': boardIndex += 8;
					break;
				default:
					break;
				}
			}
			Cosmos.whiteToMove = (fenString.charAt(++charIndex) == 'w');
			charIndex += 2;
			while (fenString.charAt(charIndex) != ' ')
			{
				switch (fenString.charAt(charIndex++))
				{
				case '-':
					break;
				case 'K': Cosmos.wCastleK = true;
					break;
				case 'Q': Cosmos.wCastleQ = true;
					break;
				case 'k': Cosmos.bCastleK = true;
					break;
				case 'q': Cosmos.bCastleQ = true;
					break;
				default:
					break;
				}
			}
			/*
			if (fenString.charAt(++charIndex) != '-')
			{
				Cosmos.EP = Moves.FileMasks8[fenString.charAt(charIndex++) - 'a'];
			}
			*/
		//the rest of the fenString is not yet utilized
	    }
		public static void makebitBoards()
		{
			String Binary;
			for (int i = 0; i < 64; i++) 
			{

		        Binary="0000000000000000000000000000000000000000000000000000000000000000";
		        Binary=Binary.substring(i+1)+"1"+Binary.substring(0, i);
				switch(board[i/8][i%8])
				{
						case 'P' : Cosmos.wPawn = Cosmos.wPawn | stringToBitboard(Binary) ;
									break;
						case 'B' : Cosmos.wBishop = Cosmos.wBishop | stringToBitboard(Binary) ;
									break;
						case 'R' : Cosmos.wRook = stringToBitboard(Binary)|Cosmos.wRook;
									break;
						case 'K' : Cosmos.wKnight = stringToBitboard(Binary)|Cosmos.wKnight;
									break;
						case 'Q' : Cosmos.wQueen = stringToBitboard(Binary)|Cosmos.wQueen;
									break;
						case 'A' : Cosmos.wKing = stringToBitboard(Binary)|Cosmos.wKing;
									break;
						case 'p' : Cosmos.bPawn = stringToBitboard(Binary)|Cosmos.bPawn;
									break;
						case 'b' : Cosmos.bBishop = stringToBitboard(Binary)|Cosmos.bBishop;
									break;
						case 'r' : Cosmos.bRook = stringToBitboard(Binary)|Cosmos.bRook;
									break;
						case 'k' : Cosmos.bKnight = stringToBitboard(Binary)|Cosmos.bKnight;
									break;
						case 'q' : Cosmos.bQueen = stringToBitboard(Binary)|Cosmos.bQueen;
									break;
						case 'a' : Cosmos.bKing = stringToBitboard(Binary)|Cosmos.bKing;
									break;
				}
		    }
		}
		public static long stringToBitboard(String Binary) 
		{
	        if (Binary.charAt(0)=='0') 
	        {
	            return Long.parseLong(Binary, 2);
	        } 
	        else 
	        {
	            return Long.parseLong("1"+Binary.substring(2), 2)*2;
	        }
	    }
		public static void dispBitBoard(long bitboard)
		{
			System.out.println("________________________________________");
			int k  =0;
			for(int i=0;i<64;i++)
			{
				if((bitboard>>i&1)==1)
				{
					System.out.print("|_*_|");
				}
				else
				{
					System.out.print("|___|");
				}
				if((i+1)%8 == 0)
				{
					System.out.println(k++);
				}
					
			}
			System.out.println("  0    1    2    3    4    5    6    7    ");
		}
		public static String whiteMove(long wPawn,long wRook,long wKnight,long wBishop,long wKing,long wQueen,long bPawn,long bRook,long bKnight,long bBishop,long bKing,long bQueen,boolean wCastleQ,boolean wCastleK,boolean bCastleQ,boolean bCastleK  )
		{
			String list = "";
			notMyPiece =  ~ (wPawn|wRook|wKnight|wBishop|wKing|wQueen|bKing);
			occupied = bPawn|bRook|bKnight|bBishop|bKing|bQueen|wPawn|wRook|wKnight|wBishop|wKing|wQueen|bKing;
			empty = ~occupied;
			list += wPawnMove(wPawn)+RookMove(wRook)+BishopMove(wBishop)+queenMove(wQueen)+ KnightMove(wKnight)+KingMove(wKing)+WCastle(wPawn,wRook,wKnight, wBishop, wKing, wQueen, bPawn,bRook,bKnight, bBishop, bKing, bQueen,wCastleQ,wCastleK);
			//System.out.println(list.length()/5);
			//System.out.println(list);
			return list;
			
		}
		public static String blackMove(long wPawn,long wRook,long wKnight,long wBishop,long wKing,long wQueen,long bPawn,long bRook,long bKnight,long bBishop,long bKing,long bQueen,boolean wCastleQ,boolean wCastleK,boolean bCastleQ,boolean bCastleK)
		{
			String list = "";
			notMyPiece = ~ (bPawn|bRook|bKnight|bBishop|bKing|bQueen|wKing);
			occupied = bPawn|bRook|bKnight|bBishop|bKing|bQueen|wPawn|wRook|wKnight|wBishop|wKing|wQueen|bKing;
			empty = ~occupied;
			list += bPawnMove(bPawn)+RookMove(bRook)+BishopMove(bBishop)+queenMove(bQueen)+KnightMove(bKnight)+KingMove(bKing)+BCastle(wPawn,wRook,wKnight, wBishop, wKing, wQueen, bPawn,bRook,bKnight, bBishop, bKing, bQueen,wCastleQ,wCastleK);
			//System.out.println(list.length()/5);
			//System.out.println(list);
			return list;
			
		}
		public static String RookMove(long rook)
		{
			long a = ~(rook-1) & rook ;
			long straight;
			String list ="";
			while(a!=0)
			{
				int i = Long.numberOfTrailingZeros(a);
				straight = straight(i) & notMyPiece;
				//dispBitBoard(straight);
				long temp = ~(straight-1) & straight;
				while(temp!=0)
				{
					int to = Long.numberOfTrailingZeros(temp);
					list += " "+(i/8)+(i%8)+(to/8)+(to%8);
					straight = straight & ~temp;
					temp = ~(straight-1) & straight;
				}
				rook = rook & ~a;
				a = ~(rook-1) & rook ;
			}
			return list;
		}
		public static String BishopMove( long bishop)
		{
			long a = ~(bishop-1)&bishop;
			long diagonal;
			String list = "";
			while(a!=0)
			{
				int i = Long.numberOfTrailingZeros(a);
				diagonal = diagonals(i) & notMyPiece;
				//dispBitBoard(diagonal);
				long temp = ~(diagonal-1) & diagonal;
				while(temp!=0)
				{
					int to = Long.numberOfTrailingZeros(temp);
					list += " "+(i/8)+(i%8)+(to/8)+(to%8);
					diagonal = diagonal & ~temp;
					temp = ~(diagonal-1) & diagonal;
				}
				bishop = bishop &~a;
				a = ~(bishop-1)&bishop;
			}
			return list;
		}
		public static String queenMove(long queen)
		{
			String list = "";
			long a = ~(queen-1)&queen;
			long diagonal;
			long straight;
			while(a!=0)
			{
				int i = Long.numberOfTrailingZeros(a);
				diagonal = diagonals(i) & notMyPiece;
				straight = straight(i) & notMyPiece;
				diagonal = (diagonal|straight);
				//dispBitBoard(diagonal);
				long temp = ~(diagonal-1) & diagonal;
				while(temp!=0)
				{
					int to = Long.numberOfTrailingZeros(temp);
					list += " "+(i/8)+(i%8)+(to/8)+(to%8);
					diagonal = diagonal & ~temp;
					temp = ~(diagonal-1) & diagonal;
				}
				queen = queen &~a;
				a = ~(queen-1)&queen;
			}
			return list;
		}
		public static long straight(int s)
		{
			long binaryS=1L<<s;
	        long Horizontal = (occupied-2*binaryS)^Long.reverse(Long.reverse(occupied)-2*Long.reverse(binaryS));
	        long Vertical = ((occupied&FileMasks8[s%8])-(2*binaryS))^Long.reverse(Long.reverse(occupied&FileMasks8[s%8])-(2*Long.reverse(binaryS)));
	        return (Horizontal&RankMasks8[s / 8]) | (Vertical&FileMasks8[s % 8]);
		}
		public static long diagonals(int s)
		{
			long binaryS = 1L<<s;
			long diagonal = ((occupied&DiagonalMasks8[(s/8)+(s%8)])-(2*binaryS))^Long.reverse(Long.reverse(occupied&DiagonalMasks8[(s/8)+(s%8)])-(2*Long.reverse(binaryS)));
			long anti = ((occupied&AntiDiagonalMasks8[(s/8)+7-(s%8)])-(2*binaryS))^Long.reverse(Long.reverse(occupied&AntiDiagonalMasks8[(s/8)+7-(s%8)])-(2*Long.reverse(binaryS)));
			return (diagonal&DiagonalMasks8[(s / 8) + (s % 8)]) | (anti&AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)]);
			
		}
		public static String wPawnMove(long wPawn)
		{
			long a=~(wPawn-1)&wPawn;
			String list="";
			long moves=0L;
			long temp=0L;
			int i=0,to=0;
			while(a!=0)
			{
				i=Long.numberOfTrailingZeros(a);
				moves=((a>>8)&empty);//one step forward
				moves=moves|(((((a&RankMasks8[6])>>8)&empty)>>8)&empty);//two steps forward
				moves=moves|((a>>9)&(~FileMasks8[7])&notMyPiece&occupied);//LeftCross
				moves=moves|((a>>7)&(~FileMasks8[0])&notMyPiece&occupied);//RightCross
				//dispBitBoard(moves);
				temp = ~(moves-1) & moves;
				while(temp!=0)
				{
					to = Long.numberOfTrailingZeros(temp);
					if(to/8==0)
					{
						list += "Q"+(i/8)+(i%8)+(to/8)+(to%8);
						list += "K"+(i/8)+(i%8)+(to/8)+(to%8);
						list += "B"+(i/8)+(i%8)+(to/8)+(to%8);
						list += "R"+(i/8)+(i%8)+(to/8)+(to%8);					
					}
					else
					list += " "+(i/8)+(i%8)+(to/8)+(to%8);
					moves = moves & ~temp;
					temp = ~(moves-1) & moves;
				}
				wPawn =wPawn &~a;
				a = ~(wPawn-1)&wPawn;	
			}
			/*
			temp = (wPawn << 1)&bPawn&RankMasks8[3]&~FileMasks8[0]&empasant;
			if (temp != 0)
	        {
	            int index=Long.numberOfTrailingZeros(temp);
	            list+="E"+(index%8+1)+(index%8)+"";
	        }
	        */
			return list;			
		}
		public static String bPawnMove(long bPawn)
		{
			long a=~(bPawn-1)&bPawn;
			String list="";
			long moves=0L;
			long temp=0L;
			int i=0,to=0;
			while(a!=0)
			{
				i=Long.numberOfTrailingZeros(a);
				moves=((a<<8)&empty);                                      //one step forward
				moves=moves|(((((a&RankMasks8[1])<<8)&empty)<<8)&empty);   //two steps forward
				moves=moves|((a<<9)&(~FileMasks8[0])&notMyPiece&occupied); //LeftCross
				moves=moves|((a<<7)&(~FileMasks8[7])&notMyPiece&occupied); //RightCross
				temp = ~(moves-1) & moves;
				while(temp!=0)
				{
					to = Long.numberOfTrailingZeros(temp);
					if(to/8==7)
					{	
						list += "q"+(i/8)+(i%8)+(to/8)+(to%8);
						list += "k"+(i/8)+(i%8)+(to/8)+(to%8);
						list += "b"+(i/8)+(i%8)+(to/8)+(to%8);
						list += "r"+(i/8)+(i%8)+(to/8)+(to%8);		
					}
					else
						list += " "+(i/8)+(i%8)+(to/8)+(to%8);
					moves = moves & ~temp;
					temp = ~(moves-1) & moves;
				}
				bPawn =bPawn &~a;
				a = ~(bPawn-1)&bPawn;						
			}
			return list;			
		}
		public static String KnightMove(long knight)
		{
			long a=~(knight-1)&knight;
			long fwdLeft=0L;
			long fwdRight=0L;
			long backLeft=0L; 
			long backRight=0L;
			long moves=0L;
			long temp=0L;
			String list="";
			int i,to;
			while(a!=0)
			{
				i=Long.numberOfTrailingZeros(a);
				
				fwdLeft=(((a&~RankMasks8[1])&(a&~RankMasks8[0])&(a&~FileMasks8[0]))>>17)&notMyPiece;//2+1
				fwdLeft=fwdLeft|((((a&~RankMasks8[0])&(a&~FileMasks8[0])&(a&~FileMasks8[1]))>>10)&notMyPiece);//1+2
				
				fwdRight=(((a&~RankMasks8[1])&(a&~RankMasks8[0])&(a&~FileMasks8[7]))>>15)&notMyPiece;//2+1
				fwdRight=fwdRight|((((a&~RankMasks8[0])&(a&~FileMasks8[7])&(a&~FileMasks8[6]))>>6)&notMyPiece);//1+2
				
				backLeft=(((a&~RankMasks8[6])&(a&~RankMasks8[7])&(a&~FileMasks8[0]))<<15)&notMyPiece;//2+1
				backLeft=backLeft|((((a&~RankMasks8[7])&(a&~FileMasks8[0])&(a&~FileMasks8[1]))<<6)&notMyPiece);//1+2
				
				backRight=(((a&~RankMasks8[6])&(a&~RankMasks8[7])&(a&~FileMasks8[7]))<<17)&notMyPiece;//2+1
				backRight=backRight|((((a&~RankMasks8[7])&(a&~FileMasks8[6])&(a&~FileMasks8[7]))<<10)&notMyPiece);//1+2
				
				moves=fwdLeft|fwdRight|backLeft|backRight;
				temp = ~(moves-1) & moves;
				//dispBitBoard(moves);
				while(temp!=0)
				{
					to = Long.numberOfTrailingZeros(temp);
					list += " "+(i/8)+(i%8)+(to/8)+(to%8);
					moves = moves & ~temp;
					temp = ~(moves-1) & moves;
				}				
				knight =knight &~a;
				a = ~(knight-1)&knight;	
			}
			return list;
			
		}
		public static String KingMove(Long king)
		{
			String list="";
			long a = 0;
			a |= (king&~FileMasks8[0])>>1&notMyPiece;    //left movement
			a |= (king&~FileMasks8[7])<<1&notMyPiece;    // right movement
			a |= (king&~RankMasks8[0])>>8&notMyPiece;    //upwards movement
			a |= (king&~RankMasks8[7])<<8&notMyPiece;      //downwards movement
			a |= (king&~RankMasks8[0]&~FileMasks8[0])>>9&notMyPiece;   // up-left movement
			a |= (king&~RankMasks8[7]&~FileMasks8[7])<<9&notMyPiece;     // down-right movement
			a |= (king&~RankMasks8[0]&~FileMasks8[7])>>7&notMyPiece;       // up-right movement
			a |= (king&~RankMasks8[7]&~FileMasks8[0])<<7&notMyPiece;     //right-up movement
			//dispBitBoard(a);
			int i = Long.numberOfTrailingZeros(king);
			long temp = ~(a-1) & a;
			while(temp!=0)
			{
				int to = Long.numberOfTrailingZeros(temp);
				list += " "+(i/8)+(i%8)+(to/8)+(to%8);
				a = a & ~temp;
				temp = ~(a-1) & a;
			}
			return list;
		}
		public static long attackWhitePawn(long pawn)
		{
			long attack = (pawn>>>7)&~FileMasks8[0];
			attack |= (pawn>>>9)&~FileMasks8[7];
			return attack;
		}
		public static long attackBlackPawn(long pawn)
		{
			long attack = (pawn<<7)&~FileMasks8[7];
			attack |= (pawn<<9)&~FileMasks8[0];
			return attack;
		}
		public static long attackQueen(long queen1)
		{
			long queen = queen1;
			long a = ~(queen-1)&queen;
			long diagonal,attack = 0;
			while(a!=0)
			{
				int i = Long.numberOfTrailingZeros(a);
				diagonal = diagonals(i);
				attack |= diagonal;
				queen = queen &~a;
				a = ~(queen-1)&queen;
			}
			queen = queen1;
			a = ~(queen-1) & queen;
			long straight;
			while(a!=0)
			{
				int i = Long.numberOfTrailingZeros(a);
				straight = straight(i) ;
				attack |= straight;
				queen = queen & ~a;
				a = ~(queen-1) & queen ;
			}
			return attack;
		}
		public static long attackRook(long rook)
		{
			long attack = 0;
			long RookQueen = rook;
			long a = ~(RookQueen-1) & RookQueen;
			long straight;
			while(a!=0)
			{
				int i = Long.numberOfTrailingZeros(a);
				straight = straight(i) ;
				attack |= straight;
				RookQueen = RookQueen & ~a;
				a = ~(RookQueen-1) & RookQueen ;
			}
			return attack;
		}
		public static long attackBishop(long bishop)
		{
			long QueenBishop = bishop;
			long a = ~(QueenBishop-1)&QueenBishop;
			long diagonal,attack = 0;
			while(a!=0)
			{
				int i = Long.numberOfTrailingZeros(a);
				diagonal = diagonals(i);
				attack |= diagonal;
				QueenBishop = QueenBishop &~a;
				a = ~(QueenBishop-1)&QueenBishop;
			}
			return attack;
		}
		public static long attackKnight(long knight)
		{
			long attack = 0;
			long a=~(knight-1)&knight;
			long fwdLeft=0L;
			long fwdRight=0L;
			long backLeft=0L; 
			long backRight=0L;
			long moves=0L;
			while(a!=0)
			{
				//int i=Long.numberOfTrailingZeros(a);
				fwdLeft=(((a&~RankMasks8[1])&(a&~RankMasks8[0])&(a&~FileMasks8[0]))>>17)&notMyPiece;//2+1
				fwdLeft=fwdLeft|((((a&~RankMasks8[0])&(a&~FileMasks8[0])&(a&~FileMasks8[1]))>>10)&notMyPiece);//1+2
				fwdRight=(((a&~RankMasks8[1])&(a&~RankMasks8[0])&(a&~FileMasks8[7]))>>15)&notMyPiece;//2+1
				fwdRight=fwdRight|((((a&~RankMasks8[0])&(a&~FileMasks8[7])&(a&~FileMasks8[6]))>>6)&notMyPiece);//1+2
				backLeft=(((a&~RankMasks8[6])&(a&~RankMasks8[7])&(a&~FileMasks8[0]))<<15)&notMyPiece;//2+1
				backLeft=backLeft|((((a&~RankMasks8[7])&(a&~FileMasks8[0])&(a&~FileMasks8[1]))<<6)&notMyPiece);//1+2
				backRight=(((a&~RankMasks8[6])&(a&~RankMasks8[7])&(a&~FileMasks8[7]))<<17)&notMyPiece;//2+1
				backRight=backRight|((((a&~RankMasks8[7])&(a&~FileMasks8[6])&(a&~FileMasks8[7]))<<10)&notMyPiece);//1+2
				moves=fwdLeft|fwdRight|backLeft|backRight;	
				attack |= moves;
				knight =knight &~a;
				a = ~(knight-1)&knight;	
			}
			return attack;
		}
		public static long attackKing(long king)
		{
			//King
			long a = 0;
			a |= (king&~FileMasks8[0])>>1;    //left movement
			a |= (king&~FileMasks8[7])<<1;   // right movement
			a |= (king&~RankMasks8[0])>>8;    //upwards movement
			a |= (king&~RankMasks8[7])<<8;      //downwards movement
			a |= (king&~RankMasks8[0]&~FileMasks8[0])>>9;  // up-left movement
			a |= (king&~RankMasks8[7]&~FileMasks8[7])<<9;     // down-right movement
			a |= (king&~RankMasks8[0]&~FileMasks8[7])>>7;       // up-right movement
			a |= (king&~RankMasks8[7]&~FileMasks8[0])<<7;     //right-up movement
			return a;
		}
		public  static void generateAttack(long wPawn,long wRook,long wKnight,long wBishop,long wKing,long wQueen,long bPawn,long bRook,long bKnight,long bBishop,long bKing,long bQueen,boolean type)
		{
			if(type)
			{
				notMyPiece =  ~ (wPawn|wRook|wKnight|wBishop|wKing|wQueen);
				occupied = bPawn|bRook|bKnight|bBishop|bKing|bQueen|wPawn|wRook|wKnight|wBishop|wKing|wQueen|bKing;
				empty = ~occupied;
			}
			else
			{
				notMyPiece =  ~ (bPawn|bRook|bKnight|bBishop|bKing|bQueen);
				occupied = bPawn|bRook|bKnight|bBishop|bKing|bQueen|wPawn|wRook|wKnight|wBishop|wKing|wQueen|bKing;
				empty = ~occupied;
			}
		}
		public  static long AttackWhite(long wPawn,long wRook,long wKnight,long wBishop,long wKing,long wQueen,long bPawn,long bRook,long bKnight,long bBishop,long bKing,long bQueen)
		{
			/*notMyPiece =  ~ (wPawn|wRook|wKnight|wBishop|wKing|wQueen);
			occupied = bPawn|bRook|bKnight|bBishop|bKing|bQueen|wPawn|wRook|wKnight|wBishop|wKing|wQueen|bKing;
			empty = ~occupied;*/
			generateAttack(wPawn,wRook,wKnight,wBishop,wKing,wQueen,bPawn,bRook,bKnight,bBishop,bKing,bQueen,true);
			long attack = 0L;
			attack = attackWhitePawn(wPawn)|attackQueen(wQueen)|attackRook(wRook)|attackKnight(wKnight)|attackBishop(wBishop)|attackKing(wKing);
			return attack;
		}
		public  static long AttackBlack(long wPawn,long wRook,long wKnight,long wBishop,long wKing,long wQueen,long bPawn,long bRook,long bKnight,long bBishop,long bKing,long bQueen)
		{
			
			/*notMyPiece =  ~ (bPawn|bRook|bKnight|bBishop|bKing|bQueen);
			occupied = bPawn|bRook|bKnight|bBishop|bKing|bQueen|wPawn|wRook|wKnight|wBishop|wKing|wQueen|bKing;
			empty = ~occupied;*/
			generateAttack(wPawn,wRook,wKnight,wBishop,wKing,wQueen,bPawn,bRook,bKnight,bBishop,bKing,bQueen,false);
			long attack = 0L;
			attack = attackBlackPawn(bPawn)|attackQueen(bQueen)|attackRook(bRook)|attackKnight(bKnight)|attackBishop(bBishop)|attackKing(bKing);
			return attack;
		}
		
		public static String WCastle(long wPawn,long wRook,long wKnight,long wBishop,long wKing,long wQueen,long bPawn,long bRook,long bKnight,long bBishop,long bKing,long bQueen,boolean wCastleQ,boolean wCastleK )
		{
			String list ="";
			
			long attack = AttackBlack(wPawn,wRook,wKnight, wBishop,wKing, wQueen, bPawn,bRook,bKnight, bBishop, bKing,bQueen);
			if((wKing & attack) == 0)
			{
				if(wCastleK&&(((1L<<63)&wRook)!=0))
				{
					if (((occupied|attack)&((1L<<61)|(1L<<62)))==0)
					{
	                    list+="C7476";
	                }
				}
				if(wCastleQ&&(((1L<<56)&wRook)!=0))
				{
					if (((occupied|(attack))&((1L<<57)|(1L<<58)|(1L<<59)))==0) 
					{
	                    list+="C7472";
	                }
				}
			}
			return list;
		}
		public static String BCastle(long wPawn,long wRook,long wKnight,long wBishop,long wKing,long wQueen,long bPawn,long bRook,long bKnight,long bBishop,long bKing,long bQueen,boolean bCastleQ,boolean bCastleK )
		{
			String list ="";
			long attack = AttackWhite(wPawn,wRook,wKnight, wBishop,wKing, wQueen, bPawn,bRook,bKnight, bBishop, bKing,bQueen);
			if((bKing & attack) == 0)
			{
				if(bCastleK&&(((1L<<7)&bRook)!=0))
				{
					if (((occupied|attack)&((1L<<5)|(1L<<6)))==0)
					{
						list+="c0406";
	                }
				}
				if(bCastleQ&&(((1L<<0)&bRook)!=0))
				{
					if (((occupied|(attack))&((1L<<1)|(1L<<2)|(1L<<3)))==0) 
					{
						list+="c0402";
	                }
				}
			}
			return list;
		}
		
		public static long makeMove(long bitboard,int start,int end,char move,char piece)
		{
			//int start = Character.getNumericValue(move.charAt(1))*8 + Character.getNumericValue(move.charAt(2));
			//int end = Character.getNumericValue(move.charAt(3))*8 + Character.getNumericValue(move.charAt(4));
			if(move==' ')     //regular move
			{

				
				if(((1L<<start)&bitboard)!=0)
				{
					bitboard &= ~(1L<<start);
					bitboard |=(1L<<end);
				}
				else if(((1L<<end)&bitboard)!=0)
				{
					bitboard &= ~(1L<<end);
					Perft.captures++;
				}
			}
			else if(move=='C')             // white castle 
			{
				
					Perft.Castel++;
					if(piece=='K')
					{
						bitboard &= ~(1L<<start);
						bitboard |=(1L<<end);
					}
					if(piece=='R' && end == 62)
					{
						bitboard &= ~(1L<<63);
						bitboard |=(1L<<61);
					}
					if(piece=='R' && end == 58)
					{
						bitboard &= ~(1L<<56);
						bitboard |=(1L<<59);
					}
				
			}
			else if(move=='c')             // white castle 
			{
					Perft.Castel++;
					if(piece=='k')
					{
						bitboard &= ~(1L<<start);
						bitboard |=(1L<<end);
					}
					if(piece=='r' && end == 6)
					{
						bitboard &= ~(1L<<7);
						bitboard |=(1L<<5);
					}
					if(piece=='r' && end == 2)
					{
						bitboard &= ~(1L<<0);
						bitboard |=(1L<<3);
					}
				
			}
			else
			{
				Perft.promotions++;
				if(((1L<<start)&bitboard)!=0)
				{
					bitboard &= ~(1L<<start);
				}
				if((move==piece))
				{
					bitboard |=(1L<<end);
				}
			}
			return bitboard;
		}
		/*
		public static long makeMoveEmpasant(long bitboard,int start,int end,char moveType)
		{
			if(moveType==' ')
			{
				 if ((Math.abs(start-end)==2)&&(((bitboard>>>start)&1)==1))
				 {
					 //return FileMasks8[start%8];
				 }
			}
			return 0;
		}
		*/
		
		public static void main(String[] args) 
		{
			//long start = System.currentTimeMillis();
			makebitBoards();
			//importFEN("rnbqkbnr/1p1ppppp/8/p1p5/8/2N2P2/PPPPP1PP/R1BQKBNR w KQkq a6 0 3 ");
			System.out.println(whiteMove(Cosmos.wPawn,Cosmos.wRook,Cosmos.wKnight, Cosmos.wBishop, Cosmos.wKing, Cosmos.wQueen, Cosmos.bPawn,Cosmos.bRook,Cosmos.bKnight, Cosmos.bBishop, Cosmos.bKing, Cosmos.bQueen,Cosmos.wCastleQ,Cosmos.wCastleK,Cosmos.bCastleQ,Cosmos.bCastleK));  
		    //Move_Generation.drawArray(Cosmos.wPawn,Cosmos.wRook,Cosmos.wKnight, Cosmos.wBishop, Cosmos.wKing, Cosmos.wQueen, Cosmos.bPawn,Cosmos.bRook,Cosmos.bKnight, Cosmos.bBishop, Cosmos.bKing, Cosmos.bQueen);
			//dispBitBoard((AttackBlack(wPawn,wRook,wKnight, wBishop, wKing, wQueen, bPawn,bRook,bKnight, bBishop, bKing, bQueen)));
			//WCastle(wPawn,wRook,wKnight, wBishop, wKing, wQueen, bPawn,bRook,bKnight, bBishop, bKing, bQueen,wCastleQ,wCastleK); 
			//dispBitBoard(wPawn);
			//dispBitBoard(wPawn>>8);
			//System.out.println();
			//dispBitBoard(makeMove(wPawn,"Q1605",'P'));
			//dispBitBoard(makeMove(wQueen,"Q1605",'Q'));
			///dispBitBoard(makemove(wPawn," 6051",'P'));
			//dispBitBoard(bBishop);
			//dispBitBoard(makemove(bBishop," 6051",'b'));
			//dispBitBoard((1L<<61));
			//long end = System.currentTimeMillis();
			//dispBitBoard(AttackBlack(Cosmos.wPawn,Cosmos.wRook,Cosmos.wKnight,Cosmos.wBishop,Cosmos.wKing,Cosmos.wQueen,Cosmos.bPawn,Cosmos.bRook,Cosmos.bKnight,Cosmos.bBishop,Cosmos.bKing,Cosmos.bQueen));
			//dispBitBoard(AttackWhite1(Cosmos.wPawn,Cosmos.wRook,Cosmos.wKnight,Cosmos.wBishop,Cosmos.wKing,Cosmos.wQueen,Cosmos.bPawn,Cosmos.bRook,Cosmos.bKnight,Cosmos.bBishop,Cosmos.bKing,Cosmos.bQueen));
			//dispBitBoard(attackQueen(Cosmos.wQueen));
			//dispBitBoard(attackQueen(Cosmos.wQueen));
			//dispBitBoard(attackRook(Cosmos.wRook));
			//dispBitBoard(attackBishop(Cosmos.wBishop));
			//dispBitBoard(attackWhitePawn(Cosmos.wPawn));
			//dispBitBoard(AttackBlack(Cosmos.wPawn,Cosmos.wRook,Cosmos.wKnight,Cosmos.wBishop,Cosmos.wKing,Cosmos.wQueen,Cosmos.bPawn,Cosmos.bRook,Cosmos.bKnight,Cosmos.bBishop,Cosmos.bKing,Cosmos.bQueen));
			//importFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
			//dispBitBoard();
			//dispBitBoard(AttackBlack(Cosmos.wPawn,Cosmos.wRook,Cosmos.wKnight,Cosmos.wBishop,Cosmos.wKing,Cosmos.wQueen,Cosmos.bPawn,Cosmos.bRook,Cosmos.bKnight,Cosmos.bBishop,Cosmos.bKing,Cosmos.bQueen));

		}
		
		/*
		public  static long AttackWhite1(long wPawn,long wRook,long wKnight,long wBishop,long wKing,long wQueen,long bPawn,long bRook,long bKnight,long bBishop,long bKing,long bQueen)
		{
			long attack=0;
			notMyPiece =  ~ (wPawn|wRook|wKnight|wBishop|wKing|wQueen|bKing);
			occupied = bPawn|bRook|bKnight|bBishop|bKing|bQueen|wPawn|wRook|wKnight|wBishop|wKing|wQueen|bKing;
			empty = ~occupied;
			// Pawn
			attack |= (wPawn>>>7)&~FileMasks8[0];
			attack |= (wPawn>>>9)&~FileMasks8[7];
			// Knight
			long a=~(wKnight-1)&wKnight;
			long fwdLeft=0L;
			long fwdRight=0L;
			long backLeft=0L; 
			long backRight=0L;
			long moves=0L;
			while(a!=0)
			{
				//int i=Long.numberOfTrailingZeros(a);
				fwdLeft=(((a&~RankMasks8[1])&(a&~RankMasks8[0])&(a&~FileMasks8[0]))>>17)&notMyPiece;//2+1
				fwdLeft=fwdLeft|((((a&~RankMasks8[0])&(a&~FileMasks8[0])&(a&~FileMasks8[1]))>>10)&notMyPiece);//1+2
				fwdRight=(((a&~RankMasks8[1])&(a&~RankMasks8[0])&(a&~FileMasks8[7]))>>15)&notMyPiece;//2+1
				fwdRight=fwdRight|((((a&~RankMasks8[0])&(a&~FileMasks8[7])&(a&~FileMasks8[6]))>>6)&notMyPiece);//1+2
				backLeft=(((a&~RankMasks8[6])&(a&~RankMasks8[7])&(a&~FileMasks8[0]))<<15)&notMyPiece;//2+1
				backLeft=backLeft|((((a&~RankMasks8[7])&(a&~FileMasks8[0])&(a&~FileMasks8[1]))<<6)&notMyPiece);//1+2
				backRight=(((a&~RankMasks8[6])&(a&~RankMasks8[7])&(a&~FileMasks8[7]))<<17)&notMyPiece;//2+1
				backRight=backRight|((((a&~RankMasks8[7])&(a&~FileMasks8[6])&(a&~FileMasks8[7]))<<10)&notMyPiece);//1+2
				moves=fwdLeft|fwdRight|backLeft|backRight;	
				attack |= moves;
				wKnight =wKnight &~a;
				a = ~(wKnight-1)&wKnight;	
			}
			// Bishop and queen Diagonal and Anti-Diagonals
			//attack = 0;
			long QueenBishop = wQueen|wBishop;
			a = ~(QueenBishop-1)&QueenBishop;
			long diagonal;
			while(a!=0)
			{
				int i = Long.numberOfTrailingZeros(a);
				diagonal = diagonals(i);
				attack |= diagonal;
				QueenBishop = QueenBishop &~a;
				a = ~(QueenBishop-1)&QueenBishop;
			}
			// Rook and queen Horizontal and vertical
			long RookQueen = wRook|wQueen;
			a = ~(RookQueen-1) & RookQueen;
			long straight;
			while(a!=0)
			{
				int i = Long.numberOfTrailingZeros(a);
				straight = straight(i) ;
				attack |= straight;
				RookQueen = RookQueen & ~a;
				a = ~(RookQueen-1) & RookQueen ;
			}
			
			//King
			a = 0;
			a |= (wKing&~FileMasks8[0])>>1;    //left movement
			a |= (wKing&~FileMasks8[7])<<1;   // right movement
			a |= (wKing&~RankMasks8[0])>>8;    //upwards movement
			a |= (wKing&~RankMasks8[7])<<8;      //downwards movement
			a |= (wKing&~RankMasks8[0]&~FileMasks8[0])>>9;  // up-left movement
			a |= (wKing&~RankMasks8[7]&~FileMasks8[7])<<9;     // down-right movement
			a |= (wKing&~RankMasks8[0]&~FileMasks8[7])>>7;       // up-right movement
			a |= (wKing&~RankMasks8[7]&~FileMasks8[0])<<7;     //right-up movement
			attack |= a;
			//dispBitBoard(attack);
			return attack;
		}
		public static Long AttackBlack1(long wPawn,long wRook,long wKnight,long wBishop,long wKing,long wQueen,long bPawn,long bRook,long bKnight,long bBishop,long bKing,long bQueen)
		{
			long attack=0;
			notMyPiece =  ~ (wPawn|wRook|wKnight|wBishop|wKing|wQueen|bKing);
			occupied = bPawn|bRook|bKnight|bBishop|bKing|bQueen|wPawn|wRook|wKnight|wBishop|wKing|wQueen|bKing;
			empty = ~occupied;
			// Pawn
			attack |= (bPawn<<7)&~FileMasks8[7];
			attack |= (bPawn<<9)&~FileMasks8[0];
			// Knight
			long a=~(bKnight-1)&bKnight;
			long fwdLeft=0L;
			long fwdRight=0L;
			long backLeft=0L; 
			long backRight=0L;
			long moves=0L;
			while(a!=0)
			{
				//int i=Long.numberOfTrailingZeros(a);
				fwdLeft=(((a&~RankMasks8[1])&(a&~RankMasks8[0])&(a&~FileMasks8[0]))>>17)&notMyPiece;//2+1
				fwdLeft=fwdLeft|((((a&~RankMasks8[0])&(a&~FileMasks8[0])&(a&~FileMasks8[1]))>>10)&notMyPiece);//1+2
				fwdRight=(((a&~RankMasks8[1])&(a&~RankMasks8[0])&(a&~FileMasks8[7]))>>15)&notMyPiece;//2+1
				fwdRight=fwdRight|((((a&~RankMasks8[0])&(a&~FileMasks8[7])&(a&~FileMasks8[6]))>>6)&notMyPiece);//1+2
				backLeft=(((a&~RankMasks8[6])&(a&~RankMasks8[7])&(a&~FileMasks8[0]))<<15)&notMyPiece;//2+1
				backLeft=backLeft|((((a&~RankMasks8[7])&(a&~FileMasks8[0])&(a&~FileMasks8[1]))<<6)&notMyPiece);//1+2
				backRight=(((a&~RankMasks8[6])&(a&~RankMasks8[7])&(a&~FileMasks8[7]))<<17)&notMyPiece;//2+1
				backRight=backRight|((((a&~RankMasks8[7])&(a&~FileMasks8[6])&(a&~FileMasks8[7]))<<10)&notMyPiece);//1+2
				moves=fwdLeft|fwdRight|backLeft|backRight;	
				attack |= moves;
				bKnight =bKnight &~a;
				a = ~(bKnight-1)&bKnight;	
			}
			// Bishop and queen Diagonal and Anti-Diagonals
			long QueenBishop = bQueen|bBishop;
			a = ~(QueenBishop-1)&QueenBishop;
			long diagonal;
			while(a!=0)
			{
				int i = Long.numberOfTrailingZeros(a);
				diagonal = diagonals(i);
				attack |= diagonal;
				QueenBishop = QueenBishop &~a;
				a = ~(QueenBishop-1)&QueenBishop;
			}
			// Rook and queen Horizontal and vertical
			long RookQueen = bRook|bQueen;
			a = ~(RookQueen-1) & RookQueen;
			long straight;
			while(a!=0)
			{
				int i = Long.numberOfTrailingZeros(a);
				straight = straight(i) ;
				attack |= straight;
				RookQueen = RookQueen & ~a;
				a = ~(RookQueen-1) & RookQueen ;
			}
			//King
			a = 0;
			a |= (bKing&~FileMasks8[0])>>1;    //left movement
			a |= (bKing&~FileMasks8[7])<<1;   // right movement
			a |= (bKing&~RankMasks8[0])>>8;    //upwards movement
			a |= (bKing&~RankMasks8[7])<<8;      //downwards movement
			a |= (bKing&~RankMasks8[0]&~FileMasks8[0])>>9;  // up-left movement
			a |= (bKing&~RankMasks8[7]&~FileMasks8[7])<<9;     // down-right movement
			a |= (bKing&~RankMasks8[0]&~FileMasks8[7])>>7;       // up-right movement
			a |= (bKing&~RankMasks8[7]&~FileMasks8[0])<<7;     //right-up movement
			attack |= a;
			return attack;
		}
		*/
		/*
		public static long makeMove(long bitboard,String move,char piece)
		{
			int start = Character.getNumericValue(move.charAt(1))*8 + Character.getNumericValue(move.charAt(2));
			int end = Character.getNumericValue(move.charAt(3))*8 + Character.getNumericValue(move.charAt(4));
			if(move.charAt(0)==' ')     //regular move
			{

				if(((1L<<start)&bitboard)!=0)
				{
					bitboard &= ~(1L<<start);
					bitboard |=(1L<<end);
				}
				else if(((1L<<end)&bitboard)!=0)
				{
					bitboard &= ~(1L<<end);
				}
			}
			else if(move.charAt(0)=='C')             // white castle 
			{
				
					if(piece=='A')
					{
						bitboard &= ~(1L<<start);
						bitboard |=(1L<<end);
					}
					if(piece=='R' && move == "C7476")
					{
						bitboard &= ~(1L<<63);
						bitboard |=(1L<<61);
					}
					if(piece=='R' && move == "C7472")
					{
						bitboard &= ~(1L<<56);
						bitboard |=(1L<<59);
					}
				
			}
			else if(move.charAt(0)=='c')             // white castle 
			{
				
					if(piece=='a')
					{
						bitboard &= ~(1L<<start);
						bitboard |=(1L<<end);
					}
					if(piece=='r' && move == "c0406")
					{
						bitboard &= ~(1L<<7);
						bitboard |=(1L<<5);
					}
					if(piece=='r' && move == "c0402")
					{
						bitboard &= ~(1L<<0);
						bitboard |=(1L<<3);
					}
				
			}
			else
			{
				if(((1L<<start)&bitboard)!=0)
				{
					bitboard &= ~(1L<<start);
				}
				if((move.charAt(0)==piece))
				{
					bitboard |=(1L<<end);
				}
			}
			return bitboard;
		}
		*/
}
