//import
var System_Package_Name = "com.github.andyshao.system";
var HelpTask = Java.type(System_Package_Name + ".HelpTask");
var InfoTask = Java.type(System_Package_Name + ".InfoTask");
var JvmTask = Java.type(System_Package_Name + ".JvmTask");
var NoArgumentTask = Java.type(System_Package_Name + ".NoArgumentTask");
var NoMatchTask = Java.type(System_Package_Name + ".NoMatchTask");
var SystemPropertiesTask = Java.type(System_Package_Name + ".SystemPropertiesTask");
var VersionTask = Java.type(System_Package_Name + ".VersionTask");

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
tail = setNextTask(tail, new NoMatchTask());