<?php 
	header('Pragma: no-cache');
	session_start();
	if( !isset($HTTP_SESSION_VARS['user'])) {
		header("Location: /index.php");
		exit();
	}
	include("../include/template.php");
	genericheader("Buses");
	include("../include/funciones.php");
	
	$myquery= "SELECT bus.codigo,bus.placa,bus.dueno,bus.modelo,parqueadero.nombre,COUNT(busconductorruta.codigo) FROM bus".
	" LEFT JOIN parqueadero ON (parqueadero.codigo=bus.parqueadero) LEFT JOIN busconductorruta ON (busconductorruta.bus=bus.codigo) ".
	"GROUP BY bus.codigo";
    $result = query($myquery, $link,$HTTP_SESSION_VARS['user'], $HTTP_SESSION_VARS['passwd']);
	if( !$result || !mysql_num_rows($result)) {
		echo "<br><p aling=\"center\"> No hay Buses</p><br>";
	} else {
	?>
<table border=1>
<tr>
<td><b>Codigo del Bus</b></td>
 <td><b>Placa</b></td>
  <td><b>Dueño</b></td>
  <td><b>modelo</b></td>
  <td><b>Parqueadero</b></td>
  <td><b>Rutas</b></td>
  <td><b>Turnos</b></td>
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
				if( $h==0) {
					$id=$row[$h];
					echo "<a href=\"relacion/bus.php?id=".$row[$h]."\">".$row[$h]."</a>";
				} else {
					echo $row[$h];
				}
				echo "</td>\n";
				if( $h==5) {
					echo "<td>";
					$myquery= "SELECT COUNT(*) FROM busconductorturno WHERE bus=".$id;
					$result1 = query($myquery, $link1,$HTTP_SESSION_VARS['user'], $HTTP_SESSION_VARS['passwd']);
					if( !$result1 || !mysql_num_rows($result1)) {
						echo "0";
					} else {
						$row=mysql_fetch_array($result1);
						echo $row[0];
					}
					echo "</td>";
				}
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
<div align="center"><a href="add/bus.php">Agregar Nuevo Bus 
</a></div>
<?php
	genericfooter();
?>

  