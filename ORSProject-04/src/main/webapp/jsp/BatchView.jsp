<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.BatchCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<html>
<head>
<title>Add Batch Module</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

	<form action="<%=ORSView.BATCH_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.BatchBean"
			scope="request"></jsp:useBean>

		<%
			HashMap<String, String> timingMap =
			(HashMap<String, String>) request.getAttribute("timingMap");
		%>

		<div align="center">

			<h1 style="color: navy">
				<%
					if (bean != null && bean.getId() > 0) {
				%>
				Update Batch Module
				<%
					} else {
				%>
				Add Batch Module
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

			<input type="hidden" name="id" value="<%=bean.getId()%>">
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
					<th align="left">Batch Code</th>
					<td>
						<input type="text" name="batchCode"
							placeholder="Enter Batch Code"
							value="<%=DataUtility.getStringData(bean.getBatchCode())%>">
					</td>
					<td>
						<font color="red">
							<%=ServletUtility.getErrorMessage("batchCode", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Batch Name</th>
					<td>
						<input type="text" name="batchName"
							placeholder="Enter Batch Name"
							value="<%=DataUtility.getStringData(bean.getBatchName())%>">
					</td>
					<td>
						<font color="red">
							<%=ServletUtility.getErrorMessage("batchName", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Trainer Name</th>
					<td>
						<input type="text" name="trainerName"
							placeholder="Enter Trainer Name"
							value="<%=DataUtility.getStringData(bean.getTrainerName())%>">
					</td>
					<td>
						<font color="red">
							<%=ServletUtility.getErrorMessage("trainerName", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Batch Timing</th>
					<td>
						<%=HTMLUtility.getList("batchTiming",
								bean.getBatchTiming(), timingMap)%>
					</td>
					<td>
						<font color="red">
							<%=ServletUtility.getErrorMessage("batchTiming", request)%>
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
							value="<%=BatchCtl.OP_UPDATE%>">
						<input type="submit" name="operation"
							value="<%=BatchCtl.OP_CANCEL%>">
					</td>

					<%
						} else {
					%>

					<td>
						<input type="submit" name="operation"
							value="<%=BatchCtl.OP_SAVE%>">
						<input type="submit" name="operation"
							value="<%=BatchCtl.OP_RESET%>">
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