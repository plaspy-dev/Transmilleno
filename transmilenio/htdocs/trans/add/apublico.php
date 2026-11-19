<?php 
	header('Pragma: no-cache');
	session_start();
	if( !isset($_SESSION['user'])) {
		header("Location: /index.php");
		exit();
	}
	if($_POST && isset($_POST["Submit"])) {

		include("../../include/funciones.php");
		$link= NULL;
		$realquery= "INSERT INTO taquillero VALUES( ";
		$realquery.= "\"".$_POST["codigo"]."\",";
		$realquery.= "\"".$_POST["nombre"]."\",";
		$realquery.= "\"".$_POST["apellido"]."\",";
		$realquery.= "\"".$_POST["nivel"]."\")";
		query($realquery, $link,$_SESSION['user'],$_SESSION['passwd']);
		mysql_close($link);
		header("Location: /trans/apublico.php");
		exit();
	}
	include("../../include/template.php");
	genericheader("Nuevo Empleado");
?>
  <div align="center">Nuevo Empleado</div>
  <br>
<div align="center">
  <form name="form1" method="post" action="">
    <table border="0">
      <tr>
        <td><div align="left">Codigo</div></td>
        <td><input name="codigo" type="text" id="codigo"></td>
      </tr>
      <tr>
        <td><div align="left">Nombre</div></td>
        <td><input name="nombre" type="text" id="nombre"></td>
      </tr>
      <tr>
        <td><div align="left">Apellido</div></td>
        <td><input name="apellido" type="text" id="apellido"></td>
      </tr>
      <tr>
        <td><div align="left">Nivel Educativo </div></td>
        <td><input name="nivel" type="text" id="nivel"></td>
      </tr>
      <tr>
        <td><div align="center">
          <input type="submit" name="Submit" value="Aceptar">
        </div></td>
        <td><div align="center">
          <input type="reset" name="Submit" value="Cancelar">
        </div></td>
      </tr>
    </table>
  </form>
</div>
<br><div align="right"><a href="/trans/apublico.php">Volver</a></div>
<?php
genericfooter();
?>
