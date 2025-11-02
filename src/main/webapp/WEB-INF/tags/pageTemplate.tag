<%@ tag description="base page template" pageEncoding="UTF-8" %>
<%@ attribute name="pageTitle" required="true" %>

<!DOCTYPE html>
<html lang="ro">
<head>
    <meta charset="UTF-8">
    <title>${pageTitle}</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<header>
    <h1>${pageTitle}</h1>
    <nav>
        <a href="${pageContext.request.contextPath}/parkingLot.jsp">Acasă</a> |
        <a href="${pageContext.request.contextPath}/about.jsp">Despre</a>
    </nav>
</header>

<main>
    <jsp:doBody/>
</main>

<footer>
    <p>&copy; 2025 - Parking System</p>
</footer>
</body>
</html>
