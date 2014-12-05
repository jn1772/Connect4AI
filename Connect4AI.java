package connect4ai;

import java.util.Scanner;

class Board{
    byte[][] board = new byte[7][7];
    
    public Board(){
        board = new byte[][]{
            {1,0,0,0,0,0,0,},
            {2,0,0,0,0,0,0,},
            {2,0,0,0,0,0,0,},
            {2,0,0,1,0,0,0,},
            {2,0,0,2,1,0,0,},
            {2,0,0,2,2,0,0,},
            {2,0,0,2,2,0,1,},            
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
        //Game draw!
        return 0;
    }
    
    int calculateScore(int aiScore, int moreMoves){ 
        System.out.println("More moves required = "+moreMoves);
        int moveScore = 4 - moreMoves;
        if(aiScore==0)return 0;
        else if(aiScore==1)return 1*moveScore;
        else if(aiScore==2)return 10*moveScore;
        else if(aiScore==3)return 100*moveScore;
        else return 1000;
    }
    
    //Evaluate board favorableness for AI
    public int evaluateBoard(Board b){
      
        int aiScore=1;
        int score=0;
        int blanks = 0;
        int k=0, moreMoves=0;
        for(int i=6;i>=0;--i){
            for(int j=0;j<=6;++j){
                
                if(b.board[i][j]==0 || b.board[i][j]==2) continue;
                
                System.out.println("Right");
                
                if(j<=3){ 
                    for(k=1;k<4;++k){
                        if(b.board[i][j+k]==1)aiScore++;
                        else if(b.board[i][j+k]==2){aiScore=0;blanks = 0;break;}
                        else blanks++;
                    }
                    
//                  System.out.println("AIScore = "+aiScore+" blanks = "+blanks+" k = "+k);
                    moreMoves = 0; 
                    if(blanks>0) 
                        for(int c=1;c<4;++c){
                            int column = j+c;
                            for(int m=i; m<= 6;m++){
                             if(b.board[m][column]==0)moreMoves++;
                                else break;
                            } 
                        } 
                    
                    if(moreMoves!=0) score += calculateScore(aiScore, moreMoves);
                    aiScore=1;   
                }
                
                System.out.println("Up");
                
                if(i>=3){
                    for(k=1;k<4;++k){
                        if(b.board[i-k][j]==1)aiScore++;
                        else if(b.board[i-k][j]==2){aiScore=0;break;} 
                    }
//                    System.out.println("AIScore = "+aiScore+" k = "+k);
                    moreMoves = 0; 
                    
                    if(aiScore>0){
                        int column = j;
                        for(int m=i-k+1; m<=i-1;m++){
                         if(b.board[m][column]==0)moreMoves++;
                            else break;
                        }  
                    }
                    if(moreMoves!=0) score += calculateScore(aiScore, moreMoves);
                    aiScore=1;  
                }
                
                System.out.println("Left");
                if(j>=3){
                    for(k=1;k<4;++k){
                        if(b.board[i][j-k]==1)aiScore++;
                        else if(b.board[i][j-k]==2){aiScore=0; blanks=0;break;}
                        else blanks++;
                    }
                    moreMoves=0;
                    if(blanks>0) 
                        for(int c=1;c<4;++c){
                            int column = j- c;
                            for(int m=i; m<= 6;m++){
                             if(b.board[m][column]==0)moreMoves++;
                                else break;
                            } 
                        } 
                    
                    if(moreMoves!=0) score += calculateScore(aiScore, moreMoves);
                    aiScore=1; 
                }
                
                System.out.println("Top-right");
                //Top-right diagonal
                if(j<=3 && i>=3){
                    for(k=1;k<4;++k){
                        if(b.board[i-k][j+k]==1)aiScore++;
                        else if(b.board[i-k][j+k]==2){aiScore=0;blanks=0;break;}
                        else blanks++;                        
                    }
                    moreMoves=0;
                    if(blanks>0){
                        for(int c=1;c<4;++c){
                            int column = j+c, row = i-c;
                            for(int m=row;m<=6;++m){
                                if(b.board[m][column]==0)moreMoves++;
                                else if(b.board[m][column]==1);
                                else break;
                            }
                        } 
                        if(moreMoves!=0) score += calculateScore(aiScore, moreMoves);
                        aiScore=1;
                    }
                }
                
                System.out.println("Top-left");
                if(i>=3 && j>=3){
                    for(k=1;k<4;++k){
                        if(b.board[i-k][j-k]==1)aiScore++;
                        else if(b.board[i-k][j-k]==2){aiScore=0;blanks=0;break;}
                        else blanks++;                        
                    }
                    moreMoves=0;
                    if(blanks>0){
                        for(int c=1;c<4;++c){
                            int column = j-c, row = i-c;
                            for(int m=row;m<=6;++m){
                                if(b.board[m][column]==0)moreMoves++;
                                else if(b.board[m][column]==1);
                                else break;
                            }
                        } 
                        if(moreMoves!=0) score += calculateScore(aiScore, moreMoves);
                        aiScore=1;
                    }
                } 
            }
        }
        return score;
    }
    
    public static void main(String[] args) {
        Board b = new Board();
        Connect4AI ai = new Connect4AI(b);
        
//        for(int i=0;i<49;++i){
//            ai.letOpponentMove();
            ai.displayBoard(b);
            System.out.println("Result = "+ai.gameResult(b)+", Score = "+ai.evaluateBoard(b));
//        }
    }
}

