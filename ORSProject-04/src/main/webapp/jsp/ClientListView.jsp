<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.bean.ClientBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.ClientListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<html>
<head>
<title>Client List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png">
</head>
<body>

	<form action="<%=ORSView.CLIENT_LIST_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.ClientBean"
			scope="request"></jsp:useBean>

		<%
			List<ClientBean> list = (List<ClientBean>) request.getAttribute("list");

			HashMap<String, String> statusMap = (HashMap<String, String>) request.getAttribute("statusMap");

			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int nextListSize = (request.getAttribute("nextListSize") != null)
					? (Integer) request.getAttribute("nextListSize")
					: 0;
		%>

		<div align="center">

			<h1 style="color: navy">Client List</h1>

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

					<td>Client Name</td>
					<td><input type="text" name="clientName"
						value="<%=DataUtility.getStringData(bean.getClientName())%>">
					</td>

					<td>Email</td>
					<td><input type="text" name="email"
						value="<%=DataUtility.getStringData(bean.getEmail())%>"></td>

					<td>Status</td>
					<td><%=HTMLUtility.getList("priority", bean.getPriority(), statusMap)%></td>

					<td><input type="submit" name="operation"
						value="<%=ClientListCtl.OP_SEARCH%>"> <input type="submit"
						name="operation" value="<%=ClientListCtl.OP_RESET%>"></td>

				</tr>
			</table>

			<br>

			<!-- ================= LIST TABLE ================= -->

			<table border="1" style="width: 100%; border: groove;">
				<tr style="background-color: #e1e6f1e3;">
					<th>Select</th>
					<th>ID</th>
					<th>Client Name</th>
					<th>Email</th>
					<th>Phone</th>
					<th>Status</th>
					<th>Edit</th>
				</tr>

				<%
					if (list != null && !list.isEmpty()) {

						for (ClientBean c : list) {
				%>

				<tr align="center">

					<td><input type="checkbox" name="ids" value="<%=c.getId()%>">
					</td>

					<td><%=c.getId()%></td>
					<td><%=c.getClientName()%></td>
					<td><%=c.getEmail()%></td>
					<td><%=c.getPhone()%></td>
					<td><%=c.getPriority()%></td>

					<td><a href="<%=ORSView.CLIENT_CTL%>?id=<%=c.getId()%>">
							Edit </a></td>

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
						value="<%=ClientListCtl.OP_PREVIOUS%>" <%if (pageNo == 1) {%>
						disabled <%}%>></td>

					<td align="center"><input type="submit" name="operation"
						value="<%=ClientListCtl.OP_DELETE%>"> <input type="submit"
						name="operation" value="<%=ClientListCtl.OP_NEW%>"></td>

					<td align="right"><input type="submit" name="operation"
						value="<%=ClientListCtl.OP_NEXT%>" <%if (nextListSize == 0) {%>
						disabled <%}%>></td>

				</tr>
			</table>

		</div>
	</form>
</body>
</html>
