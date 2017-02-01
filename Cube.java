//Xia Lin(110732381)
package cosmic.wimpout;

public class Cube {
    private int valueOfRoll;
    public Cube(){
        roll();
    }
    public void roll(){
        valueOfRoll =(int)(Math.random()*6+1);
        
        if(valueOfRoll==1)
            valueOfRoll=10;
    }
    public int value(){
        return valueOfRoll;
    }
    public void setValue(int v){
        valueOfRoll=v;
    }
    public String toString(){
        return ""+valueOfRoll;
    }
}

