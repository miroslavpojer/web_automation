package automation.demo.driver;

@FunctionalInterface
public interface FindComponent<T> {

    T find();
}
