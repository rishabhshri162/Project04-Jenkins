<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.bean.VendorBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.VendorListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<html>
<head>
<title>Vendor List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png">
</head>
<body>

<form action="<%=ORSView.VENDOR_LIST_CTL%>" method="post">

	<%@ include file="Header.jsp"%>

	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.VendorBean"
		scope="request"></jsp:useBean>

	<%
		List<VendorBean> list =
			(List<VendorBean>) request.getAttribute("list");

		HashMap<String, String> serviceMap =
			(HashMap<String, String>) request.getAttribute("serviceMap");

		int pageNo = ServletUtility.getPageNo(request);
		int pageSize = ServletUtility.getPageSize(request);
		int nextListSize = (request.getAttribute("nextListSize") != null)
				? (Integer) request.getAttribute("nextListSize")
				: 0;
	%>

	<div align="center">

		<h1 style="color: navy">Vendor List</h1>

		<h3><font color="red">
			<%=ServletUtility.getErrorMessage(request)%>
		</font></h3>

		<h3><font color="green">
			<%=ServletUtility.getSuccessMessage(request)%>
		</font></h3>

		<!-- SEARCH SECTION -->

		<table>
			<tr>
				<td>Vendor Code</td>
				<td>
					<input type="text" name="vendorCode"
						value="<%=DataUtility.getStringData(bean.getVendorCode())%>">
				</td>

				<td>Vendor Name</td>
				<td>
					<input type="text" name="vendorName"
						value="<%=DataUtility.getStringData(bean.getVendorName())%>">
				</td>

				<td>Service Type</td>
				<td>
					<%=HTMLUtility.getList("serviceType",
						bean.getServiceType(), serviceMap)%>
				</td>

				<td>
					<input type="submit" name="operation"
						value="<%=VendorListCtl.OP_SEARCH%>">
					<input type="submit" name="operation"
						value="<%=VendorListCtl.OP_RESET%>">
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
				<th>Vendor Code</th>
				<th>Vendor Name</th>
				<th>Service Type</th>
				<th>Contact Number</th>
				<th>Address</th>
				<th>Edit</th>
			</tr>

			<%
				if (list != null && !list.isEmpty()) {
					for (VendorBean v : list) {
			%>

			<tr align="center">
				<td>
					<input type="checkbox" name="ids" class="case"
						value="<%=v.getId()%>">
				</td>

				<td><%=v.getId()%></td>
				<td><%=v.getVendorCode()%></td>
				<td><%=v.getVendorName()%></td>
				<td><%=v.getServiceType()%></td>
				<td><%=v.getContactNumber()%></td>
				<td><%=v.getAddress()%></td>

				<td>
					<a href="<%=ORSView.VENDOR_CTL%>?id=<%=v.getId()%>">
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
						value="<%=VendorListCtl.OP_PREVIOUS%>"
						<% if (pageNo == 1) { %> disabled <% } %>>
				</td>

				<td align="center">
					<input type="submit" name="operation"
						value="<%=VendorListCtl.OP_DELETE%>">
					<input type="submit" name="operation"
						value="<%=VendorListCtl.OP_NEW%>">
				</td>

				<td align="right">
					<input type="submit" name="operation"
						value="<%=VendorListCtl.OP_NEXT%>"
						<% if (nextListSize == 0) { %> disabled <% } %>>
				</td>

			</tr>
		</table>

	</div>

</form>

</body>
</html>