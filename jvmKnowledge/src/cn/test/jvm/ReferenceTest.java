package cn.test.jvm;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class ReferenceTest {

	public static void main(String[] args) {
		//在堆中创建一个对象Obj
		//在栈中创建一个p来抢引用此对象Obj
		Person p = new Person(1);
		
		//在栈中创建一个softReference来软引用此对象Obj,可以获取对象的属性值
		//SoftReference<Person> softReference = new SoftReference<Person>(p);		
		/*p = null; //断开p和Obj的强引用
		//System.out.println(p.getId());
		System.gc();
		System.out.println(softReference.get().getId());*///可以获得,并没有回收,即使使用垃圾回收
		
		
		//在栈中创建一个weakReference来弱引用此对象Obj 可以获取对象的属性值
		/*WeakReference<Person> weakReference = new WeakReference<Person>(p);
		p = null;
		System.out.println(weakReference.get().getId());
		System.gc();
		System.out.println(weakReference.get().getId());*///垃圾回收之后,弱引用将被回收
	
		
		/**
		 *虚引用的作用在于,这个对象被回收器回收时,收到一个系统通知 
		 */
		ReferenceQueue<Person>  referenceQueue = new ReferenceQueue<>();
		//在栈中创建一个phantomReference来虚引用此对象Obj, 不可以获取对象的属性值
		PhantomReference<Person> phantomReference = new PhantomReference<Person>(p, referenceQueue);
		//System.out.println(phantomReference.get().getId()); //获取不到
		
		//回收前
		System.out.println(referenceQueue.poll());
		
		//回收后
		p = null;
		System.gc(); //垃圾回收之后会往referenceQueue中放信息
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(referenceQueue.poll());
	}

}

class Person{
	
	public Person(Integer id) {
		this.id = id;
	}

	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/*@Override
	protected void finalize() throws Throwable {
		System.out.println("finalized!!!");
	}*/
	
}

