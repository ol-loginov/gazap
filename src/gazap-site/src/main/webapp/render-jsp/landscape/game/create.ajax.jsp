<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="l" uri="http://gazap/jstl/local" %>
<%@ taglib prefix="lt" uri="http://gazap/jstl/local-tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:import url="../shared/html.jsp"/>
<form id="modalGameCreateForm" action="${cp}/game/create" data-ax-action="${au}/game/create.ajax" method="post">
    <h1><lt:t key="ModalGameCreate.title"/></h1>

    <div class="control-group">
        <div class="controls">
            <input type="text" name="title" placeholder="<lt:t key="ModalGameCreate.form.title.placeholder"/>"/>
        </div>
    </div>
    <div class="control-group submit-group">
        <button class="btn btn-info" type="submit"><lt:t key="ModalGameCreate.form.submit"/></button>
        <button class="btn submit-option form-canceller"><lt:t key="ModalGameCreate.form.cancel"/></button>
    </div>
    <div class="alert alert-message alert-progress hidden"><lt:t key="ModalGameCreate.form.submitting"/></div>
    <div class="alert alert-error hidden"/>
    <script type="text/javascript">BUS.game.modal_create_dialog.init('#modalGameCreateForm')</script>
</form>
