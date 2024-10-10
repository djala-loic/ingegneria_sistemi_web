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

    .prenotationAccettaForm{
        display: flex;
        flex-direction: column;
        justify-content: end;
        align-items: flex-start;
        gap: 1rem;
        margin: 1rem;
        width: 100%;
    }

    .templateNoteForm button{
        padding: 2px 10px;
    }

</style>

<form style="display: none;" name="platformMapForm" action="Dispatcher" method="post">
    <input type="hidden" name="platform" id="platform" value="<%=prenotazione.getViaIncontro()%>">
    <input  type="hidden" name="controllerAction" value="Prenotazioni.search"/>
</form>

<div class="one_container">

    <%if(!prenotazione.isDeleteState()){%>
    <div style="width: 80%; margin:0 auto;display: flex;justify-content: space-between;align-items: center;">
        <nav aria-label="breadcrumb mb-3">
            <ol class="breadcrumb">
                <li class="breadcrumb-item">
                    <a href="Dispatcher?controllerAction=Prenotazioni.view">Prenotazioni</a>
                </li>
                <li class="breadcrumb-item">
                    <a style="cursor: pointer" onclick="getByPlatform()">
                        <%=prenotazione.getViaIncontro()%>
                    </a>
                </li>
                <li class="breadcrumb-item" aria-current="page"><%=prenotazione.getOggetto()%></li>
            </ol>
        </nav>

        <%if((prenotazione.getUser().getUserId() == loggedUser.getUserId() ||prenotazione.getDestinatario().getUserId() == loggedUser.getUserId() ) && !loggedUser.isAdmin()){%>
        <%if ( !prenotazione.isAccepted()){%>
            <div style="display: flex;justify-content: space-between;align-items: center; gap: 2rem">
                <%if(prenotazione.getUser().getUserId() == loggedUser.getUserId()){%>
                <button onclick="editPrenotazione('<%=prenotazione.getPrenotazioneID()%>')"  class="btn btn-primary">Edit <i class="fa-solid fa-marker"></i></button>
                <%}%>
                <button onclick="deletePrenotazione('<%=prenotazione.getPrenotazioneID()%>')"
                        class="btn btn-danger">Delete <i class="fa-solid fa-trash"></i></button>
            </div>
        <%}else{%>
        <div style="display: flex;justify-content: space-between;align-items: center; gap: 2rem">
            <button onclick="deletePrenotazione('<%=prenotazione.getPrenotazioneID()%>')" style="padding: 4px 10px;cursor: pointer;background:#fff; border: 1px solid #193354;color: red;"
                    class="raduis">Delete <i class="fa-solid fa-trash"></i></button>
        </div>
        <%}%>
        <%}%>
    </div>
    <%}%>

    <%if(prenotazione.getUser().getUserId() == loggedUser.getUserId()){%>
    <h4 style="text-align: center">STATO : <b><i><u><mark style="font-size: 15px !important;"><%=prenotazione.isAccepted() ? "la  prenotazione e stata accettata dal Autore." : "prenotazione non e ancora accettata dall Autore" %></mark></u></i></b></h4>
    <%if(prenotazione.isAccepted()){%>
    <div style="display: flex;flex-direction: column;align-items: flex-start;width: 80%;margin: 1rem auto;gap: 1rem;">
        <h1 style="text-decoration: underline"> Info Ricevimento: </h1>
        <h3>Link : <a href="<%=prenotazione.getLink()%>" target="_blank"><u><%=prenotazione.getLink()%></u></a></h3>
        <p> <b>Messaggio del Autore</b> :  <%=prenotazione.getResponse()%> </p>
    </div>
    <%}%>
    <%}%>
    <div class="title_info" style="width: 80%; margin: 0 auto" >
        <h4 style="text-decoration: underline;margin: 1rem 0">Oggetto</h4>
        <div style="display: flex; justify-content: space-between; align-items: center;margin: 1rem 0">
            <h1><%=prenotazione.getOggetto()%></h1>
            <span style="font-weight: bold;font-size: 15px;text-decoration: underline"><%=prenotazione.getViaIncontro()%></span>
        </div>
        <p style="margin-bottom: 1rem;">Fatto il: <b><i><mark><%=prenotazione.getCreatedAt()%></mark></i></b> </p>
        <div style="width: 100%;display: flex; justify-content: space-between; align-items: center;flex-wrap: wrap">
            <p style=" padding: 0.5rem;text-align: left; font-size: 12px;">Per il Template: <a style="cursor: pointer"  onclick="viewTemplate(<%=prenotazione.getTemplate().getTemplateID()%>)"><u><%=prenotazione.getTemplate().getTitle()%></u></a> </p>
        </div>
    </div>

    <% if (!prenotazione.getBody().isEmpty()){%>
    <div style="width: 80%; margin: 0 auto">
        <h4 style="text-align: left; width: 100%; text-decoration: underline">body</h4>
        <div  id ="prenotazioneView_<%=prenotazione.getPrenotazioneID()%>" style="position: relative;padding: 0!important; background: white;" class="input"></div>
    </div>
    <%}%>


    <%if((prenotazione.getDestinatario().getUserId() == loggedUser.getUserId()) && loggedUser.isCreatore()){%>
    <div style="width: 80%;margin: 1rem auto; display: flex;flex-direction: column;align-items: flex-start">
        <%if(!prenotazione.isAccepted()){%>
        <h4 style="text-align: left; width: 100%; text-decoration: underline">Accetta La prenotazione.</h4>
        <form name="accettaPrenotazioneForm" class="prenotationAccettaForm" action="Dispatcher" method="post" >
            <div class="input-group mb-3 mt-3">
                <span class="input-group-text" id="basic-link">Link <%=prenotazione.getViaIncontro()%></span>
                <input class="form-control"  type="url" name="link" id="link" placeholder="https://www.youtube.com/embed/aSB77Wwfo7k" required>
            </div>

            <div class="input-group mb-3">
                <span class="input-group-text">message</span>
                <textarea class="form-control" aria-label="response"  name="response" id="response" maxlength="500" required></textarea>
            </div>

            <input type="hidden" name="prenotazioneID" value="<%=prenotazione.getPrenotazioneID()%>">
            <input type="hidden" name="accept" value="yes">
            <input type="hidden" name="controllerAction" value="Prenotazioni.acceptPrenotazione"/>
            <button  type="submit" class="btn btn-primary"> Accetta la prenotazione</button>
        </form>
        <%}else{%>
        <div style="display: flex;justify-content: space-between;align-items: center;">
            <h4 style="text-align: left; width: 100%; text-decoration: underline">Hai accettato la prenotazione di <%=prenotazione.getUser().getFirstname()+"  "+prenotazione.getUser().getSecondName()%> rispetto al template <a onclick="viewTemplate('<%=prenotazione.getTemplate().getTemplateID()%>')"><%=prenotazione.getTemplate().getTitle()%> </a>.</h4>
            <form name="templateNoteForm" class="templateNoteForm" action="Dispatcher" method="post" >
                <input type="hidden" name="prenotazioneID" value="<%=prenotazione.getPrenotazioneID()%>">
                <input type="hidden" name="controllerAction" value="Prenotazioni.acceptPrenotazione"/>
                <button type="submit" class="btn btn-danger"> Annulla l'incontro</button>
            </form>
        </div>
        <%}%>
    </div>
    <%}%>



</div>

<form style="display: none" name="deletePrenotazioneForm" action="Dispatcher" method="post">
    <input type="hidden" name="prenotazioneID"/>
    <input type="hidden" name="controllerAction" value="Prenotazioni.delete"/>
</form>



<form style="display: none" name="editPrenotazioneForm" action="Dispatcher" method="post">
    <input type="hidden" name="prenotazioneID"/>
    <input type="hidden" name="controllerAction" value="Prenotazioni.edit"/>
</form>


<form style="display: none" name="viewTemplateForm" action="Dispatcher" method="post">
    <input type="hidden" name="templateID"/>
    <input type="hidden" name="controllerAction" value="Templates.showTemplate"/>
</form>

<form style="display: none" name="prenotaRicevimento" action="Dispatcher" method="post">
    <input type="hidden" name="templateID" value="<%=prenotazione.getTemplate().getTemplateID()%>"/>
    <input type="hidden" name="userID" value="<%=prenotazione.getUser().getUserId()%>">
    <input type="hidden" name="authorID" value="<%=prenotazione.getDestinatario().getUserId()%>">
    <input type="hidden" name="controllerAction" value="Templates.PrenotaRicevimento"/>
</form>