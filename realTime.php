<?php

include 'connAndroidDb.php';

qGetInstantRecharge($_POST['idUser'], "2006-07-09 00:00:00");

function qGetInstantRecharge($idUser,$realTimeDate){
    $db=connect();
    try{
        $sql=$db->prepare("SELECT kwTot, importoTot, kwInst FROM
                        History_Charge
                        WHERE fkUser = :idUser
                        AND dataStop = :realTimeDate");
        $sql->bindParam(':idUser',$idUser);
        $sql->bindParam(':realTimeDate',$realTimeDate);
        if($sql->execute()){
            if ($row = $sql->rowCount()>0){
                $res = $sql->fetch();
                $response['success'] = true;
                $response['kwTot'] = $res['kwTot'];
                $response['kwInst'] = $res['kwInst'];
                $response['importoTot'] = $res['importoTot'];
                echo json_encode($response);
            }else{
                $response=array();
                $response['success'] = false;
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