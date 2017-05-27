<?xml version="1.0" encoding="utf-8" ?>
<!--
/*
 Copyright 2006-2011 Abdulla Abdurakhmanov (abdulla@latestbit.com)
 Original sources are available at www.latestbit.com

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xsltc="http://xml.apache.org/xalan/xsltc"
    xmlns:redirect="http://xml.apache.org/xalan/redirect"
    extension-element-prefixes="xsltc redirect"
>
    <xsl:output method="text" encoding="UTF-8" indent="no"/>

    <xsl:template name="componentTypeName">
	<xsl:param name="typeName"/>
	<xsl:variable name="found">
    		<xsl:for-each select="//module/asnTypes/sequenceSets">
			<xsl:variable name="dName"><xsl:call-template name="doMangleIdent"><xsl:with-param name='input' select="name"/></xsl:call-template></xsl:variable>
			<xsl:if test="$dName = $typeName">
            			<xsl:call-template name="elements"/>            
            			<xsl:call-template name="sequenceFunctions"/>
			</xsl:if>
	    	</xsl:for-each>
    		<xsl:for-each select="//module/asnTypes/choices">
			<xsl:variable name="dName"><xsl:call-template name="doMangleIdent"><xsl:with-param name='input' select="name"/></xsl:call-template></xsl:variable>
			<xsl:if test="$dName = $typeName">
	    			<xsl:call-template name="elements"><xsl:with-param name="isChoice">true</xsl:with-param></xsl:call-template>
            			<xsl:call-template name="choiceFunctions"/>
			</xsl:if>
	    	</xsl:for-each>
	</xsl:variable>	

	<xsl:value-of select="$found"/>

	<xsl:if test="string-length($found)&lt;1">
		<!-- Trying to find redefined sequence -->
    		<xsl:for-each select="//module/asnTypes/defineds">
			<xsl:variable name="dName"><xsl:call-template name="doMangleIdent"><xsl:with-param name='input' select="name"/></xsl:call-template></xsl:variable>
			<xsl:if test="$dName = $typeName">
				<xsl:call-template name="componentTypeName"><xsl:with-param name="typeName" select="typeName"/></xsl:call-template>
			</xsl:if>		
    		</xsl:for-each>
	</xsl:if>

    </xsl:template>

</xsl:stylesheet>
