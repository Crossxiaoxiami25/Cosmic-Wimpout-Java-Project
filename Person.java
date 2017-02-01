//Xia Lin(110732381)
package cosmic.wimpout;

public class Person {
    private int finalScore=0;
    private boolean isEnterGame=false;
    public boolean setFinalScore(int fs){
        if(fs>=35)
            isEnterGame=true;
        if(isEnterGame)
            finalScore+=fs;
        return isEnterGame;
    }
    public int getFinalScore(){
        return finalScore;
    }
    
}
