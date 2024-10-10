<%@ page import="com.esame.kit.model.mo.Like" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="com.esame.kit.model.mo.Note" %>
<div class="templates_dati">
    <div class="info">
        <div class="info_title">
            <h1><%= search!=null && search.isEmpty() == false ? search.equals("all") ?  "All Prenotazioni" : search : "All Prenotazioni" %></h1>
            <% if(loggedUser.isCreatore()){%>
            <button  class="btn btn-primary"><a style="color: white" href="Dispatcher?controllerAction=Templates.newTemplate">Crea un nuovo Templates</a></button>
            <%}%>
        </div>
        <svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
            <symbol id="check-circle-fill" fill="currentColor" viewBox="0 0 16 16">
                <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z"/>
            </symbol>
            <symbol id="info-fill" fill="currentColor" viewBox="0 0 16 16">
                <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zm.93-9.412-1 4.705c-.07.34.029.533.304.533.194 0 .487-.07.686-.246l-.088.416c-.287.346-.92.598-1.465.598-.703 0-1.002-.422-.808-1.319l.738-3.468c.064-.293.006-.399-.287-.47l-.451-.081.082-.381 2.29-.287zM8 5.5a1 1 0 1 1 0-2 1 1 0 0 1 0 2z"/>
            </symbol>
            <symbol id="exclamation-triangle-fill" fill="currentColor" viewBox="0 0 16 16">
                <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
            </symbol>
        </svg>

        <div class="alert alert-primary d-flex align-items-center" role="alert">
            <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Info:"><use xlink:href="#info-fill"/></svg>
            <div>
                In questa pagina, Lei potra <i><b><mark>Visualizzare</mark></b></i>, <i><b><mark>Editare</mark></b></i>, <i><b><mark>Delete</mark></b></i> le prenotazioni prese da
                lei sul sito.
            </div>
        </div>
    </div>

    <% if (prenotazioni.size()>0) {%>
    <div class="dati">
        <table class="table table-striped">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">DI</th>
                <th scope="col">A</th>
                <th scope="col">Per il Templates</th>
                <th scope="col">PlatForm</th>
                <th scope="col">Data e ora</th>
                <th scope="col">STATO</th>
                <th scope="col">OPZIONI</th>
            </tr>
            </thead>
            <tbody>
            <% for (Prenotazione prenotazione: prenotazioni) {%>
                <tr>
                    <th scope="row"><%=prenotazione.getPrenotazioneID()%></th>
                    <td><%=prenotazione.getUser().getFirstname()+" "+prenotazione.getUser().getSecondName()%></td>
                    <td><%=prenotazione.getDestinatario().getFirstname()+" "+prenotazione.getDestinatario().getSecondName()%></td>
                    <td><%=prenotazione.getTemplate().getTitle()+" ID: "+prenotazione.getTemplate().getTemplateID()%></td>
                    <td><%=prenotazione.getViaIncontro()%></td>
                    <td><%=prenotazione.getDataOra()%></td>
                    <td><%=prenotazione.isAccepted() ? "ACCETTATA" : "NON ACCETTATA" %></td>
                    <td>
                        <% if((prenotazione.getUser().getUserId() == loggedUser.getUserId() ||prenotazione.getDestinatario().getUserId() == loggedUser.getUserId() ) && !loggedUser.isAdmin() ){%>
                        <%if ( !prenotazione.isAccepted()){%>
                            <%if (prenotazione.getUser().getUserId() == loggedUser.getUserId()){%>
                            <button onclick="editPrenotazione('<%=prenotazione.getPrenotazioneID()%>')" style="border: none;cursor: pointer;" class="btn btn-primary"><i class="fa-solid fa-marker"></i></button>
                            <%}%>
                            <button onclick="deletePrenotazione('<%=prenotazione.getPrenotazioneID()%>')" style="border: none;cursor: pointer;"  class="btn btn-danger"><i class="fa-solid fa-trash"></i></button>

                            <botton class="btn btn-light" onclick="viewPrenotazione('<%=prenotazione.getPrenotazioneID()%>')" style="cursor: pointer;">
                                <i class="fa-solid fa-eye"></i>
                            </botton>
                        <%}else{%>
                            <button onclick="deletePrenotazione('<%=prenotazione.getPrenotazioneID()%>')" style="border: none;cursor: pointer;"  class="btn btn-danger"><i class="fa-solid fa-trash"></i></button>
                            <botton class="btn btn-light" onclick="viewPrenotazione('<%=prenotazione.getPrenotazioneID()%>')" style="cursor: pointer;">
                                <i class="fa-solid fa-eye"></i>
                            </botton>
                        <%}%>
                        <%}%>
                    </td>
                </tr>
            <%}%>
            </tbody>
        </table>
    </div>

    <form style="display: none" name="deletePrenotazioneForm" action="Dispatcher" method="post">
        <input type="hidden" name="prenotazioneID"/>
        <input type="hidden" name="controllerAction" value="Prenotazioni.delete"/>
    </form>


    <form style="display: none" name="editPrenotazioneForm" action="Dispatcher" method="post">
        <input type="hidden" name="prenotazioneID"/>
        <input type="hidden" name="controllerAction" value="Prenotazioni.edit"/>
    </form>


    <form style="display: none" name="viewPrenotazioneForm" action="Dispatcher" method="post">
        <input type="hidden" name="prenotazioneID"/>
        <input type="hidden" name="controllerAction" value="Prenotazioni.oneView"/>
    </form>


    <%}else{%>
    <svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
        <symbol id="check-circle-fill" fill="currentColor" viewBox="0 0 16 16">
            <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z"/>
        </symbol>
        <symbol id="info-fill" fill="currentColor" viewBox="0 0 16 16">
            <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zm.93-9.412-1 4.705c-.07.34.029.533.304.533.194 0 .487-.07.686-.246l-.088.416c-.287.346-.92.598-1.465.598-.703 0-1.002-.422-.808-1.319l.738-3.468c.064-.293.006-.399-.287-.47l-.451-.081.082-.381 2.29-.287zM8 5.5a1 1 0 1 1 0-2 1 1 0 0 1 0 2z"/>
        </symbol>
        <symbol id="exclamation-triangle-fill" fill="currentColor" viewBox="0 0 16 16">
            <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
        </symbol>
    </svg>

    <div class="alert alert-primary d-flex align-items-center" role="alert">
        <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Info:"><use xlink:href="#info-fill"/></svg>
        <h1>Nessun elemento trovato... </h1>
    </div>
    <%}%>
</div>