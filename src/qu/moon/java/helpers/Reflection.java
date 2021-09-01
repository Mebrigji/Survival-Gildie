package qu.moon.java.helpers;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Reflection {
    private static String OBC_PREFIX = Bukkit.getServer().getClass().getPackage().getName();
    private static String NMS_PREFIX;
    private static String VERSION;
    private static Pattern MATCH_VARIABLE;

    public Reflection() {
    }
    public static Class<?> getCraftClass(String name) {
        String className = "net.minecraft.server." + getVersion() + name;
        Class c = null;

        try {
            c = Class.forName(className);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return c;
    }

    public static Class<?> getBukkitClass(String name) {
        String className = "org.bukkit.craftbukkit." + getVersion() + name;
        Class c = null;

        try {
            c = Class.forName(className);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return c;
    }

    public static Object getHandle(Entity entity) {
        try {
            return getMethod(entity.getClass(), "getHandle").invoke(entity);
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static Object getHandle(World world) {
        try {
            return getMethod(world.getClass(), "getHandle").invoke(world);
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static Field getPrivateField(Class<?> cl, String field_name) {
        try {
            Field field = cl.getDeclaredField(field_name);
            field.setAccessible(true);
            return field;
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static boolean setValue(Field f, Object o, Object v) {
        try {
            f.setAccessible(true);
            f.set(o, v);
            f.setAccessible(false);
            return true;
        } catch (Exception var4) {
            var4.printStackTrace();
            return false;
        }
    }


    public static Method getMethod(Class<?> cl, String method) {
        Method[] methods;
        int length = (methods = cl.getMethods()).length;

        for(int i = 0; i < length; ++i) {
            Method m = methods[i];
            if (m.getName().equals(method)) {
                return m;
            }
        }

        return null;
    }

    public static boolean classListEqual(Class<?>[] l1, Class<?>[] l2) {
        boolean equal = true;
        if (l1.length != l2.length) {
            return false;
        } else {
            for(int i = 0; i < l1.length; ++i) {
                if (l1[i] != l2[i]) {
                    equal = false;
                    break;
                }
            }

            return equal;
        }
    }

    public static String getVersion() {
        String name = Bukkit.getServer().getClass().getPackage().getName();
        return String.valueOf(name.substring(name.lastIndexOf(46) + 1)) + ".";
    }
    public static <T> FieldAccessor<T> getSimpleField(Class target, String name) {
        return getField(target, name);
    }

    public static <T> FieldAccessor<T> getField(Class target, String name, Class<T> fieldType) {
        return getField(target, name, fieldType, 0);
    }

    public static <T> FieldAccessor<T> getField(String className, String name, Class<T> fieldType) {
        return getField(getClass(className), name, fieldType, 0);
    }

    public static <T> FieldAccessor<T> getField(Class target, Class<T> fieldType, int index) {
        return getField(target, (String)null, fieldType, index);
    }

    public static <T> FieldAccessor<T> getField(String className, Class<T> fieldType, int index) {
        return getField(getClass(className), fieldType, index);
    }

    private static <T> FieldAccessor<T> getField(Class target, String name, Class<T> fieldType, int index) {
        Field[] var7;
        int var6 = (var7 = target.getDeclaredFields()).length;

        for(int var5 = 0; var5 < var6; ++var5) {
            final Field field = var7[var5];
            if ((name == null || field.getName().equals(name)) && fieldType.isAssignableFrom(field.getType()) && index-- <= 0) {
                field.setAccessible(true);
                return new FieldAccessor<T>() {
                    public T get(Object target) {
                        try {
                            return (T)field.get(target);
                        } catch (IllegalAccessException var3) {
                            throw new RuntimeException("Cannot access reflection.", var3);
                        }
                    }

                    public void set(Object target, Object value) {
                        try {
                            field.set(target, value);
                        } catch (IllegalAccessException var4) {
                            throw new RuntimeException("Cannot access reflection.", var4);
                        }
                    }

                    public boolean hasField(Object target) {
                        return field.getDeclaringClass().isAssignableFrom(target.getClass());
                    }
                };
            }
        }

        if (target.getSuperclass() != null) {
            return getField(target.getSuperclass(), name, fieldType, index);
        } else {
            throw new IllegalArgumentException("Cannot find field with type " + fieldType);
        }
    }

    public static <T> FieldAccessor<T> getField(Class target, String name) {
        Field[] var5;
        int var4 = (var5 = target.getDeclaredFields()).length;

        for(int var3 = 0; var3 < var4; ++var3) {
            final Field field = var5[var3];
            if (name == null || field.getName().equals(name)) {
                field.setAccessible(true);
                return new FieldAccessor<T>() {
                    public T get(Object target) {
                        try {
                            return (T)field.get(target);
                        } catch (IllegalAccessException var3) {
                            throw new RuntimeException("Cannot access reflection.", var3);
                        }
                    }

                    public void set(Object target, Object value) {
                        try {
                            field.set(target, value);
                        } catch (IllegalAccessException var4) {
                            throw new RuntimeException("Cannot access reflection.", var4);
                        }
                    }

                    public boolean hasField(Object target) {
                        return field.getDeclaringClass().isAssignableFrom(target.getClass());
                    }
                };
            }
        }

        if (target.getSuperclass() != null) {
            return getField(target.getSuperclass(), name);
        } else {
            throw new IllegalArgumentException("Cannot find field with type");
        }
    }

    public static MethodInvoker getMethod(String className, String methodName, Class... params) {
        return getTypedMethod(getClass(className), methodName, (Class)null, params);
    }

    public static MethodInvoker getMethod(Class clazz, String methodName, Class... params) {
        return getTypedMethod(clazz, methodName, (Class)null, params);
    }

    public static MethodInvoker getTypedMethod(Class clazz, String methodName, Class returnType, Class... params) {
        Method[] var7;
        int var6 = (var7 = clazz.getDeclaredMethods()).length;

        for(int var5 = 0; var5 < var6; ++var5) {
            final Method method = var7[var5];
            if ((methodName == null || method.getName().equals(methodName)) && returnType == null || method.getReturnType().equals(returnType) && Arrays.equals(method.getParameterTypes(), params)) {
                method.setAccessible(true);
                return new MethodInvoker() {
                    public Object invoke(Object target, Object... arguments) {
                        try {
                            return method.invoke(target, arguments);
                        } catch (Exception var4) {
                            throw new RuntimeException("Cannot invoke method " + method, var4);
                        }
                    }
                };
            }
        }

        if (clazz.getSuperclass() != null) {
            return getMethod(clazz.getSuperclass(), methodName, params);
        } else {
            throw new IllegalStateException(String.format("Unable to find method %s (%s).", methodName, Arrays.asList(params)));
        }
    }

    public static ConstructorInvoker getConstructor(String className, Class... params) {
        return getConstructor(getClass(className), params);
    }

    public static ConstructorInvoker getConstructor(Class clazz, Class... params) {
        Constructor[] var5;
        int var4 = (var5 = clazz.getDeclaredConstructors()).length;

        for(int var3 = 0; var3 < var4; ++var3) {
            final Constructor constructor = var5[var3];
            if (Arrays.equals(constructor.getParameterTypes(), params)) {
                constructor.setAccessible(true);
                return new ConstructorInvoker() {
                    public Object invoke(Object... arguments) {
                        try {
                            return constructor.newInstance(arguments);
                        } catch (Exception var3) {
                            throw new RuntimeException("Cannot invoke constructor " + constructor, var3);
                        }
                    }
                };
            }
        }

        throw new IllegalStateException(String.format("Unable to find constructor for %s (%s).", clazz, Arrays.asList(params)));
    }

    public static Class<Object> getUntypedClass(String lookupName) {
        Class<Object> clazz = getClass(lookupName);
        return clazz;
    }

    public static Class getClass(String lookupName) {
        return getCanonicalClass(expandVariables(lookupName));
    }

    public static Class getMinecraftClass(String name) {
        return getCanonicalClass(NMS_PREFIX + "." + name);
    }

    public static Class getCraftBukkitClass(String name) {
        return getCanonicalClass(OBC_PREFIX + "." + name);
    }

    private static Class getCanonicalClass(String canonicalName) {
        try {
            return Class.forName(canonicalName);
        } catch (ClassNotFoundException var2) {
            throw new IllegalArgumentException("Cannot find " + canonicalName, var2);
        }
    }

    private static String expandVariables(String name) {
        StringBuffer output = new StringBuffer();

        Matcher matcher;
        String replacement;
        for(matcher = MATCH_VARIABLE.matcher(name); matcher.find(); matcher.appendReplacement(output, Matcher.quoteReplacement(replacement))) {
            String variable = matcher.group(1);
            replacement = "";
            if ("nms".equalsIgnoreCase(variable)) {
                replacement = NMS_PREFIX;
            } else if ("obc".equalsIgnoreCase(variable)) {
                replacement = OBC_PREFIX;
            } else {
                if (!"version".equalsIgnoreCase(variable)) {
                    throw new IllegalArgumentException("Unknown variable: " + variable);
                }

                replacement = VERSION;
            }

            if (replacement.length() > 0 && matcher.end() < name.length() && name.charAt(matcher.end()) != '.') {
                replacement = replacement + ".";
            }
        }

        matcher.appendTail(output);
        return output.toString();
    }

    static {
        NMS_PREFIX = OBC_PREFIX.replace("org.bukkit.craftbukkit", "net.minecraft.server");
        VERSION = OBC_PREFIX.replace("org.bukkit.craftbukkit", "").replace(".", "");
        MATCH_VARIABLE = Pattern.compile("\\{([^\\}]+)\\}");
    }

    public interface ConstructorInvoker {
        Object invoke(Object... var1);
    }
    public static FieldAccessor<Float> durabilityField = Reflection.getField(Reflection.getMinecraftClass("Block"), "durability", (Class)float.class);
    public interface FieldAccessor<T> {
        T get(Object var1);

        void set(Object var1, Object var2);

        boolean hasField(Object var1);
    }

    public interface MethodInvoker {
        Object invoke(Object var1, Object... var2);
    }

    public static void changeDurability(String name, float durability) {
        FieldAccessor<Object> f = Reflection.getSimpleField(Reflection.getMinecraftClass("Blocks"), name.toUpperCase());
        durabilityField.set(f.get(null), durability);
    }
}
