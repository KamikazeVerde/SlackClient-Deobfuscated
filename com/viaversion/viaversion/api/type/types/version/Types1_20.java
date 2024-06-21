/*    */ package com.viaversion.viaversion.api.type.types.version;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.types.MetaTypes;
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.types.MetaTypes1_19_4;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.api.type.types.minecraft.MetaListType;
/*    */ import com.viaversion.viaversion.api.type.types.minecraft.ParticleType;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Types1_20
/*    */ {
/* 34 */   public static final ParticleType PARTICLE = new ParticleType();
/* 35 */   public static final MetaTypes1_19_4 META_TYPES = new MetaTypes1_19_4(PARTICLE);
/* 36 */   public static final Type<Metadata> METADATA = (Type<Metadata>)new MetadataType((MetaTypes)META_TYPES);
/* 37 */   public static final Type<List<Metadata>> METADATA_LIST = (Type<List<Metadata>>)new MetaListType(METADATA);
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\version\Types1_20.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */