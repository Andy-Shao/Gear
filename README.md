Some thing about the useful tools of the **JDK 1.8**.
I find out some API is not very well in Java Core API.
I try to repair it with my experience and shall with you.
If there any problems then send the email to **hj13035952619@126.com**
###############################################################################

* must be JDK1.8 or up.
* must be Maven3 or up.

If you want to let it into the eclipse. Try to use the command:
mvn eclipse:eclipse

You could download from the maven repository, now!<br>
&lt;dependency&gt;<br>
	&lt;groupId&gt;com.github.Andy-Shao&lt;/groupId&gt;<br>
	&lt;artifactId&gt;Gear&lt;/artifactId&gt;<br>
	&lt;version&gt;${Gear.Version.Number}&lt;/version&gt;<br>
&lt;/dependency&gt;<br>
###############################################################################
**What the newest plugins for v2.5**

Fix the bug of ArrayWrapper<br>

**What the newest plugins for v2.4**

Rewrite the ArrayOperation make it more efficient than before<br>
Adding ArrayWrapper for array wrap<br>
Adding adjust toArray() in ArrayOperation<br>
Adding lastIndexofAll() and indexOfAll in ArrayOperation<br>
Adding com.github.andyshao.system package for information running job<br>
Reflects add a new newInstance() method<br>
Adding ClassAssembly<br>
Fix the bugging of ByteBufferReader and String BufferReader<br>

**What the newest plugins for v2.3**

All of value type operation class add the comparator<T> function. e.g. CharOperation has comparator()<br>
Adding com.github.andyshao.nio.StringBufferReader<br>
com.github.andyshao.lang.GeneralSystemProperty add key(), value() option<br>
Adding com.github.andyshao.nio.BufferReader inteface and some sub classess<br>
Adding CharBufferOperation, ShortBufferOperation, LongBuffereOperation ...etc<br>

**What the newest plugins for v2.2**

Move ByteBufereOperation to ByteBufferOperation<br>
Move ArrayOperation.findFirstItem() to ArrayOperation.indexOf()<br>
Move ArrayOperation.findLastItem() to ArrayOperation.lastIndexOf()<br>
ByteBufferOperation add indexOf() and lastIndexOf() methods<br>
AutoIncreaseArray add method toAutoIncreaseArray()<br>

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
