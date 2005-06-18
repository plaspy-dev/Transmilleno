<?php 
	header('Pragma: no-cache');
	session_start();
	if( !isset($HTTP_SESSION_VARS['user'])) {
		header("Location: /index.php");
		exit();
	}
	include("../../include/funciones.php");
	if($_POST && isset($_POST["Submit"])) {

		$link= NULL;
		$realquery= "INSERT INTO bus VALUES( ";
		$realquery.= "\"".$_POST["codigo"]."\",";
		$realquery.= "\"".$_POST["placa"]."\",";
		$realquery.= "\"".$_POST["dueno"]."\",";
		$realquery.= "\"".$_POST["npasajeros"]."\",";
		$realquery.= "\"".$_POST["fingreso"]."\",";
		$realquery.= "\"".$_POST["modelo"]."\",";
		$realquery.= "\"".$_POST["motor"]."\",";
		$realquery.= "\"".$_POST["chasis"]."\",";
		$realquery.= "\"".$_POST["parqueadero"]."\")";
		query($realquery, $link,$HTTP_SESSION_VARS['user'],$HTTP_SESSION_VARS['passwd']);
		mysql_close($link);
		header("Location: /trans/buses.php");
		exit();
	}
	include("../../include/template.php");
	genericheader("Nuevo Bus");
?>
  <div align="center">Nuevo Bus</div>
  <br>
<form name="form1" method="post" action="bus.php">
  <div align="center">
  <table border="0">
    <tr>
      <td><div align="left">Codigo</div></td>
      <td><input name="codigo" type="text" id="codigo"></td>
    </tr>
    <tr>
      <td><div align="left">Placa</div></td>
      <td><input name="placa" type="text" id="placa"></td>
    </tr>
    <tr>
      <td><div align="left">Due&ntilde;o</div></td>
      <td><input name="dueno" type="text" id="dueno"></td>
    </tr>
    <tr>
      <td><div align="left">Fecha de Ingreso</div></td>
      <td><input name="fingreso" type="text" id="fingreso"></td>
    </tr>
    <tr>
      <td><div align="left">Modelo</div></td>
      <td><input name="modelo" type="text" id="modelo"></td>
    </tr>
    <tr>
      <td><div align="left">Numero de Pasajeros</div></td>
      <td><input name="npasajeros" type="text" id="npasajeros"></td>
    </tr>
    <tr>
      <td><div align="left">Marca Motor</div></td>
      <td><input name="motor" type="text" id="motor"></td>
    </tr>
    <tr>
      <td><div align="left">Marca Chasis</div></td>
      <td><input name="chasis" type="text" id="chasis"></td>
    </tr>
    <tr>
      <td><div align="left">Parqueadero</div></td>
      <td><div align="left">
        <select name="parqueadero">
	      <?php
		$myquery= "SELECT codigo, nombre FROM parqueadero";
	    $result = query($myquery, $link,$HTTP_SESSION_VARS['user'], $HTTP_SESSION_VARS['passwd']);
		if( !$result || !mysql_num_rows($result)) {
			echo "algo";
		} else {
			for( $i=0; $i<mysql_num_rows($result); $i++) {
				if( !mysql_data_seek($result, $i)) 
					continue;
				if( !($row= mysql_fetch_array($result))) 
					continue;
				for( $h=0; $h<mysql_num_fields($result); $h++) {
					if( $h==0) 
						echo "<option value=\"".$row[$h]."\">";
					else 
						echo $row[$h]."</option>\n";
				}
			}
		}
	  ?>
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
</div>
</form>
<br><div align="right"><a href="/trans/buses.php">Volver</a></div>
<?php
genericfooter();
?>
