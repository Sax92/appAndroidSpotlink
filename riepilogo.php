<?php

include 'connAndroidDb.php';

$response = array();

$response[0] = qGetRechargeNumber($_POST['idUser']);
$response[1] = qGetTotKW($_POST['idUser']);
$response[2] = qCouponCode($_POST['idUser']);

echo json_encode($response);


//NUMERO RICARICHE EFFETTUATE
function qGetRechargeNumber($idUser){
    $db=connect();
    try{
        $sql=$db->prepare("SELECT COUNT(*) AS numR ".
                            "FROM History_Charge ".
                            "WHERE fkUser = :idUser");
        $sql->bindParam(':idUser',$idUser);
        if($sql->execute()){
            if ($row = $sql->rowCount()>0){
                $res = $sql->fetch();
                return $res['numR'];
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

//TOTALE KW RICARICATI
function qGetTotKW($idUser){
    $db=connect();
    try{
        $sql=$db->prepare("SELECT SUM(kwTot) AS tot ".
                            "FROM History_Charge ".
                            "WHERE fkUser = :idUser");
        $sql->bindParam(':idUser',$idUser);
        if($sql->execute()){
            if ($row = $sql->rowCount()>0){
                $res = $sql->fetch();
                if ($res['tot'] == NULL){
                    return 0;
                }else{
                    return $res['tot'];
                } 
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

//codice coupon
function qCouponCode($idUser){
    $db = connect();
    try{
        $sql = $db->prepare('SELECT codice
                            FROM User_Coupon JOIN Coupon
                            ON User_Coupon.fkCoupon = Coupon.idCoupon
                            WHERE fkUser = :idUser');
        //binding dei parametri
        $sql->bindParam(':idUser',$idUser);
        if ($sql->execute()){
            $res = $sql->fetch();
            return $res['codice'];
        }else{
            return false;
        }	
    }catch(PDOException $ex){
        $ex->getMessage();
    }

    $db=null;
}

?>