<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:r="com.elurm.gsite.web.mvc.support.XsltViewResources"
                extension-element-prefixes="r">

    <xsl:include href="return-html.xsl"/>
    <xsl:include href="_layout-bands.xsl"/>

    <xsl:template match="/">
        <html>
            <head>
                <xsl:call-template name="html-head">
                    <xsl:with-param name="styles">
                        <link rel="stylesheet/less" href="{$stylesRoot}/config.less"/>
                        <link rel="stylesheet/less" href="{$stylesRoot}/_layout-single.less"/>
                    </xsl:with-param>
                </xsl:call-template>
            </head>
            <body>
                <header id="header">
                    <div class="content">
                        <xsl:call-template name="topbar"/>
                    </div>
                </header>
                <div id="body">
                    <xsl:apply-templates select="$content"/>
                    <div class="clear"/>
                </div>
                <footer id="footer">
                    <div class="content">
                        <xsl:call-template name="footer"/>
                    </div>
                </footer>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>