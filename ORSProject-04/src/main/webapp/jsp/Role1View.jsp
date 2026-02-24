<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.Role1Ctl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<html>
<head>
<title>Add Role Module</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

	<form action="<%=ORSView.ROLE1_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.Role1Bean"
			scope="request"></jsp:useBean>

		<%
			HashMap<String, String> statusMap = (HashMap<String, String>) request.getAttribute("statusMap");
		%>

		<div align="center">

			<h1 style="color: navy">
				<%
					if (bean != null && bean.getId() > 0) {
				%>
				Add Role Module
				<%
					} else {
				%>
				Add Role Module
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

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">

			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">

			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table>

				<tr>
					<th align="left">Role Code</th>
					<td><input type="text" name="roleCode"
						placeholder="Enter Role Code"
						value="<%=DataUtility.getStringData(bean.getRoleCode())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("roleCode", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Role Name</th>
					<td><input type="text" name="roleName"
						placeholder="Enter Role Name"
						value="<%=DataUtility.getStringData(bean.getRoleName())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("roleName", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Description</th>
					<td><input type="text" name="roleDescription"
						placeholder="Enter Role Description"
						value="<%=DataUtility.getStringData(bean.getRoleDescription())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("roleDescription", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Status</th>
					<td><%=HTMLUtility.getList("status", bean.getStatus(), statusMap)%></td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("status", request)%>
					</font></td>
				</tr>

				<tr>
					<th></th>

					<%
						if (bean != null && bean.getId() > 0) {
					%>

					<td><input type="submit" name="operation"
						value="<%=Role1Ctl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=Role1Ctl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td><input type="submit" name="operation"
						value="<%=Role1Ctl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=Role1Ctl.OP_RESET%>"></td>

					<%
						}
					%>

				</tr>

			</table>

		</div>
	</form>
</body>
</html>