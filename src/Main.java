import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {

    public static void main(String[] args) throws Exception {


        Constructor constructor = String.class.getConstructor(StringBuffer.class);

/*//            传了一个String，实际上应该使用StringBuffer来和上面的代码匹配，因为类型不匹配，运行时会报错
//            String str1 =  (String)constructor.newInstance(("abc"));*/

        String str1 = (String) constructor.newInstance(new StringBuffer("abc"));
        System.out.println(str1.charAt(2));
        System.out.println(constructor.getDeclaringClass());


        ReflectPoint reflectPoint = new ReflectPoint(3, 5);
        Field fieldY = reflectPoint.getClass().getField("y");

        System.out.println(fieldY);
        System.out.println(fieldY.get(reflectPoint));

        //对private成员变量进行暴力反射
        Field fieldX = reflectPoint.getClass().getDeclaredField("x");
        fieldX.setAccessible(true);
        System.out.println(fieldX.get(reflectPoint));
        changeStringValue(reflectPoint);


        System.out.println("=============");
        //TestArgument.main(new String[]{"ddd","aa","ggg"});
        String className = args[0];
        Method mainMethod = Class.forName(className).getMethod("main",String[].class);
        mainMethod.invoke(null , new Object[]{new String[]{"ddd","aa","ggg"}});

    }

    private static void changeStringValue(Object object) throws Exception {
        Field[] fields = object.getClass().getFields();
        System.out.println("=========改变前============");
        for (Field field: fields){
            System.out.println(field.get(object));

        }


        for (Field field : fields){

            //字节码只有一份，不需要使用equals
            //field.getType().equals(String.class);

            if (field.getType() == String.class){
                String oldString = (String)field.get(object);
                String newString = oldString.replace("b" , "x");
                field.set(object,newString);
            }

        }

        System.out.println("===========改变后============= ");
        for (Field field: fields){
            System.out.println(field.get(object));

        }

    }


}
class TestArgument{
    public static void main(String args[]){
        for (String arg : args){
            System.out.println(arg);
        }
    }
}