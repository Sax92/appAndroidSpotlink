<?php
    require 'connAndroidDb.php';

    qGetProvince();
	
    //recupero province
	 function qGetProvince(){
		$db = connect();
        try{
			// preparazione della query 
			$sql = $db->prepare('SELECT * FROM Province ORDER BY nomeP');
			if ($sql->execute()){
                while ($res = $sql->fetch()){
                    $response[] = $res['nomeP'];
                }
                echo json_encode($response);
            }
		}catch(PDOException $ex){
			$ex->getMessage();
		}
		
		$db=null;
	}

?>