<script>
    function searchByRuolo() {
        document.ruoloForm.submit();
    }

    function  operationToUser(userID,operation,role=null){
        var msg ;


        if(operation === "changeRole"){
            var roleForChange = document.getElementById("changeRole_"+userID).value;
            msg = "Confermi la tranformation del user "+ role +" con ID : "+userID+"  in user "+ roleForChange +" ?";
            document.operationUser.role.value = roleForChange
        }else{
            msg = "Confermi l operatione di "+operation+" sul user con ID : "+userID+"?";
        }
        let confirmation = confirm(msg)
        if (confirmation){
            document.operationUser.operation.value = operation
            document.operationUser.userID.value = userID
            document.operationUser.submit()
        }else{
            if(operation==="changeRole")
            document.getElementById("changeRole_"+userID).options[0].selected = true
        }
    }
</script>