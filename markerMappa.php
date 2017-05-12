<?php
    require 'connAndroidDb.php';	
//torrette mappa utente
    qGetTowerMarkerUser($_POST['idUser']);
				//torrette mappa utente
		function qGetTowerMarkerUser($idUser){
			$response = array();
			$i=0;
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
																					FROM Coupon JOIN
																					(User_Coupon
																					JOIN (User JOIN Tower ON User.idUser=Tower.fkUser)
																					ON User_Coupon.fkUser=User.idUser)
																					ON Coupon.idCoupon = User_Coupon.fkCoupon
																					WHERE ruolo= :gestore AND codice= :temp");
                            $gest="gestore";
                            $sql2->bindParam(':gestore',$gest);
                            $sql2->bindParam(':temp',$temp);
                            if($sql2->execute()){
															//$response[0]['success']=true;
                                if ($row2 = $sql->rowCount()>0){
                                    while($res2 = $sql2->fetch()){
                                        $response[$i]['address']=$res2['address'];
																				$response[$i]['latitudine']=$res2['latitudine'];
																				$response[$i]['longitudine']=$res2['longitudine'];
																				$i++;
                                    }
																	echo json_encode($response);
                                }
                            }else{
                                //$response['success']=false;
                                echo json_encode($response);
                            }
                        }
				        	  
					}
								
				}else{		
					//$response['success']=false;
          //echo json_encode($response);
					return false;
				}	
                
                
                
		}catch(PDOException $ex){
			$ex->getMessage();
			//$response['success']=false;
      //echo json_encode($response);
				return false;
		}
		$db=null;	         
    }

?>
