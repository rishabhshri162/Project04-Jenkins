<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.bean.BatchBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.BatchListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<html>
<head>
<title>Batch Module List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png">
</head>
<body>

<form action="<%=ORSView.BATCH_LIST_CTL%>" method="post">

	<%@ include file="Header.jsp"%>

	<jsp:useBean id="bean"
		class="in.co.rays.proj4.bean.BatchBean"
		scope="request"></jsp:useBean>

	<%
		List<BatchBean> list =
			(List<BatchBean>) request.getAttribute("list");

		HashMap<String, String> timingMap =
			(HashMap<String, String>) request.getAttribute("timingMap");

		int pageNo = ServletUtility.getPageNo(request);
		int pageSize = ServletUtility.getPageSize(request);
		int nextListSize = (request.getAttribute("nextListSize") != null)
				? (Integer) request.getAttribute("nextListSize")
				: 0;
	%>

	<div align="center">

		<h1 style="color: navy">Batch Module List</h1>

		<h3><font color="red">
			<%=ServletUtility.getErrorMessage(request)%>
		</font></h3>

		<h3><font color="green">
			<%=ServletUtility.getSuccessMessage(request)%>
		</font></h3>

		<!-- SEARCH SECTION -->

		<table>
			<tr>
				<td>Batch Code</td>
				<td>
					<input type="text" name="batchCode"
						value="<%=DataUtility.getStringData(bean.getBatchCode())%>">
				</td>

				<td>Batch Name</td>
				<td>
					<input type="text" name="batchName"
						value="<%=DataUtility.getStringData(bean.getBatchName())%>">
				</td>

				<td>Trainer Name</td>
				<td>
					<input type="text" name="trainerName"
						value="<%=DataUtility.getStringData(bean.getTrainerName())%>">
				</td>

				<td>Timing</td>
				<td>
					<%=HTMLUtility.getList("batchTiming",
						bean.getBatchTiming(), timingMap)%>
				</td>

				<td>
					<input type="submit" name="operation"
						value="<%=BatchListCtl.OP_SEARCH%>">
					<input type="submit" name="operation"
						value="<%=BatchListCtl.OP_RESET%>">
				</td>
			</tr>
		</table>

		<br>

		<!-- LIST TABLE -->

		<table border="1" style="width: 100%; border: groove;">
			<tr style="background-color: #e1e6f1e3;">
				<th width="5%">
					<input type="checkbox" id="selectall" />
				</th>
				<th>ID</th>
				<th>Batch Code</th>
				<th>Batch Name</th>
				<th>Trainer Name</th>
				<th>Batch Timing</th>
				<th>Edit</th>
			</tr>

			<%
				if (list != null && !list.isEmpty()) {
					for (BatchBean b : list) {
			%>

			<tr align="center">
				<td>
					<input type="checkbox" name="ids" class="case"
						value="<%=b.getId()%>">
				</td>

				<td><%=b.getId()%></td>
				<td><%=b.getBatchCode()%></td>
				<td><%=b.getBatchName()%></td>
				<td><%=b.getTrainerName()%></td>
				<td><%=b.getBatchTiming()%></td>

				<td>
					<a href="<%=ORSView.BATCH_CTL%>?id=<%=b.getId()%>">
						Edit
					</a>
				</td>
			</tr>

			<%
					}
				}
			%>

		</table>

		<br>

		<input type="hidden" name="pageNo" value="<%=pageNo%>">
		<input type="hidden" name="pageSize" value="<%=pageSize%>">

		<!-- PAGINATION -->

		<table width="90%">
			<tr>

				<td>
					<input type="submit" name="operation"
						value="<%=BatchListCtl.OP_PREVIOUS%>"
						<% if (pageNo == 1) { %> disabled <% } %>>
				</td>

				<td align="center">
					<input type="submit" name="operation"
						value="<%=BatchListCtl.OP_DELETE%>">
					<input type="submit" name="operation"
						value="<%=BatchListCtl.OP_NEW%>">
				</td>

				<td align="right">
					<input type="submit" name="operation"
						value="<%=BatchListCtl.OP_NEXT%>"
						<% if (nextListSize == 0) { %> disabled <% } %>>
				</td>

			</tr>
		</table>

	</div>

</form>

</body>
</html>