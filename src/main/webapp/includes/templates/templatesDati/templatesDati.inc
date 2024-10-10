<%@ page import="com.esame.kit.model.mo.Like" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="com.esame.kit.model.mo.Note" %>
<div class="templates_dati">
    <div class="info">
        <div class="info_title">
            <h1><%= search!=null && search.isEmpty() == false ? search.equals("all") ?  "All Templates" : search : "All Templates" %></h1>
            <% if(loggedUser.isCreatore()){%>
            <button class="btn btn-primary"><a href="Dispatcher?controllerAction=Templates.newTemplate" style="color: white">Crea un nuovo Templates</a></button>
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

        <%if (loggedUser.isCreatore()){ %>
        <div class="alert alert-primary d-flex align-items-center" role="alert">
            <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Info:"><use xlink:href="#info-fill"/></svg>
            <div>
                In questa pagina, Lei potra <i><b><mark>Visualizzare</mark></b></i>, <i><b><mark>Editare</mark></b></i>, <i><b><mark>Delete</mark></b></i> i templates publicati  da
                lei sul sito e potra invece  <i><b><mark>solo Visualizzare</mark></b></i> quelli degli altri utilisatori.
            </div>
        </div>
        <%}%>
    </div>

    <% if (templates.size()>0) {%>
    <div class="dati">
        <ol>
            <% for (Template template: templates) {%>
            <li style="min-height: 260px">
                <div class="item" style="position: relative;">
                    <h3 class="textCardTitle"><a style="cursor: pointer;" onclick="viewTemplate('<%=template.getTemplateID()%>')"><%=template.getTitle()%></a></h3>
                    <p class="textCard"><%=template.getDescription()%></p>
                    <%if (template.getLink()!=null  && template.getLink().length()>0){%>
                    <h3>Template <mark>con</mark> trailer esplicativo.</h3>
                    <%}else{%>
                    <h3>Template <mark>senza</mark> trailer esplicativo.</h3>
                    <%}%>

                    <%if(loggedUser.isLettore() && !Template.isPrenotedByUser(loggedUser,template)){%>
                    <button onclick="PrenotaRicevimentoDati(<%=template.getTemplateID()%>,<%=template.getUser().getUserId()%>)" style="font-size: 12px;font-weight: 500;width: 100%;text-align:right;cursor: pointer;border: none; background:#fff; text-decoration: underline;color: #383D3B;">
                        prenota un ricevimento
                    </button>
                    <%}%>
                    <%if(loggedUser.isLettore() && Template.isPrenotedByUser(loggedUser,template)){%>
                    <h4 style="text-align: right; width: 100%;color: #383D3B; font-size: 12px;">Gia Prenotato. <a style="font-weight: 500;text-decoration: underline;color: #97C6E3;font-size: 12px" href="Dispatcher?controllerAction=Prenotazioni.view">vedi le tue prenotazioni</a></h4>
                    <%}%>

                    <div style="display: flex;justify-content:space-between;width: 100%;flex-wrap: wrap; text-align: right; font-size: 12px;">
                        <div>
                            <span style="font-weight: bold;margin-right: 1rem;"><%=template.getComments().length %> Comment<%=template.getComments().length>0 ? 'i' : 'o'%></span>
                            <span style="font-weight: bold"><%=template.getLikes().length%> Like<%=template.getLikes().length>0 ? 's':""%></span>
                            <span style="font-weight: bold; margin-left: 0.3rem"> <u><%=template.getTotNote()%></u>/<%=Note.MAX%> per <%=template.getNotes().length%> personne in tutto</span>
                        </div>
                        <span>By <b><i><mark><%=template.getUser().getUsername()%></mark></i></b></span>
                    </div>

                    <div class="option" style="border-top: 1px solid #111111;padding:  0.5rem;">
                        <% if(template.getUser().getUserId() == loggedUser.getUserId() && loggedUser.isCreatore()){%>
                        <div class="isUserOption">
                            <button onclick="editTemplate('<%=template.getTemplateID()%>')" style="border: none;cursor: pointer;" class="blue raduis"><i class="fa-solid fa-marker"></i></button>
                            <button onclick="deleteTemplate('<%=template.getTemplateID()%>')" style="border: none;cursor: pointer;" class="red raduis"><i class="fa-solid fa-trash"></i></button>
                        </div>
                        <%}%>
                        <button class="raduis" onclick="LikeTemplate('Template','<%=template.getTemplateID()%>','<%=loggedUser.getUserId()%>',<%=Like.isLikeForUser(loggedUser.getUserId(),template.getTemplateID(),Arrays.asList(template.getLikes()),"Template")%>)" id="Likes_<%=template.getTemplateID()%>" style="cursor: pointer; border: none;<%=Like.isLikeForUser(loggedUser.getUserId(),template.getTemplateID(),Arrays.asList(template.getLikes()),"Template")? "color: red !important" : "" %>"><i class="fa-solid fa-heart"></i></button>
                        <botton class="view raduis" onclick="viewTemplate('<%=template.getTemplateID()%>')" style="cursor: pointer;"> <i class="fa-solid fa-eye"></i> </botton>
                    </div>

                    <span style="position: absolute; top: -1.6rem; right: 0;padding: 0.2rem;font-weight: bold;font-size: 14px;"><%=template.getLanguage()%></span>
                    <span style="position: absolute; top: -1.6rem; left: 1rem;padding: 0.2rem;font-weight: bold;font-size: 14px;"><%=template.getCreatedAt()%></span>
                </div>
            </li>
            <%}%>
        </ol>
    </div>

    <form style="display: none" name="viewTemplateForm" action="Dispatcher" method="post">
        <input type="hidden" name="templateID"/>
        <input type="hidden" name="controllerAction" value="Templates.showTemplate"/>
    </form>


    <form style="display: none" name="deleteTemplateForm" action="Dispatcher" method="post">
        <input type="hidden" name="templateID"/>
        <input type="hidden" name="controllerAction" value="Templates.delete"/>
    </form>


    <form style="display: none" name="editTemplateForm" action="Dispatcher" method="post">
        <input type="hidden" name="templateID"/>
        <input type="hidden" name="controllerAction" value="Templates.edit"/>
    </form>

    <form style="display: none" name="LikeTemplateForm" action="Dispatcher" method="post">
        <input type="hidden" name="valueID"/>
        <input type="hidden" name="userID"/>
        <input type="hidden" name="classType"/>
        <input type="hidden" name="controllerAction" value="Templates.ToggleLike"/>
    </form>

    <form style="display: none" name="prenotaRicevimento" action="Dispatcher" method="post">
        <input type="hidden" name="templateID"/>
        <input type="hidden" name="userID" value="<%=loggedUser.getUserId()%>">
        <input type="hidden" name="authorID">
        <input type="hidden" name="controllerAction" value="Templates.PrenotaRicevimento"/>
    </form>

    <%}else{%>
    <h1>Nessun elemento trovato... </h1>
    <h3><b><i>Inserine uno ;-)</i></b></h3>
    <%}%>
</div>