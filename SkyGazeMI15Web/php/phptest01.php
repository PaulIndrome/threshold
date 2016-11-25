<!DOCTYPE HTML>
<html lang="de">
	<head>
		<meta charset="utf-8">
		<title>php test site 1</title>			
	</head>
	<body>
	<p> <a href="../index.html">Zurück</a> zum Index.</p>
	<hr>
	<br>
		<h2>Hello World!</h2>
		<p>Was executed on:</p>
		<?php
		
		$zeit = time();
		$datum = date("D.d.M.Y", $zeit);
		$uhrzeit = date("H:i (s)", $zeit);
		
		?>
	<!-- !!! php ist case-sensitive -->
		<table border="1">
			<tr><th>Datum</th><th>Uhrzeit</th></tr>
			<tr><td>
				<? echo $datum; ?>
			</td><td>
				<? echo $uhrzeit; ?>
			</td></tr>
		</table>
	
	<hr>
	
	</body>
</html>

<? 
$test = "das ist ein Test mit Hochkomma und Anführungszeichen (Apostroph)";
echo "mit Hochkomma:$test";
echo 'mit Anführungszeichen: $test <br>';
echo '$test außerhalb der Anführungszeichen ' .$test.'<br>'; //mit Verkettung durch "."
?>
