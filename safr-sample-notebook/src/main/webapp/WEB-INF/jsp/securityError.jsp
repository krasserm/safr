<%@ include file="top.jsp" %>

<div id="navigation">
    <a href="logout">Logout</a>
    <a href="listNotebooks.htm">Notebooks</a>
</div>

<div id="content">
    <p>
    Insufficient privileges.<br>
    <p>
    <table border="1"/>
        <tr>
            <td>Required</td>              
            <td>${permission.action}</td>              
        </tr>
        <tr>
            <td>Notebook ID</td>              
            <td>${permission.target.identifier}</td>              
        </tr>
        <tr>
            <td>Owner</td>              
            <td>${permission.target.context}</td>              
        </tr>
    </table>
</div>

<%@ include file="bottom.jsp" %>

