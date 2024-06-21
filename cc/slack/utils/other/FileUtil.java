/*    */ package cc.slack.utils.other;
/*    */ import cc.slack.utils.client.mc;
/*    */ import com.google.gson.GsonBuilder;
/*    */ import com.google.gson.JsonObject;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.FileWriter;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class FileUtil extends mc {
/* 14 */   public static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
/*    */   
/*    */   public static JsonObject readJsonFromFile(String path) {
/*    */     try {
/* 18 */       return (JsonObject)GSON.fromJson(new FileReader(path), JsonObject.class);
/* 19 */     } catch (Exception e) {
/* 20 */       e.printStackTrace();
/*    */       
/* 22 */       return null;
/*    */     } 
/*    */   }
/*    */   public static JsonObject readJsonFromResourceLocation(ResourceLocation resourceLocation) {
/* 26 */     try(InputStream inputStream = Minecraft.getMinecraft().getResourceManager().getResource(resourceLocation).getInputStream(); 
/* 27 */         BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
/* 28 */       StringBuilder stringBuilder = new StringBuilder();
/*    */       String line;
/* 30 */       while ((line = reader.readLine()) != null) {
/* 31 */         stringBuilder.append(line).append('\n');
/*    */       }
/* 33 */       return (JsonObject)GSON.fromJson(stringBuilder.toString(), JsonObject.class);
/* 34 */     } catch (IOException e) {
/* 35 */       e.printStackTrace();
/*    */       
/* 37 */       return null;
/*    */     } 
/*    */   }
/*    */   public static void writeJsonToFile(JsonObject json, String path) {
/*    */     try {
/* 42 */       FileWriter writer = new FileWriter(path);
/* 43 */       GSON.toJson((JsonElement)json, writer);
/* 44 */       writer.close();
/* 45 */     } catch (Exception e) {
/* 46 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   public static String readInputStream(InputStream inputStream) {
/* 51 */     StringBuilder stringBuilder = new StringBuilder();
/*    */     
/*    */     try {
/* 54 */       BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
/*    */       String line;
/* 56 */       while ((line = bufferedReader.readLine()) != null) {
/* 57 */         stringBuilder.append(line).append('\n');
/*    */       }
/* 59 */     } catch (Exception e) {
/* 60 */       e.printStackTrace();
/*    */     } 
/* 62 */     return stringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\utils\other\FileUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */