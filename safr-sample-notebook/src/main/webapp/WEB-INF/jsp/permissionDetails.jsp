<%@ include file="top.jsp" %>

<div id="navigation">
    <a href="logout">Logout</a>
    <a href="listNotebooks.htm">Notebooks</a>
</div>

<div id="content">
    <p>
    Permission assignment to ${assignment.assigneeId} for 
    notebook <a href="detailNotebook.htm?notebookId=${notebook.id}">${notebook.id}</a> of ${notebook.owner.id}
    <p>
    <form:form commandName="assignment">
      Change permission: 
      <form:select 
        path="actionString" 
        items="${assignment.actionStrings}" 
        multiple="false" /> 
      <input type="submit" value="Save" />
    </form:form>

</div>

<%@ include file="bottom.jsp" %>
