/*    */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*    */ import com.viaversion.viaversion.api.type.types.minecraft.MetaListTypeTemplate;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class MetadataListType
/*    */   extends MetaListTypeTemplate {
/* 11 */   private final MetadataType metadataType = new MetadataType();
/*    */   
/*    */   public List<Metadata> read(ByteBuf buffer) throws Exception {
/*    */     Metadata m;
/* 15 */     ArrayList<Metadata> list = new ArrayList<>();
/*    */ 
/*    */     
/*    */     do {
/* 19 */       m = (Metadata)Types1_7_6_10.METADATA.read(buffer);
/* 20 */       if (m == null)
/* 21 */         continue;  list.add(m);
/*    */     }
/* 23 */     while (m != null);
/*    */     
/* 25 */     return list;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, List<Metadata> metadata) throws Exception {
/* 30 */     for (Metadata meta : metadata) {
/* 31 */       Types1_7_6_10.METADATA.write(buffer, meta);
/*    */     }
/* 33 */     if (metadata.isEmpty()) {
/* 34 */       Types1_7_6_10.METADATA.write(buffer, new Metadata(0, MetaType1_7_6_10.Byte, Byte.valueOf((byte)0)));
/*    */     }
/* 36 */     buffer.writeByte(127);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\types\MetadataListType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */