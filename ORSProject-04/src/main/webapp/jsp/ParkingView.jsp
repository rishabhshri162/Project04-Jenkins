<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.ParkingCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<html>
<head>
<title>Add Parking</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

	<form action="<%=ORSView.PARKING_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.ParkingBean"
			scope="request"></jsp:useBean>

		<%
			HashMap<String, String> parkingStatusMap = (HashMap<String, String>) request
					.getAttribute("parkingStatusMap");
		%>

		<div align="center">

			<h1 style="color: navy">
				<%
					if (bean != null && bean.getId() > 0) {
				%>
				Update Parking
				<%
					} else {
				%>
				Add Parking
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
					<th align="left">Parking Code</th>
					<td><input type="text" name="parkingCode"
						placeholder="Enter Parking Code"
						value="<%=DataUtility.getStringData(bean.getParkingCode())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("parkingCode", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Location</th>
					<td><input type="text" name="location"
						placeholder="Enter Location"
						value="<%=DataUtility.getStringData(bean.getLocation())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("location", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Slot Number</th>
					<td><input type="number" name="slotNumber"
						placeholder="Enter Slot Number"
						value="<%=DataUtility.getStringData(bean.getSlotNumber())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("slotNumber", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Parking Status</th>
					<td><%=HTMLUtility.getList("parkingStatus", bean.getParkingStatus(), parkingStatusMap)%></td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("parkingStatus", request)%>
					</font></td>
				</tr>

				<tr>
					<th></th>

					<%
						if (bean != null && bean.getId() > 0) {
					%>

					<td><input type="submit" name="operation"
						value="<%=ParkingCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=ParkingCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td><input type="submit" name="operation"
						value="<%=ParkingCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=ParkingCtl.OP_RESET%>"></td>

					<%
						}
					%>

				</tr>

			</table>

		</div>
	</form>
</body>
</html>