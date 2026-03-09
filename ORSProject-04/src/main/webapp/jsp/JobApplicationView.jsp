<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.JobApplicationCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<html>
<head>
<title>Add Job Application</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

</head>

<body>

	<form action="<%=ORSView.JOB_APPLICATION_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean"
			class="in.co.rays.proj4.bean.JobApplicationBean" scope="request"></jsp:useBean>

		<%
			HashMap<String, String> positionMap = (HashMap<String, String>) request.getAttribute("positionMap");
		%>

		<div align="center">

			<h1 style="color: navy">

				<%
					if (bean != null && bean.getId() > 0) {
				%>

				Update Job Application

				<%
					} else {
				%>

				Add Job Application

				<%
					}
				%>

			</h1>

			<h3>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</h3>

			<h3>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</h3>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">

			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">

			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">


			<table>

				<tr>
					<th align="left">Applicant Name</th>

					<td><input type="text" name="applicantName"
						placeholder="Enter Applicant Name"
						value="<%=DataUtility.getStringData(bean.getApplicantName())%>">
					</td>

					<td><font color="red"> <%=ServletUtility.getErrorMessage("applicantName", request)%>
					</font></td>
				</tr>


				<tr>
					<th align="left">Company Name</th>

					<td><input type="text" name="companyName"
						placeholder="Enter Company Name"
						value="<%=DataUtility.getStringData(bean.getCompanyName())%>">
					</td>

					<td><font color="red"> <%=ServletUtility.getErrorMessage("companyName", request)%>
					</font></td>
				</tr>


				<tr>
					<th align="left">Position</th>

					<td><%=HTMLUtility.getList("position", bean.getPosition(), positionMap)%></td>

					<td><font color="red"> <%=ServletUtility.getErrorMessage("position", request)%>
					</font></td>
				</tr>


				<tr>
					<th align="left">Application Date</th>

					<td><input type="text" name="applicationDate"
						placeholder="dd-MM-yyyy"
						value="<%=DataUtility.getDateString(bean.getApplicationDate())%>">
					</td>

					<td><font color="red"> <%=ServletUtility.getErrorMessage("applicationDate", request)%>
					</font></td>
				</tr>

				<tr>
					<th></th>

					<%
						if (bean != null && bean.getId() > 0) {
					%>

					<td><input type="submit" name="operation"
						value="<%=JobApplicationCtl.OP_UPDATE%>"> <input
						type="submit" name="operation"
						value="<%=JobApplicationCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td><input type="submit" name="operation"
						value="<%=JobApplicationCtl.OP_SAVE%>"> <input
						type="submit" name="operation"
						value="<%=JobApplicationCtl.OP_RESET%>"></td>

					<%
						}
					%>

				</tr>

			</table>

		</div>

	</form>

</body>
</html>