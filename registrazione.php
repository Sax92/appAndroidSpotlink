<?php

require 'connAndroidDb.php';



qRegister($_POST['nome'],$_POST['cognome'],$_POST['email'], $_POST['password'], $_POST['fisso'], $_POST['cel'], $_POST['data_nascita'], $_POST['citta'], $_POST['indirizzo'], $_POST['pIva'], $_POST['CF']);
        
	
//REGISTRAZIONE UTENTI
    function qRegister($nome,$cognome,$email,$password,$fisso,$cel,$data_nascita,$citta,$indirizzo,$pIva,$cf){
        $response = array();
		$db = connect();
		// preparazione della query 
		$sql = $db->prepare("INSERT INTO User (nome, cognome, email, password, ruolo, indirizzo, citta, data_nascita, cellulare, telefono, pIva, CF)".
							"VALUES (:nome,:cognome,:email,:password,:ruolo,:indirizzo,:citta,:data_nascita,:cel,:fisso,:pIva,:CF)");
		try{
			// esecuzione della query
			$pwdhashed=md5($password); //hashing password
			$ruolo="user";
            if ($nome == ''){
                $nome = NULL;
            }else{
                $nome = ucfirst($nome);
            }
            if ($cognome == ''){
                $cognome = NULL;
            }else{
                $cognome=ucfirst($cognome);
            }
            if ($indirizzo == ''){
                $indirizzo = NULL;
            }
            if ($citta == ''){
                $citta = NULL;
            }else{
                $citta = ucfirst($citta);
            }
            if ($data_nascita == 0000-00-00){
                $data_nascita = NULL;
            }
            if ($fisso == ''){
                $fisso = NULL;
            }
            if ($pIva == ''){
                $pIva = NULL;
            }else{
                $pIva = strtoupper($pIva);
            }
            if ($cf == ''){
                $cf = NULL;
            }else{
                $cf = strtoupper($cf);
            }
            if($cel == ''){
                $cel = NULL;
            }
            
			$sql->bindParam(':nome',$nome);
			$sql->bindParam(':cognome',$cognome);	
			$sql->bindParam(':email',$email);
			$sql->bindParam(':password',$pwdhashed);
			$sql->bindParam(':ruolo',$ruolo);
			$sql->bindParam(':indirizzo',$indirizzo);
			$sql->bindParam(':citta',$citta);
			$sql->bindParam(':data_nascita',$data_nascita);
			$sql->bindParam(':cel',$cel);
			$sql->bindParam(':fisso',$fisso);
			$sql->bindParam(':pIva',$pIva);
			$sql->bindParam(':CF',$cf);	
			if($sql->execute()){
                //adesso genero codice coupon per utente
                $idUser=$db->lastInsertId();
                $trovato=false;
				while ($trovato==false){
                    $code=makeUnique(6);
                    $sql=$db->prepare("SELECT codice FROM Coupon WHERE codice = :codice");
                    $sql->bindParam(':codice',$code);
                    if ($sql->execute()){
                        if ($row = $sql->rowCount()>0){
                            $code=makeUnique(6);
                        }else{
                            $sql=$db->prepare("INSERT INTO Coupon (codice, tipo)
                                                VALUES (:codice, :provvisorio)");
                            $provvisorio='provvisorio';
                            $sql->bindParam(':codice', $code);
                            $sql->bindParam(':provvisorio', $provvisorio);
                            if ($sql->execute()){
                                $id = $db->lastInsertId();
                                $sql = $db->prepare("INSERT INTO User_Coupon VALUES (:idUser, :idCoupon)");
                                $sql->bindParam(':idUser',$idUser);
                                $sql->bindParam(':idCoupon',$id);
                                if ($sql->execute()){
                                    $trovato=true;
                                }
                            }
                        }
                    }
                }
                if ($trovato==true){
                    $response['success']=true;
                    echo json_encode($response);
                }
			}else{
                $response['success']=false;
                echo json_encode($response);
            }
			
		}catch(PDOException $ex){
			$ex->getMessage();
			return false;
		}
		$db=null;						
	}

    function makeUnique ($length) {
           $salt       = '0123456789';
           $len        = strlen($salt);
           $makepass   = '';
           mt_srand(10000000*(double)microtime());
           for ($i = 0; $i < $length; $i++) {
               $makepass .= $salt[mt_rand(0,$len - 1)];
           }
       	   return $makepass;
    }

?>
