<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:r="com.elurm.gsite.web.mvc.support.XsltViewResources"
                extension-element-prefixes="r">

    <xsl:include href="../shared/html.xsl"/>

    <xsl:template match="/Register">
        <div id="modalRegister" class="small-modal-dialog">
            <h1>
                <xsl:value-of select="r:t($rs, 'Register.title')"/>
                <span class="notice">
                    <xsl:value-of select="r:t($rs, 'Register.title.login', $au)" disable-output-escaping="yes"/>
                </span>
            </h1>
            <p class="notice alert-info">
                <xsl:value-of select="r:t($rs, 'Register.notice', $au)" disable-output-escaping="yes"/>
            </p>
            <div>
                <form id="registerForm" action="{$au}/auth/register" data-ax-action="{$au}/auth/register.ajax"
                      method="post">
                    <div class="control-group">
                        <div class="controls">
                            <input type="text" placeholder="{r:t($rs, 'Register.form.username.placeholder')}"
                                   name="username"/>
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <input type="password" placeholder="{r:t($rs, 'Register.form.password.placeholder')}"
                                   name="password"/>
                        </div>
                    </div>
                    <div class="control-group submit-group">
                        <button class="btn" type="submit">
                            <xsl:value-of select="r:t($rs, 'Register.form.submit')"/>
                        </button>
                        <a class="submit-option modal-closer">
                            <xsl:value-of select="r:t($rs, 'Register.form.cancel')"/>
                        </a>
                    </div>
                    <div class="alert alert-message alert-progress hidden">
                        <xsl:value-of select="r:t($rs, 'Register.form.submitting')"/>
                    </div>
                    <div class="alert alert-error hidden"/>
                </form>
                <script type="text/javascript">UI.triggerInitModalRegisterDialog('#registerForm')</script>
            </div>
        </div>
    </xsl:template>

</xsl:stylesheet>