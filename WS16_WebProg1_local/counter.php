<?php

# Definition der Datenablage, Persistenzschicht
define('DATA', 'counter.txt');
define('DELIMITER', ',');

#Hilfsvariable
#Counter, numerische Variable
$i = 0;
#boolsche Hilfsvariable
$found = false;
if (file_exists(DATA)) {
	$content = file(DATA, FILE_SKIP_EMPTY_LINES | FILE_IGNORE_NEW_LINES);
	#alle Elemente (Datens�tze) durchlaufen und auf Dateinamen der aufrufenden Datei testen
	foreach ($content as $record) {
		$fields = explode(DELIMITER, $record);
		if ($fields[0] == $_SERVER['PHP_SELF']) {
			$found = true;
			break;
		}
		$i++;
	}
}

if(!$found){
	$fields[0] = $_SERVER['PHP_SELF'];
	$fields[1] = 1;
	$fields[2] = time();
}

echo $fields[1] . ' Zugriff(e) zuletzt am ';
echo date('D, d M Y H:i:s');

$fields[1]++;
$fields[2] = time();

#Datensatz zusammen setzen
$content[$i] = join(DELIMITER,$fields);

# Z�hlerstand erh�hen und zur�ckschreiben
# (Daten persistieren)

$fh = fopen(DATA, 'w');
foreach($content as $record){
	fwrite($fh, $record.PHP_EOL);
}
fwrite($fh, $i);
fclose($fh);
?>
