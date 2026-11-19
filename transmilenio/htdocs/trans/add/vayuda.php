<?php 
	header('Pragma: no-cache');
	session_start();
	if( !isset($_SESSION['user'])) {
		header("Location: /index.php");
		exit();
	}
	include("../../include/funciones.php");
	if($_POST && isset($_POST["Submit"])) {

		$link= NULL;
		$realquery= "INSERT INTO ayuda VALUES( ";
		$realquery.= "\"".$_POST["codigo"]."\",";
		$realquery.= "\"".$_POST["placa"]."\",";
		$realquery.= "\"".$_POST["dueno"]."\",";
		$realquery.= "\"".$_POST["ingreso"]."\",";
		$realquery.= "\"".$_POST["modelo"]."\",";
		$realquery.= "\"".$_POST["caracteristica"]."\",";
		$realquery.= "\"".$_POST["parqueadero"]."\", 1,1,1)";
		query($realquery, $link,$_SESSION['user'],$_SESSION['passwd']);
		mysql_close($link);
		header("Location: /trans/vayuda.php");
		exit();
	}
	include("../../include/template.php");
	genericheader("Nuevo Vehiculo de Ayuda");
?>
  <div align="center">Nuevo Vehiculo de Ayuda</div>
  <br>
<div align="center">
  <form name="form1" method="post" action="">
    <table border="0">
      <tr>
        <td><div align="left">codigo</div></td>
        <td><input name="codigo" type="text" id="codigo"></td>
      </tr>
      <tr>
        <td><div align="left">placa</div></td>
        <td><input name="placa" type="text" id="placa"></td>
      </tr>
      <tr>
        <td><div align="left">due&ntilde;o</div></td>
        <td><input name="dueno" type="text" id="dueno"></td>
      </tr>
      <tr>
        <td><div align="left">fecha ingreso </div></td>
        <td><input name="ingreso" type="text" id="ingreso"></td>
      </tr>
      <tr>
        <td><div align="left">modelo</div></td>
        <td><input name="modelo" type="text" id="modelo"></td>
      </tr>
      <tr>
        <td><div align="left">caracteristica</div></td>
        <td><input name="caracteristica" type="text" id="caracteristica"></td>
      </tr>
	  <tr>
      <td><div align="left">Parqueadero</div></td>
      <td><div align="left">
        <select name="parqueadero">
	      <?php
		$myquery= "SELECT codigo, nombre FROM parqueadero";
	    $result = query($myquery, $link,$_SESSION['user'], $_SESSION['passwd']);
		if( !$result || !mysql_num_rows($result)) {
			echo "algo";
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
<br><div align="right"><a href="/trans/vayuda.php">Volver</a></div>
<?php
genericfooter();
?>
