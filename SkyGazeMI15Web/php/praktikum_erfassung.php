<!DOCTYPE HTML>
<html lang="de">
	<head>
		<meta charset="utf-8">
		<title>php praktikumerfassung</title>			
	</head>
	<body>	
	<p> <a href="../index.html">Zurück</a> zum Index.</p>
<hr>
	<h2>Erfasste Formulardaten</h2>
		<table border="1">
			<tr><td>Name: </td><td colspan="4"><? echo htmlspecialchars($_GET['name']); ?></td></tr>
			<tr><td>Vorname: </td><td colspan="4"><? echo htmlspecialchars($_GET['vorname']); ?></td></tr>
			<tr><td>Username: </td><td colspan="4"><? echo htmlspecialchars($_GET['username']); ?></td></tr>
			<tr><td>EMail-Adresse: </td><td colspan="4"><? echo htmlspecialchars($_GET['email']); ?></td></tr>
			<tr><td>Passwort: </td><td colspan="4"><? echo htmlspecialchars($_GET['passwort']); ?></td></tr>
			<tr><td>Bundesland: </td><td colspan="4"><? echo htmlspecialchars($_GET['bundeslaender']) ?></td></tr>
			<tr><td>Geschlecht: </td><td colspan="4"><? echo htmlspecialchars($_GET['geschlecht']); ?></td></tr>
			<tr><td>Altersbereich: </td><td colspan="4"><? echo htmlspecialchars($_GET['alter']); ?></td></tr>
			<tr><td>Interessen: </td>
				<td colspan="2">
					<?
						foreach($_GET['interessen'] as $interessenauswahl){
							echo htmlspecialchars($interessenauswahl)." ";
						}
					?>
				</td>
				<td>Du hast <? echo count($_GET['interessen'])." "; 
					if (count($_GET['interessen'])>1)
						echo "Interessen.";
					else if (count($_GET['interessen'])==0)
						echo "Interessen.";
					else
						echo "Interesse.";				
					?> </td>
			<tr><td>Urlaub: </td>
				<td colspan="2">Beginn: <br>
					<? 
						echo $_GET['beginndatum'].'<br>';
						echo $_GET['beginnzeit'];
					?>
				</td>
				<td colspan="2">Ende: <br>
					<? 
						echo $_GET['endedatum'].'<br>';
						echo $_GET['endezeit'];
					?>
				</td>
			</tr>
			<tr>
				<td>Kommentar: </td><td colspan="4"><? echo htmlspecialchars($_GET['kommentar']); ?></td>
			</tr>
		</table>
		<p> <a href="../HTML/html_nocss/formularpraktikum.html">Zurück</a> dahin, wo du her kamst.</p>
	</body>
</html>