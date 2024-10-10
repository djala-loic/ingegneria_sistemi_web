
<script type="text/javascript">
    // template gestion
    function editTemplate(templateId) {
        document.editTemplateForm.templateID.value = templateId;
        document.editTemplateForm.submit()


    }

    function viewTemplate(templateID) {
        document.viewTemplateForm.templateID.value = templateID
        document.viewTemplateForm.submit()
    }

    function deleteTemplate(templateID) {
        document.deleteTemplateForm.templateID.value = templateID;
        document.deleteTemplateForm.submit();
    }

    function  restoreTemplate(templateID){
        document.RestoreTemplateForm.templateID.value = templateID;
        document.RestoreTemplateForm.submit();

    }

    function deleteTemplateForever(templateID){
        document.deleteTemplateFormForever.templateID.value = templateID;
        document.deleteTemplateFormForever.submit();
    }

    function LikeTemplate(classType,templateID,userID,isLike){
        var botton = document.getElementById("Likes_"+templateID);
        console.log(botton)
        if(isLike){
            botton.style.color = "red !important";
        }else{
            botton.style.color = "black !important";
        }
        document.LikeTemplateForm.userID.value = userID
        document.LikeTemplateForm.valueID.value = templateID
        document.LikeTemplateForm.classType.value = classType
        document.LikeTemplateForm.submit();
    }


    function LikeComment(classType,commentID,userID,isLike){
        var botton = document.getElementById("Likes_"+commentID);
        console.log(botton)
        if(isLike){
            botton.style.color = "red !important";
        }else{
            botton.style.color = "black !important";
        }
        document.LikeCommentForm.userID.value = userID
        document.LikeCommentForm.valueID.value = commentID
        document.LikeCommentForm.classType.value = classType
        document.LikeCommentForm.submit();
    }



    // search gesttion

    function searchByLanguage() {
        document.languageForm.submit();
    }


    function getByLanguage(language) {
        document.languageMapForm.language.value = language
        document.languageMapForm.submit();
    }

    // comment gestion

    function deleteCommentTemplate(commentID,templateID){
        document.deleteCommentTemplateForm.commentID.value = commentID
        document.deleteCommentTemplateForm.templateID.value = templateID
        document.deleteCommentTemplateForm.submit();
    }

    function  restoreCommentTemplate(commentID){
        document.RestoreCommentTemplateForm.commentID.value = commentID;
        document.RestoreCommentTemplateForm.submit();
    }

    function  operationToTemplate(templateID,operation){
        document.operationTemplate.templateID.value= templateID;
        document.operationTemplate.operation.value= operation;
        document.operationTemplate.submit()
    }

    // gestion copy
    function  copyContent(content){
        var range = document.createRange();
        range.selectNode(document.getElementById(content));
        window.getSelection().removeAllRanges(); // clear current selection
        window.getSelection().addRange(range); // to select text
        document.execCommand("copy");
        window.getSelection().removeAllRanges();
        alert("copy successfully")
    }
     // gestion des prenotation
    function  PrenotaRicevimento(){
        document.prenotaRicevimento.submit();
    }

    function PrenotaRicevimentoDati(templateID,authorID){
        document.prenotaRicevimento.authorID.value = authorID
        document.prenotaRicevimento.templateID.value = templateID
        document.prenotaRicevimento.submit()
    }
</script>



<script src="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.11.0/highlight.min.js"></script>
<script>
    function highlightCode() {
        var pres = document.querySelectorAll("pre>code");
        console.log(pres)
        for (var i = 0; i < pres.length; i++) {
            hljs.highlightBlock(pres[i]);
        }
    }
    highlightCode();

    function getValue(max){
        document.getElementById("note_val").innerHTML= document.getElementById("note").value+"/"+max
    }

</script>





