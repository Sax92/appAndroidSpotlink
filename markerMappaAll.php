<?php
 require 'connAndroidDb.php';	
qGetTowerMarkerAll($_POST['idUser']);
	//torrette mappa tutte
 function qGetTowerMarkerAll($idUser){
	 		$response = array();
			$i=0;
        global $temp;
        $db=connect();
        try{
						$sql=$db->prepare("SELECT codice 
									FROM User
									JOIN (User_Coupon JOIN Coupon
                                    ON User_Coupon.fkCoupon = Coupon.idCoupon)
									ON User.idUser=User_Coupon.fkUser
									WHERE idUser= :idUser LIMIT 0,1");
				$sql->bindParam(':idUser',$idUser);
				if($sql->execute()){
					if ($row = $sql->rowCount()>0){
        		while ($res = $sql->fetch()){
							$temp=$res['codice'];  
							$sql2=$db->prepare("SELECT address,latitudine,longitudine 
																	FROM Tower 
																	WHERE idTower NOT IN (SELECT idTower
																				FROM Coupon JOIN
																				(User_Coupon
																				JOIN (User JOIN Tower ON User.idUser=Tower.fkUser)
																				ON User_Coupon.fkUser=User.idUser)
																				ON Coupon.idCoupon = User_Coupon.fkCoupon
																				WHERE ruolo= :gestore AND codice= :temp)");
            	$gest="gestore";
            	$sql2->bindParam(':gestore',$gest);
            	$sql2->bindParam(':temp',$temp);                                         
            	if($sql2->execute()){
                if ($row2 = $sql2->rowCount()>0){
										while($res2 = $sql2->fetch()){
                        $response[$i]['address']=$res2['address'];
												$response[$i]['latitudine']=$res2['latitudine'];
												$response[$i]['longitudine']=$res2['longitudine'];
												$i++;
                    }
										echo json_encode($response);
                }
            	}else{
                	echo json_encode($response);
							}	
						}
					}
					}else{		
					//$response['success']=false;
          //echo json_encode($response);
					echo json_encode($response);
					}	
		}catch(PDOException $ex){
			$ex->getMessage();
			echo json_encode($response);
		}
		$db=null;	         
    }
	?>