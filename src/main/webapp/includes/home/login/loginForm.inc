<div class="sub_form_container">
    <div class="form">
        <h1>Login</h1>
        <form id="login" action="Dispatcher" method="post">

            <div class="input-group mb-3 mt-3">
                <span class="input-group-text" id="basic-addon1">email</span>
                <input type="email" name="email" id="email" class="form-control" placeholder="john@doe.com" aria-label="email" aria-describedby="basic-addon1" required>
            </div>

            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon2">Password</span>
                <input type="password" name="password" id="password" required minlength="6" class="form-control" aria-label="password" aria-describedby="basic-addon2" >
            </div>

            <input type="hidden" name="controllerAction" value="Home.login">

            <button type="submit" class="btn btn-primary w-100">Login</button>
        </form>
        <div class="info">
            <p>Non sei ancora registrato ?</p>
            <a href="Dispatcher?controllerAction=Home.newUser">Registrati</a>
        </div>
    </div>
</div>