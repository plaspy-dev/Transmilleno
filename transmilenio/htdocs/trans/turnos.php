<?php 
	header('Pragma: no-cache');
	session_start();
	if( !isset($_SESSION['user'])) {
		header("Location: /index.php");
		exit();
	}
	include("../include/template.php");
	genericheader("Turnos");
	include("../include/funciones.php");
	
	$myquery= "SELECT codigo,horainicio,horafin FROM turno";
    $result = query($myquery, $link,$_SESSION['user'], $_SESSION['passwd']);
	if( !$result || !mysql_num_rows($result)) {
		echo "<br><p aling=\"center\">No hay Turnos</p><br>";
	} else {
	?>
<table border=1>
<tr>
<td><b>Codigo</b></td>
 <td><b>Inicio</b></td>
  <td><b>Fin</b></td>
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
					echo "<a href=\"turnos.php?id=".$row[$h]."\">".$row[$h]."</a>";
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
<div align="center"><a href="add/turnos.php">Agregar Nuevo Turno</a></div>
<?php
	genericfooter();
?>

  