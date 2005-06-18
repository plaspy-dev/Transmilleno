<?php 
	header('Pragma: no-cache');
	session_start();
	if( !isset($HTTP_SESSION_VARS['user'])) {
		header("Location: /index.php");
		exit();
	}
	include("../include/template.php");
	genericheader($HTTP_SESSION_VARS['user']);
?>
<p align="center">Bienvenido
<?php
 	echo " ".$HTTP_SESSION_VARS['user']."<br></p>";
	genericfooter();
?>
