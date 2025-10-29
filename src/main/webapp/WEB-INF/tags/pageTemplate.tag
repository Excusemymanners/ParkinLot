<%@tag description="base page template" pageEncoding="utf-8"%>
<%@attribute name="pageTitle" %>

<!DOCTYPE html>
<html>
<head>
    <title>${pageTitle}</title>

</head>

<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<jsp:doBody/>
</body>
</html>