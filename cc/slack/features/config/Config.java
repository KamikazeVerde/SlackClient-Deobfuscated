/*    */ package cc.slack.features.config;
/*    */ import cc.slack.Slack;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.BooleanValue;
/*    */ import cc.slack.features.modules.api.settings.impl.ColorValue;
/*    */ import cc.slack.features.modules.api.settings.impl.ModeValue;
/*    */ import cc.slack.features.modules.api.settings.impl.NumberValue;
/*    */ import cc.slack.features.modules.api.settings.impl.StringValue;
/*    */ import cc.slack.features.modules.api.settings.impl.SubCatagory;
/*    */ import cc.slack.utils.client.mc;
/*    */ import cc.slack.utils.other.FileUtil;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import java.awt.Color;
/*    */ import java.io.File;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.Minecraft;
/*    */ 
/*    */ public class Config extends mc {
/*    */   private final String name;
/* 23 */   private final File directory = new File((Minecraft.getMinecraft()).mcDataDir, "/SlackClient/configs"); public File getDirectory() { return this.directory; }
/*    */    public String getName() {
/* 25 */     return this.name;
/*    */   }
/*    */   public Config(String name) {
/* 28 */     this.name = name;
/*    */   }
/*    */   
/*    */   public void write() {
/* 32 */     JsonObject jsonObject = new JsonObject();
/*    */     
/* 34 */     for (Module module : Slack.getInstance().getModuleManager().getModules()) {
/* 35 */       JsonObject moduleJson = new JsonObject();
/*    */       
/* 37 */       moduleJson.addProperty("state", Boolean.valueOf(module.isToggle()));
/* 38 */       moduleJson.addProperty("bind", Integer.valueOf(module.getKey()));
/*    */       
/* 40 */       JsonObject valueJson = new JsonObject();
/*    */       
/* 42 */       module.getSetting().forEach(property -> {
/*    */             if (property instanceof BooleanValue) {
/*    */               valueJson.addProperty(property.getName(), (Boolean)((BooleanValue)property).getValue());
/*    */             }
/*    */             
/*    */             if (property instanceof ModeValue) {
/*    */               valueJson.addProperty(property.getName(), Integer.valueOf(((ModeValue)property).getIndex()));
/*    */             }
/*    */             
/*    */             if (property instanceof NumberValue) {
/*    */               if (((NumberValue)property).getMinimum() instanceof Integer) {
/*    */                 valueJson.addProperty(property.getName(), (Integer)property.getValue());
/*    */               }
/*    */               
/*    */               if (((NumberValue)property).getMinimum() instanceof Float) {
/*    */                 valueJson.addProperty(property.getName(), (Float)property.getValue());
/*    */               }
/*    */               
/*    */               if (((NumberValue)property).getMinimum() instanceof Double) {
/*    */                 valueJson.addProperty(property.getName(), (Double)property.getValue());
/*    */               }
/*    */             } 
/*    */             if (property instanceof ColorValue) {
/*    */               valueJson.addProperty(property.getName(), Integer.valueOf(((Color)((ColorValue)property).getValue()).getRGB()));
/*    */             }
/*    */             if (property instanceof StringValue) {
/*    */               valueJson.addProperty(property.getName(), (String)((StringValue)property).getValue());
/*    */             }
/*    */             if (property instanceof SubCatagory) {
/*    */               valueJson.addProperty(property.getName(), (Boolean)((SubCatagory)property).getValue());
/*    */             }
/*    */           });
/* 74 */       moduleJson.add("values", (JsonElement)valueJson);
/* 75 */       jsonObject.add(module.getName(), (JsonElement)moduleJson);
/*    */     } 
/*    */     
/* 78 */     FileUtil.writeJsonToFile(jsonObject, (new File(this.directory, this.name + ".json")).getAbsolutePath());
/*    */   }
/*    */   public void read() {
/* 81 */     JsonObject config = FileUtil.readJsonFromFile((new File(this.directory, this.name + ".json"))
/*    */         
/* 83 */         .getAbsolutePath());
/* 84 */     configManager.currentConfig = this.name;
/* 85 */     for (Iterator<Map.Entry<String, JsonElement>> iterator = config.entrySet().iterator(); iterator.hasNext(); ) { Map.Entry<String, JsonElement> entry = iterator.next();
/* 86 */       Slack.getInstance().getModuleManager().getModules().forEach(module -> {
/*    */             if (((String)entry.getKey()).equalsIgnoreCase(module.getName())) {
/*    */               JsonObject json = (JsonObject)entry.getValue();
/*    */               
/*    */               module.setToggle(json.get("state").getAsBoolean());
/*    */               
/*    */               module.setKey(json.get("bind").getAsInt());
/*    */               
/*    */               JsonObject values = json.get("values").getAsJsonObject();
/*    */               
/*    */               for (Map.Entry<String, JsonElement> value : (Iterable<Map.Entry<String, JsonElement>>)values.entrySet()) {
/*    */                 if (module.getValueByName(value.getKey()) != null)
/*    */                   try {
/*    */                     Value v = module.getValueByName(value.getKey());
/*    */                     if (v instanceof BooleanValue) {
/*    */                       ((BooleanValue)v).setValue(Boolean.valueOf(((JsonElement)value.getValue()).getAsBoolean()));
/*    */                     }
/*    */                     if (v instanceof ModeValue) {
/*    */                       ((ModeValue)v).setIndex(Integer.valueOf(((JsonElement)value.getValue()).getAsInt()));
/*    */                     }
/*    */                     if (v instanceof NumberValue) {
/*    */                       if (((NumberValue)v).getMinimum() instanceof Integer) {
/*    */                         v.setValue(Integer.valueOf(((JsonElement)value.getValue()).getAsInt()));
/*    */                       }
/*    */                       if (((NumberValue)v).getMinimum() instanceof Float) {
/*    */                         v.setValue(Float.valueOf(((JsonElement)value.getValue()).getAsFloat()));
/*    */                       }
/*    */                       if (((NumberValue)v).getMinimum() instanceof Double) {
/*    */                         v.setValue(Double.valueOf(((JsonElement)value.getValue()).getAsDouble()));
/*    */                       }
/*    */                     } 
/*    */                     if (v instanceof ColorValue) {
/*    */                       v.setValue(new Color(((JsonElement)value.getValue()).getAsInt()));
/*    */                     }
/*    */                     if (v instanceof StringValue) {
/*    */                       v.setValue(((JsonElement)value.getValue()).getAsString());
/*    */                     }
/*    */                     if (v instanceof SubCatagory) {
/*    */                       v.setValue(Boolean.valueOf(((JsonElement)value.getValue()).getAsBoolean()));
/*    */                     }
/* <6 */                   } catch (Exception exception) {} 
/*    */               } 
/*    */             } 
/*    */           }); }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\config\Config.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */