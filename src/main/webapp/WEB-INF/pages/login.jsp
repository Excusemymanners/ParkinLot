<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Login">
    <c:if test="${message != null}">
        <div class="alert alert-warning" role="alert">
                ${message}
        </div>
    </c:if>

    <%-- Schimbă action să bată în Servletul tău --%>
    <form class="form-signin" method="POST" action="${pageContext.request.contextPath}/Login">
        <h1 class="h3 mb-3 font-weight-normal">Sign in</h1>

            <%-- Schimbă name în ceva simplu (nu j_username) pentru a evita conflicte --%>
        <label for="username" class="sr-only">Username</label>
        <input type="text" id="username" name="username" class="form-control" placeholder="Username" required autofocus />

        <label for="password" class="sr-only">Password</label>
        <input type="password" id="password" name="password" class="form-control" placeholder="Password" required />

        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    </form>
</t:pageTemplate>