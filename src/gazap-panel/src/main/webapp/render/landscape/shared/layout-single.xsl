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
                        <link rel="stylesheet" href="{$cp}/static/bootstrap/dist/css/bootstrap.css"/>
                    </xsl:with-param>
                    <xsl:with-param name="scripts">
                        <script type="text/javascript"
                                src="https://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
                        <script type="text/javascript" src="{$scriptsRoot}/less/less-1.2.1.min.js"></script>
                        <script type="text/javascript" src="{$scriptsRoot}/jquery/jquery.form-2.94.min.js"></script>
                        <script type="text/javascript" src="{$cp}/static/bootstrap/dist/js/bootstrap.min.js"/>
                        <script type="text/javascript" src="{$scriptsRoot}/visitor.js"></script>
                    </xsl:with-param>
                </xsl:call-template>
            </head>
            <body style="padding-top: 40px;">
                <div class="container">
                    <header id="header">
                        <xsl:call-template name="topbar"/>
                    </header>

                    <xsl:apply-templates select="$content"/>

                    <footer id="footer">
                        <xsl:call-template name="footer"/>
                    </footer>
                </div>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>