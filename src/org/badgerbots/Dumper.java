/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.badgerbots;
import edu.wpi.first.wpilibj.*;
import org.badgerbots.lib.*;

/**
 *
 * @author Badge
 */

public class Dumper {
    
    public Dumper(Pot pLow, Pot pHigh, SpeedController sLow, SpeedController  sHigh)
    {
        this.pLow = pLow;
        this.pHigh = pHigh;
    }
    
  public void LowSlotLoad()
    {
        double lowdiff = pLow.get() - POSITIONS [0][0];
        double highdiff = pHigh.get() - POSITIONS [0][1];
        
        /*math*/
        
        sLow.set(0);
        sHigh.set(0);
    }
    

        public void LowGoalDump()
    {
        double lowdiff = pLow.get() - POSITIONS [1][0];
        double highdiff = pHigh.get() - POSITIONS [1][1];
      
        
        /*math*/
    
        sLow.set(0);
        sHigh.set(0);
    }
        
          public void PyramidDump()
    {
        double lowdiff = pLow.get() - POSITIONS [2][0];
        double highdiff = pHigh.get() - POSITIONS [2][1];
        
        /*math*/
        
        sLow.set(0);
        sHigh.set(0);
    }
          
            public void MiddleSlotLoad()
    {
        double lowdiff = pLow.get() - POSITIONS [3][0];
        double highdiff = pHigh.get() - POSITIONS [3][1];
        
        /*math*/
        
        sLow.set(0);
        sHigh.set(0);
    }
            
              public void FloorDump()
              {
        double lowdiff = pLow.get() - POSITIONS [4][0];
        double highdiff = pHigh.get() - POSITIONS [4][1];
        
        /*math*/
        
        sLow.set(0);
        sHigh.set(0);
    }
        
                public void PyramidClimb()
    {
        double lowdiff = pLow.get() - POSITIONS [5][0];
        double highdiff = pHigh.get() - POSITIONS [5][1];
        
        /*math*/
        
        sLow.set(0);
        sHigh.set(0);
    }
              
                  public void ResetOrStop()
    {
        double lowdiff = pLow.get() - POSITIONS [6][0];
        double highdiff = pHigh.get() - POSITIONS [6][1];
        
        /*math*/
        
        sLow.set(0);
        sHigh.set(0);
    }
  
        Pot pLow;
        Pot pHigh;
        
        SpeedController sLow;
        SpeedController sHigh;

    private final double [][] POSITIONS = new double [][] {{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}};

}