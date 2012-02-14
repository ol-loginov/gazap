<!DOCTYPE xsl:stylesheet [
        <!ENTITY nbsp "&#160;">
        <!ENTITY copy "&#169;">
        <!ENTITY mdash "&#8212;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:r="com.elurm.gsite.web.mvc.support.XsltViewResources"
                extension-element-prefixes="r">

    <xsl:include href="shared/layout-single.xsl"/>

    <xsl:template match="/WelcomePage" mode="styles">
        <link rel="stylesheet/less" href="{$stylesRoot}/layout-single.less"/>
    </xsl:template>

    <xsl:template match="/WelcomePage" mode="scripts"/>

    <xsl:template match="/WelcomePage">
    </xsl:template>

</xsl:stylesheet>