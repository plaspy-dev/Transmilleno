<?php
function validate($usuario, $password) {
		$link = mysql_connect("", $usuario,$password);
		if(!$link) 
			return false;
		mysql_close ($link);
		return true;
}

function initjava() {
?>
<SCRIPT LANGUAGE="JavaScript">
<!--
function OnlyNumbers(campo) {
	if( isNaN( campo.value) || campo.value=="") {
		alert("debe ingresar un numero en este campo");
		campo.focus();
		return false;
	}
	return true;
}
//-->
</SCRIPT>
<?php
}
function query($myquery, &$link, $usuario, $passwd) {

	if(!$link) 
		$link = mysql_connect("", $usuario, $passwd);
	if(!$link) {
		echo "error conectando a mysql<br>\n";
		return NULL;
	}
	if( !mysql_select_db("transmilenio", $link)) {
		echo "error seleccionando base de datos<br>\n";
		return NULL;
	}
	$result= mysql_query($myquery, $link);
/*	if( !$result) {
		echo "error realizando la consulta<br>\n";
		return NULL;
	}*/
	return $result;
}

function myshowex($realquery, $begin, $end,$id, &$link) {

	$result= query($realquery, $link);
	echo "<tr>\n";
	for( $h=0; $h<mysql_num_fields($result); $h++) {
		echo "<td><a href=\"realadmin.php?tipo=".$id."&sort=".mysql_field_name($result, $h);
		echo "\"><center><b>".mysql_field_name($result, $h)."</b></center></a></td>\n";
	}
	echo "</tr>\n";
					
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
			if($h) {
				echo "<td>".$row[$h]."</td>\n";
			} else {
				echo $begin;
				echo $row[$h];
				echo $end;
			}
		}
		echo "</tr>\n";
	}
	
mysql_free_result ($result);
mysql_close ($link);
}
?>
