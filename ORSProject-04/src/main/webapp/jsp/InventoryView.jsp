<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.InventoryCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<html>
<head>
<title>Add Inventory</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

	<form action="<%=ORSView.INVENTORY_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.InventoryBean"
			scope="request"></jsp:useBean>

		<%
			HashMap<String, String> stockMap = (HashMap<String, String>) request.getAttribute("stockMap");
		%>

		<div align="center">

			<h1 style="color: navy">
				<%
					if (bean != null && bean.getId() > 0) {
				%>
				Update Inventory
				<%
					} else {
				%>
				Add Inventory
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

			<input type="hidden" name="id" value="<%=bean.getId()%>">

			<table>

				<tr>
					<th align="left">Item Name</th>
					<td><input type="text" name="itemName"
						placeholder="Enter Item Name"
						value="<%=DataUtility.getStringData(bean.getItemName())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("itemName", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Stock Status</th>
					<td><%=HTMLUtility.getList("stock", bean.getStock(), stockMap)%>
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("stock", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Price</th>
					<td><input type="text" name="price" placeholder="Enter Price"
						value="<%=DataUtility.getStringData(bean.getPrice())%>"></td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("price", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Quantity</th>
					<td><input type="text" name="quantity"
						placeholder="Enter Quantity"
						value="<%=DataUtility.getStringData(bean.getQuantity())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("quantity", request)%>
					</font></td>
				</tr>

				<tr>
					<th></th>

					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<td><input type="submit" name="operation"
						value="<%=InventoryCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=InventoryCtl.OP_CANCEL%>"></td>
					<%
						} else {
					%>
					<td><input type="submit" name="operation"
						value="<%=InventoryCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=InventoryCtl.OP_RESET%>"></td>
					<%
						}
					%>

				</tr>

			</table>

		</div>

	</form>

</body>
</html>
