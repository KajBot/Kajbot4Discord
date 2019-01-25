package support.kajstech.kajbot.utils;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Collections;

public class ClassHelper<C> {
    public static String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return name.substring(lastIndexOf);
    }

    private File compileClass(File clazz) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(Collections.singleton(clazz));
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);
        task.call();
        fileManager.close();

        return new File(clazz.getAbsolutePath().replace(".java", ".class"));
    }

    public C loadClass(File classPath, Class<C> parentClass, boolean compile) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        URL url = classPath.getParentFile().toURI().toURL();
        URL[] urls = new URL[]{url};
        if (!compile)
            return new URLClassLoader(urls).loadClass(classPath.getName().replace(".class", "")).asSubclass(parentClass).getDeclaredConstructor().newInstance();

        return new URLClassLoader(urls).loadClass(compileClass(classPath).getName().replace(".class", "")).asSubclass(parentClass).getDeclaredConstructor().newInstance();
    }
}
