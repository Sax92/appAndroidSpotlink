<?php

//connessione DB
    function connect(){
		// collegamento al database con PDO
		$col = 'mysql:host=localhost;dbname=emotionV04';
			
		// blocco try per il lancio dell'istruzione
		try {
            // connessione tramite creazione di un oggetto PDO
            $db = new PDO($col , 'spotlink', 'spot2016');
            return $db;
		}
		// blocco catch per la gestione delle eccezioni
		catch(PDOException $e) {
            // notifica in caso di errorre
            echo 'Attenzione: '.$e->getMessage();
		}
    }

?>
