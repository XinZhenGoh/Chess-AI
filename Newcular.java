package chess;
//Ray Goh Xin Zhen
//Program Name: Conor McGregor

import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class Newcular {
int board[][] = new int[7][9];
Stack minStack = new Stack();
Stack maxStack = new Stack();
Stack miniMaxStack = new Stack();
//pawn
public final static int p = 10;
//knight
public final static int n = 32;
//rook
public final static int r = 50;
//bishop
public final static int b = 33;
//king
public final static int k = 2000;

int maxdepth = 6;
Stream s = new Stream();
long time = 0;
int nodeCount = 0;
boolean winMove = false;
int pruneMove [][] = new int[5][5];
int mostPrune[] = {50,50,50,50,50,50};

public int startingPieces[][] = {
		{0,0,0,-p,0,p,0,0,0},
		{-n,0,0,0,0,0,0,0,n},
		{-r,0,0,-p,0,p,0,0,r},
		{-k,-b,-b,0,0,0,b,b,k},
		{-r,0,0,-p,0,p,0,0,r},
		{-n,0,0,0,0,0,0,0,n},
		{0,0,0,-p,0,p,0,0,0}
};

public static void main(String[] args) { new Newcular();	}

public void setup() {
	for(int i = 0; i<7;i++) {
		for(int j = 0;j<9;j++) {
			board[i][j] = startingPieces[i][j];
		}
	}
}

private int check4winner()
{
	boolean playerKing=false, cpuKing=false;
	
	for (int i=0; i<7; i++) {
		for(int j =0; j<9; j++) {
		if (board[i][j] == k) {	cpuKing = true; }
		if (board[i][j] == -k) { playerKing = true; }
		}
	}	
	
	if (playerKing && !cpuKing) { return -10000; }
	else if (!playerKing && cpuKing) {	return 10000; }	
	
	return -1;
}

private void checkGameOver() {
	if (check4winner() == 10000) { System.out.println("\nArtificial Intelligence: I WIN."); System.exit(0); }
	else if (check4winner() == -10000) { System.out.println("\nArtificial Intelligence: Good game well played"); System.exit(0); }
}

private void printBoard() {
	char pieceName = 0;
	int num = 9;

	System.out.println("\n   A B C D E F G \n");
	for (int j = 8; j>= 0;j--) {
		System.out.print(num + " ");
		for(int g = 0; g<7 ;g++) {
		System.out.print(" ");
		switch(board[g][j]) {	
		case 0: pieceName = '-'; break;
		case k: pieceName = 'K'; break;
		case r: pieceName = 'R'; break;
		case b: pieceName = 'B'; break;
		case n: pieceName = 'N'; break;
		case p: pieceName = 'P'; break;
		case -k: pieceName = 'k'; break;
		case -r: pieceName = 'r'; break;
		case -b: pieceName = 'b'; break;
		case -n: pieceName = 'n'; break;
		case -p: pieceName = 'p'; break;
		}
		System.out.print(pieceName);
		}
		System.out.println("  " + num);
		num--;
	}
	System.out.println("\n   A B C D E F G");	
	}
	

public Newcular() {
setup();
System.out.println("Would you like to make the first move? y/n");
Scanner sn = new Scanner(System.in);
char first = sn.next().charAt(0);
printBoard();
if(first == 'y' || first == 'Y'){
	for( ;; ) {
	System.out.println("\nSystem: Players Turn");
	getAmove();
	printBoard();
	checkGameOver();
	System.out.println("\nSystem: A.I's turn");
	miniMax();
	checkGameOver();
	}
}
else if(first == 'n' || first == 'N'){
	for( ;;) {
	System.out.println("\nSystem: A.I's turn");
	miniMax();
	checkGameOver();
	System.out.println("\nSystem: Players Turn");
	getAmove();
	printBoard();
	checkGameOver();
}
}
}


private void getAmove() {
	PlayerLegalMoves pMove = new PlayerLegalMoves(board);
	int[][] currentMoves = pMove.getMoves();
	char c1,r1,c2,r2;
	//remove this
	System.out.println("\nCurrent A.I legal moves available:");
	AILegalMoves AIMove = new AILegalMoves(board);
	int[][] AIcurrentMoves = AIMove.getMoves();
	
	int count2 = AIMove.getCount();
	//count how many legal moves left
	int count = pMove.getCount();
	
	for(int i = 0;i < count2;i++) {
		c1 = (char) (AIcurrentMoves[i][0] + 'a');
		r1 = (char) (AIcurrentMoves[i][1] + '1');
		c2 = (char) (AIcurrentMoves[i][2] + 'a');
		r2 = (char) (AIcurrentMoves[i][3] + '1');	
			System.out.print(c1);
			System.out.print(r1);
			System.out.print(c2);
			System.out.print(r2);
		System.out.print(", ");
		if((i+1)%10==0) {
			System.out.println();
		}
	}
	
	//testing, remove after
	System.out.println("\n\nCurrent legal moves available:");
	for(int i = 0;i < count;i++) {
			c1 = (char) (currentMoves[i][0] + 'a');
			r1 = (char) (currentMoves[i][1] + '1');
			c2 = (char) (currentMoves[i][2] + 'a');
			r2 = (char) (currentMoves[i][3] + '1');	
		System.out.print(c1);
		System.out.print(r1);
		System.out.print(c2);
		System.out.print(r2);
		System.out.print(", ");
		if((i+1)%10==0) {
			System.out.println();
		}
		
	}
	
	if(count == 0) {
		System.out.println("You have no legal moves remaining, you lost");
	//add exit 
	}
	
	Scanner kb = new Scanner(System.in);
		String playerMove;
		System.out.println("\n\nEnter your move: ");
		playerMove = kb.nextLine();
		
		while(playerMove.length() != 4) {
			System.out.println("Invalid move please try again");
			//getAmove();
			System.out.println("\n\nEnter your move: ");
			playerMove = kb.nextLine();
		}
		
		int a,b,c,d;
		int n,m,t,y;
		a = playerMove.charAt(0) - 'a';
		b = playerMove.charAt(1) - '1';
		c = playerMove.charAt(2) - 'a';
		d = playerMove.charAt(3) - '1';

		
		if(a>=0 && a<=6 && b>=0 && b<9 && c>=0 && c<=6 && d>=0 && d<9) {
			for(int i = 0; i<count; i++) {
			if(a ==currentMoves [i][0] && b ==currentMoves [i][1] && c ==currentMoves [i][2] && d ==currentMoves [i][3]) {
			
			if(a == c && b == d) {	
				
				n = a+1;
				m = a-1;
				t = b+1;
				y = b-1;

				if(n>6) {
					n = 6;
				}
				if(m < 0) {
					m = 0;
				} 
				if(t>8) {
					t = 8 ;
				}
				if(y<0) {
					y = 0;
				}

			board[a][b] = 0;
			board[m][b] = 0;
			board[n][b] = 0;
			board[a][y] = 0;
			board[a][t] = 0;
			board[m][t] = 0;
			board[n][t] = 0;
			board[m][y] = 0;
			board[n][y] = 0;
			
			System.out.println("Boooooom explode! ");
			}
			//if no explosion
				
			board[c][d] = board[a][b];
			board[a][b] = 0;
			return;
			
			}
			}
		}
		System.out.println("Illegal Move! try again please");
		printBoard();
		getAmove();
	}

private int min(int depth, int alpha, int beta) {
	int best = 99999, score;
	PlayerLegalMoves pMoves = new PlayerLegalMoves(board);
	int [][] move = pMoves.getMoves();
	int count = pMoves.getCount();
	int a,b,c,d,n,m,t,y,prev;
	if (check4winner() != -1) return (check4winner());
	
	if(depth == maxdepth || count == 0) {
		return (evaluate());
	}
	
	for (int i = 0;i<count;i++) {
		//incre node
		nodeCount++;
		a = move[i][0]; b = move[i][1]; c = move[i][2]; d = move[i][3];
		//check for explosion first
		if(a == c && b == d) {
			n = a+1;
			m = a-1;
			t = b+1;
			y = b-1;

			if(n>6) {n = 6;}
			if(m < 0) {m = 0;} 
			if(t>8) {t = 8 ;}
			if(y<0) {y = 0;}

//save pieces

minStack.push(board[a][b]);
minStack.push(board[n][b]);
minStack.push(board[m][b]);
minStack.push(board[a][y]);
minStack.push(board[a][t]);
minStack.push(board[m][t]);
minStack.push(board[n][t]);
minStack.push(board[m][y]);
minStack.push(board[n][y]);
			
			
//perform explode
		board[a][b] = 0;
		board[m][b] = 0;
		board[n][b] = 0;
		board[a][y] = 0;
		board[a][t] = 0;
		board[m][t] = 0;
		board[n][t] = 0;
		board[m][y] = 0;
		board[n][y] = 0;
			
		
		score = max(depth+1, alpha, beta);
		
		if(score < best) best = score;
		
		//alpha - beta pruning
		// -1 if alpha/beta value is still null
		if(alpha != -1) {
			if(score <= alpha) {
				board[n][y] = (int) minStack.pop();
				board[m][y] = (int) minStack.pop();
				board[n][t] = (int) minStack.pop();
				board[m][t] = (int) minStack.pop();
				board[a][t] = (int) minStack.pop();
				board[a][y] = (int) minStack.pop();
				board[m][b] = (int) minStack.pop();
				board[n][b] = (int) minStack.pop();
				board[a][b] = (int) minStack.pop();
				return best;
			}
		}
		
		if(score < beta || beta == -1) {
			beta = score;
		}
		
		
		//put the move back	
		board[n][y] = (int) minStack.pop();
		board[m][y] = (int) minStack.pop();
		board[n][t] = (int) minStack.pop();
		board[m][t] = (int) minStack.pop();
		board[a][t] = (int) minStack.pop();
		board[a][y] = (int) minStack.pop();
		board[m][b] = (int) minStack.pop();
		board[n][b] = (int) minStack.pop();
		board[a][b] = (int) minStack.pop();
		
		}
		
		//regular move
		else if(a != c || b != d) {
		//System.out.println("Min = "+a+" = "+b+" = "+c+" = "+d);
		prev = board[c][d];
		
		//put move
		board[c][d] = board[a][b];
		board[a][b] = 0;
		
		score = max(depth+1, alpha, beta);
		
		if(score < best) best = score;
		
		//alpha - beta pruning
		// -1 if alpha/beta value is still null
		if(alpha != -1) {
			if(score <= alpha) {
				board[a][b] = board[c][d];
				board[c][d] = prev;
				return best;
			}
		}
		
		if(score < beta || beta == -1) {
			beta = score;
		}
		//put the move back
		board[a][b] = board[c][d];
		board[c][d] = prev;
		
		
		}

	}

	
	return (best);
}

private int max(int depth, int alpha, int beta) {
	int best = -99999, score;
	AILegalMoves aiMoves = new AILegalMoves(board);
	int [][] move = aiMoves.getMoves();
	int count = aiMoves.getCount();
	int a,b,c,d,n,m,t,y,prev;
	if (check4winner() != -1) return (check4winner());
	
	if(depth == maxdepth || count == 0) { 
		return (evaluate());

	}
	
	//remove
	
	for (int i = 0;i<count;i++) {
		//incre node
		nodeCount++;
		
		a = move[i][0]; b = move[i][1]; c = move[i][2]; d = move[i][3];
		//check for explosion first
		if(a == c && b == d) {
		    //unimplemented
				n = a+1;
				m = a-1;
				t = b+1;
				y = b-1;

				if(n>6) { n = 6; }
				if(m < 0) { m = 0; } 
				if(t>8) { t = 8 ; }
				if(y<0) { y = 0; }

	//save pieces
			maxStack.push(board[a][b]);
			maxStack.push(board[n][b]);
			maxStack.push(board[m][b]);
			maxStack.push(board[a][y]);
			maxStack.push(board[a][t]);
			maxStack.push(board[m][t]);
			maxStack.push(board[n][t]);
			maxStack.push(board[m][y]);
			maxStack.push(board[n][y]);		
				
	//perform explode
			board[a][b] = 0;
			board[m][b] = 0;
			board[n][b] = 0;
			board[a][y] = 0;
			board[a][t] = 0;
			board[m][t] = 0;
			board[n][t] = 0;
			board[m][y] = 0;
			board[n][y] = 0;
				
			score = min(depth+1, alpha, beta);
			
			if(score > best) best = score;
			
			//alpha - beta pruning
			// -1 if alpha/beta value is still null
			if(beta != -1) {
				if(score >= beta) {
					board[n][y] = (int) maxStack.pop();
					board[m][y] = (int) maxStack.pop();
					board[n][t] = (int) maxStack.pop();
					board[m][t] = (int) maxStack.pop();
					board[a][t] = (int) maxStack.pop();
					board[a][y] = (int) maxStack.pop();
					board[m][b] = (int) maxStack.pop();
					board[n][b] = (int) maxStack.pop();
					board[a][b] = (int) maxStack.pop();	
					return best;
				}
			}
			
			if(score > alpha || alpha == -1) {
				alpha = score;
			}
			
			//put the move back
			board[n][y] = (int) maxStack.pop();
			board[m][y] = (int) maxStack.pop();
			board[n][t] = (int) maxStack.pop();
			board[m][t] = (int) maxStack.pop();
			board[a][t] = (int) maxStack.pop();
			board[a][y] = (int) maxStack.pop();
			board[m][b] = (int) maxStack.pop();
			board[n][b] = (int) maxStack.pop();
			board[a][b] = (int) maxStack.pop();	
			
			
			
}	
		//regular move
		else if(a != c || b != d) {
		//saving move location
		prev = board[c][d];
		
		//execute move
		board[c][d] = board[a][b];
		board[a][b] = 0;
		
		score = min(depth+1, alpha, beta);
			
		if(score > best) best = score;
		
		//alpha - beta pruning
		// -1 if alpha/beta value is still null
		if(beta != -1) {
			if(score >= beta) {
				board[a][b] = board[c][d];
				board[c][d] = prev;
				return best;
			}
		}
		
		if(score > alpha || alpha == -1) {
			alpha = score;
		}
		//put the move back
		board[a][b] = board[c][d];
		board[c][d] = prev;
		
		}

	}
	
	return (best);
}

//sort moves at player's thinking time
private void sortMoves(int aiCurrentMoves[][], int count) {
	int a,b,c,d,n,m,t,y, prev;
	int aiCurrentScore;
	int[][] moveWScore = new int[55][5];
	
for (int i=0; i<count; i++) {	
			
		//incre node
		nodeCount++;
		a = aiCurrentMoves[i][0];	
		b = aiCurrentMoves[i][1];	
		c = aiCurrentMoves[i][2];	
		d = aiCurrentMoves[i][3];	
		
		if( a == c && b == d) {
		//System.out.println("Minimax explode = "+a+" = "+b+" = "+c+" = "+d);	
		n = a+1;
		m = a-1;
		t = b+1;
		y = b-1;

		if(n>6) {n = 6;}
		if(m < 0) {m = 0;} 
		if(t>8) {t = 8 ;}
		if(y<0) {y = 0;}

//save pieces
		miniMaxStack.push(board[a][b]);
		miniMaxStack.push(board[n][b]);
		miniMaxStack.push(board[m][b]);
		miniMaxStack.push(board[a][y]);
		miniMaxStack.push(board[a][t]);
		miniMaxStack.push(board[m][t]);
		miniMaxStack.push(board[n][t]);
		miniMaxStack.push(board[m][y]);
		miniMaxStack.push(board[n][y]);		
		
//perform explode
	board[a][b] = 0;
	board[m][b] = 0;
	board[n][b] = 0;
	board[a][y] = 0;
	board[a][t] = 0;
	board[m][t] = 0;
	board[n][t] = 0;
	board[m][y] = 0;
	board[n][y] = 0;
	
	//make win
		
	aiCurrentScore = evaluate();
	
	if (check4winner() == 10000) {
		System.out.print("This move kills your king lol: = "+a+" = "+b+" = "+c+" = "+d);
		winMove = true;
		
		board[n][y] = (int) miniMaxStack.pop();
		board[m][y] = (int) miniMaxStack.pop();
		board[n][t] = (int) miniMaxStack.pop();
		board[m][t] = (int) miniMaxStack.pop();
		board[a][t] = (int) miniMaxStack.pop();
		board[a][y] = (int) miniMaxStack.pop();
		board[m][b] = (int) miniMaxStack.pop();
		board[n][b] = (int) miniMaxStack.pop();
		board[a][b] = (int) miniMaxStack.pop();
		
		moveWScore[i][0] = a;
		moveWScore[i][1] = b;
		moveWScore[i][2] = c;
		moveWScore[i][3] = d;
		moveWScore[i][4] = aiCurrentScore;
		
		aiCurrentScore = 10001;
		break;
	}
	
	moveWScore[i][0] = a;
	moveWScore[i][1] = b;
	moveWScore[i][2] = c;
	moveWScore[i][3] = d;
	moveWScore[i][4] = aiCurrentScore;
	
	//put the move back
	
	board[n][y] = (int) miniMaxStack.pop();
	board[m][y] = (int) miniMaxStack.pop();
	board[n][t] = (int) miniMaxStack.pop();
	board[m][t] = (int) miniMaxStack.pop();
	board[a][t] = (int) miniMaxStack.pop();
	board[a][y] = (int) miniMaxStack.pop();
	board[m][b] = (int) miniMaxStack.pop();
	board[n][b] = (int) miniMaxStack.pop();
	board[a][b] = (int) miniMaxStack.pop();
	
//	printBoard();	
		}
		
		else if(a != c || b != d) {
		prev = board[c][d];
		
		board[c][d] = board[a][b];
		board[a][b] = 0;
		
		aiCurrentScore = evaluate();
		
		if (check4winner() == 10000) {
			System.out.print("Artificial intelligence: Found the move that kills your king\n");
			winMove = true;
			
			moveWScore[i][0] = a;
			moveWScore[i][1] = b;
			moveWScore[i][2] = c;
			moveWScore[i][3] = d;
			moveWScore[i][4] = aiCurrentScore;
			
			board[a][b] = board[c][d];
			board[c][d] = prev;
			
			aiCurrentScore = 100000;
			break;
		}
		
		moveWScore[i][0] = a;
		moveWScore[i][1] = b;
		moveWScore[i][2] = c;
		moveWScore[i][3] = d;
		moveWScore[i][4] = aiCurrentScore;
		
		//put move back
		board[a][b] = board[c][d];
		board[c][d] = prev;
	}
	}//end of for loop

bubbleSort(moveWScore,count);

for(int i = 0;i < count;i++) {
aiCurrentMoves[i][0] = moveWScore[i][0];
aiCurrentMoves[i][1] = moveWScore[i][1];
aiCurrentMoves[i][2] = moveWScore[i][2];
aiCurrentMoves[i][3] = moveWScore[i][3];
}
}//end of sort function

private void bubbleSort(int arr[][], int count) {
	int tempa,tempb,tempc,tempd,tempe;
	if (count == 1) return;
	
	for (int i=0; i<count-1;i++) {
		if(arr[i][4] < arr[i+1][4]) {
			tempa = arr[i][0];	
			tempb = arr[i][1];	
			tempc = arr[i][2];	
			tempd = arr[i][3];
			tempe = arr[i][4];
			
					
			arr[i][0] = arr[i+1][0];
			arr[i][1] = arr[i+1][1];
			arr[i][2] = arr[i+1][2];
			arr[i][3] = arr[i+1][3];
			arr[i][4] = arr[i+1][4];
			
			arr[i+1][0] = tempa;
			arr[i+1][1] = tempb;
			arr[i+1][2] = tempc;
			arr[i+1][3] = tempd;	
			arr[i+1][4] = tempe;	
		}
	}
	
	bubbleSort(arr,count-1);	
}

private void miniMax() {
	AILegalMoves aiMoves = new AILegalMoves(board);
	int[][] aiCurrentMoves = aiMoves.getMoves();
	int count = aiMoves.getCount();
	int alpha = -1;
	int beta = -1;
	int reduced = 0;
	
	//enable or disable sort
	sortMoves(aiCurrentMoves,count);
	
	if(count == 0) { System.out.println("Artificial Intelligence: I have no moves left, haha good game"); }
	s.start();
	
	int [][] bestMoves = new int[1][5];
	
	bestMoves[0][4] = -99999;
	
	System.out.println("\nArtificial Intelligence is thinking .... ");
	
	
	int aiCurrentScore, depth = 1;
	int a,b,c,d,n,m,t,y, prev;
	
	for (int i=0; i<count; i++) {	
		//timer
		if(s.current() >= 4500 && reduced == 0) {
			System.out.println("\nSystem: A.I took too long to think, depth reduced");
			maxdepth = 5;
			reduced = 1;
		}
		
		//incre node
		nodeCount++;
		a = aiCurrentMoves[i][0];	
		b = aiCurrentMoves[i][1];	
		c = aiCurrentMoves[i][2];	
		d = aiCurrentMoves[i][3];	
		
		if( a == c && b == d) {
		//System.out.println("Minimax explode = "+a+" = "+b+" = "+c+" = "+d);	
		n = a+1;
		m = a-1;
		t = b+1;
		y = b-1;

		if(n>6) { n = 6;}
		if(m < 0) {m = 0;} 
		if(t>8) {t = 8 ;}
		if(y<0) {y = 0;}

//save pieces
		miniMaxStack.push(board[a][b]);
		miniMaxStack.push(board[n][b]);
		miniMaxStack.push(board[m][b]);
		miniMaxStack.push(board[a][y]);
		miniMaxStack.push(board[a][t]);
		miniMaxStack.push(board[m][t]);
		miniMaxStack.push(board[n][t]);
		miniMaxStack.push(board[m][y]);
		miniMaxStack.push(board[n][y]);		
		
//perform explode
	board[a][b] = 0;
	board[m][b] = 0;
	board[n][b] = 0;
	board[a][y] = 0;
	board[a][t] = 0;
	board[m][t] = 0;
	board[n][t] = 0;
	board[m][y] = 0;
	board[n][y] = 0;
	
	//make win
	if (check4winner() == 10000) {
		System.out.print("This move kills your king lol: = "+a+" = "+b+" = "+c+" = "+d);
		winMove = true;
		bestMoves[0][0] = a; 	
		bestMoves[0][1] = b;	
		bestMoves[0][2] = c;	
		bestMoves[0][3] = d;	
		
		board[n][y] = (int) miniMaxStack.pop();
		board[m][y] = (int) miniMaxStack.pop();
		board[n][t] = (int) miniMaxStack.pop();
		board[m][t] = (int) miniMaxStack.pop();
		board[a][t] = (int) miniMaxStack.pop();
		board[a][y] = (int) miniMaxStack.pop();
		board[m][b] = (int) miniMaxStack.pop();
		board[n][b] = (int) miniMaxStack.pop();
		board[a][b] = (int) miniMaxStack.pop();
		break;
	}
		
	aiCurrentScore = min(depth+1, alpha, beta);
	
	
	if (aiCurrentScore>bestMoves[0][4]) { 
		bestMoves[0][0] = a; 	
		bestMoves[0][1] = b;	
		bestMoves[0][2] = c;	
		bestMoves[0][3] = d;	
		bestMoves[0][4]=aiCurrentScore; 
	} 
	
	//put the move back
	
	board[n][y] = (int) miniMaxStack.pop();
	board[m][y] = (int) miniMaxStack.pop();
	board[n][t] = (int) miniMaxStack.pop();
	board[m][t] = (int) miniMaxStack.pop();
	board[a][t] = (int) miniMaxStack.pop();
	board[a][y] = (int) miniMaxStack.pop();
	board[m][b] = (int) miniMaxStack.pop();
	board[n][b] = (int) miniMaxStack.pop();
	board[a][b] = (int) miniMaxStack.pop();
	
//	printBoard();	
		}
		
		else if(a != c || b != d) {
		prev = board[c][d];
		
		board[c][d] = board[a][b];
		board[a][b] = 0;
		
		//make win
		if (check4winner() == 10000) {
			winMove = true;
			bestMoves[0][0] = a; 	
			bestMoves[0][1] = b;	
			bestMoves[0][2] = c;	
			bestMoves[0][3] = d;
			
			board[a][b] = board[c][d];
			board[c][d] = prev;
			
			break;
		}
		
		aiCurrentScore = min(depth+1, alpha, beta);
		//what is this doing?
		if (aiCurrentScore>bestMoves[0][4]) { 
			bestMoves[0][0] = a; 	
			bestMoves[0][1] = b;	
			bestMoves[0][2] = c;	
			bestMoves[0][3] = d;	
			bestMoves[0][4]=aiCurrentScore; 
		}
		
		//put move back
		board[a][b] = board[c][d];
		board[c][d] = prev;
	}
	}//end of for loop
	makeMove(bestMoves);
	maxdepth = 6;
	}


private void makeMove(int[][] moves) {
	int a,b,c,d,n,m,t,y;
	char c1,r1,c2,r2;
	
	a = moves[0][0];	
	b = moves[0][1];	
	c = moves[0][2];	
	d = moves[0][3];	
	
	c1 = (char) (a + 'a');
	r1 = (char) (b + '1');
	c2 = (char) (c + 'a');
	r2 = (char) (d + '1');
	s.stop();
	time = s.getDuration();
	System.out.println("\nTime elapsed: "+TimeUnit.MILLISECONDS.toSeconds(time) +" seconds");
	
	System.out.print("I made this move: = ");
	System.out.print(c1);
	System.out.print(r1);
	System.out.print(c2);
	System.out.print(r2 +" (");

	System.out.print(convertColumn(c1));
	System.out.print(convertRow(r1));
	System.out.print(convertColumn(c2));
	System.out.print(convertRow(r2) + ")");
	
	System.out.println("\nDepth = "+ maxdepth);
	System.out.println("Node Visited =" + nodeCount);
	nodeCount = 0;
	if(a == c && b == d) {	
		
		n = a+1;
		m = a-1;
		t = b+1;
		y = b-1;

		if(n>6) {n = 6;}
		if(m < 0) {m = 0;} 
		if(t>8) {t = 8 ;}
		if(y<0) {y = 0;}

	board[a][b] = 0;
	board[m][b] = 0;
	board[n][b] = 0;
	board[a][y] = 0;
	board[a][t] = 0;
	board[m][t] = 0;
	board[n][t] = 0;
	board[m][y] = 0;
	board[n][y] = 0;
	
	System.out.println("\nA.I: Boom. ");
	}
	board[c][d]=board[a][b];   
	board[a][b]=0;

	printBoard();
}

private int evaluate() { 
	int value = 0;
	for (int i=0; i<7; i++) {
		for (int j=0; j<9; j++) {
			value += board[i][j];
		}
	}
	return value;
}

private char convertRow(char r) {
	char r2 = 0;
	
	switch (r) {
	case '1': r2='9'; break;
	case '2': r2='8'; break;
	case '3': r2='7'; break;
	case '4': r2='6'; break;
	case '5': r2='5'; break;
	case '6': r2='4'; break;
	case '7': r2='3'; break;
	case '8': r2='2'; break;
	case '9': r2='1'; break;
	}
	return r2;
}

private char convertColumn(char c) {
	char c2 = 0;
	
	switch (c) {
	case 'a': c2='g'; break;
	case 'b': c2='f'; break;
	case 'c': c2='e'; break;
	case 'd': c2='d'; break;
	case 'e': c2='c'; break;
	case 'f': c2='b'; break;
	case 'g': c2='a'; break;
	}
	return c2;
}


}
