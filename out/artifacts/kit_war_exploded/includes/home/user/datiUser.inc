<form style="display: none" method="post" action="Dispatcher" name="cancellaAccount" id="cancellaAccount">
    <input type="hidden" name="controllerAction" value="Home.deleteAccount"/>
    <input type="hidden" name="UserID" value="<%=loggedUser.getUserId()%>"/>
</form>

<div class="row gutters-sm">
    <div class="col-md-4 mb-3">
        <div class="card">
            <div class="card-body">
                <div class="d-flex flex-column align-items-center text-center">
                    <img src="images/user.png" alt="Admin" class="rounded-circle" width="150">
                    <div class="mt-3">
                        <h4> <%=loggedUser.getFirstname()%> <%=loggedUser.getSecondName()%></h4>
                        <p class="text-muted font-size-sm">
                            <%=loggedUser.getEmail()%>
                        </p>
                        <p class="text-secondary mb-1">
                           alias: <%=loggedUser.getUsername()%>
                        </p>
                        <a class="btn btn-primary" href="Dispatcher?controllerAction=Home.edit">Edit</a>
                        <button class="btn btn-outline-danger" id="ButtonCancellaAccount">Cancella</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-8">
        <div class="card mb-3">
            <div class="card-body">
                <div class="row">
                    <div class="col-sm-3">
                        <h6 class="mb-0">Fist Name</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                        <%=loggedUser.getFirstname()%>
                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-sm-3">
                        <h6 class="mb-0">Second Name</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                        <%=loggedUser.getSecondName()%>
                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-sm-3">
                        <h6 class="mb-0">Email</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                        <%=loggedUser.getEmail()%>
                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-sm-3">
                        <h6 class="mb-0">Language</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                        <%=loggedUser.getLanguagesCodePreferences()%>
                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-sm-3">
                        <h6 class="mb-0">Password</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                        <%=loggedUser.getPassword()%>
                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-sm-3">
                        <h6 class="mb-0">Role
                        </h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                        <%=loggedUser.getRole()%>
                    </div>
                </div>
                <div class="row mt-2">
                    <div class="col-sm-12">
                        <a class="btn btn-info w-100" href="Dispatcher?controllerAction=Home.edit">Edit</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>