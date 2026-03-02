<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.bean.BoardingPassBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.BoardingPassListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<html>
<head>
<title>Boarding Pass List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png">
</head>
<body>

	<form action="<%=ORSView.BOARDING_PASS_LIST_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.BoardingPassBean"
			scope="request"></jsp:useBean>

		<%
			List<BoardingPassBean> list = (List<BoardingPassBean>) request.getAttribute("list");

			HashMap<String, String> gateMap = (HashMap<String, String>) request.getAttribute("gateMap");

			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int nextListSize = (request.getAttribute("nextListSize") != null)
					? (Integer) request.getAttribute("nextListSize")
					: 0;
		%>

		<div align="center">

			<h1 style="color: navy">Boarding Pass List</h1>

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
					<td>Seat Number</td>
					<td><input type="text" name="seatNumber"
						value="<%=DataUtility.getStringData(bean.getSeatNumber())%>">
					</td>

					<td>Gate</td>
					<td><%=HTMLUtility.getList("gate", bean.getGate(), gateMap)%></td>

					<td><input type="submit" name="operation"
						value="<%=BoardingPassListCtl.OP_SEARCH%>"> <input
						type="submit" name="operation"
						value="<%=BoardingPassListCtl.OP_RESET%>"></td>
				</tr>
			</table>

			<br>

			<!-- LIST TABLE -->

			<table border="1" style="width: 100%; border: groove;">

				<tr style="background-color: #e1e6f1e3;">
					<th width="5%"><input type="checkbox" id="selectall" /></th>
					<th>ID</th>
					<th>Seat Number</th>
					<th>Gate</th>
					<th>Boarding Time</th>
					<th>Edit</th>
				</tr>

				<%
					if (list != null && !list.isEmpty()) {
						for (BoardingPassBean b : list) {
				%>

				<tr align="center">

					<td><input type="checkbox" name="ids" class="case"
						value="<%=b.getId()%>"></td>

					<td><%=b.getId()%></td>
					<td><%=b.getSeatNumber()%></td>
					<td><%=b.getGate()%></td>
					<td><%=b.getBoardingTime()%></td>

					<td><a href="<%=ORSView.BOARDING_PASS_CTL%>?id=<%=b.getId()%>">
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
						value="<%=BoardingPassListCtl.OP_PREVIOUS%>"
						<%if (pageNo == 1) {%> disabled <%}%>></td>

					<td align="center"><input type="submit" name="operation"
						value="<%=BoardingPassListCtl.OP_DELETE%>"> <input
						type="submit" name="operation"
						value="<%=BoardingPassListCtl.OP_NEW%>"></td>

					<td align="right"><input type="submit" name="operation"
						value="<%=BoardingPassListCtl.OP_NEXT%>"
						<%if (nextListSize == 0) {%> disabled <%}%>></td>

				</tr>
			</table>

		</div>

	</form>

</body>
</html>