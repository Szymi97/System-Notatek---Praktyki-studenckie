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
    

    if (!$_POST['NazwaKli'] || !$_POST['NazwaUzy'] || !$_POST['TrescN'])
    {
           echo "Uzupełnij wszystkie pola" ;
           return; 
    }



    $NazwaK = $_POST['NazwaKli']; 
    $NazwaU = $_POST['NazwaUzy']; 
    $Tresc = $_POST['TrescN']; 
      
    connection(); 



    $zapytanie = "INSERT INTO `Notatki` (`Id`, `Data_dodania`, `Nazwa_klienta`, `Nazwa_uzytkownika` , `Tresc`, `Data_edycji`) 
    VALUES ('', '".date('y-m-d H:i:s')."', '$NazwaK', '$NazwaU', '$Tresc', '".date('y-m-d H:i:s')."')";
    $ins = mysql_query($zapytanie);
     
    if($ins) header("Location:wyswietlanie.php?akcja=true",TRUE,301);  
    else header("Location:wyswietlanie.php?akcja=false",TRUE,301); 

?>
<!--Szymon Kaszuba-->
