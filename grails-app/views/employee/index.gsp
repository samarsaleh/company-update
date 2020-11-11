<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'employee.label', default: 'Employee')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-employee" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            %{--    <g:textField id="txtSearch" name="txtSearch" class="input-xxlarge" value="q"  placeholder="Search" /> --}%%{--value="${employee.firstname}"--}%%{--
                <button  id="btn"  onclick="getInputValue()" >Search</button>
--}%
  %{--              <script>
                    function getInputValue(){
                        var inputVal = document.getElementById("txtSearch").value;

                        $(document).ready(function(){
                            // alert("ok");
                            $("#btn").on('click',function (e){
                                // alert("inside click");
                                var URL="${createLink(controller:'employee',action:'index')}"
                                // alert(URL)
                                $.ajax({
                                    url:URL,
                                    type: "post",
                                    data:inputVal
                                    })
                            })
                            e.preventDefault();
                        })
                    }
                </script>--}%
                <g:form controller="employee" action="index" >
                    <input type="search" id="site-search" name="txtSearch"
                           aria-label="Search through site content">
                    <button type="submit">Search</button>
                </g:form>
                </ul>
        </div>
        <div id="list-employee" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <f:table collection="${employeeList}" />

            <div class="pagination">
                <g:paginate total="${employeeCount ?: 0}" />
            </div>
        </div>
    </body>
</html>