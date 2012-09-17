<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:r="com.elurm.gsite.web.mvc.support.XsltViewResources"
                extension-element-prefixes="r">

    <xsl:include href="../shared/html.xsl"/>

    <xsl:template match="/Login">
        <div id="modalLogin" class="small-modal-dialog">
            <h1>
                <xsl:value-of select="r:t($rs, 'Login.title')"/>
                <span class="notice">
                    <xsl:value-of select="r:t($rs, 'Login.title.registration', $au)" disable-output-escaping="yes"/>
                </span>
            </h1>
            <p class="notice alert-info">
                <xsl:value-of select="r:t($rs, 'Login.notice.forget', $au)" disable-output-escaping="yes"/>
            </p>
            <div class="by-form">
                <form id="loginForm" action="{$au}/auth" data-ax-action="{$au}/auth.ajax" method="post">
                    <input type="hidden" name="_spring_security_remember_me" value="on"/>
                    <div class="control-group">
                        <div class="controls">
                            <input type="email" placeholder="{r:t($rs, 'Login.auth.form.username.placeholder')}"
                                   name="j_username"/>
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <input type="password" placeholder="{r:t($rs, 'Login.auth.form.password.placeholder')}"
                                   name="j_password"/>
                        </div>
                    </div>
                    <div class="control-group submit-group">
                        <button class="btn" type="submit">
                            <xsl:value-of select="r:t($rs, 'Login.form.submit')"/>
                        </button>
                        <a class="submit-option modal-closer">
                            <xsl:value-of select="r:t($rs, 'Login.form.cancel')"/>
                        </a>
                    </div>
                    <div class="alert alert-message alert-progress hidden">
                        <xsl:value-of select="r:t($rs, 'Login.form.submitting')"/>
                    </div>
                    <div class="alert alert-error hidden"/>
                </form>
                <script type="text/javascript">UI.triggerInitModalLoginDialog('#loginForm')</script>
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