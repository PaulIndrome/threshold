<html>
	<head>
		<title>Blog moi</title>
	</head>
	<body>
		<h1>Tiddl</h1>
		<p>
			Gallia est omnis divisa in partes tres, quarum unam incolunt Belgae, aliam Aquitani,
			tertiam qui ipsorum lingua Celtae, nostra Galli appellantur. Hi omnes lingua, institutis,
			legibus inter se differunt. Gallos ab Aquitanis Garumna flumen, a Belgis Matrona et Sequana dividit.
			Horum omnium fortissimi sunt Belgae, propterea quod a cultu atque humanitate provinciae longissime absunt,
			minimeque ad eos mercatores saepe commeant atque ea quae ad effeminandos animos pertinent important,
			proximique sunt Germanis, qui trans Rhenum incolunt, quibuscum continenter bellum gerunt.
			Qua de causa Helvetii quoque reliquos Gallos virtute praecedunt,
			quod fere cotidianis proeliis cum Germanis contendunt,
			cum aut suis finibus eos prohibent aut ipsi in eorum finibus bellum gerunt.
		</p>
		<h1>Kommentare</h1>
		<p>
			<?php
			#Hier kommen die KOmmentare rein
			var_dump($_POST);
			?>
		</p>
		<h1>Schreib was</h1>
		<form name="entry" method="post" action="blog.php" enctype="multipart/form-data">
			<input type="text" name="name" size="30" maxlength="50"/> (Nick-)Name <br>
			<input type="email" name="mail" size="30" maxlength="80" /> E-Mail <br>
			<textarea name="comment" cols="66" rows="3" wrap="soft" placeholder="Keep it classy!"></textarea><br>
			<input type="submit" value="submit" />
			
		</form>
	</body>

</html>