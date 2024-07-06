// ------------- HEADER SECTION -------------


/** # CreateJavaCompiler #
 * @abbrev jac
 * @brief Creates a Java compiler object
 *
 * $$ jac = app.CreateJavaCompiler(options) $$
 * @param {str_com} options NoDefJar:Prevents the default android\.jar file from being used.
 * @returns dso-JavaCompiler
*/

// ------------- LONG DESCRIPTION ------------- 

/** @description
This plugin, compiles Java files at runtime and can include the result in the jar package.

**Note**: The jar package you create is saved in the output folder with the name “classes.jar”.
When you create a jar package, all files in the output folder are included in the package. Make sure the output folder is empty.
It is better to use a temporary folder with <js>app.GetTempFolder</js> as output folder. At the end of the process you can get the jar package with <js>app.CopyFile</js>.
Delete the temporary folder at the end of the process.
*/

// ------------- VISIBLE METHODS & PROPERTIES ------------- 

/** ### SetOptions ###
 * Compilation flags.
 * $$ jac.SetOptions(options) $$
 * @param {lst-[ args:str ]} options
 */

/** ### AddJavaFile ###
 * Add Java file.
 * $$ jac.AddJavaFile(path) $$
 * @param {str_ptf} path
 */

/** ### AddJarFile ###
 * Add the classes from the Jar package.
 * $$ jac.AddJarFile(path) $$
 * @param {str_ptf} path
 */

/** ### AddPlatformJarFile ###
 * Packages containing system classes such as android.jar.
 * $$ jac.AddPlatformJarFile(path) $$
 * @param {str_ptf} path
 */

/** ### SetOutputFolder ###
 * $$ jac.SetOutputFolder(path) $$
 * Output folder for adding packages created during compilation.
 * @param {str_ptd} path
 */

/** ### Compile ###
 * @brief Start the compile process.
 * Starts the compilation process. Returns true if the operation is successful.
 * $$ jac.Compile() $$
 * @returns bin
 */

/** ### GenerateJarFile ###
 * It adds every file in the output folder to the jar package and it is added to the output folder with the name classes.jar.
 * $$ jac.GenerateJarFile() $$
 * @returns bin
 */

/** ### GetDiagnosticMessages ###
 * Log records generated during compilation.
 * $$ jac.GetDiagnosticMessages() $$
 * @returns str
 */


// ------------- SAMPLES ------------- 

/**
@sample Basic
app.LoadPlugin("JavaCompiler")

function OnStart()
{
    // Create a temporary folder to hold the output files.
    // Using device folders may result in unwanted files.
    const outputFolder = app.GetTempFolder() + "/classes"
    app.MakeFolder(outputFolder)

    const jac = app.CreateJavaCompiler()
    // Path to the Java file
    jac.AddJavaFile("App.java")
    jac.SetOutputFolder(outputFolder)

    const res = jac.Compile()

     // If the operation is successful.
    if(res === true) {
        // Create a jar package without deleting the resulting .class files.
        jac.GenerateJarFile()

        // Copy the generated jar file to the project folder.
        app.CopyFile(outputFolder + "/classes.jar", "classes.jar")

        app.Quit(app.RealPath("classes.jar"), "Path to the generated jar package")
    } else {
        // Show messages when the operation fails.
        app.Quit(jac.GetDiagnosticMessages(), "Error")
    }

    // Now we can clean up the output folder.
    app.DeleteFolder(outputFolder)
}
 */

