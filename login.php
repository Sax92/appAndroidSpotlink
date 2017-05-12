<?php
    require 'connAndroidDb.php';

qLogin($_POST['email'], $_POST['password']);
	//LOGIN
	 function qLogin($email,$password){
		$response = array();
		$db =connect();
		try{
			// preparazione della query 
			$sql = $db->prepare('SELECT * FROM User WHERE email = :email and password = :password');
			//binding dei parametri
			$sql->bindParam(':email',$email);
            if (strlen($password)>10){
                $sql->bindParam(':password', $password);
            }else{
                $sql->bindParam(':password',md5($password));
            }
			
			// esecuzione della query 
			$sql->execute();
			// creazione di un array dei risultati
			if ($row = $sql->rowCount()>0){
				$res = $sql->fetch();
				$response['success']=true;
				$response['email'] = $res['email'];
				$response['idUser'] =  $res['idUser'];
				$response['nome'] = $res['nome'];
				$response['cognome'] = $res['cognome'];
				$response['indirizzo'] = $res['indirizzo'];
				$response['citta'] = $res['citta'];
				$response['CF'] = $res['CF'];
				$response['cellulare'] = $res['cellulare'];
				$response['telefono'] = $res['telefono'];
				$response['data_nascita'] = $res['data_nascita'];
				$response['ditta'] = $res['ditta'];
				$response['pIva'] = $res['pIva'];
				echo json_encode($response);
			}
			else{
				$response['success']=false;
				echo json_encode($response);
			}	
		}catch(PDOException $ex){
			$ex->getMessage();
		}
		
		$db=null;
	}
?>
