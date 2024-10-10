<%@ page import="java.util.Locale" %>
<nav class="navbar navbar-dark bg-dark mt-3">
    <div class="container-fluid">
        <a class="navbar-brand" href="Dispatcher?controllerAction=Prenotazioni.view"><span><%=prenotazioni.size()%></span> Prenotazion<%=prenotazioni.size()>1 ? 'i' : "e"%></a>
        <div class="d-flex">
            <form name="platformForm" action="Dispatcher" method="post" class="me-1">
                <select class="form-select" onchange="searchByPlatform()" id="platform" name="platform">
                    <option selected disabled value="all">Search By Platform</option>
                    <option value="all">all</option>
                    <%for(String platform: Prenotazione.Platform){%>
                    <option value="<%=platform%>"><%=platform%></option>
                    <%}%>
                </select>
                <input  type="hidden" name="controllerAction" value="Prenotazioni.search"/>
            </form>

            <form name="acceptForm" action="Dispatcher" method="post" class="me-1">
                <select class="form-select" onchange="searchByAccept()" id="accept" name="accept">
                    <option selected disabled value="all">Search By State</option>
                    <option value="all">all</option>
                    <option value="accettati">Accettati</option>
                    <option value="in Attesa">In Attesa</option>
                </select>
                <input  type="hidden" name="controllerAction" value="Prenotazioni.search"/>
            </form>

            <form  class="d-flex" name="searchForm" action="Dispatcher" method="post">
                <input class="form-control me-2" type="search" placeholder="Search.." name="search" aria-label="Search"/>
                <input type="hidden" name="controllerAction" value="Prenotazioni.search"/>
                <button class="btn btn-outline-success" type="submit">Search</button>
            </form>
        </div>
    </div>
</nav>