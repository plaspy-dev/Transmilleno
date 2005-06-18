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
		$realquery= "INSERT INTO historialbus VALUES( ";
		$realquery.= "\"".$_POST["codigo"]."\",";
		$realquery.= "\"".$_POST["observaciones"]."\",";
		$realquery.= "\"".$_POST["arreglo"]."\",";
		$realquery.= "\"".$_POST["policia"]."\",";
		$realquery.= "\"".$_POST["fecha"]."\",";
		$realquery.= "\"".$_POST["bus"]."\")"; 
		query($realquery, $link,$HTTP_SESSION_VARS['user'],$HTTP_SESSION_VARS['passwd']);
		mysql_close($link);
		header("Location: /trans/historialbuses.php");
		exit();
	}
	include("../../include/template.php");
	genericheader("Nuevo Historeial de Bus");
?>
  <div align="center">Nuevo Historial del Bus </div>
  <br>
<div align="center">
  <form name="form1" method="post" action="">
    <table border="0">
      <tr>
        <td><div align="left">Codigo</div></td>
        <td><input name="codigo" type="text" id="codigo"></td>
      </tr>
      <tr>
        <td><div align="left">Acontecimiento</div></td>
        <td><div align="left">
          <input name="observaciones" type="text" id="observaciones">
        </div></td>
      </tr>
      <tr>
        <td><div align="left">Hubo Arreglo</div></td>
        <td>        <div align="left">
          <input name="arreglo" type="checkbox" id="arreglo" value="1">
        </div></td>
      </tr>
      <tr>
        <td><div align="left">Intervino la Policia</div></td>
        <td><div align="left">
          <input name="policia" type="checkbox" id="policia" value="1">
        </div></td>
      </tr>
      <tr>
        <td><p>Fecha de Acontecimiento</p>
        </td>
        <td><input name="fecha" type="text" id="fecha"></td>
      </tr>
      <tr>
        <td>Bus del Acontecimiento </td>
        <td><select name="bus" id="bus">
          <?php
			$myquery= "SELECT codigo,placa FROM bus";
			$result = query($myquery, $link,$HTTP_SESSION_VARS['user'], $HTTP_SESSION_VARS['passwd']);
			if( !$result || !mysql_num_rows($result)) {
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
		</td>
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
<br><div align="right"><a href="/trans/historialbuses.php">Volver</a></div>
<?php
genericfooter();
?>

