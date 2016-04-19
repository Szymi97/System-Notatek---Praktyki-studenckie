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
public class SwapFileLastPage 
{
   public int WhichProgram; // 1, 2 ,3 lub 4
   public int WhichPage; // 0, 1, 2 ... n    n-ta stonnica programu
   
   public SwapFileLastPage()
   { 
        WhichPage = -1; // nic nie sprowadzono, tylko na poczatku bo potem zawsze cos bedzie
        WhichProgram = 0; // brak nazwy programu
   }
}
