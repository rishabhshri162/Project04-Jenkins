<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.bean.InventoryBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.InventoryListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<html>
<head>
<title>Inventory List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png">
</head>
<body>

	<form action="<%=ORSView.INVENTORY_LIST_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.InventoryBean"
			scope="request"></jsp:useBean>

		<%
			List<InventoryBean> list = (List<InventoryBean>) request.getAttribute("list");

			HashMap<String, String> stockMap = (HashMap<String, String>) request.getAttribute("stockMap");

			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int nextListSize = (request.getAttribute("nextListSize") != null)
					? (Integer) request.getAttribute("nextListSize")
					: 0;
		%>

		<div align="center">

			<h1 style="color: navy">Inventory List</h1>

			<h3>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</h3>

			<h3>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</h3>

			<!-- Search Section -->

			<table>
				<tr>

					<td>Item Name</td>
					<td><input type="text" name="itemName"
						value="<%=DataUtility.getStringData(bean.getItemName())%>">
					</td>

					<td>Stock Status</td>
					<td><%=HTMLUtility.getList("stock", bean.getStock(), stockMap)%></td>

					<td><input type="submit" name="operation"
						value="<%=InventoryListCtl.OP_SEARCH%>"> <input
						type="submit" name="operation"
						value="<%=InventoryListCtl.OP_RESET%>"></td>

				</tr>
			</table>

			<br>

			<!-- List Table -->

			<table border="1" style="width: 100%; border: groove;">
				<tr style="background-color: #e1e6f1e3;">
					<th>Select</th>
					<th>ID</th>
					<th>Item Name</th>
					<th>Stock</th>
					<th>Price</th>
					<th>Quantity</th>
					<th>Edit</th>
				</tr>

				<%
					if (list != null && !list.isEmpty()) {
						for (InventoryBean inv : list) {
				%>

				<tr align="center">

					<td><input type="checkbox" name="ids" value="<%=inv.getId()%>">
					</td>

					<td><%=inv.getId()%></td>
					<td><%=inv.getItemName()%></td>
					<td><%=inv.getStock()%></td>
					<td><%=inv.getPrice()%></td>
					<td><%=inv.getQuantity()%></td>

					<td><a href="<%=ORSView.INVENTORY_CTL%>?id=<%=inv.getId()%>">
							Edit </a></td>

				</tr>

				<%
					}
					}
				%>

			</table>

			<br> <input type="hidden" name="pageNo" value="<%=pageNo%>">
			<input type="hidden" name="pageSize" value="<%=pageSize%>">

			<table width="90%">
				<tr>

					<td><input type="submit" name="operation"
						value="<%=InventoryListCtl.OP_PREVIOUS%>" <%if (pageNo == 1) {%>
						disabled <%}%>></td>

					<td align="center"><input type="submit" name="operation"
						value="<%=InventoryListCtl.OP_DELETE%>"> <input
						type="submit" name="operation"
						value="<%=InventoryListCtl.OP_NEW%>"></td>

					<td align="right"><input type="submit" name="operation"
						value="<%=InventoryListCtl.OP_NEXT%>" <%if (nextListSize == 0) {%>
						disabled <%}%>></td>

				</tr>
			</table>

		</div>

	</form>

</body>
</html>
