<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.ClientCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<html>
<head>
<title>Add Client</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

	<form action="<%=ORSView.CLIENT_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.ClientBean"
			scope="request"></jsp:useBean>

		<%
			HashMap<String, String> priorityMap = (HashMap<String, String>) request.getAttribute("priorityMap");
		%>

		<div align="center">

			<h1 style="color: navy">
				<%
					if (bean != null && bean.getId() > 0) {
				%>
				Update Client
				<%
					} else {
				%>
				Add Client
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
					<th align="left">Client Name</th>
					<td><input type="text" name="clientName"
						placeholder="Enter Client Name"
						value="<%=DataUtility.getStringData(bean.getClientName())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("clientName", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Email</th>
					<td><input type="text" name="email" placeholder="Enter Email"
						value="<%=DataUtility.getStringData(bean.getEmail())%>"></td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("email", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Phone</th>
					<td><input type="text" name="phone"
						placeholder="Enter Phone Number"
						value="<%=DataUtility.getStringData(bean.getPhone())%>"></td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("phone", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Priority</th>
					<td><%=HTMLUtility.getList("priority", bean.getPriority(), priorityMap)%></td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("priority", request)%>
					</font></td>
				</tr>

				<tr>
					<th></th>

					<%
						if (bean != null && bean.getId() > 0) {
					%>

					<td><input type="submit" name="operation"
						value="<%=ClientCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=ClientCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td><input type="submit" name="operation"
						value="<%=ClientCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=ClientCtl.OP_RESET%>"></td>

					<%
						}
					%>

				</tr>

			</table>

		</div>
	</form>
</body>
</html>
