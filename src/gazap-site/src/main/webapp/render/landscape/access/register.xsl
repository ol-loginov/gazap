<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:r="com.elurm.gsite.web.mvc.support.XsltViewResources"
                extension-element-prefixes="r">

    <xsl:include href="../shared/layout-single.xsl"/>

    <xsl:template match="/Register" mode="styles">
        <link rel="stylesheet/less" href="{$stylesRoot}/layout-single.less"/>
    </xsl:template>

    <xsl:template match="/Register" mode="scripts"/>

    <xsl:template match="/Register">
        <form method="POST">
            <div class="control-group">
                <input type="text" placeholder="Email" name="username"/>
            </div>
            <div class="control-group">
                <input type="password" placeholder="Password" name="password"/>
            </div>
            <button class="btn" type="submit">Войти</button>
        </form>
    </xsl:template>

</xsl:stylesheet>