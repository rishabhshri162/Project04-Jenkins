<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.VendorCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<html>
<head>
<title>Add Vendor</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

	<form action="<%=ORSView.VENDOR_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean"
			class="in.co.rays.proj4.bean.VendorBean"
			scope="request"></jsp:useBean>

		<%
			HashMap<String, String> serviceMap =
				(HashMap<String, String>) request.getAttribute("serviceMap");
		%>

		<div align="center">

			<h1 style="color: navy">
				<%
					if (bean != null && bean.getId() > 0) {
				%>
				Update Vendor
				<%
					} else {
				%>
				Add Vendor
				<%
					}
				%>
			</h1>

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

			<input type="hidden" name="id"
				value="<%=bean.getId()%>">

			<input type="hidden" name="createdBy"
				value="<%=bean.getCreatedBy()%>">

			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>">

			<input type="hidden" name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">

			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table>

				<tr>
					<th align="left">Vendor Code</th>
					<td>
						<input type="text" name="vendorCode"
							placeholder="Enter Vendor Code"
							value="<%=DataUtility.getStringData(bean.getVendorCode())%>">
					</td>
					<td>
						<font color="red">
							<%=ServletUtility.getErrorMessage("vendorCode", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Vendor Name</th>
					<td>
						<input type="text" name="vendorName"
							placeholder="Enter Vendor Name"
							value="<%=DataUtility.getStringData(bean.getVendorName())%>">
					</td>
					<td>
						<font color="red">
							<%=ServletUtility.getErrorMessage("vendorName", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Service Type</th>
					<td>
						<%=HTMLUtility.getList("serviceType",
								bean.getServiceType(),
								serviceMap)%>
					</td>
					<td>
						<font color="red">
							<%=ServletUtility.getErrorMessage("serviceType", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Contact Number</th>
					<td>
						<input type="text" name="contactNumber"
							placeholder="Enter Contact Number"
							value="<%=DataUtility.getStringData(bean.getContactNumber())%>">
					</td>
					<td>
						<font color="red">
							<%=ServletUtility.getErrorMessage("contactNumber", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Address</th>
					<td>
						<input type="text" name="address"
							placeholder="Enter Address"
							value="<%=DataUtility.getStringData(bean.getAddress())%>">
					</td>
					<td>
						<font color="red">
							<%=ServletUtility.getErrorMessage("address", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th></th>

					<%
						if (bean != null && bean.getId() > 0) {
					%>

					<td>
						<input type="submit" name="operation"
							value="<%=VendorCtl.OP_UPDATE%>">

						<input type="submit" name="operation"
							value="<%=VendorCtl.OP_CANCEL%>">
					</td>

					<%
						} else {
					%>

					<td>
						<input type="submit" name="operation"
							value="<%=VendorCtl.OP_SAVE%>">

						<input type="submit" name="operation"
							value="<%=VendorCtl.OP_RESET%>">
					</td>

					<%
						}
					%>

				</tr>

			</table>

		</div>
	</form>
</body>
</html>