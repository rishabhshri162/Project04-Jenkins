<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.ClaimCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<html>
<head>
<title>Add Claim</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

	<form action="<%=ORSView.CLAIM_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.ClaimBean"
			scope="request"></jsp:useBean>

		<%
			HashMap<String, String> statusMap = (HashMap<String, String>) request.getAttribute("statusMap");
		%>

		<div align="center">

			<h1 style="color: navy">
				<%
					if (bean != null && bean.getId() > 0) {
				%>
				Update Claim
				<%
					} else {
				%>
				Add Claim
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
					<th align="left">Claim Number</th>
					<td><input type="text" name="claimNumber"
						placeholder="Enter Claim Number"
						value="<%=DataUtility.getStringData(bean.getClaimNumber())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("claimNumber", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Claim Amount</th>
					<td><input type="text" name="claimAmount"
						placeholder="Enter Claim Amount"
						value="<%=DataUtility.getStringData(bean.getClaimAmount())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("claimAmount", request)%>
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
						value="<%=ClaimCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=ClaimCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td><input type="submit" name="operation"
						value="<%=ClaimCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=ClaimCtl.OP_RESET%>"></td>

					<%
						}
					%>

				</tr>

			</table>

		</div>
	</form>
</body>
</html>