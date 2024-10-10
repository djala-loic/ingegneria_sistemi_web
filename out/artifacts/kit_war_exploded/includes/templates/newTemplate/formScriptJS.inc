<script type="text/javascript">
    function headerOnLoadHandler() {
        let title = document.querySelector("#title");
        let language = document.querySelector("#language");
        let description = document.querySelector("#description");
        let code = document.querySelector("#code");

        let TextMsg = " \xE8 obbligatorio.";


        if (title!==undefined && description!==undefined && code!==undefined) {

            title.setCustomValidity("Title "+TextMsg);
            title.addEventListener("change", function () {
                console.log(this.validity.valueMissing)
                this.setCustomValidity(this.validity.valueMissing ? "Title "+TextMsg : "");
            });

           /* description.setCustomValidity("Description "+TextMsg);
            description.addEventListener("change", function () {
                this.setCustomValidity(this.validity.valueMissing ? "Description "+TextMsg : "");
            });

            code.setCustomValidity("Code "+TextMsg);
            code.addEventListener("change", function () {
                this.setCustomValidity(this.validity.valueMissing ? "Code "+TextMsg : "");
            });*/

            language.setCustomValidity("this field"+TextMsg);
            language.addEventListener("change", function () {
                this.setCustomValidity(this.validity.valueMissing ? "this field"+TextMsg : "");
            });
        }
    }

    window.addEventListener('load',headerOnLoadHandler)

</script>

 <script type="text/javascript">
    const Tools={
     header: {
      class: Header,
      inlineToolbar: ['link','bold']
     },
     list: {
      class: List,
      inlineToolbar: true
     },
     code: CodeTool,

     paragraph: {
      class: Paragraph,
      inlineToolbar: true,
     },
     Marker: {
      class: Marker,
      shortcut: 'CMD+SHIFT+M',
     },
     inlineCode: {
      class: InlineCode,
      shortcut: 'CMD+SHIFT+M',
     },
     underline: Underline,
     link: {
      class: LinkAutocomplete,
      config: {
       endpoint: 'http://localhost:3000/',
       queryParam: 'search'
      }
     },
     embed: {
      class: Embed,
      config: {
       services: {
        youtube: true,
        coub: true,
       }
      }
     },
     raw: RawTool,
    }
    const codeEditor = new EditorJS({
        holder: 'code',
        tools:Tools,
    });



    function submitNewTemplate(){
     let error=false;

     codeEditor.save().then((outputData) => {
          document.RegisterTemplate.code.value = JSON.stringify(outputData);
        }).catch((error) => {
            console.log('Saving failed: ', error)
            error = true
        });

        // save the data
        if(!error) document.RegisterTemplate.submit()
        else  alert("un errore è accaduto... per favore compilate tutti campi");
    }
</script>