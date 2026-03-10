<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.bean.DepartmentBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.DepartmentListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<html>
<head>
<title>Department List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png">
</head>
<body>

	<form action="<%=ORSView.DEPARTMENT_LIST_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.DepartmentBean"
			scope="request"></jsp:useBean>

		<%
			List<DepartmentBean> list = (List<DepartmentBean>) request.getAttribute("list");

			HashMap<String, String> statusMap = (HashMap<String, String>) request.getAttribute("statusMap");

			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int nextListSize = (request.getAttribute("nextListSize") != null)
					? (Integer) request.getAttribute("nextListSize")
					: 0;
		%>

		<div align="center">

			<h1 style="color: navy">Department List</h1>

			<h3>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</h3>

			<h3>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</h3>

			<!-- SEARCH SECTION -->

			<table>
				<tr>
					<td>Department Code</td>
					<td><input type="text" name="departmentCode"
						value="<%=DataUtility.getStringData(bean.getDepartmentCode())%>">
					</td>

					<td>Department Name</td>
					<td><input type="text" name="departmentName"
						value="<%=DataUtility.getStringData(bean.getDepartmentName())%>">
					</td>

					<td><input type="submit" name="operation"
						value="<%=DepartmentListCtl.OP_SEARCH%>"> <input
						type="submit" name="operation"
						value="<%=DepartmentListCtl.OP_RESET%>"></td>
				</tr>
			</table>

			<br>

			<!-- LIST TABLE -->

			<table border="1" style="width: 100%; border: groove;">

				<tr style="background-color: #e1e6f1e3;">
					<th width="5%"><input type="checkbox" id="selectall" /></th>
					<th>ID</th>
					<th>Department Code</th>
					<th>Department Name</th>
					<th>Department Head</th>
					<th>Status</th>
					<th>Edit</th>
				</tr>

				<%
					if (list != null && !list.isEmpty()) {
						for (DepartmentBean b : list) {
				%>

				<tr align="center">

					<td><input type="checkbox" name="ids" class="case"
						value="<%=b.getId()%>"></td>

					<td><%=b.getId()%></td>
					<td><%=b.getDepartmentCode()%></td>
					<td><%=b.getDepartmentName()%></td>
					<td><%=b.getDepartmentHead()%></td>
					<td><%=b.getDepartmentStatus()%></td>

					<td><a href="<%=ORSView.DEPARTMENT_CTL%>?id=<%=b.getId()%>">
							Edit </a></td>

				</tr>

				<%
					}
					}
				%>

			</table>

			<br> <input type="hidden" name="pageNo" value="<%=pageNo%>">
			<input type="hidden" name="pageSize" value="<%=pageSize%>">

			<!-- PAGINATION -->

			<table width="90%">
				<tr>

					<td><input type="submit" name="operation"
						value="<%=DepartmentListCtl.OP_PREVIOUS%>"
						<%if (pageNo == 1) {%> disabled <%}%>></td>

					<td align="center"><input type="submit" name="operation"
						value="<%=DepartmentListCtl.OP_DELETE%>"> <input
						type="submit" name="operation"
						value="<%=DepartmentListCtl.OP_NEW%>"></td>

					<td align="right"><input type="submit" name="operation"
						value="<%=DepartmentListCtl.OP_NEXT%>"
						<%if (nextListSize == 0) {%> disabled <%}%>></td>

				</tr>
			</table>

		</div>

	</form>

</body>
</html>