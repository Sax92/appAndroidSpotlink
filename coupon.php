<?php
    require 'connAndroidDb.php';  
    
    qGetCouponState($_POST['idUser']);

	//STATO COUPON
	function qGetCouponState($idUser){
		$db=connect();
		
		try{
			$sql=$db->prepare("SELECT User.ditta, tab1.fkCoupon, DATE_FORMAT(scadenza,'%d/%m/%Y') AS scadenza, tipo, descrizione, valore FROM
							User_Coupon tab1 JOIN (Coupon JOIN
							(User_Coupon tab2 JOIN User
							ON tab2.fkUser=User.idUser)
							ON Coupon.idCoupon=tab2.fkCoupon)
                            ON tab1.fkCoupon= tab2.fkCoupon
							WHERE tab1.fkUser = :idUser
                            AND tab1.fkCoupon=tab2.fkCoupon
                            AND tab2.fkUser <> tab1.fkUser");
			$sql->bindParam(':idUser',$idUser);
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
			return false;
		}
		$db=null;	
	}


?>