<%--@elvariable id="themeRoot" type="java.lang.String"--%>
<%--@elvariable id="cp" type="java.lang.String"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lf" uri="http://gazap/jstl/local" %>
<%@ taglib prefix="lt" uri="http://gazap/jstl/local-tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<c:import url="../shared/html.jsp"/>
<html>
<c:import url="../shared/html-head.jsp"/>
<body class="brand-with-menu">
<div id="leftPane">
    <c:import url="../shared/html-bars-brand.jsp"/>
    <c:import url="../shared/html-bars-account.jsp"/>
</div>
<div id="mainColumn">
    <div class="form-container">
        <h1>
            <lt:t key="MapCreatePage.h"/>
        </h1>

        <form id="createMapForm" method="post" action="${cp}/map/create" data-ax-action="${cp}/map/create.ajax"
              class="non-modal width-medium form-horizontal">
            <div class="control-group mandatory">
                <label class="control-label" for="mapTitle"><lt:t key="MapCreateForm.title.label"/></label>

                <div class="controls">
                    <input id="mapTitle" type="text" name="title" size="64"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="mapAlias"><lt:t key="MapCreateForm.alias.label"/></label>

                <div class="controls">
                    <input id="mapAlias" type="text" name="alias" size="64"/>
                </div>
            </div>
            <div class="control-group mandatory">
                <label class="control-label" for="geometryClass"><lt:t key="MapCreateForm.geometryClass.label"/></label>

                <div class="controls">
                    <label class="radio" style="float:left;padding: 0;margin-left: 50px;cursor: pointer;">
                        <div>
                            <input id="geometryClassPlain" type="radio" value="plain" name="geometryClass"/>
                            <span style="padding-left: 10px;">
                                <lt:t key="MapCreateForm.geometryClass.plain.label"/></span>
                        </div>
                        <img src="${themeRoot}/img/sprites/geometry-plain-preview.png"
                             style="display: block;width:100px;height:100px;margin-top:10px;"/>
                    </label>
                    <label class="radio" style="float:left;padding: 0;margin-left: 50px;cursor: pointer;">
                        <div>
                            <input id="geometryClassGeoid" type="radio" value="geoid" name="geometryClass"/>
                            <span style="padding-left: 10px;">
                                <lt:t key="MapCreateForm.geometryClass.geoid.label"/></span>
                        </div>
                        <img src="${themeRoot}/img/sprites/geometry-geoid-preview.png"
                             style="display: block;width:100px;height:100px;margin-top:10px;"/>
                    </label>
                </div>
            </div>
            <div class="control-group sub-group mandatory hidden" data-visible-for-input="geometryClassGeoid">
                <label class="control-label" for="geoidRadiusZ"><lt:t
                        key="MapCreateForm.geoidRadiusZ.label"/></label>

                <div class="controls">
                    <input id="geoidRadiusZ" type="text" name="geoidRadiusZ"/>
                </div>
            </div>
            <div class="control-group sub-group mandatory hidden" data-visible-for-input="geometryClassGeoid">
                <label class="control-label" for="geoidRadiusX"><lt:t
                        key="MapCreateForm.geoidRadiusX.label"/></label>

                <div class="controls">
                    <input id="geoidRadiusX" type="text" name="geoidRadiusX"/>
                </div>
            </div>
            <div class="control-group sub-group mandatory hidden" data-visible-for-input="geometryClassGeoid">
                <label class="control-label" for="geoidRadiusY"><lt:t
                        key="MapCreateForm.geoidRadiusY.label"/></label>

                <div class="controls">
                    <input id="geoidRadiusY" type="text" name="geoidRadiusY"/>
                </div>
            </div>
            <div class="control-group submit-group form-actions">
                <button class="btn" type="submit">
                    <lt:t key="MapCreateForm.submit"/>
                </button>
                <a class="submit-option modal-closer">
                    <lt:t key="MapCreateForm.cancel"/>
                </a>
            </div>
            <div class="alert alert-message alert-progress hidden">
                <lt:t key="MapCreateForm.submitting"/>
            </div>
            <div class="alert alert-error hidden"/>
        </form>
        <script type="text/javascript">BUS.map.modal_create_dialog.init('#createMapForm')</script>
    </div>
</div>
</body>