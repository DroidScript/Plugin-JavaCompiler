package com.candlelight.javacompiler.plugins.user;

/*
	DroidScript Plugin class.
	(This is where you put your main plugin code)

	Notes:

	- You can make and install the plugin to DroidScript by running the 'make-plugin.sh' script in
	  the AndroidStudio terminal window (after building an APK)

	- You can't use resources in the project 'res' folder in plugins, so put required images files
	  etc in the top level of the 'assets' folder instead.  They can then be loaded from the
	  directory path stored in the m_plugDir variable at runtime after the plugin is installed.

	- Only top level asset files and those found in the sub-folders 'assets/lib', 'assets/arm64-v8a'
	  and 'assets/armeabi-v7a' are included in APKs built with the APKBuilder.

	 - It is important to have the multiDexEnabled option set to false in your build.gradle file.

	 - The package name for free plugins must end with the string '.user' or a license error will
	   be triggered.  Premium only plugins can have any package name.
*/

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.candlelight.nbja.JavaCompilerHelper;

import java.util.Locale;

import jdkx.tools.Diagnostic;

public class JavaCompiler {
    public static String TAG = "JavaCompiler";
    public static float VERSION = 2.00f;
    private String m_plugDir;
    private JarToDex jarToDex;

    // Construct plugin.
    public JavaCompiler() {
        Log.d(TAG, "Creating plugin object");
    }

    // Initialise plugin.
    public void Init(Context ctx, Object parent) throws Exception {
        Log.d(TAG, "Initialising plugin object");

        // Save context and reference to parent (AndroidScript).
        Context m_ctx = ctx;

        jarToDex = new JarToDex();

        // Use reflection to get parent's method
        Log.d(TAG, "Getting CallScript method");

        // Get the plugin directory path (where your plugin will live when running in an app)
        m_plugDir = m_ctx.getDir("Plugins", 0).getAbsolutePath() + "/javacompiler";
    }

    // Handle commands from DroidScript.
    public String CallPlugin(Bundle b, Object obj) throws Exception {
        // Extract command.
        String cmd = b.getString("cmd");

        // Process commands.
        String ret = null;

        if (cmd.equals("getVersion")) return Float.toString(VERSION);

        JavaCompilerHelper helper = (JavaCompilerHelper) obj;

        String p1 = b.getString("p1");

        try {
            switch (cmd) {
                case "setOptions":
                    helper.setOptions(p1.split(","));
                    break;
                case "addJavaFile":
                    helper.addJavaFile(p1);
                    break;
                case "addPlatformJarFile":
                    helper.addPlatformJarFile(p1);
                    break;
                case "addJarFile":
                    helper.addJarFile(p1);
                    break;
                case "setOutputFolder":
                    helper.setOutputFolder(p1);
                    jarToDex.outputPath = p1;
                    break;
                case "compile":
                    ret = Boolean.toString(helper.compile());
                    break;
                case "generateJarFile":
                    helper.generateJarFile();
                    break;
                case "generateDexFile":
                    ret = jarToDex.startConversion(p1);
                    break;
                case "getDiagnosticMessages":
                    for (Diagnostic diagnostic : helper.getDiagnostics()) {
                        Log.i(TAG, diagnostic.getKind().toString() + diagnostic.getMessage(Locale.getDefault()));
                    }

                    ret = helper.getDiagnosticMessages();
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        return ret;
    }

    // Handle creating object from DroidScript.
    public Object CreateObject(Bundle b) {
        // Process commands.
        JavaCompilerHelper helper = new JavaCompilerHelper();
        helper.setOptions("-proc:none", "-source", "8", "-target", "8");

        if (!b.getString("p1", "").toLowerCase().contains("nodefjar")) {
            helper.addPlatformJarFile(this.m_plugDir + "/android.jar");
        }

        return ((Object) helper);
    }
}
