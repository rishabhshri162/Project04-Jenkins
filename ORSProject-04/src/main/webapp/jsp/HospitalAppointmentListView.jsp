<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.bean.HospitalAppointmentBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.HospitalAppointmentListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<html>
<head>
<title>Hospital Appointment List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png">
</head>
<body>

	<form action="<%=ORSView.HOSPITAL_APPOINTMENT_LIST_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean"
			class="in.co.rays.proj4.bean.HospitalAppointmentBean" scope="request"></jsp:useBean>

		<%
			List<HospitalAppointmentBean> list = (List<HospitalAppointmentBean>) request.getAttribute("list");

			HashMap<String, String> statusMap = (HashMap<String, String>) request.getAttribute("statusMap");

			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int nextListSize = (request.getAttribute("nextListSize") != null)
					? (Integer) request.getAttribute("nextListSize")
					: 0;
		%>

		<div align="center">

			<h1 style="color: navy">Hospital Appointment List</h1>

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

					<td>Patient Name</td>
					<td><input type="text" name="patientName"
						value="<%=DataUtility.getStringData(bean.getPatientName())%>">
					</td>

					<td>Doctor Name</td>
					<td><input type="text" name="doctorName"
						value="<%=DataUtility.getStringData(bean.getDoctorName())%>">
					</td>

					<td>Status</td>
					<td><%=HTMLUtility.getList("status", bean.getStatus(), statusMap)%></td>

					<td><input type="submit" name="operation"
						value="<%=HospitalAppointmentListCtl.OP_SEARCH%>"> <input
						type="submit" name="operation"
						value="<%=HospitalAppointmentListCtl.OP_RESET%>"></td>

				</tr>
			</table>

			<br>

			<!-- LIST TABLE -->

			<table border="1" style="width: 100%; border: groove;">
				<tr style="background-color: #e1e6f1e3;">
					<th width="5%"><input type="checkbox" id="selectall"></th>
					<th>ID</th>
					<th>Patient Name</th>
					<th>Doctor Name</th>
					<th>Date</th>
					<th>Time</th>
					<th>Fee</th>
					<th>Status</th>
					<th>Edit</th>
				</tr>

				<%
					if (list != null && !list.isEmpty()) {
						for (HospitalAppointmentBean b : list) {
				%>

				<tr align="center">

					<td><input type="checkbox" name="ids" value="<%=b.getId()%>">
					</td>

					<td><%=b.getId()%></td>
					<td><%=b.getPatientName()%></td>
					<td><%=b.getDoctorName()%></td>
					<td><%=DataUtility.getDateString(b.getAppointmentDate())%></td>
					<td><%=b.getAppointmentTime()%></td>
					<td><%=b.getConsultationFee()%></td>
					<td><%=b.getStatus()%></td>

					<td><a
						href="<%=ORSView.HOSPITAL_APPOINTMENT_CTL%>?id=<%=b.getId()%>">
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
						value="<%=HospitalAppointmentListCtl.OP_PREVIOUS%>"
						<%if (pageNo == 1) {%> disabled <%}%>></td>

					<td align="center"><input type="submit" name="operation"
						value="<%=HospitalAppointmentListCtl.OP_DELETE%>"> <input
						type="submit" name="operation"
						value="<%=HospitalAppointmentListCtl.OP_NEW%>"></td>

					<td align="right"><input type="submit" name="operation"
						value="<%=HospitalAppointmentListCtl.OP_NEXT%>"
						<%if (nextListSize == 0) {%> disabled <%}%>></td>

				</tr>
			</table>

		</div>

	</form>

</body>
</html>