<%@ page import="java.util.Locale" %>


<nav class="navbar navbar-dark bg-dark mt-3">
    <div class="container-fluid">
        <a class="navbar-brand"><%=templates.size()%> Template<%=templates.size()>1 ? 's' : ""%></a>
        <div class="d-flex">

            <form name="languageForm" action="Dispatcher" method="post" class="me-2">
                <select class="form-select" onchange="searchByLanguage()" id="language" name="language">
                    <option selected disabled>Search By languages</option>
                    <option value="all">all</option>
                    <%for(String language: Template.LANGUAGELIST){%>
                    <option value="<%=language%>"><%=language%></option>
                    <%}%>
                </select>
                <input  type="hidden" name="controllerAction" value="Templates.searchByLanguage"/>
            </form>

            <form  class="d-flex" name="searchForm" action="Dispatcher" method="post">
                <input class="form-control me-2" type="search" placeholder="Search.." name="search" aria-label="Search"/>
                <input type="hidden" name="controllerAction" value="Templates.searchByTitle"/>
                <button class="btn btn-outline-success" type="submit">Search</button>
            </form>
        </div>
    </div>
</nav>