<?php

include 'connAndroidDb.php';

qUpdateUser($_POST['idUser'], $_POST['nome'], $_POST['cognome'], $_POST['email'], $_POST['data_nascita'], $_POST['indirizzo'], $_POST['citta'], $_POST['cellulare'], $_POST['telefono'], $_POST['cf'], $_POST['pIva'], $_POST['provincia'], $_POST['comune'], $_POST['cap']);

//AGGIORNAMENTO PROFILO
function qUpdateUser($idUser, $nome, $cognome, $email, $data_nascita, $indirizzo, $citta, $cellulare, $telefono, $cf, $pIva, $provincia, $comune, $cap){
    $db=connect();
    try{
        $sql=$db->prepare("UPDATE User SET email = :email, 
                            nome = :nome, 
                            cognome = :cognome,
                            indirizzo = :indirizzo,  
                            citta = :citta,  
                            CF = :cf,
                            cellulare = :cellulare,
                            telefono = :telefono,
                            data_nascita = :data_nascita,
                            provincia = :provincia,
                            comune = :comune,
                            CAP = :cap,
                            pIva = :pIva
                            WHERE idUser = :idUser");

        $sql->bindParam(':idUser',$idUser);									
        $sql->bindParam(':email',$email);
        $sql->bindParam(':nome', ucfirst($nome));
        $sql->bindParam(':cognome',ucfirst($cognome));

        if (($indirizzo=='') or ($indirizzo=='-')){
            $indirizzo=NULL;
        }
        if (($citta=='') or ($citta=='-')){
            $citta=NULL;
        }else{
            $citta=ucfirst($citta);
        }
        if (($cellulare=='') or ($cellulare=='-')){
            $cellulare=NULL;
        }
        if (($telefono=='') or ($telefono=='-')){
            $telefono=NULL;
        }
        if (($data_nascita==0000-00-00) or ($data_nascita=='-')){
            $data_nascita=NULL;
        }
        if (($pIva=='') or ($pIva=='-')){
            $pIva=NULL;
        }else{
            $pIva=strtoupper($pIva);
        }
        if (($cf=='') or ($cf=='-')){
            $cf=NULL;
        }else{
            $cf=strtoupper($cf);
        }

        if ($comune == ''){
            $comune = NULL;
        }else{
            $comune = ucfirst($comune);
        }

        if ($provincia == ''){
            $provincia = NULL;
        }else{
            $provincia = ucfirst($provincia);
        }

        if ($cap == ''){
            $cap = NULL;
        }

        $sql->bindParam(':indirizzo',$indirizzo);
        $sql->bindParam(':citta',$citta);
        $sql->bindParam(':cf',$cf);
        $sql->bindParam(':cellulare',$cellulare);
        $sql->bindParam(':telefono',$telefono);
        $sql->bindParam(':data_nascita',$data_nascita);
        $sql->bindParam(':pIva',$pIva);
        $sql->bindParam(':provincia',$provincia);
        $sql->bindParam(':comune',$comune);
        $sql->bindParam(':cap',$cap);
        if($sql->execute()){
            $sql=$db->prepare("SELECT * FROM User WHERE idUser = :idUser");
            $sql->bindParam(':idUser', $idUser);
            if ($sql->execute()){
                if ($row = $sql->rowCount()>0){
                    $res = $sql->fetch();
                    $response['success']=true;
                    $response['email'] = $res['email'];
                    $response['idUser'] =  $res['idUser'];
                    $response['nome'] = $res['nome'];
                    $response['cognome'] = $res['cognome'];
                    $response['provincia'] = $res['provincia'];
                    $response['comune'] = $res['comune'];
                    $response['CAP'] = $res['CAP'];
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
            }
        }else{
            $response['success']=false;
            echo json_encode($response);
        }	
    }catch(PDOException $ex){
        $ex->getMessage();
        return false;
    }	
}

?>    