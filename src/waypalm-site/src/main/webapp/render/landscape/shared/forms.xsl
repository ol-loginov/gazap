<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:r="com.elurm.gsite.web.mvc.support.XsltViewResources"
                extension-element-prefixes="r">
    <xsl:template name="form-display-operation-result">
        <xsl:param name="status"/>
        <xsl:param name="context"/>

        <xsl:variable name="done" select="concat(' ', $status/done)"/>

        <div class="form-submitting progress-message hidden">
            <span>
                <xsl:value-of select="r:t($rs, concat($context,'.submitting'))"/>
            </span>
        </div>

        <div class="form-success alert-message success {substring-after('true hidden', $done)}">
            <span>
                <xsl:value-of select="r:t($rs, concat($context,'.done'))"/>
            </span>
        </div>

        <div class="form-error alert-message error {substring-after('false hidden', $done)}">
            <strong>
                <span>
                    <xsl:value-of select="r:t($rs, concat($context,'.failed'))"/>
                </span>
            </strong>
            <ul class="error-list"/>
        </div>
    </xsl:template>


    <xsl:template name="form-display-row">
        <xsl:param name="status"/>
        <xsl:param name="context"/>
        <xsl:param name="field"/>
        <xsl:param name="editor"/>

        <xsl:variable name="fieldError" select="$status/invalidFields/item[field=$field]"/>
        <xsl:variable name="rowClass">
            <xsl:if test="count($fieldError) >0">
                <xsl:text> has-error</xsl:text>
            </xsl:if>
        </xsl:variable>

        <div class="form-row {$rowClass}">
            <xsl:if test="count($fieldError) >0">
                <div class="error-list">
                    <ul>
                        <xsl:for-each select="$fieldError">
                            <li>
                                <xsl:value-of select="./error"/>
                            </li>
                        </xsl:for-each>
                    </ul>
                </div>
            </xsl:if>
            <label>
                <xsl:value-of select="r:t($rs, concat($context,'.',$field,'.label'))"/>
                <span class="traits">
                    <xsl:value-of select="r:t($rs, concat($context,'.',$field,'.traits'))"/>
                </span>
            </label>
            <div class="form-row-editor{$rowClass}">
                <xsl:copy-of select="$editor"/>
            </div>
        </div>
    </xsl:template>

    <xsl:template name="form-display-row-nobr">
        <xsl:param name="status"/>
        <xsl:param name="context"/>
        <xsl:param name="field"/>
        <xsl:param name="editor"/>
        <xsl:param name="labelClass"/>

        <xsl:variable name="fieldError" select="$status/invalidFields/item[field=$field]"/>
        <xsl:variable name="rowClass">
            <xsl:if test="count($fieldError) >0">
                <xsl:text> has-error</xsl:text>
            </xsl:if>
        </xsl:variable>

        <div class="form-row nobr{$rowClass}">
            <xsl:if test="count($fieldError) >0">
                <div class="error-list">
                    <ul>
                        <xsl:for-each select="$fieldError">
                            <li>
                                <xsl:value-of select="./error"/>
                            </li>
                        </xsl:for-each>
                    </ul>
                </div>
            </xsl:if>
            <label class="{$labelClass}">
                <xsl:value-of select="r:t($rs, concat($context,'.',$field,'.label'))"/>
                <span class="traits">
                    <xsl:value-of select="r:t($rs, concat($context,'.',$field,'.traits'))"/>
                </span>
            </label>
            <div class="form-row-editor{$rowClass}">
                <xsl:copy-of select="$editor"/>
            </div>
        </div>
    </xsl:template>

    <xsl:template name="stacked-form-input">
        <xsl:param name="status"/>
        <xsl:param name="context"/>
        <xsl:param name="field"/>
        <xsl:param name="editor"/>

        <xsl:variable name="fieldError" select="$status/invalidFields/item[field=$field]"/>
        <xsl:variable name="containerClass">
            <xsl:text>clearfix</xsl:text>
            <xsl:if test="count($fieldError) >0">
                <xsl:text> error</xsl:text>
            </xsl:if>
        </xsl:variable>

        <div class="{$containerClass}">
            <label for="{$field}">
                <xsl:value-of select="r:t($rs, concat($context,'.',$field,'.label'))"/>
                <span class="traits">
                    <xsl:value-of select="r:t($rs, concat($context,'.',$field,'.traits'))"/>
                </span>
            </label>
            <div class="input">
                <xsl:copy-of select="$editor"/>
            </div>
        </div>
    </xsl:template>
</xsl:stylesheet>