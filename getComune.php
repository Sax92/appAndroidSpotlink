<?php
    require 'connAndroidDb.php';

    qGetComuni($_POST['prov']);
    
    function qGetComuni($prov){
        $db = connect();
		try{
			// preparazione della query 
			$sql = $db->prepare('SELECT * FROM Comuni WHERE fkNomeP = :prov ORDER BY nomeC');
            $sql->bindParam(':prov', $prov);
			if ($sql->execute()){
                while ($res = $sql->fetch()){
                    $result[] = $res['nomeC'];
                }
                echo json_encode($result);
            }
		}catch(PDOException $ex){
			$ex->getMessage();
		}
        
		$db=null;
    }

?>