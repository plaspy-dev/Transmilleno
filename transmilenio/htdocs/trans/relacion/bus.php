<?php 
	header('Pragma: no-cache');
	session_start();
	if( !isset($HTTP_SESSION_VARS['user'])) {
		header("Location: /index.php");
		exit();
	}
	include("../../include/template.php");
	genericheader("Rutas y Turnos de buses");
	include("../../include/funciones.php");
	
	if($_POST && isset($_POST["Submit"])) {

		$link= NULL;
		$realquery= "INSERT INTO ";
		if( $_POST["type"]==1) {
			$realquery.= "busconductorturno";
		} else {
			$realquery.= "busconductorruta";
		}
		$realquery.= " VALUES( ";
		$realquery.= "NULL,";
		$realquery.= "\"".$_POST["bus"]."\",";
		$realquery.= "\"".$_POST["conductor"]."\",";
		$realquery.= "\"".$_POST["ruta"]."\",";
		$realquery.= "\"".$_POST["fecha"]."\")";
		query($realquery, $link,$HTTP_SESSION_VARS['user'],$HTTP_SESSION_VARS['passwd']);
		mysql_close($link);
	}
	
	$link= NULL;
	$myquery= "SELECT busconductorruta.codigo,CONCAT(conductor.nombre, \" \",conductor.apellido), busconductorruta.codigo, busconductorruta.fecha".
	" FROM busconductorruta LEFT JOIN conductor ON (busconductorruta.conductor=conductor.cedula)".
	" WHERE busconductorruta.bus=";
	if( $_GET) {
		$myquery= $myquery.$_GET["id"];
	} else {
		$myquery= $myquery.$_POST["bus"];
	}
    $result = query($myquery, $link,$HTTP_SESSION_VARS['user'], $HTTP_SESSION_VARS['passwd']);
	if( !$result || !mysql_num_rows($result)) {
		echo "<br><p aling=\"center\">No hay Rutas para este Bus</p><br>";
	} else {
	?>
	<hr>
	<table border=1>
<tr>
<td><b>Codigo</b></td>
 <td><b>Conductor</b></td>
  <td><b>Ruta</b></td>
  <td><b>Fecha</b></td>
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
		<input type="hidden" name="type" value="0">
          <select name="conductor" id="conductor">
		  <?php
	  		$myquery= "SELECT CAST(cedula AS SIGNED),CONCAT(nombre, \" \", apellido) FROM conductor WHERE tipovehiculo=1";
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
        <td><div align="left">Ruta</div></td>
        <td><div align="left">
          <select name="ruta" id="ruta">
		  <?php
		$myquery= "SELECT codigo,codigo FROM ruta";
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
        <td><div align="left">Fecha de la Ruta </div></td>
        <td><input name="fecha" type="text" id="fecha"></td>
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
<?php
	$link= NULL;
	$myquery= "SELECT busconductorturno.codigo,CONCAT(conductor.nombre, \" \",conductor.apellido), busconductorturno.codigo, busconductorturno.fecha".
	" FROM busconductorturno LEFT JOIN conductor ON (busconductorturno.conductor=conductor.cedula)".
	" WHERE busconductorturno.bus=";
	if( $_GET) {
		$myquery= $myquery.$_GET["id"];
	} else {
		$myquery= $myquery.$_POST["bus"];
	}
    $result = query($myquery, $link,$HTTP_SESSION_VARS['user'], $HTTP_SESSION_VARS['passwd']);
	if( !$result || !mysql_num_rows($result)) {
		echo "<br><p aling=\"center\">No hay Turnos para este Bus</p><br>";
	} else {
	?>
	<hr>
	<table border=1>
<tr>
<td><b>Codigo</b></td>
 <td><b>Conductor</b></td>
  <td><b>Turno</b></td>
  <td><b>Fecha</b></td>
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
?><br>

<div align="center">
  <form name="form2" method="post" action="">
		<input type="hidden" name="type" value="1">
    <table border="0">
      <tr>
        <td><div align="left">Conductor</div></td>
        <td><div align="left">
          <select name="conductor" id="conductor">
		  <?php
	  		$myquery= "SELECT CAST(cedula AS SIGNED),CONCAT(nombre, \" \", apellido) FROM conductor  WHERE tipovehiculo=1";
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
        <td><div align="left">Fecha del Turno</div></td>
        <td><input name="fecha" type="text" id="fecha"></td>
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
<br><div align="right"><a href="/trans/buses.php">Volver</a></div>
<?php
	genericfooter();
?>

