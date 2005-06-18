<?php 
include("include/template.php");
genericheader("Ingreso");
?>
<form name="form1" method="post" action="ingreso.php">
<?php
	if( $_GET) 
		if( $_GET["message"]) {
			echo "<p align=\"center\">";
			echo $_GET["message"];
			echo "</p><br>\n";
		}
?>
  <p>usuario 
    <input name="user" type="text" id="user" value="">
  </p>
  <p>contrase&ntilde;a 
    <input name="passwd" type="password" id="passwd" value="">
</p>
  <p>
    <input type="submit" name="Submit" value="Ingresar">
  </p>
</form>
<?php
genericfooter();
?>

