<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.LanguageCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<html>
<head>
<title>Add Language</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

	<form action="<%=ORSView.LANGUAGE_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean"
			class="in.co.rays.proj4.bean.LanguageBean"
			scope="request"></jsp:useBean>

		<%
			HashMap<String, String> statusMap =
				(HashMap<String, String>) request.getAttribute("statusMap");
		%>
			<%
			HashMap<String, String> directionMap =
				(HashMap<String, String>) request.getAttribute("directionMap");
		%>

		<div align="center">

			<h1 style="color: navy">
				<%
					if (bean != null && bean.getId() > 0) {
				%>
				Update Language
				<%
					} else {
				%>
				Add Language
				<%
					}
				%>
			</h1>

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

			<input type="hidden" name="id"
				value="<%=bean.getId()%>">

			<input type="hidden" name="createdBy"
				value="<%=bean.getCreatedBy()%>">

			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>">

			<input type="hidden" name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">

			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table>

				<tr>
					<th align="left">Language Code</th>
					<td>
						<input type="text" name="languageCode"
							placeholder="Enter Language Code"
							value="<%=DataUtility.getStringData(bean.getLanguageCode())%>">
					</td>
					<td>
						<font color="red">
							<%=ServletUtility.getErrorMessage("languageCode", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Language Name</th>
					<td>
						<input type="text" name="languageName"
							placeholder="Enter Language Name"
							value="<%=DataUtility.getStringData(bean.getLanguageName())%>">
					</td>
					<td>
						<font color="red">
							<%=ServletUtility.getErrorMessage("languageName", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Direction</th>
					<td>
						<%=HTMLUtility.getList("direction",
								bean.getDirection(),
								directionMap)%>
					</td>
					<td>
						<font color="red">
							<%=ServletUtility.getErrorMessage("direction", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Language Status</th>
					<td>
						<%=HTMLUtility.getList("languageStatus",
								bean.getLanguageStatus(),
								statusMap)%>
					</td>
					<td>
						<font color="red">
							<%=ServletUtility.getErrorMessage("languageStatus", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th></th>

					<%
						if (bean != null && bean.getId() > 0) {
					%>

					<td>
						<input type="submit" name="operation"
							value="<%=LanguageCtl.OP_UPDATE%>">

						<input type="submit" name="operation"
							value="<%=LanguageCtl.OP_CANCEL%>">
					</td>

					<%
						} else {
					%>

					<td>
						<input type="submit" name="operation"
							value="<%=LanguageCtl.OP_SAVE%>">

						<input type="submit" name="operation"
							value="<%=LanguageCtl.OP_RESET%>">
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