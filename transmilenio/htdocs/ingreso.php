<?php 
	include("include/funciones.php");
	$location= "Location: index.php";
	if( $_POST) {
		if( validate( $_POST["user"], $_POST["passwd"])) {
			session_start();
		   $HTTP_SESSION_VARS['user']= $_POST["user"];
		   $HTTP_SESSION_VARS['passwd']= $_POST["passwd"];
		   $location= "Location: trans/admin.php";
		} else $location="Location: index.php?message=Usuario+Invalido";
	}
	header($location);
?>
