<%@ page import="com.esame.kit.model.mo.User" %>

<div class="sub_form_container">
    <div class="form">
        <h1>Register</h1>
        <form id="Register" action="Dispatcher" method="post">

            <div class="input-group mb-3 mt-3">
                <span class="input-group-text" id="basic-addon1">First Name</span>
                <input type="text" name="firstName" id="firstName" class="form-control" placeholder="john" aria-label="First name" aria-describedby="basic-addon1" required>
            </div>

            <div class="input-group mb-3 mt-3">
                <span class="input-group-text" id="basic-addon2">second Name</span>
                <input type="text" name="secondName" id="secondName" class="form-control" placeholder="doe" aria-label="Second name" aria-describedby="basic-addon2" required>
            </div>

            <div class="input-group mb-3 mt-3">
                <span class="input-group-text" id="basic-addon3">email</span>
                <input type="email" name="email" id="email" class="form-control" placeholder="john@doe.com" aria-label="email" aria-describedby="basic-addon3" required>
            </div>

            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon4">Password</span>
                <input type="password" name="password" id="password" required minlength="6" class="form-control" aria-label="password" aria-describedby="basic-addon4" >
            </div>

            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon5">FrameWork Preferito</span>
                <input type="text" name="languageCodePreferences" id="languageCodePreferences" required maxlength="40" class="form-control" aria-label="languageCodePreferences" aria-describedby="basic-addon5" >
            </div>

            <div>
                <fieldset style="padding: 1rem">
                    <legend>Select a Status utente:</legend>
                    <select class="form-select" aria-label="Select a Status utente:" required name="role">
                        <%for (String role : User.ROLE){%>
                        <%if(!role.equals("admin")){%>
                        <option value="<%=role%>"><%=role%> di contenuto.</option>
                        <%}%>
                        <%}%>
                    </select>
                </fieldset>
                <div class="alert alert-warning d-flex align-items-center" role="alert">
                    <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Warning:"><use xlink:href="#exclamation-triangle-fill"/></svg>
                    <div>
                        una volta scelto lo stato, potra essere modificabile solo dall'amministratore
                    </div>
                </div>
            </div>

            <input type="hidden" name="controllerAction" value="Home.insertUser">

            <button type="submit" class="btn btn-primary w-100">Register</button>
        </form>
        <div class="info">
            <p>Ti sei ancora gia registrato ?</p>
            <a href="Dispatcher?controllerAction=Home.view">Connettiti</a>
        </div>
    </div>
</div>