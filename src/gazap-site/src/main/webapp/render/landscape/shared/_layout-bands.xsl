<!DOCTYPE xsl:stylesheet [
        <!ENTITY nbsp "&#160;">
        <!ENTITY copy "&#169;">
        <!ENTITY zwsp "&#8203;">
        <!ENTITY mdash "&#8212;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:r="com.elurm.gsite.web.mvc.support.XsltViewResources"
                extension-element-prefixes="r">

    <xsl:template name="topbar-menu-button">
        <xsl:param name="header"/>
        <xsl:param name="route"/>
        <xsl:param name="thumb"/>

        <h2>
            <xsl:value-of select="r:t($rs,$header)"/>
        </h2>
        <a class="thumbnail" href="{$route}">
            <img src="{$thumb}"/>
        </a>
        <div class="detail">
            <xsl:value-of select="r:t($rs,concat($header,'.detail'))"/>
        </div>
    </xsl:template>

    <xsl:template name="application-bar">
        <div class="application-bar">
            <ul>
                <li>
                    <a href="{$au}" class="brand">GAMZA</a>
                </li>
            </ul>
            <xsl:call-template name="application-bar-account-band"/>
        </div>
    </xsl:template>

    <xsl:template name="application-bar-account-band">
        <xsl:variable name="v" select="/*/modules/visitor"/>
        <xsl:choose>
            <xsl:when test="$v/logged='true'">
                <xsl:call-template name="application-bar-account-band-logged">
                    <xsl:with-param name="visitor" select="$v"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="application-bar-account-band-anonymous"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="application-bar-account-band-anonymous">
        <ul id="accountBar">
            <li>
                <a id="auth-welcome" class="a" href="{$au}/auth">
                    <xsl:value-of select="r:t($rs, 'tb.menu.login')"/>
                </a>
            </li>
        </ul>
    </xsl:template>

    <xsl:template name="application-bar-account-band-logged">
        <xsl:param name="visitor"/>

        <ul id="accountBar">
            <!-- visitor display -->
            <li>
                <a href="{$cp}/profile" class="btn">
                    <img src="http://www.gravatar.com/avatar/{$visitor/gravatar}?s=24"
                         style="position:absolute;left;top:50%;margin-top:-12px;margin-left: -6px;"/>
                    <span style="margin-left: 24px;">
                        <xsl:if test="string-length($visitor/name)>0">
                            <xsl:value-of select="$visitor/name"/>
                        </xsl:if>
                        <xsl:if test="not(string-length($visitor/name)>0)">
                            <xsl:value-of select="r:t($rs,'tb.menu.welcome.anonymous.name')"/>
                        </xsl:if>
                    </span>
                </a>
            </li>
        </ul>

    </xsl:template>

    <xsl:template match="/*/modules/visitor[logged='false']" mode="render-account-menu">
        <xsl:apply-templates select="authProviders" mode="render-account-menu"/>
    </xsl:template>

    <xsl:template match="/*/modules/visitor/authProviders" mode="render-account-menu">
        <ul class="unstyled">
            <xsl:for-each select="provider">
                <li>
                    <xsl:apply-templates select="." mode="render-account-menu"/>
                </li>
            </xsl:for-each>
            <li class="disclaimer">
                <p>
                    <xsl:value-of select="r:t($rs, 'authProvider.disclaimer')"/>
                </p>
            </li>
        </ul>
    </xsl:template>

    <xsl:template match="/*/modules/visitor/authProviders/provider[type='openid']" mode="render-account-menu">
        <a href="{$cp}/auth/openid?url={url}">
            <img src="{$imagesRoot}/socials/{provider}-48x48.png"/>
            <div class="detail">
                <xsl:value-of select="r:t($rs, concat('authProvider.title.', provider))"/>
            </div>
        </a>
    </xsl:template>

    <xsl:template match="/*/modules/visitor/authProviders/provider[type='oauth']" mode="render-account-menu">
        <a href="{$cp}/auth/oauth?oauth_provider={provider}">
            <img src="{$imagesRoot}/socials/{provider}-48x48.png"/>
            <div class="detail">
                <xsl:value-of select="r:t($rs, concat('authProvider.title.', provider))"/>
            </div>
        </a>
    </xsl:template>

    <xsl:template match="/*/modules/visitor[logged='true']" mode="render-account-menu">
        <ul class="unstyled">
            <li>
                <xsl:call-template name="topbar-menu-button">
                    <xsl:with-param name="header" select="'tb.menu.studio'"/>
                    <xsl:with-param name="route" select="concat($cp,'/studio')"/>
                    <xsl:with-param name="thumb" select="concat($stylesRoot,'/topmenu/palette.png')"/>
                </xsl:call-template>
            </li>
            <li>
                <xsl:call-template name="topbar-menu-button">
                    <xsl:with-param name="header" select="'tb.menu.account'"/>
                    <xsl:with-param name="route" select="concat($cp,'/account')"/>
                    <xsl:with-param name="thumb" select="concat($stylesRoot,'/topmenu/tools.png')"/>
                </xsl:call-template>
            </li>
        </ul>
    </xsl:template>

    <xsl:template name="footer">
        <span>
            <xsl:text>2011-</xsl:text>
            <xsl:value-of select="/*/modules/cdn/year"/>
        </span>
    </xsl:template>
</xsl:stylesheet>