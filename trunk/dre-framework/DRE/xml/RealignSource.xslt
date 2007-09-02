<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">      
  <xsl:template match="/">
     <xsl:apply-templates/>
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
        <Source>
          <xsl:value-of select="@Source"/>
        </Source>
        <Rank>
          <xsl:value-of select="@Rank"/>
        </Rank>
        <xsl:apply-templates select="Title"/>
        <xsl:apply-templates select="Url"/>
        <xsl:apply-templates select="Description"/>
        <xsl:apply-templates select="DisplayUrl"/>
        <xsl:apply-templates select="CacheSize"/>
        <xsl:apply-templates select="CacheUrl"/>
        <xsl:apply-templates select="Mimetype"/>
        <xsl:apply-templates select="ModificationDate"/>
      </xsl:copy>
    </xsl:if>
  </xsl:template>

  <xsl:template match="Title">
    <Title>
      <xsl:value-of select='.'/>
    </Title>
  </xsl:template>

  <xsl:template match="Url">
    <Url>
      <xsl:value-of select="."/>
    </Url>
  </xsl:template>

  <xsl:template match="Description">
    <Description>
      <xsl:value-of select='.'/>
    </Description>
  </xsl:template>

  <xsl:template match="DisplayUrl">
    <DisplayUrl>
      <xsl:value-of select='.'/>
    </DisplayUrl>
  </xsl:template>

  <xsl:template match="CacheSize">
    <CacheSize>
      <xsl:value-of select="."/>
    </CacheSize>
  </xsl:template>

  <xsl:template match="CacheUrl">
    <CacheUrl>
      <xsl:value-of select="."/>
    </CacheUrl>
  </xsl:template>

  <xsl:template match="Mimetype">
    <Mimetype>
      <xsl:value-of select="."/>
    </Mimetype>
  </xsl:template>
  
  <xsl:template match="ModificationDate">
    <ModificationDate>
      <xsl:value-of select="."/>
    </ModificationDate>
  </xsl:template>
  
</xsl:stylesheet>
