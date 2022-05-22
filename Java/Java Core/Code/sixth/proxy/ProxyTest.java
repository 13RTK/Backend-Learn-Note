import java.lang.reflect.*;
import java.util.*;

public class ProxyTest {
    public static void main(String[] args) {
        Object[] elements = new Object[1000];

        for (int i = 0; i < elements.length; i++) {
            Integer value = i + 1;
            InvocationHandler handler = new TraceHandler(value);
            Object proxy = Proxy.newProxyInstance(null, new Class[] { Comparable.class }, handler);
            elements[i] = proxy;
        }

        Integer key = new Random().nextInt(elements.length) + 1;

        int result = Arrays.binarySearch(elements, key);

        if (result > 0) {
            System.out.println(elements[result]);
        }

        System.out.println("Is it a proxy class?");
        System.out.println(Proxy.isProxyClass(elements[0].getClass()));
    }
}

class TraceHandler implements InvocationHandler {
    private Object target;

    public TraceHandler(Object t) {
        target = t;
    }

    @Override
    public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
        System.out.println(target);
        System.out.println("." + m.getName() + "(");

        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                System.out.println(args[i]);

                if (i < args.length - 1) {
                    System.out.println(", ");
                }
            }
        }
        System.out.println(")");

        return m.invoke(target, args);
    }
}