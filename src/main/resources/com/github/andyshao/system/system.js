//import
var System_Package_Name = "com.github.andyshao.system";
var HelpTask = Java.type(System_Package_Name + ".HelpTask");
var InfoTask = Java.type(System_Package_Name + ".InfoTask");
var JvmTask = Java.type(System_Package_Name + ".JvmTask");
var NoArgumentTask = Java.type(System_Package_Name + ".NoArgumentTask");
var NoMatchTask = Java.type(System_Package_Name + ".NoMatchTask");
var SystemPropertiesTask = Java.type(System_Package_Name + ".SystemPropertiesTask");
var VersionTask = Java.type(System_Package_Name + ".VersionTask");
var CleanJavadocTask = Java.type(System_Package_Name + ".CleanJavadocTask");
var HttpTask = Java.type(System_Package_Name + ".HttpTask");
var PrintChar = Java.type(System_Package_Name + ".PrintChar");
var SearchTask = Java.type(System_Package_Name + ".SearchTask");
var ClassAnalysis = Java.type(System_Package_Name + ".ClassAnalysis");
var EnvironmentTask = Java.type(System_Package_Name + ".EnvironmentTask");

//setNextTask
function setNextTask(head, tail){
	head.setNextTask(tail);
	return tail;
}

//main
var head = new NoArgumentTask();
var tail = setNextTask(head, new HelpTask());
tail = setNextTask(tail, new InfoTask());
tail = setNextTask(tail, new VersionTask());
tail = setNextTask(tail, new SystemPropertiesTask());
tail = setNextTask(tail, new JvmTask());
tail = setNextTask(tail, new CleanJavadocTask());
tail = setNextTask(tail, new HttpTask());
tail = setNextTask(tail, new PrintChar());
tail = setNextTask(tail, new SearchTask());
tail = setNextTask(tail, new ClassAnalysis());
tail = setNextTask(tail, new EnvironmentTask());
tail = setNextTask(tail, new NoMatchTask());
tail.setNextTask(head);