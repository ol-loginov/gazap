<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;"><!ENTITY copy "&#169;"><!ENTITY mdash "&#8212;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:r="com.elurm.gsite.web.mvc.support.XsltViewResources"
                extension-element-prefixes="r">

    <xsl:include href="../shared/layout-single.xsl"/>

    <xsl:template match="/Listing" mode="styles">
    </xsl:template>

    <xsl:template match="/Listing" mode="scripts"/>

    <xsl:template match="/Listing">
        <ul class="breadcrumb">
            <li>
                <a href="{$cp}/catalog">Весь каталог</a>
                <xsl:if test="count(breadcrumbs/anchor)>0">
                    <span class="divider">/</span>
                </xsl:if>
            </li>
            <xsl:for-each select="breadcrumbs/anchor">
                <li>
                    <a href="{$cp}{route}">
                        <xsl:value-of select="title"/>
                    </a>
                    <xsl:if test="last()>position()">
                        <span class="divider">/</span>
                    </xsl:if>
                </li>
            </xsl:for-each>
        </ul>
        <xsl:apply-templates select="suggestions"/>
    </xsl:template>

    <xsl:template match="Listing/suggestions">
        <ul class="unstyled list-bar">
            <xsl:apply-templates select="anchor"/>
        </ul>
    </xsl:template>

    <xsl:template match="Listing/suggestions/anchor">
        <li>
            <a href="{$cp}{route}">
                <xsl:value-of select="title"/>
            </a>
        </li>
    </xsl:template>
</xsl:stylesheet>