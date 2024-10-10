<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@page session="false"%>
<%@ page import="com.esame.kit.model.mo.User" %>


<%  boolean loggedOn = (boolean) request.getAttribute("loggedOn");
    User loggedUser = (User)  request.getAttribute("loggedUser");
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = "Home";
%>

<!DOCTYPE html>
<html lang="it-IT">
<head>
    <%@include file="/includes/layout/headerHtml.inc"%>
    <%@include file="/includes/home/layoutHome/homeStyle.inc"%>
    <%@include file="/includes/layout/style.inc"%>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

</head>
<body>
        <%if (loggedOn){%>
        <%@include file="/includes/layout/navbar.inc"%>
            <div class="container">
                <%@include file="/includes/home/layoutHome/hero.inc"%>
                <%@include file="/includes/home/user/datiUser.inc"%>
            </div>
            <%@include file="/includes/layout/script.inc"%>
        <%}else{%>
            <%@include file="/includes/home/login/loginForm.inc"%>
            <%@include file="/includes/home/login/loginScriptJs.inc"%>
        <%}%>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>