<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:r="com.elurm.gsite.web.mvc.support.XsltViewResources"
                extension-element-prefixes="r">

    <xsl:include href="html.xsl"/>
    <xsl:include href="layout-single-sections.xsl"/>

    <xsl:template match="/">
        <html>
            <head>
                <xsl:call-template name="html-head">
                    <xsl:with-param name="styles">
                        <!--link rel="stylesheet" href="{$cp}/static/landscape/dist/gazap.min.css"/-->
                        <link rel="stylesheet/less" href="{$cp}/static/landscape/less/layout.less"/>
                        <link rel="stylesheet" href="{$libRoot}/fancybox/jquery.fancybox.css"/>
                    </xsl:with-param>
                    <xsl:with-param name="scripts">
                        <script type="text/javascript" src="{$libRoot}/jquery/jquery-1.7.1.min.js"></script>
                        <script type="text/javascript" src="{$libRoot}/jquery/jquery.form-2.94.min.js"></script>
                        <script type="text/javascript" src="{$libRoot}/less/less-1.2.2.min.js"></script>
                        <script type="text/javascript" src="{$libRoot}/fancybox/jquery.fancybox.js"></script>
                        <script type="text/javascript" src="{$libRoot}/backbone/undescore-1.1.7.min.js"></script>
                        <script type="text/javascript" src="{$libRoot}/backbone/backbone-0.5.3.min.js"></script>
                        <script type="text/javascript" src="{$scriptsRoot}/visitor.js"></script>
                        <script type="text/javascript" src="{$scriptsRoot}/ui.js"></script>
                        <script type="text/javascript" src="{$scriptsRoot}/events/InitLoginDialogAjax.js"></script>
                    </xsl:with-param>
                </xsl:call-template>
            </head>
            <body>
                <xsl:call-template name="layout-single-brandBar"/>
                <xsl:call-template name="layout-single-accountBar"/>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>