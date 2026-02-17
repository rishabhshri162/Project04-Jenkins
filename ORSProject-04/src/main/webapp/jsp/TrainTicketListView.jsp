<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.bean.TrainTicketBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.TrainTicketListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<html>
<head>
<title>Train Ticket List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png">
</head>
<body>

	<form action="<%=ORSView.TRAIN_TICKET_LIST_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.TrainTicketBean"
			scope="request"></jsp:useBean>

		<%
			List<TrainTicketBean> list = (List<TrainTicketBean>) request.getAttribute("list");

			HashMap<String, String> classMap = (HashMap<String, String>) request.getAttribute("classMap");

			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int nextListSize = (request.getAttribute("nextListSize") != null)
					? (Integer) request.getAttribute("nextListSize")
					: 0;
		%>

		<div align="center">

			<h1 style="color: navy">Train Ticket List</h1>

			<h3>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</h3>

			<h3>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</h3>

			<!-- Search Section -->

			<table>
				<tr>

					<td>Passenger Name</td>
					<td><input type="text" name="passengerName"
						value="<%=DataUtility.getStringData(bean.getPassengerName())%>">
					</td>

					<td>Train Number</td>
					<td><input type="text" name="trainNumber"
						value="<%=DataUtility.getStringData(bean.getTrainNumber())%>">
					</td>

					<td>Class</td>
					<td><%=HTMLUtility.getList("ticketClass", bean.getTicketClass(), classMap)%></td>

					<td><input type="submit" name="operation"
						value="<%=TrainTicketListCtl.OP_SEARCH%>"> <input
						type="submit" name="operation"
						value="<%=TrainTicketListCtl.OP_RESET%>"></td>

				</tr>
			</table>

			<br>

			<!-- List Table -->

			<table border="1" style="width: 100%; border: groove;">
				<tr style="background-color: #e1e6f1e3;">
					<th>Select</th>
					<th>ID</th>
					<th>Passenger</th>
					<th>Train No</th>
					<th>Train Name</th>
					<th>Source</th>
					<th>Destination</th>
					<th>Journey Date</th>
					<th>Seat</th>
					<th>Class</th>
					<th>Edit</th>
				</tr>

				<%
					if (list != null && !list.isEmpty()) {
						for (TrainTicketBean t : list) {
				%>

				<tr align="center">
					<td><input type="checkbox" name="ids" value="<%=t.getId()%>"></td>

					<td><%=t.getId()%></td>
					<td><%=t.getPassengerName()%></td>
					<td><%=t.getTrainNumber()%></td>
					<td><%=t.getTrainName()%></td>
					<td><%=t.getSourceStation()%></td>
					<td><%=t.getDestinationStation()%></td>
					<td><%=t.getJourneyDate()%></td>
					<td><%=t.getSeatNumber()%></td>
					<td><%=t.getTicketClass()%></td>

					<td><a href="<%=ORSView.TRAIN_TICKET_CTL%>?id=<%=t.getId()%>">
							Edit </a></td>
				</tr>

				<%
					}
					}
				%>

			</table>

			<br> <input type="hidden" name="pageNo" value="<%=pageNo%>">
			<input type="hidden" name="pageSize" value="<%=pageSize%>">

			<table width="90%">
				<tr>

					<td><input type="submit" name="operation"
						value="<%=TrainTicketListCtl.OP_PREVIOUS%>" <%if (pageNo == 1) {%>
						disabled <%}%>></td>

					<td align="center"><input type="submit" name="operation"
						value="<%=TrainTicketListCtl.OP_DELETE%>"> <input
						type="submit" name="operation"
						value="<%=TrainTicketListCtl.OP_NEW%>"></td>

					<td align="right"><input type="submit" name="operation"
						value="<%=TrainTicketListCtl.OP_NEXT%>"
						<%if (nextListSize == 0) {%> disabled <%}%>></td>

				</tr>
			</table>

		</div>
	</form>
</body>
</html>
