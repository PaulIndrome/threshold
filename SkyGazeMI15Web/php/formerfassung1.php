<!DOCTYPE HTML>
<html lang="de">
	<head>
		<meta charset="utf-8">
		<title>php Formerfassung 1</title>			
	</head>
	<body>	
	<p> <a href="../index.html">Zurück</a> zum Index.</p>
	<hr>
	<?
		echo 'Erfasste Formulardaten';
		echo 'über POST-Methode:<br>';
		$datenP = print_r($_POST, true); //print_r gibt Dateneingang in lesbarer Form zurück
		echo $datenP;
		echo '<br>';
		echo '------------------<br>';
		echo 'über GET-Methode:<br>';
		$datenG = print_r($_GET, true);
		echo $datenG;
		echo '<br>';
		?>
		
	
	
	</body>
</html>