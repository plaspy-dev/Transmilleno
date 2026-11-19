<?php 
	include("include/funciones.php");
	$location= "Location: index.php";
	if( $_POST) {
		if( validate( $_POST["user"], $_POST["passwd"])) {
			session_start();
			session_regenerate_id(true);
		   $_SESSION['user']= $_POST["user"];
		   $_SESSION['passwd']= $_POST["passwd"];
		   $location= "Location: trans/admin.php";
		} else $location="Location: index.php?message=Usuario+Invalido";
	}
	header($location);
?>
