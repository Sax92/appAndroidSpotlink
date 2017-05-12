<?php
    require 'connAndroidDb.php';  
    
    qGetRecharge($_POST['idUser']);

//ULTIME RICARICHE
	function qGetRecharge($idUser){
		$db=connect();
		try{
			$sql=$db->prepare("SELECT DATE_FORMAT(dataStart,'%d/%m/%Y %H:%i') AS dataStart, DATE_FORMAT(dataStop,'%d/%m/%Y %H:%i') AS dataStop, kwTot, importoTot, address FROM ".
							"History_Charge JOIN Tower ".
							"ON History_Charge.fkTower=Tower.idTower ". 
							"WHERE History_Charge.fkUser = :idUser ".
                            "AND dataStop <> :dataRealTime ".  
							"ORDER BY dataStart DESC LIMIT 0,30");
            $dataRealTime='2006-07-09 00:00:00';
			$sql->bindParam(':idUser',$idUser);
            $sql->bindParam(':dataRealTime', $dataRealTime);
			if($sql->execute()){
				if ($row = $sql->rowCount()>0){
					while($res = $sql->fetch()){
						$response[]=$res;
					}
					echo json_encode($response);
				}else{
                    $response=array();
                    echo json_encode($response);
                }
			}else{
				return false;
			}	
		}catch(PDOException $ex){
			$ex->getMessage();
		}
		$db=null;
	}



?>