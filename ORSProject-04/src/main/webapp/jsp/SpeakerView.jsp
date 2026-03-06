<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.SpeakerCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<html>
<head>
<title>Add Speaker</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

</head>
<body>

	<form action="<%=ORSView.SPEAKER_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.SpeakerBean"
			scope="request"></jsp:useBean>

		<%
			HashMap<String, String> organizationMap = (HashMap<String, String>) request.getAttribute("organizationMap");
		%>

		<div align="center">

			<h1 style="color: navy">

				<%
					if (bean != null && bean.getId() > 0) {
				%>

				Update Speaker

				<%
					} else {
				%>

				Add Speaker

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
					<th align="left">Speaker Name</th>

					<td><input type="text" name="speakerName"
						placeholder="Enter Speaker Name"
						value="<%=DataUtility.getStringData(bean.getSpeakerName())%>">
					</td>

					<td><font color="red"> <%=ServletUtility.getErrorMessage("speakerName", request)%>
					</font></td>

				</tr>

				<tr>

					<th align="left">Topic</th>

					<td><input type="text" name="topic" placeholder="Enter Topic"
						value="<%=DataUtility.getStringData(bean.getTopic())%>"></td>

					<td><font color="red"> <%=ServletUtility.getErrorMessage("topic", request)%>
					</font></td>

				</tr>

				<tr>

					<th align="left">Organization</th>

					<td><%=HTMLUtility.getList("organization", bean.getOrganization(), organizationMap)%></td>

					<td><font color="red"> <%=ServletUtility.getErrorMessage("organization", request)%>
					</font></td>

				</tr>

				<tr>

					<th></th>

					<%
						if (bean != null && bean.getId() > 0) {
					%>

					<td><input type="submit" name="operation"
						value="<%=SpeakerCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=SpeakerCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td><input type="submit" name="operation"
						value="<%=SpeakerCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=SpeakerCtl.OP_RESET%>"></td>

					<%
						}
					%>

				</tr>

			</table>

		</div>

	</form>

</body>
</html>