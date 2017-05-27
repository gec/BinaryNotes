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

    <xsl:template name="choiceDecl">
	<xsl:param name="elementName"/>        
	<xsl:variable name="choiceName"><xsl:call-template name="toUpperFirstLetter"><xsl:with-param name="input" select="$elementName"/></xsl:call-template>ChoiceType</xsl:variable>
            <xsl:if test="typeReference/isChoice = 'true'">
                <xsl:for-each select="typeReference">

    [ASN1PreparedElement]    
    [ASN1Choice ( Name = "<xsl:value-of select='$elementName'/>" )]
    public class <xsl:value-of select='$choiceName'/> : IASN1PreparedElement  {
	    <xsl:call-template name="elements"><xsl:with-param name="isChoice">true</xsl:with-param></xsl:call-template>
            <xsl:call-template name="choiceFunctions"/>

            public void initWithDefaults()
	    {
	    }

            private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(typeof(<xsl:value-of select='$choiceName'/>));
            public IASN1PreparedElementData PreparedData {
            	get { return preparedData; }
            }

    }
                </xsl:for-each>
            </xsl:if>
    </xsl:template>
</xsl:stylesheet>
