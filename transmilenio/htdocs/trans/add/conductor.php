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
		$realquery= "INSERT INTO conductor VALUES( ";
		$realquery.= "\"".$_POST["cedula"]."\",";
		$realquery.= "\"".$_POST["nombre"]."\",";
		$realquery.= "\"".$_POST["apellido"]."\",";
		$realquery.= "\"".$_POST["pase"]."\",";
		$realquery.= "\"".$_POST["categoria"]."\",";
		$realquery.= "\"".$_POST["vehiculo"]."\")";
		query($realquery, $link,$HTTP_SESSION_VARS['user'],$HTTP_SESSION_VARS['passwd']);
		mysql_close($link);
		header("Location: /trans/conductores.php");
		exit();
	}
	include("../../include/template.php");
	genericheader("Nuevo Conductor");
?>
  <div align="center">Nuevo Conductor</div>
  <br>

<div align="center">
  <form name="form1" method="post" action="">
    <table border="0">
      <tr>
        <td><div align="left">Cedula</div></td>
        <td><input name="cedula" type="text" id="cedula"></td>
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
        <td><div align="left">Numero de Pase </div></td>
        <td><input name="pase" type="text" id="pase"></td>
      </tr>
      <tr>
        <td><div align="left">Categoria</div></td>
        <td><input name="categoria" type="text" id="categoria"></td>
      </tr>
      <tr>
        <td><div align="left">Tipo de Vehiculo</div></td>
        <td><div align="left">
          <select name="vehiculo" id="vehiculo">
              <option value="1" selected>Bus</option>
              <option value="2">Ayuda</option>
              <option value="3">Valores</option>
          </select>
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
<br><div align="right"><a href="/trans/conductores.php">Volver</a></div>
<?php
genericfooter();
?>
