<%@ page import="com.esame.kit.model.mo.Note" %>
<style>
    .codex-editor .codex-editor__redactor{
        padding: 0 !important;
    }
    .templateNoteForm{
        display: flex;
        justify-content: end;
        align-items: center;
        gap: 1rem;
        margin: 1rem 5rem;
    }

    .templateNoteForm button{
        padding: 2px 10px;
    }

</style>

<form style="display: none;" name="languageMapForm" action="Dispatcher" method="post">
    <input type="hidden" name="language" id="language">
    <input  type="hidden" name="controllerAction" value="Templates.searchByLanguage"/>
</form>

<div class="one_container">

    <%if(!template.getDeleteState()){%>
    <div style="width: 80%; margin:0 auto;display: flex;justify-content: space-between;align-items: center;">

        <nav aria-label="breadcrumb mb-3">
            <ol class="breadcrumb">
                <li class="breadcrumb-item">
                    <a href="Dispatcher?controllerAction=Templates.view">Templates</a>
                </li>
                <li class="breadcrumb-item">
                    <a style="cursor: pointer" onclick="getByLanguage('<%=template.getLanguage()%>')">
                        <%=template.getLanguage()%>
                    </a>
                </li>
                <li class="breadcrumb-item" aria-current="page"><%=template.getTitle()%></li>
            </ol>
        </nav>

        <%if((template.getUser().getUserId() == loggedUser.getUserId()) && loggedUser.isCreatore()){%>
        <div style="display: flex;">
            <button onclick="editTemplate('<%=template.getTemplateID()%>')"  class="btn btn-primary me-1"><i class="fa-solid fa-marker"></i></button>
            <button onclick="deleteTemplate('<%=template.getTemplateID()%>')" class="btn btn-danger"><i class="fa-solid fa-trash"></i></button>

            <!--<button id="Likes_<%=template.getTemplateID()%>"
                   onclick="LikeTemplate('Template','<%=template.getTemplateID()%>','<%=loggedUser.getUserId()%>',<%=Like.isLikeForUser(loggedUser.getUserId(),template.getTemplateID(),Arrays.asList(template.getLikes()),"Template")%>)"
                   style="padding: 0.2rem 1rem;border-radius: 5px;margin-left: 1rem;<%=Like.isLikeForUser(loggedUser.getUserId(),template.getTemplateID(),Arrays.asList(template.getLikes()),"Template")? "color: red !important" : "" %>"
           >
               Like <i class="fa-solid fa-heart"></i>
           </button>-->

        </div>
        <%}%>
    </div>
    <%}%>

    <div style="width: 80%;margin: 0 auto; display: flex; justify-content: space-between;align-items: center">

        <%if(!Note.isNotedForUser(loggedUser.getUserId(), template.getTemplateID(), Arrays.asList(template.getNotes()))){%>
        <h3 style="text-align: left; width: 100%; text-decoration: underline" class="mt-2">Valuta il  template.</h3>
        <form name="templateNoteForm" class="templateNoteForm" action="Dispatcher" method="post" >
            <div style="display: flex">
                <input type="range" class="form-range me-1" id="note" value="<%=Note.MAX/2%>" oninput="getValue(<%=Note.MAX%>)" name="note" min="<%=Note.MIN%>" step="<%=Note.STEP%>" max="<%=Note.MAX%>"/>
                <span id="note_val" class=""><%=Note.MAX/2%>/<%=Note.MAX%></span>
            </div>
            <input type="hidden" name="templateID" value="<%=template.getTemplateID()%>">
            <input type="hidden" name="userID" value="<%=loggedUser.getUserId()%>">
            <input type="hidden" name="controllerAction" value="Templates.noteTemplate"/>
            <button type="submit"  class="btn btn-primary"> valida</button>
        </form>
        <%}else{%>
        <h4 style="text-align: left; width: 100%; text-decoration: underline">Hai dato una nota di <%=template.getNotesByUser(loggedUser,template).getNoteValue()%>/<%=Note.MAX%> a questo template.</h4>
        <form name="templateNoteForm" class="templateNoteForm" action="Dispatcher" method="post" >
            <input type="hidden" name="templateID" value="<%=template.getTemplateID()%>">
            <input type="hidden" name="note" value="<%=template.getNotesByUser(loggedUser,template).getNoteID()%>">
            <input type="hidden" name="controllerAction" value="Templates.deleteNoteTemplate"/>
            <button type="submit" style="width: 200px"> cancella la nota</button>
        </form>
        <%}%>
    </div>

    <div class="title_info" style="width: 80%; margin: 0 auto" >
        <div style="display: flex; justify-content: space-between; align-items: center">
            <h2><%=template.getTitle()%></h2>
            <span style="font-weight: bold;font-size: 15px;"><%=template.getLanguage()%></span>
        </div>
        <p style="margin-bottom: 1rem;"> <%=template.getDescription()%></p>
        <div style="width: 100%;display: flex; justify-content: space-between; align-items: center;flex-wrap: wrap">
            <p style=" padding: 0.5rem;text-align: left;font-weight: bold; font-size: 12px;"> <%=template.getLikes().length%> Likes </p>
            <%if(loggedUser.isLettore() && !Template.isPrenotedByUser(loggedUser,template)){%>
            <button onclick="PrenotaRicevimento()" style="cursor: pointer;border: none; background:#fff; text-decoration: underline;color: #383D3B;">
                prenota un ricevimento
            </button>
            <%}%>
            <%if(loggedUser.isLettore() && Template.isPrenotedByUser(loggedUser,template)){%>
                <h4 style="text-align: right;font-weight: 500; width: 100%;color: #383D3B; font-size: 12px; text-decoration: underline">Hai gia preso un appuntamento per questo template. <a style="text-decoration: none;color: #97C6E3" href="Dispatcher?controllerAction=Prenotazioni.view">vedi le tue prenotazioni</a></h4>
            <%}%>
        </div>
        <p style="width: 100%; padding: 0.5rem;text-align: left;font-weight: bold; font-size: 12px;"> <u><%=template.getTotNote()%>/<%=Note.MAX%></u> per <u><%=template.getNotes().length%> personne </u> che l'hanno valutato. </p>
    </div>

    <!--<div style="width: 80%; margin: 0 auto">
        <h4 style="text-align: left; width: 100%; text-decoration: underline">Code</h4>
        <div style="position: relative">
            <pre id="content_<template.getTemplateID()" style="position: relative">
                <code class="language"><%=template.getCode().trim()%></code>
            </pre>
            <i id="copy_template.getTemplateID()%>" onclick="copyContent('content_<%=template.getTemplateID()%>')" style="color:black !important;position: absolute; cursor: pointer;top: 2rem; right: 1rem; font-size: 30px" class="fa-solid fa-copy"></i>
        </div>
        <div  id ="codeView_template.getTemplateID()%>" style="position: relative;padding: 0!important; background: white;" class="input"></div>
    </div>-->

    <%if (template.getLink()!=null && template.getLink().length()>0){%>
    <div style="width: 80%; margin: 1rem auto">
        <h4 style="text-align: left; width: 100%; text-decoration: underline; margin: 1rem 0">Video esplicativo</h4>
        <iframe
                loading="lazy"
                src="<%=template.getLink()%>"
                style=" width: 100%;height: 500px;border: 0;font-size: 10px;border-radius: 4px;overflow: hidden;"
                allow="accelerometer; ambient-light-sensor; camera; encrypted-media; geolocation; gyroscope; hid; microphone; midi; payment; usb; vr; xr-spatial-tracking"
                sandbox="allow-forms allow-modals allow-popups allow-presentation allow-same-origin allow-scripts"
        ></iframe>
    </div>
    <%}%>

    <div  style="width: 80%; margin: 2rem auto" class="fotos">
        <h4 style="text-align: left; width: 100%; text-decoration: underline;margin-bottom: 1rem"><%=template.getComments().length %> Comment<%=template.getComments().length>0 ? 'i' : 'o'%></h4>
        <div class="templates_dati " style="padding: 1rem;display: flex;justify-content: center;align-items: center;flex-direction: column;">
            <%if(!template.getDeleteState()){%>
            <form method="post" action="Dispatcher" name="PostComment" style="width: 100%; margin-bottom: 1rem">
                <!--<textarea style="margin-bottom: 1rem;" name="comment" required placeholder="Lascia un commento"></textarea>-->
                <div class="input-group mb-3 mt-3">
                    <span class="input-group-text">Comment</span>
                    <textarea class="form-control" aria-label="Description"   name="comment"  id="comment"  required></textarea>
                </div>
                <!--<div id="commentJs" style="padding: 1rem; padding-bottom:5rem; margin: 1rem; margin-bottom:2rem;background: white; border: 1px solid #193354;border-radius: 5px;"></div>-->

                <input name="templateID" value="<%=template.getTemplateID()%>" type="hidden"/>
                <input name="userID" value="<%=loggedUser.getUserId()%>" type="hidden"/>
                <input name="controllerAction" value="Templates.createComment" type="hidden">
                <button class="btn btn-primary" onclick="sendComment()"> manda il tuo commento</button>
            </form>
            <%}%>

            <hr/>
            <div class="dati" style="padding: 0.5rem !important; width: 100%;">
            <ol id="comments" style=" width: 100%; display: grid; grid-template-columns: 1fr !important; column-gap: 1rem!important;">
                <%for(Comment comment: template.getComments()) {%>
                <li style="border: none !important; margin-bottom: 1rem;">
                    <div  class="item" style="position: relative;">
                        <p class="textCard" style="margin-top:1rem;"><%=comment.getBody()%></p>
                        <!--<div id="comment_comment.getCommentID()%>" class="input" style="width: 100%"></div>-->
                       <div id="hidden_<%=comment.getCommentID()%>" style="display: none"><%=comment.getBody().toString()%></div>

                        <div class="option">
                            <div class="" style="width: 100%;display: flex; justify-content: space-between;align-items: center;">
                                <button class="raduis" onclick="LikeComment('Comment','<%=comment.getCommentID()%>','<%=loggedUser.getUserId()%>',<%=Like.isLikeForUser(loggedUser.getUserId(),comment.getCommentID(),Arrays.asList(comment.getLikes()),"Comment")%>)" id="Likes_<%=comment.getCommentID()%>" style="background:#ffffff;cursor: pointer; border: none;<%=Like.isLikeForUser(loggedUser.getUserId(),comment.getCommentID(),Arrays.asList(comment.getLikes()),"Comment")? "color: red !important" : "" %>"><%=comment.getLikes().length%> <i class="fa-solid fa-heart"></i></button>
                                <% if((comment.getUser().getUserId() == loggedUser.getUserId()) || (template.getUser().getUserId() == loggedUser.getUserId() && loggedUser.isCreatore())){%>
                                <button onclick="deleteCommentTemplate('<%=comment.getCommentID()%>','<%=comment.getTemplate().getTemplateID()%>')" style="border: none;cursor: pointer;" class="red raduis"><i class="fa-solid fa-trash"></i></button>
                                <%}%>
                            </div>
                        </div>

                        <span style="position: absolute; top: -1.6rem; right: 0;padding: 0.2rem;font-weight: bold;font-size: 13px!important;"><b><i><mark><%=comment.getUser().getFirstname()+"  "+comment.getUser().getSecondName()%></mark></i></b></span>
                        <span style="position: absolute; top: -1.6rem; left: 1rem;padding: 0.2rem;font-weight: bold;font-size: 13px!important;"><%=comment.getCreatedAt()%></span>
                    </div>
                </li>
                <hr/>
                <%}%>
            </ol>
            </div>
        </div>
    </div>
</div>


<form style="display: none" name="LikeCommentForm" action="Dispatcher" method="post">
    <input type="hidden" name="valueID"/>
    <input type="hidden" name="userID"/>
    <input type="hidden" name="templateID" value="<%=template.getTemplateID()%>"/>
    <input type="hidden" name="classType"/>
    <input type="hidden" name="controllerAction" value="Templates.commentToggleLike"/>
</form>

<form style="display: none" name="deleteTemplateForm" action="Dispatcher" method="post">
    <input type="hidden" name="templateID"/>
    <input type="hidden" name="controllerAction" value="Templates.delete"/>
</form>


<form style="display: none" name="editTemplateForm" action="Dispatcher" method="post">
    <input type="hidden" name="templateID"/>
    <input type="hidden" name="controllerAction" value="Templates.edit"/>
</form>


<form style="display: none" name="deleteCommentTemplateForm" action="Dispatcher" method="post">
    <input type="hidden" name="templateID"/>
    <input type="hidden" name="commentID">
    <input type="hidden" name="controllerAction" value="Templates.deleteComment"/>
</form>


<form style="display: none" name="LikeTemplateForm" action="Dispatcher" method="post">
    <input type="hidden" name="valueID" value=""/>
    <input type="hidden" name="classType" value=""/>
    <input type="hidden" name="userID" value="">
    <input type="hidden" name="controllerAction" value="Templates.ToggleLike"/>
</form>

<form style="display: none" name="likeCommentTemplateForm" action="Dispatcher" method="post">
    <input type="hidden" name="templateID" value="<%=template.getTemplateID()%>"/>
    <input type="hidden" name="userID" value="<%=loggedUser.getUserId()%>">
    <input type="hidden" name="controllerAction" value="Templates.toggleLike"/>
</form>


<form style="display: none" name="prenotaRicevimento" action="Dispatcher" method="post">
    <input type="hidden" name="templateID" value="<%=template.getTemplateID()%>"/>
    <input type="hidden" name="userID" value="<%=loggedUser.getUserId()%>">
    <input type="hidden" name="authorID" value="<%=template.getUser().getUserId()%>">
    <input type="hidden" name="controllerAction" value="Templates.PrenotaRicevimento"/>
</form>