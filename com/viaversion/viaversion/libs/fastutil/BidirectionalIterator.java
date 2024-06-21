package com.viaversion.viaversion.libs.fastutil;

import java.util.Iterator;

public interface BidirectionalIterator<K> extends Iterator<K> {
  K previous();
  
  boolean hasPrevious();
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\fastutil\BidirectionalIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */