<%@ page import="com.esame.kit.model.mo.Prenotazione" %>

<div class="mt-3 mb-3">
    <div class="form" style="padding: 1rem">
        <h1 style="font-size: 22px !important;">Prenota ricevimento per il Template. Author: <u><%=prenotazione.getDestinatario().getFirstname()%> <%=prenotazione.getDestinatario().getSecondName()%></u></h1>
        <form id="Register" name="editPrenotazione" action="Dispatcher" method="post">

            <div class="input-group mb-3 mt-3">
                <span class="input-group-text" id="basic-addon1">DI</span>
                <input disabled type="text" value="<%=prenotazione.getUser().getSecondName()+"  "+prenotazione.getUser().getFirstname()%>" name="mittente"  id="mittente" class="form-control" aria-describedby="basic-addon1">
            </div>

            <div class="input-group mb-3 mt-3">
                <span class="input-group-text" id="basic-addon2">title</span>
                <input disabled type="text" value="<%=prenotazione.getTemplate().getTitle()%>" name="title" maxlength="50" minlength="3"  id="title" class="form-control"  aria-label="title" aria-describedby="basic-addon2">
            </div>

            <div class="input-group mb-3 mt-3">
                <span class="input-group-text">Description</span>
                <textarea disabled class="form-control" aria-label="Description" minlength="50" name="description"  id="description" maxlength="500">
                   <%=prenotazione.getTemplate().getDescription()%>
                </textarea>
            </div>

            <div class="input-group mb-3 mt-3">
                <span class="input-group-text" id="basic-addon3">Oggetto</span>
                <input type="text" name="oggetto" maxlength="50" minlength="3" value="<%=prenotazione.getOggetto()%>" id="oggetto" required class="form-control"  aria-label="oggetto" aria-describedby="basic-addon3">
            </div>

            <div class="input-group mb-3 mt-3">
                <span class="input-group-text" id="basic-addon4">Data e ora</span>
                <input type="datetime-local" name="dataOra" value="<%=prenotazione.getDataOra().replace(" ","T")%>" id="dataOra" required class="form-control"  aria-label="dataOra" aria-describedby="basic-addon4">
            </div>

            <div  class="mb-3 mt-3">
                <fieldset style="padding: 1rem">
                    <legend>PLatform</legend>
                    <select class="form-select" id="viaIncontro" name="viaIncontro" required>
                        <%for(String platform: Prenotazione.Platform){%>
                        <option selected="<%=platform.equals(prenotazione.getViaIncontro()) %>" value="<%=platform%>"><%=platform%></option>
                        <%}%>
                    </select>
                </fieldset>
            </div>

            <div class="mb-3 mt-3">
                <label for="body">message</label>
                <!--  <textarea name="code"  id="code" required></textarea>
                <span>required</span>-->

                <div id ="body" style="background: white; border: 1px solid #193354;border-radius: 5px;" class="input-group"></div>
            </div>


            <input type="hidden" name="body">

            <input type="hidden" name="prenotazioneID" value="<%=prenotazione.getPrenotazioneID()%>"/>

            <input type="hidden" name="controllerAction" value="Prenotazioni.update">

            <button onclick="submitEditPrenotazione()" class="btn btn-primary w-100">Edit Prenotazione</button>
        </form>

        <div class="info" style="margin: 2rem 0">
            <p>clicca qui per annullare le modifiche e tornare  al template</p>
            <a onclick="viewPrenotazione('<%=prenotazione.getPrenotazioneID()%>')" class="btn btn-info">Annulla</a>
        </div>


        <form name="viewPrenotazioneForm" action="Dispatcher" method="post">
            <input type="hidden" name="prenotazioneID"/>
            <input type="hidden" name="controllerAction" value="Prenotazioni.oneView"/>
        </form>
    </div>
</div>