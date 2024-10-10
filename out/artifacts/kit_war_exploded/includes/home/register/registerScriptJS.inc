
<script type="text/javascript">
    function headerOnLoadHandler() {
        let firstName = document.querySelector("#firstName");
        let languageCodePreferences = document.querySelector("#languageCodePreferences");
        let secondName = document.querySelector("#secondName");
        let password = document.querySelector("#password");
        let email = document.querySelector("#email");
        let TextMsg = " \xE8 obbligatorio.";


        if (firstName!==undefined && secondName!==undefined && password!==undefined && email!== undefined ) {

            firstName.setCustomValidity("First name "+TextMsg);
            firstName.addEventListener("change", function () {
                console.log(this.validity.valueMissing)
                this.setCustomValidity(this.validity.valueMissing ? "First name "+TextMsg : "");
            });

            secondName.setCustomValidity("Second name "+TextMsg);
            secondName.addEventListener("change", function () {
                this.setCustomValidity(this.validity.valueMissing ? "Second name "+TextMsg : "");
            });

            password.setCustomValidity("Password "+TextMsg);
            password.addEventListener("change", function () {
                this.setCustomValidity(this.validity.valueMissing ? "Password "+TextMsg : "");
            });

            email.setCustomValidity("E-mail "+TextMsg);
            email.addEventListener("change", function () {
                this.setCustomValidity(this.validity.valueMissing ? "E-mail "+TextMsg : "");
            });

            languageCodePreferences.setCustomValidity("this field"+TextMsg);
            languageCodePreferences.addEventListener("change", function () {
                this.setCustomValidity(this.validity.valueMissing ? "this field"+TextMsg : "");
            });
        }
    }

    window.addEventListener('load',headerOnLoadHandler)
</script>