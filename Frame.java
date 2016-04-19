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

public class Frame
{
    char[] Code; // 32 znaki kodu programów
    int Stamp; //zwiekszany za kazdym razem gdy zostanie wykozystane funkcja zwiazana z ramka
    
    Frame()
    {
        Code = new char[32];
        Stamp = 0;
    }
     
    public void setStamp()
    {
        Stamp = StampClass.Stamp;
    }
    
    public void setCode (String CodePage)
    {
        char[] tabChar = CodePage.toCharArray();
        
        int CodePageSize = CodePage.length();
        
        for (int i = 0; i < 32 && CodePageSize > 0 ; i++)
        {
            Code[i] = tabChar[i];
            CodePageSize--;
        } 
        setStamp();//użyłem ramki, czyli aktualizacja stampa
        StampClass.Stamp++;
    }
    
    String getCode(int fiz_address, int size)
    {
        String _Code = "";
        
            for (int i = fiz_address, j = 0; i < 32 && j < size; i++, j++)
            {
                _Code = _Code + Code[i];
            }
            
        setStamp();//uzyłem ramki, czyli aktualizacja stampa
        StampClass.Stamp++;
 
        return _Code;
    }
    
    public String getCodeForLogs()
    {
        String LogCode = "|";
        
        for (int i = 0; i < 32; i++)
            LogCode = LogCode + Code[i] + "|";
        
        return LogCode;
    }   
}
