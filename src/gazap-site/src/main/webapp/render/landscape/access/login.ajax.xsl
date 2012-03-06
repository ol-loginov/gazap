<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:r="com.elurm.gsite.web.mvc.support.XsltViewResources"
                extension-element-prefixes="r">

    <xsl:include href="../shared/html.xsl"/>

    <xsl:template match="/Login">
        <div id="modalLogin" class="section-dialog login-variants" style="width: 400px;">
            <h1>
                <xsl:value-of select="r:t($rs, 'Login.auth.title')"/>
                <span class="notice">
                    <xsl:value-of select="r:t($rs, 'Login.auth.registration', $au)" disable-output-escaping="yes"/>
                </span>
            </h1>
            <div class="by-form">
                <form id="loginForm" action="{$au}/auth" a-action="{$au}/auth.ajax" method="post">
                    <div class="control-group">
                        <input type="text" placeholder="{r:t($rs, 'Login.auth.form.username.placeholder')}"
                               name="j_username"/>
                    </div>
                    <div class="control-group">
                        <input type="password" placeholder="{r:t($rs, 'Login.auth.form.password.placeholder')}"
                               name="j_password"/>
                    </div>
                    <div class="control-group">
                        <button class="btn form-firer" type="submit">
                            <xsl:value-of select="r:t($rs, 'Login.auth.form.submit')"/>
                        </button>
                        <a href="{$au}/auth/restore" a-href="{$au}/auth/restore.ajax" class="submit-option modal-firer">
                            <xsl:value-of select="r:t($rs, 'Login.auth.form.forget')"/>
                        </a>
                    </div>
                </form>
                <script type="text/javascript">UI.triggerInitLoginDialogAjax('#loginForm')</script>
            </div>
            <div class="by-social">
                <span class="welcome">
                    <xsl:value-of select="r:t($rs, 'Login.auth.3rd')"/>
                </span>
                <ul class="unstyled auth-list">
                    <xsl:apply-templates select="providers/entry"/>
                </ul>
                <div class="clear"/>
            </div>
        </div>
    </xsl:template>

    <xsl:template match="Login/providers/entry[type='oauth']">
        <li>
            <a action="{$au}/auth/oauth/{provider}" target="_blank" class="auth-provider-invoke x26 {provider}">
                <xsl:value-of select="r:t($rs, concat('Login.auth.3rd.',provider))"/>
            </a>
        </li>
    </xsl:template>

    <xsl:template match="Login/providers/entry[type='openid']">
        <li>
            <a href="{$au}/auth/openid/{provider}" target="_blank" class="auth-provider-invoke x26 {provider}">
                <xsl:value-of select="r:t($rs, concat('Login.auth.3rd.',provider))"/>
            </a>
        </li>
    </xsl:template>

</xsl:stylesheet>