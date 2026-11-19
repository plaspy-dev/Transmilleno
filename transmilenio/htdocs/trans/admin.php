<?php 
	header('Pragma: no-cache');
	session_start();
	if( !isset($_SESSION['user'])) {
		header("Location: /index.php");
		exit();
	}
	include("../include/template.php");
	genericheader($_SESSION['user']);
?>
<p align="center">Bienvenido
<?php
 	echo " ".$_SESSION['user']."<br></p>";
	genericfooter();
?>
