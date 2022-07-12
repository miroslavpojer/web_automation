package automation.demo.driver.factories;

import automation.demo.driver.annotations.WaitUntilPresent;
import automation.demo.driver.Configuration;
import automation.demo.driver.driver.DriverManager;
import automation.demo.driver.objects.Component;
import automation.demo.driver.objects.PageObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class PageFactory {

    public static <T extends PageObject> T createObject(DriverManager driver, T... klass) {
        T object;
        Constructor<T> constructor;
        try {
            constructor = (Constructor<T>) klass.getClass().getComponentType().getConstructor(DriverManager.class);
            object = constructor.newInstance(driver);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return object;
    }

    public static void processPageObjects(PageObject pageObject, DriverManager driverManager) {
        processObjects(pageObject, driverManager);
    }

    public static void processComponents(Component component, DriverManager driverManager) {
        processObjects(component, driverManager);
    }

    private static void processObjects(Object object, DriverManager driverManager) {
        Class<?> klass = object.getClass();
        // process class level annotations
        if (Component.class.isAssignableFrom(klass)) {
            processComponentAnnotations((Component) object, klass.getDeclaredAnnotations());
        }
        // process current page object klass fields
        processFields(klass.getDeclaredFields(), object, driverManager);
        // go through all classes in hierarchy from current class to parent
        while ((klass = klass.getSuperclass()) != PageObject.class && klass != Component.class) {
            // process page object class fields
            processFields(klass.getDeclaredFields(), object, driverManager);
        }
    }

    private static void processFields(Field[] fields, Object owner, DriverManager driverManager) {
        Arrays.stream(fields)
              // filter only fields of type Component
              .filter(field -> Component.class.isAssignableFrom(field.getType()))
              .forEach(field -> {
                  // create new instance of field
                  Component component = createComponent(field, driverManager);
                  // set object instance into variable declaration in class
                  field.setAccessible(true);
                  try {
                      field.set(owner, component);
                  } catch (IllegalAccessException e) {
                      throw new RuntimeException("Could not set new instance for component field.", e);
                  }
              });
    }

    private static <R extends Component> R createComponent(Field field, DriverManager driverManager) throws ClassCastException {
        R component;
        try {
            component = (R) field.getType().asSubclass(Component.class).getDeclaredConstructor(DriverManager.class).newInstance(driverManager);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Exception while creating component field.", e);
        }
        // process fields annotations
        processComponentAnnotations(component, field.getDeclaredAnnotations());
        return component;
    }

    private static void processComponentAnnotations(Component component, Annotation[] annotations) {
        Map<Class<? extends Annotation>, Annotation> annotationMap = Arrays.stream(annotations)
                                                                           .collect(Collectors.toMap(Annotation::annotationType, annotation -> annotation));

        Configuration.ApplicationType applicationType = Configuration.getApplicationType();
        applicationType.processAnnotations(annotationMap, component);

        processWaitUntilPresentAnnotation(annotationMap.get(WaitUntilPresent.class), component);
    }

    private static void processWaitUntilPresentAnnotation(Annotation waitUntilPresent, Component component) {
        if (waitUntilPresent != null) {
            component.findComponent();
        }
    }
}
