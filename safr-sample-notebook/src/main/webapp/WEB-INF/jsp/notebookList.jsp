<%@ include file="top.jsp" %>

<div id="navigation">
    <a href="logout">Logout</a>
</div>

<div id="content">
    <p>
    Notebook List
    <p>
	<table border="1"/>
		<tr>
			<th>Identifier</th>
			<th>Owner</th>
			<th>Action</th>
		</tr>
		<c:forEach var="notebook" items="${notebooks}">
			<tr>
				<td><a href="detailNotebook.htm?notebookId=${notebook.id}">${notebook.id}</a></td>				
				<td>${notebook.owner.id}</td>				
				<td><a href="deleteNotebook.htm?notebookId=${notebook.id}">Delete</a></td>				
			</tr>
		</c:forEach>
	</table>
    <p>
    <form:form commandName="nbid">
        New notebook for current user. Notebook ID*:  
        <form:input path="identifier" /> 
        <input type="submit" value="Save" />
    </form:form>
    <p>
    * If you enter the id of an existing notebook then that notebook will be replaced with the created one (sufficient privileges provided).
</div>

<%@ include file="bottom.jsp" %>
