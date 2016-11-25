<!DOCTYPE HTML>
<html lang="de">
	<head>
		<meta charset="utf-8">
		<title>php Formerfassung UND Ausgabe (groß)</title>			
	</head>
	<body>	
	<p> <a href="../index.html">Zurück</a> zum Index.</p>
<hr>
<br>
<form name="praktikum" action="formerfassungUNDausgabeGROSS.php" method="get">
	<table border="1">
		<tr><td>Name: </td><td colspan="3"><input type="text" value="Name" name="name" maxlength="30" style="width:480px" onfocus="if(this.value=='Name'){this.value='';}"></td></tr>
		<tr><td>Vorname: </td><td colspan="3"><input type="text" value="Vorname" name="vorname" maxlength="30" style="width:480px" onfocus="if(this.value=='Vorname'){this.value='';}"></td></tr>
		<tr><td>Username: </td><td colspan="3"><input type="text" value="Username" name="username" maxlength="30" style="width:480px" onfocus="if(this.value=='Username'){this.value='';}"></td></tr>
		<tr><td>EMail-Adresse: </td><td colspan="3"><input type="email" value="adresse@domain.XX" name="email" style="width:480px" onfocus="if(this.value=='adresse@domain.XX'){this.value='';}"></td></tr>
		<tr><td>Passwort: </td><td colspan="3"><input type="password" name="passwort" style="width:480px"></td></tr>
		<tr><td>Bundesland: </td><td colspan="3">
			<input type="text" name="bundeslaender" list="bundeslandliste" value="Bundesland" style="width:480px" onfocus="if(this.value=='Bundesland'){this.value='';}"/>
				<datalist id="bundeslandliste">
				<option value="Baden-Württemberg">
				<option value="Bayern">
				<option value="Berlin">
				<option value="Brandenburg">
				<option value="Bremen">
				<option value="Hamburg">
				<option value="Hessen">
				<option value="Mecklenburg-Vorpommern">
				<option value="Niedersachsen">
				<option value="Nordrhein-Westfalen">
				<option value="Rheinland-Pfalz">
				<option value="Saarland">
				<option value="Sachsen">
				<option value="Sachsen-Anhalt">
				<option value="Schleswig-Holstein">
				<option value="Thüringen">
				</datalist>
		</td></tr>
		<tr><td>Geschlecht: </td>
			<td><input type="radio" name="geschlecht" value="maennlich"/>Männlich</td>
			<td><input type="radio" name="geschlecht" value="weiblich"/>Weiblich</td>
			<td><input type="radio" name="geschlecht" value="nonbin"/>Nonbinary</td>
		</tr>
		<tr><td>Altersbereich: </td>
			<td><input type="radio" name="alter" value="10-19"/>10-19<br>
				<input type="radio" name="alter" value="30-39"/>30-39
			</td>
			<td><input type="radio" name="alter" value="20-29"/>20-29<br>
				<input type="radio" name="alter" value="40-49"/>40-49
			</td>
		</tr>
		<tr><td>Interessen: </td>
			<td><input type="checkbox" name="interessen[]" id="musik" value="Musik">Musik
				<br>
				<input type="checkbox" name="interessen[]" id="sport" value="Sport">Sport
			</td>
			<td><input type="checkbox" name="interessen[]" id="reisen" value="Reisen">Reisen
				<br>
				<input type="checkbox" name="interessen[]" id="studium" value="Studium">Studium
			</td>
		<tr><td>Urlaub: </td>
			<td>Beginn: <br>
				<input type="date" name="beginndatum" id="beginndate">
				<br>
				<input type="time" name="beginnzeit" id="beginntime">
			</td>
			<td>Ende: <br>
				<input type="date" name="endedatum" id="endedate">
				<br>
				<input type="time" name="endezeit" id="endetime">
			</td>
		</tr>
		<tr>
			<td>Kommentar: </td><td colspan="3"><textarea rows="5" name="kommentar" cols="50" style="resize:none" onfocus="if(this.value=='Nichts Obszönes, bitte!'{this.value='';})">Nichts Obszönes, bitte!</textarea></td>
		</tr>
		<tr>
			<td>Formular: </td><td colspan="3"><input type="submit" value="Senden" name="senden"> - <input type="reset" value="Löschen" name="loeschen"></td>
		</tr>
		
	</table>
	</form>
	<br>
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
					else if (count($_GET['interessen'])==0) // wäre mit ==1 und ANSONSTEN besser gewesen
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
	</body>
</html>