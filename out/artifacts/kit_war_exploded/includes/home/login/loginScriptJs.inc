
<script type="text/javascript">
    function headerOnLoadHandler() {
        let password = document.querySelector("#password");
        let email = document.querySelector("#email");
        let TextMsg = " \xE8 obbligatorio.";


        if ( password!==undefined && email!== undefined ) {
            password.setCustomValidity("Password "+TextMsg);
            password.addEventListener("change", function () {
                this.setCustomValidity(this.validity.valueMissing ? "Password "+TextMsg : "");
            });

            email.setCustomValidity("E-mail "+TextMsg);
            email.addEventListener("change", function () {
                this.setCustomValidity(this.validity.valueMissing ? "E-mail "+TextMsg : "");
            });
        }
    }
    window.addEventListener('load',headerOnLoadHandler)
</script>