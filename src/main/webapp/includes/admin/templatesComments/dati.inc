<div class="GestionDati">
    <div class="info_title">
        <h1>Gestore di Templates</h1>
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
            In questa pagina l'Admin potra <b><mark>delete</mark></b>, <b><mark>delete definitivamente</mark></b> , <b><mark>restore</mark></b> un template
        </div>
    </div>

    <nav class="navbar navbar-dark bg-dark mt-3">
        <div class="container-fluid">
            <div></div>
            <div class="d-flex">
                <form  name="languageForm" action="Dispatcher" method="post" class="me-2">
                    <select class="form-select" onchange="searchByLanguage()" id="language" name="language">
                        <option selected disabled>Search By Language</option>
                        <option value="all">all</option>
                        <%for(String language: Template.LANGUAGELIST){%>
                        <option value="<%=language%>"><%=language%></option>
                        <%}%>
                    </select>
                    <input  type="hidden" name="controllerAction" value="Admin.searchTemplate"/>
                </form>

                <form  class="d-flex"  name="searchNameForm" action="Dispatcher" method="post">
                    <input class="form-control me-2" type="search" placeholder="Search.." name="search" aria-label="Search"/>
                    <input type="hidden" name="controllerAction" value="Admin.searchTemplate"/>
                    <button class="btn btn-outline-success" type="submit">Search</button>
                </form>
            </div>
        </div>
    </nav>

    <table class="table table-striped mt-3">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Title</th>
            <th scope="col">Language</th>
            <th scope="col">Author</th>
            <th scope="col">Likes</th>
            <th scope="col">Comments</th>
            <th scope="col">Prenotazioni</th>
            <th scope="col">at</th>
            <th scope="col">Options</th>
        </tr>
        </thead>
        <tbody>
        <%for (Template template: templates) {%>
        <tr>
            <th scope="row"><%=template.getTemplateID()%></th>
            <td><%=template.getTitle()%></td>
            <td><%=template.getLanguage()%></td>
            <td><%=template.getUser().getUsername()%></td>
            <td><%=template.getLikes().length%></td>
            <td><%=template.getComments().length%></td>
            <td><%=template.getPrenotazioni().length%></td>
            <td><%=template.getCreatedAt()%></td>
            <td>
                <div class="d-flex">
                    <% if (template.getDeleteState()){%>
                    <button class="btn btn-success" onclick="operationToTemplate('<%=template.getTemplateID()%>','restore')" style="border: none;cursor: pointer"><i style="margin-right: 5px" class="fa-solid fa-trash-can-arrow-up"></i></button>
                    <%}%>
                    <!--  <span onclick="editTemplate('<%=template.getTemplateID()%>')" style="color: #193354;cursor: pointer;"><i style="margin-right: 5px" class="fa-solid fa-marker"></i>edit</span>-->
                    <button class="btn btn-light fa-solid fa-eye" style="color: #193354;cursor: pointer;" onclick="viewTemplate('<%=template.getTemplateID()%>')"> <i style="margin-right: 5px"></i></button>

                    <%if (!template.getDeleteState()){%>
                    <button class=" btn btn-danger fa-solid fa-trash" onclick="operationToTemplate('<%=template.getTemplateID()%>','delete')" style="border: none;cursor: pointer;" ><i style="margin-right: 5px"></i></button>
                    <%}else{%>
                    <button class="btn btn-danger fa-solid fa-trash-can " onclick="operationToTemplate('<%=template.getTemplateID()%>','deleteForever')" style="border: none;margin-left: 1rem;cursor: pointer"><i style="margin-right: 5px"></i> Delete Forever</button>
                    <%}%>
                </div>
            </td>
        </tr>
        <%}%>
        </tbody>
    </table>
</div>


<form style="display: none" name="operationUser">
    <input type="hidden" name="controllerAction" value="Admin.operationUser"/>
    <input type="hidden" name="operation" value=""/>
    <input type="hidden" name="userID" value=""/>
</form>


<form style="display: none" name="operationTemplate">
    <input type="hidden" name="controllerAction" value="Admin.operationTemplate"/>
    <input type="hidden" name="operation" value=""/>
    <input type="hidden" name="templateID" value=""/>
</form>


<form style="display: none" name="viewTemplateForm" action="Dispatcher" method="post">
    <input type="hidden" name="templateID"/>
    <input type="hidden" name="controllerAction" value="Templates.showTemplate"/>
</form>
