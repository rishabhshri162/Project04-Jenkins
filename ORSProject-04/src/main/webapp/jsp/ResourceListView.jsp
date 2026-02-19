<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.bean.ResourceBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.ResourceListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<html>
<head>
<title>Resource List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png">
</head>
<body>

	<form action="<%=ORSView.RESOURCE_LIST_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean"
			class="in.co.rays.proj4.bean.ResourceBean"
			scope="request"></jsp:useBean>

		<%
			List<ResourceBean> list =
				(List<ResourceBean>) request.getAttribute("list");

			HashMap<String, String> statusMap =
				(HashMap<String, String>) request.getAttribute("statusMap");

			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int nextListSize = (request.getAttribute("nextListSize") != null)
					? (Integer) request.getAttribute("nextListSize")
					: 0;
		%>

		<div align="center">

			<h1 style="color: navy">Resource List</h1>

			<h3>
				<font color="red">
					<%=ServletUtility.getErrorMessage(request)%>
				</font>
			</h3>

			<h3>
				<font color="green">
					<%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</h3>

			<!-- ================= SEARCH SECTION ================= -->

			<table>
				<tr>

					<td>Resource Code</td>
					<td>
						<input type="text" name="resourceCode"
							value="<%=DataUtility.getStringData(bean.getResourceCode())%>">
					</td>

					<td>Resource Name</td>
					<td>
						<input type="text" name="resourceName"
							value="<%=DataUtility.getStringData(bean.getResourceName())%>">
					</td>

					<td>Status</td>
					<td>
						<%=HTMLUtility.getList("resourceStatus",
								bean.getResourceStatus(),
								statusMap)%>
					</td>

					<td>
						<input type="submit" name="operation"
							value="<%=ResourceListCtl.OP_SEARCH%>">
						<input type="submit" name="operation"
							value="<%=ResourceListCtl.OP_RESET%>">
					</td>

				</tr>
			</table>

			<br>

			<!-- ================= LIST TABLE ================= -->

			<table border="1" style="width: 100%; border: groove;">
				<tr style="background-color: #e1e6f1e3;">
					<th>Select</th>
					<th>ID</th>
					<th>Resource Code</th>
					<th>Resource Name</th>
					<th>Resource Type</th>
					<th>Status</th>
					<th>Edit</th>
				</tr>

				<%
					if (list != null && !list.isEmpty()) {

						for (ResourceBean r : list) {
				%>

				<tr align="center">

					<td>
						<input type="checkbox" name="ids"
							value="<%=r.getId()%>">
					</td>

					<td><%=r.getId()%></td>
					<td><%=r.getResourceCode()%></td>
					<td><%=r.getResourceName()%></td>
					<td><%=r.getResourceType()%></td>
					<td><%=r.getResourceStatus()%></td>

					<td>
						<a href="<%=ORSView.RESOURCE_CTL%>?id=<%=r.getId()%>">
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

			<input type="hidden" name="pageNo"
				value="<%=pageNo%>">

			<input type="hidden" name="pageSize"
				value="<%=pageSize%>">

			<!-- ================= PAGINATION ================= -->

			<table width="90%">
				<tr>

					<td>
						<input type="submit" name="operation"
							value="<%=ResourceListCtl.OP_PREVIOUS%>"
							<% if (pageNo == 1) { %> disabled <% } %>>
					</td>

					<td align="center">
						<input type="submit" name="operation"
							value="<%=ResourceListCtl.OP_DELETE%>">

						<input type="submit" name="operation"
							value="<%=ResourceListCtl.OP_NEW%>">
					</td>

					<td align="right">
						<input type="submit" name="operation"
							value="<%=ResourceListCtl.OP_NEXT%>"
							<% if (nextListSize == 0) { %> disabled <% } %>>
					</td>

				</tr>
			</table>

		</div>
	</form>
</body>
</html>
