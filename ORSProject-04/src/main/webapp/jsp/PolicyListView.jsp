<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.bean.PolicyBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.PolicyListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>

<html>
<head>
<title>Policy List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png">
</head>
<body>

	<form action="<%=ORSView.POLICY_LIST_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.PolicyBean"
			scope="request"></jsp:useBean>

		<%
			List<PolicyBean> list = (List<PolicyBean>) request.getAttribute("list");

			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int nextListSize = (request.getAttribute("nextListSize") != null)
					? (Integer) request.getAttribute("nextListSize")
					: 0;
		%>

		<div align="center">

			<h1 style="color: navy">Policy List</h1>

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

					<td>Policy Name</td>
					<td><input type="text" name="policyName"
						value="<%=DataUtility.getStringData(bean.getPolicyName())%>">
					</td>

					<td><input type="submit" name="operation"
						value="<%=PolicyListCtl.OP_SEARCH%>"> <input type="submit"
						name="operation" value="<%=PolicyListCtl.OP_RESET%>"></td>

				</tr>
			</table>

			<br>

			<!-- LIST TABLE -->

			<table border="1" style="width: 100%; border: groove;">

				<tr style="background-color: #e1e6f1e3;">
					<th width="5%"><input type="checkbox" id="selectall" /></th>
					<th>ID</th>
					<th>Policy Name</th>
					<th>Premium Amount</th>
					<th>Start Date</th>
					<th>Edit</th>
				</tr>

				<%
					if (list != null && !list.isEmpty()) {
						for (PolicyBean b : list) {
				%>

				<tr align="center">

					<td><input type="checkbox" name="ids" class="case"
						value="<%=b.getId()%>"></td>

					<td><%=b.getId()%></td>
					<td><%=b.getPolicyName()%></td>
					<td><%=b.getPremiumAmount()%></td>
					<td><%= new java.text.SimpleDateFormat("dd-MM-yyyy").format(b.getStartDate()) %></td>
					<td><a href="<%=ORSView.POLICY_CTL%>?id=<%=b.getId()%>">
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
						value="<%=PolicyListCtl.OP_PREVIOUS%>" <%if (pageNo == 1) {%>
						disabled <%}%>></td>

					<td align="center"><input type="submit" name="operation"
						value="<%=PolicyListCtl.OP_DELETE%>"> <input type="submit"
						name="operation" value="<%=PolicyListCtl.OP_NEW%>"></td>

					<td align="right"><input type="submit" name="operation"
						value="<%=PolicyListCtl.OP_NEXT%>" <%if (nextListSize == 0) {%>
						disabled <%}%>></td>

				</tr>
			</table>

		</div>

	</form>

</body>
</html>