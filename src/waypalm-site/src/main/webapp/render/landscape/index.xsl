<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;"><!ENTITY copy "&#169;"><!ENTITY mdash "&#8212;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:r="com.elurm.gsite.web.mvc.support.XsltViewResources"
                extension-element-prefixes="r">

    <xsl:include href="shared/layout-single.xsl"/>

    <xsl:template match="/Welcome" mode="styles">
    </xsl:template>

    <xsl:template match="/Welcome" mode="scripts"/>

    <xsl:template match="/Welcome">
    </xsl:template>

</xsl:stylesheet>