<?php 
	header('Pragma: no-cache');
	session_start();
	if( !isset($HTTP_SESSION_VARS['user'])) {
		header("Location: /index.php");
		exit();
	}
	include("../include/template.php");
	genericheader("Repuestos");
	include("../include/funciones.php");
	
	$myquery= "SELECT repuesto.codigo,repuesto.nombre,repuesto.precio,repuesto.fechacoloacion FROM repuesto";
    $result = query($myquery, $link,$HTTP_SESSION_VARS['user'], $HTTP_SESSION_VARS['passwd']);
	if( !$result || !mysql_num_rows($result)) {
		echo "<br><p aling=\"center\"> No hay Repuestos</p><br>";
	} else {
	?>
<table border=1>
<tr>
<td><b>Codigo</b></td>
 <td><b>Nombre</b></td>
  <td><b>Precio</b></td>
  <td><b>Fecha de Colocacion</b></td>

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
					echo "<a href=\"relacion/repuesto.php?id=".$row[$h]."\">".$row[$h]."</a>";
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
<div align="center"><a href="add/repuesto.php">Agregar Nuevo Repuesto</a></div>
<?php
	genericfooter();
?>

  