<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.bean.Role1Bean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.Role1ListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<html>
<head>
<title>Role Module List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png">
</head>
<body>

<form action="<%=ORSView.ROLE1_LIST_CTL%>" method="post">

	<%@ include file="Header.jsp"%>

	<jsp:useBean id="bean"
		class="in.co.rays.proj4.bean.Role1Bean"
		scope="request"></jsp:useBean>

	<%
		List<Role1Bean> list =
			(List<Role1Bean>) request.getAttribute("list");

		HashMap<String, String> statusMap =
			(HashMap<String, String>) request.getAttribute("statusMap");

		int pageNo = ServletUtility.getPageNo(request);
		int pageSize = ServletUtility.getPageSize(request);
		int nextListSize = (request.getAttribute("nextListSize") != null)
				? (Integer) request.getAttribute("nextListSize")
				: 0;
	%>

	<div align="center">

		<h1 style="color: navy">Role Module List</h1>

		<h3><font color="red">
			<%=ServletUtility.getErrorMessage(request)%>
		</font></h3>

		<h3><font color="green">
			<%=ServletUtility.getSuccessMessage(request)%>
		</font></h3>

		<!-- SEARCH SECTION -->

		<table>
			<tr>
				<td>Role Code</td>
				<td>
					<input type="text" name="roleCode"
						value="<%=DataUtility.getStringData(bean.getRoleCode())%>">
				</td>

				<td>Role Name</td>
				<td>
					<input type="text" name="roleName"
						value="<%=DataUtility.getStringData(bean.getRoleName())%>">
				</td>

				<td>Status</td>
				<td>
					<%=HTMLUtility.getList("status",
						bean.getStatus(), statusMap)%>
				</td>

				<td>
					<input type="submit" name="operation"
						value="<%=Role1ListCtl.OP_SEARCH%>">
					<input type="submit" name="operation"
						value="<%=Role1ListCtl.OP_RESET%>">
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
				<th>Role Code</th>
				<th>Role Name</th>
				<th>Description</th>
				<th>Status</th>
				<th>Edit</th>
			</tr>

			<%
				if (list != null && !list.isEmpty()) {
					for (Role1Bean r : list) {
			%>

			<tr align="center">
				<td>
					<input type="checkbox" name="ids" class="case"
						value="<%=r.getId()%>">
				</td>

				<td><%=r.getId()%></td>
				<td><%=r.getRoleCode()%></td>
				<td><%=r.getRoleName()%></td>
				<td><%=r.getRoleDescription()%></td>
				<td><%=r.getStatus()%></td>

				<td>
					<a href="<%=ORSView.ROLE1_CTL%>?id=<%=r.getId()%>">
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
						value="<%=Role1ListCtl.OP_PREVIOUS%>"
						<% if (pageNo == 1) { %> disabled <% } %>>
				</td>

				<td align="center">
					<input type="submit" name="operation"
						value="<%=Role1ListCtl.OP_DELETE%>">
					<input type="submit" name="operation"
						value="<%=Role1ListCtl.OP_NEW%>">
				</td>

				<td align="right">
					<input type="submit" name="operation"
						value="<%=Role1ListCtl.OP_NEXT%>"
						<% if (nextListSize == 0) { %> disabled <% } %>>
				</td>

			</tr>
		</table>

	</div>

</form>

</body>
</html>