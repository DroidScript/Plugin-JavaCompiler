
//Add CreateJavaCompiler method to global app object.
app.CreateJavaCompiler = function( options ) {
	return new CreateJavaCompiler( options );
}

//Plugin wrapper object.
function CreateJavaCompiler( options )
{
    const plg = _CreatePlugin( "com.candlelight.javacompiler.plugins.user.JavaCompiler", options );
    
    const id = plg.CreateObj("JavaCompiler", options);
    let inst;
	
    if( id ) inst = new SObj(id, "jac");
    else throw Error("CreateJavaCompiler: Something going wrong.");
    
    const send = (name, p1) => plg.SendObj(name, inst, p1);
    
    inst.GetVersion = () => parseFloat( plg.Send("getVersion") );
    inst.SetOptions = opt => send("setOptions", opt.join(","));
    inst.AddJavaFile = path => send("addJavaFile", app.RealPath(path));
    inst.AddPlatformJarFile = path => send("addPlatformJarFile", app.RealPath(path));
    inst.AddJarFile = path => send("addJarFile", app.RealPath(path));
    inst.SetOutputFolder = path => send("setOutputFolder", app.RealPath(path));
    inst.Compile = () => send("compile");// === "true";
    inst.GenerateJarFile = () => send("generateJarFile");
    inst.GetDiagnosticMessages = () => send("getDiagnosticMessages");
    
    return inst;
}