<%@page import="java.util.List"%>

<%@page import="in.co.rays.proj4.bean.FollowUpBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.FollowUpCtl"%>

<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<html>
<head>
<title>Follow Up</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

</head>

<body>

	<form action="<%=ORSView.FOLLOWUP_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.FollowUpBean"
			scope="request" />


		<center>

			<h1 style="color: navy">
				<%
					if (bean != null && bean.getId() > 0) {
				%>
				Update Follow Up
				<%
					} else {
				%>
				Add Follow Up
				<%
					}
				%>
			</h1>

			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>

			<input type="hidden" name="id" value="<%=bean.getId()%>">
			<input type="hidden" name="createdBy"
				value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>">
			<input type="hidden" name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table>

			
					<td>
						<font color="red"><%=ServletUtility.getErrorMessage("patientId", request)%></font>
					</td>
				</tr>

				<!-- Doctor -->
				<tr>
					<th align="left">Doctor <font color="red">*</font></th>
					
					<td>
						<font color="red"><%=ServletUtility.getErrorMessage("doctorId", request)%></font>
					</td>
				</tr>

				<!-- Visit Date -->
				<tr>
					<th align="left">Visit Date <font color="red">*</font></th>
					<td>
						<input type="text" name="visitDate" id="udate"
							placeholder="Select Visit Date"
							value="<%=DataUtility.getDateString(bean.getVisitDate())%>">
					</td>
					<td>
						<font color="red"><%=ServletUtility.getErrorMessage("visitDate", request)%></font>
					</td>
				</tr>

				<!-- Fees -->
				<tr>
					<th align="left">Fees <font color="red">*</font></th>
					<td>
						<input type="text" name="fees"
							placeholder="Enter Fees"
							value="<%=bean.getFees()%>">
					</td>
					<td>
						<font color="red"><%=ServletUtility.getErrorMessage("fees", request)%></font>
					</td>
				</tr>

				<!-- Buttons -->
				<tr>
					<th></th>
					<td colspan="2">
						<%
							if (bean != null && bean.getId() > 0) {
						%>
						<input type="submit" name="operation"
							value="<%=FollowUpCtl.OP_UPDATE%>">
						<input type="submit" name="operation"
							value="<%=FollowUpCtl.OP_CANCEL%>">
						<%
							} else {
						%>
						<input type="submit" name="operation"
							value="<%=FollowUpCtl.OP_SAVE%>">
						<input type="submit" name="operation"
							value="<%=FollowUpCtl.OP_RESET%>">
						<%
							}
						%>
					</td>
				</tr>

			</table>

		</center>

	</form>

</body>
</html>
