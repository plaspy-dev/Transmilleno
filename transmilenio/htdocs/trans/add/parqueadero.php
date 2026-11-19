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
		$realquery= "INSERT INTO parqueadero VALUES( ";
		$realquery.= "\"".$_POST["codigo"]."\",";
		$realquery.= "\"".$_POST["nombre"]."\",";
		$realquery.= "\"".$_POST["capacidad"]."\",";
		$realquery.= "\"".$_POST["taller"]."\",";
		$realquery.= "\"".$_POST["bomba"]."\",";
		$realquery.= "\"".$_POST["lavadero"]."\")";
		query($realquery, $link,$_SESSION['user'],$_SESSION['passwd']);
		mysql_close($link);
		header("Location: /trans/parqueaderos.php");
		exit();
	}
	include("../../include/template.php");
	genericheader("Nuevo Parqueadero");
?>
  <div align="center">Nuevo Parqueadero</div>
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
        <td><div align="left">Capacidad</div></td>
        <td><input name="capacidad" type="text" id="capacidad"></td>
      </tr>
      <tr>
        <td><div align="left">Taller</div></td>
        <td><div align="left">
          <input name="taller" type="checkbox" id="taller" value="1">
        </div></td>
      </tr>
      <tr>
        <td><div align="left">Bomba</div></td>
        <td><div align="left">
          <input name="bomba" type="checkbox" id="bomba" value="1">
        </div></td>
      </tr>
      <tr>
        <td><div align="left">Lavadero</div></td>
        <td><div align="left">
          <input name="lavadero" type="checkbox" id="lavadero" value="1">
        </div></td>
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
<br><div align="right"><a href="/trans/parqueaderos.php">Volver</a></div>
<?php
genericfooter();
?>

