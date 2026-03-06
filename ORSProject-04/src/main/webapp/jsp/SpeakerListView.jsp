<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.bean.SpeakerBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.SpeakerListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<html>
<head>

<title>Speaker List</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png">

</head>

<body>

	<form action="<%=ORSView.SPEAKER_LIST_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.SpeakerBean"
			scope="request"></jsp:useBean>

		<%
			List<SpeakerBean> list = (List<SpeakerBean>) request.getAttribute("list");

			HashMap<String, String> organizationMap = (HashMap<String, String>) request.getAttribute("organizationMap");

			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);

			int nextListSize = (request.getAttribute("nextListSize") != null)
					? (Integer) request.getAttribute("nextListSize")
					: 0;
		%>

		<div align="center">

			<h1 style="color: navy">Speaker List</h1>

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

					<td>Speaker Name</td>

					<td><input type="text" name="speakerName"
						value="<%=DataUtility.getStringData(bean.getSpeakerName())%>">
					</td>

					<td>Organization</td>

					<td><%=HTMLUtility.getList("organization", bean.getOrganization(), organizationMap)%></td>

					<td><input type="submit" name="operation"
						value="<%=SpeakerListCtl.OP_SEARCH%>"> <input
						type="submit" name="operation"
						value="<%=SpeakerListCtl.OP_RESET%>"></td>

				</tr>

			</table>

			<br>

			<!-- LIST TABLE -->

			<table border="1" style="width: 100%; border: groove;">

				<tr style="background-color: #e1e6f1e3;">

					<th width="5%"><input type="checkbox" id="selectall" /></th>

					<th>ID</th>
					<th>Speaker Name</th>
					<th>Topic</th>
					<th>Organization</th>
					<th>Edit</th>

				</tr>

				<%
					if (list != null && !list.isEmpty()) {

						for (SpeakerBean b : list) {
				%>

				<tr align="center">

					<td><input type="checkbox" name="ids" class="case"
						value="<%=b.getId()%>"></td>

					<td><%=b.getId()%></td>
					<td><%=b.getSpeakerName()%></td>
					<td><%=b.getTopic()%></td>
					<td><%=b.getOrganization()%></td>

					<td><a href="<%=ORSView.SPEAKER_CTL%>?id=<%=b.getId()%>">
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
						value="<%=SpeakerListCtl.OP_PREVIOUS%>" <%if (pageNo == 1) {%>
						disabled <%}%>></td>

					<td align="center"><input type="submit" name="operation"
						value="<%=SpeakerListCtl.OP_DELETE%>"> <input
						type="submit" name="operation" value="<%=SpeakerListCtl.OP_NEW%>">

					</td>

					<td align="right"><input type="submit" name="operation"
						value="<%=SpeakerListCtl.OP_NEXT%>" <%if (nextListSize == 0) {%>
						disabled <%}%>></td>

				</tr>

			</table>

		</div>

	</form>

</body>
</html>