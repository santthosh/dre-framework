<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">      
  <xsl:template match="/">
    <html>
      <body>        
        <font face="arial" size="2">
          <xsl:apply-templates/>
        </font>
      </body>
    </html>
  </xsl:template>

  <xsl:template match="ResultSet">
    <xsl:if test="not(node()) or not(preceding-sibling::node()[.=string(current())])">
      <xsl:copy>
        <xsl:apply-templates select="@*|node()"/>
      </xsl:copy>
    </xsl:if>
  </xsl:template>

  <xsl:template match="Result">
    <xsl:if test="not(node()) or not(preceding-sibling::node()[.=string(current())])">
      <xsl:copy>
        <p>
          <xsl:variable name="hrefUrl">
            <xsl:value-of select="Url"/>
          </xsl:variable>

          <xsl:variable name="hrefCacheUrl">
            <xsl:value-of select="CacheUrl"/>
          </xsl:variable>

          <a href="{$hrefUrl}">
            <xsl:apply-templates select="Title"/>
          </a><br/>          
          <xsl:apply-templates select="Description"/>          
          <xsl:apply-templates select="DisplayUrl"/>
          <xsl:apply-templates select="CacheSize"/>
          
          <a href="{$hrefCacheUrl}" style='text-decoration: none;'>
            <font color="gray"> - Cache  </font>
          </a>

          <font color="red">[Found in <xsl:apply-templates select="Source"/>]</font>
          <br/>
        </p>
      </xsl:copy>
    </xsl:if>    
  </xsl:template>

  <xsl:template match="Title">
    <font size="3" color="0000CC">
      <xsl:value-of select='.'/>
    </font>
  </xsl:template>

  <xsl:template match="Url">
    <xsl:value-of select="."/>
  </xsl:template>

  <xsl:template match="Description">
    <xsl:value-of select='.' disable-output-escaping="yes"/>
    <br/>
  </xsl:template>

  <xsl:template match="DisplayUrl">
    <font color="green">
      <xsl:value-of select='.'/>
    </font>
  </xsl:template>

  <xsl:template match="CacheSize">
    <font color="gray">
      - <xsl:value-of select=". div 1000"/>k
    </font>
  </xsl:template>

</xsl:stylesheet>