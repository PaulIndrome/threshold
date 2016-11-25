<!DOCTYPE HTML>
<html lang="de">
	<head>
		<meta charset="utf-8">
		<title>phpemail test site</title>			
	</head>
	<body>	
	<p> <a href="../index.html">Zurück</a> zum Index.</p>
	<hr>
	
		<form name="mailform" action="phpemail.php" method="POST">
			<table border="1">
				<tr>
					<td><label for="sender">Sendername:</label></td>
					<td><input type="text" name="sender"></td>
				</tr>
				<tr>
					<td><label for="password">Passwort:</label></td>
					<td><input type="password" name="password"></td>
				</tr>
				<tr>
					<td><label for="email">Empfaenger-Adresse:</label></td>
					<td><input type="email" name="recipientmail"></td>
				</tr>
				<tr>
					<td><label for="betreff">Betreff:</label></td>
					<td><input type="text" name="betreff"></td>
				</tr>
				<tr>
					<td><label for="nachricht">Nachricht:</label></td>
					<td><textarea name="nachricht" cols="20"></textarea></td>
				</tr>
				<tr>
					<td>Senden:</td>
					<td><input type="submit" value="Senden"></td>
				</tr>
			</table>
			
		</form>	
		
		<?php
		
		require('phpmailer/class.phpmailer.php');
		$mail = new PHPMailer();
		
		$mail->IsSMTP();
		$mail->Host = "mail.hs-mittweida.de";
		$mail->SMTPAuth = true;
		$mail->Username = "fschocke";
		$mail->Password = $_POST["password"];
		
		if(isset($_POST["recipientmail"])){
				$name=htmlspecialchars($_POST["sender"]);
				$mailadresse=htmlspecialchars($_POST["recipientmail"]);
				$betreff=htmlspecialchars($_POST["betreff"]);
				$nachricht=htmlspecialchars($_POST["nachricht"]);
				$password=htmlspecialchars($_POST["password"]);
				
				echo $name;
				echo $mailadresse;
				echo $betreff;
				echo $nachricht;
			
			
			$mail->FromName=$name;
			$mail->AddAddress($mailadresse);
			$mail->Subject = $betreff;
			$mail->Body = $nachricht;
			$mail->CharSet = "utf-8";
			$mail->isHTML(false);
			//
			
			if(!$mail->Send()){ //Funktion, die E-Mail tatsächlich zu senden, wird hier aufgerufen. Gibt grundsätzlich einen boolean zurück, der im if-Fall abgefragt wird 
				echo "E-Mail delivery failed";
				echo "Error: " . $mail->ErrorInfo;
			}
			else{
				echo "E-Mail delivery successfull";
			}
		}
		?>
	
	</body>
</html>