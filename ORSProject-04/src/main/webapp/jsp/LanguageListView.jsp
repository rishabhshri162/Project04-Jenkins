<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.bean.LanguageBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.LanguageListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<html>
<head>
<title>Language List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png">
</head>
<body>

	<form action="<%=ORSView.LANGUAGE_LIST_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean"
			class="in.co.rays.proj4.bean.LanguageBean"
			scope="request"></jsp:useBean>

		<%
			List<LanguageBean> list =
				(List<LanguageBean>) request.getAttribute("list");

			HashMap<String, String> statusMap =
				(HashMap<String, String>) request.getAttribute("statusMap");

			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int nextListSize = (request.getAttribute("nextListSize") != null)
					? (Integer) request.getAttribute("nextListSize")
					: 0;
		%>

		<div align="center">

			<h1 style="color: navy">Language List</h1>

			<h3>
				<font color="red">
					<%=ServletUtility.getErrorMessage(request)%>
				</font>
			</h3>

			<h3>
				<font color="green">
					<%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</h3>


			<table>
				<tr>

					<td>Language Code</td>
					<td>
						<input type="text" name="languageCode"
							value="<%=DataUtility.getStringData(bean.getLanguageCode())%>">
					</td>

					<td>Language Name</td>
					<td>
						<input type="text" name="languageName"
							value="<%=DataUtility.getStringData(bean.getLanguageName())%>">
					</td>

					<td>Status</td>
					<td>
						<%=HTMLUtility.getList("languageStatus",
								bean.getLanguageStatus(),
								statusMap)%>
					</td>

					<td>
						<input type="submit" name="operation"
							value="<%=LanguageListCtl.OP_SEARCH%>">
						<input type="submit" name="operation"
							value="<%=LanguageListCtl.OP_RESET%>">
					</td>

				</tr>
			</table>

			<br>


			<table border="1" style="width: 100%; border: groove;">
				<tr style="background-color: #e1e6f1e3;">
					<th>Select</th>
					<th>ID</th>
					<th>Language Code</th>
					<th>Language Name</th>
					<th>Direction</th>
					<th>Status</th>
					<th>Edit</th>
				</tr>

				<%
					if (list != null && !list.isEmpty()) {

						for (LanguageBean l : list) {
				%>

				<tr align="center">

					<td>
						<input type="checkbox" name="ids"
							value="<%=l.getId()%>">
					</td>

					<td><%=l.getId()%></td>
					<td><%=l.getLanguageCode()%></td>
					<td><%=l.getLanguageName()%></td>
					<td><%=l.getDirection()%></td>
					<td><%=l.getLanguageStatus()%></td>

					<td>
						<a href="<%=ORSView.LANGUAGE_CTL%>?id=<%=l.getId()%>">
							Edit
						</a>
					</td>

				</tr>

				<%
					}
					}
				%>

			</table>

			<br>

			<input type="hidden" name="pageNo"
				value="<%=pageNo%>">

			<input type="hidden" name="pageSize"
				value="<%=pageSize%>">


			<table width="90%">
				<tr>

					<td>
						<input type="submit" name="operation"
							value="<%=LanguageListCtl.OP_PREVIOUS%>"
							<% if (pageNo == 1) { %> disabled <% } %>>
					</td>

					<td align="center">
						<input type="submit" name="operation"
							value="<%=LanguageListCtl.OP_DELETE%>">

						<input type="submit" name="operation"
							value="<%=LanguageListCtl.OP_NEW%>">
					</td>

					<td align="right">
						<input type="submit" name="operation"
							value="<%=LanguageListCtl.OP_NEXT%>"
							<% if (nextListSize == 0) { %> disabled <% } %>>
					</td>

				</tr>
			</table>

		</div>
	</form>
</body>
</html>