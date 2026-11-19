<?php 
	header('Pragma: no-cache');
	session_start();
	if( !isset($_SESSION['user'])) {
		header("Location: /index.php");
		exit();
	}
	include("../../include/template.php");
	genericheader("Estaciones de la Ruta");
	include("../../include/funciones.php");
	
	if($_POST && isset($_POST["Submit"])) {

		$link= NULL;
		$realquery= "INSERT INTO rutaestacion VALUES ( ";
		$realquery.= "NULL,";
		$realquery.= "\"".$_POST["bus"]."\",";
		$realquery.= "\"".$_POST["conductor"]."\",";
		$realquery.= "\"".$_POST["observacion"]."\")";
		query($realquery, $link,$_SESSION['user'],$_SESSION['passwd']);
		mysql_close($link);
	}
	
	$link= NULL;
	$myquery= "SELECT rutaestacion.codigo,estacion.nombre, rutaestacion.observaciones".
	" FROM rutaestacion LEFT JOIN estacion ON (rutaestacion.estacion=estacion.codigo)".
	" WHERE rutaestacion.ruta=";
	if( $_GET) {
		$myquery= $myquery.$_GET["id"];
	} else {
		$myquery= $myquery.$_POST["bus"];
	}
    $result = query($myquery, $link,$_SESSION['user'], $_SESSION['passwd']);
	if( !$result || !mysql_num_rows($result)) {
		echo "<br><p aling=\"center\">No hay Estaciones para esta ruta</p><br>";
	} else {
	?>
	<hr>
	<table border=1>
<tr>
<td><b>Codigo</b></td>
 <td><b>Estacion</b></td>
  <td><b>Observaciones</b></td>
 </tr>
	<?php
	 	for( $i=0; $i<mysql_num_rows($result); $i++) {
			if( !mysql_data_seek($result, $i)) {
				echo "no se puede encontrar la columna".$i;
				continue;
			}
			if( !($row= mysql_fetch_array($result))) {
				echo "error seleccionando array".$i;
				continue;
			}
			echo "<tr>\n";
			for( $h=0; $h<mysql_num_fields($result); $h++) {
				echo "<td>";
				if( $h==0) 
					echo "<a href=\"bus.php?id=".$row[$h]."\">".$row[$h]."</a>";
				else {
					echo $row[$h];
				}
				echo "</td>\n";
			}
			echo "</tr>\n";
		}
?>
</table>
<?php
	}
?>
<br><br>

<div align="center">
  <form name="form1" method="post" action="">
    <table border="0">
      <tr>
        <td><div align="left">Estacion</div></td>
        <td><div align="left">
          <select name="conductor" id="conductor">
		  <?php
	  		$myquery= "SELECT codigo,nombre FROM estacion";
			$result = query($myquery, $link,$_SESSION['user'], $_SESSION['passwd']);
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
          <input type="hidden" name="bus" value=<?php echo "\"";
		  	if( $_GET) {
				echo $_GET["id"]."\"";;
			} else {
				echo $_POST["bus"]."\"";;
			}?>>
        </div></td>
      </tr>
      <tr>
        <td><div align="left">Observaciones</div></td>
        <td><div align="left">
          <input name="observacion" type="text" id="observacion">
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
<hr>
<br><div align="right"><a href="/trans/ruta.php">Volver</a></div>
<?php
	genericfooter();
?>

