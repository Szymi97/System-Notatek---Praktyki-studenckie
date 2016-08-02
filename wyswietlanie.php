<!--Szymon Kaszuba-->
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="style.css">
</head>
<body><?php

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
    or die('Brak połączenia z serwerem MySQL.'); 
    // łączymy się z bazą danych 
    @mysql_select_db($mysql_db) 
    or die('Błąd wyboru bazy danych.'); 
}

   connection(); 

  $zapytanie = 'select * from Notatki';
  $wynik = mysql_query( $zapytanie );

?>

<a href=dodaj.php><input type="submit" value="Dodaj nową notatkę"></a> <br><br> 

<?php 
      while ($row = mysql_fetch_array($wynik, MYSQL_NUM)) {
?>  

<div> 

<?php
      printf("Id: %s | Data dodania: %s | Ostatnia modyfikacja: %s <br> Nazwa klineta: %s | Nazwa użytkownika: %s <br> Treść: %s <br>", $row[0], $row[1], $row[5], $row[2], $row[3], $row[4]) 
?>
 
</div>
<a href=edytuj.php?Id=<?=$row[0]?>><input type="submit" value="Edytuj"></a>
<a href=usuwanie.php?Id=<?=$row[0]?>><input type="submit" value="Usuń"></a>

<br><br>

<?php
      }
?>



</body>

</html>
<!--Szymon Kaszuba-->