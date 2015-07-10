<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome to IBP</title>
</head>
<body>
	<b>IBP Administration console</b>
	<hr>
	<br /> The no fo processors available:
	<s:property value="noOfProcessors" />
	<br /> Maximum amount of memory that the Java virtual machine will
	attempt to use:
	<s:property value="maxMemory" />
	<br /> Amount of free memory in the Java Virtual Machine:
	<s:property value="freeMemory" />
	<br /> Total memory available to the JVM:
	<s:property value="totalMemory" />
	<hr>
	<br /> No of tasks pending in the processing queue is
	<s:property value="pendingTasks" />
	<br />
	<hr>
	<!-- Change status of polling therad -->
	<s:form action="/pages/StopPolling" method="post">
		Polling thread status is <s:property value="pollingStatus" />
		<s:submit value="Toggle polling status" />
	</s:form>
	<hr>
	<!-- Change logging level -->
	<s:form action="/pages/ChangeLogging" method="post">
		Current logging level is
		 <s:property value="loggingLevel" />
		<s:select label="Choose your logging level" headerKey="-1"
			headerValue="Select logging level" list="loggingLevels"
			name="chosenLoggingLevel" />
		<s:submit value="Change Logging level" />
	</s:form>
	<hr>
	You can gracefully shutdown IBP, it will process all pending requests
	in queue and then shutdown
	<s:form action="/pages/StopIBP" method="post">
		<s:submit value="Stop IBP" title="Stop IBP" />
	</s:form>
	
	<hr>
</body>
</html>