<!DOCTYPE HTML>
<html lang="de">
	<head>
		<meta charset="utf-8">
		<title>php Formerfassung 2</title>			
	</head>
	<body>	
	<p> <a href="../index.html">Zurück</a> zum Index.</p>
	<p> <a href="../HTML/html_nocss/formular.html">Zurück</a> dahin, wo du her kamst.</p>
	<hr>
	<?
	echo 'über GET-Methode:<br>';
		$datenG = print_r($_GET, true);
		echo $datenG;
		echo '<br>';
		echo "Selections:";
		echo '<br>';
		echo "single select: " .htmlspecialchars($_GET["auswahl1"]);
		echo '<br>';
		echo "multiple select 1: " .htmlspecialchars($_GET["auswahl2"]);
		echo '<br>';
		echo "laenge select 1: " .count($_GET["auswahl2"]);
		echo '<br>';
		echo "multiple select 2: " .htmlspecialchars($_GET["auswahl3"]);
		echo '<br>';
		echo "laenge select 2: " .count($_GET["auswahl3"]);
		echo '<br>';
		echo '<br>';
		echo '<br>';
		echo "foreach methode zur Ausgabe der Selektionen hintereinander:";
		foreach($_GET['auswahl3'] as $auswahlliste3){
			echo htmlspecialchars($auswahlliste3)." ";
		}
		
		echo '<br>';
		echo "Test für Einzelauswahl an der Stelle 0: ".$_GET['auswahl3'][0].'<br>';
		echo "Test für Einzelauswahl an der Stelle 1: ".$_GET['auswahl3'][1].'<br>';
		echo '<br>';
		echo '<br>';
		
		for ($i=0;$i<count($_GET['auswahl3']);$i++){
			echo $_GET['auswahl3'][$i]." ";
		}
		
		
	?>
		
	
	
	</body>
</html>