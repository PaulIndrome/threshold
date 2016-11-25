<!DOCTYPE html>
<html>
	<head>
		<title>Formularauswertung</title>
	</head>
	<body>
		<h1>Formularauswertung</h1>
	</body>
<?php 
	if($_GET){
		echo '<h2> Per GET oder Query gesendeter Request</h2>';
		echo '<pre>';
		var_dump($_GET);
		echo '</pre>';
	}
	if($_POST){
		echo '<h2> Per POST gesendeter Request</h2>';
		echo '<pre>';
		var_dump($_POST);
		echo '</pre>';
	}
	$all = array_merge($_GET,$_POST,$_FILES);
		echo '<h2>Alle Formulardaten</h2>';
		echo '<pre>';
		var_dump($all);
		echo '</pre>';
	
?>
</html>
