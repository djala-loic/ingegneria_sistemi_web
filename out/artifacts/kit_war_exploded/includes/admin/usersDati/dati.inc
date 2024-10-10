<%@ page import="java.util.Locale" %>
<div class="GestionDati">
    <div class="info_title">
        <h1>Gestore di Users</h1>
        <button class="btn btn-primary text-white"><a href="Dispatcher?controllerAction=Admin.newUser" style="color: white">Crea un nuovo Users</a></button>
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
            In questa pagina l'Admin potra <b><mark> agguingere</mark></b>, <b><mark> delete</mark></b>, <b><mark> delete definitivamente</mark></b> , <b><mark> restore</mark></b> un user
        </div>
    </div>

    <nav class="navbar navbar-dark bg-dark mt-3">
        <div class="container-fluid">
            <%if(search!=null){%>
            <h1 class="navbar-brand"> <%=users.size()%> Risultati for : <%=search%></h1>
            <%}else{%>
            <h1 class="navbar-brand"> <%=users.size()%> Users</h1>
            <%}%>

            <div class="d-flex">

                <form name="ruoloForm" action="Dispatcher" method="post" class="me-2">
                    <select class="form-select"  onchange="searchByRuolo()" id="role" name="role">
                        <option selected disabled>Search By Role</option>
                        <option value="all">all</option>
                        <%for(String role: User.ROLE){%>
                        <option value="<%=role%>"><%=role%></option>
                        <%}%>
                    </select>
                    <input  type="hidden" name="controllerAction" value="Admin.searchByRole"/>
                </form>

                <form  class="d-flex" name="searchForm" action="Dispatcher" method="post">
                    <input class="form-control me-2" type="search" placeholder="Search.." name="search" aria-label="Search"/>
                    <input type="hidden" name="controllerAction" value="Admin.searchByRole"/>
                    <button class="btn btn-outline-success" type="submit">Search</button>
                </form>
            </div>
        </div>
    </nav>

    <table class="table table-striped mt-3">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">First Name</th>
            <th scope="col">Second Name</th>
            <th scope="col">Username</th>
            <th scope="col">Email</th>
            <th scope="col">Language Preferito</th>
            <th scope="col">Role</th>
            <th scope="col">Option</th>
        </tr>
        </thead>
        <tbody>
        <%for (User user: users) {%>
            <%if (user.getUserId() != loggedUser.getUserId()){%>
            <tr>
                <th scope="row"><%=user.getUserId()%></th>
                <td><%=user.getFirstname()%></td>
                <td><%=user.getSecondName()%></td>
                <td><%=user.getUsername()%></td>
                <td><%=user.getEmail()%></td>
                <td><%=user.getLanguagesCodePreferences()%></td>
                <td><%=user.getRole()%></td>
                <td>
                    <div class="d-flex">
                        <% if (user.isDelete()){%>
                        <button class="btn btn-success me-1" onclick="operationToUser('<%=user.getUserId()%>','restore',null)">Restore</button>
                        <button class="btn btn-danger me-1" onclick="operationToUser('<%=user.getUserId()%>','deleteForever',null)" >Delete Forever</button>
                        <%}else{%>
                        <select class="form-select me-1"  style="color: #193354 !important;" onchange="operationToUser('<%=user.getUserId()%>','changeRole','<%=user.getRole()%>')" id="changeRole_<%=user.getUserId()%>" name="role">
                            <option id="optionDefault" selected disabled >change By Role</option>
                            <%for(int i=0; i<User.ROLE.size();i++){%>
                            <option  value="<%=User.ROLE.get(i)%>">
                                <%=User.ROLE.get(i)%>
                            </option>
                            <%}%>
                        </select>
                        <%}%>
                        <%if (!user.isDelete()){%>
                        <button class="btn btn-warning" onclick="operationToUser('<%=user.getUserId()%>','delete',null)">blocca</button>
                        <%}%>
                    </div>
                </td>
            </tr>
        <%}%>
        <%}%>
        </tbody>
    </table>
</div>


<form style="display: none" name="operationUser">
    <input type="hidden" name="controllerAction" value="Admin.operationUser"/>
    <input type="hidden" name="operation" value=""/>
    <input type="hidden" name="userID" value=""/>
    <input type="hidden" name="role" value=""/>
</form>
