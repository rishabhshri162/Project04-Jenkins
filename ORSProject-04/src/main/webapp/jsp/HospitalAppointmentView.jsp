<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.HospitalAppointmentCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<html>
<head>
<title>Hospital Appointment Module</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

	<form action="<%=ORSView.HOSPITAL_APPOINTMENT_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean"
			class="in.co.rays.proj4.bean.HospitalAppointmentBean" scope="request"></jsp:useBean>

		<%
			HashMap<String, String> statusMap = (HashMap<String, String>) request.getAttribute("statusMap");
		%>

		<div align="center">

			<h1 style="color: navy">
				<%
					if (bean != null && bean.getId() > 0) {
				%>
				Update Hospital Appointment
				<%
					} else {
				%>
				Add Hospital Appointment
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
					<th align="left">Patient Name</th>
					<td><input type="text" name="patientName"
						placeholder="Enter Patient Name"
						value="<%=DataUtility.getStringData(bean.getPatientName())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("patientName", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Doctor Name</th>
					<td><input type="text" name="doctorName"
						placeholder="Enter Doctor Name"
						value="<%=DataUtility.getStringData(bean.getDoctorName())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("doctorName", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Appointment Date</th>
					<td><input type="date" name="appointmentDate"
						value="<%=DataUtility.getDateString(bean.getAppointmentDate())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("appointmentDate", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Appointment Time</th>
					<td><input type="text" name="appointmentTime"
						placeholder="e.g. 10:30 AM"
						value="<%=DataUtility.getStringData(bean.getAppointmentTime())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("appointmentTime", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Symptoms</th>
					<td><input type="text" name="symptoms"
						placeholder="Enter Symptoms"
						value="<%=DataUtility.getStringData(bean.getSymptoms())%>">
					</td>
				</tr>

				<tr>
					<th align="left">Consultation Fee</th>
					<td><input type="text" name="consultationFee"
						placeholder="Enter Fee"
						value="<%=DataUtility.getStringData(bean.getConsultationFee())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("consultationFee", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Status</th>
					<td><%=HTMLUtility.getList("status", bean.getStatus(), statusMap)%></td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("status", request)%>
					</font></td>
				</tr>

				<tr>
					<th></th>

					<%
						if (bean != null && bean.getId() > 0) {
					%>

					<td><input type="submit" name="operation"
						value="<%=HospitalAppointmentCtl.OP_UPDATE%>"> <input
						type="submit" name="operation"
						value="<%=HospitalAppointmentCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td><input type="submit" name="operation"
						value="<%=HospitalAppointmentCtl.OP_SAVE%>"> <input
						type="submit" name="operation"
						value="<%=HospitalAppointmentCtl.OP_RESET%>"></td>

					<%
						}
					%>

				</tr>

			</table>

		</div>

	</form>
</body>
</html>