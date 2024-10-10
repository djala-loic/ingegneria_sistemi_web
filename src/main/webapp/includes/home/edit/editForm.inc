<div class="sub_form_container">
    <div class="form">
        <h1>Edit</h1>
        <form id="Edit" action="Dispatcher" method="post">

            <div class="input-group mb-3 mt-3">
                <span class="input-group-text" id="basic-addon1">First Name</span>
                <input type="text" name="firstName" id="firstName" class="form-control" value="<%=loggedUser.getFirstname()%>" required>
            </div>

            <div class="input-group mb-3 mt-3">
                <span class="input-group-text" id="basic-addon2">second Name</span>
                <input type="text" name="secondName" id="secondName" value="<%=loggedUser.getSecondName()%>"  class="form-control" required>
            </div>

            <div class="input-group mb-3 mt-3">
                <span class="input-group-text" id="basic-addon3">email</span>
                <input type="email" name="email" id="email" class="form-control"  value="<%=loggedUser.getEmail()%>"  required>
            </div>

            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon4">Password</span>
                <input type="password" name="password" id="password" value="<%=loggedUser.getPassword()%>" required minlength="6" class="form-control">
            </div>

            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon5">FrameWork Preferito</span>
                <input type="text" name="languageCodePreferences" id="languageCodePreferences" value="<%=loggedUser.getLanguagesCodePreferences()%>" required maxlength="40" class="form-control"  >
            </div>

            <input type="hidden" name="controllerAction" value="Home.editUser">
            <button type="submit" class="btn btn-primary w-100">Edit</button>
        </form>
        <div class="info">
            <p>Clicca qui per annullare le modifiche e tornare alla Home Page</p>
            <a href="Dispatcher?controllerAction=Home.view">Annulla</a>
        </div>
    </div>
</div>