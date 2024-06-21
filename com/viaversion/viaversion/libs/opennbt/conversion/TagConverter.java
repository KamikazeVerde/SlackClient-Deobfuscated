package com.viaversion.viaversion.libs.opennbt.conversion;

public interface TagConverter<T extends com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag, V> {
  V convert(T paramT);
  
  T convert(V paramV);
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\opennbt\conversion\TagConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */