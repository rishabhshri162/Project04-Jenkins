<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.BoardingPassCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<html>
<head>
<title>Add Boarding Pass</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

	<form action="<%=ORSView.BOARDING_PASS_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.BoardingPassBean"
			scope="request"></jsp:useBean>

		<%
			HashMap<String, String> gateMap = (HashMap<String, String>) request.getAttribute("gateMap");
		%>

		<div align="center">

			<h1 style="color: navy">
				<%
					if (bean != null && bean.getId() > 0) {
				%>
				Update Boarding Pass
				<%
					} else {
				%>
				Add Boarding Pass
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
					<th align="left">Seat Number</th>
					<td><input type="text" name="seatNumber"
						placeholder="Enter Seat Number"
						value="<%=DataUtility.getStringData(bean.getSeatNumber())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("seatNumber", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Gate</th>
					<td><%=HTMLUtility.getList("gate", bean.getGate(), gateMap)%></td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("gate", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Boarding Time</th>
					<td><input type="text" name="boardingTime"
						placeholder="dd-MM-yyyy HH:mm:ss"
						value="<%=DataUtility.getStringData(bean.getBoardingTime())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("boardingTime", request)%>
					</font></td>
				</tr>

				<tr>
					<th></th>

					<%
						if (bean != null && bean.getId() > 0) {
					%>

					<td><input type="submit" name="operation"
						value="<%=BoardingPassCtl.OP_UPDATE%>"> <input
						type="submit" name="operation"
						value="<%=BoardingPassCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td><input type="submit" name="operation"
						value="<%=BoardingPassCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=BoardingPassCtl.OP_RESET%>"></td>

					<%
						}
					%>

				</tr>

			</table>

		</div>
	</form>
</body>
</html>