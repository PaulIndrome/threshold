<!DOCTYPE HTML>
<html lang="de">
	<head>
		<meta charset="utf-8">
		<title>Upload test site</title>			
	</head>
	<body>	
	<p> <a href="../index.html">Zurück</a> zum Index.</p>
	<hr>
	
		<form name="upload" action="upload.php" method="POST" enctype="multipart/form-data">
			Datei:<br>
			<input type="hidden" name"MAX_FILE_SIZE" value="30000"/>
			<input type="file" name="datei" /><br>
			<br>
			<input type="submit" value="Upload that thang" />
			<hr>
			
		</form>
		
		<?php
			if (isset($_FILES["datei"]) and ! $_FILES["datei"]["error"]){
				foreach ($_FILES["datei"] as $key => $value) {
				echo "$key: $value <br>\n";
				}
			}
		
		echo "<hr>";
		
			if (isset($_FILES["datei"]) and ! $_FILES["datei"]["error"]){
				if(move_uploaded_file($_FILES["datei"]["tmp_name"],
					"../uploadedfiles/".basename($_FILES["datei"]["name"]))){
					echo "Upload successfull.";
					}					
				else { echo "Upload failed."; }
			}
		?>
	
	<!-- kellerhartkäsereibekuchenschneidgerätbedienerbedienungsanleitungsumschlagverpackungsetikettsalatdressingtubendrehervorrichtungsbaupaketbeurteilungszetteldrucktintenpatronenreinigerlösungsspickerversteckschubladenschlossschlüssellochdietrichbiegeradiusmessgeräteichungsvorgangsspezialistenausbildungsleiterlehrgangsbuffetservicetablettenschachtelelefantenrüsselschraubzwingenhalterungsnagelkopfsalat -->
	</body>
</html>