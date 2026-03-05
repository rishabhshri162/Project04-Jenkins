<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.SubscriptionPlanCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Subscription Plan</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

	<form action="<%=ORSView.SUBSCRIPTION_PLAN_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean"
			class="in.co.rays.proj4.bean.SubscriptionPlanBean" scope="request"></jsp:useBean>

		<%
			HashMap<String, String> daysMap = (HashMap<String, String>) request.getAttribute("daysMap");
		%>

		<div align="center">

			<h1 style="color: navy">
				<%
					if (bean != null && bean.getId() > 0) {
				%>
				Update Subscription Plan
				<%
					} else {
				%>
				Add Subscription Plan
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
					<th align="left">Plan Name</th>

					<td><input type="text" name="planName"
						placeholder="Enter Plan Name"
						value="<%=DataUtility.getStringData(bean.getPlanName())%>">
					</td>

					<td><font color="red"> <%=ServletUtility.getErrorMessage("planName", request)%>
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
					<th align="left">Validity Days</th>
					<td><%=HTMLUtility.getList("validityDays", DataUtility.getStringData(bean.getValidityDays()), daysMap)%></td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("validityDays", request)%>
					</font></td>
				</tr>

				<tr>
					<th></th>

					<%
						if (bean != null && bean.getId() > 0) {
					%>

					<td><input type="submit" name="operation"
						value="<%=SubscriptionPlanCtl.OP_UPDATE%>"> <input
						type="submit" name="operation"
						value="<%=SubscriptionPlanCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td><input type="submit" name="operation"
						value="<%=SubscriptionPlanCtl.OP_SAVE%>"> <input
						type="submit" name="operation"
						value="<%=SubscriptionPlanCtl.OP_RESET%>"></td>

					<%
						}
					%>

				</tr>

			</table>

		</div>

	</form>

</body>
</html>