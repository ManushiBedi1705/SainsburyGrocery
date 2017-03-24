<%@ page contentType="text/html; charset = UTF-8"%>
<html>
<head>
<title>Home</title>
</head>
<!-- Sample JSP to display the contents processed and obtained in JSON content includes 
results: title,unit price, size of HTML, description and total unit price of the products -->
<body>
	<h2>Welcome to Sainsbury</h2>
	<h3>Ripe Ready fruits JSON</h3>
	${jsonContent}
</body>
</html>