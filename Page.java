/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamone.operatingsystem.virtualmemory;

/**
 *
 * @author Szymon
 */
public class Page {
    
    public int WhichFrame;
    public boolean Valid; //0 invalid - nie ma tej stronnicy w ramce, 1 valid - stronnica jest w ramce
    
    Page()
    {
        WhichFrame = -1;
        Valid = false;
    }
    
    void SetFrame(int FrameNr)
    {
        WhichFrame = FrameNr;
        SetValid(true);
    }
    
    void SetValid (boolean NewValid)
    {
        Valid = NewValid;
    }
    

}
