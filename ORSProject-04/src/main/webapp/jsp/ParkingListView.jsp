<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.bean.ParkingBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.ParkingListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<html>
<head>
<title>Parking List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png">
</head>
<body>

	<form action="<%=ORSView.PARKING_LIST_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.ParkingBean"
			scope="request"></jsp:useBean>

		<%
			List<ParkingBean> list = (List<ParkingBean>) request.getAttribute("list");

			HashMap<String, String> parkingStatusMap = (HashMap<String, String>) request
					.getAttribute("parkingStatusMap");

			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int nextListSize = (request.getAttribute("nextListSize") != null)
					? (Integer) request.getAttribute("nextListSize")
					: 0;
		%>

		<div align="center">

			<h1 style="color: navy">Parking List</h1>

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
					<td>Parking Code</td>
					<td><input type="text" name="parkingCode"
						value="<%=DataUtility.getStringData(bean.getParkingCode())%>">
					</td>

					<td>Location</td>
					<td><input type="text" name="location"
						value="<%=DataUtility.getStringData(bean.getLocation())%>">
					</td>

					<td>Status</td>
					<td><%=HTMLUtility.getList("parkingStatus", bean.getParkingStatus(), parkingStatusMap)%></td>

					<td><input type="submit" name="operation"
						value="<%=ParkingListCtl.OP_SEARCH%>"> <input
						type="submit" name="operation"
						value="<%=ParkingListCtl.OP_RESET%>"></td>
				</tr>
			</table>

			<br>

			<!-- LIST TABLE -->

			<table border="1" style="width: 100%; border: groove;">
				<tr style="background-color: #e1e6f1e3;">
					<th width="5%"><input type="checkbox" id="selectall" /></th>
					<th>ID</th>
					<th>Parking Code</th>
					<th>Location</th>
					<th>Slot Number</th>
					<th>Status</th>
					<th>Edit</th>
				</tr>

				<%
					if (list != null && !list.isEmpty()) {
						for (ParkingBean p : list) {
				%>

				<tr align="center">
					<td><input type="checkbox" name="ids" class="case"
						value="<%=p.getId()%>"></td>

					<td><%=p.getId()%></td>
					<td><%=p.getParkingCode()%></td>
					<td><%=p.getLocation()%></td>
					<td><%=p.getSlotNumber()%></td>
					<td><%=p.getParkingStatus()%></td>

					<td><a href="<%=ORSView.PARKING_CTL%>?id=<%=p.getId()%>">
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
						value="<%=ParkingListCtl.OP_PREVIOUS%>" <%if (pageNo == 1) {%>
						disabled <%}%>></td>

					<td align="center"><input type="submit" name="operation"
						value="<%=ParkingListCtl.OP_DELETE%>"> <input
						type="submit" name="operation" value="<%=ParkingListCtl.OP_NEW%>">
					</td>

					<td align="right"><input type="submit" name="operation"
						value="<%=ParkingListCtl.OP_NEXT%>" <%if (nextListSize == 0) {%>
						disabled <%}%>></td>

				</tr>
			</table>

		</div>

	</form>

</body>
</html>