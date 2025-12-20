<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.HostelRoomCtl"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Hostel Room</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>
	<form action="<%=ORSView.HOSTEL_ROOM_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.HostelRoomBean"
			scope="request"></jsp:useBean>

		<%
			HashMap<String, String> statusMap = (HashMap<String, String>) request.getAttribute("statusMap");
		%>

		<div align="center">
			<h1 align="center" style="margin-bottom: -15; color: navy">
				<%
					if (bean != null && bean.getId() > 0) {
				%>
				Update
				<%
					} else {
				%>
				Add
				<%
					}
				%>
				Hostel Room
			</h1>

			<div style="height: 15px; margin-bottom: 12px">
				<h3 align="center">
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
					</font>
				</h3>

				<h3 align="center">
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</h3>
			</div>

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
					<th align="left">Room Number<span style="color: red">*</span></th>
					<td><input type="text" name="roomNumber"
						placeholder="Enter Room Number"
						value="<%=DataUtility.getStringData(bean.getRoomNumber())%>">
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("roomNumber", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Room Type<span style="color: red">*</span></th>
					<td><input type="text" name="roomType"
						placeholder="Single / Double / Triple"
						value="<%=DataUtility.getStringData(bean.getRoomType())%>">
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("roomType", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Capacity<span style="color: red">*</span></th>
					<td><input type="text" name="capacity"
						placeholder="Enter Capacity"
						value="<%=DataUtility.getStringData(bean.getCapacity())%>">
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("capacity", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Rent<span style="color: red">*</span></th>
					<td><input type="text" name="rent" placeholder="Enter Rent"
						value="<%=DataUtility.getStringData(bean.getRent())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("rent", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Status<span style="color: red">*</span></th>
					<td><%=HTMLUtility.getList("status", bean.getStatus(), statusMap)%>
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("status", request)%>
					</font></td>
				</tr>

				<tr>
					<th></th>
					<td></td>
				</tr>

				<tr>
					<th></th>
					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=HostelRoomCtl.OP_UPDATE%>"> <input
						type="submit" name="operation"
						value="<%=HostelRoomCtl.OP_CANCEL%>"></td>
					<%
						} else {
					%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=HostelRoomCtl.OP_SAVE%>"> <input
						type="submit" name="operation" value="<%=HostelRoomCtl.OP_RESET%>">
					</td>
					<%
						}
					%>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>
