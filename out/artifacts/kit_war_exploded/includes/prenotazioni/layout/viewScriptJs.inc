<script>
    function  viewPrenotazione(prenotazioneID){
        document.viewPrenotazioneForm.prenotazioneID.value = prenotazioneID
        document.viewPrenotazioneForm.submit()
    }

    function  editPrenotazione(prenotazioneID){
        document.editPrenotazioneForm.prenotazioneID.value = prenotazioneID
        document.editPrenotazioneForm.submit()
    }

    function viewTemplate(templateID) {
        document.viewTemplateForm.templateID.value = templateID
        document.viewTemplateForm.submit()
    }

    function  deletePrenotazione(prenotazioneID){
        let confirmation = confirm("confermi la cancellazione di questa prenotazione ?")
        if (confirmation) {
            document.deletePrenotazioneForm.prenotazioneID.value = prenotazioneID
            document.deletePrenotazioneForm.submit()
        }
    }

    function  searchByAccept (){
        document.acceptForm.submit()
    }

    function  searchByPlatform (){
        document.platformForm.submit()
    }

    function getByPlatform(){
        document.platformMapForm.submit()
    }

</script>