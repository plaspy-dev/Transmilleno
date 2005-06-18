<?php 
	header('Pragma: no-cache');
	session_start();
	if( !isset($HTTP_SESSION_VARS['user'])) {
		header("Location: /index.php");
		exit();
	}
	include("../include/template.php");
	genericheader("Conductores");
	include("../include/funciones.php");
	
	$myquery= "SELECT CAST(cedula AS UNSIGNED),nombre,apellido,CAST(npase AS UNSIGNED),ncategoria,".
	"IF(tipovehiculo=1, \"Bus\", IF(tipovehiculo=2, \"Ayuda\", \"Valores\")) FROM conductor";
    $result = query($myquery, $link,$HTTP_SESSION_VARS['user'], $HTTP_SESSION_VARS['passwd']);
	if( !$result || !mysql_num_rows($result)) {
		echo "<br><p aling=\"center\"> No hay Conductores</p><br>";
	} else {
	?>
<table border=1>
<tr>
<td><b>Cedula</b></td>
<td><b>Nombre</b></td>
<td><b>Apellido</b></td>
<td><b>N Pase</b></td>
<td><b>N Categoria</b></td>
<td><b>Vehiculo</b></td>
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
					echo "<a href=\"conductores.php?id=".$row[$h]."\">".$row[$h]."</a>";
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
<br>
<br>
<div align="center"><a href="add/conductor.php">Agregar Nuevo Conductor</a></div>
<?php
	genericfooter();
?>

  