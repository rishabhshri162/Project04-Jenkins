<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.TrainTicketCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<html>
<head>
<title>Add Train Ticket</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

	<form action="<%=ORSView.TRAIN_TICKET_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.TrainTicketBean"
			scope="request"></jsp:useBean>

		<%
			HashMap<String, String> classMap = (HashMap<String, String>) request.getAttribute("classMap");
		%>

		<div align="center">

			<h1 style="color: navy">
				<%
					if (bean != null && bean.getId() > 0) {
				%>
				Update Train Ticket
				<%
					} else {
				%>
				Add Train Ticket
				<%
					}
				%>
			</h1>

			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
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
					<th align="left">Passenger Name</th>
					<td><input type="text" name="passengerName"
						placeholder="Enter Passenger Name"
						value="<%=DataUtility.getStringData(bean.getPassengerName())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("passengerName", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Train Number</th>
					<td><input type="text" name="trainNumber"
						placeholder="Enter Train Number"
						value="<%=DataUtility.getStringData(bean.getTrainNumber())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("trainNumber", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Train Name</th>
					<td><input type="text" name="trainName"
						placeholder="Enter Train Name"
						value="<%=DataUtility.getStringData(bean.getTrainName())%>">
					</td>

					<td><font color="red"> <%=ServletUtility.getErrorMessage("trainName", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Source Station</th>
					<td><input type="text" name="sourceStation"
						placeholder="Enter Source Station"
						value="<%=DataUtility.getStringData(bean.getSourceStation())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("sourceStation", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Destination Station</th>
					<td><input type="text" name="destinationStation"
						placeholder="Enter Destination Station"
						value="<%=DataUtility.getStringData(bean.getDestinationStation())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("destinationStation", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Journey Date</th>
					<td><input type="text" id="udate" name="journeyDate"
						placeholder="Enter Journey Date"
						value="<%=DataUtility.getStringData(bean.getJourneyDate())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("journeyDate", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Seat Number</th>
					<td><input type="text" name="seatNumber"
						placeholder="Enter Seat Number"
						value="<%=DataUtility.getStringData(bean.getSeatNumber())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("seatNumber", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Ticket Class</th>
					<td><%=HTMLUtility.getList("ticketClass", bean.getTicketClass(), classMap)%></td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("ticketClass", request)%>
					</font></td>
				</tr>
				<tr>
					<th></th>

					<%
						if (bean != null && bean.getId() > 0) {
					%>

					<td><input type="submit" name="operation"
						value="<%=TrainTicketCtl.OP_UPDATE%>"> <input
						type="submit" name="operation"
						value="<%=TrainTicketCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td><input type="submit" name="operation"
						value="<%=TrainTicketCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=TrainTicketCtl.OP_RESET%>"></td>

					<%
						}
					%>

				</tr>

			</table>

		</div>
	</form>
</body>
</html>
