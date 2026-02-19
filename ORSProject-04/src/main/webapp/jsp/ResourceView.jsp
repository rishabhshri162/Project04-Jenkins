<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.ResourceCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<html>
<head>
<title>Add Resource</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

	<form action="<%=ORSView.RESOURCE_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean"
			class="in.co.rays.proj4.bean.ResourceBean"
			scope="request"></jsp:useBean>

		<%
			HashMap<String, String> statusMap =
				(HashMap<String, String>) request.getAttribute("statusMap");
		%>

		<div align="center">

			<h1 style="color: navy">
				<%
					if (bean != null && bean.getId() > 0) {
				%>
				Update Resource
				<%
					} else {
				%>
				Add Resource
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
					<th align="left">Resource Code</th>
					<td>
						<input type="text" name="resourceCode"
							placeholder="Enter Resource Code"
							value="<%=DataUtility.getStringData(bean.getResourceCode())%>">
					</td>
					<td>
						<font color="red">
							<%=ServletUtility.getErrorMessage("resourceCode", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Resource Name</th>
					<td>
						<input type="text" name="resourceName"
							placeholder="Enter Resource Name"
							value="<%=DataUtility.getStringData(bean.getResourceName())%>">
					</td>
					<td>
						<font color="red">
							<%=ServletUtility.getErrorMessage("resourceName", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Resource Type</th>
					<td>
						<input type="text" name="resourceType"
							placeholder="Enter Resource Type"
							value="<%=DataUtility.getStringData(bean.getResourceType())%>">
					</td>
					<td>
						<font color="red">
							<%=ServletUtility.getErrorMessage("resourceType", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Resource Status</th>
					<td>
						<%=HTMLUtility.getList("resourceStatus",
								bean.getResourceStatus(),
								statusMap)%>
					</td>
					<td>
						<font color="red">
							<%=ServletUtility.getErrorMessage("resourceStatus", request)%>
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
							value="<%=ResourceCtl.OP_UPDATE%>">

						<input type="submit" name="operation"
							value="<%=ResourceCtl.OP_CANCEL%>">
					</td>

					<%
						} else {
					%>

					<td>
						<input type="submit" name="operation"
							value="<%=ResourceCtl.OP_SAVE%>">

						<input type="submit" name="operation"
							value="<%=ResourceCtl.OP_RESET%>">
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
