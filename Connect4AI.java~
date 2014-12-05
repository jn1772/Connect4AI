package connect4ai;

import java.util.Scanner;

class Board{
    byte[][] board = new byte[7][7];
    
    public Board(){
        board = new byte[][]{
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
        };
    } 
}

public class Connect4AI { 
    private Board b;
    private Scanner scan;
    
    public Connect4AI(Board b){
        this.b = b;
        scan = new Scanner(System.in);
    }
    
    //Placing a Move on the board
    public boolean placeMove(int column, int player){
        if(!isLegalMove(column)) {System.out.println("Illegal move!"); return false;}
        for(int i=6;i>=0;--i){
            if(b.board[i][column] == 0) {
                b.board[i][column] = (byte)player;
                return true;
            }
        }
        return false;
    }
    
    //Opponent's turn
    public boolean isLegalMove(int column){
        return b.board[0][column]==0;
    }
    
    public void letOpponentMove(){
        System.out.println("Your move (1-7): ");
        int move = scan.nextInt();
        while(move<1 || move > 7 || !isLegalMove(move-1)){
            System.out.println("Invalid move.\n\nYour move (1-7): "); 
            move = scan.nextInt();
        }
        
        //Assume 2 is the opponent
        placeMove(move-1, (byte)2); 
    }
    
    //Printing the board
    public void displayBoard(Board b){
        System.out.println();
        for(int i=0;i<=6;++i){
            for(int j=0;j<=6;++j){
                System.out.print(b.board[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    //Game Result
    public int gameResult(Board b){
        int aiScore = 0, humanScore = 0;
        for(int i=6;i>=0;--i){
            for(int j=0;j<=6;++j){
                if(b.board[i][j]==0) continue;
                
                //Checking cells to the right
                if(j<=3){
                    for(int k=0;k<4;++k){ 
                            if(b.board[i][j+k]==1) aiScore++;
                            else if(b.board[i][j+k]==2) humanScore++;
                            else break; 
                    }
                    if(aiScore==4)return 1; else if (humanScore==4)return 2;
                    aiScore = 0; humanScore = 0;
                } 
                
                //Checking cells up
                if(i>=3){
                    for(int k=0;k<4;++k){
                            if(b.board[i-k][j]==1) aiScore++;
                            else if(b.board[i-k][j]==2) humanScore++;
                            else break;
                    }
                    if(aiScore==4)return 1; else if (humanScore==4)return 2;
                    aiScore = 0; humanScore = 0;
                } 
                
                //Checking diagonal up-right
                if(j<=3 && i>= 3){
                    for(int k=0;k<4;++k){
                        if(b.board[i-k][j+k]==1) aiScore++;
                        else if(b.board[i-k][j+k]==2) humanScore++;
                        else break;
                    }
                    if(aiScore==4)return 1; else if (humanScore==4)return 2;
                    aiScore = 0; humanScore = 0;
                }
                
                //Checking diagonal up-left
                if(j>=3 && i>=3){
                    for(int k=0;k<4;++k){
                        if(b.board[i-k][j-k]==1) aiScore++;
                        else if(b.board[i-k][j-k]==2) humanScore++;
                        else break;
                    }
                    if(aiScore==4)return 1; else if (humanScore==4)return 2;
                    aiScore = 0; humanScore = 0;
                }  
            }
        }
        
        for(int i=0;i<7;++i){
            //Game has not ended yet
            if(b.board[0][i]==0)return -1;
        }
        //Game has been drawn!
        return 0;
    }
    
    public static void main(String[] args) {
        Board b = new Board();
        Connect4AI ai = new Connect4AI(b);
        
        for(int i=0;i<49;++i){
            ai.letOpponentMove();
            ai.displayBoard(b);
            System.out.println("Result = "+ai.gameResult(b));
        }
    }
}

