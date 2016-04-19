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
public class FrameBoard
{
    public Frame[] FrameBoard;
    
    FrameBoard()
    {  
        FrameBoard = new Frame[16];
        for(int i = 0; i < 16; i++)
        {
            FrameBoard[i] = new Frame();
            FrameBoard[i].Stamp = 0;
        }
    }
    
    int LRU_Stamp()
    {
        int MinStamp = FrameBoard[0].Stamp; 
        int FrameNumber = 0; 
        
        int i = 0;
        for (; i < 16; i++) //wyszkanie najmniejszego stampa
        {
            if (FrameBoard[i].Stamp == 0)
            {
                FrameNumber = i;
                break;
            }
            
            if (MinStamp > FrameBoard[i].Stamp)
            {
                MinStamp = FrameBoard[i].Stamp;
                FrameNumber = i; //znalezienie numeru ramki dla najmniejszego stampa
            }
        }
        return FrameNumber;
    }    
}