package org.yaml.snakeyaml.inspector;

import org.yaml.snakeyaml.nodes.Tag;

public interface TagInspector {
  boolean isGlobalTagAllowed(Tag paramTag);
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\org\yaml\snakeyaml\inspector\TagInspector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */