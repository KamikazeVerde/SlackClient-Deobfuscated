/*    */ package de.gerrygames.viarewind.replacement;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*    */ 
/*    */ 
/*    */ public class Replacement
/*    */ {
/*    */   private final int id;
/*    */   private final int data;
/*    */   
/*    */   public Replacement(int id) {
/* 15 */     this(id, -1);
/*    */   }
/*    */   private final String name; private String resetName; private String bracketName;
/*    */   public Replacement(int id, int data) {
/* 19 */     this(id, data, null);
/*    */   }
/*    */   
/*    */   public Replacement(int id, String name) {
/* 23 */     this(id, -1, name);
/*    */   }
/*    */   
/*    */   public Replacement(int id, int data, String name) {
/* 27 */     this.id = id;
/* 28 */     this.data = data;
/* 29 */     this.name = name;
/* 30 */     if (name != null) {
/* 31 */       this.resetName = "§r" + name;
/* 32 */       this.bracketName = " §r§7(" + name + "§r§7)";
/*    */     } 
/*    */   }
/*    */   
/*    */   public int getId() {
/* 37 */     return this.id;
/*    */   }
/*    */   
/*    */   public int getData() {
/* 41 */     return this.data;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 45 */     return this.name;
/*    */   }
/*    */   
/*    */   public Item replace(Item item) {
/* 49 */     item.setIdentifier(this.id);
/* 50 */     if (this.data != -1) item.setData((short)this.data); 
/* 51 */     if (this.name != null) {
/* 52 */       CompoundTag compoundTag = (item.tag() == null) ? new CompoundTag() : item.tag();
/* 53 */       if (!compoundTag.contains("display")) compoundTag.put("display", (Tag)new CompoundTag()); 
/* 54 */       CompoundTag display = (CompoundTag)compoundTag.get("display");
/* 55 */       if (display.contains("Name")) {
/* 56 */         StringTag name = (StringTag)display.get("Name");
/* 57 */         if (!name.getValue().equals(this.resetName) && !name.getValue().endsWith(this.bracketName))
/* 58 */           name.setValue(name.getValue() + this.bracketName); 
/*    */       } else {
/* 60 */         display.put("Name", (Tag)new StringTag(this.resetName));
/*    */       } 
/* 62 */       item.setTag(compoundTag);
/*    */     } 
/* 64 */     return item;
/*    */   }
/*    */   
/*    */   public int replaceData(int data) {
/* 68 */     return (this.data == -1) ? data : this.data;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\replacement\Replacement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */