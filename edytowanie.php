<!--Szymon Kaszuba-->
<?php

   function connection() { 
    // serwer 
    $mysql_server = "XYZ"; 
    // admin 
    $mysql_admin = "XYZ"; 
    // hasło 
    $mysql_pass = "XYZ"; 
    // nazwa bazy 
    $mysql_db = "XYZ"; 
    // nawiązujemy połączenie z serwerem MySQL 
    @mysql_connect($mysql_server, $mysql_admin, $mysql_pass) 
    or die('Brak polaczenia z serwerem MySQL.'); 
    // łączymy się z bazą danych 
    @mysql_select_db($mysql_db) 
    or die('Błąd wyboru bazy danych.'); 
}


    $Tresc = $_POST['TrescN'];
    $Id = $_POST['IdN'] ;

    if (!$_POST['TrescN'])
    {
           echo "Uzupełnij wszystkie pola" ;
           return; 
    }

    connection(); 
    
    $zapytanie= "UPDATE Notatki SET Tresc='$Tresc', Data_edycji='".date('y-m-d H:i:s')."' WHERE Id='$Id'";
    $ins = mysql_query($zapytanie);
    
    if($ins) header("Location:wyswietlanie.php?akcja=true",TRUE,301);  
    else header("Location:wyswietlanie.php?akcja=false",TRUE,301); 
   
?>
<!--Szymon Kaszuba-->