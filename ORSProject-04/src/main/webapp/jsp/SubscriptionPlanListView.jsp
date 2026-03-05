<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.bean.SubscriptionPlanBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.SubscriptionPlanListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>

<html>
<head>
<title>Subscription Plan List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png">
</head>

<body>

	<form action="<%=ORSView.SUBSCRIPTION_PLAN_LIST_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean"
			class="in.co.rays.proj4.bean.SubscriptionPlanBean" scope="request"></jsp:useBean>

		<%
			List<SubscriptionPlanBean> list = (List<SubscriptionPlanBean>) request.getAttribute("list");

			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);

			int nextListSize = (request.getAttribute("nextListSize") != null)
					? (Integer) request.getAttribute("nextListSize")
					: 0;
		%>

		<div align="center">

			<h1 style="color: navy">Subscription Plan List</h1>

			<h3>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</h3>

			<h3>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</h3>

			<!-- SEARCH SECTION -->

			<table>

				<tr>

					<td>Plan Name</td>

					<td><input type="text" name="planName"
						value="<%=DataUtility.getStringData(bean.getPlanName())%>">
					</td>

					<td><input type="submit" name="operation"
						value="<%=SubscriptionPlanListCtl.OP_SEARCH%>"> <input
						type="submit" name="operation"
						value="<%=SubscriptionPlanListCtl.OP_RESET%>"></td>

				</tr>

			</table>

			<br>

			<!-- LIST TABLE -->

			<table border="1" style="width: 100%; border: groove;">

				<tr style="background-color: #e1e6f1e3;">

					<th width="5%"><input type="checkbox" id="selectall" /></th>

					<th>ID</th>
					<th>Plan Name</th>
					<th>Price</th>
					<th>Validity Days</th>
					<th>Edit</th>

				</tr>

				<%
					if (list != null && !list.isEmpty()) {

						for (SubscriptionPlanBean b : list) {
				%>

				<tr align="center">

					<td><input type="checkbox" name="ids" class="case"
						value="<%=b.getId()%>"></td>

					<td><%=b.getId()%></td>
					<td><%=b.getPlanName()%></td>
					<td><%=b.getPrice()%></td>
					<td><%=b.getValidityDays()%></td>

					<td><a
						href="<%=ORSView.SUBSCRIPTION_PLAN_CTL%>?id=<%=b.getId()%>">
							Edit </a></td>

				</tr>

				<%
					}
					}
				%>

			</table>

			<br> <input type="hidden" name="pageNo" value="<%=pageNo%>">
			<input type="hidden" name="pageSize" value="<%=pageSize%>">

			<!-- PAGINATION -->

			<table width="90%">

				<tr>

					<td><input type="submit" name="operation"
						value="<%=SubscriptionPlanListCtl.OP_PREVIOUS%>"
						<%if (pageNo == 1) {%> disabled <%}%>></td>

					<td align="center"><input type="submit" name="operation"
						value="<%=SubscriptionPlanListCtl.OP_DELETE%>"> <input
						type="submit" name="operation"
						value="<%=SubscriptionPlanListCtl.OP_NEW%>"></td>

					<td align="right"><input type="submit" name="operation"
						value="<%=SubscriptionPlanListCtl.OP_NEXT%>"
						<%if (nextListSize == 0) {%> disabled <%}%>></td>

				</tr>

			</table>

		</div>

	</form>

</body>
</html>