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
		echo 'Erfasste Formulardaten';
			echo '<br>';
		echo "What you did say: " .htmlspecialchars($_GET["tf1"]); //specialchars als Entwertung von HTML-Sonderzeichen
			echo '<br>';
		echo "What you would say: " .htmlspecialchars($_GET["tf2"]);
			echo '<br>';
		echo "Who you are: " .htmlspecialchars($_GET["tf3"]);
			echo '<br>';
		echo "What you couldn't say: " .htmlspecialchars($_GET["tf4"]);
			echo '<br>';
		echo "What you can't see: " .htmlspecialchars($_GET["tf5"]);
		
			echo '<br>';
			echo '<br>';
			echo '<br>';
			
		echo "Hurricane: " .htmlspecialchars($_GET["ta1"]);
			echo '<br>';
		echo "Every way: " .htmlspecialchars($_GET["ta2"]);
			echo '<br>';
		echo "Poor Hurricane: " .htmlspecialchars($_GET["ta3"]);
			echo '<br>';
		echo "Which pocket: " .htmlspecialchars($_GET["combo1"]);
			echo '<br>';
		echo "Hidden messages: " .htmlspecialchars($_GET["hidden"]);
		
			echo '<br>';
			echo '<br>';
			echo '<br>';
			
		echo "Which rabbit did I follow? " .htmlspecialchars($_GET["whichrabbit"]);
			echo '<br>';
		echo "Which other rabbit would I follow? " .htmlspecialchars($_GET["rabbits"]);
			echo '<br>';
		echo "Which other animals do I follow? " .htmlspecialchars($_GET["dodo"])." ".htmlspecialchars($_GET["walrus"])." ".htmlspecialchars($_GET["tweedles"]);
			echo '<br>';
	?>
		
	
	
	</body>
</html>