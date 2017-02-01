//Xia Lin(110732381)
package cosmic.wimpout;

public class SunCube extends Cube{
    public SunCube(){
        super.roll();   
    }
    public int value(){
        return super.value();
    }
    public String toString(){
        if(super.value()==0)
            return "*";
        else
            return ""+super.value();
    }
}
