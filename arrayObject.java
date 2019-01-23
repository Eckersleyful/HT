/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ilmari
 */
public class arrayObject {
    int foundCounter;
    int A_Line;
    boolean foundFromA;
    boolean foundFromB;
    int fromFile;
    int value;
    
    public arrayObject(int value){
        this.value = value;
        this.foundCounter = 1;
    }
    public void incrementCounter(){
        foundCounter++;
    }
    public int getCounter(){
        return foundCounter;
    }
    
    
    
}
