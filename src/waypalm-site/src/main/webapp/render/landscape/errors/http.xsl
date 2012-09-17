<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:r="com.elurm.gsite.web.mvc.support.XsltViewResources"
                extension-element-prefixes="r">

    <xsl:include href="../shared/layout-single.xsl"/>

    <xsl:template match="/ErrorPage" mode="styles">
    </xsl:template>

    <xsl:template match="/ErrorPage" mode="scripts"/>

    <xsl:template match="/ErrorPage">
        <xsl:variable name="prefix" select="concat('http.', http)"/>

        <div class="error-disclaimer">
            <h1>
                <xsl:value-of select="r:t($rs,$prefix, $au)" disable-output-escaping="yes"/>
                <p>
                    <xsl:value-of select="r:t($rs,concat($prefix,'.h'), $au)" disable-output-escaping="yes"/>
                </p>
            </h1>
            <p>
                <xsl:value-of select="r:t($rs,concat($prefix,'.p'), $au)" disable-output-escaping="yes"/>
            </p>
            <ul>
                <xsl:for-each select="suggestions/key">
                    <li>
                        <xsl:value-of select="r:t($rs, concat($prefix, '.suggestions.', .), $au)"
                                      disable-output-escaping="yes"/>
                    </li>
                </xsl:for-each>
            </ul>
        </div>
    </xsl:template>
</xsl:stylesheet>