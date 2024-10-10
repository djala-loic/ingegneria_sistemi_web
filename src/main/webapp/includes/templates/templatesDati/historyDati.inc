<div class="templates_dati">
    <div class="info">
        <div class="info_title">
            <h1>History Action</h1>
            <button  class="btn btn-primary"><a style="color: white" href="Dispatcher?controllerAction=Templates.newTemplate">Crea un nuovo Templates</a></button>
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
                In questa pagina, Lei potra <i><b><mark>Visualizzare</mark></b></i>, <i><b><mark>Editare</mark></b></i>, <i><b><mark>Restore</mark></b></i>,<i><b><mark>Delete forever</mark></b></i> ed i templates publicati e suppressi da
                lei sul sito.
            </div>
        </div>
    </div>

    <%if (loggedUser.isCreatore()){%>
    <% if (templates_offlines.size()>0) {%>
    <div class="dati">
        <div style="display: flex;justify-content: space-between;align-items: center;">
            <h1> <%= templates_offlines.size()%> Template<%= templates_offlines.size()>0 ? "s suppressi" : " suppresso"%> </h1>
            <button  class="btn btn-primary" ><a style="color: white" href="Dispatcher?controllerAction=History.delete">delete All</a></button>
        </div>
        <table class="table table-striped mt-2">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">title</th>
                <th scope="col">author</th>
                <th scope="col">description</th>
                <th scope="col">language</th>
                <th scope="col">created at</th>
                <th scope="col">options</th>
            </tr>
            </thead>
            <tbody>
            <% for (Template template_offLine: templates_offlines) {%>
            <tr>
                <th scope="row"><%=template_offLine.getTemplateID()%></th>
                <td><%=template_offLine.getTitle()%></td>
                <td><%=template_offLine.getUser().getFirstname()%></td>
                <td><%=template_offLine.getDescription()%></td>
                <td><%=template_offLine.getLanguage()%></td>
                <td><%=template_offLine.getCreatedAt()%></td>
                <td>
                    <% if(template_offLine.getUser().getUserId()==loggedUser.getUserId()){%>
                    <button onclick="editTemplate('<%=template_offLine.getTemplateID()%>')" style="border: none;cursor: pointer" class="btn btn-primary"><i class="fa-solid fa-marker"></i></button>
                    <button onclick="restoreTemplate('<%=template_offLine.getTemplateID()%>')" style="border: none;cursor: pointer" class="btn btn-success "><i class="fa-solid fa-trash-can-arrow-up"></i></button>
                    <button onclick="deleteTemplateForever('<%=template_offLine.getTemplateID()%>')" style="border: none;margin-left: 1rem;cursor: pointer" class="btn btn-danger"><i class="fa-solid fa-trash-can"></i></button>
                    <%}%>
                    <button onclick="viewTemplate('<%=template_offLine.getTemplateID()%>')" style="border-radius: 5px;cursor: pointer;" class="btn btn-light"> <i class="fa-solid fa-eye"></i> </button>
                </td>
            </tr>
            <%}%>
            </tbody>
        </table>
    </div>
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
        <h1>
            Nessun templates suppresso...
        </h1>
    </div>
    <%}%>
    <%}%>



    <%if(comments_offlines != null){%>
    <div class="dati">
        <div style="display: flex;justify-content: space-between;align-items: center;margin-top: 1rem">
            <h1> <%=comments_offlines.size()%> comment<%= comments_offlines.size()>0 ? "i suppressi" : "o suppresso"%> </h1>

           <%if(comments_offlines.size()>0){ %>
            <button class="btn btn-primary"><a style="color: white" href="Dispatcher?controllerAction=History.deleteComment">delete All</a></button>
            <%}%>
        </div>

        <table class="table table-striped mt-2">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Template Title</th>
                <th scope="col">Author comment</th>
                <th scope="col">Comment content</th>
                <th scope="col">commented at</th>
                <th scope="col">options</th>
            </tr>
            </thead>
            <tbody>
            <% for (Comment comment_offLine: comments_offlines) {%>
            <tr>
                <th scope="row"><%=comment_offLine.getCommentID()%></th>
                <td><%=comment_offLine.getTemplate().getTitle()%></td>
                <td><%=comment_offLine.getUser().getFirstname()%></td>
                <td><%=comment_offLine.getBody()%></td>
                <td><%=comment_offLine.getCreatedAt()%></td>
                <td>
                    <button onclick="restoreCommentTemplate('<%=comment_offLine.getCommentID()%>')" style="border: none;cursor: pointer" class="btn btn-success "><i class="fa-solid fa-trash-can-arrow-up"></i></button>
                    <button onclick="deleteCommentTemplate('<%=comment_offLine.getCommentID()%>')" style="border: none;margin-left: 1rem;cursor: pointer" class="btn btn-danger"><i class="fa-solid fa-trash-can"></i></button>
                    <button onclick="viewTemplate('<%=comment_offLine.getTemplate().getTemplateID()%>')" style="border-radius: 5px;cursor: pointer;" class="btn btn-light"> <i class="fa-solid fa-eye"></i> </button>
                </td>
            </tr>
            <%}%>
            </tbody>
        </table>

    </div>
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
        <h1>
            Nessun commento suppresso...
        </h1>
    </div>
    <%}%>
    <form name="viewTemplateForm" action="Dispatcher" method="post">
        <input type="hidden" name="templateID"/>
        <input type="hidden" name="controllerAction" value="Templates.showTemplate"/>
    </form>


    <form name="RestoreTemplateForm" action="Dispatcher" method="post">
        <input type="hidden" name="templateID"/>
        <input type="hidden" name="controllerAction" value="History.restore"/>
    </form>

    <form name="RestoreCommentTemplateForm" action="Dispatcher" method="post">
        <input type="hidden" name="commentID"/>
        <input type="hidden" name="controllerAction" value="History.restoreComment"/>
    </form>

    <form name="deleteTemplateFormForever" action="Dispatcher" method="post">
        <input type="hidden" name="templateID"/>
        <input type="hidden" name="controllerAction" value="History.delete"/>
    </form>

    <form name="deleteCommentTemplateForm" action="Dispatcher" method="post">
        <input type="hidden" name="templateID"/>
        <input type="hidden" name="commentID"/>
        <input type="hidden" name="controllerAction" value="History.deleteComment"/>
    </form>


    <form name="editTemplateForm" action="Dispatcher" method="post">
        <input type="hidden" name="templateID"/>
        <input type="hidden" name="controllerAction" value="Templates.edit"/>
    </form>

</div>