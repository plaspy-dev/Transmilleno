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
		$realquery= "INSERT INTO estacion VALUES( ";
		$realquery.= "\"".$_POST["codigo"]."\",";
		$realquery.= "\"".$_POST["observaciones"]."\",";
		$realquery.= "\"".$_POST["recorrido"]."\",";
		$realquery.= "\"".$_POST["transbordo"]."\")";
		query($realquery, $link,$_SESSION['user'],$_SESSION['passwd']);
		mysql_close($link);
		header("Location: /trans/estaciones.php");
		exit();
	}
	include("../../include/template.php");
	genericheader("Nueva Estacion");
?>
  <div align="center">Nueva Estacion</div>
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
        <td><input name="observaciones" type="text" id="observaciones"></td>
      </tr>
      <tr>
        <td><div align="left">Direccion</div></td>
        <td><input name="recorrido" type="text" id="recorrido"></td>
      </tr>
      <tr>
        <td><div align="left">Trasbordo</div></td>
        <td><div align="left">
          <input name="transbordo" type="checkbox" id="transbordo" value="1">
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
<br><div align="right"><a href="/trans/estaciones.php">Volver</a></div>
<?php
genericfooter();
?>

