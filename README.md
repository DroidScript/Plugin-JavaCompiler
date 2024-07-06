# DroidScript Java Compiler

Runtime Java compiler plugin for DroidScript.

> [!NOTE]
> Minimum required Android version: 8 (SDK 26).

## How to use

After installing the plugin, you can use the example in the documentation to get started.

## Build it

This library is mandatory: [nb-javac-android](https://github.com/cemalgnlts/nb-javac-android).



Open and compile the project with [AndroidIDE](https://github.com/AndroidIDEOfficial) or Android Studio.

### Install with shell commands

It can be imported into DroidScript with this example:

```sh
cd app/build/outputs/apk/debug
rm -r temp
unzip app-debug.apk -d temp
cd temp
zip JavaCompiler.jar classes*.dex
rm classes.dex
cp -r assets/* .
cp -r lib/* .
rm -r lib
rm -r assets
rm -r res
rm AndroidManifest.xml
rm resources.arsc
rm -r META-INF
zip -r ../JavaCompiler.ppk *.

cd ..
export PATH=$PATH:~/Library/Android/sdk/platform-tools/
adb push JavaCompiler.ppk /sdcard/Android/data/com.smartphoneremote.androidscriptfree/files/DroidScript/Plugins/JavaCompiler.ppk
```

### Install manually

* Unzip the APK.
* Add the `classes.dex` files to a zip file named `JavaCompiler.jar`.
* Then add the `JavaCompiler.jar` file and the contents of the `assets` folder to a zip file named `JavaCompiler.ppk`.
* Open `JavaCompiler.ppk` with DroidScript.

The `JavaCompiler.ppk` file should look like this
```
JavaCompiler.ppk/
    - JavaCompiler.jar
    - JavaCompiler.html
    - JavaCompiler.inc
    - android.jar
```

## Documentation

You can check this repo to create the document: [DroidScript/Docs](https://github.com/DroidScript/Docs). The code for the current document is in [document.js](document.js). After building the document you can drop it into the [assets](./app/src/main/assets) folder.