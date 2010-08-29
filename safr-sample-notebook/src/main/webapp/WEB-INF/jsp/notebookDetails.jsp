<%@ include file="top.jsp"%>

<div id="navigation">
    <a href="logout">Logout</a>
    <a href="listNotebooks.htm">Notebooks</a>
    <a href="listPermissions.htm?notebookId=${notebook.id}">Permissions</a>
</div>

<div id="content">
    <p>
    Entries in notebook ${notebook.id} of ${notebook.owner.id}
    <p>
    <table border="1" />
        <tr>
            <th>Entry</th>
            <th>Action</th>
        </tr>
        <c:forEach var="entry" items="${notebook.entries}">
            <tr>
                <td width="80%">${entry.text}</td>
                <td width="20%"><a href="deleteEntry.htm?notebookId=${notebook.id}&entryId=${entry.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
    <p>
    <form:form commandName="entry">
        New entry: 
        <form:input path="text" /> 
        <input type="submit" value="Save" />
    </form:form>
    
</div>

<%@ include file="bottom.jsp" %>
