package mod.tyron.compiler;

import android.util.Log;

import com.besome.sketch.SketchApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import a.a.a.Jp;
import a.a.a.KB;
import a.a.a.Kp;
import a.a.a.oB;
import a.a.a.yq;
import mod.agus.jcoderz.editor.library.ExtLibSelected;
import mod.agus.jcoderz.editor.manage.library.locallibrary.ManageLocalLibrary;
import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.build.BuildSettings;
import mod.tyron.compiler.file.JavaFile;

import static com.besome.sketch.SketchApplication.getContext;

public class IncrementalJavaCompiler extends Compiler {

    public static final String TAG = IncrementalJavaCompiler.class.getSimpleName();

    private final String SAVE_PATH;

    private final StringBuffer errorBuffer = new StringBuffer();

    private final FilePathUtil filePathUtil = new FilePathUtil();

    private final yq projectConfig;
    private final BuildSettings buildSettings;
    private final ManageLocalLibrary mll;

    private Compiler.Result onResultListener;

    private final ArrayList<String> builtInLibraries = new ArrayList<>();

    private final oB g;
    private final File l;

    public IncrementalJavaCompiler(yq projectConfig) {
        SAVE_PATH = FileUtil.getExternalStorageDir() + "/.sketchware/mysc/" + projectConfig.b + "/incremental";

        this.projectConfig = projectConfig;
        this.buildSettings = new BuildSettings(projectConfig.b);
        this.mll = new ManageLocalLibrary(projectConfig.b);
        this.g = new oB(false);
        this.l = new File(getContext().getFilesDir(), "libs");

    }

    public void setOnResultListener(Compiler.Result result) {
        onResultListener = result;
    }

    @Override
    public ArrayList<File> getSourceFiles() {

        ArrayList<JavaFile> oldFiles = findJavaFiles(new File(SAVE_PATH));

        //combine all the java files from sketchware pro
        ArrayList<JavaFile> newFiles = new ArrayList<>(getSketchwareFiles());
        //add original sketchware generated files
        newFiles.addAll(findJavaFiles(projectConfig.c + "/app/src/main/java"));

        //add R.java files
        newFiles.addAll(findJavaFiles(projectConfig.c + "/gen"));

        for (String library : getBuiltInLibraries()) {
            JavaFile rFile = new JavaFile(library + "/R.java");
            if (rFile.exists()) {
                newFiles.add(rFile);
            }
        }

        return new ArrayList<>(getModifiedFiles(oldFiles, newFiles));
    }

    @Override
    public void compile() {



        if (onResultListener == null ) {
            throw new IllegalStateException("No result listeners were set");
        }

        ArrayList<File> projectJavaFiles = getSourceFiles();

        if (projectJavaFiles.isEmpty()) {
            Log.d(TAG, "Java files are up to date, skipping compilation");
            onResultListener.onResult(true, "Files up to date");
            return;
        }

        Log.d(TAG, "Found " + projectJavaFiles.size() + " file(s) that are modified");

        CompilerOutputStream errorOutputStream = new CompilerOutputStream(new StringBuffer());
        PrintWriter errWriter = new PrintWriter(errorOutputStream);

        CompilerOutputStream outputStream = new CompilerOutputStream(new StringBuffer());
        PrintWriter outWriter = new PrintWriter(outputStream);

        ArrayList<String> args = new ArrayList<>();

        args.add("-" + buildSettings.getValue(BuildSettings.SETTING_JAVA_VERSION, BuildSettings.SETTING_JAVA_VERSION_1_7));
        args.add("-nowarn");
        if (!buildSettings.getValue(BuildSettings.SETTING_NO_WARNINGS, "false").equals("true")) {
            args.add("-deprecation");
        }
        args.add("-d");
        args.add(SAVE_PATH + "/classes");
        args.add("-proc:none");
        args.add("-cp");

        args.add(l.getAbsolutePath() + "/jdk/rt.jar:" + l.getAbsolutePath() + "/android.jar" +
                getLibrariesJarFile());

        for (File file : projectJavaFiles) {
            args.add(file.getAbsolutePath());
        }


        args.add("-sourcepath");
        args.add(SAVE_PATH + "/java");

        org.eclipse.jdt.internal.compiler.batch.Main main = new org.eclipse.jdt.internal.compiler.batch.Main(outWriter, errWriter, false, null, null);

        Log.d(TAG, "Started compilation");
        main.compile(args.toArray(new String[0]));
        boolean success = true;

        if (main.globalErrorsCount > 0) {
            success = false;
            onResultListener.onResult(false, errorOutputStream.buffer.toString());
        }

        try {
            errorOutputStream.close();
            outputStream.close();
            errWriter.close();
            outWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //merge the classes to the non modified classes so that we can compare later
        if(success) {
            Log.d(TAG, "Merging modified java files");
            mergeClasses(projectJavaFiles);

            onResultListener.onResult(true, "Success");
        }

    }

    /**
     * Gets all Java source code files, that Sketchware Pro has generated.
     */
    private ArrayList<JavaFile> getSketchwareFiles() {
        ArrayList<JavaFile> arrayList = new ArrayList<>();

        if (FileUtil.isExistFile(filePathUtil.getPathJava(projectConfig.b))) {
            arrayList.addAll(findJavaFiles(filePathUtil.getPathJava(projectConfig.b)));
        }

        if (FileUtil.isExistFile(filePathUtil.getPathBroadcast(projectConfig.b))) {
            arrayList.addAll(findJavaFiles(filePathUtil.getPathBroadcast(projectConfig.b)));
        }

        if (FileUtil.isExistFile(filePathUtil.getPathService(projectConfig.b))) {
            arrayList.addAll(findJavaFiles(filePathUtil.getPathService(projectConfig.b)));
        }

        return arrayList;
    }

    /**
     * Finds all Java source code files in a given directory.
     *
     * @param input input directory
     * @return returns a list of java files
     */
    private ArrayList<JavaFile> findJavaFiles(File input) {

        ArrayList<JavaFile> foundFiles = new ArrayList<>();

        if (input.isDirectory()) {
            File[] contents = input.listFiles();
            if (contents != null) {
                for (File child : contents) {
                    foundFiles.addAll(findJavaFiles(child));
                }
            }
        } else {
            if (input.getName().endsWith(".java")) {
                foundFiles.add(new JavaFile(input.getPath()));
            }
        }
        return foundFiles;
    }

    /**
     * Convenience method for {@link IncrementalJavaCompiler#findJavaFiles(File)} with Strings.
     */
    private ArrayList<JavaFile> findJavaFiles(String input) {
        return findJavaFiles(new File(input));
    }

    /**
     * Compares two list of Java source code files, and outputs ones, that are modified.
     */
    private ArrayList<File> getModifiedFiles(ArrayList<JavaFile> oldFiles, ArrayList<JavaFile> newFiles) {
        ArrayList<File> modifiedFiles = new ArrayList<>();

        for (JavaFile newFile : newFiles) {
            if (!oldFiles.contains(newFile)) {
                modifiedFiles.add(newFile);
            } else {
                File oldFile = oldFiles.get(oldFiles.indexOf(newFile));
                if (contentModified(oldFile, newFile)) {
                    modifiedFiles.add(newFile);
                    if(oldFile.delete()) {
                        Log.d(TAG, oldFile.getName() + ": Removed old class file that has been modified");
                    }
                }
                oldFiles.remove(oldFile);
            }
        }
        //we delete the removed classes from the original path
        for (JavaFile removedFile : oldFiles) {
            Log.d(TAG, "Class no longer exists, deleting file: " + removedFile.getName());
            if (!removedFile.delete()) {
                Log.w(TAG, "Failed to delete file " + removedFile.getAbsolutePath());
            } else {
                String name = removedFile.getName().substring(0, removedFile.getName().indexOf("."));
                deleteClassInDir(name, new File(SAVE_PATH + "/classes"));
            }
        }
        return modifiedFiles;
    }

    /**
     * merges the modified classes to the non modified files so that we can compare it next compile
     */
    public void mergeClasses(ArrayList<File> files) {
        for (File file : files) {
            String packagePath = SAVE_PATH + "/java/" + getPackageName(file);
            FileUtil.copyFile(file.getAbsolutePath(), packagePath);
        }
    }

    private boolean deleteClassInDir(String name, File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            if (children != null) {
                for (File child : children) {
                    deleteClassInDir(name, child);
                }
            }
        }else {
            String dirName = dir.getName().substring(0, dir.getName().indexOf("."));
            if (dirName.contains("$")) {
                dirName = dirName.substring(0, dirName.indexOf("$"));
            }
            if (dirName.equals(name)) {
                return dir.delete();
            }
        }

        return false;
    }

    /**
     * checks if contents of the file has been modified
     */
    private boolean contentModified(File old, File newFile) {
        if (old.isDirectory() || newFile.isDirectory()) {
            throw new IllegalArgumentException("Given file must be a java file");
        }
        if (!old.exists()) {
            return true;
        }
        if (!newFile.exists()) {
            return false;
        }
        if (newFile.length() > old.length()) {
            return true;
        }else if (newFile.length() == old.length()) {
            return false;
        }
        return newFile.lastModified() > old.lastModified();
    }

    /**
     * Gets the package name of a specific Java source code file.
     *
     * @param file The Java file
     * @return The file's package name, in Java format
     */
    private String getPackageName(File file) {
        String packageName = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            while (!packageName.contains("package")) {
                packageName = reader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (packageName.contains("package")) {

            packageName = packageName.replace("package ", "")
                    .replace(";", ".")
                    .replace(".", "/");

            if(!packageName.endsWith("/")){
                packageName = packageName.concat("/");
            }

            return packageName + file.getName();
        }

        return null;
    }

    private String getLibrariesJarFile() {
        StringBuilder sb = new StringBuilder();

        for (String library : getBuiltInLibraries()) {
            sb.append(":");
            sb.append(library).append("/").append("classes.jar");
        }

        if (buildSettings.getValue(BuildSettings.SETTING_NO_HTTP_LEGACY, "false").equals("false")) {
            sb.append(":");
            sb.append(l.getAbsolutePath());
            sb.append("/");
            sb.append("libs");
            sb.append("/");
            sb.append("http-legacy-android-28");
            sb.append("/");
            sb.append("classes.jar");
        }

        sb.append(mll.getJarLocalLibrary());

        String str = sb.toString();
        return str;
    }
    private static class CompilerOutputStream extends OutputStream {

        public StringBuffer buffer;

        public CompilerOutputStream(StringBuffer buffer) {
            this.buffer = buffer;
        }

        @Override
        public void write(int b)  {
            buffer.append((char) b);
        }
    }

    public ArrayList<String> getBuiltInLibraries() {
    
        String path = getContext().getFilesDir() + "/libs/libs/";
        ArrayList<String> arrayList = new ArrayList<>();
        
        if (projectConfig.N.g) {
            arrayList.add(":" + path + "appcompat-1.0.0");
            arrayList.add(":" + path + "coordinatorlayout-1.0.0");
            arrayList.add(":" + path + "material-1.0.0");
        }
        if (projectConfig.N.h) {
            arrayList.add(":" + path + "firebase-common-19.0.0");
        }
        if (projectConfig.N.i) {
            arrayList.add(":" + path + "firebase-auth-19.0.0");
        }
        if (projectConfig.N.j) {
            arrayList.add(":" + path + "firebase-database-19.0.0");
        }
        if (projectConfig.N.k) {
            arrayList.add(":" + path + "firebase-storage-19.0.0");
        }
        if (projectConfig.N.m) {
            arrayList.add(":" + path + "play-services-maps-17.0.0");
        }
        if (projectConfig.N.l) {
            arrayList.add(":" + path + "play-services-ads-18.2.0");
        }

        if (projectConfig.N.n) {
            arrayList.add(":" + path + "glide-4.11.0");
        }
        if (projectConfig.N.p) {
            arrayList.add(":" + path + "okhttp-3.9.1");
        }
        if (projectConfig.N.o) {
            arrayList.add(":" + path + "gson-2.8.0");
        }
        
        return arrayList;
        
    }

}
