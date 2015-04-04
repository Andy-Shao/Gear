Some thing about the useful tools of the **JDK 1.8**.
I find out some API is not very well in Java Core API.
I try to repair it with my experience and shall with you.
If there any problems then send the email to **hj13035952619@126.com**
###############################################################################

* Should has JDK1.8 or up.
* Should has Maven3 or up.

If you want to let it into the eclipse. Try to use the command:
mvn eclipse:eclipse

You could download from the maven repository, now!<br>
&lt;dependency&gt;<br>
	&lt;groupId&gt;com.github.Andy-Shao&lt;/groupId&gt;<br>
	&lt;artifactId&gt;Gear&lt;/artifactId&gt;<br>
	&lt;version&gt;${Gear.Version.Number}&lt;/version&gt;<br>
&lt;/dependency&gt;<br>
###############################################################################

**What the newest plugins for v2.1**

*Add the new operation of int[], byte[], short[], long[], char[]*<br>
you can do the bit operation for them.

*AutoIcreatArray is the subclass of Collection' now*<br>

**What the newest plugins for v2.0**

*rewrite & adjust the old project structure*<br>
ArrayTools move to ArrayOperation<br>
ByteBufferTools move to ByteBufferOperaion<br>
StringTools move to StringOperation<br>
Chang the Convert' package<br>
...

*Add the classic data structure*<br>
You can use the graph or others more difficult data structure, now.

*Add the classic aritmetic*<br>
Graph arithmetic & Bit tree arithmetic<br>
Never add the compress & security arithmetic

**What the newest plugins for v1.0**

*Support JDK8 interface functional*<br>
Adjust the com.github.andyshao.convert.Convert &
com.github.andyshao.proxy.ProxyFactory support the JDK8 interface functional

*Use the interface build ProxyFactory*<br>
Now, you can use the com.github.andyshao.proxy.DynamicPF &
com.github.andyshao.proxy.CglibPF interface build the ProxyFactory.

*Improve Java System property*<br>
The com.github.andyshao.lang.GeneralSystemProperty that can get the Java System
Property more convenient is a Enum class. 
