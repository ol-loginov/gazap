<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" omit-xml-declaration="yes" encoding="utf-8"
                doctype-system="-//W3C//DTD XHTML 1.0 Strict//EN"
                doctype-public="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"/>

    <xsl:param name="rs"/>

    <xsl:variable name="cp" select="//modules/cdn/contextPath"/>
    <xsl:variable name="au" select="concat(//modules/cdn/server,$cp)"/>
    <xsl:variable name="stylesRoot" select="concat($cp,'/static/landscape/css')"/>
    <xsl:variable name="imagesRoot" select="concat($cp,'/static/landscape/img')"/>
    <xsl:variable name="scriptsRoot" select="concat($cp,'/static/landscape/scripts')"/>
    <xsl:variable name="content" select="child::node()"/>
</xsl:stylesheet>