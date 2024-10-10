<script>
    let  editor;
    function codeView() {
        let element_id = "code_<%=template.getTemplateID()%>";
        console.log(<%=template.getCode()%>);
        let code =<%=template.getCode()%>;

        editor = new EditorJS({
            holder: element_id,
            data: code,
            tools: {
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
            },
        });
        console.log(element_id)
        console.log(code)
    }

    function updateTemplate(e){
        let error=false;

        editor.save().then((outputData) => {
            document.editTemplate.code.value = JSON.stringify(outputData);
            console.log('Article data: ', outputData);

        }).catch((error) => {
            console.log('Saving failed: ', error);
            error =true
        });

        e.preventDefault()

        /*if(!error) document.editTemplate.submit()
        else  alert("un errore è accaduto... per favore compilate tutti campi");*/
    }

    codeView()

</script>