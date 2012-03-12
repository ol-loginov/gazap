<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<c:import url="shared/html.jsp"/>
<html>
<c:import url="shared/html-head.jsp"/>
<body>
<div id="leftPane">
  <c:import url="shared/html-bars-brand.jsp"/>
  <c:import url="shared/html-bars-account.jsp"/>
</div>
<div id="mainColumn">
  <div id="fastSearch">
    <form id="fastSearchForm" method="post" class="form-search" action="${cp}/search" data-ax-action="${cp}/search">
      <div class="controls">
        <div class="input-append">
          <input type="text" name="query" class="search-query"/>
          <button class="btn static-element" type="submit">искать</button>
          <span class="btn progress-element disabled">ищу ...</span>
        </div>
      </div>
    </form>
    <script type="text/javascript">
      UI.triggerInitFastSearch("#fastSearchForm");
    </script>
  </div>
</div>
</body>
</html>