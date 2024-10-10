<script>
    const Tools= {
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
    let bodyEditor ;
    function EditorView() {
        let code_id = "body";
        bodyEditor = new EditorJS({
            holder: code_id,
            tools: Tools,
            placeholder:"Si spiega qua il motivo del ricevimento "
        });
    }
    EditorView()

    function  submitNewPrenotazione(){
        let error=false;

        bodyEditor.save().then((outputData) => {
            document.RegisterPrenotazione.body.value = JSON.stringify(outputData);
            console.log('Article data: ', outputData)
        }).catch((error) => {
            console.log('Saving failed: ', error)
            error =true
        });

        if(!error) document.RegisterPrenotazione.submit()
        else  alert("un errore è accaduto... per favore compilate tutti campi");
    }

</script>