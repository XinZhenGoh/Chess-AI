package chess;
import chess.Newcular;

public class PlayerLegalMoves {
	int legalMoveArray[][] = new int[55][4];
	int board[][];
	int count = 0;
	
	public PlayerLegalMoves(int[][] n) {
		board = n;
		for(int j=8;j>=0;j--) {
			for(int i=0;i<7;i++) {
				switch (board[i][j]) {
				case -(Newcular.p): playerPawnMoves(i,j);break;
				case -(Newcular.r): playerRookMoves(i,j);break;
				case -(Newcular.b): playerBishopMoves(i,j);break;
				case -(Newcular.n):playerKnightMoves(i,j);break;
				case -(Newcular.k):playerKingMoves(i,j);break;
				}
		}	
		}	
	}

	public int[][] getMoves() {
		return legalMoveArray;
	}
	
	public int getCount() {
		return count;
	}
	
	private void playerPawnMoves(int c,int r) {
		//explosion move
		legalMoveArray[count][0] = c;
		legalMoveArray[count][1] = r;
		legalMoveArray[count][2] = c;
		legalMoveArray[count][3] = r;
		count++;
		
		
		if(r<8) {
			if(board[c][r+1] == 0) {
				legalMoveArray[count][0] = c;
				legalMoveArray[count][1] = r;
				legalMoveArray[count][2] = c;
				legalMoveArray[count][3] = r+1;
				count++;	
			}
			if (c>0) {
				if (board[c-1][r+1] > 0) {
					legalMoveArray[count][0]=c;
					legalMoveArray[count][1]=r;
					legalMoveArray[count][2]=c-1;
					legalMoveArray[count][3]=r+1;
					count++;
				}
			}
			if (c<6) {	
				if (board[c+1][r+1] > 0) {
					legalMoveArray[count][0]=c;
					legalMoveArray[count][1]=r;
					legalMoveArray[count][2]=c+1;
					legalMoveArray[count][3]=r+1;
					count++;
				}		
		}	
	}
	}
	
	
	
private void playerRookMoves(int c, int r) {
//explosion	
		legalMoveArray[count][0] = c;
		legalMoveArray[count][1] = r;
		legalMoveArray[count][2] = c;
		legalMoveArray[count][3] = r;
		count++;
		
int c1 = c, r1 = r;

while(r<8) {
	r++;
	//move one step infront
	if(board[c1][r] == 0) {
		legalMoveArray[count][0] = c1;
		legalMoveArray[count][1] = r1;
		legalMoveArray[count][2] = c1;
		legalMoveArray[count][3] = r;
		count++;	
	}
	
	else if(board[c1][r] > 0) {
		legalMoveArray[count][0] = c1;
		legalMoveArray[count][1] = r1;
		legalMoveArray[count][2] = c1;
		legalMoveArray[count][3] = r;
		count++;	
		//captures, cant go further, exit out
		break;
	}
	else { break; }
}

c = c1;
r = r1;

while(c<6) {
	c++;
	//move one step to the right
	if(board[c][r1] == 0) {
		legalMoveArray[count][0] = c1;
		legalMoveArray[count][1] = r1;
		legalMoveArray[count][2] = c;
		legalMoveArray[count][3] = r1;
		count++;	
	}
	
	else if(board[c][r1] > 0) {
		legalMoveArray[count][0] = c1;
		legalMoveArray[count][1] = r1;
		legalMoveArray[count][2] = c;
		legalMoveArray[count][3] = r1;
		count++;	
		//captures, cant go further, exit out
		break;
	}
	else { break; }
}

c = c1;
r = r1;

while(c>0) {
	c--;
	//move one step to the left
	if(board[c][r1] == 0) {
		legalMoveArray[count][0] = c1;
		legalMoveArray[count][1] = r1;
		legalMoveArray[count][2] = c;
		legalMoveArray[count][3] = r1;
		count++;	
	}
	
	else if(board[c][r1] > 0) {
		legalMoveArray[count][0] = c1;
		legalMoveArray[count][1] = r1;
		legalMoveArray[count][2] = c;
		legalMoveArray[count][3] = r1;
		count++;	
		//captures, cant go further, exit out
		break;
	}
	else { break; }
}

c = c1;
r = r1;

//move back action
while(r>0) {
	r--;
	//move one step backward
	if(board[c1][r] == 0) {
	}
	
	else if(board[c1][r] > 0) {
		legalMoveArray[count][0] = c1;
		legalMoveArray[count][1] = r1;
		legalMoveArray[count][2] = c1;
		legalMoveArray[count][3] = r;
		count++;	
		//captures, cant go further, exit out
		break;
	}
	else { break; }
}
//end of playerRookAction		
	}
	
private void playerBishopMoves(int c, int r){
		
//explosion	
legalMoveArray[count][0] = c;
legalMoveArray[count][1] = r;
legalMoveArray[count][2] = c;
legalMoveArray[count][3] = r;
count++;
//c1,r1 = original positions
int c1 = c, r1 = r;		
while(r<8 && c>0) {
	c--;
	r++;
	//move diagonally to the top left square
	if(board[c][r] == 0) {
		legalMoveArray[count][0] = c1;
		legalMoveArray[count][1] = r1;
		legalMoveArray[count][2] = c;
		legalMoveArray[count][3] = r;
		count++;	
	}
	
	else if(board[c][r] > 0) {
		legalMoveArray[count][0] = c1;
		legalMoveArray[count][1] = r1;
		legalMoveArray[count][2] = c;
		legalMoveArray[count][3] = r;
		count++;	
		//captures, cant go further, exit out
		break;
	}
	
	else { break;}	
}

c = c1;
r = r1;
	
while(r<8 && c<6) {
	c++;
	r++;
	//move diagonally to the top right square
	if(board[c][r] == 0) {
		legalMoveArray[count][0] = c1;
		legalMoveArray[count][1] = r1;
		legalMoveArray[count][2] = c;
		legalMoveArray[count][3] = r;
		count++;	
	}
	
	else if(board[c][r] > 0) {
		legalMoveArray[count][0] = c1;
		legalMoveArray[count][1] = r1;
		legalMoveArray[count][2] = c;
		legalMoveArray[count][3] = r;
		count++;	
		//captures, cant go further, exit out
		break;
	}
	
	else { break;}	
}

c = c1;
r = r1;
	
//bishop move back left & right
while(r>0 && c>0) {
	c--;
	r--;
	//move diagonally to the bottom left square
	if(board[c][r] == 0) {
	
	}
	
	else if(board[c][r] > 0) {
		legalMoveArray[count][0] = c1;
		legalMoveArray[count][1] = r1;
		legalMoveArray[count][2] = c;
		legalMoveArray[count][3] = r;
		count++;	
		//captures, cant go further, exit out
		break;
	}
	
	else { break;}		
}

c = c1;
r = r1;
	
while(r>0 && c<6) {
	c++;
	r--;
	//move diagonally to the bottom right square
	if(board[c][r] == 0) {
	
	}
	
	else if(board[c][r] > 0) {
		legalMoveArray[count][0] = c1;
		legalMoveArray[count][1] = r1;
		legalMoveArray[count][2] = c;
		legalMoveArray[count][3] = r;
		count++;	
		//captures, cant go further, exit out
		break;
	}
	
	else { break;}					
}
	}
	

	private void playerKingMoves(int c, int r) {
		if(r<8) {
			//capture forward
			if(board[c][r+1] >= 0) {
				legalMoveArray[count][0] = c;
				legalMoveArray[count][1] = r;
				legalMoveArray[count][2] = c;
				legalMoveArray[count][3] = r+1;
				count++;	
			}
			if (c>0) {
				//capture diagonally left
				if (board[c-1][r+1] >= 0) {
					legalMoveArray[count][0]=c;
					legalMoveArray[count][1]=r;
					legalMoveArray[count][2]=c-1;
					legalMoveArray[count][3]=r+1;
					count++;
				}
			}
			if (c<6) {	
				//capture diagonally right
				if (board[c+1][r+1] >= 0) {
					legalMoveArray[count][0]=c;
					legalMoveArray[count][1]=r;
					legalMoveArray[count][2]=c+1;
					legalMoveArray[count][3]=r+1;
					count++;
				}		
		}	
	}		
	}
	
private void playerKnightMoves(int c, int r) {
	//explosion	
	legalMoveArray[count][0] = c;
	legalMoveArray[count][1] = r;
	legalMoveArray[count][2] = c;
	legalMoveArray[count][3] = r;
	count++;
	
	int c1 = c, r1 = r;
	
	//move front right
	if (c<6 && r<7) {
if(board[c+1][r+2] >= 0) {
	legalMoveArray[count][0] = c;
	legalMoveArray[count][1] = r;
	legalMoveArray[count][2] = c+1;
	legalMoveArray[count][3] = r+2;
	count++;
		}
	}

	//move right up
if(c<5 && r<8) {
if(board[c+2][r+1] >= 0) {
	legalMoveArray[count][0] = c;
	legalMoveArray[count][1] = r;
	legalMoveArray[count][2] = c+2;
	legalMoveArray[count][3] = r+1;	
	count++;
		}
}


//move left up
if(c>1 && r<8) {
if(board[c-2][r+1] >= 0) {
	legalMoveArray[count][0] = c;
	legalMoveArray[count][1] = r;
	legalMoveArray[count][2] = c-2;
	legalMoveArray[count][3] = r+1;	
	count++;
}
}

//move front left
if(c>0 && r<7) {
if(board[c-1][r+2] >= 0) {
	legalMoveArray[count][0] = c;
	legalMoveArray[count][1] = r;
	legalMoveArray[count][2] = c-1;
	legalMoveArray[count][3] = r+2;
	count++;
}
}

//backwards movement
if(c>0 && r>1) {
if(board[c-1][r-2] > 0) {
	legalMoveArray[count][0] = c;
	legalMoveArray[count][1] = r;
	legalMoveArray[count][2] = c-1;
	legalMoveArray[count][3] = r-2;	
	count++;	
}
}

if(c<5 && r>1) {
if(board[c+1][r-2] > 0) {
	legalMoveArray[count][0] = c;
	legalMoveArray[count][1] = r;
	legalMoveArray[count][2] = c+1;
	legalMoveArray[count][3] = r-2;	
	count++;
}
}

if(c<5 && r>0) {
if(board[c+2][r-1] > 0) {
	legalMoveArray[count][0] = c;
	legalMoveArray[count][1] = r;
	legalMoveArray[count][2] = c+2;
	legalMoveArray[count][3] = r-1;
	count++;
}
}

if(c>1 && r>0) {
if(board[c-2][r-1] > 0) {
	legalMoveArray[count][0] = c;
	legalMoveArray[count][1] = r;
	legalMoveArray[count][2] = c-2;
	legalMoveArray[count][3] = r-1;
	count++;
}
}
	}//close playerKnightMove
}

