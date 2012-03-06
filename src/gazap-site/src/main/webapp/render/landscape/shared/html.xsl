<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:r="com.elurm.gsite.web.mvc.support.XsltViewResources"
                extension-element-prefixes="r">
    <xsl:output method="html" omit-xml-declaration="yes" encoding="utf-8"
                doctype-system="-//W3C//DTD XHTML 1.0 Strict//EN"
                doctype-public="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"/>

    <xsl:param name="rs"/>

    <xsl:variable name="cp" select="//modules/cdn/contextPath"/>
    <xsl:variable name="au" select="concat(//modules/cdn/server,$cp)"/>
    <xsl:variable name="stylesRoot" select="concat($cp,'/static/landscape/css')"/>
    <xsl:variable name="imagesRoot" select="concat($cp,'/static/landscape/img')"/>
    <xsl:variable name="scriptsRoot" select="concat($cp,'/static/landscape/scripts')"/>
    <xsl:variable name="libRoot" select="concat($cp,'/static/landscape/lib')"/>
    <xsl:variable name="content" select="child::node()"/>

    <xsl:template name="html-head">
        <xsl:param name="styles"/>
        <xsl:param name="scripts"/>

        <xsl:copy-of select="$styles"/>
        <xsl:apply-templates select="$content" mode="styles"/>

        <script type="text/javascript">V={};</script>
        <script type="text/javascript">
            <xsl:text>E={"SCE":'</xsl:text>
            <xsl:value-of select="r:t($rs,'E.SCE')"/>
            <xsl:text>'};</xsl:text>
        </script>
        <xsl:copy-of select="$scripts"/>
        <xsl:apply-templates select="$content" mode="scripts"/>

        <xsl:variable name="meta" select="/*/modules/viewMeta"/>
        <xsl:if test="string-length($meta/titleKey)>0">
            <title>
                <xsl:value-of select="r:t($rs, $meta/titleKey)"/>
            </title>
        </xsl:if>
        <xsl:if test="string-length($meta/title)>0">
            <title>
                <xsl:value-of select="r:t($rs, $meta/title)"/>
            </title>
        </xsl:if>
    </xsl:template>
</xsl:stylesheet>