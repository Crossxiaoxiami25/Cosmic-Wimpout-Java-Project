//Xia Lin(110732381)
package cosmic.wimpout;
import java.util.*;
public class DiceSet{
    private ArrayList<Cube> dice = new ArrayList<Cube>();
    private ArrayList<Cube> usedDice = new ArrayList<Cube>();
    private int setTotal=0;
    private int currentScore=0;
    private int valueOfRepeat=0;
    private String s="";
    private boolean mustRollAgain=false;
    private boolean freightTrain=false;
    private boolean flash=false;
    private boolean wimpedOut=true;
    private boolean fiveOrTen=false;
    
    public DiceSet(){
        reset();
    }
    public void setTotal(int st){
        setTotal=st;
    }
    public void setCurrentScore(int cs){
        currentScore=cs;
    }
    public void setWimpedOut(boolean b){
        wimpedOut=b;
    }
    public void setFiveOrTen(boolean b){
        fiveOrTen=b;
    }
    public int getCurrentScore(){
        return currentScore;
    }
    public boolean getWimpedOut(){
        return wimpedOut;
    }
    public boolean getFreightTrain(){
        return freightTrain;
    }
    public boolean getFlash(){
        return flash;
    }
    public boolean getMustRollAgain(){
        return mustRollAgain;   
    }
    public boolean getFiveOrTen(){
        return fiveOrTen;
    }
    public int getValueOfRepeat(){
        return valueOfRepeat;
    }
    public String getString(){
        return s;
    }
    public ArrayList<Cube> getCube(){
        return dice;
    }
    public void reset(){
        dice.clear();
        for(int i=0;i<4;i++){
            dice.add(new Cube());
        }
        dice.add(new SunCube());
        valueOfRepeat=0;
        mustRollAgain=false;
        freightTrain=false;
        flash=false;
        wimpedOut=true;
        fiveOrTen=false;
    }
    public void roll(){
        for(int i=0;i<dice.size();i++){
            dice.get(i).roll();
        }
        if(dice.size()==5){
            if(dice.get(dice.size()-1).value()==3)
                dice.get(dice.size()-1).setValue(0);
        }
        s=toString();
        wimpedOut=true;
        fiveOrTen=false;
        flash=false;
        deterAndEvalu();
    }
    public int diceLeft(){
        return dice.size();
    }
    public String toString(){
        String s ="";
        for(int i=0;i<dice.size();i++){
            s+=dice.get(i).toString()+" ";
        }
        return s;
    }
    public void deterAndEvalu(){
        int count=0,indexOfMid=0,indexOfJ=0;
        for(int i=0;i<dice.size()-2;i++){
            for(int j=1+i;j<dice.size();j++){
                if(j==4&&dice.get(j).value()==0&&count==1){
                    indexOfJ=4;
                    count++;
                }
                else if(j==4&&dice.get(j).value()==0&&count==3&&(valueOfRepeat==5||valueOfRepeat==10))
                    count++;
                else if(dice.get(i).value()==dice.get(j).value()){
                    if(indexOfMid!=0)
                        indexOfJ=j;
                    if(indexOfMid==0)
                        indexOfMid=j;
                    valueOfRepeat=dice.get(i).value();
                    count++;
                }
                if(i!=0&&count==2){
                    break;
                }
            }
            if(count==4){
                freightTrain=true;
                wimpedOut=false;
                for(int f=dice.size()-1;f>=0;f--){
                    usedDice.add(dice.get(f));
                    dice.remove(f);
                }
                currentScore+=valueOfRepeat*100;
                break;
            }
            else if(count==2){
                flash=true;
                wimpedOut=false;
                usedDice.add(dice.get(i));
                usedDice.add(dice.get(indexOfMid));
                usedDice.add(dice.get(indexOfJ));
                dice.remove(indexOfJ);
                dice.remove(indexOfMid);
                dice.remove(i);
                currentScore+=valueOfRepeat*10;
                break;
            }
            else{
                count=0;
                indexOfMid=0;
            }
        }
        for(int i=dice.size()-1;i>=0;i--){
            if(dice.get(i).value()==5||dice.get(i).value()==10){
                currentScore+=dice.get(i).value();
                usedDice.add(dice.get(i));
                dice.remove(i);
                fiveOrTen=true;
                wimpedOut=false;
            }
        }
        if(dice.size()>0){
            if(dice.get(dice.size()-1).value()==0){
                currentScore+=10;
                usedDice.add(dice.get(dice.size()-1));
                dice.remove(dice.size()-1);
                fiveOrTen=true;
                wimpedOut=false;
            }
        }
        if(dice.size()==0)
            mustRollAgain=true;
    }
}