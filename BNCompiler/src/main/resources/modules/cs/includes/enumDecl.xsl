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

    <xsl:import href="header.xsl"/>
    <xsl:import href="footer.xsl"/>
    <xsl:import href="enumItems.xsl"/>

    <xsl:output method="text" encoding="UTF-8" indent="no"/>
    
    <xsl:variable name="outputDirectory"><xsl:value-of select="//outputDirectory"/></xsl:variable>
    
    <xsl:template name="enumDecl">
	<xsl:param name="elementName"/>        
	<xsl:variable name="enumName"><xsl:call-template name="toUpperFirstLetter"><xsl:with-param name="input" select="$elementName"/></xsl:call-template>EnumType</xsl:variable>
            <xsl:if test="typeReference/isEnum = 'true'">
                <xsl:for-each select="typeReference">


    [ASN1PreparedElement]
    [ASN1Enum ( Name = "<xsl:value-of select='$enumName'/>")]
    public class <xsl:value-of select='$enumName'/> : IASN1PreparedElement {        
        public enum EnumType {
            <xsl:call-template name="enumItems"/>
        }
        
        private EnumType val;
        
        public EnumType Value
        {
            get { return val; }
            set { val = value; }
        }        

            public void initWithDefaults()
	    {
	    }


            private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(typeof(<xsl:value-of select='$enumName'/>));
            public IASN1PreparedElementData PreparedData {
            	get { return preparedData; }
            }

                
    }

                </xsl:for-each>
            </xsl:if>

    </xsl:template>
    
</xsl:stylesheet>
