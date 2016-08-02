<!--Szymon Kaszuba-->
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="style.css">
</head>
<body>
<?php
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
    
    
     $zapytanie = 'select * from Notatki where Id= '.$_GET["Id"].'';
     $wynik = mysql_query( $zapytanie );
     $wynik = mysql_fetch_row($wynik); 

?>

<form action="edytowanie.php" method="post">
<div> 
Treść: (max 500 znaków, pozostałe zostaną pominięte)<br /> 
<textarea name="TrescN" cols="90" rows="8" /><?=$wynik[4]?></textarea><br />
<input type="hidden" name="IdN" value="<?=$_GET["Id"]?>">
</div> 
<input type="submit" value="Aktualizuj" />
</form>
              
</body>

</html>
<!--Szymon Kaszuba-->