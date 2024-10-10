
<script>
    document.getElementById("ButtonCancellaAccount").addEventListener('click',cancellaAccount)
    function  cancellaAccount () {
        let cancel = confirm("conferma la cancellazione del tuo account ?? ")
        if(cancel)
            document.cancellaAccount.submit()
    }
</script>