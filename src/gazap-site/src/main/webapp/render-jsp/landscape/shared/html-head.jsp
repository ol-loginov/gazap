<%--@elvariable id="cp" type="java.lang.String"--%>
<%--@elvariable id="libRoot" type="java.lang.String"--%>
<%--@elvariable id="scriptsRoot" type="java.lang.String"--%>
<%--@elvariable id="content" type="gazap.site.web.views.GazapPage"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="lt" uri="http://gazap/jstl/local-tags" %>
<%@ page pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<head>
    <link rel="stylesheet" href="${libRoot}/fancybox/jquery.fancybox.css"/>
    <script type="text/javascript">
        V = {};
        E = {"SCE":'<fmt:message key="E.SCE"/>'};
    </script>
    <script type="text/javascript" src="${libRoot}/jquery/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="${libRoot}/jquery/jquery.form-2.94.min.js"></script>
    <script type="text/javascript" src="${libRoot}/jquery/jquery.exists.js"></script>
    <script type="text/javascript" src="${libRoot}/fancybox/jquery.fancybox.js"></script>
    <script type="text/javascript" src="${libRoot}/backbone/undescore-1.1.7.min.js"></script>
    <script type="text/javascript" src="${libRoot}/backbone/backbone-0.5.3.min.js"></script>
    <script type="text/javascript" src="${libRoot}/bootstrap/bootstrap-tooltip.js"></script>
    <script type="text/javascript" src="${libRoot}/bootstrap/bootstrap-popover.js"></script>
    <script type="text/javascript" src="${scriptsRoot}/visitor.js"></script>
    <script type="text/javascript" src="${scriptsRoot}/ui.js"></script>
    <script type="text/javascript" src="${scriptsRoot}/events/InitModalLoginDialog.js"></script>
    <script type="text/javascript" src="${scriptsRoot}/events/InitModalRegisterDialog.js"></script>
    <script type="text/javascript" src="${scriptsRoot}/events/InitFastSearch.js"></script>
    <script type="text/javascript" src="${scriptsRoot}/events/ReCaptcha.js"></script>

    <link rel="stylesheet/less" href="${cp}/static/landscape/less/layout.less"/>
    <script type="text/javascript" src="${libRoot}/less/less-1.3.0.min.js"></script>
    <!--
  <link rel="stylesheet" href="${cp}/static/landscape/dist/gazap.css"/>
  -->

    <c:set scope="page" var="viewMeta" value="${content.moduleViewMeta}"/>
    <c:if test="${fn:length(viewMeta.titleKey) gt 0}">
        <title>
            <fmt:message key="${viewMeta.titleKey}"/>
        </title>
    </c:if>
    <c:if test="${fn:length(viewMeta.title) gt 0}">
        <title>
            <c:out value="${viewMeta.title}"/>
        </title>
    </c:if>
</head>