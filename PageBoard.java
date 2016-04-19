/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamone.operatingsystem.virtualmemory;
import com.teamone.operatingsystem.processmanagement.Process;
import com.teamone.operatingsystem.VirtualMemory2.HDDProvider;

/**
 *
 * @author Szymon
 */

public class PageBoard
{
    public int NameOfBoard;
    public Page[] PageBoardForProcess;
    
    public PageBoard(Process PCB, int SizeOfProgram)
    {
        NameOfBoard = PCB.processID;
        PageBoardForProcess = new Page[SizeOfProgram]; //Utworzenie wystarczająco dużej tablicy stonnic
        for(int i = 0; i < SizeOfProgram; i++)
        {
            PageBoardForProcess[i] = new Page();
        }
    } 
}
