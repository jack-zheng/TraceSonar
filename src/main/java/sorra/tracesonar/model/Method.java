package sorra.tracesonar.model;

/**
 * Represents a method identifier in class files
 */
public class Method {
  public String owner;
  public String methodName;
  public String desc;

  public Method(String owner, String methodName, String desc) {
    this.owner = owner;
    this.methodName = methodName;
    this.desc = desc;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public String getMethodName() {
    return methodName;
  }

  public void setMethodName(String methodName) {
    this.methodName = methodName;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Method caller = (Method) o;

    return methodName.equals(caller.methodName) && owner.equals(caller.owner) && desc.equals(caller.desc);
  }

  @Override
  public int hashCode() {
    int result = owner.hashCode();
    result = 31 * result + methodName.hashCode();
    // result = 31 * result + desc.hashCode();
    return result;
  }

//  @Override
//  public String toString() {
//    String name = methodName;
//    if (methodName.contains("<") || methodName.contains(">")) {
//      name = methodName.replace("<", "&lt;").replace(">", "&gt;");
//    }
//
//    String desc = this.desc.replace("Ljava/lang/", "");
//
//    return String.format("<- %s #%s %s", owner.replace('/', '.'), name, desc);
//  }

  @Override
  public String toString() {
    return String.format("%s #%s %s", owner.replace('/', '.'), methodName, desc);
  }
}
