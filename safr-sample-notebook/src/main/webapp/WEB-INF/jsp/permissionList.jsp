<%@ include file="top.jsp" %>

<div id="navigation">
    <a href="logout">Logout</a>
    <a href="listNotebooks.htm">Notebooks</a>
</div>

<div id="content">
    <p>
    Permissions for notebook <a href="detailNotebook.htm?notebookId=${notebook.id}">${notebook.id}</a> of ${notebook.owner.id}
    <p>
    <table border="1"/>
        <tr>
            <th>Assignee</th>
            <th>Permission</th>
            <th>Action</th>
        </tr>
        <c:forEach var="assignment" items="${assignments}">
            <tr>
                <td>${assignment.assigneeId}</td>              
                <td>${assignment.actionString}</td>
                <td>
                    <c:if test="${assignment.notebookIdWildcard == false}">
                        <a href="detailPermission.htm?notebookId=${notebook.id}&assigneeId=${assignment.assigneeId}">Change</a>              
                    </c:if>              
                    <c:if test="${assignment.notebookIdWildcard == true}">
                        -
                    </c:if>              
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<%@ include file="bottom.jsp" %>

