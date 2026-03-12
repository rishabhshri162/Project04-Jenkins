<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.bean.PetAdoptionBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.PetAdoptionListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<html>
<head>

<title>Pet Adoption List</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png">

</head>

<body>

	<form action="<%=ORSView.PET_ADOPTION_LIST_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.PetAdoptionBean"
			scope="request"></jsp:useBean>

		<%
			List<PetAdoptionBean> list = (List<PetAdoptionBean>) request.getAttribute("list");

			HashMap<String, String> statusMap = (HashMap<String, String>) request.getAttribute("statusMap");

			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);

			int nextListSize = (request.getAttribute("nextListSize") != null)
					? (Integer) request.getAttribute("nextListSize")
					: 0;
		%>

		<div align="center">

			<h1 style="color: navy">Pet Adoption List</h1>


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

					<td>Pet Name</td>

					<td><input type="text" name="petName"
						value="<%=DataUtility.getStringData(bean.getPetName())%>">

					</td>


					<td>Animal Type</td>

					<td><input type="text" name="animalType"
						value="<%=DataUtility.getStringData(bean.getAnimalType())%>">

					</td>


					<td>Status</td>

					<td><%=HTMLUtility.getList("adoptionStatus", bean.getAdoptionStatus(), statusMap)%></td>


					<td><input type="submit" name="operation"
						value="<%=PetAdoptionListCtl.OP_SEARCH%>"> <input
						type="submit" name="operation"
						value="<%=PetAdoptionListCtl.OP_RESET%>"></td>

				</tr>

			</table>

			<br>


			<!-- LIST TABLE -->

			<table border="1" style="width: 100%; border: groove;">

				<tr style="background-color: #e1e6f1e3;">

					<th width="5%"><input type="checkbox" id="selectall" /></th>

					<th>ID</th>
					<th>Pet Name</th>
					<th>Animal Type</th>
					<th>Age</th>
					<th>Status</th>
					<th>Edit</th>

				</tr>


				<%
					if (list != null && !list.isEmpty()) {

						for (PetAdoptionBean p : list) {
				%>


				<tr align="center">

					<td><input type="checkbox" name="ids" class="case"
						value="<%=p.getId()%>"></td>

					<td><%=p.getId()%></td>

					<td><%=p.getPetName()%></td>

					<td><%=p.getAnimalType()%></td>

					<td><%=p.getAge()%></td>

					<td><%=p.getAdoptionStatus()%></td>


					<td><a href="<%=ORSView.PET_ADOPTION_CTL%>?id=<%=p.getId()%>">

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
						value="<%=PetAdoptionListCtl.OP_PREVIOUS%>" <%if (pageNo == 1) {%>
						disabled <%}%>></td>


					<td align="center"><input type="submit" name="operation"
						value="<%=PetAdoptionListCtl.OP_DELETE%>"> <input
						type="submit" name="operation"
						value="<%=PetAdoptionListCtl.OP_NEW%>"></td>


					<td align="right"><input type="submit" name="operation"
						value="<%=PetAdoptionListCtl.OP_NEXT%>"
						<%if (nextListSize == 0) {%> disabled <%}%>></td>


				</tr>

			</table>

		</div>

	</form>

</body>

</html>