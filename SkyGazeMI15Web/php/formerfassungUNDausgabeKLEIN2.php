<!DOCTYPE HTML>
<html lang="de">
	<head>
		<meta charset="utf-8">
		<title>Formerfassung UND Ausgabe (klein)</title>			
	</head>
	<body>	
	<p> <a href="../index.html">Zurück</a> zum Index.</p>
	<hr>
	
		<form name="form2" action="formerfassungUNDausgabeKLEIN2.php" method="get">
			<table border="1">
				<tr>
					<td><label for="vorname">Vorname:</label></td>
					<td><input type="text" id="vorname" name="vorname" size="20" value="<?php echo $vorname; ?>"></td>
				</tr>
				<tr>
					<td><label for="nachname">Nachname:</label></td>
					<td><input type="text" id="nachname" name="nachname" size="20" value="<?php echo $nachname; ?>"></td>
				</tr>
				<tr>
					<td><label for="username">Username:</label></td>
					<td><input type="text" id="username" name="username" size="20" value="<?php echo $username; ?>"></td>
				</tr>
				<tr>
					<td>Formular:</td>
					<td><input type="submit" value="Senden"></td>
				</tr>
			</table>
			
		</form>
		
		<?php
			if (isset($_GET["vorname"]) || ($_GET["nachname"]) || ($_GET["username"])){
				$vorname=htmlspecialchars($_GET["vorname"]);
				$nachname=htmlspecialchars($_GET["nachname"]);
				$username=htmlspecialchars($_GET["username"]);
				echo "Erfasse Formulardaten:<br>"
				."Vorname: ".htmlspecialchars($_GET["vorname"])."<br>"
				."Nachname: ".htmlspecialchars($_GET["nachname"])."<br>"
				."Username: ".htmlspecialchars($_GET["username"]);
			}
			else {$vorname=""; $nachname=""; $username="";}
		?>
	
	
	</body>
</html>