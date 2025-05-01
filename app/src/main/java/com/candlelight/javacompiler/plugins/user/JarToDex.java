package com.candlelight.javacompiler.plugins.user;

import android.util.Log;

import com.xiaoyv.dx.command.Main;

import java.nio.file.Paths;

public class JarToDex {
    public String outputPath;

    String startConversion(String jarPath) {
        String outFile = Paths.get(this.outputPath)
                .resolve("classes.dex")
                .toString();
        Log.e("JavaCompiler", "OKU:" + outFile);
        String[] args = {
            "--dex",
            "--no-strict",
            "--min-sdk-version=26",
            "--output=" + outFile,
            jarPath
        };
        Log.e("JavaCompiler", "OKU:" + String.join(",", args));

        try {
            Main.main(args);
        } catch (Exception e) {
            return "Error: " + e.getClass().getSimpleName() + " - " + e.getMessage();
        }

        return "";
    }
}
