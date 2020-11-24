package sorra.tracesonar.model;

import org.objectweb.asm.Type;

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

  /**
   * Transfer method string from Type format to java format.
   * expected format: void className.methodName(String, int, ...)
   *
   * @return
   */
  public String formatString() {
    String returnStr = removePathFromObjectType(Type.getType(desc).getReturnType().getClassName());
    Type[] paramsTypes = Type.getType(desc).getArgumentTypes();
    StringBuilder paramsStr = new StringBuilder();
    if (paramsTypes.length != 0) {
      for (Type paramsType : paramsTypes) {
        String cName = paramsType.getClassName();
        paramsStr.append(removePathFromObjectType(cName));
      }
    }
    String className = removePathFromObjectType(owner);

    return String.format("%s %s.%s(%s)", returnStr, className, methodName, paramsStr);
  }

  /**
   * Remove prefix path from class.
   * before: Lcom/lang/Object or com.lang.Object
   * after: Object
   */
  private String removePathFromObjectType(String className) {
    if (className.contains("/")) {
      return className.substring(className.lastIndexOf("/") + 1);
    }

    if (className.contains(".")) {
      return className.substring(className.lastIndexOf(".") + 1);
    }
    return className;
  }
}
