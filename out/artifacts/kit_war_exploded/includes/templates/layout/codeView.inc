<script>
    const Tools= {
            header: {
                class: Header,
                inlineToolbar: ['bold']
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
            raw: RawTool,
        }

    function EditorView() {
        let code_id = "codeView_<%=template.getTemplateID()%>";

        let code = <%=template.getCode()%>;
        new EditorJS({
            holder: code_id,
            data: code,
            tools: Tools,
            readOnly: true,
        });
    }
    EditorView()

    let commentEditor;
    function  comment_Editor(){
        let comment_id = "commentJs"

        commentEditor = new EditorJS({
            holder: comment_id,
            tools: Tools,
            placeholder: 'lascia una critica o un commento qua ',
        })
    }
    comment_Editor()

    function sendComment (){
        let error=false;

        commentEditor.save().then((outputData) => {
            document.PostComment.comment.value = JSON.stringify(outputData);
            console.log('Article data: ', outputData)
        }).catch((error) => {
            console.log('Saving failed: ', error)
            error =true
        });

        if(!error) document.PostComment.submit()
        else  alert("un errore è accaduto... per favore compilate tutti campi");
    }

    function commentView(){
        let comments = document.getElementById("comments").children
        let ids=[];
        let values=[];

        for (let i =0 ; i<comments.length;i++){
            let childs = comments[i].children
            for (let j = 0; j < childs.length;j++){
                ids.push(childs[j].children[0].getAttribute("id"))
                values.push({"json":JSON.parse(childs[j].children[1].getInnerHTML()),"id":childs[j].children[0].getAttribute("id")})
            }
        }

        for (let i = 0; i < ids.length; i++) {
            let comment_id = ids[i];
            let id  = parseInt(comment_id.split("_")[1])

            let temp ={};

            for (let i = 0; i < values.length; i++) {
                if(values[i].id === comment_id){
                    temp = values[i]
                }
            }

            new EditorJS({
                holder: comment_id,
                data: temp.json,
                tools: Tools,
                readOnly: true,
            });
        }
    }
    commentView()
</script>