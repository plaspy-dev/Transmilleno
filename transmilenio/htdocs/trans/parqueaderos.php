<?php 
	header('Pragma: no-cache');
	session_start();
	if( !isset($_SESSION['user'])) {
		header("Location: /index.php");
		exit();
	}
	include("../include/template.php");
	genericheader("Parqueaderos");
	include("../include/funciones.php");
	
	$myquery= "SELECT codigo,nombre,capacidad,IF(taller=1, \"si\", \"no\"),IF(bomba=1, \"si\", \"no\"),IF(labadero=1, \"si\", \"no\") FROM parqueadero";
    $result = query($myquery, $link,$_SESSION['user'], $_SESSION['passwd']);
	if( !$result || !mysql_num_rows($result)) {
		echo "<br><p aling=\"center\"> No hay Parqueaderos</p><br>";
	} else {
	?>
<table border=1>
<tr>
<td><b>Codigo</b></td>
<td><b>Nombre</b></td>
<td><b>Capacidad</b></td>
<td><b>Taller</b></td>
<td><b>Bomba</b></td>
<td><b>Lavadero</b></td>
<td><b>Buses</b></td>
<td><b>Ayuda</b></td>
<td><b>Valores</b></td>
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
					echo "<a href=\"parqueaderos.php?id=".$row[$h]."\">".$row[$h]."</a>";
					$id= $row[$h];
				} else {
					echo $row[$h];
				}
				echo "</td>\n";
				if( $h==5) {
					echo "<td>";
					$myquery= "SELECT COUNT(*) FROM bus WHERE parqueadero=".$id;
					$result1 = query($myquery, $link1,$_SESSION['user'], $_SESSION['passwd']);
					if( !$result1 || !mysql_num_rows($result1)) {
						echo "0";
					} else {
						$row=mysql_fetch_array($result1);
						echo $row[0];
					}
					echo "</td>\n<td>";
					$myquery= "SELECT COUNT(*) FROM ayuda WHERE parqueadero=".$id;
					$result1 = query($myquery, $link1,$_SESSION['user'], $_SESSION['passwd']);
					if( !$result1 || !mysql_num_rows($result1)) {
						echo "0";
					} else {
						$row=mysql_fetch_array($result1);
						echo $row[0];
					}
					echo "</td>\n<td>";
					$myquery= "SELECT COUNT(*) FROM dinero WHERE parqueadero=".$id;
					$result1 = query($myquery, $link1,$_SESSION['user'], $_SESSION['passwd']);
					if( !$result1 || !mysql_num_rows($result1)) {
						echo "0";
					} else {
						$row=mysql_fetch_array($result1);
						echo $row[0];
					}
					echo "</td>\n";
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
<div align="center"><a href="add/parqueadero.php">Agregar Nuevo Parqueadero
</a></div>
<?php
	genericfooter();
?>

