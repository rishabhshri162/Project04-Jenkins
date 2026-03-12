<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.PetAdoptionCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<html>
<head>
<title>Add Pet</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

</head>

<body>

	<form action="<%=ORSView.PET_ADOPTION_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.PetAdoptionBean"
			scope="request"></jsp:useBean>

		<%
			HashMap<String, String> statusMap = (HashMap<String, String>) request.getAttribute("statusMap");
		%>

		<div align="center">

			<h1 style="color: navy">

				<%
					if (bean != null && bean.getId() > 0) {
				%>

				Update Pet

				<%
					} else {
				%>

				Add Pet

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

					<th align="left">Pet Name</th>

					<td><input type="text" name="petName"
						placeholder="Enter Pet Name"
						value="<%=DataUtility.getStringData(bean.getPetName())%>">

					</td>

					<td><font color="red"> <%=ServletUtility.getErrorMessage("petName", request)%>

					</font></td>

				</tr>



				<tr>

					<th align="left">Animal Type</th>

					<td><input type="text" name="animalType"
						placeholder="Enter Animal Type"
						value="<%=DataUtility.getStringData(bean.getAnimalType())%>">

					</td>

					<td><font color="red"> <%=ServletUtility.getErrorMessage("animalType", request)%>

					</font></td>

				</tr>



				<tr>

					<th align="left">Age</th>

					<td><input type="text" name="age" placeholder="Enter Age"
						value="<%=DataUtility.getStringData(bean.getAge())%>"></td>

					<td><font color="red"> <%=ServletUtility.getErrorMessage("age", request)%>

					</font></td>

				</tr>



				<tr>

					<th align="left">Adoption Status</th>

					<td><%=HTMLUtility.getList("adoptionStatus", bean.getAdoptionStatus(), statusMap)%></td>

					<td><font color="red"> <%=ServletUtility.getErrorMessage("adoptionStatus", request)%>

					</font></td>

				</tr>



				<tr>

					<th></th>

					<%
						if (bean != null && bean.getId() > 0) {
					%>

					<td><input type="submit" name="operation"
						value="<%=PetAdoptionCtl.OP_UPDATE%>"> <input
						type="submit" name="operation"
						value="<%=PetAdoptionCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td><input type="submit" name="operation"
						value="<%=PetAdoptionCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=PetAdoptionCtl.OP_RESET%>"></td>

					<%
						}
					%>

				</tr>

			</table>

		</div>

	</form>

</body>

</html>