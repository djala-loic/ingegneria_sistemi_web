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
    function EditorView() {
        let body_id = "prenotazioneView_<%=prenotazione.getPrenotazioneID()%>";
        console.log(<%=prenotazione.getBody()%>)
        let body =<%=prenotazione.getBody()%>;

        new EditorJS({
            holder: body_id,
            data: body,
            tools: Tools,
            readOnly: true,
        });
    }
    EditorView()
</script>