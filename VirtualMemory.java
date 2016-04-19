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
import com.teamone.operatingsystem.interpreter.MemoryException;
import com.teamone.operatingsystem.VirtualMemory2.HDDProvider;
import com.teamone.operatingsystem.processmanagement.Process;
import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VirtualMemory
        implements com.teamone.operatingsystem.filesmanagement.VMProvider,
        com.teamone.operatingsystem.interpreter.VMProvider,
        com.teamone.operatingsystem.processmanagement.MemoryProvider {
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 

    /*Zmienne*/
    private List<PageBoard> ListofPageBoard; //Tablice stronnic przechowywane na liscie charakteryzujacej sie szybkim dodawaniem i usuwaniem za pomoca iteratora 
    private FrameBoard Frame_Board; //pamiec operacyjna, 16 x 32 chary
    File PlikWymiany; //normalnie na dysku, w naszym systemie u nas, zaproponowane przez profesora
    private String LinkToFile; //link do pliku
    HDDProvider HardDisc;
    private SwapFileLastPage WhatIsIn; //dane o ostatnio sprowadzonej stronnicy do pliku wymiany, którego programu i numer stronnicy
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 

    /*Konstruktor*/
    public VirtualMemory() {
        ListofPageBoard = new LinkedList();
        Frame_Board = new FrameBoard();
        LinkToFile = "PlikWymiany.txt";
        PlikWymiany = new File(LinkToFile);
        WhatIsIn = new SwapFileLastPage();
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*Funkcja wypisująca na Console zawartość ramek*/
    @Override
    public void writeMemoryToConsole() {
        for (int i = 0; i < Frame_Board.FrameBoard.length; i++) {
            System.out.println("Stamp: " + Frame_Board.FrameBoard[i].Stamp + "\tRamka: " + i + "\t" + Frame_Board.FrameBoard[i].getCodeForLogs());
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*Funkcja wypisująca na Console zawartość listy tablic stronnic*/
    public void writeListofPageBoardToConsole() {
        for (int i = 0; i < ListofPageBoard.size(); i++) {
            System.out.println("Id procesu: " + ListofPageBoard.get(i).NameOfBoard);
            System.out.println("Page \t Frame \t Valid");
            for (int j = 0; j < ListofPageBoard.get(i).PageBoardForProcess.length; j++) {
                System.out.println(j + "\t" + ListofPageBoard.get(i).PageBoardForProcess[j].WhichFrame + "\t" + ListofPageBoard.get(i).PageBoardForProcess[j].Valid);
            }
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*Ustawianie referencji do dysku, 1 parametr w funkcji*/
    public void setReferences(HDDProvider HardDisc) {
        this.HardDisc = HardDisc; //Przypisanie wartości
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  

    /*Metoda interfejsowa, ustalanie miejsca utworzenia pliku wymiany*/
    @Override
    public String getPath() {
        return LinkToFile; //przekazanie Maćkowi ścieżki do pliku wymiany
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*Zarezerwowanie i wczytanie do pamięci pierwszej stronnicy programu*/
    
    public void provideMemoryForProcess(Process PCB, int SizeOfProgram) throws IOException  {
        PageBoard NewTab = new PageBoard(PCB, SizeOfProgram);

        ListofPageBoard.add(NewTab);

        //writeListofPageBoardToConsole();
        //writeMemoryToConsole();
        
        try {
            getCode(PCB, 0, 32); //zmienia sie na valid w funkcji, po wpisaniu do ramki
        } catch (MemoryException ex) {
            
        }
        
        //writeListofPageBoardToConsole();
        //writeMemoryToConsole();        
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*Zwolnienie miejsca zarezerwowanego dla programu podczas terminacji*/
    public void freeMemoryFromProcess(com.teamone.operatingsystem.processmanagement.Process PCB) {
        //writeListofPageBoardToConsole();
        //writeMemoryToConsole();
        
        for (int i = 0; i < ListofPageBoard.size(); i++) {
            if (ListofPageBoard.get(i).NameOfBoard == PCB.processID) {
                for(int j = 0; j < ListofPageBoard.get(i).PageBoardForProcess.length; j++){
                    if(ListofPageBoard.get(i).PageBoardForProcess[j].Valid == true)
                        Frame_Board.FrameBoard[ListofPageBoard.get(i).PageBoardForProcess[j].WhichFrame].Stamp = 0;
                }
                ListofPageBoard.remove(i);
                break; //przerywamy petle skoro juz znaleziono prawidłowy proces do usuniecia
            }
        }
        
        //writeListofPageBoardToConsole();
        //writeMemoryToConsole();
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*Metoda do pobierania fragmentu kodu przez procesor*/
    @Override
    public String getCode(com.teamone.operatingsystem.processmanagement.Process PCB, int address, int size) throws MemoryException, IOException {
        String Code = "";

        String CodeFromFile = "";

        int WhereItIs = 0;
        boolean ShouldIThrowException = false;
        
        int temp_size = size;
        int temp_address = address;

        for (int i = 0; i < ListofPageBoard.size(); i++) {

            if (ListofPageBoard.get(i).NameOfBoard == PCB.processID) {
                    int _Page = fromLogicalToPhysical(temp_address);
                    //System.out.print("[Pamiec wirtualna] ");
                    //System.out.println("Fragment nalezy do " + _Page + " stronnicy");

                    if (ListofPageBoard.get(i).PageBoardForProcess[_Page].Valid) 
                    {
                        if(ListofPageBoard.get(i).PageBoardForProcess[_Page].WhichFrame == -1)  {   break;  }
                        
                        WhereItIs = ListofPageBoard.get(i).PageBoardForProcess[_Page].WhichFrame;
                        System.out.print("[Pamiec wirtualna] ");
                        System.out.println("Stronnica " + _Page + " znajduje sie w ramce " + WhereItIs);

                        Code = Frame_Board.FrameBoard[WhereItIs].getCode(temp_address % 32, temp_size);
                        
                        if (Code.length() != size)
                        {
                            ShouldIThrowException = true;
                            break;
                        }

                    } else {
                        if (PCB.getProgrammName() != WhatIsIn.WhichProgram || _Page != WhatIsIn.WhichPage) {
                            HardDisc.getPage(PCB.getProgrammName(), fromLogicalToPhysical(temp_address));
                            WhatIsIn.WhichProgram = PCB.getProgrammName();
                            WhatIsIn.WhichPage = _Page;
                        }

                        CodeFromFile = readFromFile();

                        int Ofiara = Frame_Board.LRU_Stamp();

                        findFrameAndSetInvalid(Ofiara); //Czyjas ta ramka była i trzeba zmienic mu na invalid

                        Frame_Board.FrameBoard[Ofiara].setCode(CodeFromFile);
                        ListofPageBoard.get(i).PageBoardForProcess[fromLogicalToPhysical(temp_address)].SetFrame(Ofiara);
                        ListofPageBoard.get(i).PageBoardForProcess[fromLogicalToPhysical(temp_address)].SetValid(true);
                        //Zmiana invalid na valid, stronnica juz jest w ramce

                        ShouldIThrowException = true; //Stronnica juz jest ale mimo to trzeba rzucic wyjatek
                        
                        break;
                    }
                    temp_address = temp_address + Code.length();
                    temp_size = temp_size - Code.length();
            }
        }
        
        //writeListofPageBoardToConsole();
        //writeMemoryToConsole();
        
        //System.out.println("Code: " + Code);
        
        if (ShouldIThrowException) {
            throw new MemoryException(Code);
        } else {
            if (Code == "") {
                throw new NullPointerException();
            }
            return Code;
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 

    /*Przeliczanie adresu logicznego na fizyczny*/
    int fromLogicalToPhysical(int adress) {
        int WhichPage = 0;

        for (int i = 0; i < 16 && adress > 0; i++) {
            if (adress < 32) {
                break;
            }
            //bo takto by blednie podawalo ze pierwsza stronnica, a przeciez jest w zerowej, bo 0 < adress < 32 

            adress -= 32; // adres >= 32, adress == 32 => stronnica 1, index 0
            ++WhichPage; //ramka nastepna i znowu szukamy czy to tu czy nadal adress wiekszy niz 32
        }
        return WhichPage;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*Wyszukiwanie czyją ramke zabraliśmy i ustawienie dla niego invalid*/
    public void findFrameAndSetInvalid(int Frame) {
        for (int i = 0; i < ListofPageBoard.size(); i++) //szukanie po całej liście tablic stonnic
        {
            for (int j = 0; j < ListofPageBoard.get(i).PageBoardForProcess.length; j++) //w tablicach stonnic szukanie wartosci danej ramki
            {
                if (ListofPageBoard.get(i).PageBoardForProcess[j].WhichFrame == Frame) //jezeli ramka która jest ofiarą jest przypisana innemu procesowi
                {
                    ListofPageBoard.get(i).PageBoardForProcess[j].SetFrame(-1); //ustawiamy dla niego nieprawidłową wartość ramki
                    ListofPageBoard.get(i).PageBoardForProcess[j].SetValid(false); //ustawiamy dla niego invalid
                    break;
                }
            }
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*Czytanie z pliku*/
    public String readFromFile() throws IOException {
        String PagefromChangeFile = "";

        try (Scanner plik = new Scanner(PlikWymiany)) {
            while (plik.hasNext())//Dopuki cos jest w pliku bd czytało, a bd w niej jedna stronnica
            {
                PagefromChangeFile = PagefromChangeFile + plik.nextLine();
            }
            plik.close(); //zamykanie pliku
        }

        return PagefromChangeFile;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  
}
