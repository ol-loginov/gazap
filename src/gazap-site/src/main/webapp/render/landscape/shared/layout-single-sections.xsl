<!DOCTYPE xsl:stylesheet [
        <!ENTITY nbsp "&#160;">
        <!ENTITY copy "&#169;">
        <!ENTITY zwsp "&#8203;">
        <!ENTITY mdash "&#8212;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:r="com.elurm.gsite.web.mvc.support.XsltViewResources"
                extension-element-prefixes="r">

    <xsl:template name="layout-single-brandBar">
        <section id="brandBar">
            <a class="brand-logo" href="{$au}">
                <span>GAMZA</span>
            </a>
        </section>
    </xsl:template>

    <xsl:template name="layout-single-accountBar">
        <xsl:variable name="v" select="/*/modules/visitor"/>
        <section id="accountBar" class="logged-{$v/logged}">
            <xsl:choose>
                <xsl:when test="$v/logged='true'">
                    <xsl:call-template name="layout-single-accountBar-logged">
                        <xsl:with-param name="visitor" select="$v"/>
                    </xsl:call-template>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:call-template name="layout-single-accountBar-anonymous"/>
                </xsl:otherwise>
            </xsl:choose>
        </section>
    </xsl:template>

    <xsl:template name="layout-single-accountBar-anonymous">
        <h3>
            <xsl:value-of select="r:t($rs,'accountBar.anonymous.welcome')" disable-output-escaping="yes"/>
        </h3>
        <p>
            <xsl:value-of select="r:t($rs,'accountBar.anonymous.welcome.p', $au)" disable-output-escaping="yes"/>
        </p>
    </xsl:template>


    <xsl:template name="layout-single-accountBar-logged">
        <xsl:param name="visitor"/>

        <div class="account-name">
            <a href="{$cp}/profile">
                <img src="http://www.gravatar.com/avatar/{$visitor/gravatar}?s=16"/>
                <span>
                    <xsl:if test="string-length($visitor/name)>0">
                        <xsl:value-of select="$visitor/name"/>
                    </xsl:if>
                    <xsl:if test="not(string-length($visitor/name)>0)">
                        <xsl:value-of select="r:t($rs,'tb.menu.welcome.anonymous.name')"/>
                    </xsl:if>
                </span>
            </a>
        </div>
        <div class="account-menu">
            <ul class="unstyled">
                <li>
                    <a href="{$cp}/user/{$visitor/@id}/maps">Карты</a>
                </li>
            </ul>
        </div>
    </xsl:template>
</xsl:stylesheet>