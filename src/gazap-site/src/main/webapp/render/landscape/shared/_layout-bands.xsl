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

        <xsl:copy-of select="$styles"/>
        <xsl:apply-templates select="$content" mode="styles"/>

        <script type="text/javascript">V={};</script>
        <script type="text/javascript">
            <xsl:text>E={"SCE":'</xsl:text>
            <xsl:value-of select="r:t($rs,'E.SCE')"/>
            <xsl:text>'};</xsl:text>
        </script>
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
        <script type="text/javascript" src="{$scriptsRoot}/visitor.js"></script>
        <script type="text/javascript" src="{$scriptsRoot}/site-navigation.js"></script>
        <script type="text/javascript" src="{$scriptsRoot}/ajax-forms.js"></script>
        <script type="text/javascript" src="{$scriptsRoot}/less/less-1.1.6.min.js"></script>
        <script type="text/javascript" src="{$scriptsRoot}/jquery/jquery.form-2.94.min.js"></script>
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
        <div id="topbarMenu">
            <ul class="hmenu unstyled">
                <li class="navigation">
                    <a id="navigationCaller" href="{$au}">
                        <xsl:value-of select="r:t($rs,'tb.menu.welcome')"/>
                    </a>
                </li>
                <xsl:if test="/*/modules/visitor/debug='true'">
                    <li>
                        <a id="debugCaller" href="?d:v=xml">
                            <xsl:text>[xml]</xsl:text>
                        </a>
                    </li>
                </xsl:if>
                <li class="account">
                    <xsl:choose>
                        <xsl:when test="//modules/visitor/logged='true'">
                            <a id="accountCaller" href="{$cp}/account">
                                <xsl:value-of select="r:t($rs,'tb.menu.account.welcome.prefix')"/>
                                <span class="name">
                                    <xsl:value-of select="//modules/visitor/name"/>
                                </span>
                            </a>
                        </xsl:when>
                        <xsl:otherwise>
                            <a id="accountCaller" href="{$cp}/auth">
                                <xsl:value-of select="r:t($rs, 'tb.menu.account.login')"/>
                            </a>
                        </xsl:otherwise>
                    </xsl:choose>
                </li>
            </ul>
            <div class="clear"/>
            <div id="navigation" class="hidden topmenu">
                <ul class="unstyled">
                    <li>
                        <xsl:call-template name="topbar-menu-button">
                            <xsl:with-param name="header" select="'tb.menu.channels'"/>
                            <xsl:with-param name="route" select="concat($cp,'/channels')"/>
                            <xsl:with-param name="thumb" select="concat($stylesRoot,'/img/topmenu/gallery.png')"/>
                        </xsl:call-template>
                    </li>
                    <li>
                        <xsl:call-template name="topbar-menu-button">
                            <xsl:with-param name="header" select="'tb.menu.settings'"/>
                            <xsl:with-param name="route" select="concat($cp,'/settings')"/>
                            <xsl:with-param name="thumb" select="concat($stylesRoot,'/img/topmenu/tools.png')"/>
                        </xsl:call-template>
                    </li>
                </ul>
                <div class="clear"/>
            </div>

            <xsl:variable name="visitor" select="/*/modules/visitor"/>
            <div id="account" class="hidden topmenu logged-{$visitor/logged}">
                <xsl:apply-templates select="$visitor" mode="render-account-menu"/>
                <div class="clear"/>
            </div>
        </div>
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