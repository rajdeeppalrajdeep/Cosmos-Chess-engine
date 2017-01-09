public class Evaluation
{
	static int score =0;
	//static int values[]={100,300,350,500,975,32767};
	static int values[]={20,60,70,100,200,6276};
//	static int values[]={10,30,35,50,97,3276};

	//static int values1[]={900,500,350,300,100,10};
	static int a,b;
	public static short[] PawnTable = new short[] 
	{
	 0, 0, 0, 0,0, 0, 0, 0, 
	 50, 50, 50, 50, 50, 50, 50, 50, 
	 10, 10, 20, 30, 30, 20,10, 10, 
	 5, 5, 10, 27, 27, 10, 5, 5, 
	 0, 0, 0, 25, 25, 0, 0, 0,
	 5,-5,-10, 0, 0,-10, -5, 5,
	 5, 10, 10,-25,-25, 10, 10, 5,
	 0, 0, 0, 0, 0, 0, 0, 0
	 };
	 public static short[] KnightTable = new short[]
	{
	-50,-40,-30,-30,-30,-30,-40,-50,
	-40,-20, 0, 0, 0, 0,-20,-40,
	-30, 0, 10, 15, 15, 10, 0,-30,
	-30, 5, 15, 20, 20, 15, 5,-30,
	-30, 0, 15, 20, 20, 15, 0,-30,
	-30, 5, 10, 15, 15, 10, 5,-30,
	-40,-20, 0, 5, 5, 0,-20,-40,
	-50,-40,-20,-30,-30,-20,-40,-50,
	};
	 public static short[] RookTable = new short[]
	        { 0,  0,  0,  0,  0,  0,  0,  0,
	         5, 10, 10, 10, 10, 10, 10,  5,
	        -5,  0,  0,  0,  0,  0,  0, -5,
	        -5,  0,  0,  0,  0,  0,  0, -5,
	        -5,  0,  0,  0,  0,  0,  0, -5,
	        -5,  0,  0,  0,  0,  0,  0, -5,
	        -5,  0,  0,  0,  0,  0,  0, -5,
	         0,  0,  0,  5,  5,  0,  0,  0
	        	};
	 public static short[] QueenTable = new short[]
	        {
		-20,-10,-10, -5, -5,-10,-10,-20,
	        -10,  0,  0,  0,  0,  0,  0,-10,
	        -10,  0,  5,  5,  5,  5,  0,-10,
	         -5,  0,  5,  5,  5,  5,  0, -5,
	          0,  0,  5,  5,  5,  5,  0, -5,
	        -10,  5,  5,  5,  5,  5,  0,-10,
	        -10,  0,  5,  0,  0,  0,  0,-10,
	        -20,-10,-10, -5, -5,-10,-10,-20
	        };
	        
	public static short[] BishopTable = new short[]
	{
	-20,-10,-10,-10,-10,-10,-10,-20,
	-10, 0, 0, 0, 0, 0, 0,-10,
	-10, 0, 5, 10, 10, 5, 0,-10,
	-10, 5, 5, 10, 10, 5, 5,-10,
	-10, 0, 10, 10, 10, 10, 0,-10,
	-10, 10, 10, 10, 10, 10, 10,-10,
	-10, 5, 0, 0, 0, 0, 5,-10,
	-20,-10,-40,-10,-10,-40,-10,-20
	};
	public static short[] KingTable = new short[]
	{
	-30, -40, -40, -50, -50, -40, -40, -30,
	-30, -40, -40, -50, -50, -40, -40, -30,
	-30, -40, -40, -50, -50, -40, -40, -30,
	-30, -40, -40, -50, -50, -40, -40, -30,
	-20, -30, -30, -40, -40, -30, -30, -20,
	-10, -20, -20, -20, -20, -20, -20, -10,
	20, 20, 0, 0, 0, 0, 20, 20,
	20, 30, 10, 0, 0, 10, 30, 20
	};
	public static int countOnes(long x)
	{
		long a = ~(x-1)&x;
		int count = 0;
		while(a!=0)
		{
			int i = Long.numberOfTrailingZeros(a);
			count ++;
			x = x &~a;
			a = ~(x-1)&x;
		}
		return count;
	}
	public static int getPositionValue(long pawn,long knight,long bishop,long rook,long queen, long king,boolean type)
	{
		long res=0L;
		int k;
		int score=0;
		long temp;
		if(type)
		{
			b=0;
			a=-1;
		}
		else
		{
			b=63;
			a=1;
		}
		temp=~(pawn-1)&pawn;
		while(temp!=0)
		{
			k=Long.numberOfTrailingZeros(temp);
			score+=PawnTable[b-a*k];
			pawn=pawn&~temp;
			temp=~(pawn-1)&pawn;
		}
		temp=~(knight-1)&knight;
		while(temp!=0)
		{
			k=Long.numberOfTrailingZeros(temp);
			score+=KnightTable[b-a*k];			
			knight=knight&~temp;
			temp=~(knight-1)&knight;
			
		}
		temp=~(bishop-1)&bishop;
		while(temp!=0)
		{
			k=Long.numberOfTrailingZeros(temp);
			score+=BishopTable[b-a*k];
			bishop=bishop&~temp;
			temp=~(bishop-1)&bishop;
			
		}
		temp=~(rook-1)&rook;
		while(temp!=0)
		{
			k=Long.numberOfTrailingZeros(temp);
			score+=RookTable[b-a*k];
			rook=rook&~temp;
			temp=~(rook-1)&rook;
			
		}
		
		temp=~(queen-1)&queen;
		while(temp!=0)
		{
			k=Long.numberOfTrailingZeros(temp);
			score+=QueenTable[b-a*k];
			queen=queen&~temp;
			temp=~(queen-1)&queen;
			
		}
		temp=~(king-1)&king;
		while(temp!=0)
		{
			k=Long.numberOfTrailingZeros(temp);
			score+=KingTable[b-a*k];
			king=king&~temp;
			temp=~(king-1)&king;
			
		}
		return score;
			
	}
	public static int attack(long wBoard[],long bBoard[])
	{
		int who,what,score=0,x=0;
		long temp=0L,attack=0L,occupied=0L,attackB=0L;
		occupied=bBoard[0]|bBoard[1]|bBoard[2]|bBoard[3]|bBoard[4]|bBoard[5];
		for(who=0;who<6;who++)
		{

			switch(who)
			{
				case 0:
					attack=Move_Generation.attackWhitePawn(wBoard[who]);
					break;
				case 1:
					attack=Move_Generation.attackKnight(wBoard[who]);
					break;
				case 2: 
					attack=Move_Generation.attackBishop(wBoard[who]);
					break;
				case 3:
					attack=Move_Generation.attackRook(wBoard[who]);
					break;
				case 4:
					attack=Move_Generation.attackQueen(wBoard[who]);
					break;
				case 5:
					attack=Move_Generation.attackKing(wBoard[who]);
					break;	
								
			}
			attack=attack&occupied;
			temp=~(attack-1)&attack;
			while(temp!=0)
			{	
				x=0;
				for(what=5;what>=0;what--)
				{
					if((attack&bBoard[what])!=0L)
						break;
				}
				if(Cosmos.debug)
				{
					System.out.println(who+"is attacking "+what);
					System.out.println("This is the attack");
					Move_Generation.dispBitBoard(temp);
					System.out.println("This is what is getting attacked");
									Move_Generation.dispBitBoard(bBoard[what]);
					System.out.println("This is who is attacking");
					Move_Generation.dispBitBoard(wBoard[who]);
				}
				
				
				bBoard[what]=bBoard[what]&(~temp);					
				attackB=Move_Generation.AttackBlack(wBoard[0],wBoard[3],wBoard[1],wBoard[2],wBoard[5],wBoard[4],bBoard[0],bBoard[3],bBoard[1],bBoard[2],bBoard[5],bBoard[4]);				
				if((temp&attackB)!=0L)
					x=1;
			//	score+=values[what]+values[what]*(1-x)*10-(values[who]*x);
				score+=(values[what]-(values[who]*x));
				//score+=values1[who]-values[who]*x;
				//System.out.println(score);
				bBoard[what]=bBoard[what]|temp;							
				attack=attack&~temp;
				temp=~(attack-1)&attack;			
			}
		}
			return score*2;
		}
		
	
	public static int protect(long bBoard[],long wBoard[])
	{
		int who,what,score=0,x=0;
		long temp=0L,attack=0L,occupied=0L,attackW=0L;
		occupied=wBoard[0]|wBoard[1]|wBoard[2]|wBoard[3]|wBoard[4]|wBoard[5];
		for(who=0;who<6;who++)
		{
			switch(who)
			{
				case 0:
					attack=Move_Generation.attackBlackPawn(bBoard[who]);
					break;
				case 1:
					attack=Move_Generation.attackKnight(bBoard[who]);
					break;
				case 2: 
					attack=Move_Generation.attackBishop(bBoard[who]);
					break;
				case 3:
					attack=Move_Generation.attackRook(bBoard[who]);
					break;
				case 4:
					attack=Move_Generation.attackQueen(bBoard[who]);
					break;
				case 5:
					attack=Move_Generation.attackKing(bBoard[who]);
					break;	
								
			}
			attack=attack&occupied;
			temp=~(attack-1)&attack;
			while(temp!=0)
			{	
				x=0;
				for(what=5;what>=0;what--)
				{
					if((attack&wBoard[what])!=0L)
						break;
				}
				
				wBoard[what]=wBoard[what]&(~temp);					
				attackW=Move_Generation.AttackWhite(wBoard[0],wBoard[3],wBoard[1],wBoard[2],wBoard[5],wBoard[4],bBoard[0],bBoard[3],bBoard[1],bBoard[2],bBoard[5],bBoard[4]);							
				if((temp&attackW)!=0L)
					x=1;
				score+=values[who]*x-values[what];
			///	System.out.println(score);
				wBoard[what]=wBoard[what]|temp;									
				attack=attack&~temp;
				temp=~(attack-1)&attack;			
			}
			
		}
		return score;
	}
	public static int evaluate(long wPawn,long wKnight,long wBishop,long wRook,long wQueen, long wKing,long bPawn,long bKnight,long bBishop,long bRook,long bQueen, long bKing,boolean type,boolean wCastleQ,boolean wCastleK,boolean bCastleQ,boolean bCastleK )
	{
		score = 0;
		long wBoard[]={wPawn,wKnight,wBishop,wRook,wQueen,wKing};
		long bBoard[]={bPawn,bKnight,bBishop,bRook,bQueen,bKing};
		
		//adding piece value to score
		score+=(countOnes(wPawn)*100+countOnes(wKnight)*300+countOnes(wBishop)*350+countOnes(wRook)*500+countOnes(wQueen)*975+countOnes(wKing)*32767)*10;
		if(Cosmos.debug)
		{
			System.out.println("White score after piece"+ score);
		}
		score-=(countOnes(bPawn)*100+countOnes(bKnight)*300+countOnes(bBishop)*350+countOnes(bRook)*500+countOnes(bQueen)*975+countOnes(bKing)*32767)*10;
		if(Cosmos.debug)
		{
			System.out.println("black score after piece"+ score);
		}
		//adding position value to the score
		score+=getPositionValue(wPawn,wKnight,wBishop,wRook,wQueen,wKing,true);
		if(Cosmos.debug)
		{
			System.out.println("White score after postion"+ score);
		}
		score-=getPositionValue(bPawn,bKnight,bBishop,bRook,bQueen,bKing,false);
		if(Cosmos.debug)
		{
			System.out.println("black score after postion"+ score);
		}
		////checking for possible attacks
		Move_Generation.generateAttack(wPawn,wRook,wKnight,wBishop,wKing,wQueen,bPawn,bRook,bKnight,bBishop,bKing,bQueen,true);
		score+=attack(wBoard,bBoard);
		if(Cosmos.debug)
		{
			System.out.println("score after attack"+ score);
		}
		//checking for protection
		Move_Generation.generateAttack(wPawn,wRook,wKnight,wBishop,wKing,wQueen,bPawn,bRook,bKnight,bBishop,bKing,bQueen,false);
		score+=protect(bBoard,wBoard);
		if(Cosmos.debug)
		{
			System.out.println(" score after protect"+ score);
		}
		String moves=Move_Generation.whiteMove(wPawn,wRook,wKnight,wBishop,wKing,wQueen,bPawn,bRook,bKnight,bBishop,bKing,bQueen,wCastleQ,wCastleK,bCastleQ,bCastleK);
		score+=(moves.length()/5)*2;
		score+=wCastleQ?25:0;
		score+=wCastleK?25:0;
		score+=bCastleQ?25:0;
		score+=bCastleK?25:0;
		
		
		return score;
	}
	public static void main(String[] args) 
	{
		Move_Generation.makebitBoards();
		//System.out.println(evaluate(Cosmos.wPawn,Cosmos.wKnight,Cosmos.wBishop,Cosmos.wRook,Cosmos.wQueen,Cosmos.wKing,Cosmos.bPawn,Cosmos.bKnight, Cosmos.bBishop, Cosmos.bRook, Cosmos.bQueen, Cosmos.bKing, true));
	}
	
	
}

