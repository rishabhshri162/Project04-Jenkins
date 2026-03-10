<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.DepartmentCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<html>
<head>
<title>Add Department</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

	<form action="<%=ORSView.DEPARTMENT_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.DepartmentBean"
			scope="request"></jsp:useBean>

		<%
			HashMap<String, String> statusMap = (HashMap<String, String>) request.getAttribute("statusMap");
		%>

		<div align="center">

			<h1 style="color: navy">
				<%
					if (bean != null && bean.getId() > 0) {
				%>
				Update Department
				<%
					} else {
				%>
				Add Department
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
					<th align="left">Department Code</th>
					<td><input type="text" name="departmentCode"
						placeholder="Enter Department Code"
						value="<%=DataUtility.getStringData(bean.getDepartmentCode())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("departmentCode", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Department Name</th>
					<td><input type="text" name="departmentName"
						placeholder="Enter Department Name"
						value="<%=DataUtility.getStringData(bean.getDepartmentName())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("departmentName", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Department Head</th>
					<td><input type="text" name="departmentHead"
						placeholder="Enter Department Head"
						value="<%=DataUtility.getStringData(bean.getDepartmentHead())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("departmentHead", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Department Status</th>
					<td><%=HTMLUtility.getList("departmentStatus", bean.getDepartmentStatus(), statusMap)%></td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("departmentStatus", request)%>
					</font></td>
				</tr>

				<tr>
					<th></th>

					<%
						if (bean != null && bean.getId() > 0) {
					%>

					<td><input type="submit" name="operation"
						value="<%=DepartmentCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=DepartmentCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td><input type="submit" name="operation"
						value="<%=DepartmentCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=DepartmentCtl.OP_RESET%>"></td>

					<%
						}
					%>

				</tr>

			</table>

		</div>
	</form>
</body>
</html>