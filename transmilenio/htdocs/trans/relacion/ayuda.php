<?php 
	header('Pragma: no-cache');
	session_start();
	if( !isset($HTTP_SESSION_VARS['user'])) {
		header("Location: /index.php");
		exit();
	}
	include("../../include/template.php");
	genericheader("Turnos de Vehiculos de Ayuda");
	include("../../include/funciones.php");
	
	if($_POST && isset($_POST["Submit"])) {

		$link= NULL;
		$realquery= "INSERT INTO conductorayuda VALUES ( ";
		$realquery.= "NULL,";
		$realquery.= "\"".$_POST["conductor"]."\",";
		$realquery.= "\"".$_POST["bus"]."\",";
		$realquery.= "\"".$_POST["ruta"]."\")";
		query($realquery, $link,$HTTP_SESSION_VARS['user'],$HTTP_SESSION_VARS['passwd']);
		mysql_close($link);
	}
	
	$link= NULL;
	$myquery= "SELECT conductorayuda.codigo,CONCAT(conductor.nombre, \" \",conductor.apellido), conductorayuda.turno".
	" FROM conductorayuda LEFT JOIN conductor ON (conductorayuda.conductor=conductor.cedula)".
	" WHERE conductorayuda.ayuda=";
	if( $_GET) {
		$myquery= $myquery.$_GET["id"];
	} else {
		$myquery= $myquery.$_POST["bus"];
	}
    $result = query($myquery, $link,$HTTP_SESSION_VARS['user'], $HTTP_SESSION_VARS['passwd']);
	if( !$result || !mysql_num_rows($result)) {
		echo "<br><p aling=\"center\">No hay Turnos para este Vehiculo</p><br>";
	} else {
	?>
	<hr>
	<table border=1>
<tr>
<td><b>Codigo</b></td>
 <td><b>Conductor</b></td>
  <td><b>Turno</b></td>
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
        <td><div align="left">Conductor</div></td>
        <td><div align="left">
          <select name="conductor" id="conductor">
		  <?php
	  		$myquery= "SELECT CAST(cedula AS SIGNED),CONCAT(nombre, \" \", apellido) FROM conductor  WHERE tipovehiculo=2";
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
          <input type="hidden" name="bus" value=<?php echo "\"";
		  	if( $_GET) {
				echo $_GET["id"]."\"";;
			} else {
				echo $_POST["bus"]."\"";;
			}?>>
        </div></td>
      </tr>
      <tr>
        <td><div align="left">Turno</div></td>
        <td><div align="left">
          <select name="ruta" id="ruta">
		  <?php
		$myquery= "SELECT codigo,codigo FROM turno";
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
<br><div align="right"><a href="/trans/vayuda.php">Volver</a></div>
<?php
	genericfooter();
?>

