<!DOCTYPE xsl:stylesheet [
        <!ENTITY nbsp "&#160;">
        <!ENTITY copy "&#169;">
        <!ENTITY zwsp "&#8203;">
        <!ENTITY mdash "&#8212;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:r="com.elurm.gsite.web.mvc.support.XsltViewResources"
                extension-element-prefixes="r">

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
    </xsl:template>

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

    <xsl:template name="topbar">
        <div class="navbar navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container">
                    <ul class="nav">
                        <li>
                            <a href="#">
                                <xsl:value-of select="r:t($rs, 'tb.menu.catalog')"/>
                            </a>
                        </li>
                    </ul>
                    <a class="brand" href="{$au}">
                        gamza
                    </a>

                    <xsl:variable name="v" select="/*/modules/visitor"/>
                    <xsl:choose>
                        <xsl:when test="$v/logged='true'">
                            <xsl:call-template name="topbar-menu-logged">
                                <xsl:with-param name="visitor" select="$v"/>
                            </xsl:call-template>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:call-template name="topbar-menu-anonymous"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </div>
            </div>
        </div>
    </xsl:template>

    <xsl:template name="topbar-menu-anonymous">
        <form class="navbar-search pull-left">
            <input type="text" class="search-query" placeholder="{r:t($rs, 'tb.menu.search')}"/>
        </form>

        <ul class="nav  pull-right">
            <li>
                <a id="auth-welcome" href="{$au}/auth">
                    <xsl:value-of select="r:t($rs, 'tb.menu.login')"/>
                </a>
            </li>
        </ul>
    </xsl:template>

    <xsl:template name="topbar-menu-logged">
        <xsl:param name="visitor"/>

        <ul class="nav pull-left" style="margin-left: 50px;">
            <!-- visitor display -->
            <li class="gamer-label">
                <span>
                    <span>
                        <xsl:value-of select="r:t($rs,'tb.menu.welcome')"/>
                    </span>
                    <a>
                        <xsl:value-of select="$visitor/name"/>
                    </a>
                    <span style="padding-left:5px;">
                        <xsl:value-of select="r:t($rs,'tb.menu.welcome.gamer.aka')"/>
                    </span>
                </span>
            </li>
            <!-- player selection -->
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    <xsl:if test="count($visitor/player)>0">
                        <xsl:value-of select="$visitor/player/name"/>
                    </xsl:if>
                    <xsl:if test="not(count($visitor/player)>0)">
                        <xsl:value-of select="r:t($rs,'tb.menu.welcome.gamer.aka.none')"/>
                    </xsl:if>
                    <b class="caret"></b>
                </a>
                <ul class="dropdown-menu">
                    <li>
                        <a href="{$cp}/players/add">
                            добавить
                        </a>
                    </li>
                </ul>
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
            <img src="{$stylesRoot}/img/socials/{provider}-48x48.png"/>
            <div class="detail">
                <xsl:value-of select="r:t($rs, concat('authProvider.title.', provider))"/>
            </div>
        </a>
    </xsl:template>

    <xsl:template match="/*/modules/visitor/authProviders/provider[type='oauth']" mode="render-account-menu">
        <a href="{$cp}/auth/oauth?oauth_provider={provider}">
            <img src="{$stylesRoot}/img/socials/{provider}-48x48.png"/>
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
                    <xsl:with-param name="thumb" select="concat($stylesRoot,'/img/topmenu/palette.png')"/>
                </xsl:call-template>
            </li>
            <li>
                <xsl:call-template name="topbar-menu-button">
                    <xsl:with-param name="header" select="'tb.menu.account'"/>
                    <xsl:with-param name="route" select="concat($cp,'/account')"/>
                    <xsl:with-param name="thumb" select="concat($stylesRoot,'/img/topmenu/tools.png')"/>
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