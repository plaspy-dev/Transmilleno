<?php 
	header('Pragma: no-cache');
	session_start();
	if( !isset($HTTP_SESSION_VARS['user'])) {
		header("Location: /index.php");
		exit();
	}
	if($_POST && isset($_POST["Submit"])) {

		include("../../include/funciones.php");
		$link= NULL;
		$realquery= "INSERT INTO turno VALUES( ";
		$realquery.= "\"".$_POST["codigo"]."\",";
		$realquery.= "\"".$_POST["observaciones"]."\",";
		$realquery.= "\"".$_POST["recorrido"]."\")";
		query($realquery, $link,$HTTP_SESSION_VARS['user'],$HTTP_SESSION_VARS['passwd']);
		mysql_close($link);
		header("Location: /trans/turnos.php");
		exit();
	}
	include("../../include/template.php");
	genericheader("Nuevo Turno");
?>
  <div align="center">Nuevo Turno</div>
  <br>
<div align="center">
  <form name="form1" method="post" action="">
    <table border="0">
      <tr>
        <td><div align="left">Codigo</div></td>
        <td><input name="codigo" type="text" id="codigo"></td>
      </tr>
      <tr>
        <td><div align="left">Hora inicio </div></td>
        <td><input name="observaciones" type="text" id="observaciones"></td>
      </tr>
      <tr>
        <td><div align="left">Hora Fin</div></td>
        <td><input name="recorrido" type="text" id="recorrido"></td>
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
<br><div align="right"><a href="/trans/turnos.php">Volver</a></div>
<?php
genericfooter();
?>

