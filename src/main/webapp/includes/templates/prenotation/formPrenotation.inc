<%@ page import="com.esame.kit.model.mo.Prenotazione" %>
<div class="mt-3 mb-3">
    <div class="form" style="padding: 1rem">
        <h3 style="font-size: 22px !important;">Prenota ricevimento per il Template. Author: <u><%=destinatario.getFirstname()%> <%=destinatario.getSecondName()%></u></h3>
        <form id="Register" name="RegisterPrenotazione" action="Dispatcher" method="post">

            <div class="input-group mb-3 mt-3">
                <span class="input-group-text" id="basic-addon1">DI</span>
                <input disabled type="text" value="<%=mittente.getSecondName()+"  "+mittente.getFirstname()%>" name="mittente"  id="mittente" class="form-control" aria-describedby="basic-addon1">
            </div>

            <div class="input-group mb-3 mt-3">
                <span class="input-group-text" id="basic-addon2">title</span>
                <input disabled type="text" value="<%=template.getTitle()%>" name="title" maxlength="50" minlength="3"  id="title" class="form-control"  aria-label="title" aria-describedby="basic-addon2">
            </div>

            <div class="input-group mb-3 mt-3">
                <span class="input-group-text">Description</span>
                <textarea disabled class="form-control" aria-label="Description" minlength="50" name="description"  id="description" maxlength="500">
                    <%=template.getDescription()%>
                </textarea>
            </div>

            <div class="input-group mb-3 mt-3">
                <span class="input-group-text" id="basic-addon3">Oggetto</span>
                <input type="text" name="oggetto" maxlength="50" minlength="3"  id="oggetto" required class="form-control"  aria-label="oggetto" aria-describedby="basic-addon3">
            </div>

            <div class="input-group mb-3 mt-3">
                <span class="input-group-text" id="basic-addon4">Data e ora</span>
                <input type="datetime-local" name="dataOra"  id="dataOra" required class="form-control"  aria-label="dataOra" aria-describedby="basic-addon4">
            </div>

            <div  class="mb-3 mt-3">
                <fieldset style="padding: 1rem">
                    <legend>PLatform</legend>
                    <select class="form-select" id="viaIncontro" name="viaIncontro" required>
                        <%for(String platform: Prenotazione.Platform){%>
                        <option value="<%=platform%>"><%=platform%></option>
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

            <input type="hidden" name="mittenteID" value="<%=mittente.getUserId()%>">

            <input type="hidden" name="destinatarioID" value="<%=destinatario.getUserId()%>">

            <input type="hidden" name="templateID" value="<%=template.getTemplateID()%>">

            <input type="hidden" name="controllerAction" value="Prenotazioni.create">

            <button onclick="submitNewPrenotazione()" class="btn btn-primary w-100">create Prenotazione</button>
        </form>
    </div>
</div>
