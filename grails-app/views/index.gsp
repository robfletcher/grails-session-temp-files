<%@ page contentType="text/html;charset=UTF-8" %>
<html>
	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
		<title>Session</title>
	</head>
	<body>
		<dl>
			<% session.maxInactiveInterval = 60 %>
			<dt>Session ID:</dt>
			<dd>${session.id}</dd>
			<dt>Temp Dir:</dt>
			<dd>${session.tempDir}</dd>
			<dt>Creation Time:</dt>
			<dd>${new Date(session.creationTime)}</dd>
			<dt>Max Inactive Interval:</dt>
			<dd>${session.maxInactiveInterval}</dd>
		</dl>
	</body>
</html>