package sorra.tracesonar.model;

/**
 * Represents a method identifier in class files
 */
public class Method {
  // class name of method's owner, sample as: sorra.tracesonar.model.Method
  public String owner;
  public String methodName;
  // String format of Type in ASM lib, sample as: (Ljava/lang/String;)V
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
    return result;
  }

  @Override
  public String toString() {
    return "Method{" +
            "owner='" + owner + '\'' +
            ", methodName='" + methodName + '\'' +
            ", desc='" + desc + '\'' +
            '}';
  }
}
