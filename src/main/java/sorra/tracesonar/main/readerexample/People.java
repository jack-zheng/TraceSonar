package sorra.tracesonar.main.readerexample;

import com.sun.istack.internal.NotNull;
import org.apache.poi.util.Removal;

@SuppressWarnings("")
@Deprecated
@Removal
public class People implements Mammal {
    private String name = "Jack";
    private int age;
    private int weight = 12;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    class Shoes {
    }

    public static void main(String[] args) {
        People p = new People();
    }

    public void throwE() throws NumberFormatException {}
}
