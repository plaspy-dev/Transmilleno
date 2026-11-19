<?php 
	header('Pragma: no-cache');
	session_start();
	if( !isset($_SESSION['user'])) {
		header("Location: /index.php");
		exit();
	}
	include("../include/template.php");
	genericheader("Vehiculos de ayuda");
	include("../include/funciones.php");
	
	$myquery= "SELECT ayuda.codigo,ayuda.placa,ayuda.dueno,ayuda.modelo,parqueadero.nombre,COUNT(conductorayuda.codigo) FROM ayuda ".
	"LEFT JOIN parqueadero ON (parqueadero.codigo=ayuda.parqueadero) LEFT JOIN conductorayuda ON (conductorayuda.ayuda=ayuda.codigo) "
	."GROUP BY ayuda.codigo";
    $result = query($myquery, $link,$_SESSION['user'], $_SESSION['passwd']);
	if( !$result || !mysql_num_rows($result)) {
		echo "<br><p aling=\"center\"> No hay Vehiculos</p><br>";
	} else {
	?>
<table border=1>
<tr>
<td><b>Codigo</b></td>
 <td><b>Placa</b></td>
  <td><b>Dueño</b></td>
  <td><b>modelo</b></td>
  <td><b>parqueadero</b></td>
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
				if( $h==0) 
					echo "<a href=\"relacion\ayuda.php?id=".$row[$h]."\">".$row[$h]."</a>";
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
<div align="center"><a href="add/vayuda.php">Agregar Nuevo Vehiculo
</a></div>
<?php
	genericfooter();
?>
