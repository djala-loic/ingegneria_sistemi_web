<form name="logoutForm" action="Dispatcher" method="post">
    <input type="hidden" name="controllerAction" value="Home.logout"/>
</form>

<nav class="navbar navbar-dark bg-dark navbar-expand-lg py-0">
    <div class="container-fluid">
        <div class="collapse navbar-collapse" id="navbarText">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">

                <li class="nav-item">
                    <a  class="nav-link <%=menuActiveLink.equals("Home") ? "active": "" %>" href="Dispatcher?controllerAction=Home.view" >Home</a>
                </li>
                <li class="nav-item">
                    <a  class="nav-link <%=menuActiveLink.equals("Templates") ? "active": "" %>"  href="Dispatcher?controllerAction=Templates.view">Templates</a>
                </li>

                <% if (loggedUser!= null && loggedUser.isAdmin()){%>
                    <li class="nav-item">
                        <a  class="nav-link <%=menuActiveLink.equals("usersGestion") ? "active": "" %>"  href="Dispatcher?controllerAction=Admin.view">Gestore Users</a>
                    </li>
                    <li class="nav-item">
                        <a  class="nav-link <%=menuActiveLink.equals("templatesCommentsGestion") ? "active": "" %>"  href="Dispatcher?controllerAction=Admin.templatesCommentsView">Gestore Templates</a>
                    </li>
                <%}%>
            </ul>

            <div class="navbar-text">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">


                <%if(loggedOn && loggedUser.isCreatore()) {%>
                    <li class="nav-item" >
                        <a  class=" nav-link <%=menuActiveLink.equals("History") ? "active": "" %>" href="Dispatcher?controllerAction=History.view">My History</a>
                    </li>
                <%}%>
                <%if(loggedOn && !loggedUser.isAdmin()) {%>
                    <li class="nav-item"  >
                        <a  class="nav-link <%=menuActiveLink.equals("Prenotazioni") ? "active": "" %>" href="Dispatcher?controllerAction=Prenotazioni.view">Prenotazioni</a>
                    </li>
                <%}%>
                <li class="nav-item" >
                    <a class="nav-link " href="javascript:logoutForm.submit()">Logout</a>
                </li>
            </ul>
            </div>
        </div>
    </div>
</nav>
