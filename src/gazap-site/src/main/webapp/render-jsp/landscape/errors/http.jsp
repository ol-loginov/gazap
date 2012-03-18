<%--@elvariable id="au" type="java.lang.String"--%>
<%--@elvariable id="content" type="gazap.site.web.views.errors.HttpError"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="lf" uri="http://gazap/jstl/local" %>
<%@ taglib prefix="lt" uri="http://gazap/jstl/local-tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set scope="page" var="prefix" value="http.${content.http}"/>
<!DOCTYPE html>
<c:import url="../shared/html.jsp"/>
<html>
<c:import url="../shared/html-head.jsp"/>
<body>
<div id="leftPane">
    <c:import url="../shared/html-bars-brand.jsp"/>
</div>
<div id="mainColumn">
    <div class="error-disclaimer">
        <h1>
            <lt:t key="${prefix}" arg1="${au}"/>
        </h1>

        <h2>
            <lt:t key="${prefix}.h" arg1="${au}"/>
        </h2>

        <p>
            <lt:t key="${prefix}.p" arg1="${au}"/>
        </p>
        <ul>
            <c:forEach items="${content.suggestions}" var="e">
                <li>
                    <lt:t key="${prefix}.suggestions.${e}" arg1="${au}"/>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>
</body>
</html>