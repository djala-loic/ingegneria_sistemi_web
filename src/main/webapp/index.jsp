<%@ page import="java.util.Calendar" %>
<%@ page import="java.sql.*" %>
<%@page session="false"%>
<% String contextPath=request.getContextPath();%>
<!DOCTYPE HTML>
<html lang="it-IT">
<head>
    <meta charset="UTF-8">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter+Tight:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
    <script type="text/javascript">
        function onLoadHandler() {
            window.location.href = "<%=contextPath%>/Dispatcher";
        }
        window.addEventListener("load", onLoadHandler);
    </script>
    <title>Page Redirection</title>
</head>
<body>
If you are not redirected automatically, follow the <a href='<%=contextPath%>/Dispatcher'>Here</a>
</body>
</html>
