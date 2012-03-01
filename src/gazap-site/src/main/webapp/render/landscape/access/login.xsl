<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:r="com.elurm.gsite.web.mvc.support.XsltViewResources"
                extension-element-prefixes="r">

    <xsl:include href="../shared/layout-single.xsl"/>

    <xsl:template match="/Login" mode="styles">
    </xsl:template>

    <xsl:template match="/Login" mode="scripts"/>

    <xsl:template match="/Login">
        <div class="section-dialog login-variants">
            <h1>
                <xsl:value-of select="r:t($rs, 'Login.auth.title')"/>
            </h1>

            <div class="row">
                <div class="span4 login-variant">
                    <h2>
                        <xsl:value-of select="r:t($rs, 'Login.auth.selection.form')"/>
                    </h2>
                    <div class="well">
                        <form action="{$au}/j_spring_security_check" method="post">
                            <div class="control-group">
                                <input type="text" placeholder="{r:t($rs, 'Login.auth.form.username.placeholder')}"
                                       name="j_username"/>
                            </div>
                            <div class="control-group">
                                <input type="password" placeholder="{r:t($rs, 'Login.auth.form.password.placeholder')}"
                                       name="j_password"/>
                            </div>
                            <button class="btn" type="submit">
                                <xsl:value-of select="r:t($rs, 'Login.auth.form.submit')"/>
                            </button>
                        </form>
                    </div>
                </div>
                <div class="span4 login-variant">
                    <h2>
                        <xsl:value-of select="r:t($rs, 'Login.auth.selection.3rd')"/>
                    </h2>
                    <div class="well">
                        <ul class="unstyled inline">
                            <xsl:apply-templates select="providers/entry"/>
                        </ul>
                    </div>
                </div>
                <div class="span4 login-variant">
                    <h2>
                        <xsl:value-of select="r:t($rs, 'Login.auth.selection.other')"/>
                    </h2>
                    <div class="well">
                        <ul>
                            <li>
                                <a href="{$au}/auth/register">
                                    <xsl:value-of select="r:t($rs, 'Login.auth.other.register')"/>
                                </a>
                            </li>
                            <li>
                                <a href="{$au}/auth/restore">
                                    <xsl:value-of select="r:t($rs, 'Login.auth.other.restore')"/>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </xsl:template>

    <xsl:template match="Login/providers/entry[type='oauth']">
        <li>
            <form action="{$au}/j_spring_oauth_security_check" method="post">
                <input name="oauth_provider" type="hidden" value="{provider}"/>
                <input name="_spring_security_remember_me" type="hidden" value="true"/>
                <input type="submit" class="auth-provider-invoke x48 {provider}"
                       value="{r:t($rs, concat('Login.auth.3rd.',provider))}"/>
            </form>
        </li>
    </xsl:template>

    <xsl:template match="Login/providers/entry[type='openid']">
        <li>
            <form action="{$au}/j_spring_openid_security_check" method="post">
                <input name="openid_identifier" type="hidden" value="{url}"/>
                <input name="_spring_security_remember_me" type="hidden" value="true"/>
                <input type="submit" class="auth-provider-invoke x48 {provider}"
                       value="{r:t($rs, concat('Login.auth.3rd.',provider))}"/>
            </form>
        </li>
    </xsl:template>

</xsl:stylesheet>