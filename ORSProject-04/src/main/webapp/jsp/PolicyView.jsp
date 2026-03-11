<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.PolicyCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<html>
<head>
<title>Add Policy</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

	<form action="<%=ORSView.POLICY_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.PolicyBean"
			scope="request"></jsp:useBean>

		<%
			HashMap<String, String> statusMap = (HashMap<String, String>) request.getAttribute("statusMap");
		%>

		<div align="center">

			<h1 style="color: navy">
				<%
					if (bean != null && bean.getId() > 0) {
				%>
				Update Policy
				<%
					} else {
				%>
				Add Policy
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
					<th align="left">Policy Name</th>
					<td><input type="text" name="policyName"
						placeholder="Enter Policy Name"
						value="<%=DataUtility.getStringData(bean.getPolicyName())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("policyName", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Premium Amount</th>
					<td><input type="text" name="premiumAmount"
						placeholder="Enter Premium Amount"
						value="<%=DataUtility.getStringData(bean.getPremiumAmount())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("premiumAmount", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Start Date</th>
					<td><input type="text" name="startDate"
						placeholder="dd-MM-yyyy"
						value="<%=DataUtility.getDateString(bean.getStartDate())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("startDate", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Status</th>
					<td><%=HTMLUtility.getList("status", "", statusMap)%></td>
				</tr>

				<tr>
					<th></th>

					<%
						if (bean != null && bean.getId() > 0) {
					%>

					<td><input type="submit" name="operation"
						value="<%=PolicyCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=PolicyCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td><input type="submit" name="operation"
						value="<%=PolicyCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=PolicyCtl.OP_RESET%>"></td>

					<%
						}
					%>

				</tr>

			</table>

		</div>
	</form>
</body>
</html>