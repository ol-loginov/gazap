<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:r="com.elurm.gsite.web.mvc.support.XsltViewResources"
                extension-element-prefixes="r">

    <xsl:include href="../shared/layout-single.xsl"/>

    <xsl:template match="/LoginDialog" mode="styles">
        <link rel="stylesheet/less" href="{$stylesRoot}/layout-single.less"/>
    </xsl:template>

    <xsl:template match="/LoginDialog" mode="scripts"/>

    <xsl:template match="/LoginDialog">
        <div class="login-dialog">
            <h2>
                <xsl:value-of select="r:t($rs, 'LoginDialog.auth.title')"/>
            </h2>

            <div class="row">
                <div class="span2"/>
                <div class="span4">

                </div>
                <div class="span4">
                    <ul class="third-party-auth unstyled">
                        <li class="column">
                            <form action="{$au}/j_spring_openid_security_check" method="post">
                                <input name="openid_identifier" type="hidden"
                                       value="https://www.google.com/accounts/o8/id"/>
                                <input name="_spring_security_remember_me" type="hidden" value="true"/>
                                <input type="submit" class="openid-invoke google" value="Google"/>
                            </form>
                        </li>
                        <li class="column">
                            <form action="{$au}/j_spring_openid_security_check" method="post">
                                <input name="openid_identifier" type="hidden" value="http://openid.yandex.ru"/>
                                <input name="_spring_security_remember_me" type="hidden" value="true"/>
                                <input type="submit" class="openid-invoke yandex" value="Yandex"/>
                            </form>
                        </li>
                        <li class="column">
                            <form action="{$au}/j_spring_oauth_security_check" method="post">
                                <input name="oauth_provider" type="hidden" value="facebook"/>
                                <input name="_spring_security_remember_me" type="hidden" value="true"/>
                                <input type="submit" class="openid-invoke facebook" value="Facebook"/>
                            </form>
                        </li>
                        <li class="column">
                            <form action="{$au}/j_spring_oauth_security_check" method="post">
                                <input name="oauth_provider" type="hidden" value="twitter"/>
                                <input name="_spring_security_remember_me" type="hidden" value="true"/>
                                <input type="submit" class="openid-invoke twitter" value="Twitter"/>
                            </form>
                        </li>
                    </ul>
                </div>
                <div class="span2"/>
            </div>
        </div>
    </xsl:template>

</xsl:stylesheet>