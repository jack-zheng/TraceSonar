package sorra.tracesonar.core;

import java.util.ArrayList;
import java.util.List;

import sorra.tracesonar.model.Method;

public class TreeNode {
  private Method method;
  private boolean isCallingSuper; // self is calling the super method of parent
  private TreeNode parent;
  private int depth;

  public List<TreeNode> callers = new ArrayList<>();

  private String error;

  public TreeNode(Method method, boolean isCallingSuper, TreeNode parent) {
    this.method = method;
    this.isCallingSuper = isCallingSuper;
    this.parent = parent;

    if (parent == null) {
      depth = 0;
    } else {
      depth = parent.depth + 1;
      parent.callers.add(this);
    }
  }

  public Method getMethod() {
    return method;
  }

  public void setMethod(Method method) {
    this.method = method;
  }

  public boolean isCallingSuper() {
    return isCallingSuper;
  }

  public void setCallingSuper(boolean callingSuper) {
    isCallingSuper = callingSuper;
  }

  public List<TreeNode> getCallers() {
    return callers;
  }

  public void setCallers(List<TreeNode> callers) {
    this.callers = callers;
  }

  public int getDepth() {
    return depth;
  }

  public void setDepth(int depth) {
    this.depth = depth;
  }

  boolean findCycle(Method neo) {
    TreeNode cur = this;
    do {
      if (cur.method.equals(neo) || cur.callers.stream().anyMatch(x -> x.method.equals(neo))) {
        return true;
      }
      cur = cur.parent;
    } while (cur != null);

    return false;
  }

  void addCycleEnd(Method caller, boolean asSuper) {
    TreeNode cycleEnd = new TreeNode(caller, asSuper, this);
    callers.add(cycleEnd);
  }

  boolean hasError() {
    return error != null;
  }

  String getError() {
    return error;
  }

  void setError(String error) {
    this.error = error;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    TreeNode cur = this;
    do {
      sb.append(cur.depth);
      for (int i = 0; i < cur.depth; i++) {
        sb.append("  ");
      }
      sb.append(cur.method).append('\n');
      cur = cur.parent;
    } while (cur != null);

    return sb.toString();
  }
}
