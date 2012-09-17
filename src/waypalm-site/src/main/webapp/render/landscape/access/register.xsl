<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:r="com.elurm.gsite.web.mvc.support.XsltViewResources"
                extension-element-prefixes="r">

    <xsl:include href="../shared/layout-single.xsl"/>

    <xsl:template match="/Register" mode="styles">
    </xsl:template>

    <xsl:template match="/Register" mode="scripts"/>

    <xsl:template match="/Register">
        <div class="row">
            <div class="span5 offset2 login-variant">
                <h2>
                    <xsl:value-of select="r:t($rs, 'Register.form.h2')"/>
                </h2>
                <div class="well">
                    <form method="POST">
                        <div class="control-group">
                            <input type="text" placeholder="Email" name="username"/>
                        </div>
                        <div class="control-group">
                            <input type="password" placeholder="Password" name="password"/>
                        </div>
                        <div class="control-group">
                            <input type="password" placeholder="Retype password" name="passwordConfirm"/>
                        </div>
                        <div class="form-buttons">
                            <button class="btn" type="submit">Войти</button>
                            <a class="link" href="{$cp}/auth">к страничке входа</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </xsl:template>

</xsl:stylesheet>