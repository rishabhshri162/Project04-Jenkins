<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.bean.JobApplicationBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.JobApplicationListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<html>
<head>
<title>Job Application List</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png">

</head>

<body>

	<form action="<%=ORSView.JOB_APPLICATION_LIST_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean"
			class="in.co.rays.proj4.bean.JobApplicationBean" scope="request"></jsp:useBean>

		<%
			List<JobApplicationBean> list = (List<JobApplicationBean>) request.getAttribute("list");

			HashMap<String, String> positionMap = (HashMap<String, String>) request.getAttribute("positionMap");

			int pageNo = ServletUtility.getPageNo(request);

			int pageSize = ServletUtility.getPageSize(request);

			int nextListSize = (request.getAttribute("nextListSize") != null)
					? (Integer) request.getAttribute("nextListSize")
					: 0;
		%>

		<div align="center">

			<h1 style="color: navy">Job Application List</h1>

			<h3>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</h3>

			<h3>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</h3>


			<!-- ================= SEARCH SECTION ================= -->

			<table>

				<tr>

					<td>Applicant Name</td>

					<td><input type="text" name="applicantName"
						value="<%=DataUtility.getStringData(bean.getApplicantName())%>">
					</td>

					<td>Company Name</td>

					<td><input type="text" name="companyName"
						value="<%=DataUtility.getStringData(bean.getCompanyName())%>">
					</td>

					<td>Position</td>

					<td><%=HTMLUtility.getList("position", bean.getPosition(), positionMap)%></td>

					<td><input type="submit" name="operation"
						value="<%=JobApplicationListCtl.OP_SEARCH%>"> <input
						type="submit" name="operation"
						value="<%=JobApplicationListCtl.OP_RESET%>"></td>

				</tr>

			</table>

			<br>


			<!-- ================= LIST TABLE ================= -->

			<table border="1" style="width: 100%; border: groove;">

				<tr style="background-color: #e1e6f1e3;">

					<th width="5%"><input type="checkbox" id="selectall" /></th>
					<th>ID</th>
					<th>Applicant Name</th>
					<th>Company Name</th>
					<th>Position</th>
					<th>Application Date</th>
					<th>Edit</th>

				</tr>

				<%
					if (list != null && !list.isEmpty()) {

						for (JobApplicationBean b : list) {
				%>

				<tr align="center">

					<td><input type="checkbox" name="ids" class="case"
						value="<%=b.getId()%>"></td>
					<td><%=b.getId()%></td>
					<td><%=b.getApplicantName()%></td>
					<td><%=b.getCompanyName()%></td>
					<td><%=b.getPosition()%></td>
					<td><%=DataUtility.getDateString(b.getApplicationDate())%></td>
					<td><a
						href="<%=ORSView.JOB_APPLICATION_CTL%>?id=<%=b.getId()%>">Edit</a></td>

				</tr>

				<%
					}
					}
				%>

			</table>

			<br> <input type="hidden" name="pageNo" value="<%=pageNo%>">

			<input type="hidden" name="pageSize" value="<%=pageSize%>">


			<!-- ================= PAGINATION ================= -->

			<table width="90%">

				<tr>

					<td><input type="submit" name="operation"
						value="<%=JobApplicationListCtl.OP_PREVIOUS%>"
						<%if (pageNo == 1) {%> disabled <%}%>></td>


					<td align="center"><input type="submit" name="operation"
						value="<%=JobApplicationListCtl.OP_DELETE%>"> <input
						type="submit" name="operation"
						value="<%=JobApplicationListCtl.OP_NEW%>"></td>


					<td align="right"><input type="submit" name="operation"
						value="<%=JobApplicationListCtl.OP_NEXT%>"
						<%if (nextListSize == 0) {%> disabled <%}%>></td>

				</tr>

			</table>

		</div>

	</form>

</body>
</html>